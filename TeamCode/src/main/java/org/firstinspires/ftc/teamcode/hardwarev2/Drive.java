package org.firstinspires.ftc.teamcode.hardwarev2;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drive {

    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private LinearOpMode opMode;
    private Telemetry telem;

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
        this.telem = opMode.telemetry;
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
        if (wheelFrontLeftPower < 0.2 && wheelFrontLeftPower > -0.2)
            wheelFrontLeftPower = 0;
        if (wheelFrontRightPower < 0.2 && wheelFrontRightPower > -0.2)
            wheelFrontRightPower = 0;
        if (wheelBackLeftPower < 0.2 && wheelBackLeftPower > -0.2)
            wheelBackLeftPower = 0;
        if (wheelBackRightPower < 0.2 && wheelBackRightPower > -0.2)
            wheelBackRightPower = 0;

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

    public void strafe(StrafeDirection direction, double speed, double distanceInMM, double timeout) {
        if (!opMode.opModeIsActive())
            return;
        int target = ((int) (distanceInMM * COUNTS_PER_MM));

        if (direction == StrafeDirection.FORWARD || direction == StrafeDirection.BACKWARD)
            drive(speed, distanceInMM, timeout);
        if (direction == StrafeDirection.LEFT)
            moveMotors(-target, target, target, -target, speed, timeout);
        else if (direction == StrafeDirection.RIGHT)
            moveMotors(target, -target, -target, target,speed, timeout);
        else if (direction == StrafeDirection.LEFT_FRONT)
            moveMotors(0, target, target, 0, speed, timeout);
        else if (direction == StrafeDirection.RIGHT_FRONT)
            moveMotors(target, 0, 0, target, speed, timeout);
        else if (direction == StrafeDirection.LEFT_BACK)
            moveMotors(-target, 0, 0, target, speed, -timeout);
        else if (direction == StrafeDirection.RIGHT_BACK)
            moveMotors(0, -target, -target, 0, speed, timeout);
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
        setMode((DcMotor.RunMode.RUN_WITHOUT_ENCODER));
    }

    private void setPower(double fl, double fr, double bl, double br) {
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

    private void setMode(DcMotor.RunMode mode) {
        leftFront.setMode(mode);
        rightFront.setMode(mode);
        leftBack.setMode(mode);
        rightBack.setMode(mode);
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
                case 6: return BACKWARD;
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
}
