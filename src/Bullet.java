import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject{
	
	private float height, power, weaponAngle;
	private boolean inAir = false;
	private Handler handler;
	private int bulletSpeed = 10,counter;
	private BufferedImage[] bowArrowAnimations = new BufferedImage[3]; 
	private SoundFiles sound = new SoundFiles();
	
	private float theta, x2,y2;
	
	public Bullet(int x, int y, ID id, Handler handler,SpriteSheet ss, float power, float weaponAngle) {
		super(x, y, id, ss);
		this.handler = handler;
		this.power = power;
		this.weaponAngle = weaponAngle;
		System.out.println(weaponAngle);
		

		//bow and arrow
		bowArrowAnimations[0] = ss.getSpriteSection(1, 2, 32, 32);
		bowArrowAnimations[1] = ss.getSpriteSection(2, 2, 32, 32);
		bowArrowAnimations[2] = ss.getSpriteSection(3, 2, 32, 32);
		
	}

	
	public void tick() {
		
		x2=x;
		y2=y;
		
		x+=velX;
		y+=velY;
		
		//gravity
		velY+=0.5;
		
		
		velX = power*(float)Math.cos(Math.toRadians(-weaponAngle));
		
		
		if(!inAir) {
		velY = -(power*(float)Math.sin(Math.toRadians(-weaponAngle)));
		inAir = true;
		}
		


		
		
		for(int i = 0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Brick) {
				if(tempObject.getBounds().intersects(getBounds()))
					handler.removeObject(this);
					
				
			}
		}		
	}

	
	public void render(Graphics g) {
		//g.setColor(Color.red);
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		int angle = (int)(Math.atan((x-x2)/(1+x2*x))); 
        g2d.rotate(Math.toRadians(angle), x+4, y+17);
    
        g2d.drawImage(bowArrowAnimations[2],x,y,null);
        g2d.setTransform(old);
		
		
		//g.fillRect(x, y, 5, 5);
	}

	
	public Rectangle getBounds() {
		return new Rectangle(x,y,10,10);
	}

}
