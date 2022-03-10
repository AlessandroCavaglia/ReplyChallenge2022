package org.example.model;

public class Game {
    @Override
    public String toString() {
        return "Game{" +
                "maxTurn=" + maxTurn +
                ", actualTurn=" + actualTurn +
                ", stamina=" + stamina +
                ", maxStamina=" + maxStamina +
                '}';
    }

    int maxTurn;
    int actualTurn;
    int stamina;
    int maxStamina;

    public Game(int maxTurn, int stamina, int maxStamina) {
        this.maxTurn = maxTurn;
        this.stamina = stamina;
        this.maxStamina = maxStamina;
        this.actualTurn=0;
    }

    public int getMaxTurn() {
        return maxTurn;
    }

    public void setMaxTurn(int maxTurn) {
        this.maxTurn = maxTurn;
    }

    public int getActualTurn() {
        return actualTurn;
    }

    public void setActualTurn(int actualTurn) {
        this.actualTurn = actualTurn;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
        if(this.stamina>this.maxStamina)
            this.stamina=this.maxStamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }
}
