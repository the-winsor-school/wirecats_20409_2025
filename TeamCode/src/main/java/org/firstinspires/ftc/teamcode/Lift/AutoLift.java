package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;

import java.util.HashMap;

public class AutoLift {
    public EncoderMotorObject lift;
    public WristMotorObject wrist;

    //arm location encoder values
    //TODO find the values
    public final int LiftResetValue = 0;
    public final int LiftHighRungValue = 5000;
    public final double WristResetAngle = 3.37;
    public final double WristHighRungAngle = 1.52;

    public final double WristHighRungPlaceAngle = 1.37;


    public AutoLift(DcMotorEx LiftMotor, DcMotorEx WristMotor, AnalogInput wristAngle){
        lift = new EncoderMotorObject(LiftMotor, 1, 300);
        wrist = new WristMotorObject(WristMotor, 0.3, 0.05, wristAngle);
    }

    public void moveScissorToPosition (LiftPosition pos){
        if(pos == LiftPosition.RESET){
            lift.setTargetPosition(LiftResetValue);
        }
        else if(pos == LiftPosition.HIGH_RUNG){
            lift.setTargetPosition(LiftHighRungValue);
        }
    }

    @Deprecated
    public void moveLiftToPosition (LiftPosition pos){
        if(pos == LiftPosition.RESET){
            lift.setTargetPosition(LiftResetValue);
            WristThread wristThread = new WristThread(wrist, WristResetAngle);
            wristThread.start();
        }
        else if(pos == LiftPosition.HIGH_RUNG){
            lift.setTargetPosition(LiftHighRungValue);
            WristThread wristThread = new WristThread(wrist, WristHighRungAngle);
            wristThread.start();
        }
    }

    public void moveWristToPositionAsync (LiftPosition pos){
        if(pos == LiftPosition.RESET){
            WristThread wristThread = new WristThread(wrist, WristResetAngle);
            wristThread.start();
        }
        else if(pos == LiftPosition.HIGH_RUNG){
            WristThread wristThread = new WristThread(wrist, WristHighRungAngle);
            wristThread.start();
        }
    }

}
