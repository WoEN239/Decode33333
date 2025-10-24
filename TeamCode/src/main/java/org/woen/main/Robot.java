package org.woen.main;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.woen.core.device.trait.Initializable;
import org.woen.main.gun.GunControl;
import org.woen.main.movement.Vehicles;


public final class Robot implements Initializable {
    private static final Robot INSTANCE = new Robot();

    boolean initialized;


    private Robot() {
        initialized = false;
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
