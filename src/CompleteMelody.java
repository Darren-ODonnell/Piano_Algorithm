import java.util.ArrayList;
import java.util.List;

public class CompleteMelody {
//    Chords: [[D, F], [B, D, G], [D, a], [E, a], [C, F, a], [C, G], [C, F, a], [B, F]]
//    Melody : [[A, C, E, a, E, D, G, E], [F, G, a, B, F, A, A, E], [A, C, E, a, D, G, A, A], [B, a, a, G, a, A, G, B], [A, C, E, a, D, F, a, A], [D, E, C, D, B, B, C, D], [A, C, E, a, E, F, a, E], [G, F, a, A, A, G, B, F]]
//    Left Multipliers: [2, 2, 3, 3, 2, 2, 1, 3]
//    Right Multipliers: [4, 4, 5, 4, 3, 3, 3, 4]
//    Extra Notes: [, A, , A, , , , , , , , A, , , , , , , , E, , , , A, , , , B, , C, , , , C, A, E, D, , , , , , , , C, G, , , , , , , , B, B, D, , , , , , , , D]
//    Scale Num = 11

    ArrayList<ArrayList<String>> chords = new ArrayList<>();
    String[][] melody;
    List<Integer> leftMultipliers = new ArrayList<>();
    List<Integer> rightMultipliers = new ArrayList<>();
    List<String> extraNotes = new ArrayList<>();
    int scaleNum;

    public CompleteMelody() {
    }

    public CompleteMelody(ArrayList<ArrayList<String>> chords, String[][] melody, List<Integer> leftMultipliers, List<Integer> rightMultipliers, List<String> extraNotes, int scaleNum) {
        this.chords = chords;
        this.melody = melody;
        this.leftMultipliers = leftMultipliers;
        this.rightMultipliers = rightMultipliers;
        this.extraNotes = extraNotes;
        this.scaleNum = scaleNum;
    }

    public ArrayList<ArrayList<String>> getChords() {
        return chords;
    }

    public void setChords(ArrayList<ArrayList<String>> chords) {
        this.chords = chords;
    }

    public String[][] getMelody() {
        return melody;
    }

    public void setMelody(String[][] melody) {
        this.melody = melody;
    }

    public List<Integer> getLeftMultipliers() {
        return leftMultipliers;
    }

    public void setLeftMultipliers(List<Integer> leftMultipliers) {
        this.leftMultipliers = leftMultipliers;
    }

    public List<Integer> getRightMultipliers() {
        return rightMultipliers;
    }

    public void setRightMultipliers(List<Integer> rightMultipliers) {
        this.rightMultipliers = rightMultipliers;
    }

    public List<String> getExtraNotes() {
        return extraNotes;
    }

    public void setExtraNotes(List<String> extraNotes) {
        this.extraNotes = extraNotes;
    }

    public int getScaleNum() {
        return scaleNum;
    }

    public void setScaleNum(int scaleNum) {
        this.scaleNum = scaleNum;
    }
}
