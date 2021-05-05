import javax.sound.midi.*;
import java.util.HashMap;
import java.util.Random;

public class MidiTest{

    public static void main(String[] args) {
        try{
            /* Create a new Sythesizer and open it. Most of
             * the methods you will want to use to expand on this
             * example can be found in the Java documentation here:
             * https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html
             */
            Synthesizer midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

            //get and load default instrument and channel lists
            Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
            MidiChannel[] mChannels = midiSynth.getChannels();

            midiSynth.loadInstrument(instr[0]);//load an instrument


            //13 notes from c to c including black notes
            //Middle C is 60
            Random r = new Random();
            int num = r.nextInt();
            // 24 is start of piano at C
            // 107 is end of piano at B
            HashMap<String, Integer> notes = new HashMap<>();
            notes.put("C",1);
            notes.put("C#",2);
            notes.put("D",3);
            notes.put("D#",4);
            notes.put("E",5);
            notes.put("F",6);
            notes.put("F#",7);
            notes.put("G",8);
            notes.put("G#",9);
            notes.put("A",10);
            notes.put("A#",2);
            notes.put("B",12);

            //m is for multiplier and is used for picking where on the piano the melody should be played
            int mLeft = 3;
            int mRight = 4;
            while(true) {
                mChannels[0].noteOn(60, 20);//On channel 0, play note number 60 with velocity 100
                sleep(400);
                mChannels[0].noteOff(60);//turn of the note
                mChannels[0].noteOn(30, 20);
                sleep(800);
                mChannels[0].noteOff(30);//turn of the note
                sleep(200);
            }

        } catch (MidiUnavailableException e) {}
    }

    public static void sleep(int i){
        try {
            Thread.sleep(i);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}