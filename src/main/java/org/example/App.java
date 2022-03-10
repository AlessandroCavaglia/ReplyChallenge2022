package org.example;


import org.example.model.Demon;
import org.example.model.Game;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
        ReadFile rf=new ReadFile("inputs/00-example.txt");
        List<Demon> demoni = new ArrayList<>();
        Game game = null;
        rf.read(game,demoni);
        //System.out.println(game.toString());

        WriteFile wr = new WriteFile("file.txt");
        List <Integer> demoniBattuti = new ArrayList<>();
        demoniBattuti.add(1);
        demoniBattuti.add(2);
        demoniBattuti.add(4);
        demoniBattuti.add(0);
        wr.write(demoniBattuti);



    }
}
