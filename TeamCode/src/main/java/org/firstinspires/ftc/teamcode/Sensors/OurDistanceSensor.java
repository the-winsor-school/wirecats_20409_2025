package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * In this file we:
 * define our own distance sensor class
 * although there already is a distance sensor class
 * using this format allows us to add our own functions like
 * returning true when the distance is grreater than a certain value
 * each color sensor will be initalized as an instance of the OurDistanceSensor object
 * so it will inheriet all the properites and functions in this class
 */public class OurDistanceSensor {

    private final DistanceSensor sensor;

    public OurDistanceSensor(DistanceSensor sensor) {
        this.sensor = sensor;
    }

    public boolean isDistanceGreater(double distance) {
        return sensor.getDistance(DistanceUnit.CM) > distance;
    }

    public boolean isDistanceLess(double distance) {
        return sensor.getDistance(DistanceUnit.CM) < distance;
    }

    public double getDistance() { return sensor.getDistance(DistanceUnit.CM); }
}

