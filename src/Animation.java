import java.awt.image.BufferedImage;

public class Animation {
	
	private int speed;
	private BufferedImage[] bufferedImages;
	private int frames, count=0;
	private int currentImage=0;
	
	public Animation(int speed, BufferedImage... args) {
		this.speed = speed;
		this.frames = args.length;
		bufferedImages = new BufferedImage[frames];
		for(int i=0;i<args.length;i++) {
			bufferedImages[i] = args[i];
		}
	}
	
	public BufferedImage animate() {
		
		if(count >= speed) {
			count=0;
			if(currentImage<frames) {
				currentImage++;
			}
			
			if(currentImage>=frames) currentImage=0;
			
			return bufferedImages[currentImage];
		}
		else {
			count++;
			return bufferedImages[currentImage];
		}
			
	}
	
}
