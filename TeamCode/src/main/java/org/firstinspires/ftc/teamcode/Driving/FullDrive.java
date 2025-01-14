package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.hardware.DcMotor;
<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/StrafeDrive.java
=======
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.DcMotorEx;
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Driving/FullDrive.java

public class FullDrive {

    private DcMotor rf;
    private DcMotor rb;
    private DcMotor lf;
    private DcMotor lb;

    private double speed = 0.1;

<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/StrafeDrive.java
    public StrafeDrive(DcMotor rf, DcMotor rb, DcMotor lf, DcMotor lb) {
=======
    //encoder ticks (given from getCurrentPosition) conversion to revolutions of motor
    //value if hardware and can be looked up on REV website
    private double ticksPerRevolution = 28;

    //gear boxes on wheels (should be same for all wheels)
    private int gearReduction = 5*4;

    //circumference of wheels
    private double wheelCircumference = 7.5 * Math.PI;

    //calculated by values above
    //cm/ticks
    public double cmPerTick = (1/wheelCircumference) * gearReduction * ticksPerRevolution;

    //in ticks
    private int tolerance = 10;
    //test for this value after any major changes to the robot
    //limit is found when robot start to slip/skid when acceleration
    private double maxAcceleration = 20; //measure in some unit

    //calculate horizontal component of sigmoid function (derivation proves this)
    private double horizontalStretchSigmoid;

    //horizontal shift for sigmoid so that left side lines up with x=0
    //makes point (0, 0.1) on the function
    private double horizontalShiftSigmoid;

    public FullDrive(DcMotorEx rf, DcMotorEx rb, DcMotorEx lf, DcMotorEx lb) {
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Driving/FullDrive.java
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb =lb;
    }


    public void joystickDrive (float X, float Y, float T) {
        //threshold for values (bc our controllers are old and bad)
        //these are condensed if statements
        float x = (Math.abs(X) < 0.1f) ? 0 : X;
        float y = (Math.abs(Y) < 0.1f) ? 0 : Y;
        float t = (Math.abs(T) < 0.1f) ? 0 : T;

        //explanation in drive and slack
        rf.setPower((y - x - t));
        rb.setPower((y + x - t));
        lf.setPower((y + x + t));
        lb.setPower((y - x + t));
    }

    public void turn (double t) {
        setEachPower(t, t, -t, -t);
    }

    public void horizontal (double power) { //right positive
        setEachPower(-power, power, power, -power); // -rf, +rb, lf, -lb)
    }

    public void vertical (double power) { //forward positive
        setEachPower(power,  power, power, power); //one side negative -rf, -rb
    }

    public void stop () {
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void setEachPower (double rfp, double rbp, double lfp, double lbp) {
        rf.setPower(rfp * speed);
        rb.setPower(rbp * speed);
        lf.setPower(lfp * speed);
        lb.setPower(lbp * speed);
    }

    public void adjustSpeed(double x) {
        speed = speed + x;
    }

    public double getSpeed() { return speed; }

}
