package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class FullArmLift {
    public LiftMotor liftMotor;
    private TouchSensor bottomLiftLimit;
    private TouchSensor topLiftLimit;

    public FullArmLift(DcMotorEx LiftMotor, TouchSensor botLiftLimit, TouchSensor tLiftLimit){
        liftMotor = new LiftMotor(LiftMotor, 0.5, 200);
        bottomLiftLimit = botLiftLimit;
        topLiftLimit = tLiftLimit;
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
        if (!(bottomLiftLimit.isPressed() && input < 0) || !(topLiftLimit.isPressed() && input > 0)) {
            liftMotor.setMotorPower(input);
        }
    }

    public enum LIFT_POSITION {
        RESET,
        HIGHBASKET,
        LOWBASKET

    }
}
