package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.hardwarev2.Accessories;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

@Autonomous(name = "RedFoundationInner", group = "auto")
public class RedFoundationInner extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Bot bot = new Bot().init(hardwareMap, this);
        Accessories accessories = bot.getAccessories();
        Drive drive = bot.getDrive();
        drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        accessories.setFoundation(false);

        while (!drive.getImu().isGyroCalibrated())
            idle();

        telemetry.addLine("Opmode is ready to start.");
        telemetry.update();
        waitForStart();

        drive.strafe(Drive.StrafeDirection.RIGHT_BACK, .8, 650.6, 30);
        drive.drive(.8, -459.6, 30);
        drive.drive(.3, -100, 30);
        accessories.setFoundation(true);
        ElapsedTime timer = new ElapsedTime();
        while (timer.seconds() < .75)
            idle();
        drive.drive(.2, -200, 30);

        drive.drive(.5, 1500, 30);
        accessories.setFoundation(false);
        timer = new ElapsedTime();
        while (timer.seconds() < .75)
            idle();
        drive.strafe(Drive.StrafeDirection.LEFT, .8, 762, 30);
        drive.drive(.8, -609.6, 30);
        drive.strafe(Drive.StrafeDirection.RIGHT, .8, 304.8, 30);
        drive.strafe(Drive.StrafeDirection.LEFT, .8, -609.6, 30);
    }
}
