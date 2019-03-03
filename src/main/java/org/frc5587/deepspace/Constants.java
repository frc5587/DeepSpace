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

    public static boolean COMPRESSOR_ENABLED = true;
    public static final int TCP_PORT = 3456;

    // set to zero to skip waiting for confirmation, set to nonzero to wait and
    // report to DS if action fails
    public static final int kTimeoutMs = 10;
    public static final double kVCompSaturation = 12.0;

    public static final class Elevator {
        public static final double STU_PER_INCH = 18884 / 30;

        public static final int MIN_UNCHECKED_VELOCITY = 100;
        public static final double UNSAFE_IDLE_CURRENT_DRAW = 10.0;
        public static final double UNSAFE_TIME_BEFORE_STOP = 1.5;

        public static final double bottomTicks = 0;
        public static final double middleTicks = 28 * STU_PER_INCH;
        public static final double topTicks = 56 * STU_PER_INCH;

        public static final int kSlotIdx = 0;
        public static final int kPIDLoopIdx = 0;

        public static final int kTimeoutMs = 10;

        public static final double vCompSaturation = 12.0;

        public static final double[] PIDs = {
            2.9568, //kP
            0.0, //kI
            10.9136, //kD
            (1 / 5246) * 1023 // kF
        };

        public static final double minPercentOut = 0.0, maxPercentBw = .6, maxPercentFw = 1;
        public static final double HOLD_VOLTAGE = 0.075;

        public static final int maxVelocity = 5246, maxAcceleration = 5246;
    }

    public static final class Drive {
        // set to zero to skip waiting for confirmation, set to nonzero to wait and
        // report to DS if action fails
        public static final int kTimeoutMs = 10;

        public static final double kMaxVelocity = 2500; // measured in STU

        public static final double kVCompSaturation = 12.0;

        public static final int minBufferCount = 10;

        public static final int stuPerInch = 215;

        public static final int stuPerRev = 4050;

        // Safety limits
        public static final double minPercentOut = 0, maxPercentBw = 1, maxPercentFw = 1;

        // FPID Constants
        public static final FPID leftPIDs = new FPID(
                (1 / 3933) * 1023, // kF
                0.8056, // kP
                0.001, // kI
                17.7232 // kD
        );
        public static final FPID rightPIDs = new FPID(
                (1 / 4060) * 1023, // kF
                0.8056, // kP
                0.001, // kI
                17.7232 // kD
        );

        // Pathfinder constants
        public static double gyrokP = 0.00;
        public static final int wheelDiameter = 6;
        public static final PIDVA pathfinderPIDVALeft = new PIDVA(0.04, 0.0, 0.0, 0.000327 * stuPerInch / 10f,
                0.0001 * stuPerInch / 10f);
        public static final PIDVA pathfinderPIDVARight = new PIDVA(0.04, 0.0, 0.0, 0.000317 * stuPerInch / 10f,
                0.0001 * stuPerInch / 10f);

        // Turn controller
        public static final int GYRO_HISTORY_LENGTH = 50;
        public static final double LPF_PERCENT = 0.5;
        public static final double TOLERANCE_DEGREES = 2.0;
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
