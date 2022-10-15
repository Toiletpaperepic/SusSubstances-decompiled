package io.github.toiletpaperepic.SusSubstances_decompiled;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Bean {
  public HashMap<UUID, Integer> BeanList = Maps.newHashMap();
  
  public Plugin plugin = (Plugin)Main.getPlugin(Main.class);
  
  public ItemStack getBean() {
    ItemStack sugar = new ItemStack(Material.COCOA_BEANS);
    sugar.setAmount(1);
    ItemMeta m = sugar.getItemMeta();
    assert m != null;
    NamespacedKey key = new NamespacedKey(this.plugin, this.plugin.getDescription().getName());
    Glow glow = new Glow(key);
    m.addEnchant(glow, 1, true);
    m.setDisplayName(ChatColor.DARK_PURPLE + "Bean");
    sugar.setItemMeta(m);
    return sugar;
  }
  
  public void triggerHigh(final Player p) {
    final UUID id = p.getUniqueId();
    if (this.BeanList.get(id) == null) {
      this.BeanList.put(id, Integer.valueOf(0));
      p.sendMessage(ChatColor.GREEN + "Yummy");
      p.sendMessage("" + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "*stomach growls*");
      p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 50, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 50, true));
      (new BukkitRunnable() {
          public void run() {
            Bean.this.BeanList.remove(id);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 100.0F);
            p.setGliding(false);
          }
        }).runTaskLater(this.plugin, 50L);
    } 
  }
}
