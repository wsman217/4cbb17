package org.firstinspires.ftc.teamcode.learningopmodes.hardware;

import com.qualcomm.hardware.lynx.LynxServoController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HardwareLearningbot {
    HardwareMap hwMap = null;
    public DcMotor leftDrive = null;
    private ElapsedTime period = new ElapsedTime();
    public DcMotor rightDrive = null;

    public void init(HardwareMap ahwMap) {
        this.hwMap = ahwMap;
        this.leftDrive = (DcMotor) this.hwMap.get(DcMotor.class, "left_drive");
        this.rightDrive = (DcMotor) this.hwMap.get(DcMotor.class, "right_drive");
        this.leftDrive.setDirection(Direction.FORWARD);
        this.rightDrive.setDirection(Direction.REVERSE);
        this.leftDrive.setPower(LynxServoController.apiPositionFirst);
        this.rightDrive.setPower(LynxServoController.apiPositionFirst);
        this.leftDrive.setMode(RunMode.RUN_WITHOUT_ENCODER);
        this.rightDrive.setMode(RunMode.RUN_WITHOUT_ENCODER);
    }
}
