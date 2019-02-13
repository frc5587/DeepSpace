/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int PCM_ID = 10;

	public static class Hatch {
		public static final int[] HATCH_PISTONS = { 0, 1 };
		public static final int[] SLICER_PISTONS = { 2, 3 };
	}

	public static class Drive {
		public static int LEFT_MASTER = 2;
		public static int RIGHT_MASTER = 3;
		public static int LEFT_SLAVE = 4;
		public static int RIGHT_SLAVE = 5;
	}

	public static class Elevator {
		public static final int ELEVATOR_MASTER = 7;
		public static final int ELEVATOR_SLAVE = 6;
	}

}
