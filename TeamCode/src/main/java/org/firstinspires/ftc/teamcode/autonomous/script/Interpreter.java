package org.firstinspires.ftc.teamcode.autonomous.script;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Interpreter {

    private Scanner activeScript;
    private LinkedList<String> commands = new LinkedList<>();
    private boolean filesFound = false;
    private OpMode opMode;

    public Interpreter(OpMode opMode) throws FileNotFoundException {
        this.opMode = opMode;
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
            throwException();
        while (activeScript.hasNext())
            this.commands.push(activeScript.next().toLowerCase());
    }

    public void executeNextCommand() throws ScriptException {
        if (!filesFound)
            throwException();
        String command = commands.removeFirst();
        if (command.startsWith("//") || command.length() <= 0)
            return;
        for (Commands cmd : Commands.values()) {
            if (!command.startsWith(cmd + ""))
                continue;
            if (cmd == Commands.DRIVE) {
                opMode.telemetry.addLine("Got to the drive command.");
                opMode.telemetry.update();
            } else if (cmd == Commands.STRAFE) {
                opMode.telemetry.addLine("Got to the strafe command.");
                opMode.telemetry.update();
            }
        }
    }

    private void throwException() throws ScriptException {
        throw new ScriptException("Either the the scriptConfig.txt is missing or the specified script is missing.");
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
