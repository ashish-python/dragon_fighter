import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Block extends GameObject {

	private final float MAX_SPEED = 10;
	private Handler handler;
	private float gravity = 0.5f;
	private BufferedImage[] fighterAnimations = new BufferedImage[12];
	private BufferedImage currentImage;
	private int speed = 10;
	private Animation animationRight,animationLeft;
	private int width=32,height=32;
	private int weaponDirection = 1;
	private int fighterPower = 100;
	
	private int weaponAngle = 0;
	private int weaponEdgeCoord;
	private SoundFiles sound = new SoundFiles();
	
	
	public Block(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		
		//fighter looking right
		fighterAnimations[0] = ss.getSpriteSection(1,1,32,32);
		fighterAnimations[1] = ss.getSpriteSection(2, 1, 32, 32);	
		fighterAnimations[2] = ss.getSpriteSection(3, 1, 32, 32);
		fighterAnimations[3] = ss.getSpriteSection(4, 1, 32, 32);
		fighterAnimations[4] = ss.getSpriteSection(5, 1, 32, 32);
		
		//fighter bow and arrow
		fighterAnimations[5] = ss.getSpriteSection(1, 2, 32, 32);
		fighterAnimations[6] = ss.getSpriteSection(2, 2, 32, 32);
		
		//fighter looking left
		fighterAnimations[11] = ss.getSpriteSection(1,3,32, 32);		
		fighterAnimations[10] = ss.getSpriteSection(2, 3, 32, 32);	
		fighterAnimations[9] = ss.getSpriteSection(3, 3, 32, 32);
		fighterAnimations[8] = ss.getSpriteSection(4, 3, 32, 32);
		fighterAnimations[7] = ss.getSpriteSection(5, 3, 32, 32);
		
		
		animationRight = new Animation(speed,fighterAnimations[1],fighterAnimations[2],fighterAnimations[3],fighterAnimations[4]);
		animationLeft = new Animation(speed,fighterAnimations[8],fighterAnimations[9],fighterAnimations[10],fighterAnimations[11]);
	}

	
	public void tick() {
		x+=velX;
		y+=velY;
	
		
		if(falling || jumping) {			
			velY+=gravity;			
			if(velY > MAX_SPEED)
				velY = MAX_SPEED;			
		}
		
		if(handler.isUp() && !isJumping()) {
			velY=-10;
			setJumping(true);
		}
		
		
		if(handler.isLeft()) velX=-5;
		else if(!handler.isRight()) velX=0;
		
		if(handler.isRight()) velX=5;
		else if(!handler.isLeft()) velX=0;
		
		
		
		//Fire bullets
		if(handler.isSpaceReleased() && !handler.isPowerCalculate()) {
			//System.out.println(handler.getArrowPower());
			//handler.addObject(new Bullet((int)((x+16)+20*Math.cos(Math.toRadians(-weaponAngle))),(int)((y+1)-20*Math.sin(Math.toRadians(-weaponAngle))),ID.Bullet,handler,ss,handler.getArrowPower(),weaponAngle));
			handler.addObject(new Bullet(x+16,y+16,ID.Bullet,handler,ss,handler.getArrowPower(),weaponAngle));

			if(handler.isSound())sound.play(".//res//sounds//thunk.wav");
			
			handler.setSpaceReleased(false);
			handler.setArrowPower(10);			
		}
		
		if(!handler.isSpaceReleased() && handler.isPowerCalculate()) {
			handler.calculateArrowPower();
			handler.setPowerCalculate(false);
		}
		
		
		////If weapon direction has changed, change the weaponAngle for 1st and 4th quadrant
		if(weaponDirection!=handler.getFighterDirection()) {
			if(weaponDirection==-1) { //the fighter has turned left to right
				if(weaponAngle>-180) weaponAngle = -(180+weaponAngle);
				else if(weaponAngle==-180) weaponAngle = 0;
				else if(weaponAngle==-270) weaponAngle = 90;
				else weaponAngle = -(180+weaponAngle);					
				weaponDirection = 1;
				
			}
			else if(weaponDirection==1) { //the fighter has turned right to left
				if(weaponAngle<0) weaponAngle = -180-weaponAngle;
				else if(weaponAngle==90) weaponAngle = -270;
				else weaponAngle = -(180+weaponAngle);	
				weaponDirection = -1;
				
				
			}
								
		}
		
		if(handler.isWeaponUp()) {
			if(handler.getFighterDirection()==1) {							
				if(weaponAngle <= -90)
					weaponAngle = -90;
				else weaponAngle--;	
			
			}
			else {
				if(weaponAngle >= -90)
					weaponAngle = -90;
				else weaponAngle++;
				
			}
			
		}
		
		if(handler.isWeaponDown()) {
			if(handler.getFighterDirection()==1) {
				if(weaponAngle >= 90)
					weaponAngle = 90;
				else weaponAngle++;
			}
		else {
			if(weaponAngle <= -270)
				weaponAngle = -270;
				else weaponAngle--;
			}
			
		}
		
		//check collision
		collision();		
		
		//Fighter animation
		if(handler.getFighterDirection()>0) {
			if(velX==0) currentImage = fighterAnimations[0];
			else currentImage = animationRight.animate();					
		}
		else {
			if(velX==0) currentImage = fighterAnimations[7];
			else currentImage = animationLeft.animate();
			
		}
		


		
	}

	private void collision() {
		
		for(int i=0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Brick) {
				
				//top collision
				if(tempObject.getBounds().intersects(getBoundsTop())) {
					y = tempObject.getY() + 32;
					velY=0;		
				}
				
				//bottom collision
				if(tempObject.getBounds().intersects(getBoundsBottom())) {
					y = tempObject.getY() - 32;
					velY=0;
					setFalling(false);
					setJumping(false);			
				}
				else setFalling(true);				
				
				//left collision
				if(tempObject.getBounds().intersects(getBoundsLeft())) {
					x = tempObject.getX() + 32;
					velX=0;
				}
				
				// right collision
				
				if(tempObject.getBounds().intersects(getBoundsRight())) {
					x = tempObject.getX() - 32;
					velX=0;
				}
				
			}
		}
	}
	
	public void render(Graphics g) {
		
		
		
		//Draw fighter
		g.drawImage(currentImage, x, y,null);
		
		
		// Draw gun
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
				
		g2d.setColor(Color.gray);
		if(handler.getFighterDirection()>0) {
			g2d.rotate(Math.toRadians(weaponAngle),x+16,y+14);
			g2d.drawImage(fighterAnimations[5],x+12,y-2,null);
		}
		else {
			g2d.rotate(Math.toRadians(weaponAngle),x+11,y+14);
			g2d.drawImage(fighterAnimations[5],x+3,y-3,null);
		}
      
        g2d.setTransform(old);
		
		
		
		//Arrow power bar
		g.setColor(Color.blue);
		g.drawString(""+(10+(handler.getArrowPower()-10)*6)+"%",290,50);
		g.setColor(Color.red);
		g.fillRect(330, 35, (int)(handler.getArrowPower()-10)*12, 32);
		
		
		//Fighter power draw		
		g.setColor(Color.red);
		g.drawString(""+fighterPower, 690, 50);
		
		
		//fighter and bullet collision
		
		for(int i=0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.id == ID.DragonFire) {
				if(tempObject.getBounds().intersects(getBounds())) {
					handler.object.remove(tempObject);
					fighterPower-=20;
					if(fighterPower<=0) {
						//int userChoice = JOptionPane.showConfirmDialog(window, "Play again?", "Game over", JOptionPane.YES_NO_OPTION);
						
					}
					//System.out.println("HIT!");
					//if(handler.isSound()) sound.play(".//res//sounds//explosion_x.wav");
				}
			}
		}
		
		
		
		
		/*
		g.setColor(Color.red);
		g.fillRect(x, y,32, 32);
		g.setColor(Color.black);
		g.fillRect(x,y+5,5,22);
		g.fillRect(x+5,y+27,22,5);
		g.fillRect(x+5,y,22,5);
		g.fillRect(x+27,y+5,5,22);
		*/
	}
	

	public Rectangle getBounds() {
		
		return new Rectangle(x,y,32,32);
	}
	
	public Rectangle getBoundsTop() {
		
		return new Rectangle(x+5,y,22,5);
	}
	
	public Rectangle getBoundsBottom() {
		
		return new Rectangle(x+5,y+27,22,5);

	}
	
	public Rectangle getBoundsLeft() {		
		return new Rectangle(x,y+5,5,22);		
	}
	
	public Rectangle getBoundsRight() {		
		return new Rectangle(x+27,y+5,5,22);		
	}

}
