import javax.sound.midi.*;
import java.util.*;

public class Driver {
    private final Synthesizer midiSynth = MidiSystem.getSynthesizer();
    private final HashMap<String, Integer> notes = new HashMap<>();
    private final Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
    private final MidiChannel[] mChannels = midiSynth.getChannels();

    private final Random r;

    Time time;
    Scales scales = new Scales();
    UsingScales usingScales = new UsingScales();


    private String[] scale = scales.getRandomScale();
    ArrayList<String> available;

    private int multiplierLeft = 2;
    private int multiplierRight = multiplierLeft+1;
    final int FIRST_NOTE = 24;
    private int timeSignature = 8;
    private int chordAmount = 8;
    private int melodyLength = 8;
    private int chordSizeMax = 2;


    public Driver() throws MidiUnavailableException {
        init();
        initTiming();


        //Pick scale to make a random song
        time = new Time();
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

        String[][] melodies = getQuavers(melody);


        display(melodies, chords);
        playMusic(melodies, chords);



        //All below is synth work
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        Instrument[] instruments = synthesizer.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = synthesizer.getChannels();

        synthesizer.loadInstrument(instruments[0]);


    }

    private void initTiming(){
        Time.setTimeSignature(timeSignature);
        //Chord Amount Stays at 4 because i want 1 chord per bar
        melodyLength *= timeSignature; //8 notes per signature
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
        available = new ArrayList<>(Arrays.asList(scale));
        ArrayList<String> chord = new ArrayList<>();
        // List of Notes for Individual Chord
        int chordSize = r.nextInt(chordSizeMax)+2;
        System.out.println("chordSize = "+chordSize);
        int x = 0;

        while(x < chordSize) {
            chord = addToChord(chord);
            x++;
        }
        return chord;
    }
    private ArrayList<String> addToChord(ArrayList<String> chord){
        String preNote;
        String postNote;
        boolean noChange = true;
        while(noChange){
            int note = 0;
            if(available.size() > 0) {
                note = r.nextInt(available.size());
                String noteStr = available.get(note);
                int noteIndex = findIndex(noteStr);

                preNote = getNote(noteIndex-1);
                postNote = getNote(noteIndex+1);

                if(postNote != null){
                    available.remove(postNote);
                }

                chord.add(available.get(note));
                available.remove(note);

                if(preNote != null){
                    available.remove(preNote);
                }
            }
            noChange = false;
        }
        Collections.sort(chord);
        return chord;
    }

    private String getNote(int i) {
        if(i < 0 || i > 7) {
            return null;
        }else{
            return scale[i];
        }
    }

    private int findIndex(String noteStr) {
        for(int i = 0; i < scale.length; i++){
            if(scale[i].equals(noteStr)){
                return i;
            }
        }
        return -1;//never used
    }

    private void playMusic(String[][] melody, ArrayList<ArrayList<String>> chords) throws MidiUnavailableException {

        //Middle C is 60 // 24 is start of piano at C// 107 is end of piano at B

        for(int set = 0; set < chords.size(); set++) {
            playChord(chords.get(set), time.getNoteDuration() * 2, multiplierLeft);

                for (int note = 0; note < melody[set].length; note++) {
                    char noteChr = melody[set][note].charAt(0);

                    // lower case character inside a chord signifies that the note is in the next chord
                    if (Character.isLowerCase(noteChr)) {
                        melody[set][note] = melody[set][note].toUpperCase();
                        playNote(melody[set][note], multiplierRight+1);
                    } else {
                        playNote(melody[set][note], multiplierRight);
                    }

                    sleep(time.getNoteDuration());

                    // This allows the melody notes to flow into the next so it sounds less disjointed
                    if(note > 2 && note < melody[set].length){
                        endNote(melody[set][note-3], 4);
                        endNote(melody[set][note-3], 3);
                    }

                }
            endChord(chords.get(set));
        }
    }

    private void playNote(String note, int multiplier){
        System.out.println(note);
        mChannels[0].noteOn(FIRST_NOTE + (notes.get(note) + (12 * multiplier)), 30);//On channel 0, play note number 60 with velocity 100
    }
    private void endNote(String note, int multiplier){
                mChannels[0].noteOff(FIRST_NOTE + (notes.get(note) + (12 * multiplier)), 30);//turn off the note

    }

