package io.github.toiletpaperepic.SusSubstances_decompiled;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

public final class Main extends JavaPlugin {
    private static Main plugin;
  
    protected Crystals crystal;
    protected Lettuce lettuce;
    protected Events events;
    protected Sauce sauce;
    protected Sugar sugar;
    protected Bean bean;
  
    private FileConfiguration config;

    static String SpecialSauceStatus;
    static Double SpecialSauceRate;
    static String CrystalStatus;
    static String LettuceStatus;
    static Double LettuceRate;
    static String SugarStatus;
    static Double CrystalRate;
    static String BeanStatus;
    static Double SugarRate;
    static Double BeanRate;

    static Logger log;
  
    public Main() {
        plugin = this;
    }
  
    public static Main getInstance() {
        return plugin;
    }
    
    @Override
    public void onEnable() {
        log = getLogger();
        config = getConfig();
        log.log(Level.INFO, " starting up...");
        loadDefaultConfig();
        
        if (!plugin.getConfig().getBoolean("enable-plugin")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " You have disabled the plugin in the config...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        
        events = new Events(this);
        crystal = new Crystals();
        lettuce = new Lettuce();
        sauce = new Sauce();
        sugar = new Sugar();
        bean = new Bean();

        
        registerConfig();
        registerGlow();

        log.log(Level.INFO, "finished!");
    }
  
    @Override
    public void onDisable() {
        if (plugin.getConfig().getBoolean("enable-plugin")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " shutting down...");
            log.log(Level.INFO, " Goodbye!");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabling plugin...");
        }
    }
  
    private void loadDefaultConfig() {
        config.addDefault("enable-plugin", true);
        config.addDefault("original-Text", true);
        config.addDefault("lettuce.enabled", true);
        config.addDefault("lettuce.drop-rate", 0.3);
        config.addDefault("sugar.enabled", true);
        config.addDefault("sugar.drop-rate", 0.3);
        config.addDefault("bean.enabled", true);
        config.addDefault("bean.drop-rate", 0.3);
        config.addDefault("crystals.enabled", true);
        config.addDefault("crystals.drop-rate", 0.3);
        config.addDefault("special-sauce.enabled", false);
        config.addDefault("special-sauce.drop-rate", 0.9);
        config.options().copyDefaults(true);
        saveConfig();
    }
  
    static void registerConfig() {
    	log.log(Level.INFO ," registering substances...");
    	
        SugarStatus = plugin.getConfig().getString("sugar.enabled");
        SugarRate = plugin.getConfig().getDouble("sugar.drop-rate", 0.3);
        if (SugarRate.isNaN()) {
            plugin.getConfig().set("sugar.drop-rate", 0.3);
        }
      
        if (SugarStatus != null && SugarStatus.equalsIgnoreCase("true")) {
            log.log(Level.INFO ," enabled sugar");
        } else {
            SugarStatus = "false";
            plugin.getConfig().set("sugar.enabled", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled sugar");
        }
        
        LettuceStatus = plugin.getConfig().getString("lettuce.enabled");
        LettuceRate = plugin.getConfig().getDouble("lettuce.drop-rate", 0.3);
        if (LettuceRate.isNaN()) {
            plugin.getConfig().set("lettuce.drop-rate", 0.3);
        }
      
        if (LettuceStatus != null && LettuceStatus.equalsIgnoreCase("true")) {
            log.log(Level.INFO ," enabled lettuce");
        } else {
            LettuceStatus = "false";
            plugin.getConfig().set("lettuce.enabled", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled lettuce");
        }
      
        BeanStatus = plugin.getConfig().getString("bean.enabled");
        BeanRate = plugin.getConfig().getDouble("bean.drop-rate", 0.3);
        if (BeanRate.isNaN()) {
            plugin.getConfig().set("bean.drop-rate", 0.3);
        }
      
        if (BeanStatus != null && BeanStatus.equalsIgnoreCase("true")) {
            log.log(Level.INFO ," enabled bean");
        } else {
            BeanStatus = "false";
            plugin.getConfig().set("bean.enabled", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled bean");
        }
      
        CrystalStatus = plugin.getConfig().getString("crystals.enabled");
        CrystalRate = plugin.getConfig().getDouble("crystals.drop-rate", 0.3);
        if (CrystalRate.isNaN()) {
            plugin.getConfig().set("crystals.drop-rate", 0.3);
        }
      
        if (CrystalStatus != null && CrystalStatus.equalsIgnoreCase("true")) {
            log.log(Level.INFO ," enabled crystals");
        } else {
            CrystalStatus = "false";
            plugin.getConfig().set("crystals.enabled", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled crystals");
        }
      
        SpecialSauceStatus = plugin.getConfig().getString("special-sauce.enabled");
        SpecialSauceRate = plugin.getConfig().getDouble("special-sauce.drop-rate", 0.3);
        if (SpecialSauceRate.isNaN()) {
            plugin.getConfig().set("special-sauce.drop-rate", 0.3);
        }
      
        if (SpecialSauceStatus != null && SpecialSauceStatus.equalsIgnoreCase("true")) {
            log.log(Level.INFO ," enabled special sauce");
        } else {
            SpecialSauceStatus = "false";
            plugin.getConfig().set("special-sauce.enabled", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled special sauce");
        }
    }
  
    private void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
      
        try {
            Glow glow = new Glow(70);
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException illegalArgumentException) {
            // what happend here?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}