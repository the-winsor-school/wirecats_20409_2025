package org.firstinspires.ftc.teamcode.Driving;


public class TeleOpDriving {
    private Wheels wheels;

    private double speed = 0.5;

    public TeleOpDriving (Wheels wheels) {
        this.wheels = wheels;
    }

    public void joystickDrive (float X, float Y, float T) {
        //threshold for values (bc our controllers are old and bad)
        //these are condensed if statements
        float x = (Math.abs(X) < 0.1f) ? 0 : X;
        float y = (Math.abs(Y) < 0.1f) ? 0 : Y;
        float t = (Math.abs(T) < 0.1f) ? 0 : T;

        //explanation in drive and slack
        wheels.setEachPower(
                ((y - x - t) * speed), //rf
                ((y + x - t) * speed), //rb
                ((y + x + t) * speed), //lf
                ((y - x + t) * speed));//lb
    }

    public double getSpeed() { return speed; }

    public void setSpeed(double newSpeed) { speed = newSpeed; }

}
