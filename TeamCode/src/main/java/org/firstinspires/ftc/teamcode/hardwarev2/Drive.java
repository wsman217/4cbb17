package org.firstinspires.ftc.teamcode.hardwarev2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drive {

    private DcMotor leftFront, rightFront, leftBack, rightBack;

    Drive(Bot bot) {
        this.leftFront = bot.getLeftFront();
        this.rightFront = bot.getRightFront();
        this.leftBack = bot.getLeftBack();
        this.rightBack = bot.getRightBack();
    }

    public void joyStick(Gamepad pad) {
        double y1 = pad.left_stick_y;
        double x1 = -pad.left_stick_x;
        double x2 = -pad.right_stick_x;
        double wheelFrontRightPower = y1 - x2 - x1;
        double wheelBackRightPower = y1 - x2 + x1;
        double wheelFrontLeftPower = y1 + x2 + x1;
        double wheelBackLeftPower = y1 + x2 - x1;

        double max = Math.max(Math.abs(wheelFrontRightPower), Math.max(Math.abs(wheelBackRightPower),
                Math.max(Math.abs(wheelFrontLeftPower), Math.abs(wheelBackLeftPower))));

        if (max > 1.0) {
            wheelFrontRightPower /= max;
            wheelBackRightPower /= max;
            wheelFrontLeftPower /= max;
            wheelBackLeftPower /= max;
        }

        leftFront.setPower(wheelFrontLeftPower);
        rightFront.setPower(wheelFrontRightPower);
        leftBack.setPower(wheelBackLeftPower);
        rightBack.setPower(wheelBackRightPower);
    }
}
