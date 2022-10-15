package io.github.toiletpaperepic.SusSubstances_decompiled;

import java.lang.reflect.Field;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
  private static Main plugin;
  
  public Lettuce lettuce;
  
  public Events events;
  
  public Sugar sugar;
  
  public Bean bean;
  
  FileConfiguration config = getConfig();
  
  public static String LettuceStatus;
  
  public static Double LettuceRate;
  
  public static String SugarStatus;
  
  public static Double SugarRate;
  
  public static String BeanStatus;
  
  public static Double BeanRate;
  
  public static String CrystalStatus;
  
  public static Double CrystalRate;
  
  public static String SpecialSauceStatus;
  
  public static Double SpecialSauceRate;
  
  public Main() {
    plugin = this;
  }
  
  public static Main getInstance() {
    return plugin;
  }
  
  public void onEnable() {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " starting up...");
    this.config.addDefault("enable-plugin", Boolean.valueOf(true));
    this.config.addDefault("lettuce.enabled", Boolean.valueOf(true));
    this.config.addDefault("lettuce.drop-rate", Double.valueOf(0.3D));
    this.config.addDefault("sugar.enabled", Boolean.valueOf(true));
    this.config.addDefault("sugar.drop-rate", Double.valueOf(0.3D));
    this.config.addDefault("bean.enabled", Boolean.valueOf(true));
    this.config.addDefault("bean.drop-rate", Double.valueOf(0.3D));
    this.config.addDefault("crystals.enabled", Boolean.valueOf(true));
    this.config.addDefault("crystals.drop-rate", Double.valueOf(0.3D));
    this.config.addDefault("special-sauce.enabled", Boolean.valueOf(true));
    this.config.addDefault("special-sauce.drop-rate", Double.valueOf(0.9D));
    this.config.options().copyDefaults(true);
    saveConfig();
    if (((String)Objects.<String>requireNonNull(plugin.getConfig().getString("enable-plugin"))).equalsIgnoreCase("false")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " You have disabled the plugin in the config...");
      Bukkit.getPluginManager().disablePlugin((Plugin)this);
      return;
    } 
    this.lettuce = new Lettuce();
    this.events = new Events(this);
    this.sugar = new Sugar();
    this.bean = new Bean();
    registerConfig();
    registerGlow();
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " finished!");
  }
  
  public void onDisable() {
    if (((String)Objects.<String>requireNonNull(plugin.getConfig().getString("enable-plugin"))).equalsIgnoreCase("true")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " shutting down...");
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Goodbye!");
    } else {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabling plugin...");
    } 
  }
  
  public static void registerConfig() {
    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " registering substances...");
    LettuceStatus = plugin.getConfig().getString("lettuce.enabled");
    LettuceRate = Double.valueOf(Double.parseDouble(Objects.<String>requireNonNull(plugin.getConfig().getString("lettuce.drop-rate"))));
    if (LettuceRate.isNaN())
      plugin.getConfig().set("lettuce.drop-rate", Double.valueOf(0.3D)); 
    if (LettuceStatus != null && LettuceStatus.equalsIgnoreCase("true")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled lettuce");
    } else {
      LettuceStatus = "false";
      plugin.getConfig().set("lettuce.enabled", Boolean.valueOf(false));
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled lettuce");
    } 
    SugarStatus = plugin.getConfig().getString("sugar.enabled");
    SugarRate = Double.valueOf(Double.parseDouble(Objects.<String>requireNonNull(plugin.getConfig().getString("sugar.drop-rate"))));
    if (SugarRate.isNaN())
      plugin.getConfig().set("sugar.drop-rate", Double.valueOf(0.3D)); 
    if (SugarStatus != null && SugarStatus.equalsIgnoreCase("true")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled sugar");
    } else {
      SugarStatus = "false";
      plugin.getConfig().set("sugar.enabled", Boolean.valueOf(false));
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled sugar");
    } 
    BeanStatus = plugin.getConfig().getString("bean.enabled");
    BeanRate = Double.valueOf(Double.parseDouble(Objects.<String>requireNonNull(plugin.getConfig().getString("bean.drop-rate"))));
    if (BeanRate.isNaN())
      plugin.getConfig().set("bean.drop-rate", Double.valueOf(0.3D)); 
    if (BeanStatus != null && BeanStatus.equalsIgnoreCase("true")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled bean");
    } else {
      BeanStatus = "false";
      plugin.getConfig().set("bean.enabled", Boolean.valueOf(false));
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled bean");
    } 
    CrystalStatus = plugin.getConfig().getString("crystals.enabled");
    CrystalRate = Double.valueOf(Double.parseDouble(Objects.<String>requireNonNull(plugin.getConfig().getString("crystals.drop-rate"))));
    if (CrystalRate.isNaN())
      plugin.getConfig().set("crystals.drop-rate", Double.valueOf(0.3D)); 
    if (CrystalStatus != null && CrystalStatus.equalsIgnoreCase("true")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled crystals");
    } else {
      CrystalStatus = "false";
      plugin.getConfig().set("crystals.enabled", Boolean.valueOf(false));
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled crystals");
    } 
    SpecialSauceStatus = plugin.getConfig().getString("special-sauce.enabled");
    SpecialSauceRate = Double.valueOf(Double.parseDouble(Objects.<String>requireNonNull(plugin.getConfig().getString("special-sauce.drop-rate"))));
    if (SpecialSauceRate.isNaN())
      plugin.getConfig().set("special-sauce.drop-rate", Double.valueOf(0.3D)); 
    if (SpecialSauceStatus != null && SpecialSauceStatus.equalsIgnoreCase("true")) {
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled special sauce");
    } else {
      SpecialSauceStatus = "false";
      plugin.getConfig().set("special-sauce.enabled", Boolean.valueOf(false));
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled special sauce");
    } 
  }
  
  public void registerGlow() {
    try {
      Field f = Enchantment.class.getDeclaredField("acceptingNew");
      f.setAccessible(true);
      f.set(null, Boolean.valueOf(true));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      NamespacedKey key = new NamespacedKey((Plugin)this, getDescription().getName());
      Glow glow = new Glow(key);
      Enchantment.registerEnchantment(glow);
    } catch (IllegalArgumentException illegalArgumentException) {
    
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}