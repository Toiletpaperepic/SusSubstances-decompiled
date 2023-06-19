package io.github.toiletpaperepic.SusSubstances_decompiled;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Crystals {
  public HashMap<UUID, Integer> CrystalList = Maps.newHashMap();
  
  public Plugin plugin = (Plugin)Main.getPlugin(Main.class);
  
  public ItemStack getCrystal() {
    ItemStack crystal = new ItemStack(Material.AMETHYST_SHARD);
    crystal.setAmount(1);
    ItemMeta m = crystal.getItemMeta();
    assert m != null;
    NamespacedKey key = new NamespacedKey(this.plugin, this.plugin.getDescription().getName());
    Glow glow = new Glow(key);
    m.addEnchant(glow, 1, true);
    m.setDisplayName(ChatColor.DARK_PURPLE + "Crystals");
    crystal.setItemMeta(m);
    return crystal;
  }
  
  public void triggerHigh(Player p) {
//	  p.sendMessage("The fuck are you doing " + p.getName() + "?");
//	  p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 100.0F, 1.0F);
	  p.kickPlayer("The fuck are you doing " + p.getName() + "?");
  }
}