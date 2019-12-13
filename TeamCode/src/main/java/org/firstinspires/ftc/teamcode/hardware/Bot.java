package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Bot {
    private HardwareMap hardwareMap = null;
    private ElapsedTime period = new ElapsedTime();
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor linearLift;
    private Servo foundationMover;
    private Servo claw;
    private Drive drive;
    private Accessories accessories;


    public HardwareMap getHardwareMap() {
        return this.hardwareMap;
    }

    public ElapsedTime getPeriod() {
        return this.period;
    }

    public DcMotor getLeftDrive() {
        return leftDrive;
    }

    public DcMotor getRightDrive() {
        return rightDrive;
    }

    public DcMotor getLinearLift() {
        return linearLift;
    }

    public Servo getClaw() {
        return claw;
    }

    public Servo getFoundationMover() {
        return foundationMover;
    }

    public Drive getDrive() {
        return drive;
    }

    public Accessories getAccessories() {
        return accessories;
    }

    public Bot init(HardwareMap map) {
        this.hardwareMap = map;
        leftDrive = map.get(DcMotor.class, "leftDrive");
        rightDrive = map.get(DcMotor.class, "rightDrive");
        linearLift = map.get(DcMotor.class, "linearLift");
        foundationMover = map.get(Servo.class, "foundationMover");
        claw = map.get(Servo.class, "claw");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        drive = new Drive(leftDrive, rightDrive);
        accessories = new Accessories().init(this);
        return this;
    }
}
