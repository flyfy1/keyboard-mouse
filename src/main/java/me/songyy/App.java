package me.songyy;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class App {
    private static void mouseClick(Robot bot, int mouseKey) {
        bot.mousePress(mouseKey);
        bot.mouseRelease(mouseKey);
    }

    public static void main(String[] args) throws AWTException {
        Provider provider = Provider.getCurrentProvider(false);
        final PreviousKey previousKey = new PreviousKey();
        final Robot bot = new Robot();

        HotKeyListener keyListener = new MyHotKeyListener(previousKey, bot);

        String keysToRegister = "HJKLUIO";
        for (int i = 0; i < keysToRegister.length(); i++) {
            provider.register(KeyStroke.getKeyStroke(keysToRegister.charAt(i),
                    KeyEvent.SHIFT_DOWN_MASK | KeyEvent.META_DOWN_MASK), keyListener);
        }

    }
}
