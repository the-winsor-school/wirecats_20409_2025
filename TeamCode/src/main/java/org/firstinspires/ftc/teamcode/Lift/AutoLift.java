package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.Enums.LiftPosition;

public class AutoLift {
    public EncoderMotorObject lift;
    public WristMotorObject wrist;

    //arm location encoder values
    public final int LiftResetValue = 0;
    public final int LiftHighRungValue = 10000;
    public final double WristResetAngle = 3.37;
    public final double WristHighRungAngle = 1.30;

    public AutoLift(DcMotorEx LiftMotor, DcMotorEx WristMotor, AnalogInput wristAngle){
        lift = new EncoderMotorObject(LiftMotor, 1, 300);
        wrist = new WristMotorObject(WristMotor, 0.3, 0.1, wristAngle);
    }

    /**
     * moves scissor lift async
     * moves wrist sync with wrist.moveCloserToPosition() function
     * @param pos desired lift position
     */
    public void moveLiftToPosition(LiftPosition pos){
        if(pos == LiftPosition.RESET){
            lift.setTargetPosition(LiftResetValue);
            wrist.setTargetAngle(WristResetAngle);
        }
        else if(pos == LiftPosition.HIGH_RUNG){
            lift.setTargetPosition(LiftHighRungValue);
            wrist.setTargetAngle(WristHighRungAngle);
        }
    }

    public void moveScissorToPosition(LiftPosition pos){
        if(pos == LiftPosition.RESET){
            lift.setTargetPosition(LiftResetValue);
        }
        else if(pos == LiftPosition.HIGH_RUNG){
            lift.setTargetPosition(LiftHighRungValue);
        }
    }

    public void moveWristToPosition(LiftPosition pos){
        if(pos == LiftPosition.RESET){
            wrist.setTargetAngle(WristResetAngle);
        }
        else if(pos == LiftPosition.HIGH_RUNG){
            wrist.setTargetAngle(WristHighRungAngle);
        }
    }

    public boolean liftStillMoving() {
        return (lift.motorMoving() || wrist.movingToTarget());
    }

}
