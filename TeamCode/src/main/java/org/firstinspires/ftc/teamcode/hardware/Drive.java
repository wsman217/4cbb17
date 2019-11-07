package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Drive {

    private DcMotor leftDrive, rightDrive;
    private DcMotor frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive;

    public Drive(DcMotor leftDrive, DcMotor rightDrive) {

    }

    public Drive(DcMotor frontLeftDrive, DcMotor frontRightDrive, DcMotor backLeftDrive, DcMotor backRightDrive) {

    }

    public static void driveFoward() {

        resetDrive();
    }

    private static void resetDrive() {

    }
}
