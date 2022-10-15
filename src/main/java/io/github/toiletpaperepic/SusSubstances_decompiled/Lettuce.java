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

public class Lettuce implements Listener {
  public HashMap<UUID, Integer> LettuceList = Maps.newHashMap();
  
  public Plugin plugin = (Plugin)Main.getPlugin(Main.class);
  
  public ItemStack getLettuce() {
    ItemStack lettuce = new ItemStack(Material.FERN);
    lettuce.setAmount(1);
    ItemMeta m = lettuce.getItemMeta();
    assert m != null;
    NamespacedKey key = new NamespacedKey(this.plugin, this.plugin.getDescription().getName());
    Glow glow = new Glow(key);
    m.addEnchant(glow, 1, true);
    m.setDisplayName(ChatColor.DARK_PURPLE + "Lettuce");
    lettuce.setItemMeta(m);
    return lettuce;
  }
  
  public void triggerHigh(final Player p) {
    final UUID id = p.getUniqueId();
    if (this.LettuceList.get(id) == null) {
      this.LettuceList.put(id, Integer.valueOf(0));
      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 50, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 50, true));
      p.sendMessage(ChatColor.GREEN + "Yummy");
      (new BukkitRunnable() {
          int i = 0;
          
          public void run() {
            this.i++;
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
            if (this.i == 1) {
              p.sendMessage(ChatColor.RED + "You don't feel too good...");
            } else if (this.i == 5) {
              cancel();
            } 
          }
        }).runTaskTimerAsynchronously(this.plugin, 10L, 10L);
      if (this.LettuceList.get(id) == null)
        return; 
      (new BukkitRunnable() {
          public void run() {
            if (Lettuce.this.LettuceList.get(id) == null)
              return; 
            Lettuce.this.LettuceList.put(id, Integer.valueOf(1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 1, true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 3, true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 10, true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1, true));
            p.sendMessage(ChatColor.GREEN + "You are High!");
            Lettuce.this.randomSound(p, Integer.valueOf(1));
            Lettuce.this.randomVelocity(p);
            (new BukkitRunnable() {
                public void run() {
                  Lettuce.this.LettuceList.remove(id);
                  if (Lettuce.this.LettuceList.containsKey(id)) {
                    Lettuce.this.LettuceList.remove(id);
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
                    Vector vec = new Vector(0, 1, 0);
                    p.setVelocity(vec);
                  } 
                }
              }).runTaskLater((Plugin)Main.getPlugin(Main.class), 100L);
          }
        }).runTaskLater(this.plugin, 50L);
    } 
  }
  
  public void randomVelocity(final Player p) {
    final UUID id = p.getUniqueId();
    final ArrayList<Vector> vectors = getVelocities();
    (new BukkitRunnable() {
        public void run() {
          if (Lettuce.this.LettuceList.containsKey(id)) {
            if (((Integer)Lettuce.this.LettuceList.get(id)).intValue() > 5) {
              cancel();
            } else {
              int size = vectors.size();
              int random = (new Random()).nextInt(size);
              p.setVelocity(vectors.get(random));
            } 
          } else {
            cancel();
          } 
        }
      }).runTaskTimerAsynchronously(this.plugin, 3L, 3L);
  }
  
  public void randomSound(final Player p, Integer type) {
    final UUID id = p.getUniqueId();
    final ArrayList<Sound> sounds = getSounds();
    if (type.intValue() == 0) {
      Integer num = this.LettuceList.get(id);
      this.LettuceList.replace(id, num, Integer.valueOf(num.intValue() + 1));
      return;
    } 
    (new BukkitRunnable() {
        public void run() {
          if (Lettuce.this.LettuceList.get(id) == null)
            cancel(); 
          if (Lettuce.this.LettuceList.containsKey(id)) {
            if (((Integer)Lettuce.this.LettuceList.get(id)).intValue() > 5) {
              cancel();
            } else {
              int size = sounds.size();
              int random = (new Random()).nextInt(size);
              p.playSound(p.getLocation(), sounds.get(random), 100.0F, 1.0F);
              p.getWorld().spawnParticle(Particle.TOTEM, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 100);
            } 
          } else {
            cancel();
          } 
        }
      }).runTaskTimerAsynchronously(this.plugin, 1L, 1L);
  }
  
  public ArrayList<Vector> getVelocities() {
    ArrayList<Vector> vectors = new ArrayList<>();
    Vector vec = new Vector(0.5D, 0.0D, 0.0D);
    vectors.add(vec);
    vec = new Vector(0.0D, 0.5D, 0.0D);
    vectors.add(vec);
    vec = new Vector(0.0D, 0.0D, 0.5D);
    vectors.add(vec);
    vec = new Vector(-0.5D, 0.0D, 0.0D);
    vectors.add(vec);
    vec = new Vector(0.0D, -0.5D, 0.0D);
    vectors.add(vec);
    vec = new Vector(0.0D, 0.0D, -0.5D);
    vectors.add(vec);
    return vectors;
  }
  
  public ArrayList<Sound> getSounds() {
    ArrayList<Sound> sounds = new ArrayList<>();
    sounds.add(Sound.ENTITY_WITCH_CELEBRATE);
    sounds.add(Sound.ENTITY_DOLPHIN_PLAY);
    sounds.add(Sound.ENTITY_DONKEY_HURT);
    sounds.add(Sound.ENTITY_DRAGON_FIREBALL_EXPLODE);
    sounds.add(Sound.ENTITY_DROWNED_DEATH);
    sounds.add(Sound.ENTITY_ELDER_GUARDIAN_CURSE);
    sounds.add(Sound.ENTITY_ENDERMAN_SCREAM);
    sounds.add(Sound.ENTITY_EVOKER_DEATH);
    sounds.add(Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR);
    sounds.add(Sound.ENTITY_FOX_DEATH);
    sounds.add(Sound.ENTITY_GENERIC_DEATH);
    sounds.add(Sound.ENTITY_GHAST_DEATH);
    sounds.add(Sound.ENTITY_GUARDIAN_FLOP);
    sounds.add(Sound.ENTITY_LLAMA_CHEST);
    sounds.add(Sound.ENTITY_WOLF_WHINE);
    return sounds;
  }
}
