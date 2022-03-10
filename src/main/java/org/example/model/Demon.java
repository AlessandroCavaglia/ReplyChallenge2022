package org.example.model;

public class Demon {
    int staminRichiesta;
    int tempoRecupero;
    int staminaRecupero;
    int[] frammenti;
    int sommaFrammenti;
    float mediaFrammenti;

    public Demon(int staminRichiesta, int tempoRecupero, int staminaRecupero, int[] frammenti) {
        this.staminRichiesta = staminRichiesta;
        this.tempoRecupero = tempoRecupero;
        this.staminaRecupero = staminaRecupero;
        this.frammenti = frammenti;
        this.sommaFrammenti=0;
        for (int elem:
             frammenti) {
            this.sommaFrammenti+=elem;
        }
        this.mediaFrammenti = Float.parseFloat(""+sommaFrammenti) / frammenti.length;
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

    public int[] getFrammenti() {
        return frammenti;
    }

    public void setFrammenti(int[] frammenti) {
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
}
