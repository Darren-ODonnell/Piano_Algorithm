import java.util.Arrays;
import java.util.Random;

public class MelodyBuilder {
    private final Random r;
    private final Scales scales;
    private String[] scale;
    private int melodyLength;
    private int timeSignature;
    private Time time;

    public MelodyBuilder(Scales scales, String[] scale, Random r, int melodyLength, int timeSignature, Time time) {
        this.scales = scales;
        this.scale = scale;
        this.r = r;
        this.melodyLength = melodyLength;
        this.timeSignature = timeSignature;
        this.time = time;
    }

    public void setScale(String[] scale) {
        this.scale = scale;
    }

    public String[] createMelody() {
        String[] melody = new String[melodyLength];
        String[] temp = scales.getArpeggio(scale);

        for (int i = 0; i < melody.length; i++) {
            if (i % (timeSignature * 2) < 4) {
                melody[i] = temp[i % timeSignature];

            } else {
                int num = r.nextInt(8);
                melody[i] = scale[num];
            }
        }

        return melody;
    }

    public String[][] getQuavers(String[] notes) {
        int quaver = 0;
        return method(notes, quaver);
    }

    public String[][] getCrotchets(String[] notes) {
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

    private String[][] method(String[] notes, int note) {
        int timeCounter = notes.length / Time.timeSignature / Time.noteLengths[note];
        String[][] melody = new String[timeCounter][Time.timeSignature];

        // set the Time instance's note duration so callers (Driver) will sleep correctly
        if (time != null) {
            time.setNoteDuration(Time.noteLengths[note]);
        }

        for (int i = 0; i < timeCounter; i++) {
            for (int x = 0; x < Time.timeSignature / Time.noteLengths[note]; x++) {
                melody[i][x] = notes[(Time.timeSignature * i) + x];
            }
        }

        System.out.println("Melody : " + Arrays.deepToString(melody));
        return melody;
    }
}
