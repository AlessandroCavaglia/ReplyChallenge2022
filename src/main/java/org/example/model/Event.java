package org.example.model;

public class Event {
    int turnId;
    int staminaValue;

    public Event(int turnId, int staminaValue) {
        this.turnId = turnId;
        this.staminaValue = staminaValue;
    }

    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public int getStaminaValue() {
        return staminaValue;
    }

    public void setStaminaValue(int staminaValue) {
        this.staminaValue = staminaValue;
    }
}
