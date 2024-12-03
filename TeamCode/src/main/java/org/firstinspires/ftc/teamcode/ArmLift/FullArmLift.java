package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class FullArmLift {
    public LiftMotor liftMotor;
    public Claw claw;

    public FullArmLift(DcMotorEx LiftMotor){
        liftMotor = new LiftMotor(LiftMotor, 0.5, 200);
    }

    public void moveLiftToPosition (LIFT_POSITION pos){
        if(pos == LIFT_POSITION.RESET){
            liftMotor.runToPosition(0);
        }
        if(pos == LIFT_POSITION.HIGHBASKET){
            //liftMotor.runToPosition(); test for encoder
        }
        if(pos == LIFT_POSITION.LOWBASKET){
            //liftMotor.runToPosition(); test for encoder
        }
        //add one more for PICKINGUP if RESET does not work
    }

    public void joystickControlLift(float input) {
        liftMotor.setMotorPower(input);
    }

    public enum LIFT_POSITION {
        RESET,
        HIGHBASKET,
        LOWBASKET

    }
}
