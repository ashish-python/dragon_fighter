import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundFiles {
	static AudioInputStream arrowSound; 
	Clip clip;
	
	public void play(String path) {
		try {
			arrowSound = AudioSystem.getAudioInputStream(new File(path));
			clip = AudioSystem.getClip();
			clip.open(arrowSound);
			System.out.println("aaaa");
			clip.setFramePosition(0);
			clip.start();
		}
		catch(Exception e) {
			System.out.println("Cannot play file");
		}
	}
	
}
