package org.firstinspires.ftc.teamcode.main.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.servomotor.Servomotor;
import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;
import org.firstinspires.ftc.teamcode.core.device.motor.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
public class TransferBall implements Initializable {
    private static final org.firstinspires.ftc.teamcode.main.modules.TransferBall INSTANCE = new org.firstinspires.ftc.teamcode.main.modules.TransferBall();
    private final Motor motorFlow;
    private final Motor motorBrush;
    private final Servomotor servoToGun;
    public static double velocityFlow = 0.9;
    public static double velocityBrush = 1.0;
    public static double degreeServo = 0.5;

    public TransferBall() {
        motorFlow = new Motor("motor_flow");
        motorBrush = new Motor("motor_brush");
        servoToGun = new Servomotor("servo_to_gun");
    }

    public static org.firstinspires.ftc.teamcode.main.modules.TransferBall getInstance() { return INSTANCE; }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        motorFlow.initialize(hardwareMap);
        motorBrush.initialize(hardwareMap);
        servoToGun.initialize(hardwareMap);
//        motorBrush.setDirection(motorBrush.getDirection().inverted());
    }

    @Override
    public boolean isInitialized() { return motorFlow.isInitialized() && servoToGun.isInitialized(); }

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

    public void stopBrush() { motorBrush.setPower(0); }

    public void startFlow() { motorFlow.setPower(velocityFlow); }

    public void stopFlow() { motorFlow.setPower(0); }

}
