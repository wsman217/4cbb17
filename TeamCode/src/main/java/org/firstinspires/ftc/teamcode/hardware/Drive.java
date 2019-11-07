package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drive {

    private DcMotor leftDrive, rightDrive;
    private DcMotor frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive;

    public Drive(DcMotor leftDrive, DcMotor rightDrive) {
        this.leftDrive = leftDrive;
        this.rightDrive = rightDrive;
    }

    public Drive(DcMotor frontLeftDrive, DcMotor frontRightDrive, DcMotor backLeftDrive, DcMotor backRightDrive) {
        this.frontLeftDrive = frontLeftDrive;
        this.frontRightDrive = frontRightDrive;
        this.backLeftDrive = backLeftDrive;
        this.backRightDrive = backRightDrive;
    }

    public void driveFoward() {

        resetDrive();
    }

    public void joystick(Gamepad pad) {
        double left_stick_y = pad.left_stick_y;
        double right_stick_y = pad.right_stick_y;
        leftDrive.setPower(left_stick_y);
        rightDrive.setPower(right_stick_y);
    }

    private void resetDrive() {

    }
}
