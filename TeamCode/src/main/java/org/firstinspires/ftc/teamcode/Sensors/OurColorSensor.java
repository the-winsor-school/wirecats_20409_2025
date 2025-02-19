package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class OurColorSensor {
    public ColorSensor sensor;

    private final int redTapeThreshold = 5000;
    private final int blueTapeThreshold = 5000;
    private final int whiteTapeThreshold = 5000;

    public OurColorSensor(ColorSensor sensor) {
        this.sensor = sensor;
    }

    public boolean redTape(){
        return sensor.red() > redTapeThreshold;
    }
    public boolean blueTape(){
        return sensor.blue() > blueTapeThreshold;
    }
    public boolean whiteTape(){
        return (sensor.green() > whiteTapeThreshold) && (sensor.red() > whiteTapeThreshold);
    }

    public boolean redOrBlueTape() { return redTape() ||blueTape(); }
    public boolean anyTape() { return redOrBlueTape() || whiteTape(); }


    //returning values from the color sensor
    public int getRed() { return sensor.red(); }
    public int getGreen() { return sensor.green(); }
    public int getBlue() { return sensor.blue(); }

    public String getColors() {
        return "red:" + getRed() + "\n" +
                "green:" + getGreen() + "\n" +
                "blue:" + getBlue();
    }

}