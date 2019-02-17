/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace;


import java.io.IOException;

import org.frc5587.deepspace.commands.*;
import org.frc5587.deepspace.commands.control.ControlLift;
import org.frc5587.deepspace.subsystems.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;


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
    public static final Lift LIFT = new Lift();
    
    public static CameraServer cameraServer;
    public static UsbCamera driverCamera;
    public static TCPServer tcpServer;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        COMPRESSOR.setClosedLoopControl(Constants.COMPRESSOR_ENABLED);
        // cameraServer = CameraServer.getInstance();
	    // driverCamera = cameraServer.startAutomaticCapture(0);
        // cameraServer.startAutomaticCapture(driverCamera);
        new LimitResetElevator().start();
        new UpdateGyroHistory().start();

        try {
            tcpServer = new TCPServer(Constants.TCP_PORT);
            tcpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        SmartDashboard.putData(new ResetElevator());

        new Manager().start();
        new ControlLift().start();
    }

    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Heading", DRIVETRAIN.getHeading());
        SmartDashboard.putNumber("Ele Current", ELEVATOR.getCurrent());
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
