//package org.firstinspires.ftc.teamcode.main.gun;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.teamcode.core.device.servomotor.Servomotor;
//import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;
//import org.firstinspires.ftc.teamcode.core.device.motor.Motor;
//
//
//@Config
//public final class GunControl implements Initializable {
//    private static final GunControl INSTANCE = new GunControl();
//    private final Motor motorRight;
//    private final Motor motorLeft;
//    private final Servomotor servo;
//    private static double velocity = 0.6;
//    private static double degreeTower = 0;
//    public GunControl() {
//        motorLeft = new Motor("gun_motor_left");
//        motorRight = new Motor("gun_motor_right");
//        servo = new Servomotor("servo_turn_tower");
//    }
//
//    public static GunControl getInstance() {
//        return INSTANCE;
//    }
//
//    @Override
//    public void initialize(HardwareMap hardwareMap) {
//        motorLeft.initialize(hardwareMap);
//        motorRight.initialize(hardwareMap);
//        servo.initialize(hardwareMap);
//        motorLeft.setDirection(motorLeft.getDirection().inverted());
//    }
//
//    @Override
//    public boolean isInitialized() {
//        return motorLeft.isInitialized() && motorRight.isInitialized() && servo.isInitialized();
//    }
//
//    public void invertDirection(Motor motor) {
//        motor.setDirection(motor.getDirection().inverted());
//    }
//
//    public void setVelocity(double velocity) {
//        GunControl.velocity = velocity;
//        motorLeft.setPower(velocity);
//        motorRight.setPower(velocity);
//    }
//
//    public double getVelocity() { return velocity; }
//
//    public void startShot() {
//        motorRight.setPower(motorLeft.getVelocity());
////        motorLeft.setPower(motorLeft.getVelocity());
//    }
//
//    public void stopShot() {
//        motorRight.stopMotor();
////        motorLeft.stopMotor();
//    }
//
//    public void setTowerDegree(double degree) {
//        this.degreeTower = degree;
//        servo.setServoPosition(degree * 3);
//    }
//
//    public double getTowerDegree() {
//        return degreeTower;
//    }
//}
