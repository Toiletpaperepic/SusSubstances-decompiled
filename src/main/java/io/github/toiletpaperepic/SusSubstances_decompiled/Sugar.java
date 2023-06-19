package io.github.toiletpaperepic.SusSubstances_decompiled;

import com.google.common.collect.Maps;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Sugar implements Listener {
    final HashMap<UUID, Integer> sugarList = Maps.newHashMap();
    final ArrayList<UUID> sugarFly = new ArrayList<>();
    final Plugin plugin = Main.getPlugin(Main.class);

    public ItemStack getSugar() {
        ItemStack sugar = new ItemStack(Material.SUGAR);
        sugar.setAmount(1);
        ItemMeta itemMeta = sugar.getItemMeta();
        assert itemMeta != null;
        NamespacedKey key = new NamespacedKey(this.plugin, this.plugin.getDescription().getName());
        Glow glow = new Glow(key);
        itemMeta.addEnchant(glow, 1, true);
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Sugar");
        sugar.setItemMeta(itemMeta);
        return sugar;
    }

    public void triggerHigh(Player player) {
        final UUID id = player.getUniqueId();
        if (sugarList.get(id) == null) {
            sugarList.put(id, 0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 150, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 150, 10, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1, true));
            player.sendMessage(ChatColor.GREEN + "Yummy");
            player.sendMessage(ChatColor.GREEN + "You've gained SUPER POWERS!!");

            new BukkitRunnable() {
                int i;

                @Override
                public void run() {
                    i++;
                    if (i >= 5) {
                        cancel();
                    } else {
                        Location playerLocation = player.getLocation();
                        player.getWorld().spawnParticle(Particle.ASH, playerLocation, 100);
                        player.playSound(playerLocation, Sound.BLOCK_BEEHIVE_SHEAR, 100.0F, 1.0F);
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 1L, 5L);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (sugarList.get(id) == null) {
                        return;
                    }

                    player.sendMessage(ChatColor.RED + "JUST KIDDING");
                    Vector vec = new Vector(0, 2, 0);
                    player.setVelocity(vec);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (sugarList.get(id) == null) {
                                return;
                            }

                            Location playerLocation = player.getLocation();
                            player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, playerLocation, 10);
                            sugarFly.add(id);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 125, 10, true));
                            player.sendMessage("" + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "boss music plays");
                            player.playSound(playerLocation, Sound.MUSIC_DISC_PIGSTEP, 100.0F, 1.0F);
                            randomSounds(player);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.stopSound(Sound.MUSIC_DISC_PIGSTEP);
                                    sugarList.remove(id);
                                    sugarFly.remove(id);
                                    player.playSound(playerLocation, Sound.ENTITY_PLAYER_BURP, 100.0F, 100.0F);
                                }
                            }.runTaskLater(plugin, 100L);
                        }
                    }.runTaskLater(plugin, 10L);
                }
            }.runTaskLater(plugin, 50L);
        }
    }

    private void randomSounds(Player player) {
        new BukkitRunnable() {
            ArrayList<Sound> sounds = getSounds();

            @Override
            public void run() {
                if (!sugarFly.contains(player.getUniqueId())) {
                    cancel();
                } else {
                    int size = sounds.size();
                    int random = new Random().nextInt(size);
                    size = 100;
                    int random2 = new Random().nextInt(size);
                    player.playSound(player.getLocation(), sounds.get(random), 100.0F, random2);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 1L, 1L);
    }

    private ArrayList<Sound> getSounds() {
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
