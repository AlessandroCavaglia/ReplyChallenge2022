package org.example.model;

import java.util.List;

public class FirstStrategyWorkerV2 extends ListWorker{

    Game game;

    public FirstStrategyWorkerV2(int startIndex, int endIndex, List<Demon> demons, Game game) {
        super(startIndex, endIndex, demons);
        this.game=game;
    }

    public void run(){
        Demon bestDemon=null;
        this.loadDemonsScoreValutation(game.getMaxTurn()-game.getActualTurn());
        float bestDemonValutation=-1;
        for(int i=startIndex;i<=endIndex;i++){
            Demon actual=demons.get(i);
            if(actual.getStaminRichiesta()<=game.getStamina()){
                float demonValutation=actual.getValutationStamina()*2+actual.getValutationPunteggio();
                if(demonValutation>bestDemonValutation){
                    bestDemonValutation=demonValutation;
                    bestDemon=actual;
                }
            }
        }
        this.setResult(bestDemon);
    }
}
