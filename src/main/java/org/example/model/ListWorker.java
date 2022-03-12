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

    public static List<Demon> loadDemonsScoreValutation(int remainingTurns,List<Demon> demons){
        float maxValue=-1;
        for (Demon actual:
             demons) {
            if(actual.getFrammenti().size()>=remainingTurns){
                actual.setValutationPunteggio(actual.getSommaFrammenti());
            }else{
                actual.setValutationPunteggio(actual.getMediaFrammenti()*remainingTurns);
            }
            if(maxValue<actual.getValutationPunteggio())
                maxValue=actual.getValutationPunteggio();
        }
        for (Demon actual:
                demons) {
            actual.setValutationPunteggio(maxValue/actual.getValutationPunteggio());
        }
        return demons;
    }

    public static List<Demon> loadDemonsStamibaValutationAndReturn(List<Demon> demons){
        float maxValue=-1;
        for(Demon actual:demons){
            actual.setValutationStamina((actual.getStaminaRecupero()-actual.getStaminRichiesta())/ Float.parseFloat(""+actual.getTempoRecupero()));
            if(actual.getValutationStamina()>maxValue)
                maxValue=actual.getValutationStamina();
        }
        for(Demon actual:demons){
            actual.setValutationStamina(maxValue/actual.getValutationStamina());
        }
        return demons;
    }
}
