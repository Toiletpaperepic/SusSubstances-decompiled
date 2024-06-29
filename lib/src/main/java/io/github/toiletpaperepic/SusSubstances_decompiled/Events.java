package io.github.toiletpaperepic.sussubstances_decompiled;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
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
  public Sauce sauce = new Sauce();
  public Sugar sugar = new Sugar();
  public Bean bean = new Bean();
  
  public Events(Main main) {
    ((Main)Main.getPlugin(Main.class)).getServer().getPluginManager().registerEvents(this, (Plugin)main);
    ((PluginCommand)Objects.<PluginCommand>requireNonNull(Bukkit.getPluginCommand("sapi"))).setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equals("sapi"))
      if (sender instanceof Player) {
        Player player = (Player)sender;
        if (player.hasPermission("substances.perms"))
          if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Invalid Syntax!" + ChatColor.WHITE + " try /sapi help");
          } else {
            if (args[0].equals("help")) {
              player.sendMessage(ChatColor.AQUA + "Options:");
              player.sendMessage("");
              player.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi reload");
              player.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi help");
              return true;
            } 
            if (args[0].equals("reload")) {
              Main.getInstance().reloadConfig();
              Main.registerConfig();
              player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " Reloaded!");
              return true;
            } 
          }  
      } else {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        if (args.length == 0) {
          console.sendMessage(ChatColor.RED + "Invalid Syntax!" + ChatColor.WHITE + " try /sapi help");
        } else {
          if (args[0].equals("help")) {
            console.sendMessage(ChatColor.AQUA + "Options:");
            console.sendMessage("");
            console.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi reload");
            console.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi help");
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
    if (this.crystal.CrystalList.containsKey(player.getUniqueId())) {
        this.crystal.CrystalList.remove(player.getUniqueId());
        for (PotionEffect effect : player.getActivePotionEffects())
          player.removePotionEffect(effect.getType()); 
    }
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
    } else if (this.crystal.CrystalList.containsKey(player.getUniqueId())) {
      if (((Integer)this.crystal.CrystalList.get(player.getUniqueId())).intValue() >= 5)
        e.setDeathMessage(player.getName() + " died by a exploding battery"); 
      this.crystal.CrystalList.remove(player.getUniqueId());
    } 
  }
  
  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    if (Main.LettuceItemValues.getStatus() == true && e.getBlock().getType().equals(Material.FERN) && Math.random() < Main.LettuceItemValues.getRate()) {
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.lettuce.getLettuce());
    } 
    if (Main.SugarItemValues.getStatus() == true && e.getBlock().getType().equals(Material.SUGAR_CANE) && Math.random() < Main.SugarItemValues.getRate()) {
      e.setDropItems(false);
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.sugar.getSugar());
    } 
    if (Main.BeanItemValues.getStatus() == true && e.getBlock().getType().equals(Material.COCOA) && Math.random() < Main.BeanItemValues.getRate()) {
      e.setDropItems(false);
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.bean.getBean());
    } 
    if (Main.CrystalItemValues.getStatus() == true && e.getBlock().getType().equals(Material.AMETHYST_BLOCK) && Math.random() < Main.BeanItemValues.getRate()) {
      e.setDropItems(false);
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.crystal.getCrystal());
    } 
    if (Main.SpecialSauceItemValues.getStatus() == true && e.getBlock().getType().equals(Material.OAK_WOOD) && Math.random() < Main.BeanItemValues.getRate()) {
      e.setDropItems(false);
      Location loc = e.getBlock().getLocation();
      World world = loc.getWorld();
      assert world != null;
      world.dropItem(loc, this.sauce.getSauce());
    }
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    if (p.getInventory().getItemInMainHand().getType() != Material.AIR && (
      e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) ) {
      ItemStack item = p.getInventory().getItemInMainHand();
      if (item.getType().equals(Material.FERN) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Lettuce")) {
        this.lettuce.PlayerInteract(p, item, this.lettuce);
      } 
      else if (item.getType().equals(Material.SUGAR) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Sugar")) {
        this.sugar.PlayerInteract(p, item, this.sugar);
      } 
      else if (item.getType().equals(Material.COCOA_BEANS) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Bean")) {
        this.bean.PlayerInteract(p, item, this.bean);
      } 
      else if (item.getType().equals(Material.AMETHYST_SHARD) && ((ItemMeta)Objects.<ItemMeta>requireNonNull(item.getItemMeta())).getDisplayName().equals(ChatColor.DARK_PURPLE + "Crystals")) {
        this.crystal.PlayerInteract(p, item, this.crystal);
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