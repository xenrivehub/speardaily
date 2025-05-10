package com.spearforge.spearDaily;

import com.spearforge.spearDaily.commands.DailyCommand;
import com.spearforge.spearDaily.commands.ReloadCommand;
import com.spearforge.spearDaily.listener.DailyRewardListener;
import com.spearforge.spearDaily.managers.PlayerDataManager;
import com.spearforge.spearDaily.managers.RewardManager;
import com.spearforge.spearDaily.models.RewardData;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SpearDaily extends JavaPlugin {

    @Getter
    private static PlayerDataManager playerDataManager;
    @Getter
    private static SpearDaily plugin;
    @Getter
    private static HashMap<Integer, Map<String, List<String>>> rewards = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        playerDataManager = new PlayerDataManager(this);

        // load rewards from config
        RewardManager.loadRewards();
        getCommand("günlüködül").setExecutor(new DailyCommand());
        getCommand("dailyreload").setExecutor(new ReloadCommand());
        getServer().getPluginManager().registerEvents(new DailyRewardListener(), this);
    }
}
