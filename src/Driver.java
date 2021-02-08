import com.sun.java.swing.plaf.windows.WindowsDesktopIconUI;

import java.util.*;

public class Driver {
    Random r;
    Scales scales = new Scales();
    UsingScales usingScales = new UsingScales();


    String[] aMajor = scales.getaMajor();
    String[] aMajorArpeggio = usingScales.getArpeggio(aMajor);
    int chordAmount = 5;
    int chordSizeMax = 2;

    public Driver(){

        System.out.println("A Major Arpeggio: " + Arrays.toString(aMajorArpeggio));

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

    }

    private ArrayList<String> buildChord() {
        ArrayList<String> chord = new ArrayList<>();
        // List of Notes for Individual Chord
        int chordSize = r.nextInt(chordSizeMax)+2;
        System.out.println("chordSize = "+chordSize);
        int x = 0;
        while(chordSize > x) {
            int note = r.nextInt(8);


            if(!chord.contains(aMajor[note])){
                chord.add(aMajor[note]);
                x++;
                //chords[i][x] = aMajor[note];
            }
            Collections.sort(chord);

        }
        return chord;
    }


    private String[] createMelody() {
        String[] melody = new String[20];
        for(int i = 0; i < melody.length; i++){
            int num = r.nextInt(8);
            melody[i] = aMajor[num];
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

    public static void main(String[] args) {
        new Driver();
    }
}