    private void endChord(List<String> chord){
        for(int i = 0; i < chord.size(); i++) {
            mChannels[0].noteOff(FIRST_NOTE + (notes.get(chord.get(i))));//turn off the note
        }

    }
    private void playChord(List<String> chord, int sleepTime, int multiplier) throws MidiUnavailableException {
        System.out.print("\n" + "chord: ");
        for(int i = 0; i < chord.size(); i++) {

            System.out.print(chord.get(i) + " ");

            char note = chord.get(i).charAt(0);

            if(Character.isLowerCase(note)) {// lower case character inside a chord signifies that the note is in the next chord
                chord.set(i, chord.get(i).toUpperCase());
                mChannels[0].noteOn(FIRST_NOTE + (notes.get(chord.get(i)) + (12 * (multiplier+1))), 30);

            }else {
                mChannels[0].noteOn(FIRST_NOTE + (notes.get(chord.get(i)) + (12 * multiplier)), 30);
            }
        }
        System.out.println();
        sleep(time.getNoteDuration());//In to distinguish the chord from the next node played in melody

        randomiseMultiplier();

    }

    private void randomiseMultiplier() {
        // In place to ensure the song has balance, returning to 2-3 on most instances allows for good random distribution
        if(multiplierLeft == 1){
            multiplierLeft += 2;
        }else if(multiplierLeft >= 4){
            multiplierLeft-= 2;
        }
        // + and - randomizers allows for more centered randomness so that it is less monotone
        multiplierLeft = multiplierLeft + r.nextInt(multiplierLeft) - r.nextInt(multiplierLeft);
        System.out.println("MultiplierLeft = " + multiplierLeft);
        multiplierRight = multiplierLeft + 1;
    }

    private String[] createMelody() {
        String[] melody = new String[melodyLength];
        String[] temp = scales.getArpeggio(scale);

        //To be continued
        /**
         * Add in a for loop around the rest so that an arpeggio plays every 2*timeSignature
         */

        for(int i = 0 ; i < melody.length; i++){
            if(i % (timeSignature*2) < 4) {
                melody[i] = temp[i % timeSignature];

            }else {
                int num = r.nextInt(8);
                melody[i] = scale[num];
            }
        }

        return melody;
    }

    public void display(String[][] melody, ArrayList<ArrayList<String>> chords) {
        String rightHand = "\n\nRight Hand: \n";
        rightHand += Arrays.toString(melody);
        String leftHand = "\n\nLeft Hand: \n";
        String temp = "";
        int i = 1;
        for (List<String> ch : chords) {
            temp += "Chord" + i++ + ": " + ch.toString() + "\n";
        }
        leftHand += temp;

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

    public String[][] getQuavers(String[] notes){
        int quaver = 0;
        return method(notes, quaver);
    }
    public String[][] getCrotchets(String[] notes){
        int crotchet = 1;
        return method(notes, crotchet);
    }
    public String[][] getMinims(String[] notes) {
        int minim = 2;
        return method(notes, minim);
    }
    public String[][] getSemiBreves(String[] notes) {
        int semibreve = 3;
        return method(notes, semibreve);
    }

    public String[][] method(String[] notes, int note){
        int timeCounter = notes.length/Time.timeSignature/Time.noteLengths[note];
        String[][] melody = new String[timeCounter][Time.timeSignature];
            time.setNoteDuration(Time.noteLengths[note]);// noteLength[0] is quaver

        /**
         *  Outer loop, needs to go 4 times
         *  Inner loop, goes 8 times for every iteration of the outer loop
         */
            for(int i = 0; i < timeCounter; i++){
            for(int x = 0; x < Time.timeSignature/Time.noteLengths[note]; x++){
                melody[i][x] = notes[(Time.timeSignature*i)+x];
//                System.out.println("note:" + notes[(Time.timeSignature*i)+x]);
//                System.out.println("Size of notes " + notes.length);
            }
        }

            System.out.println(Arrays.deepToString(melody));
            return melody;
    }




}
