package org.firstinspires.ftc.teamcode.autonomous;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardwarev2.Accessories;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

@Autonomous(name = "Setup Color", group = "autos")
public class SetupColor extends LinearOpMode {
    private Bot bot;
    private Drive drive;
    private Accessories accessories;

    private ColorSensor color;
    private DistanceSensor distance;

    private float[] hsv = {0, 0, 0};

    private final double GLOBAL_SPEED = .5;

    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Bot().init(hardwareMap, this);
        drive = bot.getDrive();
        accessories = bot.getAccessories();

        color = accessories.color;
        distance = accessories.distanceSensor;

        waitForStart();

        while (opModeIsActive()) {
            distanceStones();
            senseColor();
        }
    }

    private void distanceStones() {
        while (!(distance.getDistance(DistanceUnit.INCH) >= 3 && distance.getDistance(DistanceUnit.INCH) <= 4)) {
            double pow = distance.getDistance(DistanceUnit.INCH) - 3.5d;
            //Decide the max power to use on the motors.
            if (pow > 0)
                pow = Math.min(GLOBAL_SPEED, pow);
            else
                pow = Math.max(-GLOBAL_SPEED, pow);
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
        return hsv[0] <= 105;
    }
}
