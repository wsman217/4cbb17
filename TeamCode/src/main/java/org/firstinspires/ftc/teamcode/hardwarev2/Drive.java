package org.firstinspires.ftc.teamcode.hardwarev2;

import android.graphics.drawable.GradientDrawable;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Drive {

    public DcMotor leftFront, rightFront, leftBack, rightBack;
    private LinearOpMode opMode;
    private Telemetry telem;

    private IMU imu;

    private long turboLC;
    private boolean turbo = false;

    /*private static final double COUNTS_PER_MOTOR_REV = 28d;
    private static final double GEAR_RATIO = 21.52d;
    private static final double WHEEL_DIAMETER_MM = 100d;
    private static final double COUNTS_PER_MM = (COUNTS_PER_MOTOR_REV * GEAR_RATIO) / (WHEEL_DIAMETER_MM * 3.1415);*/
    private static final double COUNTS_PER_MM = 1.7585741331;

    private ElapsedTime time = new ElapsedTime();

    Drive(Bot bot) {
        this.leftFront = bot.getLeftFront();
        this.rightFront = bot.getRightFront();
        this.leftBack = bot.getLeftBack();
        this.rightBack = bot.getRightBack();
        this.opMode = (LinearOpMode) bot.getOpMode();
        imu = new IMU(bot);
        this.telem = opMode.telemetry;
        turboLC = System.currentTimeMillis() / 1000;
    }

    public void joyStick(Gamepad pad) {
        double y1 = -pad.left_stick_y;
        double x1 = pad.left_stick_x;
        double x2 = -pad.right_stick_x;

        double wheelFrontRightPower = y1 - x2 - x1;
        double wheelBackRightPower = y1 - x2 + x1;
        double wheelFrontLeftPower = y1 + x2 + x1;
        double wheelBackLeftPower = y1 + x2 - x1;

        double max = Math.max(Math.abs(wheelFrontRightPower), Math.max(Math.abs(wheelBackRightPower),
                Math.max(Math.abs(wheelFrontLeftPower), Math.abs(wheelBackLeftPower))));

        if (max > 1.0) {
            wheelFrontRightPower /= max;
            wheelBackRightPower /= max;
            wheelFrontLeftPower /= max;
            wheelBackLeftPower /= max;
        }

        double turbo = !this.turbo ? .4 : 1;
        wheelFrontLeftPower *= turbo;
        wheelFrontRightPower *= turbo;
        wheelBackLeftPower *= turbo;
        wheelBackRightPower *= turbo;

        /*if (wheelFrontLeftPower < 0.2 && wheelFrontLeftPower > -0.2)
            wheelFrontLeftPower = 0;
        if (wheelFrontRightPower < 0.2 && wheelFrontRightPower > -0.2)
            wheelFrontRightPower = 0;
        if (wheelBackLeftPower < 0.2 && wheelBackLeftPower > -0.2)
            wheelBackLeftPower = 0;
        if (wheelBackRightPower < 0.2 && wheelBackRightPower > -0.2)
            wheelBackRightPower = 0;*/

        leftFront.setPower(wheelFrontLeftPower);
        rightFront.setPower(wheelFrontRightPower);
        leftBack.setPower(wheelBackLeftPower);
        rightBack.setPower(wheelBackRightPower);
    }

    public void drive(double speed, double distanceInMM, double timeout) {
        if (!opMode.opModeIsActive())
            return;
        int target = ((int) (distanceInMM * COUNTS_PER_MM));
        moveMotors(target, target, target, target, speed, timeout);
    }

    public void manualDrive(double flDistance, double flSpeed, double frDistance, double frSpeed, double blDistance, double blSpeed,
                       double brDistance, double brSpeed, double timeout) {
        int fLTarget =  (int) (flDistance * COUNTS_PER_MM), fRTarget = (int) (frDistance * COUNTS_PER_MM), bLTarget = (int) (blDistance * COUNTS_PER_MM), bRTarget = (int) (brDistance * COUNTS_PER_MM);
        leftFront.setTargetPosition(leftFront.getCurrentPosition() + fLTarget);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() + fRTarget);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() + bLTarget);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() + bRTarget);

        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        time.reset();
        setPower(Math.abs(flSpeed), Math.abs(frSpeed), Math.abs(blSpeed), Math.abs(brSpeed));

        while (opMode.opModeIsActive() && (time.seconds() < timeout) && ((leftFront.isBusy() || fLTarget == 0) && (rightFront.isBusy() || fRTarget == 0) &&
                (leftBack.isBusy() || bLTarget == 0) && (rightBack.isBusy() || bRTarget == 0))) {
            telem.addData("Path1", "Running to %7d :%7d :%7d :%7d", fLTarget, fRTarget, bLTarget, bRTarget);
            telem.update();
        }

        setPower(0, 0, 0, 0);
        setMode((DcMotor.RunMode.RUN_USING_ENCODER));
    }

    public void strafe(StrafeDirection direction, double speed, double distanceInMM, double timeout) {
        if (!opMode.opModeIsActive())
            return;
        int target = ((int) (distanceInMM * COUNTS_PER_MM));

        if (direction == StrafeDirection.FORWARD || direction == StrafeDirection.BACKWARD)
            drive(speed, distanceInMM, timeout);
        if (direction == StrafeDirection.LEFT)
            moveMotors(-target, target, target, -target, speed, timeout);
        else if (direction == StrafeDirection.RIGHT)
            moveMotors(target, -target, -target, target, speed, timeout);
        else if (direction == StrafeDirection.LEFT_FRONT)
            moveMotors(0, target, target, 0, speed, timeout);
        else if (direction == StrafeDirection.RIGHT_FRONT)
            moveMotors(target, 0, 0, target, speed, timeout);
        else if (direction == StrafeDirection.LEFT_BACK)
            moveMotors(-target, 0, 0, - target, speed, timeout);
        else if (direction == StrafeDirection.RIGHT_BACK)
            moveMotors(0, -target, -target, 0, speed, timeout);
    }

    public void strafeWithPower(StrafeDirection direction, double speed) {
        if (!opMode.opModeIsActive())
            return;

        if (direction == StrafeDirection.FORWARD || direction == StrafeDirection.BACKWARD)
            /*drive(speed, distanceInMM, timeout);*/
            return;
        if (direction == StrafeDirection.LEFT)
            moveMotorsWithPower(-speed, speed, speed, -speed);
        else if (direction == StrafeDirection.RIGHT)
            moveMotorsWithPower(speed, -speed, -speed, speed);
        else if (direction == StrafeDirection.LEFT_FRONT)
            moveMotorsWithPower(0, speed, speed, 0);
        else if (direction == StrafeDirection.RIGHT_FRONT)
            moveMotorsWithPower(speed, 0, 0, speed);
        else if (direction == StrafeDirection.LEFT_BACK)
            moveMotorsWithPower(-speed, 0, 0, speed);
        else if (direction == StrafeDirection.RIGHT_BACK)
            moveMotorsWithPower(0, -speed, -speed, 0);
    }

    public void switchTurbo() {
        long current = System.currentTimeMillis() / 1000;
        if (current - turboLC < .5)
            return;
        turboLC = current;
        turbo = !turbo;
    }

    public void stopMotors() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    private void moveMotorsWithPower(double flPower, double fRPower, double bLPower, double bRPower) {
        leftFront.setPower(flPower);
        rightFront.setPower(fRPower);
        leftBack.setPower(bLPower);
        rightBack.setPower(bRPower);
    }

    private void moveMotors(int fLTarget, int fRTarget, int bLTarget, int bRTarget, double speed, double timeout) {
        leftFront.setTargetPosition(leftFront.getCurrentPosition() + fLTarget);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() + fRTarget);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() + bLTarget);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() + bRTarget);

        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        time.reset();
        speed = Math.abs(speed);
        setPower(speed, speed, speed, speed);

        while (opMode.opModeIsActive() && (time.seconds() < timeout) && ((leftFront.isBusy() || fLTarget == 0) && (rightFront.isBusy() || fRTarget == 0) &&
                (leftBack.isBusy() || bLTarget == 0) && (rightBack.isBusy() || bRTarget == 0))) {
            telem.addData("Path1", "Running to %7d :%7d :%7d :%7d", fLTarget, fRTarget, bLTarget, bRTarget);
            telem.update();
        }

        setPower(0, 0, 0, 0);
        setMode((DcMotor.RunMode.RUN_USING_ENCODER));
    }

    public void setPower(double fl, double fr, double bl, double br) {
        leftFront.setPower(fl);
        rightFront.setPower(fr);
        leftBack.setPower(bl);
        rightBack.setPower(br);
    }

    private void setNoPower(DcMotor.ZeroPowerBehavior behave) {
        leftFront.setZeroPowerBehavior(behave);
        rightFront.setZeroPowerBehavior(behave);
        leftBack.setZeroPowerBehavior(behave);
        rightBack.setZeroPowerBehavior(behave);
    }

    public void setMode(DcMotor.RunMode mode) {
        leftFront.setMode(mode);
        rightFront.setMode(mode);
        leftBack.setMode(mode);
        rightBack.setMode(mode);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behave) {
        leftFront.setZeroPowerBehavior(behave);
        rightFront.setZeroPowerBehavior(behave);
        leftBack.setZeroPowerBehavior(behave);
        rightBack.setZeroPowerBehavior(behave);
    }

    public IMU getImu() {
        return imu;
    }

    public void rotate(int degrees, double power) {
        double fRPower, fLPower, bRPower, bLPower;

        imu.resetAngle();
        if (degrees < 0) {
            fLPower = -power;
            bLPower = -power;
            fRPower = power;
            bRPower = power;
            telem.addLine("FL" + fLPower + " FR" + fRPower + " BL" + bLPower + " BR" + bRPower);
            telem.update();
        } else if (degrees > 0) {
            fLPower = power;
            bLPower = power;
            fRPower = -power;
            bRPower = -power;
        } else
            return;

        moveMotorsWithPower(fLPower, fRPower, bLPower, bRPower);
        if (degrees < 0) {
            while (opMode.opModeIsActive() && imu.getAngle() == 0) {
                telem.addData("Header in angle 0", imu.getAngle());
                telem.addData("Header of actual", imu.lastAngle.firstAngle);
                telem.update();
            }
            while (opMode.opModeIsActive() && imu.getAngle() > degrees) {
                if (imu.getAngle() <= -100) {
                    telem.addData("Header in angle greater", imu.getAngle());
                    telem.addData("Header of actual", imu.lastAngle.firstAngle);
                    telem.update();
                }
            }
        } else
            while (opMode.opModeIsActive() && imu.getAngle() < degrees) {
                telem.addData("Header in angle less", imu.getAngle());
                telem.addData("Header of actual", imu.lastAngle.firstAngle);
                telem.update();
            }
        stopMotors();
        imu.resetAngle();
    }

    public enum StrafeDirection {
        FORWARD,
        BACKWARD,
        LEFT_FRONT,
        LEFT_BACK,
        RIGHT_FRONT,
        RIGHT_BACK,
        LEFT,
        RIGHT;

        public static StrafeDirection searchDirection(int dir) {
            switch (dir) {
                case 12:
                    return FORWARD;
                case 1:
                case 2:
                    return RIGHT_FRONT;
                case 3:
                    return RIGHT;
                case 4:
                case 5:
                    return RIGHT_BACK;
                case 6:
                    return BACKWARD;
                case 7:
                case 8:
                    return LEFT_BACK;
                case 9:
                    return LEFT;
                case 10:
                case 11:
                    return LEFT_FRONT;
                default:
                    return null;
            }
        }
    }

    public static class IMU {
        public BNO055IMU imu;
        public Orientation lastAngle = new Orientation();
        public Orientation orientation;
        public double gloabalAngle;

        public IMU(Bot bot) {
            imu = bot.getImu();
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.mode = BNO055IMU.SensorMode.IMU;
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.loggingEnabled = false;
            imu.initialize(parameters);
        }

        public double getAngle() {
            Orientation currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            this.orientation = currentAngle;
            double deltaAngle = currentAngle.firstAngle - lastAngle.firstAngle;

            if (deltaAngle < -180)
                deltaAngle += 360;
            else if (deltaAngle > 180)
                deltaAngle -= 360;

            gloabalAngle += deltaAngle;
            lastAngle = currentAngle;
            return -gloabalAngle;
        }

        public void resetAngle() {
            lastAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gloabalAngle = 0;
        }

        public double checkDirection() {
            double correction, angle, gain = .10;
            angle = getAngle();

            if (angle == 0)
                correction = 0;
            else
                correction = -angle;

            correction *= gain;
            return correction;
        }

        public boolean isGyroCalibrated() {
            return imu.isGyroCalibrated();
        }
    }
}
