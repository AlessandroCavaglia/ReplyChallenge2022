package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    public String filePath;
    public ReadFile(String path){
        filePath=path;
    }

    public String read() throws FileNotFoundException {
        String fileData="";
        try{
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileData += myReader.nextLine();
            }
            myReader.close();
        }catch(Exception ex){
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
        return fileData;
    }
}
