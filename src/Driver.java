import javax.sound.midi.*;
import java.util.*;

public class Driver {
    Synthesizer midiSynth = MidiSystem.getSynthesizer();
    HashMap<String, Integer> notes = new HashMap<>();
    Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
    MidiChannel[] mChannels = midiSynth.getChannels();


    javax.sound.midi.Synthesizer synthesizer = MidiSystem.getSynthesizer();

    Random r;
    Scales scales = new Scales();
    UsingScales usingScales = new UsingScales();


    String[] scale = scales.getRandomScale();

    int chordAmount = 10;
    int melodyLength = 10;
    int chordSizeMax = 2;

    public Driver() throws MidiUnavailableException {
        init();


        //Pick scale to make a random song

        r = new Random();
        String[] melody = new String[20];
        ArrayList<ArrayList<String>> chords = new ArrayList<>();
        ArrayList<String> chord = new ArrayList<>();


        // List of chords
        for(int i = 0; i < chordAmount; i++){

            chord = buildChord();

            chords.add(chord);

            System.out.println(chord);
        }

        System.out.println("Chords# = "+chords.size());

        melody = createMelody();

        display(melody, chords);
        playMusic(melody, chords);

        //All below is synth work
        Instrument[] instruments = synthesizer.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = synthesizer.getChannels();

        synthesizer.loadInstrument(instruments[0]);


    }

    private void init() {
        try {
            midiSynth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        midiSynth.loadInstrument(instr[0]);//load an instrument
        notes.put("C",1);notes.put("C#",2);notes.put("D",3);
        notes.put("D#",4);notes.put("E",5);notes.put("F",6);
        notes.put("F#",7);notes.put("G",8);notes.put("G#",9);
        notes.put("A",10);notes.put("A#",2);notes.put("B",12);
    }

    private ArrayList<String> buildChord() {
        ArrayList<String> chord = new ArrayList<>();
        // List of Notes for Individual Chord
        int chordSize = r.nextInt(chordSizeMax)+2;
        System.out.println("chordSize = "+chordSize);
        int x = 0;
        while(chordSize > x) {
            int note = r.nextInt(8);


            if(!chord.contains(scale[note])){
                chord.add(scale[note]);
                x++;
                //chords[i][x] = aMajor[note];
            }
            Collections.sort(chord);

        }
        return chord;
    }

    private void playMusic(String[] melody, ArrayList<ArrayList<String>> chords) throws MidiUnavailableException {
        int sleepTime = 200;

        //Middle C is 60 // 24 is start of piano at C// 107 is end of piano at B

        for(int i = 0; i < melody.length; i++) {
            char note = melody[i].charAt(0);

            if(Character.isLowerCase(note)) {// lower case character inside a chord signifies that the note is in the next chord
                melody[i] = melody[i].toUpperCase();
                playNote(melody[i], sleepTime, 3);
            }else {
                playNote(melody[i], sleepTime, 2);
            }
       }
        for(int i = 0; i < chords.size(); i++){
            for(int x = 0 ; x < chords.get(i).size(); x++) {
                //chords.get(i).set(x , chords.get(i).get(x).toUpperCase());

            }
            playChord(chords.get(i), sleepTime,3);
        }
    }

    private void playNote(String note, int sleepTime, int multiplier){

        final int FIRST_NOTE = 24;
        System.out.println(note);
        mChannels[0].noteOn(FIRST_NOTE + (notes.get(note) + (12 * multiplier)), 20);//On channel 0, play note number 60 with velocity 100
        sleep(sleepTime);
        mChannels[0].noteOff(FIRST_NOTE + (notes.get(note) + (12 * multiplier)), 20);//turn off the note
    }


    private void playChord(List<String> chord, int sleepTime, int multiplier) throws MidiUnavailableException {
        final int FIRST_NOTE = 24;

        System.out.println("Chord: ");
        for(int i = 0; i < chord.size(); i++) {

            System.out.println(chord.get(i));

            char note = chord.get(i).charAt(0);

            if(Character.isLowerCase(note)) {// lower case character inside a chord signifies that the note is in the next chord
                chord.set(i, chord.get(i).toUpperCase());
                mChannels[0].noteOn(FIRST_NOTE + (notes.get(chord.get(i)) + (12 * (multiplier+1))), 20);
            }else {
                mChannels[0].noteOn(FIRST_NOTE + (notes.get(chord.get(i)) + (12 * multiplier)), 20);
            }
        }
        System.out.println();

        sleep(sleepTime);
        for(int i = 0; i < chord.size(); i++) {
            mChannels[0].noteOff(FIRST_NOTE + (notes.get(chord.get(i)) + 12 * multiplier));//turn off the note
        }
    }


    private String[] createMelody() {
        String[] melody = new String[melodyLength];
        for(int i = 0; i < melody.length; i++){
            int num = r.nextInt(8);
            melody[i] = scale[num];
        }
        return melody;
    }

    public void display(String[] melody, ArrayList<ArrayList<String>> chords) {
        String rightHand = "\n\nRight Hand: \n";
        rightHand += Arrays.toString(melody);
        String leftHand = "\n\nLeft Hand: \n";
        String temp = "";
        int i = 1;
        for (List<String> ch : chords) {
            temp += "Chord" + i++ + ": " + ch.toString() + "\n";
        }
        leftHand += temp;


        System.out.println("\n\nNew Song in A Major Scale: ");
        System.out.print(leftHand);
        System.out.print(rightHand);
    }

    public static void main(String[] args) throws MidiUnavailableException {
        new Driver();
    }


    public static void sleep(int i){
        try {
            Thread.sleep(i);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
