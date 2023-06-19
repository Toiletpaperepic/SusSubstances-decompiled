package io.github.toiletpaperepic.SusSubstances_decompiled;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class Events implements Listener, CommandExecutor {
  public Crystals crystal = new Crystals();
  public Lettuce lettuce = new Lettuce();
  public Sugar sugar = new Sugar();
  public Bean bean = new Bean();
  
  public Events(Main main) {
    ((Main)Main.getPlugin(Main.class)).getServer().getPluginManager().registerEvents(this, (Plugin)main);
    ((PluginCommand)Objects.<PluginCommand>requireNonNull(Bukkit.getPluginCommand("wapi"))).setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equals("wapi"))
      if (sender instanceof Player) {
        Player player = (Player)sender;
        if (player.hasPermission("walmartmc.sus"))
          if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Invalid Syntax!" + ChatColor.WHITE + " try /wapi help");
          } else {
            if (args[0].equals("help")) {
              player.sendMessage(ChatColor.AQUA + "Options:");
              player.sendMessage("");
              player.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/wapi reload");
              player.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/wapi help");
              return true;
            } 
            if (args[0].equals("reload")) {
              Main.getInstance().reloadConfig();
              Main.registerConfig();
              player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " Reloaded!");
              return true;
            } 
          }  
      } else {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        if (args.length == 0) {
          console.sendMessage(ChatColor.RED + "Invalid Syntax!" + ChatColor.WHITE + " try /wapi help");
        } else {
          if (args[0].equals("help")) {
            console.sendMessage(ChatColor.AQUA + "Options:");
            console.sendMessage("");
            console.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/wapi reload");
            console.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/wapi help");
            return true;
          } 
          if (args[0].equals("reload")) {
            Main.getInstance().reloadConfig();
            Main.registerConfig();
            console.sendMessage(ChatColor.GREEN + "Reloaded!");
            return true;
          } 
        } 
      }  
    return true;
  }
  
  @SuppressWarnings("deprecation")
@EventHandler
  public void onVelEvent(PlayerVelocityEvent event) {
    Player p = event.getPlayer();
    if (this.bean.BeanList.containsKey(p.getUniqueId())) {
      if (((!p.isOnGround() ? 1 : 0) | ((p.getFallDistance() >= 5.0F) ? 1 : 0)) != 0) {
        p.setGliding(true);
      } else {
        Vector vec = new Vector(0, 1, 0);
        p.setVelocity(vec);
      } 
      p.getWorld().spawnParticle(Particle.FLAME, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 10);
      p.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 10);
    } 
    if (this.sugar.sugarFly.contains(p.getUniqueId())) {
      if (((!p.isOnGround() ? 1 : 0) | ((p.getFallDistance() >= 5.0F) ? 1 : 0)) != 0) {
        p.setGliding(true);
      } else {
        Vector vec = new Vector(0, 1, 0);
        p.setVelocity(vec);
      } 
      p.getWorld().spawnParticle(Particle.FLAME, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 10);
      p.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 10);
    } 
  }
  
  @SuppressWarnings("deprecation")
@EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    Player p = event.getPlayer();
    if (this.bean.BeanList.containsKey(p.getUniqueId())) {
      double vec;
      if (((Integer)this.bean.BeanList.get(p.getUniqueId())).intValue() == 0) {
        vec = 0.1D;
      } else {
        vec = ((Integer)this.bean.BeanList.get(p.getUniqueId())).intValue() * 0.3D;
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
    if (this.sugar.sugarFly.contains(p.getUniqueId()))
      if (((!p.isOnGround() ? 1 : 0) | ((p.getFallDistance() >= 5.0F) ? 1 : 0)) != 0) {
        double num;
        p.setGliding(true);
        if (this.sugar.sugarList.containsKey(p.getUniqueId()) && ((Integer)this.sugar.sugarList.get(p.getUniqueId())).intValue() >= 1) {
          num = ((Integer)this.sugar.sugarList.get(p.getUniqueId())).intValue() * 0.2D;
        } else {
          num = 0.2D;
        } 
        Vector vector = new Vector(0.0D, num, 0.0D);
        p.setVelocity(vector);
      } else {
        Vector vec = new Vector(0, 1, 0);
        p.setVelocity(vec);
      }  
  }
  
  @EventHandler
  public void onToggleElytra(PlayerToggleFlightEvent e) {
    if (this.bean.BeanList.containsKey(e.getPlayer().getUniqueId()))
      e.setCancelled(true); 
    if (this.sugar.sugarFly.contains(e.getPlayer().getUniqueId()))
      e.setCancelled(true); 
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    Player player = e.getPlayer();
    if (this.lettuce.LettuceList.containsKey(player.getUniqueId())) {
      this.lettuce.LettuceList.remove(player.getUniqueId());
      for (PotionEffect effect : player.getActivePotionEffects())
        player.removePotionEffect(effect.getType()); 
    } 
    if (this.sugar.sugarList.containsKey(player.getUniqueId())) {
      this.sugar.sugarList.remove(player.getUniqueId());
      this.sugar.sugarFly.remove(player.getUniqueId());
      for (PotionEffect effect : player.getActivePotionEffects())
        player.removePotionEffect(effect.getType()); 
    } 
    if (this.bean.BeanList.containsKey(player.getUniqueId())) {
      this.bean.BeanList.remove(player.getUniqueId());
      for (PotionEffect effect : player.getActivePotionEffects())
        player.removePotionEffect(effect.getType()); 
    } 
//    if (this.crystal.CrystalList.containsKey(player.getUniqueId())) {
//        this.crystal.CrystalList.remove(player.getUniqueId());
//        for (PotionEffect effect : player.getActivePotionEffects())
//          player.removePotionEffect(effect.getType()); 
//    }
  }
  
  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    Player player = e.getEntity();
    if (this.lettuce.LettuceList.containsKey(player.getUniqueId())) {
      if (((Integer)this.lettuce.LettuceList.get(player.getUniqueId())).intValue() >= 5)
        e.setDeathMessage(player.getName() + " got too high and exploded"); 
      this.lettuce.LettuceList.remove(player.getUniqueId());
    } else if (this.sugar.sugarList.containsKey(player.getUniqueId())) {
      if (((Integer)this.sugar.sugarList.get(player.getUniqueId())).intValue() >= 5)
        e.setDeathMessage(player.getName() + " got too high and died"); 
      this.sugar.sugarList.remove(player.getUniqueId());
      this.sugar.sugarFly.remove(player.getUniqueId());
    } else if (this.bean.BeanList.containsKey(player.getUniqueId())) {
      if (((Integer)this.bean.BeanList.get(player.getUniqueId())).intValue() >= 5)
        e.setDeathMessage(player.getName() + " eated too much BEAN"); 
      this.bean.BeanList.remove(player.getUniqueId());
    } 
  }
  
  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    if (Main.LettuceStatus.equalsIgnoreCase("true") && 
      e.getBlock().getType().equals(Material.FERN) && 
      Math.random() < Main.LettuceRate.doubleValue()) {
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.lettuce.getLettuce());
    } 
    if (Main.SugarStatus.equalsIgnoreCase("true") && 
      e.getBlock().getType().equals(Material.SUGAR_CANE) && 
      Math.random() < Main.SugarRate.doubleValue()) {
      e.setDropItems(false);
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.sugar.getSugar());
    } 
    if (Main.BeanStatus.equalsIgnoreCase("true") && 
      e.getBlock().getType().equals(Material.COCOA) && 
      Math.random() < Main.BeanRate.doubleValue()) {
      e.setDropItems(false);
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.bean.getBean());
    } 
    if (Main.CrystalStatus.equalsIgnoreCase("true") && 
      e.getBlock().getType().equals(Material.AMETHYST_CLUSTER) && 
      Math.random() < Main.BeanRate.doubleValue()) {
      e.setDropItems(false);
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.crystal.getCrystal());
    } 
    
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    if (p.getInventory().getItemInMainHand().getType() != Material.AIR && (
      e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) ) {
      ItemStack item = p.getInventory().getItemInMainHand();
      if (item.getType().equals(Material.FERN) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Lettuce")) {
        if (Main.LettuceStatus.equalsIgnoreCase("false")) {
          p.sendMessage(ChatColor.RED + "Lettuce is not enabled on this server!");
          return;
        } 
        p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 100.0F, 1.0F);
        p.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 3.0D, p.getLocation().getZ()), 10);
        p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1.0D, p.getLocation().getZ()), 10);
        if (this.lettuce.LettuceList.get(p.getUniqueId()) != null) {
          this.lettuce.LettuceList.replace(p.getUniqueId(), Integer.valueOf(((Integer)this.lettuce.LettuceList.get(p.getUniqueId())).intValue() + 1));
          if (((Integer)this.lettuce.LettuceList.get(p.getUniqueId())).intValue() == 1) {
            p.sendMessage(ChatColor.RED + "Damn. Ambitious");
          } else if (((Integer)this.lettuce.LettuceList.get(p.getUniqueId())).intValue() == 2) {
            p.sendMessage(ChatColor.RED + "Stoned you are");
          } else if (((Integer)this.lettuce.LettuceList.get(p.getUniqueId())).intValue() >= 5) {
            p.getWorld().createExplosion(p.getLocation(), 3.0F, false);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10.0F, 1.0F);
          } else {
            p.sendMessage(ChatColor.RED + "SHEEEEEEEEEEEEEEEEESH");
          } 
        } else {
          this.lettuce.triggerHigh(p);
        } 
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() < 1)
          item = null; 
        p.getInventory().setItemInMainHand(item);
      } else if (item.getType().equals(Material.SUGAR) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Sugar")) {
        if (Main.SugarStatus.equalsIgnoreCase("false")) {
          p.sendMessage(ChatColor.RED + "Sugar is not enabled on this server!");
          return;
        } 
        p.getWorld().spawnParticle(Particle.CRIT_MAGIC, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1.0D, p.getLocation().getZ()), 10);
        p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 100.0F, 1.0F);
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 100.0F, 1.0F);
        if (this.sugar.sugarList.get(p.getUniqueId()) != null) {
          this.sugar.sugarList.replace(p.getUniqueId(), Integer.valueOf(((Integer)this.sugar.sugarList.get(p.getUniqueId())).intValue() + 1));
          if (((Integer)this.sugar.sugarList.get(p.getUniqueId())).intValue() == 1) {
            p.sendMessage(ChatColor.RED + "Damn. Ambitious");
          } else if (((Integer)this.sugar.sugarList.get(p.getUniqueId())).intValue() == 2) {
            p.sendMessage(ChatColor.RED + "Jeez");
          } else if (((Integer)this.sugar.sugarList.get(p.getUniqueId())).intValue() >= 5) {
            p.setHealth(0.0D);
          } else {
            p.sendMessage(ChatColor.RED + "SHEEEEEEEEEEEEEEEEESH");
          } 
        } else {
          this.sugar.triggerHigh(p);
        } 
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() < 1)
          item = null; 
        p.getInventory().setItemInMainHand(item);
      } else if (item.getType().equals(Material.COCOA_BEANS) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Bean")) {
        if (Main.BeanStatus.equalsIgnoreCase("false")) {
          p.sendMessage(ChatColor.RED + "Bean is not enabled on this server!");
          return;
        } 
        p.getWorld().spawnParticle(Particle.SLIME, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1.0D, p.getLocation().getZ()), 10);
        p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 100.0F, 1.0F);
        p.playSound(p.getLocation(), Sound.ENTITY_PARROT_IMITATE_CREEPER, 100.0F, 1.0F);
        if (this.bean.BeanList.get(p.getUniqueId()) != null) {
          this.bean.BeanList.replace(p.getUniqueId(), Integer.valueOf(((Integer)this.bean.BeanList.get(p.getUniqueId())).intValue() + 1));
          if (((Integer)this.bean.BeanList.get(p.getUniqueId())).intValue() == 1) {
            p.sendMessage(ChatColor.RED + "Damn. Ambitious");
          } else if (((Integer)this.bean.BeanList.get(p.getUniqueId())).intValue() == 2) {
            p.sendMessage(ChatColor.RED + "Jeez");
          } else if (((Integer)this.bean.BeanList.get(p.getUniqueId())).intValue() >= 5) {
            p.getWorld().createExplosion(p.getLocation(), 3.0F, false);
          } else {
            p.sendMessage(ChatColor.RED + "SHEEEEEEEEEEEEEEEEESH");
          } 
        } else {
          this.bean.triggerHigh(p);
        } 
        item.setAmount(item.getAmount() - 1);
        if (item.getAmount() < 1)
          item = null; 
        p.getInventory().setItemInMainHand(item); 
      
      } else if (item.getType().equals(Material.AMETHYST_SHARD) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Crystals")) {
        if (Main.CrystalStatus.equalsIgnoreCase("false")) {
          p.sendMessage(ChatColor.RED + "Crystals is not enabled on this server!");
          return;
        } 
        this.crystal.triggerHigh(p);
      }
    } 
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    Player p = e.getPlayer();
    if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
      ItemStack item = p.getInventory().getItemInMainHand();
      if (item.getType().equals(Material.FERN) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Lettuce")) {
        e.setCancelled(true);
        e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
      } 
      if (item.getType().equals(Material.COCOA_BEANS) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Bean")) {
        e.setCancelled(true);
        e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
      } 
    } 
  }
}