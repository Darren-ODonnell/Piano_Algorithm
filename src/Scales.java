import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Scales {
    //# = sharp(up to right)

    private final String[] cMinor = new String[]{"C","D","D#","F","G","g#","a#","C"};
    private final String[] cMajor = new String[]{"C","D","E","F","G","a","b","c"};
    private final String[] dMinor = new String[]{"D","E","F","G","a","b","c#","d"};
    private final String[] dMajor = new String[]{"D","E","F#","G","a","b","c#","d"};
    private final String[] eMinor = new String[]{"E","F#","G","a","b","c#","d#","e"};
    private final String[] eMajor = new String[]{"E","F#","G#","a","b","c#","d#","e"};
    private final String[] fMinor = new String[]{"F","G","g#","a#","c","c#","e#","f"};
    private final String[] fMajor = new String[]{"F","G","a","a#","c","d","e","f"};
    private final String[] gMinor = new String[]{"G","a","a#","c","d","d#","f#","g"};
    private final String[] gMajor = new String[]{"G","a","b","c","d","e","f#","a"};
    private final String[] aMinor = new String[]{"A","B","C","D","E","F","G","a"};
    private final String[] aMajor = new String[]{"A","B","C#","D","E","F#","G#","a"};
    private final String[] bMinor = new String[]{"B","C#","D","E","F#","G","a","b"};
    private final String[] bMajor = new String[]{"B","C#","D#","E","F#","G#","a#","b"};

    private final HashMap<Integer, String[]> scales = new HashMap<>();

    public Scales() {
        scales.put(1, cMinor);
        scales.put(2, cMajor);
        scales.put(3, dMinor);
        scales.put(4, dMajor);
        scales.put(5, eMinor);
        scales.put(6, eMajor);
        scales.put(7, fMinor);
        scales.put(8, fMajor);
        scales.put(9, gMinor);
        scales.put(10, gMajor);
        scales.put(11, aMinor);
        scales.put(12, aMajor);
        scales.put(13, bMinor);
        scales.put(14, bMajor);
    }

    public String[] getcMinor() {
        return cMinor;
    }

    public String[] getcMajor() {
        return cMajor;
    }

    public String[] getdMinor() {
        return dMinor;
    }

    public String[] getdMajor() {
        return dMajor;
    }

    public String[] geteMinor() {
        return eMinor;
    }

    public String[] geteMajor() {
        return eMajor;
    }

    public String[] getfMinor() {
        return fMinor;
    }

    public String[] getfMajor() {
        return fMajor;
    }

    public String[] getgMinor() {
        return gMinor;
    }

    public String[] getgMajor() {
        return gMajor;
    }

    public String[] getaMinor() {
        return aMinor;
    }

    public String[] getaMajor() {
        return aMajor;
    }

    public String[] getbMinor() {
        return bMinor;
    }

    public String[] getbMajor() {
        return bMajor;
    }

    public String[] getArpeggio(String[] scale){
        String[] newList = new String[4];
        newList[0] = scale[0];
        newList[1] = scale[2];
        newList[2] = scale[4];
        newList[3] = scale[7];

        return newList;
    }

    public boolean checkList(String[] scale, int index, String str){
        boolean inScale = false;
        if(scale[index].equalsIgnoreCase(str))
            inScale = true;

        return inScale;
    }

    @Override
    public String toString() {
        return "Scales{" +
                "cMinor=" + Arrays.toString(cMinor) +
                ",\n cMajor=" + Arrays.toString(cMajor) +
                ",\n dMinor=" + Arrays.toString(dMinor) +
                ",\n dMajor=" + Arrays.toString(dMajor) +
                ",\n eMinor=" + Arrays.toString(eMinor) +
                ",\n eMajor=" + Arrays.toString(eMajor) +
                ",\n fMinor=" + Arrays.toString(fMinor) +
                ",\n fMajor=" + Arrays.toString(fMajor) +
                ",\n gMinor=" + Arrays.toString(gMinor) +
                ",\n gMajor=" + Arrays.toString(gMajor) +
                ",\n aMinor=" + Arrays.toString(aMinor) +
                ",\n aMajor=" + Arrays.toString(aMajor) +
                ",\n bMinor=" + Arrays.toString(bMinor) +
                ",\n bMajor=" + Arrays.toString(bMajor) +
                '}';
    }

    public String[] getRandomScale(){
        Random r = new Random();
        int num = r.nextInt(13) + 1;
        System.out.println("Random num: " + num);
        System.out.println(Arrays.toString(scales.get(num)));
        return scales.get(num);
    }
}
