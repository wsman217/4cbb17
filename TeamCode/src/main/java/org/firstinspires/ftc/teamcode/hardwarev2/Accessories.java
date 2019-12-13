package org.firstinspires.ftc.teamcode.hardwarev2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teleop.TeleOPV2;

import java.util.HashMap;

public class Accessories {

    private static boolean isFoundDown, isIntakeLiftDown;
    private static long lastClickedFoundation, lastClickedIntakeLift;
    //private DcMotor linearLift;
    private Servo foundationMoverLeft, foundationMoverRight, intakeLiftLeft, intakeLiftRight;
    private DcMotor leftIntake, rightIntake;
    private OpMode opMode;

    Accessories init(Bot bot) {
        this.opMode = bot.getOpMode();
        isFoundDown = false;
        isIntakeLiftDown = false;
        lastClickedFoundation = System.currentTimeMillis() / 1000;
        lastClickedIntakeLift = System.currentTimeMillis() / 1000;

        this.foundationMoverLeft = bot.getFoundationMoverLeft();
        this.foundationMoverRight = bot.getFoundationMoverRight();
        this.intakeLiftLeft = bot.getIntakeLiftLeft();
        this.intakeLiftRight = bot.getIntakeLiftRight();
        this.leftIntake = bot.getIntakeLeft();
        this.rightIntake = bot.getIntakeRight();

        //Set intake directions
        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set foundation movers to their up positions.

        //Set intakes lifts to their in position.

        return this;
    }

    public void switchFoundationMover() {
        long currentTime = System.currentTimeMillis() / 1000;
        long diff = currentTime - lastClickedFoundation;
        if (diff <= .5)
            return;
        lastClickedFoundation = currentTime;
        double place = isFoundDown ? 1 : -100;
        isFoundDown = !isFoundDown;
        opMode.telemetry.addLine("Place: " + place + " negPlace: " + -place + " isFoundDown:" + isFoundDown +
                " foundationMoverLeft: " + foundationMoverLeft);
        opMode.telemetry.addLine("foundationMoverLeft: " + foundationMoverLeft.getPosition() +
                " foundationMoverRight: " + foundationMoverRight.getPosition());

        opMode.telemetry.update();
        foundationMoverLeft.setPosition(-place);
        foundationMoverRight.setPosition(place);
    }

    public void switchIntakePositions() {
        /*long currentTime = System.currentTimeMillis() / 1000;
        long diff = currentTime - lastClickedIntakeLift;
        if (diff <= .5)
            return;
        lastClickedIntakeLift = currentTime;
        //IDK which value actually goes where just need them as placeholders.
        int place = isIntakeLiftDown ? -1 : 1;
        intakeLiftLeft.setPosition(place);
        intakeLiftRight.setPosition(-place);*/
        HashMap<Servo, Integer> servos = new HashMap<>();
        servos.put(intakeLiftLeft, 1);
        servos.put(intakeLiftRight, -1);
        SwitchHelper helper = switchMethod(lastClickedIntakeLift, isIntakeLiftDown, .5, servos);
        if (helper != null) {
            lastClickedIntakeLift = helper.newLong;
            isIntakeLiftDown = helper.newBoo;
        }
    }

    public void intake(Gamepad pad) {
        double intakeSpeed = -pad.left_stick_y;
        leftIntake.setPower(intakeSpeed);
        rightIntake.setPower(intakeSpeed);
    }

    private SwitchHelper switchMethod(long timeToUse, boolean booToUse, double timeout, HashMap<Servo, Integer> servos) {
        if (servos.isEmpty())
            return null;
        long currentTime = System.currentTimeMillis() / 1000;
        long diff = currentTime - timeToUse;
        if (diff <= timeout)
            return null;
        int place = booToUse ? -1 : 1;
        for (Servo key : servos.keySet())
            key.setPosition(servos.get(key) < 0 ? place : -place);
        return new SwitchHelper(!booToUse, currentTime);
    }

    class SwitchHelper {
        boolean newBoo;
        long newLong;

        SwitchHelper(boolean newBoo, long newLong) {
            this.newBoo = newBoo;
            this.newLong = newLong;
        }
    }
}
