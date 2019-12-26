package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.autonomous.script.Interpreter;
import org.firstinspires.ftc.teamcode.autonomous.script.ScriptException;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;

import java.io.FileNotFoundException;

public class Scripting extends LinearOpMode {

    private Interpreter interpreter;

    @Override
    public void runOpMode() {
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
                wait(5000);
                interpreter.executeNextCommand();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
