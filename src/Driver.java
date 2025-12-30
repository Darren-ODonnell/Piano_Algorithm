import com.google.gson.Gson;

import javax.sound.midi.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Driver {
    private final HashMap<String, Integer> notes = new HashMap<>();
    private MidiHelper midi;

    private final Random r;
    private ChordBuilder chordBuilder;
    private MelodyBuilder melodyBuilder;

    Time time;
    Scales scales = new Scales();


    private String[] scale = scales.getRandomScale();
    private int scaleNum = scales.scaleInUse;
    ArrayList<String> available;

    private int multiplierLeft = 2;
    private int multiplierRight = multiplierLeft+1;
    final int FIRST_NOTE = 24;
    private int timeSignature = 8;
    private int chordAmount = 8;
    private int melodyLength = 8;
    private int chordSizeMax = 2;

    ArrayList<ArrayList<String>> chords = new ArrayList<>();
    String[][] melodies;
    private List<Integer>leftMultipliers = new ArrayList<>();
    private List<Integer> rightMultipliers = new ArrayList<>();
    private List<String>extraNotes = new ArrayList<>();

    FileHandling fh = new FileHandling();
    CompleteMelody completeMelody = new CompleteMelody();

    boolean playRandom = true;

    public Driver() throws MidiUnavailableException {

        midi = new MidiHelper();
        init();
        initTiming();

    time = new Time();
    r = new Random();

    // initialize builders that encapsulate melody/chord logic
    chordBuilder = new ChordBuilder(r, scale, chordSizeMax);
    melodyBuilder = new MelodyBuilder(scales, scale, r, melodyLength, timeSignature, time);

        if(playRandom) {
            playRandomSong();
            saveMelodyToFile();
        }else {
            playFromMemory();
        }

    }

    private void playFromMemory() throws MidiUnavailableException {
        Gson gson = new Gson();
        try {
            completeMelody = gson.fromJson(new FileReader("melody.json"), CompleteMelody.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        playMusic(completeMelody.getChords(), completeMelody.getMelody(), completeMelody.getLeftMultipliers(), completeMelody.getRightMultipliers(), completeMelody.getExtraNotes(), completeMelody.getScaleNum());

    }

    private void saveMelodyToFile() {
        Gson gson = new Gson();
        completeMelody.setMelody(melodies);
        completeMelody.setChords(chords);
        completeMelody.setLeftMultipliers(leftMultipliers);
        completeMelody.setRightMultipliers(rightMultipliers);
        completeMelody.setExtraNotes(extraNotes);
        completeMelody.setScaleNum(scaleNum);


        try (FileWriter writer = new FileWriter("melody.json")) {
            gson.toJson(completeMelody, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playRandomSong() throws MidiUnavailableException {
        //Pick scale to make a random song
        String[] melody = new String[20];

        ArrayList<String> chord = new ArrayList<>();


        // List of chords
        for(int i = 0; i < chordAmount; i++){
            chord = chordBuilder.buildChord();
            chords.add(chord);
        }

        melody = melodyBuilder.createMelody();
        melodies = melodyBuilder.getQuavers(melody);

        display(melodies, chords);
        playMusic(melodies, chords);

        displayExtras();
    }

    private void displayExtras() {
        System.out.println("Left Multipliers: " + leftMultipliers.toString());
        System.out.println("Right Multipliers: " + rightMultipliers.toString());
        System.out.println("Extra Notes: " + extraNotes.toString());
    }

    // MIDI initialization is handled by MidiHelper

    private void initTiming(){
        Time.setTimeSignature(timeSignature);
        //Chord Amount Stays at 4 because i want 1 chord per bar
        melodyLength *= timeSignature; //8 notes per signature
    }

    private void init() {
        // notes map
        notes.put("C",1);notes.put("C#",2);notes.put("D",3);
        notes.put("D#",4);notes.put("E",5);notes.put("F",6);
        notes.put("F#",7);notes.put("G",8);notes.put("G#",9);
        notes.put("A",10);notes.put("A#",2);notes.put("B",12);
    }

    // Chord construction is handled by ChordBuilder


    private void playNote(String note, int multiplier){
        System.out.println(note);
        int n = notes.get(note);
        midi.noteOn(FIRST_NOTE + (n + (12 * multiplier)), 30);//On channel 0, play note number with velocity 30
    }
    private void endNote(String note, int multiplier){
                int n = notes.get(note);
                midi.noteOff(FIRST_NOTE + (n + (12 * multiplier)), 30);//turn off the note

    }

    private void endChord(List<String> chord){
        for(int i = 0; i < chord.size(); i++) {
            int n = notes.get(chord.get(i));
            midi.noteOff(FIRST_NOTE + n);
        }

    }
    private void playChord(List<String> chord, int multiplier) throws MidiUnavailableException {
        System.out.print("\n" + "chord: ");
        for(int i = 0; i < chord.size(); i++) {

            System.out.print(chord.get(i) + " ");

            char note = chord.get(i).charAt(0);

            if(Character.isLowerCase(note)) {// lower case character inside a chord signifies that the note is in the next chord
                chord.set(i, chord.get(i).toUpperCase());
                int n = notes.get(chord.get(i));
                midi.noteOn(FIRST_NOTE + (n + (12 * (multiplier+1))), 30);

            }else {
                int n = notes.get(chord.get(i));
                midi.noteOn(FIRST_NOTE + (n + (12 * multiplier)), 30);
            }
        }
        System.out.println();
        sleep(time.getNoteDuration());//In to distinguish the chord from the next node played in melody

    }

    private void randomiseMultiplier() {
        // In place to ensure the song has balance, returning to 2-3 on each iteration allows for a well-sounding random distribution
        if(multiplierLeft > 3) {
            multiplierLeft = 3;
        }else{
            multiplierLeft = 2;
        }

        // + and - randomizers allows for more centered randomness
        multiplierLeft = multiplierLeft + r.nextInt(multiplierLeft) - r.nextInt(multiplierLeft);
        if(multiplierLeft >=5){// The 5th octave and up is too high pitched, this reduces how often it is played
            multiplierLeft -= 1;
        }
        multiplierRight = multiplierLeft + r.nextInt(2) + 1;
        leftMultipliers.add(multiplierLeft);
        rightMultipliers.add(multiplierRight);
        System.out.println("MultiplierLeft(Chord) = " + multiplierLeft);
        System.out.println("MultiplierRight(Melody) = " + multiplierRight);

    }

    // Melody generation is handled by MelodyBuilder

    public void display(String[][] melody, ArrayList<ArrayList<String>> chords) {
        String rightHand = "\n\nRight Hand: \n";
        rightHand += Arrays.deepToString(melody);
        String leftHand = "\n\nLeft Hand: \n";
        leftHand += chords.toString();

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

    // Melody length/format conversion moved to MelodyBuilder

    private void playMusic(String[][] melody, ArrayList<ArrayList<String>> chords) throws MidiUnavailableException {

        //Middle C is 60 // 24 is start of piano at C// 107 is end of piano at B
        // Pre-compute extra notes aligned to the melody slots if not already present
        int totalSlots = 0;
        for (int i = 0; i < melody.length; i++) totalSlots += melody[i].length;
        if (extraNotes == null || extraNotes.size() != totalSlots) {
            extraNotes = chordBuilder.generateExtraNotes(chords, melody);
        }

        int globalIndex = 0;

        for(int set = 0; set < chords.size(); set++) {
            randomiseMultiplier();
            playChord(chords.get(set), multiplierLeft);

            for (int note = 0; note < melody[set].length; note++) {
                // Play precomputed extra note for this global slot, if any
                String extra = extraNotes.size() > globalIndex ? extraNotes.get(globalIndex) : "";
                if (!extra.isEmpty()) {
                    System.out.print("Extra note: ");
                    if (Character.isLowerCase(extra.charAt(0))) {
                        String sec = extra.toUpperCase();
                        playNote(sec, multiplierRight);
                    } else {
                        playNote(extra, multiplierRight-1);
                    }
                }

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
                    endNote(melody[set][note-2], multiplierRight);
                    endNote(melody[set][note-2], multiplierRight+1);
                }
                globalIndex++;
            }

            endChord(chords.get(set));
        }
    }

    private void playMusic(ArrayList<ArrayList<String>> chords, String[][] melodies, List<Integer> multipliersLeft,
                           List<Integer> multipliersRight, List<String> extraNotes,
                            int scaleSelected) throws MidiUnavailableException {

        String secondaryNoteChr = "";
        scale = scales.getScale(scaleSelected);
        time.setNoteDuration(Time.QUAVER);
        int timeCount = 0;
        for(int chordNum = 0; chordNum < chords.size(); chordNum++){
            playChord(chords.get(chordNum), multipliersLeft.get(chordNum));

                for(int note = 0; note < melodies[chordNum].length; note++){

                    //Play extra note

                    // if(!extraNotes.get(timeCount).equals("")){// "" marks the pauses between extra notes
                    //     System.out.print("Extra note: ");

                    //     if(Character.isLowerCase(extraNotes.get(timeCount).charAt(0))){
                    //         secondaryNoteChr = extraNotes.get(timeCount).toUpperCase();
                    //         playNote(secondaryNoteChr, multiplierRight);
                    //     }else{
                    //         secondaryNoteChr = extraNotes.get(timeCount);
                    //         playNote(secondaryNoteChr, multiplierRight-1);
                    //     }
                    // }
                    sleep(time.getNoteDuration());

                    playNote(melodies[chordNum][note],multipliersRight.get(chordNum));

                    if(note > 2 && note < melodies[chordNum].length){
                        endNote(melodies[chordNum][note-2], multiplierRight);
                        endNote(melodies[chordNum][note-2], multiplierRight+1);
                    }
                    timeCount ++;
                }
            if(!secondaryNoteChr.isEmpty()) {
                endNote(secondaryNoteChr, multiplierRight);
                endNote(secondaryNoteChr, multiplierRight+1);
            }
            endChord(chords.get(chordNum));
        }
    }

}
