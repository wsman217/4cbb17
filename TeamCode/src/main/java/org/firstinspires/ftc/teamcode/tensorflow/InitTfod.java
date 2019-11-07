package org.firstinspires.ftc.teamcode.tensorflow;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class InitTfod {
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static TFObjectDetector tfObjectDetector;

    public static TFObjectDetector getTfObjectDetector() {
        return tfObjectDetector;
    }

    public static void initTFod(HardwareMap map) {
        int tfodMonitorViewId = map.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", map.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfObjectDetector = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, InitVuforia.getVuforia());
        tfObjectDetector.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
