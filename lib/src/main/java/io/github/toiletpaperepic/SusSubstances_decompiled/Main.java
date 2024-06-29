package io.github.toiletpaperepic.sussubstances_decompiled;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.bukkit.Bukkit;

public final class Main extends JavaPlugin {
    private static Main plugin;

    protected Crystals crystal;
    protected Lettuce  lettuce;
    protected Events   events;
    protected Sauce    sauce;
    protected Sugar    sugar;
    protected Bean     bean;

    private FileConfiguration config;

    static ItemValues SpecialSauceItemValues;
    static ItemValues LettuceItemValues;
    static ItemValues CrystalItemValues;
    static ItemValues SugarItemValues;
    static ItemValues BeanItemValues;

    static Logger log;

    public Main() {
        //TODO: ADD USER BOX ERROR HERE
        
        plugin = this;
    }

    public static Main getInstance() { return plugin; }

    @Override
    public void onEnable() {
        config = getConfig();
        log    = getLogger();
        log.log(Level.INFO, " starting up...");

        loadDefaultConfig();

        if (!plugin.getConfig().getBoolean("enable-plugin")) {
            log.log(Level.WARNING," You have disabled the plugin in the config...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        events  = new Events(this);
        crystal = new Crystals();
        lettuce = new Lettuce();
        sauce   = new Sauce();
        sugar   = new Sugar();
        bean    = new Bean();

        registerConfig();

        log.log(Level.INFO, "finished!");
    }

    static void registerConfig() {
        log.log(Level.INFO, " registering substances...");

        SpecialSauceItemValues = readpluginconf("special-sauce");
        LettuceItemValues      = readpluginconf("lettuce");
        CrystalItemValues      = readpluginconf("crystals");
        SugarItemValues        = readpluginconf("sugar");
        BeanItemValues         = readpluginconf("bean");
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

    static class ItemValues {
        private Boolean status;
        private Double  rate;

        public Boolean getStatus() { return status; }
        public Double getRate() { return rate; }
    }

    private static ItemValues readpluginconf(String item) {
        Boolean status = plugin.getConfig().getBoolean(item + ".enabled");
        Double  rate   = plugin.getConfig().getDouble(item + ".drop-rate", 0.3);
        if (rate.isNaN()) {
            plugin.getConfig().set(item + ".drop-rate", 0.3);
        }

        if (status == true) {
            log.log(Level.INFO, "enabled " + item);
        } else {
            status = false;
            plugin.getConfig().set(item + ".enabled", status);
            log.log(Level.INFO, "disabled " + item);
        }

        ItemValues doneitem = new ItemValues();
        doneitem.status     = status;
        doneitem.rate       = rate;

        return doneitem;
    }

    @Override
    public void onDisable() {
        if (plugin.getConfig().getBoolean("enable-plugin")) {
            log.log(Level.INFO, "shutting down...");
            log.log(Level.INFO, "Goodbye!");
        } else {
            log.log(Level.INFO, "disabling plugin...");
        }
    }
}