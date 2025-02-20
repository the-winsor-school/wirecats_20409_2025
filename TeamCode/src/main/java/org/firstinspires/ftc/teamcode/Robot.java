package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Driving.TeleOpDriving;
import org.firstinspires.ftc.teamcode.Driving.Wheels;
import org.firstinspires.ftc.teamcode.Sensors.OurColorSensor;
import org.firstinspires.ftc.teamcode.Sensors.OurDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.Lift.Claw;
import org.firstinspires.ftc.teamcode.Lift.TeleOpLift;


/**
 * TODO: Explain why the separation of TeleopRobot and AutonRobot were necessary. (Single-Responsibility Principle)
 * In this file we:
 * initalize all the sensors, motors, and libraries
 * the motors and sensors go here so we are only initializing them in one place in the whole repo
 * (to avoid mistakes and conflicts)
 * the libraries like IDriving and arm etc are intialized here so they are also only in one place
 * the libaries also ahve access to everything within the robot class like the motors if they are intialized in this way
 * when you create a new opMode you should only initlaize the robot class (by passing the opMode (by writing "this" in the parentheses)
 * you cannot access any of the sensors or motors outside of this class (because encapsulation and saefty)
 * you can only control things by using the libraries and the functions within them that are public
 *
 * solved issues of teleOp and Auto controls competeubg by using a new robot class and making TeleOpControls and AutoControls extend the robot class
 * this centralizes all device names and init data into one file instead of twoi
 */
public class Robot {

    /**
     * itializtion of all sensors and motors
     */
    //wheels
    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    //Arm Lift
    protected DcMotorEx scissorMotor;
    protected DcMotorEx wristMotor;
    protected AnalogInput wristPotentiometer;

    protected CRServo clawServo;

    //sensors
    protected DistanceSensor rightDist;
    protected DistanceSensor leftDist;
    protected DistanceSensor frontDist;

    protected ColorSensor rightColor;
    protected ColorSensor leftColor;


    //wheel is private but gets passed into sigmoid or teleOp
    //driving libraries to access the wheels
    protected Wheels wheels;

    private LinearOpMode opMode;

    /**
     * @param opMode pass by writing: new Robot(this);
     */
    public Robot(LinearOpMode opMode) {
        HardwareMap map = opMode.hardwareMap;
        this.opMode = opMode;

        //____ Wheels ____
        rf = map.tryGet(DcMotorEx.class, "rf");
        rb = map.tryGet(DcMotorEx.class, "rb");
        lf = map.tryGet(DcMotorEx.class, "lf");
        lb = map.tryGet(DcMotorEx.class, "lb");

        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);

        //____ Lift ____
        scissorMotor = map.tryGet(DcMotorEx.class, "lift");
        wristMotor = map.tryGet(DcMotorEx.class, "wrist");
        clawServo = map.tryGet(CRServo.class, "servo");
        wristPotentiometer = map.tryGet(AnalogInput.class, "wristAngle");

        wristMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        wristMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //____ Sensors ____
        rightDist = map.tryGet(DistanceSensor.class, "rightDist");
        frontDist = map.tryGet(DistanceSensor.class, "frontDist");
        leftDist = map.tryGet(DistanceSensor.class, "leftDist");
        rightColor = map.tryGet(ColorSensor.class, "rightColor");
        leftColor = map.tryGet(ColorSensor.class, "leftColor");

        //____ Other Objects ____
        wheels = new Wheels(rf, rb, lf, lb);
    }

    public void printWheelPowers() {
        opMode.telemetry.addData("rf: ", rf.getPower());
        opMode.telemetry.addData("lf: ", lf.getPower());
        opMode.telemetry.addData("rb: ", rb.getPower());
        opMode.telemetry.addData("lb: ", lb.getPower());
    }

    public void printWheelCurrentPosition() {
        opMode.telemetry.addData("rf: ", rf.getCurrentPosition());
        opMode.telemetry.addData("lf: ", lf.getCurrentPosition());
        opMode.telemetry.addData("rb: ", rb.getCurrentPosition());
        opMode.telemetry.addData("lb: ", lb.getCurrentPosition());
    }

    public void printWheelTargetPosition() {
        opMode.telemetry.addData("rf: ", rf.getTargetPosition());
        opMode.telemetry.addData("lf: ", lf.getTargetPosition());
        opMode.telemetry.addData("rb: ", rb.getTargetPosition());
        opMode.telemetry.addData("lb: ", lb.getTargetPosition());
    }


}
