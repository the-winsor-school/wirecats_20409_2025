package org.firstinspires.ftc.teamcode.ArmLift;

import com.qualcomm.robotcore.hardware.DcMotor;

public class FullArmLift {
    public LiftMotor motor;

    public FullArmLift(DcMotor liftMotor){
        motor = new LiftMotor(liftMotor, 0.5);
    }

}
