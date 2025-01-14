package org.firstinspires.ftc.teamcode.Driving;

public class SigmoidDriving {

    double speed = 0.5;

    public void joystickDrive (float X, float Y, float T) {
        //threshold for values (bc our controllers are old and bad)
        //these are condensed if statements
        float x = (Math.abs(X) < 0.1f) ? 0 : X;
        float y = (Math.abs(Y) < 0.1f) ? 0 : Y;
        float t = (Math.abs(T) < 0.1f) ? 0 : T;

        //explanation in drive and slack
        rf.setPower((y - x - t) * speed);
        rb.setPower((y + x - t) * speed);
        lf.setPower((y + x + t) * speed);
        lb.setPower((y - x + t) * speed);
    }
}
