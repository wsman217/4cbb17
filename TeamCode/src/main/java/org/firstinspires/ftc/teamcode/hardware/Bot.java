package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Bot {
    private HardwareMap hardwareMap = null;
    private ElapsedTime period = new ElapsedTime();

    public HardwareMap getHardwareMap() {
        return this.hardwareMap;
    }

    public ElapsedTime getPeriod() {
        return this.period;
    }

    public Bot init(HardwareMap map) {
        this.hardwareMap = map;
        return this;
    }
}
