package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * In this file we:
 * initalize all the sensors, motors, and libraries
 * the motors and sensors go here so we are only initializing them in one place in the whole repo
 * (to avoid mistakes and conflicts)
 * the libraries like IDriving and arm etc are intialized here so they are also only in one place
 * the libaries also ahve access to everything within the robot class like the motors if they are intialized in this way
 * when you create a new opMode you should only initlaize the robot class (by passing the opMode (by writing "this" in the parentheses)
 * you cannot access any of the sensors or motors outside of this class (because encapsulation and saefty)
 * you can only control things by using the libraries and the functions within them that are public
 */
public class Robot {

    /**
     * itializtion of all sensors and motors
     */
    //wheels
    //rf, rb, lf, lb
    private DcMotor rf;
    private DcMotor rb;
    private DcMotor lf;
    private DcMotor lb;

    public StrafeDrive driving;

    private LinearOpMode opMode;

    private ColorSensor color;

    /**
     * @param opMode pass by writing: new Robot(this);
     */
    public Robot(LinearOpMode opMode) {
        HardwareMap map = opMode.hardwareMap;
        this.opMode = opMode;

        //wheels
        rf = map.tryGet(DcMotor.class, "rf");
        rb = map.tryGet(DcMotor.class, "rb");
        lf = map.tryGet(DcMotor.class, "lf");
        lb = map.tryGet(DcMotor.class, "lb");

        lb.setDirection(DcMotorSimple.Direction.REVERSE);

        driving = new StrafeDrive(rf, rb, lf, lb);

        color = map.get(ColorSensor.class,"color");
    }

    public void printWheelPowers() {
        opMode.telemetry.addData("rf: ", rf.getPower());
        opMode.telemetry.addData("lf: ", lf.getPower());
        opMode.telemetry.addData("rb: ", rb.getPower());
        opMode.telemetry.addData("lb: ", lb.getPower());

    }

    public enum Direction {
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }

    public boolean checkBlueTape() {
        if(color.blue() > 3000)
            return true;
        return false;
    }

    public boolean checkRedTape() {
        if(color.red() > 2000)
            return true;
        return false;
    }

    public boolean checkWhiteTape() {
        if(color.red() > 2000 && color.blue() > 3000 && color.green() > 3000)
            return true;
        return false;
    }

    public boolean checkEndTape() {
        if(checkBlueTape() || checkRedTape())
            return true;
        return false;
    }

    public boolean checkANYTape() {
        if (checkEndTape() || checkWhiteTape())
            return true;
        return false;
    }

    public void checkColorValues() {
        opMode.telemetry.addData("red", color.red());
        opMode.telemetry.addData("blue", color.blue());
        opMode.telemetry.addData("green", color.green());
        opMode.telemetry.update();
    }

}
