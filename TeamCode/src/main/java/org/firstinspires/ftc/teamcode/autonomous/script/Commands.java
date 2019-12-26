package org.firstinspires.ftc.teamcode.autonomous.script;

import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

public class Commands {

    private Bot bot;
    private Drive drive;

    public Commands(Bot bot) {
        this.bot = bot;
        this.drive = bot.getDrive();
    }

    public void drive(double speed, double distanceInMM, double timeout) {
        drive.drive(speed, distanceInMM, timeout);
    }
}
