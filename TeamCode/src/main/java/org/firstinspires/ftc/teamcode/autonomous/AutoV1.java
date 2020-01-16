package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.GlobalWarningSource;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardwarev2.Accessories;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

import javax.microedition.khronos.opengles.GL;

//Change this to left/right respective
@Autonomous(group = "Autonomous", name = "AutoV1")
public class AutoV1 extends LinearOpMode {
    private Bot bot;
    private Drive drive;
    private Accessories accessories;

    private ColorSensor color;
    private DistanceSensor distance;

    private float[] hsv = {0, 0, 0};
    private double offset = 0;

    private int counter = 0;
    private int skystoneDetections = 0;
    private final int SKYSTONE_LIMIT = 1;
    private double GLOBAL_SPEED = .5;

    public void runOpMode() throws InterruptedException {
        this.telemetry.addLine(">>Press play to start");
        this.telemetry.update();
        bot = new Bot().init(hardwareMap, this);
        drive = bot.getDrive();
        accessories = bot.getAccessories();

        telemetry.setAutoClear(false);

        color = accessories.color;
        distance = accessories.distanceSensor;

        while (!drive.getImu().isGyroCalibrated())
            idle();
        this.telemetry.addData("OpMode is ready to start", true);
        this.telemetry.update();
        drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        this.telemetry.addData("Op Mode has started ", true);
        this.telemetry.update();

        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Use this for when we get to the second skystone to break out of the loop and continue on the rest of the program.
        while (opModeIsActive() && counter < 3) {
            //Drive to where we are in front of a stone.

            //Move forward till we are within 3-4 inches of a stone.
            distanceStones();
            if (counter == 0) {
                ElapsedTime distanceTime = new ElapsedTime();
                while (distanceTime.seconds() <= .1)
                    idle();
                distanceStones();
            }

            //Test the color of the stones and move if its is a stone
            boolean isStone = senseColor();
            if (!isStone) {
                skystoneDetections++;
                drive.drive(GLOBAL_SPEED, 652.5944, 30);
                drive.rotate(-163, GLOBAL_SPEED);
                drive.strafe(Drive.StrafeDirection.RIGHT, GLOBAL_SPEED, 552.4, 30);
                drive.drive(GLOBAL_SPEED, -205.3944, 30);
                accessories.intakeSetPosition(false);
                accessories.runIntake(true);
                ElapsedTime time = new ElapsedTime();
                while (time.seconds() <= .5)
                    idle();
                drive.drive(GLOBAL_SPEED, 410.7888, 30);
                time = new ElapsedTime();
                while (time.seconds() <= .5)
                    idle();
                accessories.runIntake(true);
                drive.strafe(Drive.StrafeDirection.RIGHT, GLOBAL_SPEED, 457.2, 30);
                drive.drive(GLOBAL_SPEED, -1219.2, 30);
                drive.rotate(163, GLOBAL_SPEED);
                accessories.runIntake(false);
                drive.drive(GLOBAL_SPEED, 609.6, 30);

                /*accessories.setClaw(true);
                drive.strafe(Drive.StrafeDirection.LEFT_BACK, .75, 2316.24, 30);
                drive.drive(.75, -100, 30);
                drive.strafe(Drive.StrafeDirection.RIGHT, .75, 228.6, 30);
                drive.drive(.75, -1066.8, 30);
                accessories.lift(500);
                accessories.setClawLocation(false);
                accessories.lift(-500);
                accessories.setClaw(false);
                accessories.lift(500);
                drive.strafe(Drive.StrafeDirection.RIGHT, GLOBAL_SPEED, 152.4, 30);
                drive.drive(GLOBAL_SPEED, 1524, 30);*/
                if (skystoneDetections >= SKYSTONE_LIMIT)
                    break;
            }
            drive.drive(GLOBAL_SPEED, -205.3944, 30);
            counter++;
        }
    }

    private void distanceStones() {
        while (!(distance.getDistance(DistanceUnit.INCH) >= 3 && distance.getDistance(DistanceUnit.INCH) <= 4)) {
            telemetry.addLine("Distance: " + distance.getDistance(DistanceUnit.INCH));
            double pow = distance.getDistance(DistanceUnit.INCH) - 3.5d;
            //Decide the max power to use on the motors.
            if (pow > 0)
                pow = Math.min(GLOBAL_SPEED, pow);
            else
                pow = Math.max(-GLOBAL_SPEED, pow);
            telemetry.addLine("Power" + pow);
            telemetry.update();
            //When we make the other side of this auto this will have to Strafe Right
            drive.strafeWithPower(Drive.StrafeDirection.LEFT, pow);
        }
        drive.stopMotors();
    }

    private boolean senseColor() {
        final double SCALE_FACTOR = 255;
        Color.RGBToHSV((int) (color.red() * SCALE_FACTOR),
                (int) (color.green() * SCALE_FACTOR),
                (int) (color.blue() * SCALE_FACTOR),
                hsv);
        telemetry.addData("Hue", hsv[0]);
        telemetry.addData("Sat", hsv[1]);
        telemetry.addData("Val", hsv[2]);
        telemetry.update();
        return hsv[0] <= 100;
    }
}
