package org.frc5587.deepspace;

import org.frc5587.lib.pid.FPID;
import org.frc5587.lib.pid.PIDVA;

/**
 * Constants is a central place in which to store all of the constants having to
 * do with the robot and its subsystems. Where applicable, the constants are
 * stored within subclasses that describe the subsystem with which the constant
 * is associated and/or used.
 */

public class Constants {

    public static final boolean COMPRESSOR_ENABLED = true;

    // set to zero to skip waiting for confirmation, set to nonzero to wait and
    // report to DS if action fails
    public static final int TIMEOUT_MS = 10;

    public static final double V_COMP_SATURATION = 12.0;

    public static final class Elevator {
        public static final double TICKS_PER_INCH = 18349 / 27;

        // Hatch setpoints
        public static final double BOTTOM_HATCH = 0;
        public static final double MIDDLE_HATCH = 23.5 * TICKS_PER_INCH;
        public static final double TOP_HATCH = 45 * TICKS_PER_INCH;

        // Cargo setpoints
        public static final double CARGO_SHIP = 20.5 * TICKS_PER_INCH;
        public static final double BOTTOM_CARGO = 20.5 * TICKS_PER_INCH;
        public static final double MIDDLE_CARGO = 40.5 * TICKS_PER_INCH;
        public static final double TOP_CARGO = 58.5 * TICKS_PER_INCH;

        // Safety limits
        public static final float MIN_PERCENT_OUT = 0;
        public static final float MAX_PERCENT_BW = 1;
        public static final float MAX_PERCENT_FW = 1;
        public static final int MAX_VELOCITY = 2269;
        public static final int MAX_ACCELERATION = 2 * MAX_VELOCITY;
        public static final int MIN_VELOCITY = 0;

        public static final FPID ELEVATOR_PID = new FPID(
                (1 / MAX_VELOCITY) * 1023,  // kF
                2.9568,  // kP
                10.9136,  // kI
                10.9136  // kD
        );

        public static final double HOLD_PERCENT = 0.05;

        public static final int SMART_MOTION_SLOT = 0;
        public static final double ALLOWED_ERR = 0.1 * TICKS_PER_INCH;
    }

    public static final class Drive {
        public static final double MAX_VELOCITY = 2500; // measured in STU

        public static final int MIN_BUFFER_COUNT = 10;

        public static final int STU_PER_INCH = 215;

        public static final int STU_PER_REV = 4050;

        // Safety limits
        public static final double MIN_PERCENT_OUT = 0, MAX_PERCENT_BW = 1, MAX_PERCENT_FW = 1;

        // FPID Constants
        public static final FPID LEFT_PIDS = new FPID(
                (1 / 4284) * 1023, // kF
                0.42752, // kP
                0.001, // kI
                8.5504 // kD
        );
        public static final FPID RIGHT_PIDS = new FPID(
                (1 / 4553) * 1023, // kF
                0.37584, // kP
                0.001, // kI
                7.5168 // kD
        );

        // Pathfinder constants
        public static final double GYRO_KP = 0.00;
        public static final int WHEEL_DIAMETER = 6;
        public static final PIDVA PATHFINDER_PIDVA_LEFT = new PIDVA(0.04, 0.0, 0.0, 0.000327 * STU_PER_INCH / 10f,
                0.0001 * STU_PER_INCH / 10f);
        public static final PIDVA PATHFINDER_PIDVA_RIGHT = new PIDVA(0.04, 0.0, 0.0, 0.000317 * STU_PER_INCH / 10f,
                0.0001 * STU_PER_INCH / 10f);

        // Turn controller
        public static final double LPF_PERCENT = 1;  // Janky low pass filter
        public static final double TOLERANCE_DEGREES = 2.0;
        public static final double CONSTANT_FORWARD_PERCENT = 0.3;
        public static final FPID TURN_FPID = new FPID(
                0,  // kF
                0.03, // kP
                0, // kI
                0  // kD
        );
        
    }

    public static final class Hatch {
        public static final boolean REQUIRE_BOTH = false;
        public static final boolean LIMIT_CONTROL_ON = false;
    }
}
