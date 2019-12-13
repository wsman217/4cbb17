package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teleop.TeleOPV1;

public class Accessories {

    private static boolean isFoundDown, isClawUp;
    private static long lastClickedFoundation;
    private static long lastClickedClaw;
    private DcMotor linearLift;
    private Servo foundationMover, claw;
    int counter = 0;

    //private TeleOPV1 main;

    Accessories init(Bot bot) {
        //main = TeleOPV1.getInstance();
        isFoundDown = false;
        this.linearLift = bot.getLinearLift();
        this.foundationMover = bot.getFoundationMover();
        this.claw = bot.getClaw();

        this.linearLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        foundationMover.setPosition(-1);
        claw.setPosition(-1);
        lastClickedFoundation = System.currentTimeMillis() / 1000;
        lastClickedClaw = System.currentTimeMillis() / 1000;
        return this;
    }

    public void lift(Gamepad pad) {
        int LINEAR_LIFT_MAX = -900;
        int LINEAR_LIFT_MIN = -2;
        if ((linearLift.getCurrentPosition() <= LINEAR_LIFT_MAX && pad.left_stick_y < 0) ||
                (linearLift.getCurrentPosition() >= LINEAR_LIFT_MIN && pad.left_stick_y > 0)) {
            linearLift.setPower(0);
            return;
        }
        double leftStickY = pad.left_stick_y;
        linearLift.setPower(leftStickY);
    }

    public void foundationMover(boolean isPressed) {
        if (isPressed) {
            long currentTime = System.currentTimeMillis() / 1000;
            long difference = currentTime - lastClickedFoundation;
            if (difference >= .5d) {
                lastClickedFoundation = currentTime;
                foundationMover.setPosition(isFoundDown ? -1 : 1);
                isFoundDown = !isFoundDown;
            }
        }
    }

    public void claw(boolean isPressed) {
        if (isPressed) {
            long currentTime = System.currentTimeMillis() / 1000;
            long difference = currentTime - lastClickedClaw;
            if (difference >= .5d) {
                lastClickedClaw = currentTime;
                claw.setPosition(isClawUp ? -1 : 1);
                isClawUp = !isClawUp;
            }
        }
    }
}
