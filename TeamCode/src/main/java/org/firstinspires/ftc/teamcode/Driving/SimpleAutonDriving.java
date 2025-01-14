package org.firstinspires.ftc.teamcode.Driving;

public class SimpleAutonDriving {
    private Wheels wheels;

    public SimpleAutonDriving(Wheels wheels) {
        this.wheels = wheels;
    }

    public void horizontal (double power) { //right positive
        wheels.setEachPower(-power, power, power, -power); // -rf, +rb, lf, -lb)
    }

    public void vertical (double power) { //forward positive
        wheels.setEachPower(power,  power, power, power); //one side negative -rf, -rb
    }

    public void turn (double t) {
        wheels.setEachPower(t, t, -t, -t);
    }

}
