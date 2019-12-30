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
            telemetry.addLine("Am I here?");
            telemetry.update();
            try {
                while(interpreter.hasNext()) {
                    interpreter.executeNextCommand();
                }
            } catch (Exception e) {
                telemetry.addLine("uh oh");
                telemetry.update();
                e.printStackTrace();
            }
        }
    }
}
