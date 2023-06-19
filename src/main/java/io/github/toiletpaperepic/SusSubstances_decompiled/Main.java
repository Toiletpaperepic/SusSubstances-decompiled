package io.github.toiletpaperepic.SusSubstances_decompiled;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main plugin;
  
    protected Lettuce lettuce;
    protected Events events;
    protected Sugar sugar;
    protected Bean bean;
  
    private FileConfiguration config;

    static String LettuceStatus;
    static Double LettuceRate;
    static String SugarStatus;
    static Double SugarRate;
    static String BeanStatus;
    static Double BeanRate;
    static String CrystalStatus;
    static Double CrystalRate;
    static String SpecialSauceStatus;
    static Double SpecialSauceRate;
  
    public Main() {
        plugin = this;
    }
  
    public static Main getInstance() {
        return plugin;
    }
  
    @Override
    public void onEnable() {
        config = getConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " starting up...");
        loadDefaultConfig();
      
        if (!plugin.getConfig().getBoolean("enable-plugin")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " You have disabled the plugin in the config...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
      
        lettuce = new Lettuce();
        events = new Events(this);
        sugar = new Sugar();
        bean = new Bean();
        registerConfig();
        registerGlow();
      
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " finished!");
    }
  
    @Override
    public void onDisable() {
        if (plugin.getConfig().getBoolean("enable-plugin")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " shutting down...");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " Goodbye!");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabling plugin...");
        }
    }
  
    private void loadDefaultConfig() {
        config.addDefault("enable-plugin", true);
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
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " registering substances...");
      
        LettuceStatus = plugin.getConfig().getString("lettuce.enabled");
        LettuceRate = plugin.getConfig().getDouble("lettuce.drop-rate", 0.3);
        if (LettuceRate.isNaN()) {
            plugin.getConfig().set("lettuce.drop-rate", 0.3);
        }
      
        if (LettuceStatus != null && LettuceStatus.equalsIgnoreCase("true")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled lettuce");
        } else {
            LettuceStatus = "false";
            plugin.getConfig().set("lettuce.enabled", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled lettuce");
        }
      
        SugarStatus = plugin.getConfig().getString("sugar.enabled");
        SugarRate = plugin.getConfig().getDouble("sugar.drop-rate", 0.3);
        if (SugarRate.isNaN()) {
            plugin.getConfig().set("sugar.drop-rate", 0.3);
        }
      
        if (SugarStatus != null && SugarStatus.equalsIgnoreCase("true")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled sugar");
        } else {
            SugarStatus = "false";
            plugin.getConfig().set("sugar.enabled", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " disabled sugar");
        }
      
        BeanStatus = plugin.getConfig().getString("bean.enabled");
        BeanRate = plugin.getConfig().getDouble("bean.drop-rate", 0.3);
        if (BeanRate.isNaN()) {
            plugin.getConfig().set("bean.drop-rate", 0.3);
        }
      
        if (BeanStatus != null && BeanStatus.equalsIgnoreCase("true")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled bean");
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
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled crystals");
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
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SusSubstances-decompiled" + ChatColor.DARK_GRAY + "]" + ChatColor.GREEN + " enabled special sauce");
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
            NamespacedKey key = new NamespacedKey(this, getDescription().getName());
            Glow glow = new Glow(key);
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException illegalArgumentException) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}