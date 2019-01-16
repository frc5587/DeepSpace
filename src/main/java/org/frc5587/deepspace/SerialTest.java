package org.frc5587.deepspace;

import java.nio.charset.StandardCharsets;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;

public class SerialTest extends Command {
    private static int baudRate = 9600;
    private static int messageSize = 5;
    private SerialPort port;

    public SerialTest() {
        // Use requires() here to declare subsystem dependencies
        // requires(Robot.exampleSubsystem);
        port = new SerialPort(baudRate, SerialPort.Port.kUSB);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        while (port.getBytesReceived() >= messageSize) {
            byte[] bytes = port.read(messageSize);
            String receivedString = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(receivedString);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}