package org.example.model;

import java.util.List;

public class ThirdStrategyWorkerV2 extends ListWorker{

    Game game;

    public ThirdStrategyWorkerV2(int startIndex, int endIndex, List<Demon> demons, Game game) {
        super(startIndex, endIndex, demons);
        this.game=game;
    }

    public void run(){
        this.loadDemonsScoreValutation(game.getMaxTurn()-game.getActualTurn());
        Demon bestDemon=null;
        float bestDemonValutation=-1;
        for(int i=startIndex;i<=endIndex;i++){
            Demon actual=demons.get(i);
            if(actual.getStaminRichiesta()<=game.getStamina()){
                float demonValutation=actual.getValutationStamina()+actual.getValutationPunteggio()*2;
                if(demonValutation>bestDemonValutation){
                    bestDemonValutation=demonValutation;
                    bestDemon=actual;
                }
            }
        }
        this.setResult(bestDemon);
    }
}
