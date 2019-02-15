import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	private boolean up=false,down=false,left=false,right=false,spaceReleased=false,powerCalculate=false,weaponUp=false, weaponDown=false,directionChanged = false;
	private int fighterDirection = 1; //can have values 1 : look right or -1 : look left;
	private boolean sound=false;
	
	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}
	private final float MIN_ARROW_POWER = 10;
	private final float MAX_ARROW_POWER = 25;	
	private float arrowPower = MIN_ARROW_POWER;
	
	public boolean isDirectionChanged() {
		return directionChanged;
	}

	public void setDirectionChanged(boolean directionChanged) {
		this.directionChanged = directionChanged;
	}

	public boolean isWeaponUp() {
		return weaponUp;
	}

	public void setWeaponUp(boolean weaponUp) {
		this.weaponUp = weaponUp;
	}

	public boolean isWeaponDown() {
		return weaponDown;
	}
	
	public void changeFighterDirection() {
		fighterDirection*=-1;
	}

	public int getFighterDirection() {
		return fighterDirection;
	}

	public void setFighterDirection(int fighterDirection) {
		this.fighterDirection = fighterDirection;
	}

	public void setWeaponDown(boolean weaponDown) {
		this.weaponDown = weaponDown;
	}
		
	public void setArrowPower(float arrowPower) {
		this.arrowPower = arrowPower;
	}
	
	public void calculateArrowPower() {
		if(arrowPower<MAX_ARROW_POWER) arrowPower+=0.5;
	}
	
	public float getArrowPower() {
		return arrowPower;
	}
	
	public void tick() {
		for(int i=0;i<object.size();i++) {
			object.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i=0;i<object.size();i++) {
			object.get(i).render(g);
		}		
	}
	
	public void addObject(GameObject tempObject) {
		object.add(tempObject);
	}
	
	public boolean isPowerCalculate() {
		return powerCalculate;
	}

	public void setPowerCalculate(boolean powerCalculate) {
		this.powerCalculate = powerCalculate;
	}

	public void removeObject(GameObject tempObject) {
		object.remove(tempObject);
	}
	
	public boolean isSpaceReleased() {
		return spaceReleased;
	}

	public void setSpaceReleased(boolean spaceReleased) {
		this.spaceReleased = spaceReleased;
	}

	
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	
	
	
	
}
