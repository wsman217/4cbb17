package org.firstinspires.ftc.teamcode.hardwarev2;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
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
    private DcMotor lift;
    //private Servo claw;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private Servo intakeLiftLeft;
    private Servo intakeLiftRight;
    private Servo foundationMoverLeft;
    private Servo foundationMoverRight;
    private Servo claw, capstone, clawSwing;

    private BNO055IMU imu;

    private ColorSensor color;
    private DistanceSensor distance;

    private OpMode opMode;

    public Bot init(HardwareMap map, OpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = map;
        leftFront = map.get(DcMotor.class, "leftFront");
        rightFront = map.get(DcMotor.class, "rightFront");
        leftBack = map.get(DcMotor.class, "leftBack");
        rightBack = map.get(DcMotor.class, "rightBack");
        lift = map.get(DcMotor.class, "lift");

        intakeLeft = map.get(DcMotor.class, "intakeLeft");
        intakeRight = map.get(DcMotor.class, "intakeRight");
        intakeLiftLeft = map.get(Servo.class, "intakeLiftLeft");
        intakeLiftRight = map.get(Servo.class, "intakeLiftRight");
        foundationMoverLeft = map.get(Servo.class, "foundationLeft");
        foundationMoverRight = map.get(Servo.class, "foundationRight");
        claw = map.get(Servo.class, "claw");
        capstone = map.get(Servo.class, "capstone");
        clawSwing = map.get(Servo.class, "clawSwing");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        color = map.get(ColorSensor.class, "color");
        distance = map.get(DistanceSensor.class, "color");

        imu = map.get(BNO055IMU.class, "imu");

        drive = new Drive(this);
        accessories = new Accessories().init(this);
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

    DcMotor getLeftFront() {
        return leftFront;
    }

    DcMotor getRightFront() {
        return rightFront;
    }

    DcMotor getLeftBack() {
        return leftBack;
    }

    DcMotor getRightBack() {
        return rightBack;
    }

    public Accessories getAccessories() {
        return accessories;
    }

    DcMotor getIntakeLeft() {
        return intakeLeft;
    }

    DcMotor getIntakeRight() {
        return intakeRight;
    }

    Servo getIntakeLiftLeft() {
        return intakeLiftLeft;
    }

    Servo getIntakeLiftRight() {
        return intakeLiftRight;
    }

    Servo getFoundationMoverLeft() {
        return foundationMoverLeft;
    }

    Servo getFoundationMoverRight() {
        return foundationMoverRight;
    }

    OpMode getOpMode() {
        return opMode;
    }

    public DcMotor getLift() {
        return lift;
    }

    public Servo getClaw() {
        return claw;
    }

    public Servo getCapstone() {
        return capstone;
    }

    public Servo getClawSwing() {
        return clawSwing;
    }

    public ColorSensor getColor() {
        return color;
    }

    public DistanceSensor getDistance() {
        return distance;
    }

    public BNO055IMU getImu() {
        return imu;
    }
}
