package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.hardware.Bot;
import org.firstinspires.ftc.teamcode.tensorflow.InitTfod;
import org.firstinspires.ftc.teamcode.tensorflow.InitVuforia;

import java.util.List;

@Autonomous(group = "Autonomous", name = "Move Skystone")
@Disabled
public class MoveSkystones extends LinearOpMode {
    private Bot bot;
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;

    public void runOpMode() {
        setUp();
        waitForStart();
        this.telemetry.addData("Op Mode has started ", true);
        this.telemetry.update();
        this.telemetry.setAutoClear(false);
        while (opModeIsActive()) {
            int counter = 1;
            TFObjectDetector tFObjectDetector = this.tfod;
            if (tFObjectDetector != null) {
                List<Recognition> updatedRecognitions = tFObjectDetector.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    int size = updatedRecognitions.size();
                    for (Recognition recognition : updatedRecognitions) {
                        Telemetry telemetry = this.telemetry;
                        telemetry.addData("Label # " + counter, recognition.getLabel());
                        this.telemetry.update();
                        counter++;
                    }
                }
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void setUp() {
        InitVuforia.initVuforia("AYj5yyP/////AAABmSpHjpn7VUx4nTXc2kXznM+CHe0d/L9LVqJv9TxlKcTYH67NmU6ruAKE6DK4hsKzpjpHzHfSTkR6TCMGAMU+UIhUNjTilevk9yekdqd6DKls0R+VG++vtOMELRC0Gjvy0SRy4GCd6OJ3Eb1lopmjsv/Q2cXq8z+R64Oo5rTWW4Kpb0IT+AhlWdhkgXHhHxAbGXWHoDtZRNx0koOmRYLIeZZba3uvy8UGN8g/sqt8PH8+rMX4hJQv0CD/Z8lw7XGziwQFVID0WOvsUICti4UPp0sUPrws92mq43BNzoarbgbdBQTkOWM4LpzNsOpGbvwoXSggVvuwAr902lW4NJyqwneXnF7H67iL1v451feCKsxU");
        this.vuforia = InitVuforia.getVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            InitTfod.initTFod(this.hardwareMap);
            this.tfod = InitTfod.getTfObjectDetector();
        } else {
            this.telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        if (tfod != null) {
            tfod.activate();
        }
        this.bot = new Bot().init(this.hardwareMap);
        this.telemetry.addData(">", "Press Play to start op mode");
        this.telemetry.update();
    }
}
