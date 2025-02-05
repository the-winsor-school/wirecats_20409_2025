package org.firstinspires.ftc.teamcode.Lift.TestArmOpModes;

public class Test extends Thread {
    public boolean threadRan = false;

    public void start() {
        threadRan = true;
    }
}
