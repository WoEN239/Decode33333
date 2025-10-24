package org.woen.main;


import org.woen.main.gun.GunControl;
import org.woen.main.movement.Vehicles;


public final class Robot {
    public static Vehicles getVehiclesController() {
        return Vehicles.getInstance();
    }

    public static GunControl getGunController() {
        return GunControl.getInstance();
    }
}
