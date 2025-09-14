package org.woen.core.device.sensors;

import org.woen.core.device.Device;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

import android.graphics.Color;
import androidx.annotation.NonNull;

public class SensorColor extends Device{
    private ColorSensor device = null;


    public SensorColor(String name) {
        super(name);
    }

    @Override
    public void initialize(@NonNull HardwareMap hardwareMap) {
        if(isInitialized()) return;

        device = hardwareMap.get(ColorSensor.class, name);
        initialized = true;
    }

    public int getRed() {return device.red();}

    public int getGreen() {return device.green();}

    public int getBlue() {return device.blue();}

    public float getHue() {
        float[] hsv = new float[3];
        int red = getRed();
        int green = getGreen();
        int blue = getBlue();
        Color.RGBToHSV(red, green, blue, hsv);
        float hue = hsv[0];
        return hue;
    }

    public float[] getHSV() {
        float[] hsv = new float[3];
        int red = getRed();
        int green = getGreen();
        int blue = getBlue();
        Color.RGBToHSV(red, green, blue, hsv);
        return hsv;
    }

}
