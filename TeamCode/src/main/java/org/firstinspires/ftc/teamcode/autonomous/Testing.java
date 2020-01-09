package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

@Autonomous(group = "Autonomous", name = "Testing")
public class Testing extends LinearOpMode {

    private Bot bot;
    private Drive drive;

    @Override
    public void runOpMode() {
        this.bot = new Bot().init(this.hardwareMap, this);
        this.drive = bot.getDrive();

        waitForStart();

        if (opModeIsActive()) {
            drive.drive(.5, 20000, 30);
        }
    }
}
