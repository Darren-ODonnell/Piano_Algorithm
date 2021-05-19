import java.util.ArrayList;
import java.util.List;

public class CompleteMelody {
//    Chords: [[D, F], [B, D, G], [D, a], [E, a], [C, F, a], [C, G], [C, F, a], [B, F]]
//    Melody : [[A, C, E, a, E, D, G, E], [F, G, a, B, F, A, A, E], [A, C, E, a, D, G, A, A], [B, a, a, G, a, A, G, B], [A, C, E, a, D, F, a, A], [D, E, C, D, B, B, C, D], [A, C, E, a, E, F, a, E], [G, F, a, A, A, G, B, F]]
//    Left Multipliers: [2, 2, 3, 3, 2, 2, 1, 3]
//    Right Multipliers: [4, 4, 5, 4, 3, 3, 3, 4]
//    Extra Notes: [, A, , A, , , , , , , , A, , , , , , , , E, , , , A, , , , B, , C, , , , C, A, E, D, , , , , , , , C, G, , , , , , , , B, B, D, , , , , , , , D]
//    Scale Num = 11

    List<List<String>> chords = new ArrayList<>();
    List<List<String>> melody = new ArrayList<>();
    List<Integer> leftMultipliers = new ArrayList<>();
    List<Integer> rightMultipliers = new ArrayList<>();
    List<String> extraNotes = new ArrayList<>();
    int scaleNum;

    public CompleteMelody(List<List<String>> chords, List<List<String>> melody, List<Integer> leftMultipliers, List<Integer> rightMultipliers, List<String> extranotes, int scaleNum) {
        this.chords = chords;
        this.melody = melody;
        this.leftMultipliers = leftMultipliers;
        this.rightMultipliers = rightMultipliers;
        this.extraNotes = extranotes;
        this.scaleNum = scaleNum;
    }


}
