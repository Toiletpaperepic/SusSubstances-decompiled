package io.github.toiletpaperepic.sussubstances_decompiled;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

public class Sauce {
	public HashMap<UUID, Integer> CrystalList = Maps.newHashMap();
	  
	public Plugin plugin = (Plugin)Main.getPlugin(Main.class);
	  
	public ItemStack getSauce() {
	  ItemStack sauce = new ItemStack(Material.HONEY_BOTTLE);
	  sauce.setAmount(1);
	  ItemMeta m = sauce.getItemMeta();
	  assert m != null;
	  
	  m.setDisplayName(ChatColor.DARK_PURPLE + "special sauce");
	  sauce.setItemMeta(m);
	  return sauce;
	}
}
