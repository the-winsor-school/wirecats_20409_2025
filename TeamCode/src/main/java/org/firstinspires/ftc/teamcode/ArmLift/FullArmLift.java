package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class FullArmLift {
    public GenericMotor liftMotor;
    public GenericMotor wristMotor;
    public Claw claw;

    public FullArmLift(DcMotorEx LiftMotor, DcMotorEx WristMotor, CRServo clawServo){
        //both values to be tested
        liftMotor = new GenericMotor(LiftMotor, 0.5, 200);
        wristMotor = new GenericMotor(WristMotor, 0.25, 100);
        claw = new Claw(clawServo);
    }

    public void moveLiftToPosition (LIFT_POSITION pos){
        if(pos == LIFT_POSITION.RESET){
            liftMotor.runToPosition(0);
            wristMotor.runToPosition(0);
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

    public void joystickControlWrist(float input) {
        wristMotor.setMotorPower(input);
    }

    public enum LIFT_POSITION {
        RESET,
        HIGHBASKET,
        LOWBASKET

    }
}
