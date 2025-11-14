package org.firstinspires.ftc.teamcode.core.device.servomotor;

import org.firstinspires.ftc.teamcode.core.device.Device;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import androidx.annotation.NonNull;

// coding by Matvey Ivanovv

public class Servomotor extends Device {
    private Servo device;
    private double currentDegree;
    private double targetDegree;
    private double x1, x2;
    private double vmax, amax;
    private double t1, t2, t3;
    private boolean parametersCalculated = false;
    private boolean motionInProgress = false;
    private ElapsedTime runtime = new ElapsedTime();
    private double sign;

    private double minDegree = 0;
    private double maxDegree = 180;
    private double minPulseWidth = 500;
    private double maxPulseWidth = 2500;
    public Servomotor(String name) {
        
        super(name);
        device = null;
        currentDegree = 0;
        targetDegree = 0;
    }

    @Override
    public void initialize(@NonNull HardwareMap hardwareMap) {
        if (isInitialized()) return;
        device = hardwareMap.get(Servo.class, name);
        runtime.reset();
    }

    @Override
    public boolean isInitialized() { return device != null; }

    public double getCurrentDegree() { return currentDegree; }

    public void setServoPosition(double degree) {
        device.setPosition(degree);
        currentDegree = degree;
    }

    public void setParameters(double startPos, double targetPos, double maxVelocity, double maxAcceleration) {
        this.currentDegree = limitDegree(startPos);
        this.targetDegree = limitDegree(targetPos);
        this.vmax = maxVelocity;
        this.amax = maxAcceleration;
        this.parametersCalculated = false;
        this.sign = Math.signum(targetDegree - currentDegree);
    }

    public void calculateParameters() {
        double distance = Math.abs(targetDegree - currentDegree);

        t1 = vmax / amax;
        t3 = vmax / amax;

        double accelDistance = 0.5 * amax * t1 * t1;
        double decelDistance = 0.5 * amax * t3 * t3;
        if (accelDistance + decelDistance <= distance) {
            double constVelocityDistance = distance - accelDistance - decelDistance;
            t2 = constVelocityDistance / vmax;
            x1 = currentDegree + sign * accelDistance;
            x2 = x1 + sign * constVelocityDistance;
        } else {
            t1 = Math.sqrt(distance / amax);
            t2 = 0;
            t3 = t1;
            x1 = currentDegree + sign * (0.5 * amax * t1 * t1);
            x2 = x1;
        }

        parametersCalculated = true;
    }

    public void startMovement() {
        if (!parametersCalculated) {
            calculateParameters();
        }
        runtime.reset();
        motionInProgress = true;
    }

    public void update() {
        if (!motionInProgress || !parametersCalculated) return;

        double elapsedTime = runtime.seconds();
        double position;

        if (elapsedTime < t1) {
            position = currentDegree + sign * (0.5 * amax * elapsedTime * elapsedTime);
        } else if (elapsedTime < t1 + t2) {
            double constVelocityTime = elapsedTime - t1;
            position = x1 + sign * (vmax * constVelocityTime);
        } else if (elapsedTime < t1 + t2 + t3) {
            double decelTime = elapsedTime - t1 - t2;
            double decelDistance = vmax * decelTime - 0.5 * amax * decelTime * decelTime;
            position = x2 + sign * decelDistance;
        } else {
            position = targetDegree;
            motionInProgress = false;
        }
        setServoPosition(position);
        if (elapsedTime >= t1 + t2 + t3) {
            motionInProgress = false;
            currentDegree = targetDegree;
        }
    }

    public void moveTo(double target, double speed, double acceleration) {
        setParameters(currentDegree, target, speed, acceleration);
        startMovement();
    }

    public boolean isMoving() { return motionInProgress; }

    public void stop() { motionInProgress = false; }
    private double degreesToMicroseconds(double degrees) {
        double normalized = (degrees - minDegree) / (maxDegree - minDegree);
        return minPulseWidth + normalized * (maxPulseWidth - minPulseWidth);
    }

    private double pulseWidthToNormalized(double pulseWidth) { return (pulseWidth - minPulseWidth) / (maxPulseWidth - minPulseWidth); }

    private double limitDegree(double degree) { return Math.max(minDegree, Math.min(maxDegree, degree)); }
    public double getTargetDegree() {
        return targetDegree;
    }

    public double getMaxVelocity() { return vmax; }

    public double getMaxAcceleration() { return amax; }


    public void setServoLimits(double minDegree, double maxDegree, double minPulseWidth, double maxPulseWidth) {
        this.minDegree = minDegree;
        this.maxDegree = maxDegree;
        this.minPulseWidth = minPulseWidth;
        this.maxPulseWidth = maxPulseWidth;
    }
}