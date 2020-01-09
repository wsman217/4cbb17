package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardwarev2.Accessories;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

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

        mainLoop:
        while (opModeIsActive() && counter < 3) {
            //Drive to where we are in front of a stone.

            //Move forward till we are within 5-6 inches of a stone.
            distanceStones();

            //Test the color of the stones and move if its is a stone
            boolean isStone = senseColor();
            if (!isStone) {
                drive.rotate(-168, .2);
            }

            drive.drive(.2, -205.3944, 30);

            counter++;
        }
    }

    private void distanceStones() {
        while (!(distance.getDistance(DistanceUnit.INCH) >= 3 && distance.getDistance(DistanceUnit.INCH) <= 4)) {
            telemetry.addLine("Distance: " + distance.getDistance(DistanceUnit.INCH));
            double pow = distance.getDistance(DistanceUnit.INCH) - 3.5d;
            if (pow > 0)
                pow = Math.min(.20, pow);
            else
                pow = Math.max(-.20, pow);
            telemetry.addLine("Power" + pow);
            telemetry.update();
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
        return hsv[0] <= 90;
    }
}
