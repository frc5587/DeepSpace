package org.frc5587.deepspace.subsystems;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDControl extends Subsystem {

    private I2C arduino = new I2C(I2C.Port.kMXP, 8);

    public LEDControl() {

    }

    public void sendColor(DriverStation.Alliance aColor) {
        byte[] colorByte;
        switch (aColor) {
        case Blue:
            colorByte = new byte[] { (byte) 'b' };
            break;
        case Red:
            colorByte = new byte[] { (byte) 'r' };
            break;
        default:
            colorByte = new byte[] { (byte) 'w' };
        }
        byte[] combinedArray = combineArrays(new byte[] { (byte) 'u' }, colorByte);
        arduino.writeBulk(combinedArray);
    }

    public void sendColorWithHeight(Color aColor, float heightInInches) {
        byte[] heightArray = toByteArray((int) heightInInches);
        byte[] combinedArray = combineArrays(new byte[] { (byte) aColor.getChar()}, heightArray);
        arduino.writeBulk(combinedArray);
    }

    public void sendHeight(int height) {
        byte[] heightArray = toByteArray(height);
        byte[] combinedArray = combineArrays(new byte[] { (byte) 'e' }, heightArray);
        arduino.writeBulk(combinedArray);
    }

    public void sendHatchStatusWithColor(char hatchStatus, Color color) {
        byte[] hatchStatusToSend = toByteArray(hatchStatus);
        byte[] combinedArray = combineArrays(hatchStatusToSend, new byte[] { (byte) color.getChar() });
        arduino.writeBulk(combinedArray);
    }

    public static byte[] combineArrays(byte[] a1, byte[] a2) {
        byte[] result = Arrays.copyOf(a1, a1.length + a2.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }

    private static byte[] toByteArray(int i) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(i);
        return buffer.array();
    }

    public static enum Color {
        RED('r'), BLUE('b'), YELLOW('y'), GREEN('g');

        private char asChar;

        Color(char colorChar) {
            for (Color color : values()) {
                if (color.getChar() == colorChar) {
                    throw new IllegalArgumentException("The character \"" + colorChar + "\" is already associated with another value in the LEDControl.Color enum. Please check the definition for the enum to resolve repitition.");
                }
            }
            this.asChar = colorChar;
        }
        public char getChar() {
            return asChar;
        }
    
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}