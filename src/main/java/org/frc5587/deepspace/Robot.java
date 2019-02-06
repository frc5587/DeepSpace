/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace;



import org.frc5587.deepspace.commands.ArcadeDrive;
import org.frc5587.deepspace.subsystems.Drive;

import java.io.IOException;

import org.frc5587.deepspace.commands.*;
import org.frc5587.deepspace.subsystems.*;
import edu.wpi.first.wpilibj.CameraServer;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.cscore.UsbCamera;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    public static final Drive DRIVETRAIN = new Drive();
    public static final Hatch HATCH = new Hatch();
    public static final Compressor c = new Compressor();
    public static final Elevator e = new Elevator();
    public static CameraServer cameraServer;
    public static UsbCamera driverCamera;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        c.start();
        cameraServer = CameraServer.getInstance();
	    driverCamera = cameraServer.startAutomaticCapture(0);
	    cameraServer.startAutomaticCapture(driverCamera);
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        new ArcadeDrive().start();
        new ControlHatch().start();
        new ControlElevator().start();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}
