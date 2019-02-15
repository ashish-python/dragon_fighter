import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i=0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block) {
				if(key == KeyEvent.VK_UP) handler.setUp(true);
				if(key == KeyEvent.VK_DOWN) handler.setDown(true);
				if(key == KeyEvent.VK_LEFT) handler.setLeft(true);
				if(key == KeyEvent.VK_RIGHT) handler.setRight(true);
				
				if(key == KeyEvent.VK_SPACE) {					
					handler.setSpaceReleased(false);
					handler.setPowerCalculate(true);
				}
				
				if(key== KeyEvent.VK_W) {handler.setWeaponUp(true);}
				if(key== KeyEvent.VK_S) {handler.setWeaponDown(true);}
				
				if(key==KeyEvent.VK_A) 
				{
					if(!handler.isDirectionChanged()) {
						handler.changeFighterDirection();
						handler.setDirectionChanged(true);
					}
					
				}
				
				
			}	
			
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i=0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block) {
				if(key == KeyEvent.VK_UP) handler.setUp(false);
				if(key == KeyEvent.VK_DOWN) handler.setDown(false);
				if(key == KeyEvent.VK_LEFT) handler.setLeft(false);
				if(key == KeyEvent.VK_RIGHT) handler.setRight(false);
				
				if(key == KeyEvent.VK_SPACE) {handler.setSpaceReleased(true);handler.setPowerCalculate(false);}
				
				if(key== KeyEvent.VK_W) {handler.setWeaponUp(false);}
				if(key== KeyEvent.VK_S) {handler.setWeaponDown(false);}
				if(key==KeyEvent.VK_A) {handler.setDirectionChanged(false);}
				
				if(key==KeyEvent.VK_P) {
					if(handler.isSound())
						handler.setSound(false);
					else 
						handler.setSound(true);{
						
					}
				}
				
			}
						
		}
	}
	
}
