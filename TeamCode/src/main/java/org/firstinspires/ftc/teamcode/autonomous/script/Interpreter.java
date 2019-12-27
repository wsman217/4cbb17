package org.firstinspires.ftc.teamcode.autonomous.script;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.autonomous.script.exceptions.ParameterException;
import org.firstinspires.ftc.teamcode.autonomous.script.exceptions.ScriptException;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;
import org.firstinspires.ftc.teamcode.hardwarev2.Drive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Interpreter {

    private Scanner activeScript;
    private LinkedList<String> commands = new LinkedList<>();
    private boolean filesFound = false;
    private OpMode opMode;
    private Bot bot;
    private Drive drive;

    public Interpreter(OpMode opMode, Bot bot) throws FileNotFoundException {
        this.opMode = opMode;
        this.drive = bot.getDrive();
        this.bot = bot;
        opMode.telemetry.addLine("Start of interpreter.");
        opMode.telemetry.update();
        String PATH = "/storage/self/primary/FIRST/";
        File configFile = new File("/storage/self/primary/FIRST/scriptConfig.txt");
        if (!configFile.exists())
            throw new FileNotFoundException("Missing scriptConfig.txt in FIRST base folder.");
        Scanner configScanner = new Scanner(configFile);
        String activeScriptName = "";
        if (configScanner.hasNext())
            activeScriptName = configScanner.next();
        if (activeScriptName.length() <= 0)
            return;
        File activeScriptFile = new File(PATH + "scripts/" + activeScriptName);
        if (!activeScriptFile.exists())
            throw new FileNotFoundException("Script specified in scriptConfig.txt is missing.");
        this.activeScript = new Scanner(activeScriptFile);
        filesFound = true;
    }

    public void gatherCommands() throws ScriptException {
        if (!filesFound)
            throwScriptException();
        while (activeScript.hasNext())
            this.commands.push(activeScript.next().toLowerCase());
        opMode.telemetry.addLine("Finished gathering commands.");
        opMode.telemetry.update();
    }

    public void executeNextCommand() throws ScriptException, ParameterException {
        if (!filesFound)
            throwScriptException();
        String cmdAndParams = commands.removeFirst().toLowerCase();
        if (cmdAndParams.length() <= 0)
            return;
        String command = cmdAndParams.substring(0, cmdAndParams.indexOf("("));
        LinkedList<String> parameters = new LinkedList<>();
        Collections.addAll(parameters, cmdAndParams.substring(cmdAndParams.indexOf("(") + 1, cmdAndParams.indexOf(")")).split(","));
        if (command.startsWith("//") || command.length() <= 0)
            return;
        for (Commands cmd : Commands.values()) {
            if (!command.startsWith(cmd.getCommand() + ""))
                continue;

            if (cmd == Commands.DRIVE) {
                //Syntax: drive(speed<0-1>, distance<pos:forward, neg:backward>, timeout)
                if (!checkParams(parameters, 3))
                    throwParamException(cmdAndParams);
                drive.drive(parseDouble(parameters.get(0)), parseDouble(parameters.get(1)), parseDouble(parameters.get(2)));
            } else if (cmd == Commands.STRAFE) {
                //Syntax: strafe(direction<(like a clock) 12:forward, 1-2:forwardRight, 3:right, so on>, speed<0-1>, distance<pos:forward, neg:backward>, timeout)
                if (!checkParams(parameters, 4))
                    throwParamException(cmdAndParams);
                Drive.StrafeDirection direction = Drive.StrafeDirection.searchDirection((int) parseDouble(parameters.get(0)));
                drive.strafe(direction, parseDouble(parameters.get(1)), parseDouble(parameters.get(2)), parseDouble(parameters.get(3)));
            }
        }
    }

    private void throwScriptException() throws ScriptException {
        throw new ScriptException("Either the the scriptConfig.txt is missing or the specified script is missing.");
    }

    private void throwParamException(String cmdAndParams) throws ParameterException {
        throw new ParameterException("Parameters of command \"" + cmdAndParams + "\" are incorrect.");
    }

    private double parseDouble(String toParse) {
        return Double.parseDouble(toParse);
    }

    private boolean checkParams(LinkedList<String> params, int numParams) {
        if (params.size() != numParams)
            return false;
        return testParamsAsNumbers((String[]) Objects.requireNonNull(params.toArray()));
    }

    private boolean testParamsAsNumbers(String[] strings) {
        boolean allNumbers = true;
        for (String toTest : strings)
            allNumbers &= isNumber(toTest);
        return allNumbers;
    }

    private boolean isNumber(String test) {
        try {
            Double.parseDouble(test);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    enum Commands {
        DRIVE() {
            @Override
            public String getCommand() {
                return "drive";
            }
        },
        STRAFE() {
            @Override
            public String getCommand() {
                return "strafe";
            }
        };
        abstract String getCommand();
    }
}
