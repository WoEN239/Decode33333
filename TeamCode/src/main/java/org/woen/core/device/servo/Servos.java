package org.woen.core.device.servo;

import org.woen.core.device.Device;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import androidx.annotation.NonNull;

public class Servos extends Device{
    private Servo device = null;
    private double degree = 0.1;


    public Servos(String name) { super(name); }

    @Override
    public void initialize(@NonNull HardwareMap hardwareMap) {
        if(isInitialized()) return;
        device = hardwareMap.get(Servo.class, name);
    }

    @Override
    public boolean isInitialized() {
        return device != null;
    }

    public void kick() {
        if (isInitialized()) {
            device.setPosition(degree);
        }
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
