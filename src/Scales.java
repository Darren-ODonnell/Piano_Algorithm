import java.util.Arrays;

public class Scales {
    //# = sharp(up to right)
    //^ = flat(up tp the left)

    private final String[] cMinor = new String[]{"C","D","E^","F","G","a^","b^","C"};
    private final String[] cMajor = new String[]{"C","D","E","F","G","a","b","c"};
    private final String[] dMinor = new String[]{"D","E","F","G","a","b","c#","d"};
    private final String[] dMajor = new String[]{"D","E","F#","G","a","b","c#","d"};
    private final String[] eMinor = new String[]{"E","F#","G","a","b","c#","d#","e"};
    private final String[] eMajor = new String[]{"E","F#","G#","a","b","c#","d#","e"};
    private final String[] fMinor = new String[]{"F","G","a^","b^","c","d^","e^","f"};
    private final String[] fMajor = new String[]{"F","G","a","b^","c","d","e","f"};
    private final String[] gMinor = new String[]{"G","a","b^","c","d","e^","f#","g"};
    private final String[] gMajor = new String[]{"G","a","b","c","d","e","f#","a"};
    private final String[] aMinor = new String[]{"A","B","C","D","E","F","G","a"};
    private final String[] aMajor = new String[]{"A","B","C#","D","E","F#","G#","a"};
    private final String[] bMinor = new String[]{"B","C#","D","E","F#","G","a","b"};
    private final String[] bMajor = new String[]{"B","C#","D#","E","F#","G#","a#","b"};

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
}
