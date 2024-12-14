package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.HashMap;

public class FullArmLift {
    public GenericMotor lift;
    public GenericMotor wrist;
    public Claw claw;

    //arm location encoder values
    HashMap<LIFT_POSITION, Integer> liftLocations = new HashMap<LIFT_POSITION, Integer>();
    HashMap<LIFT_POSITION, Integer> wristLocations = new HashMap<LIFT_POSITION, Integer>();

    public FullArmLift(DcMotorEx LiftMotor, DcMotorEx WristMotor, CRServo clawServo){
        //both values to be tested
        lift = new GenericMotor(LiftMotor, 0.5, 200);
        wrist = new GenericMotor(WristMotor, 0.3, 100);
        claw = new Claw(clawServo);

        //Initalization of motor encoder positions
        //Drawbridge
        //TODO get these values
        liftLocations.put(LIFT_POSITION.RESET, 0);
        liftLocations.put(LIFT_POSITION.LOW_BASKET, 0);
        liftLocations.put(LIFT_POSITION.HIGH_BASKET, 0);

        //Cascade
        //TODO get these values
        wristLocations.put(LIFT_POSITION.RESET, 0);
        wristLocations.put(LIFT_POSITION.LOW_BASKET, 0);
        wristLocations.put(LIFT_POSITION.HIGH_BASKET, 0);
    }

    public void moveLiftToPosition (LIFT_POSITION pos){
        if(pos == LIFT_POSITION.RESET){
            lift.runToPosition(liftLocations.get(LIFT_POSITION.RESET));
            wrist.runToPosition(wristLocations.get(LIFT_POSITION.RESET));
        }
        else if(pos == LIFT_POSITION.LOW_BASKET){
            lift.runToPosition(liftLocations.get(LIFT_POSITION.LOW_BASKET));
            wrist.runToPosition(wristLocations.get(LIFT_POSITION.LOW_BASKET));
        }
        else if(pos == LIFT_POSITION.HIGH_BASKET){
            lift.runToPosition(liftLocations.get(LIFT_POSITION.HIGH_BASKET));
            wrist.runToPosition(wristLocations.get(LIFT_POSITION.HIGH_BASKET));
        }
        //add one more for PICKINGUP if RESET does not work
    }

    public void joystickControlLift(float input) {
        lift.setMotorPower(input);
    }

    public void joystickControlWrist(float input) {
        wrist.setMotorPower(input);
    }

    public enum LIFT_POSITION {
        RESET,
        LOW_BASKET,
        HIGH_BASKET,
    }
}
