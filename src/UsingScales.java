public class UsingScales {



    public String[] getChord(String[] scale){
        String[] newList = new String[4];
        newList[0] = scale[0];
        newList[1] = scale[2];
        newList[2] = scale[4];
        newList[3] = scale[6];

        return newList;
    }

}
