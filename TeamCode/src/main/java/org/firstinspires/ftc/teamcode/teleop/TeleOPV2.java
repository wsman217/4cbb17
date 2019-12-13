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
        /*telemetry.addData("Status", "Initialized");
        telemetry.update();*/
        //telemetry.setAutoClear(false);

        bot = new Bot().init(this.hardwareMap, this);
        drive = bot.getDrive();
        /*accessories = bot.getAccessories();*/

        waitForStart();
        bot.getPeriod().reset();
        /*accessories.switchIntakePositions();*/

        while(opModeIsActive()) {
            drive.joyStick(gamepad1);
            /*accessories.intake(gamepad2);
            if (gamepad2.a)
                accessories.switchFoundationMover();
            if (gamepad2.y)
                accessories.switchIntakePositions();*/
        }
    }

    public static TeleOPV2 getInstance() {
        return instance;
    }
}
