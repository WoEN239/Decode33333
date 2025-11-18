package org.firstinspires.ftc.teamcode.main;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.device.trait.Initializable;
import org.firstinspires.ftc.teamcode.main.modules.gun.GunControl;
import org.firstinspires.ftc.teamcode.main.movement.Vehicles;


/*
 * Main-wide Robot singleton object
 */
public final class Robot implements Initializable {
    private static final Robot INSTANCE = new Robot();

    boolean initialized;


    private Robot() {
        initialized = false;
    }

    public static Robot getInstance() {
        return INSTANCE;
    }

    public Vehicles getVehiclesController() {
        return Vehicles.getInstance();
    }

    public GunControl getGunController() {
        return GunControl.getInstance();
    }

    @Override
    public void initialize(HardwareMap hardwareMap) {
        if (isInitialized()) return;

        getVehiclesController().initialize(hardwareMap);
        getGunController().initialize(hardwareMap);

        initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
