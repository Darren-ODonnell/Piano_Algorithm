import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChordBuilder {
    private final Random r;
    private String[] scale;
    private int chordSizeMax;

    public ChordBuilder(Random r, String[] scale, int chordSizeMax) {
        this.r = r;
        this.scale = scale;
        this.chordSizeMax = chordSizeMax;
    }

    /**
     * Generate extra notes aligned with the melody slots.
     * Returns a list of length equal to total number of melody slots (sum of melodies[i].length).
     * Each entry is either "" (no extra) or a note string (may be lowercase to indicate next-chord placement).
     */
    public List<String> generateExtraNotes(List<ArrayList<String>> chords, String[][] melodies) {
        List<String> extras = new ArrayList<>();
        // total slots
        for (int c = 0; c < chords.size(); c++) {
            ArrayList<String> chord = chords.get(c);
            int slots = melodies[c].length;
            for (int s = 0; s < slots; s++) {
                double p = r.nextDouble();
                if (p < 0.30) { // 30% chance to place an extra note
                    String note;
                    if (chord.size() > 0 && r.nextDouble() < 0.7) {
                        // prefer chord tones
                        note = chord.get(r.nextInt(chord.size()));
                        // occasionally mark as lower-case to place it in the next chord
                        if (r.nextDouble() < 0.10) note = note.toLowerCase();
                    } else {
                        // pick a scale tone
                        note = scale[r.nextInt(scale.length)];
                    }
                    extras.add(note);
                } else {
                    extras.add("");
                }
            }
        }
        return extras;
    }

    public void setScale(String[] scale) {
        this.scale = scale;
    }

    public ArrayList<String> buildChord() {
        ArrayList<String> available = new ArrayList<>(Arrays.asList(scale));
        ArrayList<String> chord = new ArrayList<>();
        int chordSize = r.nextInt(chordSizeMax) + 2;
        int x = 0;
        while (x < chordSize) {
            chord = addToChord(chord, available);
            x++;
        }
        return chord;
    }

    private ArrayList<String> addToChord(ArrayList<String> chord, ArrayList<String> available) {
        String preNote;
        String postNote;
        boolean noChange = true;
        while (noChange) {
            int note = 0;
            if (available.size() > 0) {
                note = r.nextInt(available.size());
                String noteStr = available.get(note);
                int noteIndex = findIndex(noteStr);

                preNote = getNote(noteIndex - 1);
                postNote = getNote(noteIndex + 1);

                if (postNote != null) {
                    available.remove(postNote);
                }

                chord.add(available.get(note));
                available.remove(note);

                if (preNote != null) {
                    available.remove(preNote);
                }
            }
            noChange = false;
        }
        Collections.sort(chord);
        return chord;
    }

    private String getNote(int i) {
        if (i < 0 || i > 7) {
            return null;
        } else {
            return scale[i];
        }
    }

    private int findIndex(String noteStr) {
        for (int i = 0; i < scale.length; i++) {
            if (scale[i].equals(noteStr)) {
                return i;
            }
        }
        return -1;
    }
}
