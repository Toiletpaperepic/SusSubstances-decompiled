package io.github.toiletpaperepic.sussubstances_decompiled;

import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.command.CommandExecutor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import io.github.toiletpaperepic.sussubstances_decompiled.Main.ItemValues;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Events implements Listener, CommandExecutor {
    public Crystals crystal = new Crystals();
    public Lettuce lettuce = new Lettuce();
    public Sauce sauce = new Sauce();
    public Sugar sugar = new Sugar();
    public Bean bean = new Bean();

    public Events(Main main) {
        (Main.getPlugin(Main.class)).getServer().getPluginManager().registerEvents(this, main);
        ((PluginCommand)Objects.<PluginCommand>requireNonNull(Bukkit.getPluginCommand("sapi"))).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("sapi"))
            //console has permission too.
            if (sender.hasPermission("substances.perms")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax!" + ChatColor.WHITE + " try /sapi help");
                } else {
                    if (args[0].equals("help")) {
                        sender.sendMessage(ChatColor.AQUA + "Options:");
                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi reload");
                        sender.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi help");

                        return true;
                    }
                    if (args[0].equals("reload")) {
                        Main.getInstance().reloadConfig();
                        Main.registerConfig();
                        sender.sendMessage("Reloaded!");
                        return true;
                    }
                }
            }
        return true;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onVelEvent(PlayerVelocityEvent event) {
        Player p = event.getPlayer();
        if (this.bean.BeanList.containsKey(p.getUniqueId()) || this.sugar.sugarFly.contains(p.getUniqueId())) {
            if (!p.isOnGround() | (p.getFallDistance() >= 5.0F)) {
                p.setGliding(true);
            } else {
                //this does not mater if player used sugar
                //only for bean
                //jumps
                p.setVelocity(new Vector(0, 1, 0));
            }

            p.getWorld().spawnParticle(
                Particle.FLAME, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()),10);

            p.getWorld().spawnParticle(
                Particle.CAMPFIRE_COSY_SMOKE, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 10);
        }
    }
    

    //Leaving these two separate
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (this.bean.BeanList.containsKey(p.getUniqueId())) {
            double vec;
            if (this.bean.BeanList.get(p.getUniqueId()).intValue() == 0) {
                vec = 0.1D;
            } else {
                vec = this.bean.BeanList.get(p.getUniqueId()) * 0.3D;
            }
            if (!p.isOnGround()) {
                if (!p.isGliding())
                    p.setGliding(true);
                Vector v = p.getVelocity().clone();
                Vector d = p.getLocation().getDirection().clone().multiply(0.1D + vec);
                Vector hor = new Vector(d.getX(), 0.0D, d.getZ());
                Vector horV = new Vector(v.getX(), 0.0D, v.getZ());
                Vector vert = new Vector(0.0D, d.getY(), 0.0D);
                Vector use = hor.add(vert);
                if (hor.clone().add(horV).lengthSquared() >= 1.0D)
                    use = vert;
                p.setVelocity(v.add(use));
            } else {
                p.setVelocity(new Vector(0.0D, 0.1D, 0.0D));
            }
        }
        if (this.sugar.sugarFly.contains(p.getUniqueId())) {
            if (!p.isOnGround() | (p.getFallDistance() >= 5.0F)) {
                double num;
                p.setGliding(true);
                if (this.sugar.sugarList.containsKey(p.getUniqueId()) && (this.sugar.sugarList.get(p.getUniqueId())).intValue() >= 1) {
                    num = this.sugar.sugarList.get(p.getUniqueId()) * 0.2D;
                } else {
                    num = 0.2D;
                }

                p.setVelocity(new Vector(0.0D, num, 0.0D));
            } else {
                p.setVelocity(new Vector(0, 1, 0));
            }
        }
    }

    @EventHandler
    public void onToggleElytra(PlayerToggleFlightEvent e) {
        if (this.bean.BeanList.containsKey(e.getPlayer().getUniqueId()) || this.sugar.sugarFly.contains(e.getPlayer().getUniqueId()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        handleleave(e, this.lettuce.LettuceList, null);
        handleleave(e, this.sugar.sugarList, this.sugar.sugarFly);
        handleleave(e, this.bean.BeanList, null);
        handleleave(e, this.crystal.CrystalList, null);
        //add anything else here if you want to
    }

    private void handleleave(PlayerQuitEvent quit, HashMap<UUID, Integer> list, ArrayList<UUID> flylist) {
        Player player = quit.getPlayer();
        if (list.containsKey(player.getUniqueId())) {
            list.remove(player.getUniqueId());

            if (!(flylist == null))
                list.remove(player.getUniqueId());
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        handledeath(e, this.lettuce.LettuceList, "died by a exploding battery", null);
        handledeath(e, this.sugar.sugarList, "died by a exploding battery", this.sugar.sugarFly);
        handledeath(e, this.bean.BeanList, "died by a exploding battery", null);
        handledeath(e, this.crystal.CrystalList, "died by a exploding battery", null);
        //add anything else here if you want to
    }

    private void handledeath(PlayerDeathEvent death, HashMap<UUID, Integer> list, String message, ArrayList<UUID> flylist) {
        Player deadplayer = death.getEntity();
        if (list.containsKey(deadplayer.getUniqueId())) {
            if ((list.get(deadplayer.getUniqueId())).intValue() >= 5)
                death.setDeathMessage(deadplayer.getName() + " " + message);
            list.remove(deadplayer.getUniqueId());

            if (!(flylist == null))
                flylist.remove(deadplayer.getUniqueId());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        handlebreak(e, Main.LettuceItemValues, this.lettuce, Material.FERN);
        handlebreak(e, Main.SugarItemValues, this.sugar, Material.SUGAR_CANE);
        handlebreak(e, Main.BeanItemValues, this.bean, Material.COCOA);
        handlebreak(e, Main.CrystalItemValues, this.crystal, Material.AMETHYST_BLOCK);
        handlebreak(e, Main.SpecialSauceItemValues, this.sauce, Material.OAK_WOOD);
        //add anything else here if you want to
    }

    private void handlebreak(BlockBreakEvent event, ItemValues itemvalues, Item item, Material block) {
        if (itemvalues.getStatus() == true && event.getBlock().getType() == block && Math.random() < itemvalues.getRate()) {
            Location loc = event.getBlock().getLocation();
            World world = loc.getWorld();
            assert world != null;
            world.dropItem(loc, item.getitem());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getType() != Material.AIR &&
            (e.getAction().equals(Action.RIGHT_CLICK_AIR) |
             e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.FERN) &&
                ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta()))
                    .getDisplayName()
                    .equals(ChatColor.DARK_PURPLE + "Lettuce")) {
                this.lettuce.PlayerInteract(p, item, this.lettuce);
            } else if (item.getType().equals(Material.SUGAR) &&
                       ((ItemMeta)Objects.<ItemMeta>requireNonNull(
                            item.getItemMeta()))
                           .getDisplayName()
                           .equals(ChatColor.DARK_PURPLE + "Sugar")) {
                this.sugar.PlayerInteract(p, item, this.sugar);
            } else if (item.getType().equals(Material.COCOA_BEANS) &&
                       ((ItemMeta)Objects.<ItemMeta>requireNonNull(
                            item.getItemMeta()))
                           .getDisplayName()
                           .equals(ChatColor.DARK_PURPLE + "Bean")) {
                this.bean.PlayerInteract(p, item, this.bean);
            } else if (item.getType().equals(Material.AMETHYST_SHARD) &&
                       ((ItemMeta)Objects.<ItemMeta>requireNonNull(
                            item.getItemMeta()))
                           .getDisplayName()
                           .equals(ChatColor.DARK_PURPLE + "Crystals")) {
                this.crystal.PlayerInteract(p, item, this.crystal);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.FERN) &&
                ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta()))
                    .getDisplayName()
                    .equals(ChatColor.DARK_PURPLE + "Lettuce")) {
                e.setCancelled(true);
                e.getBlock()
                    .getWorld()
                    .getBlockAt(e.getBlock().getLocation())
                    .setType(Material.AIR);
            }
            if (item.getType().equals(Material.COCOA_BEANS) &&
                ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta()))
                    .getDisplayName()
                    .equals(ChatColor.DARK_PURPLE + "Bean")) {
                e.setCancelled(true);
                e.getBlock()
                    .getWorld()
                    .getBlockAt(e.getBlock().getLocation())
                    .setType(Material.AIR);
            }
        }
    }
}