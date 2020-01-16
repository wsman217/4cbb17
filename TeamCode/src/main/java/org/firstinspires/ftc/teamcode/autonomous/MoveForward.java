package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

@Autonomous(name = "MOVE", group = "autos")
public class MoveForward extends LinearOpMode {
    private Bot bot;
    private Drive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        bot = new Bot().init(hardwareMap, this);
        drive = bot.getDrive();

        waitForStart();
        drive.drive(.5, 1066.8, 30);
    }
}
