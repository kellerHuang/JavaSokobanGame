import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Music {

	String path = new String("music/");
	String file = new String("casino.mid");
	
	private Sequence seq;
	private Sequencer midi;
	private boolean sign;
	
	public Music(){
		loadSound();
	}
	
	public void loadSound() {
		try{
			seq = MidiSystem.getSequence(new File(path+file));
			midi = MidiSystem.getSequencer();
			midi.open();
			midi.setSequence(seq);
			midi.start();
			midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		}
		catch(Exception e){
			
		}
		sign = true;
	}
	public void stopMusic(){
		midi.stop();
		midi.close();
		sign = false;
	}
	public Boolean isPlay(){
		return sign;
	}
	public void setMusic(String str){
		this.file = str;
	}
}
