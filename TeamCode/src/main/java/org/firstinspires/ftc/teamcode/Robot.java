package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Sensors.OurColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.ArmLift.FullArmLift;


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
    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    //Arm Lift
    private DcMotorEx liftMotor;
    private DcMotorEx wristMotor;
    private CRServo clawServo;

    //objects
    public StrafeDrive driving;
    public FullArmLift lift;

    private LinearOpMode opMode;

    /**
     * @param opMode pass by writing: new Robot(this);
     */
    public Robot(LinearOpMode opMode) {
        HardwareMap map = opMode.hardwareMap;
        this.opMode = opMode;

        //wheels
        rf = map.tryGet(DcMotorEx.class, "rf");
        rb = map.tryGet(DcMotorEx.class, "rb");
        lf = map.tryGet(DcMotorEx.class, "lf");
        lb = map.tryGet(DcMotorEx.class, "lb");

        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);

        liftMotor = map.tryGet(DcMotorEx.class, "liftMotor");
        wristMotor = map.tryGet(DcMotorEx.class, "wristMotor");

        wristMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        clawServo = map.get(CRServo.class, "servo");

        lb.setDirection(DcMotorSimple.Direction.REVERSE);

        driving = new StrafeDrive(rf, rb, lf, lb);

        //lift = new FullArmLift(liftMotor, wristMotor, clawServo);
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

    public enum Direction {
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }


}
