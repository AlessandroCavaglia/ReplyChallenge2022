package org.example.model;

import java.util.List;

public abstract class ListWorker extends Thread{
    int startIndex;
    int endIndex;
    List<Demon> demons;
    Demon result=null;

    public ListWorker(int startIndex, int endIndex, List<Demon> demons) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.demons = demons;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public List<Demon> getDemons() {
        return demons;
    }

    public void setDemons(List<Demon> demons) {
        this.demons = demons;
    }

    public Demon getResult() {
        return result;
    }

    public void setResult(Demon result) {
        this.result = result;
    }

    public void loadDemonsScoreValutation(int remainingTurns){
        for (Demon actual:
             this.demons.subList(startIndex,endIndex+1)) {
            if(actual.getFrammenti().size()>=remainingTurns){
                actual.setValutationPunteggio(actual.getSommaFrammenti());
            }else{
                actual.setValutationPunteggio(actual.getMediaFrammenti()*remainingTurns);
            }

        }
    }

    public static List<Demon> loadDemonsStamibaValutationAndReturn(List<Demon> demons){
        for(Demon actual:demons){
            actual.setValutationStamina((actual.getStaminaRecupero()-actual.getStaminRichiesta())/ Float.parseFloat(""+actual.getTempoRecupero()));
        }
        return demons;
    }
}
