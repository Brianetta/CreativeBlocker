package net.simplycrafted.CreativeBlocker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Copyright © Brian Ronald
 * 01/01/14
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
public class CreativeBlocker extends JavaPlugin implements Listener {

    public CreativeBlocker() {
    }

    @Override
    public void onEnable(){
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        InventoryCreativeEvent.getHandlerList().unregister((Plugin) this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("creativeblocker")){
            if (args.length == 1)
                if(args[0].equalsIgnoreCase("reload")) {
                    sender.sendMessage("Reloading configuration for CreativeBlocker");
                    reloadConfig();
                    return true;
                }
        }
        return false;
    }

    @EventHandler
    public void onInventoryCreativeEvent (InventoryCreativeEvent event) {
        ItemStack stack = event.getCursor();
        List<String> blockedPotions = this.getConfig().getStringList("blocked");
        if ((stack.getType() != null) && blockedPotions.contains(stack.getType().name())) {
            if (!(event.getWhoClicked().hasPermission("creativeblocker.equip"))) {
                getLogger().info(event.getWhoClicked().getName() + " is not allowed to equip " + stack.getType().toString());
                event.setCancelled(true);
            }
        }
    }
}
