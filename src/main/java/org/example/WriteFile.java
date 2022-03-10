package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {
    public String filePath;
    public WriteFile(String path){
        filePath=path;
    }
    public void write(List<Integer> demoniBattuti){
String toWrite = "";
        for (Integer demoni:
             demoniBattuti) {
            toWrite= toWrite+demoni+"\n";
        }
        try{
            File myObj = new File(filePath);
            myObj.createNewFile();
            FileWriter myWriter = new FileWriter(myObj);
            myWriter.write(toWrite);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
