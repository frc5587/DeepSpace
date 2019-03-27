package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

/**
 * UpdateGyroHistory
 */
public class UpdateGyroHistory extends Thread {
    public UpdateGyroHistory() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                // System.out.println("History updating...");
                Robot.DRIVETRAIN.updateGyroHistory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
