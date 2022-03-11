package org.example.model;

import java.util.List;

public class Demon {
    int staminRichiesta;
    int tempoRecupero;
    int staminaRecupero;
    List<Integer> frammenti;
    int sommaFrammenti;
    float mediaFrammenti;
    float valutationStamina;
    float valutationPunteggio;

    @Override
    public String toString() {
        return "Demon{" +
                "staminRichiesta=" + staminRichiesta +
                ", tempoRecupero=" + tempoRecupero +
                ", staminaRecupero=" + staminaRecupero +
                ", frammenti=" + frammenti +
                ", sommaFrammenti=" + sommaFrammenti +
                ", mediaFrammenti=" + mediaFrammenti +
                '}';
    }

    public Demon(int staminRichiesta, int tempoRecupero, int staminaRecupero, List<Integer> frammenti) {
        this.staminRichiesta = staminRichiesta;
        this.tempoRecupero = tempoRecupero;
        this.staminaRecupero = staminaRecupero;
        this.frammenti = frammenti;
        this.sommaFrammenti=0;
        for (int elem:
             frammenti) {
            this.sommaFrammenti+=elem;
        }
        this.mediaFrammenti = Float.parseFloat(""+sommaFrammenti) / frammenti.size();
    }

    public int getStaminRichiesta() {
        return staminRichiesta;
    }

    public void setStaminRichiesta(int staminRichiesta) {
        this.staminRichiesta = staminRichiesta;
    }

    public int getTempoRecupero() {
        return tempoRecupero;
    }

    public void setTempoRecupero(int tempoRecupero) {
        this.tempoRecupero = tempoRecupero;
    }

    public int getStaminaRecupero() {
        return staminaRecupero;
    }

    public void setStaminaRecupero(int staminaRecupero) {
        this.staminaRecupero = staminaRecupero;
    }

    public List<Integer> getFrammenti() {
        return frammenti;
    }

    public void setFrammenti(List<Integer> frammenti) {
        this.frammenti = frammenti;
    }

    public int getSommaFrammenti() {
        return sommaFrammenti;
    }

    public void setSommaFrammenti(int sommaFrammenti) {
        this.sommaFrammenti = sommaFrammenti;
    }

    public float getMediaFrammenti() {
        return mediaFrammenti;
    }

    public void setMediaFrammenti(float mediaFrammenti) {
        this.mediaFrammenti = mediaFrammenti;
    }

    public float getValutationStamina() {
        return valutationStamina;
    }

    public void setValutationStamina(float valutationStamina) {
        this.valutationStamina = valutationStamina;
    }

    public float getValutationPunteggio() {
        return valutationPunteggio;
    }

    public void setValutationPunteggio(float valutationPunteggio) {
        this.valutationPunteggio = valutationPunteggio;
    }
}
