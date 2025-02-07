package org.firstinspires.ftc.teamcode.Driving;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Enums.DrivingOrientation;
import org.firstinspires.ftc.teamcode.Enums.MotorState;

public class AutoDriving {

    private final Wheels wheels;

    /**
     * converts cm tob wheels tick based on revs, ticks per rev, and gearboxes
     */
    public final double cmPerTick;

    /**
     * test for this value after any major changes to the robot
     *  limit is found when robot start to slip/skid when acceleration
     */
    private double maxAcceleration = 20; //measure in some unit

    public final int wheelsTolerance = 50;
    private double horizontalStretchSigmoid;

    /**
     *horizontal shift for sigmoid so that left side lines up with x=0
     *  makes point (0, 0.1) on the function
     */
    private double horizontalShiftSigmoid;

    /**
     * domain of sigmoid function is [-1,1]
     * so total domain is 2 * horizontal stretch
     */
    private double sigmoidDomain;

    /**
     * experimentally found distance travelled during sigmoid function
     * run sigmoid function and measure with ruler for distance in cm
     */
    private final double sigmoidDistance = 0;

    public AutoDriving(Wheels wheels) {
        this.wheels = wheels;

        calculationsBasedOnMaxAccel();

        cmPerTick = 0.059418;
    }

    /**
     * this is not an async function it will steal your thread
     * DO NOT USE FOR HORIZONTAL
     * @param orientation is vertical vs horizontal move
     * @param totalTime total time for movement in milliseconds
     */
    public void sigmoidTime(DrivingOrientation orientation, MotorState direction, int totalTime) {

        //this is the amount of time the wheels are moving at max power
        //this is the total time - (2* acceleration time)
        //acceleration time is the domain of the sigmoid
        double maxPowerTime = totalTime - (2 * sigmoidDomain);

        int directionCoefficient = direction==MotorState.FORWARD?1:-1;

        //acceleration period
        sigmoidSection(orientation, direction, true);

        //regular straight motion
        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        timer.reset();
        while(timer.time() < maxPowerTime) {
            simpleDrive(orientation, directionCoefficient);
        }

        //deceleration period
        sigmoidSection(orientation, direction, false);

        //stop motion
        wheels.stop();
    }

    public void sigmoidSection(DrivingOrientation orientation, MotorState direction, Boolean accelerating) {
        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        int accelerationDirectionCoefficient = accelerating?1:-1;
        int directionCoefficient = direction==MotorState.FORWARD?1:-1;

        timer.reset();
        while (timer.time() < sigmoidDomain) {
            double currentPower = sigmoid((accelerationDirectionCoefficient * (timer.time() + horizontalShiftSigmoid)), horizontalStretchSigmoid);
            simpleDrive(orientation, (directionCoefficient * currentPower));
        }
    }

    //MATH FUNCTIONS
    private double sigmoid(double time, double horizontalStretchComponent) {
        return (1/(1+Math.exp(-time) * horizontalStretchComponent)); }

    private double sigmoidIntegral(double ticks, double horizontalStretchSigIntegral) {
        return (Math.log(1+Math.exp(ticks*horizontalStretchSigIntegral))/horizontalStretchSigIntegral); }

    private void calculationsBasedOnMaxAccel() {
        horizontalStretchSigmoid = 4 * maxAcceleration;
        horizontalShiftSigmoid = Math.log(9) / maxAcceleration;
        sigmoidDomain = 2 / horizontalStretchSigmoid;
    }


    //MAX ACCEL CHANGES
    public double getMaxAcceleration() { return maxAcceleration; }

    /**
     * only use for teleOp
     */
    public void adjustMaxAcceleration(double maxAccelerationAdjustment) {
        this.maxAcceleration += maxAccelerationAdjustment;
        calculationsBasedOnMaxAccel();
    }

    public void stop() { wheels.stop(); }

    public void turn (double t) { wheels.setEachPower(t, t, -t, -t); }

    public void simpleDrive(DrivingOrientation orientation, double power) {
        if (orientation == DrivingOrientation.VERTICAL) {
            wheels.vertical(power);
        } else if (orientation == DrivingOrientation.HORIZONTAL) {
            wheels.horizontal(power);
        }
    }

    /**
     * drives a certain distance using the built in encoders
     * this function will stealn your thread and is NOT ASYNC
     * @param orientation horizontal vs vertical
     * @param distance in cm
     */
    public void distanceDrivingSync(DrivingOrientation orientation, double distance) {
        int ticksDist = (int) Math.round(distance / cmPerTick);
        wheels.resetEncoders();
        wheels.setTargetPositionTolerance(wheelsTolerance);
        wheels.setAllPowers(1);
        if (orientation == DrivingOrientation.VERTICAL) {
            wheels.setAllTargetPosition(ticksDist);
        } else if (orientation == DrivingOrientation.HORIZONTAL) {
            wheels.setEachTargetPosition(-ticksDist, ticksDist, ticksDist, -ticksDist);
        }
        while (((wheels.getAverageCurrentPosition() - wheelsTolerance) > ticksDist) ||
                ((wheels.getAverageCurrentPosition() - wheelsTolerance) < ticksDist)) {
            wheels.runToPosition();
        }
        wheels.runWithoutEncoders();
        wheels.stop();
    }

}
