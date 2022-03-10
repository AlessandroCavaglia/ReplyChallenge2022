package org.example;

import org.example.model.Demon;
import org.example.model.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
    public String filePath;
    public ReadFile(String path){
        filePath=path;
    }

    public void read(Game game, List<Demon> demoni) throws FileNotFoundException {
        //Game game = null;
        String fileData="";
        //List<Demon> demoni = new ArrayList<>();
        String [] infoTurno;
        String [] infoDemone;
        boolean first = true;
        try{
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if(first){
                    infoTurno = myReader.nextLine().split(" ");
                    game = new Game(Integer.parseInt(infoTurno[2]),Integer.parseInt(infoTurno[0]),Integer.parseInt(infoTurno[1]));
                    first = false;
                }else{
                    List<Integer> frammenti = new ArrayList();
                    infoDemone = myReader.nextLine().split(" ");
                    for (int i = 4; i < Integer.parseInt(infoDemone[3]) + 4; i++) {
                        frammenti.add(Integer.parseInt(infoDemone[i]));
                    }

                    demoni.add(new Demon(Integer.parseInt(infoDemone[0]), Integer.parseInt(infoDemone[1]),Integer.parseInt(infoDemone[2]), frammenti));
                }

            }
            myReader.close();
        }catch(Exception ex){
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
        game.toString();
        demoni.forEach(System.out::println);

    }
}
