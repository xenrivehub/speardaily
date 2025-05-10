package com.spearforge.spearDaily.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RewardData {

    private String message;
    private List<String> commands;

}
