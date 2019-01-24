/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace;

import java.io.IOException;

import org.frc5587.deepspace.commands.ArcadeDrive;
import org.frc5587.deepspace.commands.ControlTurret;
import org.frc5587.deepspace.commands.PostDebugData;
import org.frc5587.deepspace.commands.ResetEncoders;
import org.frc5587.deepspace.subsystems.Drive;
import org.frc5587.deepspace.subsystems.Turret;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    public static final Drive DRIVETRAIN = new Drive();
    public static final Turret TURRET = new Turret();

    private static TCPTestServer tcpServer;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        new PostDebugData().start();
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        SmartDashboard.putData("Reset Encoders", new ResetEncoders());

        // new SerialTest().start();
        // new ControlTurret().start();
        // new ArcadeDrive().start();
        
        try {
            tcpServer = new TCPTestServer(Constants.TCP_PORT);
            tcpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        if (tcpServer != null) {
            tcpServer.close();
        }
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}
