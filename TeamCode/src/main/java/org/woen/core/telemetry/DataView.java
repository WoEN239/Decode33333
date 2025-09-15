package org.woen.core.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class DataView {
    private final Telemetry telemetry;
    private final Map<String, Object> dataMap = new HashMap<>();
    private final List<String> order = new ArrayList<>();


    public DataView(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void add(String name, Object value) {
        if (!dataMap.containsKey(name)) {
            order.add(name);
        }
        dataMap.put(name, value);
    }

    public void showAll() {
        String key;
        telemetry.clear();
        for (int i = 0; i < order.size(); i++) {
            key = order.get(i);
            dataMap.put(key, dataMap.get(key));
        }
        telemetry.update();
    }


    public void show(int number) {
        String key = order.get(number);
        telemetry.clear();
        telemetry.addData(key, dataMap.get(key));
        telemetry.update();
    }

    public void clear() {
        telemetry.clear();
    }
}
