package org.firstinspires.ftc.teamcode.hardwarev2;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;

public class Accessories {

    private static boolean isFoundDown, isIntakeLiftDown, isSwingForward, isClawGripping;
    private static long lastClickedFoundation, lastClickedIntakeLift, lastClickedSwingClaw, lastClickedClaw;
    //private DcMotor linearLift;
    private Servo /*foundationMoverLeft, foundationMoverRight,*/ intakeLiftLeft, intakeLiftRight;
    private Servo claw, capstone, clawSwing;
    private DcMotor leftIntake, rightIntake;
    private DcMotor lift;
    private LinearOpMode opMode;

    public ColorSensor color;
    public DistanceSensor distanceSensor;

    Accessories init(Bot bot) {
        this.opMode = bot.getOpMode();
        isFoundDown = true;
        isIntakeLiftDown = true;
        isSwingForward = false;
        isClawGripping = true;
        lastClickedFoundation = System.currentTimeMillis() / 1000;
        lastClickedIntakeLift = System.currentTimeMillis() / 1000;
        lastClickedClaw = System.currentTimeMillis() / 1000;
        lastClickedSwingClaw = System.currentTimeMillis() / 1000;

        /*this.foundationMoverLeft = bot.getFoundationMoverLeft();
        this.foundationMoverRight = bot.getFoundationMoverRight();*/
        this.claw = bot.getClaw();
        this.capstone = bot.getCapstone();
        this.clawSwing = bot.getClawSwing();
        this.intakeLiftLeft = bot.getIntakeLiftLeft();
        this.intakeLiftRight = bot.getIntakeLiftRight();
        this.leftIntake = bot.getIntakeLeft();
        this.rightIntake = bot.getIntakeRight();

        this.lift = bot.getLift();

        this.color = bot.getColor();
        this.distanceSensor = bot.getDistance();

        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        //TODO Set foundation movers to their up positions.

        //TODO Set intakes lifts to their in position.

        //TODO Set lift to down position.

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        return this;
    }

    public void swingClaw() {
        long currentTime = System.currentTimeMillis() / 1000;
        long diff = currentTime - lastClickedSwingClaw;
        if (diff <= .5)
            return;
        lastClickedSwingClaw = currentTime;
        double target = isSwingForward ? 0 : 1;
        isSwingForward = !isSwingForward;
        clawSwing.setPosition(target);
    }

    public void switchClaw() {
        long currentTime = System.currentTimeMillis() / 1000;
        long diff = currentTime - lastClickedClaw;
        if (diff <= .5)
            return;
        lastClickedClaw = currentTime;
        double target = isClawGripping ? 0 : 1;
        isClawGripping = !isClawGripping;
        claw.setPosition(target);
        opMode.telemetry.addLine("Switching claw");
        opMode.telemetry.update();
    }

    public void dropCapstone() {
        opMode.telemetry.addLine("Dropping cap");
        opMode.telemetry.update();
        capstone.setPosition(0);
    }

    /*public void switchFoundationMover() {
        long currentTime = System.currentTimeMillis() / 1000;
        long diff = currentTime - lastClickedFoundation;
        if (diff <= .5)
            return;
        lastClickedFoundation = currentTime;
        double leftTarget = isFoundDown ? 0 : 1;
        double rightTarget = isFoundDown ? 0 : .5;
        isFoundDown = !isFoundDown;
        foundationMoverLeft.setPosition(leftTarget);
        foundationMoverRight.setPosition(rightTarget);
    }*/

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

    public void setClaw(boolean lean) {
        claw.setPosition(lean ? 0 : 1);
    }

    public void intakeSetPosition(boolean lean) {
        intakeLiftLeft.setPosition(lean ? -1 : 1);
        intakeLiftRight.setPosition(lean ? 1 : -1);
    }

    public void lift(Gamepad pad) {
        double liftSpeed = -pad.right_stick_y;
        lift.setPower(liftSpeed);
    }

    public void lift(double distance) {
        lift.setTargetPosition((int) (lift.getCurrentPosition() + distance));
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);
        while (opMode.opModeIsActive() && !lift.isBusy()) {
            opMode.idle();
        }
    }

    public void setClawLocation(boolean lean) {
        clawSwing.setPosition(lean ? 1 : 0);
    }

    public void runIntake(Gamepad pad) {
        double intakeSpeed = -pad.left_stick_y;
        leftIntake.setPower(intakeSpeed);
        rightIntake.setPower(intakeSpeed);
    }

    public void runIntake(boolean enabled) {
        leftIntake.setPower(enabled ? 1 : -1);
        rightIntake.setPower(enabled ?  1 : -1);
    }

    public void turnIntakeOff() {
        leftIntake.setPower(0);
        rightIntake.setPower(0);
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

    static class SwitchHelper {
        boolean newBoo;
        long newLong;

        SwitchHelper(boolean newBoo, long newLong) {
            this.newBoo = newBoo;
            this.newLong = newLong;
        }
    }
}
