package io.github.toiletpaperepic.sussubstances_decompiled;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Crystals implements Item {
  public HashMap<UUID, Integer> CrystalList = Maps.newHashMap();
  public Plugin plugin = (Plugin)Main.getPlugin(Main.class);
  
  public ItemStack getitem() {
    ItemStack crystal = new ItemStack(Material.AMETHYST_SHARD);
    crystal.setAmount(1);

    ItemMeta m = crystal.getItemMeta();
    assert m != null;
    
    m.setDisplayName(ChatColor.DARK_PURPLE + "Crystals");
    crystal.setItemMeta(m);
    return crystal;
  }
  
  private void triggerHigh(Player p) {
	final UUID id = p.getUniqueId();
	if (this.CrystalList.get(id) == null) {
	    this.CrystalList.put(id, Integer.valueOf(0));
	    p.sendMessage("Holy fuck im High as a kite!");
	}
	if (p.getHealth() * 2 > p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) {
		p.damage(5);
	}
	p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 100.0F, 1.0F);
	//removed from the game or spigot
	// p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 50, true));
	p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 700, 50, true));
	p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 800, 4, true));
	p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 50, true));
	p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 50, true));
	(new BukkitRunnable() {
		@Override
        public void run() {
			CrystalList.remove(p.getUniqueId());
		}
	}).runTaskLater(plugin, 50L);
  }
  
  public void PlayerInteract(final Player p, ItemStack item, Crystals crystal) {
	if (Main.CrystalItemValues.getStatus() == false) {
        p.sendMessage(ChatColor.RED + "Crystals is not enabled on this server!");
        return;
    } 
	if (crystal.CrystalList.get(p.getUniqueId()) != null) {
		crystal.CrystalList.replace(p.getUniqueId(), Integer.valueOf(((Integer)crystal.CrystalList.get(p.getUniqueId())).intValue() + 1));
	    if (((Integer)crystal.CrystalList.get(p.getUniqueId())).intValue() == 1) {
	        p.sendMessage(ChatColor.RED + "Damn. Ambitious");
	    } else if (((Integer)crystal.CrystalList.get(p.getUniqueId())).intValue() == 2) {
	    	p.sendMessage(ChatColor.RED + "Jeez");
	    } else if (((Integer)crystal.CrystalList.get(p.getUniqueId())).intValue() >= 5) {
	    	p.getWorld().createExplosion(p.getLocation(), 3.0F, false);
	      	Crystals.this.CrystalList.remove(p.getUniqueId());
	      	item.setAmount(item.getAmount() - 1);
	        if (item.getAmount() < 1)
	            item = null; 
	        p.getInventory().setItemInMainHand(item);
	    } else {
	    	p.sendMessage(ChatColor.RED + "*battery Lights on fire.");
	    } 
	} 
	crystal.triggerHigh(p);
  }
}