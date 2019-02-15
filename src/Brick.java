import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Brick extends GameObject {
	private Handler handler;

	public Brick(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		
	}

	
	public void tick() {
		
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, 16, 16);
		
	}

	
	public Rectangle getBounds() {		
		return new Rectangle(x,y,16,16);
	}

}
