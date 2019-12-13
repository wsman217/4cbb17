package org.firstinspires.ftc.teamcode.hardwarev2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Bot {
    private HardwareMap hardwareMap = null;
    private ElapsedTime period = new ElapsedTime();

    private Drive drive;
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private Accessories accessories;
    //private DcMotor linearLift;
    //private Servo claw;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private Servo intakeLiftLeft;
    private Servo intakeLiftRight;
    private Servo foundationMoverLeft;
    private Servo foundationMoverRight;

    private OpMode opMode;

    public Bot init(HardwareMap map, OpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = map;
        leftFront = map.get(DcMotor.class, "leftFront");
        rightFront = map.get(DcMotor.class, "rightFront");
        leftBack = map.get(DcMotor.class, "leftBack");
        rightBack = map.get(DcMotor.class, "rightBack");
        //linearLift = map.get(DcMotor.class, "linearLift");
        //foundationMover = map.get(Servo.class, "foundationMover");
        //claw = map.get(Servo.class, "claw");

        /*intakeLeft = map.get(DcMotor.class, "intakeLeft");
        intakeRight = map.get(DcMotor.class, "intakeRight");
        intakeLiftLeft = map.get(Servo.class, "intakeLiftLeft");
        intakeLiftRight = map.get(Servo.class, "intakeLiftRight");
        foundationMoverLeft = map.get(Servo.class, "foundationLeft");
        foundationMoverRight = map.get(Servo.class, "foundationRight");*/

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        drive = new Drive(this);
        /*accessories = new Accessories().init(this);*/
        return this;
    }

    public HardwareMap getHardwareMap() {
        return hardwareMap;
    }

    public ElapsedTime getPeriod() {
        return period;
    }

    public Drive getDrive() {
        return drive;
    }

    public DcMotor getLeftFront() {
        return leftFront;
    }

    public DcMotor getRightFront() {
        return rightFront;
    }

    public DcMotor getLeftBack() {
        return leftBack;
    }

    public DcMotor getRightBack() {
        return rightBack;
    }

    public Accessories getAccessories() {
        return accessories;
    }

    public DcMotor getIntakeLeft() {
        return intakeLeft;
    }

    public DcMotor getIntakeRight() {
        return intakeRight;
    }

    public Servo getIntakeLiftLeft() {
        return intakeLiftLeft;
    }

    public Servo getIntakeLiftRight() {
        return intakeLiftRight;
    }

    public Servo getFoundationMoverLeft() {
        return foundationMoverLeft;
    }

    public Servo getFoundationMoverRight() {
        return foundationMoverRight;
    }

    public OpMode getOpMode() {
        return opMode;
    }

}
