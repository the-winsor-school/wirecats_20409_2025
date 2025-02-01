package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class OurColorSensor {
    public ColorSensor sensor;

    private int redTapeThreshold;
    private int blueTapeThreshold;
    private int whiteTapeThreshold;

    public OurColorSensor(ColorSensor sensor) {
        this.sensor = sensor;
        redTapeThreshold = 2500;
        blueTapeThreshold = 2500;
        whiteTapeThreshold = 500;
    }

    public boolean redTape(){
        return sensor.red() > redTapeThreshold;
    }
    public boolean blueTape(){
        return sensor.blue() > redTapeThreshold;
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