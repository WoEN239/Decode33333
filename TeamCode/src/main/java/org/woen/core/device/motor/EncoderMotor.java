package org.woen.core.device.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.trait.Encoder;
import org.woen.core.device.trait.VelocityController;
import org.woen.core.util.UnimplementedException;


public class EncoderMotor extends Motor implements VelocityController, Encoder {
    public EncoderMotor(String name) {
        super(name);
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        super.initialize(hardwareMap);
        device.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void resetEncoder() {
        device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        device.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public int getEncoderPosition() {
        return device.getCurrentPosition();
    }
}
