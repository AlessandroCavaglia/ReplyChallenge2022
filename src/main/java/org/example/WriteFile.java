package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    public String filePath;
    public WriteFile(String path){
        filePath=path;
    }
    public void write(String toWrite){
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
