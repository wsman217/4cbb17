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
    private Bot bot;
    private static TeleOPV2 instance;

    @Override
    public void runOpMode() {
        instance = this;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        bot = new Bot().init(this.hardwareMap, this);
        drive = bot.getDrive();
        accessories = bot.getAccessories();

        waitForStart();
        bot.getPeriod().reset();
        /*accessories.switchIntakePositions();*/

        while(opModeIsActive()) {
            drive.joyStick(gamepad1);
            accessories.intake(gamepad2);
            accessories.lift(gamepad2);
            if (gamepad1.a)
                accessories.switchFoundationMover();
            if (gamepad2.a)
                accessories.switchClaw();
            if (gamepad2.b)
                accessories.swingClaw();
            if (gamepad2.left_bumper && gamepad2.right_bumper)
                accessories.dropCapstone();
            if (gamepad2.y)
                accessories.switchIntakePositions();
        }
    }

    public static TeleOPV2 getInstance() {
        return instance;
    }
}
