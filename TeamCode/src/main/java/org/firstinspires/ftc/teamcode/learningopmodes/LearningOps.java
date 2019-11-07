package org.firstinspires.ftc.teamcode.learningopmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(group = "Testing", name = "Learning")
public class LearningOps extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        this.telemetry.addData("Status", (Object) "Initialized");
        this.telemetry.update();
        this.leftDrive = (DcMotor) this.hardwareMap.get(DcMotor.class, "left_drive");
        this.rightDrive = (DcMotor) this.hardwareMap.get(DcMotor.class, "right_drive");
        this.leftDrive.setDirection(Direction.FORWARD);
        this.rightDrive.setDirection(Direction.REVERSE);
        waitForStart();
        this.runtime.reset();
        while (opModeIsActive()) {
            double rightJoy = (double) this.gamepad1.right_stick_y;
            this.leftDrive.setPower((double) this.gamepad1.left_stick_y);
            this.rightDrive.setPower(rightJoy);
        }
    }
}
