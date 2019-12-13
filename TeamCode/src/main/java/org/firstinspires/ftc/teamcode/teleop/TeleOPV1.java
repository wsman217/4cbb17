package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.Accessories;
import org.firstinspires.ftc.teamcode.hardware.Bot;
import org.firstinspires.ftc.teamcode.hardware.Drive;

@TeleOp(name = "TeleOpV1", group = "teleop")
public class TeleOPV1 extends LinearOpMode {

    private Drive drive;
    private Accessories accessories;
    private Bot bot;
    private static TeleOPV1 instance;

    @Override
    public void runOpMode() {
        instance = this;
        /*telemetry.addData("Status", "Initialized");
        telemetry.update();*/
        //telemetry.setAutoClear(false);

        bot = new Bot().init(this.hardwareMap);
        drive = bot.getDrive();
        accessories = bot.getAccessories();

        waitForStart();
        bot.getPeriod().reset();

        while(opModeIsActive()) {
            drive.joystick(gamepad1);
            accessories.lift(gamepad2);
            accessories.foundationMover(gamepad2.a);
            accessories.claw(gamepad2.b);
        }
    }

    public static TeleOPV1 getInstance() {
        return instance;
    }
}
