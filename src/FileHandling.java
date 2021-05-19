import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {
    public FileHandling(){


    }


    private String readFromFile(String filename) throws IOException {
        String newMessage="";

        FileReader file = null;
        try {
            file = new FileReader(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(file);

        // now to readin from file
        String line = "";
        while(( line = br.readLine()) != null) {
            newMessage += line;

        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return newMessage;

    }

    public void writeToFile(String filename, String message) {
        FileWriter file = null;
        try {
            file = new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(file);

        pw.println("Hello World");

        pw.close();
    }

    public static void main(String[] args) {
        new FileHandling();
    }
}
