package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;

import java.util.HashMap;

public class AutoLift {
    public EncoderMotorObject lift;
    public EncoderMotorObject wrist;

    private AnalogInput wristAngle;

    //arm location encoder values
    HashMap<LiftPosition, Integer> liftLocations = new HashMap<LiftPosition, Integer>();

    public AutoLift(DcMotorEx LiftMotor, DcMotorEx WristMotor, AnalogInput wristAngle){

        lift = new EncoderMotorObject(LiftMotor, 1, 100);
        wrist = new (WristMotor, 0.3, 100);
        this.wristAngle = wristAngle;


        //Initalization of motor encoder position
        //TODO get these values
        liftLocations.put(LiftPosition.RESET, 0);
        liftLocations.put(LiftPosition.LOW_BASKET, 0);
        liftLocations.put(LiftPosition.HIGH_BASKET, 0);
    }

    public void moveLiftToPosition (LiftPosition pos){
        if(pos == LiftPosition.RESET){
            lift.setTargetPosition(liftLocations.get(LiftPosition.RESET));
            wrist.move(wristLocations.get(LiftPosition.RESET));
        }
        else if(pos == LiftPosition.LOW_BASKET){
            //lift.runToPosition(liftLocations.get(LIFT_POSITION.LOW_BASKET));
            //wrist.runToPosition(wristLocations.get(LIFT_POSITION.LOW_BASKET));
        }
        else if(pos == LiftPosition.HIGH_BASKET){
            //lift.runToPosition(liftLocations.get(LIFT_POSITION.HIGH_BASKET));
            //wrist.runToPosition(wristLocations.get(LIFT_POSITION.HIGH_BASKET));
        }
    }

    public void resetEncoders() {
        lift.resetEncoders();
        wrist.resetEncoders();
    }
}
