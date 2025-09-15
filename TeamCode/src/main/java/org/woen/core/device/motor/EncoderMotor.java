package org.woen.core.device.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.trait.VelocityControl;


public class EncoderMotor extends Motor implements VelocityControl {
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

    @Override
    public double getVelocity() {
        return getPower();
    }

    @Override
    public void setVelocity(double velocity) throws UnsupportedOperationException {
        //! TODO: implement velocity control
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
