import java.util.Random;

public class Time {
    static int timeSignature = 0;
    final static int QUAVER = 1;
    final static int CROTCHET = 2;
    final static int MINIM = 4;
    final static int SEMIBREVE = 8;
    static int[] noteLengths = new int[]{QUAVER, CROTCHET, MINIM, SEMIBREVE};
    static int[] noteDurations = new int[]{250, 500, 1000, 2000};
    int noteDuration;
    int secondaryNoteDuration;

    public static void setTimeSignature(int time) {
        timeSignature = time;
    }

    public static int getTimeSignature() {
        return timeSignature;
    }

    public int getNoteDuration(){
        return noteDuration;
    }

    public void setSecondaryNoteDuration(){
        Random r = new Random();
        int num = r.nextInt(4); // 1-4
        secondaryNoteDuration = noteDurations[num];
    }

    public int getSecondaryNoteDuration(){
        return this.secondaryNoteDuration;
    }

    public void setNoteDuration(int duration){

        switch (duration){
            case QUAVER:
                this.noteDuration = noteDurations[0]; //Quarter Second
                break;
            case CROTCHET:
                this.noteDuration = noteDurations[1]; // Half Second
                break;
            case MINIM:
                this.noteDuration = noteDurations[2]; // 1 Second
                break;
            case SEMIBREVE:
                this.noteDuration = noteDurations[3]; // 2 Second
                break;
        }

    }
    public int getNoteDuration(int index){

        switch (index){
            case 0:
                //Quarter Second
                return noteDurations[0];
            case 1:
                // Half Second
                return noteDurations[1];
            case 2:
                // 1 second
                return noteDurations[2];
            case 3:
                // 2 Second
                return noteDurations[3];
        }
        return noteDurations[0];
    }
}
