import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class DragonFire extends GameObject {
	
	private Handler handler;
	private int fighterX,fighterY;
	private float velX,velY;
	private BufferedImage image;
	private SoundFiles sound = new SoundFiles();

	public DragonFire(int x, int y, ID id, Handler handler, SpriteSheet ss, int fighterX, int fighterY) {
		super(x, y, id, ss);
		this.handler = handler;
		this.fighterX = fighterX;
		this.fighterY = fighterY;
		
		velX=(fighterX-x)/80;
		velY=(fighterY-y)/80;
		
		image = ss.getSpriteSection(1, 4, 10, 10);
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		
		
		//velX=(fighterX-x)/20;
		//velY=(fighterY-y)/20;
		
		for(int i=0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.id == ID.Brick) {
				if(tempObject.getBounds().intersects(getBounds())) {
					handler.object.remove(this);
					if(handler.isSound()) sound.play(".//res//sounds//explosion_x.wav");
				}
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		//g.drawRect(x, y, 10, 10);
		g.drawImage(image,x,y,null);
		
		
		
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y,10,10);
	}
	
}
