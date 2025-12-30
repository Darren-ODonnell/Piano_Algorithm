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
