package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.autonomous.script.Interpreter;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;

@Autonomous(group = "Autonomous", name = "Scripting")
public class Scripting extends LinearOpMode {

    private Interpreter interpreter;
    private ElapsedTime time = new ElapsedTime();
    private Bot bot;
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(false);
        this.bot = new Bot().init(hardwareMap, this);
        try {
            interpreter = new Interpreter(this, bot);
            interpreter.gatherCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }

        waitForStart();
        if (opModeIsActive()) {
            try {
                interpreter.executeNextCommand();
                while (time.seconds() < 5)
                    System.out.print(".");
                interpreter.executeNextCommand();
                while (time.seconds() < 5)
                    System.out.print(".");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
