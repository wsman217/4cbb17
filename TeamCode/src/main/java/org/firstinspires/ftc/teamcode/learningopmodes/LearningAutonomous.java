package org.firstinspires.ftc.teamcode.learningopmodes;

import com.qualcomm.hardware.lynx.LynxServoController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.learningopmodes.hardware.HardwareLearningbot;

@Disabled
@Autonomous(group = "Autonomous", name = "LearningAutonomous")
public class LearningAutonomous extends LinearOpMode {
    private static final double COUNTS_PER_INCH = 114.5949387235397d;
    private static final double COUNTS_PER_MOTOR_REV = 1440.0d;
    private static final double DRIVE_GEAR_REDUCTION = 1.0d;
    private static final double DRIVE_SPEED = 0.6d;
    private static final double TURN_SPEED = 0.5d;
    private static final double WHEEL_DIAMETER_INCHES = 4.0d;
    private HardwareLearningbot robot = new HardwareLearningbot();
    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        this.robot.init(this.hardwareMap);
        this.telemetry.addData("Status", (Object) "Resetting Encoders");
        this.telemetry.update();
        this.robot.leftDrive.setMode(RunMode.STOP_AND_RESET_ENCODER);
        this.robot.rightDrive.setMode(RunMode.STOP_AND_RESET_ENCODER);
        this.robot.leftDrive.setMode(RunMode.RUN_USING_ENCODER);
        this.robot.rightDrive.setMode(RunMode.RUN_USING_ENCODER);
        this.telemetry.addData("Path0", "Starting at %7d :%7d", Integer.valueOf(this.robot.leftDrive.getCurrentPosition()), Integer.valueOf(this.robot.rightDrive.getCurrentPosition()));
        this.telemetry.update();
        waitForStart();
        encoderDrive(DRIVE_SPEED, 48.0d, 48.0d, 5.0d);
        encoderDrive(0.5d, 12.0d, -12.0d, WHEEL_DIAMETER_INCHES);
        encoderDrive(DRIVE_SPEED, -24.0d, -24.0d, WHEEL_DIAMETER_INCHES);
        sleep(1000);
        this.telemetry.addData("Path", (Object) "Complete");
        this.telemetry.update();
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        if (opModeIsActive()) {
            int newLeftTarget = this.robot.leftDrive.getCurrentPosition() + ((int) (leftInches * COUNTS_PER_INCH));
            int newRightTarget = this.robot.rightDrive.getCurrentPosition() + ((int) (COUNTS_PER_INCH * rightInches));
            this.robot.leftDrive.setTargetPosition(newLeftTarget);
            this.robot.rightDrive.setTargetPosition(newRightTarget);
            this.robot.leftDrive.setMode(RunMode.RUN_TO_POSITION);
            this.robot.rightDrive.setMode(RunMode.RUN_TO_POSITION);
            this.runtime.reset();
            this.robot.leftDrive.setPower(Math.abs(speed));
            this.robot.rightDrive.setPower(Math.abs(speed));
            while (opModeIsActive() && this.runtime.seconds() < timeoutS && this.robot.leftDrive.isBusy() && this.robot.rightDrive.isBusy()) {
                this.telemetry.addData("Path1", "Running to %7d :%7d", Integer.valueOf(newLeftTarget), Integer.valueOf(newRightTarget));
                this.telemetry.addData("Path2", "Running at %7d :%7d", Integer.valueOf(this.robot.leftDrive.getCurrentPosition()), Integer.valueOf(this.robot.rightDrive.getCurrentPosition()));
                this.telemetry.update();
            }
            this.robot.leftDrive.setPower(LynxServoController.apiPositionFirst);
            this.robot.rightDrive.setPower(LynxServoController.apiPositionFirst);
            this.robot.leftDrive.setMode(RunMode.RUN_USING_ENCODER);
            this.robot.rightDrive.setMode(RunMode.RUN_USING_ENCODER);
        }
    }
}
