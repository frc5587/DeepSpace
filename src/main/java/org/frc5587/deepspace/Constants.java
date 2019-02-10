package org.frc5587.deepspace;

/**
 * Constants is a central place in which to store all of the constants having to
 * do with the robot and its subsystems. Where applicable, the constants are
 * stored within subclasses that describe the subsystem with which the constant
 * is associated and/or used.
 */
public class Constants {

    public static boolean compressorEnabled = true;

    public static final class Elevator {
        public static final double STU_PER_INCH = 628.704;

        public static final double spoolDiameter = 2.0;

        public static final double bottomTicks = 0;

        public static final double middleTicks = 47 * STU_PER_INCH;

        public static final double topTicks = 75 * STU_PER_INCH;

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
        public static final double HOLD_VOLAGE = 0.05;

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

        public static final int wheelDiameter = 6;


        // Safety limits
        public static final double minPercentOut = 0,
            maxPercentBw = 0.75,
            maxPercentFw = 0.75;

        public static final double turnSensitivity = 0.8;

        // PIDF Constants
        public static final double[] leftPIDs = {
            0.01, // kP
            0.0, // kI
            0.01, // kD
            0.000327 * 1023 // kF
        };
        public static final double[] rightPIDs = {
            0.01, // kP
            0.0, // kI
            0.01, // kD
            0.000317 * 1023 // kF
        };

        public static final double[] pathfinderPIDVALeft = {
            0.04, // kP
            0.0, // kI/**/
            0.0, // kD
            0.000327 * stuPerInch / 10f, // kV
            0.0001 * stuPerInch / 10f // kA
        };
        public static final double[] pathfinderPIDVARight = {
            0.04, // kP
            0.0, // kI
            0.0, // kD
            0.000317 * stuPerInch / 10f, // kV
            0.0001 * stuPerInch / 10f // kA
        };

		public static double gyrokP = 0.00;
    }
}
