package org.firstinspires.ftc.teamcode.Lift;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Lift.LiftEnums.LiftPosition;
import org.firstinspires.ftc.teamcode.Lift.LiftEnums.MotorState;

public class WristMotorObject extends SimpleMotorObject {
    //motor uses DcMotorEx instead of DcMotor to allow us to have more control over the encoder loop
    //encoder loop is when we set target position and tell the motor to run to that position
    //with DcMotorEx we can adjust the tolerance
    //DcMotorEx would also allow us to eadily impelement PID coeffients if we wanted to get really fancy
    private DcMotorEx motor;
    private double powerUsed;
    private AnalogInput wristAngle;

    //Potentiometer Values for angles

    public final double ResetAngle = 0;
    public final double LowRungAngle = 0;
    public final double HighRungAngle = 0;

    public WristMotorObject (DcMotorEx wristMotor, double powerUsed, AnalogInput wristAngle) {
        super(wristMotor, powerUsed);

        this.wristAngle = wristAngle;
    }

    public void moveToPosition(LiftPosition position) {
        if (position == LiftPosition.RESET) {
        }
    }
}
