package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;

import java.util.HashMap;

public class AutoLift {
    public EncoderMotorObject lift;
    public WristMotorObject wrist;

    //arm location encoder values
    public final int LiftResetValue = 0;
    public final int LiftLowRungValue = 0;
    public final int LiftHighRungValue = 0;
    public final double WristResetAngle = 0;
    public final double WristLowRungAngle = 0;
    public final double WristHighRungAngle = 0;


    public AutoLift(DcMotorEx LiftMotor, DcMotorEx WristMotor, AnalogInput wristAngle){

        lift = new EncoderMotorObject(LiftMotor, 1, 100);
        wrist = new WristMotorObject(WristMotor, 0.3, 0.1, wristAngle);
    }

    public void moveLiftToPosition (LiftPosition pos){
        if(pos == LiftPosition.RESET){
            lift.setTargetPosition(LiftResetValue);
            wrist.moveToPosition(WristResetAngle);
        }
        else if(pos == LiftPosition.LOW_RUNG){
            lift.setTargetPosition(LiftLowRungValue);
            wrist.moveToPosition(WristLowRungAngle);
        }
        else if(pos == LiftPosition.HIGH_RUNG){
            lift.setTargetPosition(LiftHighRungValue);
            wrist.moveToPosition(WristHighRungAngle);
        }
    }

}
