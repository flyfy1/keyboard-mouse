package me.songyy;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created by songyy on 5/1/15.
 */
public class MyHotKeyListener implements HotKeyListener {

    private static final int SCREEN_WIDTH, SCREEN_HEIGHT;
    private final PreviousKey previousKey;
    private final Robot bot;

    static {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = dim.width;
        SCREEN_HEIGHT = dim.height;
    }

    public MyHotKeyListener(PreviousKey previousKey, Robot bot) throws AWTException {
        this.previousKey = previousKey;
        this.bot = new Robot();
    }

    private static void mouseClick(Robot bot, int mouseKey) {
        bot.mousePress(mouseKey);
        bot.mouseRelease(mouseKey);
    }

    @Override
    public void onHotKey(HotKey key) {
        Point currentLocation = MouseInfo.getPointerInfo().getLocation();

        int speed = previousKey.getCurrentSpeed(key.keyStroke);
        previousKey.update(key.keyStroke, speed);
        updateLocationIfNeedToMove(currentLocation, key.keyStroke.getKeyCode(), speed);

        switch (key.keyStroke.getKeyCode()) {
            case 'H':
            case 'J':
            case 'K':
            case 'L':
                bot.mouseMove(currentLocation.x, currentLocation.y);
                break;
            case 'U':
                mouseClick(bot, InputEvent.BUTTON1_MASK);
                break;
            case 'I':
                mouseClick(bot, InputEvent.BUTTON2_MASK);
                break;
            case 'O':
                mouseClick(bot, InputEvent.BUTTON3_MASK);
                break;
        }
    }

    private void updateLocationIfNeedToMove(Point currentLocation, int keyStroke, int speed) {
        switch (keyStroke) {
            case 'H':
                currentLocation.x -= speed;
                break;
            case 'J':
                currentLocation.y += speed;
                break;
            case 'K':
                currentLocation.y -= speed;
                break;
            case 'L':
                currentLocation.x += speed;
                break;
        }

        if (currentLocation.x < 0) currentLocation.x = 0;
        else if (currentLocation.y < 0) currentLocation.y = 0;
        else if (currentLocation.x > SCREEN_WIDTH) currentLocation.x = SCREEN_WIDTH;
        else if (currentLocation.y > SCREEN_HEIGHT) currentLocation.y = SCREEN_HEIGHT;
    }
}
