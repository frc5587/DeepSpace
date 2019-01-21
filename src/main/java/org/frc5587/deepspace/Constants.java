package org.frc5587.deepspace;

/**
 * Constants is a central place in which to store all of the constants having to
 * do with the robot and its subsystems. Where applicable, the constants are
 * stored within subclasses that describe the subsystem with which the constant
 * is associated and/or used.
 */
public class Constants {

    public static boolean compressorEnabled = true;

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
            maxPercentBw = 1,
            maxPercentFw = 1; 

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
