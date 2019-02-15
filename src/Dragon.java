import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Dragon extends GameObject{
	
	private BufferedImage dragonFaceLeft,dragonFaceRight,image;
	private SpriteSheet ss;
	private int width=32,height=32;
	private Handler handler;
	
	private int dragonMovement,fighterX,fighterY;
	private int count;
	private int toggle = -16;
	private boolean dragonHidden = false, dragonStopped = false;
	private String currentMove, lastMove;
	
	public Dragon(int x, int y, ID id, Handler handler,SpriteSheet ss) {
		super(x, y, id, ss);
		
		this.handler = handler;
		this.ss = ss;
		
		dragonFaceLeft = ss.getSpriteSection(4, 2, width,height);
		dragonFaceRight = ss.getSpriteSection(5, 2, width, height);
		
	}

	
	public void tick() {		
	
		x+=velX;
		
		//if(count<dragonMovement) {
			//count++;
		//}
		//else {
			//toggle*=-1;
			//x+=toggle;
			//x+=velX;
			//count=0;
			//dragonMovement = (int)(60* Math.random())+180;
			//dragon hidden or showing for at least 3 seconds
			
			//Check dragon face direction
			for(int i=0;i<handler.object.size();i++) 
			{
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getId()==ID.Block) 
				{
					this.fighterX = tempObject.getX();
					this.fighterY = tempObject.getY();
					
					if(tempObject.getX()<this.getX())
					{
						image = dragonFaceLeft;						
						currentMove="left";					
						
					}
					else if(tempObject.getX()>this.getX())
					{
						image = dragonFaceRight;
						currentMove="right";
										
					}
					else if(tempObject.getX()==this.getX())
					{
						currentMove="stop";
					}
					
				}			
			}
			
			//Walk dragon if required
			for(int i=0;i<handler.object.size();i++) 
			{
				GameObject tempObject = handler.object.get(i);
				if(tempObject.getId()==ID.InvisibleWall) 
				{
					if(tempObject.getBounds().intersects(getBounds())) {
						dragonStopped = true;
						velX=0;
					}
					
					if(lastMove!=currentMove)
						dragonStopped=false;
					//handler.addObject(new DragonFire(x,y,ID.DragonFire,handler,ss,tempObject.x,tempObject.y));
				}
				
				
			 }
			
			if(!dragonStopped) 
			{
			
				if(currentMove == "left")
					{velX=-1f;lastMove = currentMove;}
				else if(currentMove == "right")
					{velX=1f; lastMove = currentMove;}
				else if(currentMove == "stop")
				{
					velX=0;lastMove=currentMove;
				}
				
			}
			
			//dragon stopped moving, dragon can fire
			
			if(dragonStopped) {
				
				if(count<dragonMovement) 
				{
					count++;
				}
				else
				{
					count=0;
					dragonMovement = (int)(60* Math.random())+180;
					handler.addObject(new DragonFire(x,y,ID.DragonFire,handler,ss,fighterX,fighterY));
				}
				
			}
			
		
		
	}

	
	public void render(Graphics g) {
		
		g.drawImage(image, x,y,null);
		
		
		
	}

	
	public Rectangle getBounds() {
		
		return new Rectangle(x,y,height,width);
	}
	
	

}
