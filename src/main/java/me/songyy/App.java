package me.songyy;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Provider provider = Provider.getCurrentProvider(false);
    	
    	HotKeyListener keyListener = new HotKeyListener() {
			@Override
			public void onHotKey(HotKey key) {
				System.out.println("Hot Key Pressed: " + key);
			}
		};
    	
		provider.register(KeyStroke.getKeyStroke('M',KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK),keyListener);
    	provider.register(KeyStroke.getKeyStroke('A',KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK,true),keyListener);
    	provider.register(KeyStroke.getKeyStroke('M',KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK,true),keyListener);
    	
    }
}
