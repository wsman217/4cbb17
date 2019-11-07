package org.firstinspires.ftc.teamcode.learningopmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Disabled
@TeleOp(group = "Concept", name = "TensorFlowLearning")
public class TensorFlowLearning extends LinearOpMode {
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String VUFORIA_KEY = "AYj5yyP/////AAABmSpHjpn7VUx4nTXc2kXznM+CHe0d/L9LVqJv9TxlKcTYH67NmU6ruAKE6DK4hsKzpjpHzHfSTkR6TCMGAMU+UIhUNjTilevk9yekdqd6DKls0R+VG++vtOMELRC0Gjvy0SRy4GCd6OJ3Eb1lopmjsv/Q2cXq8z+R64Oo5rTWW4Kpb0IT+AhlWdhkgXHhHxAbGXWHoDtZRNx0koOmRYLIeZZba3uvy8UGN8g/sqt8PH8+rMX4hJQv0CD/Z8lw7XGziwQFVID0WOvsUICti4UPp0sUPrws92mq43BNzoarbgbdBQTkOWM4LpzNsOpGbvwoXSggVvuwAr902lW4NJyqwneXnF7H67iL1v451feCKsxU";
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;

    public void runOpMode() {
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            this.telemetry.addData("Sorry!", (Object) "This device is not compatible with TFOD");
        }
        TFObjectDetector tFObjectDetector = this.tfod;
        if (tFObjectDetector != null) {
            tFObjectDetector.activate();
        }
        this.telemetry.addData(">", (Object) "Press Play to start op mode");
        this.telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                TFObjectDetector tFObjectDetector2 = this.tfod;
                if (tFObjectDetector2 != null) {
                    List<Recognition> updatedRecognitions = tFObjectDetector2.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        this.telemetry.addData("# Object Detected", (Object) Integer.valueOf(updatedRecognitions.size()));
                        for (Recognition recognition : updatedRecognitions) {
                            this.telemetry.addData(String.format("label (%d)", new Object[]{Integer.valueOf(0)}), (Object) recognition.getLabel());
                            String str = "%.03f , %.03f";
                            this.telemetry.addData(String.format("  left,top (%d)", new Object[]{Integer.valueOf(0)}), str, Float.valueOf(recognition.getLeft()), Float.valueOf(recognition.getTop()));
                            this.telemetry.addData(String.format("  right,bottom (%d)", new Object[]{Integer.valueOf(0)}), str, Float.valueOf(recognition.getRight()), Float.valueOf(recognition.getBottom()));
                        }
                        this.telemetry.update();
                    }
                }
            }
        }
        TFObjectDetector tFObjectDetector3 = this.tfod;
        if (tFObjectDetector3 != null) {
            tFObjectDetector3.shutdown();
        }
    }

    private void initVuforia() {
        Parameters parameters = new Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
