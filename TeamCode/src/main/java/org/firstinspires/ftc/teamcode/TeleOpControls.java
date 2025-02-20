package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Driving.TeleOpDriving;
import org.firstinspires.ftc.teamcode.Sensors.OurColorSensor;
import org.firstinspires.ftc.teamcode.Sensors.OurDistanceSensor;

import org.firstinspires.ftc.teamcode.Lift.Claw;
import org.firstinspires.ftc.teamcode.Lift.TeleOpLift;


/**
 * TODO: Explain why the separation of TeleopRobot and AutonRobot were necessary. (Single-Responsibility Principle)
 * TODO: Brainstorm how we can de-dupilcate code between these two classes while preserving the necessary separation.
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
public class TeleOpControls extends Robot {

    //sensor objects
    public OurDistanceSensor rightDistObject;
    public OurDistanceSensor frontDistObject;

    public OurDistanceSensor leftDistObject;
    public OurColorSensor rightColorObject;
    public OurColorSensor leftColorObject;

    public TeleOpDriving teleOpDriving;

    public TeleOpLift teleOpLift;
    public Claw claw;


    /**
     * @param opMode pass by writing: new Robot(this);
     */
    public TeleOpControls(LinearOpMode opMode) {
        super(opMode);

        //____ Sensor Objects _____
        rightDistObject = new OurDistanceSensor(rightDist);
        frontDistObject = new OurDistanceSensor(frontDist);
        leftDistObject = new OurDistanceSensor(leftDist);
        rightColorObject = new OurColorSensor(rightColor);
        leftColorObject = new OurColorSensor(leftColor);
        
        //____ Other Objects ____
        teleOpDriving = new TeleOpDriving(wheels);
        teleOpLift = new TeleOpLift(scissorMotor, wristMotor);
        claw = new Claw(clawServo);
    }

}
