package org.frc5587.deepspace;

import org.frc5587.lib.pid.FPID;
import org.frc5587.lib.pid.PIDVA;

public class Constants {

    public static boolean compressorEnabled = false;
    public static final int TCP_PORT = 3456;

    // set to zero to skip waiting for confirmation, set to nonzero to wait and
    // report to DS if action fails
    public static final int kTimeoutMs = 10;
    public static final double kVCompSaturation = 12.0;

    public static final class Drive {
        public static final int kTimeoutMs = 10;

        public static final int minBufferCount = 10;

        public static final double kMaxVelocity = 900;

        public static final double kVCompSaturation = 12.0;

        public static final int stuPerInch = 77;

        public static final int stuPerRev = 1389;

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

    public static final class Turret {
        public static final int MAX_CRUISE_VELOCITY = 1321; // In native units / 100ms

        public static final double NU_PER_DEGREE = 4096 / 360; // Native units / degree - in CTRE Mag Encoder Docs

        public static final int PID_SLOT = 0;
        public static final double[] TURRET_FPID = {
                // 0.3874, //kF not needed because just using position PID
                0.0, 5.15, // 5.15
                0, // kI
                35.25, // 31.25
        };
    }
}
