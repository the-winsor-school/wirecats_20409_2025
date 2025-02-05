package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Driving.AutoDriving;
import org.firstinspires.ftc.teamcode.Driving.TeleOpDriving;
import org.firstinspires.ftc.teamcode.Driving.Wheels;
import org.firstinspires.ftc.teamcode.Sensors.OurColorSensor;
import org.firstinspires.ftc.teamcode.Sensors.OurDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.Lift.AutoLift;
import org.firstinspires.ftc.teamcode.Lift.Claw;
import org.firstinspires.ftc.teamcode.Lift.TeleOpLift;


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
    private DcMotorEx scissorMotor;
    private DcMotorEx wristMotor;
    private CRServo clawServo;

    //sensors
    private DistanceSensor rightDist;
    private DistanceSensor leftDist;
    private DistanceSensor frontDist;

    private ColorSensor rightColor;
    private ColorSensor leftColor;

    //sensor objects
    public OurDistanceSensor rightDistObject;
    public OurDistanceSensor frontDistObject;

    public OurDistanceSensor leftDistObject;
    public OurColorSensor rightColorObject;
    public OurColorSensor leftColorObject;

    //wheel is private but gets passed into sigmoid or teleOp
    //driving libraries to access the wheels
    private Wheels wheels;

    public AutoDriving autoDriving;
    public TeleOpDriving teleOpDriving;

    public AutoLift autoLift;
    public TeleOpLift teleOpLift;
    public Claw claw;

    private AnalogInput wristPotentiometer;

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
        wristPotentiometer = map.tryGet(AnalogInput.class, "wristAngle");
        scissorMotor = map.tryGet(DcMotorEx.class, "lift");
        wristMotor = map.tryGet(DcMotorEx.class, "wrist");
        clawServo = map.get(CRServo.class, "servo");

        wristMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //____ Arm ____
        scissorMotor = map.tryGet(DcMotorEx.class, "lift");
        wristMotor = map.tryGet(DcMotorEx.class, "wrist");
        clawServo = map.tryGet(CRServo.class, "servo");

        wristMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        wristMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

         //____ Sensors ____
        rightDist = map.tryGet(DistanceSensor.class, "rightDist");
        frontDist = map.tryGet(DistanceSensor.class, "frontDist");
        leftDist = map.tryGet(DistanceSensor.class, "leftDist");
        rightColor = map.tryGet(ColorSensor.class, "rightColor");
        leftColor = map.tryGet(ColorSensor.class, "leftColor");

        //____ Sensor Objects _____
        rightDistObject = new OurDistanceSensor(rightDist);
        frontDistObject = new OurDistanceSensor(frontDist);
        leftDistObject = new OurDistanceSensor(leftDist);
        rightColorObject = new OurColorSensor(rightColor);
        leftColorObject = new OurColorSensor(leftColor);
        
        //____ Other Objects ____
        //driving
        wheels = new Wheels(rf, rb, lf, lb);
        autoDriving = new AutoDriving(wheels);
        teleOpDriving = new TeleOpDriving(wheels);

        //arm
        autoLift = new AutoLift((DcMotorEx) scissorMotor, (DcMotorEx) wristMotor, wristPotentiometer);
        teleOpLift = new TeleOpLift((DcMotorEx) scissorMotor, (DcMotorEx) wristMotor);
        claw = new Claw(clawServo);
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
