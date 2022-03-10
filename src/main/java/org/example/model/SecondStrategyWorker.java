package org.example.model;

import java.util.List;

public class SecondStrategyWorker extends ListWorker{

    Game game;

    public SecondStrategyWorker(int startIndex, int endIndex, List<Demon> demons, Game game) {
        super(startIndex, endIndex, demons);
        this.game=game;
    }

    public void run(){
        int remainingTurns=game.maxTurn-game.actualTurn;
        Demon bestDemon=null;
        float bestDemonValutation=-1;
        for(int i=startIndex;i<=endIndex;i++){
            Demon actual=demons.get(i);
            if(actual.getStaminRichiesta()<=game.getStamina()){
                float demonValutation;
                if(actual.getFrammenti().size()>=remainingTurns){
                    demonValutation=actual.getSommaFrammenti();
                }else{
                    demonValutation=actual.getMediaFrammenti()*remainingTurns;
                }
                if(demonValutation>bestDemonValutation){
                    bestDemonValutation=demonValutation;
                    bestDemon=actual;
                }
            }
        }
        this.setResult(bestDemon);
    }
}
