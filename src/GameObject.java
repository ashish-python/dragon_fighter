import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected int x=0,y=0;
	protected float velX=0,velY=0;
	protected boolean falling=true,jumping=false;
	protected SpriteSheet ss;
	
	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public ID id;
	
	public GameObject(int x, int y, ID id, SpriteSheet ss) {
		this.x = x;
		this.y=y;
		this.id=id;
		this.ss = ss;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	
}
