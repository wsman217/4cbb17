package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teleop.TeleOPV1;

public class Accessories {

    private static boolean isArmDown;
    private static long lastClicked;
    private DcMotor linearLift;
    private Servo foundationMover;
    int counter = 0;

    private TeleOPV1 main;

    Accessories init(Bot bot) {
        main = TeleOPV1.getInstance();
        isArmDown = false;
        this.linearLift = bot.getLinearLift();
        this.foundationMover = bot.getFoundationMover();

        this.linearLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        foundationMover.setPosition(-1);
        lastClicked = System.currentTimeMillis() / 1000;
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
            long difference = currentTime - lastClicked;
            if (difference >= .5d) {
                lastClicked = currentTime;
                foundationMover.setPosition(isArmDown ? -1 : 1);
                isArmDown = !isArmDown;
            }
        }
    }
}
