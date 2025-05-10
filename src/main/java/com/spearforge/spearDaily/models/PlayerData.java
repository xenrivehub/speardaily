package com.spearforge.spearDaily.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PlayerData {

    private LocalDate lastReward;
    private int currentStreak;

    public PlayerData(LocalDate lastReward, int currentStreak) {
        this.lastReward = lastReward;
        this.currentStreak = currentStreak;
    }

    public LocalDate getLastReward() {
        return lastReward;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }
}
