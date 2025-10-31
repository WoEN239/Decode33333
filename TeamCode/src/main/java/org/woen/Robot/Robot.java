package org.woen.Robot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.woen.Modules.DriveTrain.DriveTrain;
import org.woen.Modules.Odometery.Odometry;
import org.woen.Modules.Interface.RobotModule;
import org.woen.Pools.DevicePool.DevicePool;

import java.util.List;

public class Robot {


    public LinearOpMode linearOpMode;

    public Robot(LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
    }


    public DevicePool devicePool = new DevicePool(linearOpMode.hardwareMap);

    public Odometry odometry = new Odometry(this);
    public DriveTrain driveTrain = new DriveTrain(this);

    List<LynxModule> allHubs = linearOpMode.hardwareMap.getAll(LynxModule.class);

    private final RobotModule[] robotModule = new RobotModule[]{
            odometry,
            driveTrain
    };

    public void init(){

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);//if we watch electronics <= 1 switch to auto
        }

        for (RobotModule robotModule1 : robotModule)
            robotModule1.init();

    }

    public void updateRevBulkCache() {
        for (LynxModule module : allHubs)
            module.clearBulkCache();
    }

    public void update(){
        updateRevBulkCache();
        for(RobotModule robotModule1 : robotModule){
            robotModule1.update();
        }
    }

}
