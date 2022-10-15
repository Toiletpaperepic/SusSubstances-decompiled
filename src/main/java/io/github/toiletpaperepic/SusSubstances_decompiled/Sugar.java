package io.github.toiletpaperepic.SusSubstances_decompiled;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Sugar implements Listener {
  public HashMap<UUID, Integer> SugarList = Maps.newHashMap();
  
  public ArrayList<UUID> SugarFly = new ArrayList<>();
  
  public Plugin plugin = (Plugin)Main.getPlugin(Main.class);
  
  public ItemStack getSugar() {
    ItemStack sugar = new ItemStack(Material.SUGAR);
    sugar.setAmount(1);
    ItemMeta m = sugar.getItemMeta();
    assert m != null;
    NamespacedKey key = new NamespacedKey(this.plugin, this.plugin.getDescription().getName());
    Glow glow = new Glow(key);
    m.addEnchant(glow, 1, true);
    m.setDisplayName(ChatColor.DARK_PURPLE + "Sugar");
    sugar.setItemMeta(m);
    return sugar;
  }
  
  public void triggerHigh(final Player p) {
    final UUID id = p.getUniqueId();
    if (this.SugarList.get(id) == null) {
      this.SugarList.put(id, Integer.valueOf(0));
      p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 150, 1, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 150, 10, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1, true));
      p.sendMessage(ChatColor.GREEN + "Yummy");
      p.sendMessage(ChatColor.GREEN + "You've gained SUPER POWERS!!");
      (new BukkitRunnable() {
          int i;
          
          public void run() {
            this.i++;
            if (this.i >= 5) {
              cancel();
            } else {
              p.getWorld().spawnParticle(Particle.ASH, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 100);
              p.playSound(p.getLocation(), Sound.BLOCK_BEEHIVE_SHEAR, 100.0F, 1.0F);
            } 
          }
        }).runTaskTimerAsynchronously(this.plugin, 1L, 5L);
      (new BukkitRunnable() {
          public void run() {
            if (Sugar.this.SugarList.get(id) == null)
              return; 
            p.sendMessage(ChatColor.RED + "JUST KIDDING");
            Vector vec = new Vector(0, 2, 0);
            p.setVelocity(vec);
            (new BukkitRunnable() {
                public void run() {
                  if (Sugar.this.SugarList.get(id) == null)
                    return; 
                  p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 10);
                  //p.setGliding(true);
                  Sugar.this.SugarFly.add(p.getUniqueId());
                  p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 125, 10, true));
                  p.sendMessage("" + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "boss music plays");
                  p.playSound(p.getLocation(), Sound.MUSIC_DISC_PIGSTEP, 100.0F, 1.0F);
                  Sugar.this.randomSounds(p);
                  (new BukkitRunnable() {
                      public void run() {
                        p.stopSound(Sound.MUSIC_DISC_PIGSTEP);
                        Sugar.this.SugarList.remove(id);
                        Sugar.this.SugarFly.remove(id);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 100.0F);
                      }
                    }).runTaskLater(Sugar.this.plugin, 100L);
                }
              }).runTaskLater(Sugar.this.plugin, 10L);
          }
        }).runTaskLater(this.plugin, 50L);
    } 
  }
  
  public void randomSounds(final Player p) {
    (new BukkitRunnable() {
        final ArrayList<Sound> sounds = Sugar.this.getSounds();
        
        public void run() {
          if (!Sugar.this.SugarFly.contains(p.getUniqueId())) {
            cancel();
          } else {
            int size = this.sounds.size();
            int random = (new Random()).nextInt(size);
            size = 100;
            int random2 = (new Random()).nextInt(size);
            p.playSound(p.getLocation(), this.sounds.get(random), 100.0F, random2);
          } 
        }
      }).runTaskTimerAsynchronously(this.plugin, 1L, 1L);
  }
  
  public ArrayList<Sound> getSounds() {
    ArrayList<Sound> sounds = new ArrayList<>();
    sounds.add(Sound.BLOCK_ANVIL_PLACE);
    sounds.add(Sound.BLOCK_ANVIL_USE);
    sounds.add(Sound.BLOCK_ANVIL_BREAK);
    sounds.add(Sound.BLOCK_ANVIL_FALL);
    sounds.add(Sound.BLOCK_ANVIL_HIT);
    sounds.add(Sound.BLOCK_ANVIL_DESTROY);
    sounds.add(Sound.BLOCK_ANVIL_LAND);
    sounds.add(Sound.BLOCK_ANVIL_STEP);
    sounds.add(Sound.BLOCK_ENCHANTMENT_TABLE_USE);
    return sounds;
  }
}
