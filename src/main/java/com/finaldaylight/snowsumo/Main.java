package com.finaldaylight.snowsumo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("wand")){
            if (!(sender instanceof Player)){
                sender.sendMessage(ChatColor.DARK_RED + "You cannot run this command");
                return true;
            }

            Player player = (Player) sender;
            //check if inventory slot is full or empty
            if (player.getInventory().firstEmpty() == -1){
                Location loc = player.getLocation();
                World world = player.getWorld();

                world.dropItemNaturally(loc, getItem());
                player.sendMessage(ChatColor.GOLD + "Your wand has been dropped");
                return true;
            }
            player.getInventory().addItem(getItem());
            player.sendMessage(ChatColor.GOLD + "You have received your wand");
            return true;
        }
        return false;
    }

    //wand
    public ItemStack getItem(){
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Sumo Wand");
        List <String> lore = new ArrayList<String>();

        meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        item.setItemMeta(meta);
        return item;
    }
    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD))
            if (Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).hasLore()){
                //right click event
                Player player = (Player) event.getPlayer();
                if (event.getAction()== Action.RIGHT_CLICK_AIR) {
                    player.launchProjectile(Snowball.class);
                }
            }
    }
}
