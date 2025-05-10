package com.spearforge.spearDaily.commands;

import com.spearforge.spearDaily.SpearDaily;
import com.spearforge.spearDaily.managers.RewardManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;

            if (player.hasPermission("speardaily.reload")){
                player.sendMessage("§aSpearDaily config reloaded!");
                player.sendMessage("§aSpearDaily rewards reloaded!");

                // Reload the config and rewards
                SpearDaily.getPlugin().reloadConfig();
                SpearDaily.getRewards().clear();
                RewardManager.loadRewards();

            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6ꜱᴘᴇᴀʀ &fɴᴇᴛᴡᴏʀᴋ &8» &cBu komutu kullanmak için yeterli izniniz yok!"));
            }

        }

        return true;


    }
}
