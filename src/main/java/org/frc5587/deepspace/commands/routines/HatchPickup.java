// package org.frc5587.deepspace.commands.routines;

// import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;
// import org.frc5587.deepspace.subsystems.Hatch.HatchGrabState;

// import edu.wpi.first.wpilibj.command.CommandGroup;

// /**
//  * Pickup
//  */
// public class HatchPickup extends CommandGroup {
//     public HatchPickup() {
//         addParallel(new SetElevator(ElevatorHeights.BOTTOM_LEVEL));
//         addSequential(new Limelight());
//         addSequential(new SetHatch(HatchGrabState.GRAB));
//     }
// }