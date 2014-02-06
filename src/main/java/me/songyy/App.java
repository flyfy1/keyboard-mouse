package me.songyy;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.KeyStroke;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

class PreviousKey{
	public static final int DEFAULT_SPEED = 1;
	public static final int FAST_SPEED_MUL = 10;
	public static final int TimeIntervalToSpeedUp = 500;
	
	Date strokeTime;
	KeyStroke key;
	int speed;
	
	public static final int MAX_SPEED;
	static{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		MAX_SPEED = Math.min(dim.height / 4, dim.width / 4);
	}
	
	public int getCurrentSpeed(KeyStroke currentStroke){
		if(key == null) return DEFAULT_SPEED;
		
		int speed;
		if((currentStroke.getModifiers() & this.key.getModifiers()) == this.key.getModifiers()
				&& currentStroke.getKeyCode() == this.key.getKeyCode()){
			speed = this.speed;
		} else if((currentStroke.getModifiers() & KeyEvent.META_DOWN_MASK) != 0){
			speed = FAST_SPEED_MUL * DEFAULT_SPEED;
		} else{
			speed = DEFAULT_SPEED;
		}
		
		int diff = (int) ((new Date()).getTime() - strokeTime.getTime());
		
		if(diff < TimeIntervalToSpeedUp){
			speed += speed;
		} else{
			System.out.println("diff = " + diff);
		}
		
		if(speed > MAX_SPEED) speed = MAX_SPEED;
		return speed;
	}

	public void update(KeyStroke keyStroke, int speed) {
		this.key = keyStroke;
		this.speed = speed;
		this.strokeTime = new Date();
	}
}

/**
 * Hello world!
 *
 */
public class App 
{
	private static final int SCREEN_WIDTH, SCREEN_HEIGHT;
	static{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_WIDTH = dim.width; SCREEN_HEIGHT = dim.height;
	}
	
	private static void mouseClick(Robot bot, int mouseKey){
		bot.mousePress(mouseKey);
		bot.mouseRelease(mouseKey);
	}
    public static void main( String[] args ) throws AWTException
    {
    	Provider provider = Provider.getCurrentProvider(false);
    	final PreviousKey previousKey = new PreviousKey();
    	final Robot bot = new Robot();
    	
    	HotKeyListener keyListener = new HotKeyListener() {
			@Override
			public void onHotKey(HotKey key) {
				Point currentLocation = MouseInfo.getPointerInfo().getLocation();
				
				int speed = previousKey.getCurrentSpeed(key.keyStroke);
				previousKey.update(key.keyStroke,speed);
				updateLocationIfNeedToMove(currentLocation,key.keyStroke.getKeyCode(),speed);
				
				switch(key.keyStroke.getKeyCode()){
				case 'H':case 'J':case 'K': case 'L':
					bot.mouseMove(currentLocation.x, currentLocation.y);
					break;
				case 'U':
					mouseClick(bot,InputEvent.BUTTON2_MASK);
					break;
				case 'I':
					mouseClick(bot,InputEvent.BUTTON3_MASK);
					break;
				}
			}

			private void updateLocationIfNeedToMove(Point currentLocation, int keyStroke,int speed) {
				switch(keyStroke){
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
				
				if(currentLocation.x < 0)	currentLocation.x = 0;
				else if(currentLocation.y < 0) currentLocation.y = 0;
				else if(currentLocation.x > SCREEN_WIDTH) currentLocation.x = SCREEN_WIDTH;
				else if(currentLocation.y > SCREEN_HEIGHT) currentLocation.y = SCREEN_HEIGHT;
			}
		};
		
    	
		String directionKeys = "HJKL";
		for(int i = 0 ; i < directionKeys.length(); i++){
			provider.register(KeyStroke.getKeyStroke(directionKeys.charAt(i),
					KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK),keyListener);
			provider.register(KeyStroke.getKeyStroke(directionKeys.charAt(i),
					KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK | KeyEvent.META_DOWN_MASK),keyListener);
		}
		
		String clickKeys = "UIO";
		for(int i = 0 ; i < clickKeys.length(); i++){
			provider.register(KeyStroke.getKeyStroke(clickKeys.charAt(i),
					KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK),keyListener);
		}
    }
}
