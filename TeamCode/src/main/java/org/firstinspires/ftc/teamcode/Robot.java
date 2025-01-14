package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
<<<<<<< Updated upstream
import com.qualcomm.robotcore.hardware.DcMotor;
=======
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Driving.FullDrive;
import org.firstinspires.ftc.teamcode.Sensors.OurColorSensor;
>>>>>>> Stashed changes
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
    private DcMotor rf;
    private DcMotor rb;
    private DcMotor lf;
    private DcMotor lb;
    private CRServo clawServo;

    //Arm Lift
    private DcMotor liftMotor;

<<<<<<< Updated upstream
    public StrafeDrive driving;
=======
    //sensor objects
    public OurDistanceSensor rightDistObject;
    public OurDistanceSensor leftDistObject;
    public OurColorSensor rightColorObject;
    public OurColorSensor leftColorObject;

    //objects
    public FullDrive driving;
>>>>>>> Stashed changes
    public FullArmLift lift;
    private LinearOpMode opMode;

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

        liftMotor = map.tryGet(DcMotorEx.class, "liftMotor");

<<<<<<< Updated upstream
        driving = new StrafeDrive(rf, rb, lf, lb);

        //(DcMotorEx) casts the lift motor to that class
        lift = new FullArmLift((DcMotorEx) liftMotor);

        clawServo = map.tryGet(CRServo.class, "servo");
=======
         //____ Sensors ____
        rightDist = map.tryGet(DistanceSensor.class, "rightDist");
        leftDist = map.tryGet(DistanceSensor.class, "leftDist");
        rightColor = map.tryGet(ColorSensor.class, "rightColor");
        leftColor = map.tryGet(ColorSensor.class, "leftColor");

        //____ Sensor Objects _____
        rightDistObject = new OurDistanceSensor(rightDist);
        leftDistObject = new OurDistanceSensor(leftDist);
        rightColorObject = new OurColorSensor(rightColor);
        leftColorObject = new OurColorSensor(leftColor);
        
        //____ Other Objects ____
        lift = new FullArmLift(liftMotor, wristMotor, clawServo);
        driving = new FullDrive(rf, rb, lf, lb);
>>>>>>> Stashed changes
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


}
