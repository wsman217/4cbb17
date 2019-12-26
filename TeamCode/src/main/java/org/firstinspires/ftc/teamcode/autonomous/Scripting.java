package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.autonomous.script.Interpreter;
import org.firstinspires.ftc.teamcode.autonomous.script.ScriptException;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;

import java.io.FileNotFoundException;

@Autonomous(group = "Autonomous", name = "Scripting")
public class Scripting extends LinearOpMode {

    private Interpreter interpreter;
    private ElapsedTime time = new ElapsedTime();
    @Override
    public void runOpMode() {
        telemetry.setAutoClear(false);
        try {
            interpreter = new Interpreter(this);
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
