package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.hardwarev2.Bot;

@Autonomous(group = "Autonomous", name = "Move")
public class Move extends LinearOpMode {

    private static final double COUNTS_PER_MOTOR_REV = 28d;
    private static final double GEAR_RATIO = 19.2;
    private static final double WHEEL_DIAMETER_MM = 96d;
    private static final double COUNTS_PER_MM = (COUNTS_PER_MOTOR_REV * GEAR_RATIO) / (WHEEL_DIAMETER_MM * 3.1415);

    private ElapsedTime runtime = new ElapsedTime();

    private Bot bot;

    public void runOpMode() {
        this.bot = new Bot().init(this.hardwareMap, this);
        this.telemetry.addData(">", "Press Play to start op mode");
        this.telemetry.update();

        /*this.bot.getLeftDrive().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.bot.getRightFront().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.bot.getLeftDrive().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.bot.getRightFront().setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/

        waitForStart();

        encoderDrive(1d, 2000, 2000, 30);
        encoderDrive(.2d, 1000, 2000, 30);
    }

    private void encoderDrive(double speed, double leftMM, double rightMM, double timeoutS) {
        if (opModeIsActive()) {
            /*int newLeftTarget = this.bot.getLeftDrive().getCurrentPosition() + ((int) (leftMM * COUNTS_PER_MM));
            int newRightTarget = this.bot.getRightFront().getCurrentPosition() + ((int) (COUNTS_PER_MM * rightMM));
            this.bot.getLeftDrive().setTargetPosition(newLeftTarget);
            this.bot.getRightFront().setTargetPosition(newRightTarget);
            this.bot.getLeftDrive().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.bot.getRightFront().setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.runtime.reset();
            this.bot.getLeftDrive().setPower(Math.abs(speed));
            this.bot.getRightFront().setPower(Math.abs(speed));
            while (opModeIsActive() && this.runtime.seconds() < timeoutS && this.bot.getLeftDrive().isBusy() && this.bot.getRightFront().isBusy()) {
                this.telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                this.telemetry.addData("Path2", "Running at %7d :%7d", this.bot.getLeftDrive().getCurrentPosition(), this.bot.getRightFront().getCurrentPosition());
                this.telemetry.update();
            }
            this.bot.getLeftDrive().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.bot.getRightFront().setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
        }
    }
}
