package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Wheels {

    private DcMotorEx rf;
    private DcMotorEx rb;
    private DcMotorEx lf;
    private DcMotorEx lb;

    public Wheels (DcMotorEx rf, DcMotorEx rb, DcMotorEx lf, DcMotorEx lb) {
        this.rf = rf;
        this.rb = rb;
        this.lf = lf;
        this.lb = lb;
    }


}
