package org.woen.main.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.servomotor.Servomotor;
import org.woen.core.device.trait.Initializable;
import org.woen.core.device.motor.Motor;

public class TransferBall implements Initializable {
    private static final org.woen.main.modules.TransferBall INSTANCE = new org.woen.main.modules.TransferBall();
    private final Motor motorFlow;
    private final Motor motorBrush;
    private final Servomotor servoToGun;
    private double velocityFlow = 0.7;
    private static double velocityBrush = 0.9;
    private double degreeServo = 0.1;

    public TransferBall() {
        motorFlow = new Motor("motor_flow");
        motorBrush = new Motor("motor_brush");
        servoToGun = new Servomotor("servo_to_gun");
    }

    public static org.woen.main.modules.TransferBall getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        motorFlow.initialize(hardwareMap);
        motorBrush.initialize(hardwareMap);
        servoToGun.initialize(hardwareMap);
        motorBrush.setDirection(motorBrush.getDirection().inverted());
    }

    @Override
    public boolean isInitialized() {
        return motorFlow.isInitialized() && motorBrush.isInitialized() && servoToGun.isInitialized();
    }

    public void setVelocityFlow(double velocity) { velocityFlow = velocity; }

    public double getVelocityFlow() { return velocityFlow; }

    public void setVelocityBrush(double velocity) { velocityBrush = velocity; }

    public double getVelocityBrush() { return velocityBrush; }

    public void setDegreeServo(double degree) {
        degreeServo = degree;
        servoToGun.setServoPosition(degreeServo);
    }

    public double getDegreeServo() { return degreeServo; }

    public void startBrush() { motorBrush.setPower(velocityBrush); }

    public void stopBrush() { motorBrush.stopMotor(); }

    public void startFlow() { motorFlow.setPower(velocityFlow); }

    public void stopFlow() { motorFlow.stopMotor(); }

}
