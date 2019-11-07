package org.firstinspires.ftc.teamcode.tensorflow;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters;

public class InitVuforia {
    private static VuforiaLocalizer vuforia;

    public static VuforiaLocalizer getVuforia() {
        return vuforia;
    }

    public static void initVuforia(String VUFORIA_KEY) {
        Parameters parameters = new Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
}
