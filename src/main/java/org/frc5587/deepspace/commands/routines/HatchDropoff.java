// package org.frc5587.deepspace.commands.routines;

// import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;
// import org.frc5587.deepspace.subsystems.Hatch.HatchGrabState;

// import edu.wpi.first.wpilibj.command.CommandGroup;

// /**
//  * HatchDropoff
//  */
// public class HatchDropoff extends CommandGroup {
//     public HatchDropoff(ElevatorHeights height) {
//         addParallel(new SetElevator(height));
//         addSequential(new Limelight());
//         addSequential(new SetHatch(HatchGrabState.DROP));
//     }
// }