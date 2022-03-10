package org.example;


import org.example.model.Demon;
import org.example.model.Event;
import org.example.model.FirstStrategyWorker;
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
    public static final int THREADS=2;

    public static void main( String[] args ) throws FileNotFoundException {
        ReadFile rf=new ReadFile("inputs/01-the-cloud-abyss.txt");
        List<Demon> demons = new ArrayList<>();
        Game game = null;
        game=rf.readGame();
        demons=rf.readDemons();


        List<Integer> enemiesDefeated=new ArrayList<Integer>();
        List<Event> events=new ArrayList<Event>();

        while(game.getActualTurn()<= game.getMaxTurn()){
            List<Event> turnEvents=  getTurnEvents(events,game.getActualTurn());
            for (Event e:
                    turnEvents) {
                game.setStamina(game.getStamina()+e.getStaminaValue());
            }

            int enemy=playTurn(game,demons,events);

            if(enemy!=-1){
                Demon chosenDemon=demons.get(enemy);
                events.add(new Event(game.getActualTurn()+chosenDemon.getTempoRecupero(),chosenDemon.getStaminaRecupero()));
                game.setStamina(game.getStamina()-chosenDemon.getStaminRichiesta());
                demons.get(enemy).setStaminRichiesta(game.getMaxStamina()+1);
                enemiesDefeated.add(enemy);
            }

            game.setActualTurn(game.getActualTurn()+1);
        }



        WriteFile wr = new WriteFile("file.txt");
        wr.write(enemiesDefeated);
    }

    //Return the id of the enemy chosen to defeat or -1 if we don't do anything
    public static int playTurn(Game game,List<Demon>demons,List<Event> events){
        int chosenEnemy=-1;
        //At the start of each turn get all the events and update the stamina
        chosenEnemy=chooseEnemy(game,demons,events);
        //At the end of the turn update the game turn
        return chosenEnemy;
    }


    public static List<Event> getTurnEvents(List<Event> events,int turn){
        List<Event> returnVal=new ArrayList<Event>();
        for (Event e:
             events) {
            if(e.getTurnId()==turn){
                returnVal.add(e);
            }
        }
        return returnVal;
    }

    public static int chooseEnemy(Game game,List<Demon>demons,List<Event> events){
        int choosenEnemy=-1;

        choosenEnemy=firstStrategy(game,demons,events);


        return choosenEnemy;
    }

    public static int firstStrategy(Game game,List<Demon>demons,List<Event> events){
        List<FirstStrategyWorker> workers=new ArrayList<>();
        List<Demon> bestBets=new ArrayList<>();
        Demon chosenDemon=null;
        float bestDemonValutation=-1;

        int scale=demons.size()/THREADS;
        for(int i=0;i<THREADS;i++){
            int firstIndex=i*scale;
            int endIndex=((i+1)*scale)-1;
            if(i==THREADS-1){
                endIndex=demons.size()-1;
            }
            FirstStrategyWorker worker=new FirstStrategyWorker(firstIndex,endIndex,demons,game);
            worker.start();
            workers.add(worker);
        }
        for(int i=0;i<THREADS;i++){
            try{
                workers.get(i).join();
                if(workers.get(i).getResult()!=null){
                    bestBets.add(workers.get(i).getResult());
                }
            }catch (Exception ex){


            }
        }

        for (Demon actual:
             bestBets) {
            float demonValutation=(actual.getStaminaRecupero()-actual.getStaminRichiesta())/ Float.parseFloat(""+actual.getTempoRecupero());
            if(demonValutation>bestDemonValutation){
                bestDemonValutation=demonValutation;
                chosenDemon=actual;
            }
        }
        //Scegli il migliore

        System.out.println("Ho scelto:"+chosenDemon);

        if(chosenDemon!=null)
            return demons.indexOf(chosenDemon);
        else
            return -1;
    }

}
