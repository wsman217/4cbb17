package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardwarev2.Accessories;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

@TeleOp(name = "TeleOpV2", group = "teleop")
public class TeleOPV2 extends LinearOpMode {

    private Drive drive;
    private Accessories accessories;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Bot bot = new Bot().init(this.hardwareMap, this);
        drive = bot.getDrive();
        accessories = bot.getAccessories();

        waitForStart();
        bot.getPeriod().reset();
        /*accessories.switchIntakePositions();*/

        while (opModeIsActive()) {
            gamepad1();
            gamepad2();
        }
    }

    private void gamepad1() {
        drive.joyStick(gamepad1);
        if (gamepad1.a)
            drive.switchTurbo();
    }

    private void gamepad2() {
        accessories.runIntake(gamepad2);
        accessories.lift(gamepad2);
        if (gamepad2.a)
            accessories.switchClaw();
        if (gamepad2.b)
            accessories.swingClaw();
        if (gamepad2.left_bumper && gamepad2.right_bumper)
            accessories.dropCapstone();
        if (gamepad2.y)
            accessories.switchIntakePositions();
        /*if (gamepad1.x)
            accessories.switchFoundationMover();*/

    }
}
