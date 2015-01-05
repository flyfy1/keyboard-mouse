package me.songyy;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class PreviousKey {
    public static final int DEFAULT_SPEED = 1;
    public static final int FAST_SPEED_MUL = 10;
    public static final int TimeIntervalToSpeedUp = 500;
    public static final int MAX_SPEED;

    static {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        MAX_SPEED = Math.min(dim.height / 4, dim.width / 4);
    }

    Date strokeTime;
    KeyStroke key;
    int speed;

    public int getCurrentSpeed(KeyStroke currentStroke) {
        if (key == null) return DEFAULT_SPEED;

        int speed;
        if (currentStroke.getKeyCode() == this.key.getKeyCode()) {
            speed = this.speed;
        } else {
            speed = DEFAULT_SPEED;
        }

        int diff = (int) ((new Date()).getTime() - strokeTime.getTime());

        if (diff < TimeIntervalToSpeedUp) {
            speed += speed;
        } else {
            System.out.println("diff = " + diff);
        }

        if (speed > MAX_SPEED) speed = MAX_SPEED;
        return speed;
    }

    public void update(KeyStroke keyStroke, int speed) {
        this.key = keyStroke;
        this.speed = speed;
        this.strokeTime = new Date();
    }
}

