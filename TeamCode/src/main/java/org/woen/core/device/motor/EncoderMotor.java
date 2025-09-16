package org.woen.core.device.motor;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.trait.Encoder;
import org.woen.core.device.trait.VelocityControl;
import org.woen.core.util.NotImplementedException;


public class EncoderMotor extends Motor implements VelocityControl, Encoder {
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
    public void setVelocityControlMode(ControlMode mode) throws NotImplementedException {
        if (!isVelocityControlModeSupported(mode)) {
            throw new NotImplementedException();
        }
        velocityControlMode = mode;
    }

    @Override
    public void setVelocity(double velocity) throws NotImplementedException {
        if (super.isVelocityControlModeSupported(velocityControlMode)) {
            super.setVelocity(velocity);
            return;
        }

        //! TODO: implement velocity control
        throw new NotImplementedException();
    }

    @Override
    public boolean isVelocityControlModeSupported(ControlMode mode) {
        switch (mode) {
            case RAW:
            case TIMER:
            case AMPERAGE:
            case OWN_ENCODER:
            case THIRD_PARTY_ENCODER:
                return true;
        }

        return false;
    }
}
