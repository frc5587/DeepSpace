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

    public static boolean compressorEnabled = false;
    public static final int TCP_PORT = 3456;

    // set to zero to skip waiting for confirmation, set to nonzero to wait and
    // report to DS if action fails
    public static final int kTimeoutMs = 10;
    public static final double kVCompSaturation = 12.0;

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

        // PIDF Constants
        public static final FPID leftPIDs = new FPID(0.000975 * 1023, // kF
                0.04, // kP
                0.0, // kI
                0.0 // kD
        );
        public static final FPID rightPIDs = new FPID(
                (1 / 4060) * 1023, // kF
                0.8056, // kP
                0.001, // kI
                17.7232 // kD
        );

        public static final int wheelDiameter = 6;
        public static double gyrokP = 0.00;

        public static final PIDVA pathfinderPIDVALeft = new PIDVA(0.04, 0.0, 0.0, 0.000327 * stuPerInch / 10f,
                0.0001 * stuPerInch / 10f);
        public static final PIDVA pathfinderPIDVARight = new PIDVA(0.04, 0.0, 0.0, 0.000317 * stuPerInch / 10f,
                0.0001 * stuPerInch / 10f);
    }
}
