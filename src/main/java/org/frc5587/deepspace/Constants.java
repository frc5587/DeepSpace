package org.frc5587.deepspace;

public class Constants {

    public static boolean compressorEnabled = false;

    //set to zero to skip waiting for confirmation, set to nonzero to wait and report to DS if action fails
    public static final int kTimeoutMs = 10;

    public static final class Drive {
        public static final int kTimeoutMs = 10;

        public static final int minBufferCount = 10;

        public static final double kMaxVelocity = 900;

        public static final double kVCompSaturation = 12.0;

        public static final int stuPerInch = 77;

        public static final int stuPerRev = 1389;

        public static final int wheelDiameter = 6;

        //Safety limits
        public static final double minPercentOut = 0,
            maxPercentBw = 1,
            maxPercentFw = 1; 

        //PIDF Constants
        public static final double[] leftPIDs = {
            0.04,	//kP
            0.0,	//kI
            0.0,	//kD
            0.000975 * 1023 	//kF
        };
        public static final double[] rightPIDs = {
            0.04,	//kP
            0.0,	//kI
            0.0,	//kD
            0.000975 * 1023    //kF
        };

        public static final double[] pathfinderPIDVA = {
            0.003,    //kP
            0.0,    //kI
            0.0,    //kD
            0.000975 * stuPerInch / 10f,    //kV
            0.0005 * stuPerInch / 10f     //kA
        };

		public static double gyrokP = 0.0;
    }
    public class Elevator {
        // Which PID slot to pull gains from. Starting 2018, you can choose from 0,1,2 or 3.
        public static final int kSlotIdx = 0;
        // Talon SRX/Victor SPX will supported multiple (cascaded) PID loops. For now we just want the primary one
        public static final int kPIDLoopIdx = 0;
        // set to zero to skip waiting for confirmation, set to nonzero to wait and report to DS if action fails
        public static final int kTimeoutMs = 10;


        public static final double vCompSaturation = 12.0;
    
    
    //775pro lift code

        // public static final double holdPercent = 0.2;

        // // The tolerance for the target position (see Elevator.isDone())
        // public static final double kDeadband = 200;
        // //PID Constants
        // public static final double kF = .2,
        //         kP = 0.1,
        //         kI = 0.0,
        //         kD = 0.13;
        // //Safety limits
        // public static final double minPercentOut = 0,
        //         maxPercentBw = .2,
        //         maxPercentFw = 1;

        // //System Constraints
        // public static final int maxVelocity = 4200, //measured in native units/100ms
        //         maxAcceleration = 8000; //measured in native units/100ms/sec
        
    //MiniCIM Lift Code

        public static final double holdPercent = 0.2;

        // The tolerance for the target position (see Elevator.isDone())
        public static final double kDeadband = 200;
        //PID Constants
        public static final double kF = .48,
                kP = 0.05,
                kI = 0.0,
                kD = 0.07;
        
        //Safety limits
        public static final double minPercentOut = 0,
                maxPercentBw = .7,
                maxPercentFw = 1;

        //System Constraints
        public static final int maxVelocity = 2500, //measured in native units/100ms
                maxAcceleration = 2500; //measured in native units/100ms/sec

        //Unit Conversion
        public static final int stuPerInch = 1500;

        //Hall effect sensor height in native units MEASURED FROM BOTTOM OF cube
        // 
        public static final int hallHeight = 0;

        //Height to place on scale in inches
        public static final double switchHeight = 25; //19.5
        public static final double carryHeight = 7.25; //10
        public static final double intakeHeight = 0; //5.75
    }

    public static final class Turret {
        public static final int PID_SLOT = 0;
        public static final double[] turretFPID = {
            0.0, //kF
            0.0, //kP
            0.0, //kI
            0.0, //kD
        };
    }
}
