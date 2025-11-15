package org.firstinspires.ftc.teamcode.core.device.single;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;


// coding by Timofei
public class Gyro implements Initializable {
    private static Gyro instance;

    // 180 / pi
    public static final double RAD_TO_DEG = 57.295779513082320876798154814105170332405472466564;
    // pi / 180
    public static final double DEG_TO_RAD = 0.0174532925199432957692369076848861271344287188854;

    private IMU imu;
    private boolean initialized = false;
    private RevHubOrientationOnRobot.LogoFacingDirection logoDirection;
    private RevHubOrientationOnRobot.UsbFacingDirection usbDirection;

    private Gyro() {
        // default directions
        this.logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        this.usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.UP;
    }

    public static synchronized Gyro getInstance() {
        if (instance == null) {
            instance = new Gyro();
        }
        return instance;
    }

    public void initialize(HardwareMap hardwareMap,
                           RevHubOrientationOnRobot.LogoFacingDirection logoDirection,
                           RevHubOrientationOnRobot.UsbFacingDirection usbDirection) {
        if (initialized) {
            return;
        }
        this.logoDirection = logoDirection;
        this.usbDirection = usbDirection;

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(
                logoDirection, usbDirection);

        IMU.Parameters parameters = new IMU.Parameters(orientationOnRobot);
        imu.initialize(parameters);

        initialized = true;
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        initialize(hardwareMap, this.logoDirection, this.usbDirection);
    }

    @Override
    public boolean isInitialized() {
        return initialized && imu != null;
    }

    private void checkInitialized() {
        if (!isInitialized()) {
            throw new IllegalStateException("IMU is not initialized. Call initialize() first.");
        }
    }

    public double getYaw() {
        checkInitialized();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return -orientation.getYaw(AngleUnit.DEGREES);
    }

    public void resetYaw() {
        checkInitialized();
        imu.resetYaw();
    }

    public static double normalizeAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

    public static double getShortestPathToAngle(double currentAngle, double targetAngle) {
        double difference = targetAngle - currentAngle;
        return normalizeAngle(difference);
    }
}
