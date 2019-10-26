/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace;

import java.util.ArrayList;

import org.frc5587.deepspace.commands.*;
import org.frc5587.deepspace.commands.control.*;
import org.frc5587.deepspace.commands.routines.Limelight;
import org.frc5587.deepspace.subsystems.*;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    public static final Compressor COMPRESSOR = new Compressor(RobotMap.PCM_ID);
    public static final Elevator ELEVATOR = new Elevator();
    public static final Drive DRIVETRAIN = new Drive();
    public static final Hatch HATCH = new Hatch();
    public static final Cargo CARGO = new Cargo();
    public static final PistonLift PISTON_LIFT = new PistonLift();

    private static ArrayList<Command> controlCommands;
    private static ArrayList<Command> continuousCommands;
    public static CameraServer cameraServer;
    public static UsbCamera driverCamera;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        COMPRESSOR.setClosedLoopControl(Constants.COMPRESSOR_ENABLED);

        cameraServer = CameraServer.getInstance();
        cameraServer.startAutomaticCapture(0);
        // cameraServer.startAutomaticCapture(1);

        // SmartDashboard.putData(new ResetElevator());

        continuousCommands = new ArrayList<>();
        // continuousCommands.add(new LimitResetElevator());
        // continuousCommands.add(new UpdateGyroHistory());
        continuousCommands.add(new LogDebugData());
        continuousCommands.forEach(c -> c.start());

        controlCommands = new ArrayList<>();
        controlCommands.add(new Manager());
        controlCommands.add(new ControlElevator());
        controlCommands.add(new ControlHatch());
        controlCommands.add(new ControlCargo());
        controlCommands.add(new ControlPistonLift());

        Limelight.disableLEDs();
    }

    private void startControlCommands() {
        for (var command : controlCommands) {
            if (command.isCompleted() || !command.isRunning()) {
                command.start();
            }
        }
    }

    @Override
    public void autonomousInit() {
        startControlCommands();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        startControlCommands();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}
