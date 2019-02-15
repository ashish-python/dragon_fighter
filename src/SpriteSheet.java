import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getSpriteSection(int x,int y,int h,int w) {
		return image.getSubimage(x*32-32, y*32-32, w, h);
	}
	
	
}
