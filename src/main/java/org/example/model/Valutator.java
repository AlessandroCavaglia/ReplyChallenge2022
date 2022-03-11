package org.example.model;

import org.example.ReadFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Valutator {
    public static void main(String[] args) throws FileNotFoundException {
        ReadFile rf=new ReadFile("inputs/5.txt");
        ReadFile rfR=new ReadFile("file5.txt");
        int score=0;
        List<Demon> demons = new ArrayList<>();
        Game game = null;
        List<Integer> enemies;
        game=rf.readGame();
        demons=rf.readDemons();
        enemies=rfR.readEnemies();
        List<Event> eventsStamina=new ArrayList<Event>();
        List<Event> eventsScore=new ArrayList<Event>();
        while(game.getActualTurn()<= game.getMaxTurn()){
            List<Event> turnEvents=  getTurnEvents(eventsStamina,game.getActualTurn());
            for (Event e:
                    turnEvents) {
                game.setStamina(game.getStamina()+e.getStaminaValue());
            }
            turnEvents=  getTurnEvents(eventsScore,game.getActualTurn());
            for (Event e:
                    turnEvents) {
                score+=+e.getStaminaValue();
            }
            if(enemies.size()>0){
                Demon turnDemon=demons.get(enemies.get(0));
                if(game.getStamina()>=turnDemon.getStaminRichiesta()){
                    game.setStamina(game.getStamina()-turnDemon.getStaminRichiesta());
                    eventsStamina.add(new Event(game.getActualTurn()+turnDemon.getTempoRecupero(),turnDemon.getStaminaRecupero()));
                    for (int i=0;i<turnDemon.getFrammenti().size();i++){
                        eventsScore.add(new Event(game.getActualTurn()+i+1,turnDemon.getFrammenti().get(i)));
                    }
                    enemies.remove(0);
                }
            }
            game.setActualTurn(game.getActualTurn()+1);
        }

        System.out.println(score);
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
}
