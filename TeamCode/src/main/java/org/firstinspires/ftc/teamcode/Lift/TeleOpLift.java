package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class TeleOpLift {
    public SimpleMotorObject scissor;
    public SimpleMotorObject wrist;

    public TeleOpLift(DcMotorEx ScissorMotor, DcMotorEx WristMotor){
        scissor = new SimpleMotorObject(ScissorMotor, 1);
        wrist = new SimpleMotorObject(WristMotor, 0.5);
    }


    public void joystickControlLift(float input) {
        scissor.setMotorPower(input);
    }

    public void joystickControlWrist(float input) {
        wrist.setMotorPower(input);
    }

}
