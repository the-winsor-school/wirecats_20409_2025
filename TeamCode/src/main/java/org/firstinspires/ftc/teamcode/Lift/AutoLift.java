package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.HashMap;

public class AutoLift {
    public EncoderMotorObject lift;
    public EncoderMotorObject wrist;

    //arm location encoder values
    HashMap<LIFT_POSITION, Integer> liftLocations = new HashMap<LIFT_POSITION, Integer>();
    HashMap<LIFT_POSITION, Integer> wristLocations = new HashMap<LIFT_POSITION, Integer>();

    public AutoLift(DcMotorEx LiftMotor, DcMotorEx WristMotor){

        lift = new EncoderMotorObject(LiftMotor, 1, 100);
        wrist = new EncoderMotorObject(WristMotor, 0.3, 100);


        //Initalization of motor encoder position
        //TODO get these values
        liftLocations.put(LIFT_POSITION.RESET, 0);
        liftLocations.put(LIFT_POSITION.LOW_BASKET, 0);
        liftLocations.put(LIFT_POSITION.HIGH_BASKET, 0);

        //TODO get these values
        wristLocations.put(LIFT_POSITION.RESET, 0);
        wristLocations.put(LIFT_POSITION.LOW_BASKET, 0);
        wristLocations.put(LIFT_POSITION.HIGH_BASKET, 0);
    }

    public void moveLiftToPosition (LIFT_POSITION pos){
        if(pos == LIFT_POSITION.RESET){
            //lift.setTargetPosition(liftLocations.get(LIFT_POSITION.RESET));
            //wrist.runToPosition(wristLocations.get(LIFT_POSITION.RESET));
        }
        else if(pos == LIFT_POSITION.LOW_BASKET){
            //lift.runToPosition(liftLocations.get(LIFT_POSITION.LOW_BASKET));
            //wrist.runToPosition(wristLocations.get(LIFT_POSITION.LOW_BASKET));
        }
        else if(pos == LIFT_POSITION.HIGH_BASKET){
            //lift.runToPosition(liftLocations.get(LIFT_POSITION.HIGH_BASKET));
            //wrist.runToPosition(wristLocations.get(LIFT_POSITION.HIGH_BASKET));
        }
        //add one more for PICKINGUP if RESET does not work
    }

    public void resetEncoders() {
        lift.resetEncoders();
        wrist.resetEncoders();
    }

    public enum LIFT_POSITION {
        RESET,
        LOW_BASKET,
        HIGH_BASKET,
    }
}
