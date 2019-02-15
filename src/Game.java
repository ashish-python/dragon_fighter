import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
	private Thread thread;
	private boolean isRunning = false;
	private Handler handler;
	private BufferedImage level,fighter,bow_arrow,brick;
	private Camera camera;
	private SpriteSheet ss;

	private enum GAMESTATE{PAUSE,GAME;}
	
	private GAMESTATE state = GAMESTATE.GAME;
	
	
	public Game() {
		new Window(812,675,"Kill Dragon",this);
		
	
		handler = new Handler();		
		
		BufferedImageLoader loader = new BufferedImageLoader();		
		level = loader.loadImage("/game_level.png");		
		fighter = loader.loadImage("/fighter_spreet.png");
		camera = new Camera(0,0);
		
		ss = new SpriteSheet(fighter);
		
		this.addKeyListener(new KeyInput(handler));	
	
		
		
		loadLevel(level);
		
		start();
		
	}
		
	private void start() {
			thread = new Thread(this);
			thread.start();
			isRunning = true;
		
	}
	
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		for(int xx=0;xx<w;xx++) {
			for(int yy=0;yy<h;yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel>>16) & 0xff;
				int green = (pixel>>8) & 0xff;
				int blue = pixel & 0xff;
				int yellow = (pixel>>8) & 0xFFFF;	
				
			
				
				
				if(green == 255 && red==0 && blue==0) {
					handler.addObject(new Brick(xx*16,yy*16,ID.Brick, handler, ss));
				}				
				
				if(blue == 255 && red==0 && green==0) {					
					handler.addObject(new Block(xx*16,yy*16,ID.Block, handler, ss));
				}
				
				if(green==255 && red==255 && blue==0) {
					
					handler.addObject(new Dragon(xx*16,
							yy*16,ID.Dragon,handler,ss));
				}
				
				if(green==255 && blue==255 && red==0) {
					handler.addObject(new InvisibleWall(xx*16,yy*16,ID.InvisibleWall,handler,ss));
				}
				
				
				
			}
		}
		
		
	}
	
	public void run() {
		//this code has been taken from an online tutorial
		//updates game values 60 times per minute
		//refreshes the screen 1000 times per minute
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta >=1) {
				tick();
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
		
	}
	
	
	
	public void tick() {
		if(state == GAMESTATE.GAME) {
			
			for(int i=0;i<handler.object.size();i++) {
				if(handler.object.get(i).getId() == ID.Block) {
					camera.tick(handler.object.get(i));
				}
			}
			
			handler.tick();
		}
		
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		
		g.setColor(Color.white);
		g.fillRect(0,0,810,670);
		
	
		g2d.translate(-camera.getX(),-camera.getY());
		
		g.setColor(Color.blue);
		g.drawRect(330, 35, 180, 32);
		g.setColor(Color.orange);
		g.setFont(new Font("Helvetica",Font.PLAIN,10));
		g.drawString("Arrow Power - press space to increase", 340, 80);
		
		g.setColor(Color.blue);
		g.setFont(new Font("Helvetica",Font.BOLD,10));
		g.drawString("Fighter power:", 600, 50);
		
		g.setColor(Color.darkGray);
		g.drawString("'A' to change player direction",80, 50);
		g.drawString("'W' Gun up",80, 65);
		g.drawString("'S' Gun down",80, 80);
		g.drawString("'P' Turn sound ON or OFF",80, 95);
		if(state == GAMESTATE.GAME) {
			handler.render(g);
		}
		g2d.translate(camera.getX(),camera.getY());
		////////
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args) {		
		new Game();		
	}
	

}
