package com.spearforge.spearDaily.commands;

import com.spearforge.spearDaily.gui.DailyRewardsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DailyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("Bu komutu sadece oyuncular kullanabilir.");
            return true;
        }

        Player player = (Player) commandSender;
        DailyRewardsGUI dailyRewardsGUI = new DailyRewardsGUI();
        dailyRewardsGUI.openRewardGUI(player);




        return true;
    }
}
