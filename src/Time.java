public class Time {
    static int timeSignature = 0;
    final static int QUAVER = 1;
    final static int CROTCHET = 2;
    final static int MINIM = 4;
    final static int SEMIBREVE = 8;
    static int[] noteLengths = new int[]{QUAVER, CROTCHET, MINIM, SEMIBREVE};
    int noteDuration;

    public static void setTimeSignature(int time) {
        timeSignature = time;
    }

    public static int getTimeSignature() {
        return timeSignature;
    }

    public  int getNoteDuration(){
        return noteDuration;
    }

    public void setNoteDuration(int duration){

        switch (duration){
            case QUAVER:
                this.noteDuration = 250; //Quarter Second
                break;
            case CROTCHET:
                this.noteDuration = 500; // Half Second
                break;
            case MINIM:
                this.noteDuration = 1000; // 1 Second
                break;
            case SEMIBREVE:
                this.noteDuration = 2000; // 2 Second
                break;
        }

    }
}
