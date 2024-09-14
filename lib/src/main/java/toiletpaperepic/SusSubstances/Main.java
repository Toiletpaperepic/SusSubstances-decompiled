package toiletpaperepic.SusSubstances;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.logging.Level;
import org.bukkit.Bukkit;

public final class Main extends JavaPlugin {
    private FileConfiguration config;
    private static Main plugin;
    public static Logger log;
    public Events events;

    public static ArrayList<Item> itemslist;
    
    public Main() {plugin = this;}
    public static Main getInstance() { return plugin; }
    
    @Override
    public void onEnable() {
        log = getLogger();
        log.log(Level.INFO, "starting up...");
        
        config = getConfig();

        setitemslist();
        loadDefaultConfig();
        
        if (!plugin.getConfig().getBoolean("enable-plugin")) {
            log.log(Level.WARNING,"You have disabled the plugin in the config...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        events = new Events(this);

        registerConfig();

        log.log(Level.INFO, "finished!");
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

    void setitemslist() {
        itemslist = new ArrayList<Item>();

        itemslist.add(new Crystals());
        itemslist.add(new Lettuce());
        itemslist.add(new Sauce());
        itemslist.add(new Sugar());
        itemslist.add(new Bean());
    }

    static void registerConfig() {
        log.log(Level.INFO, "registering substances...");

        for (int i = 0; i < itemslist.size(); i++) {
            ItemValues item = readpluginconf(itemslist.get(i).getitemvalues().name);
            itemslist.get(i).getitemvalues().status = item.status;
            itemslist.get(i).getitemvalues().rate = item.rate;
            item = null;
        }
    }

    private static ItemValues readpluginconf(String itemread) {
        Boolean status = plugin.getConfig().getBoolean(itemread.toLowerCase() + ".enabled");
        Double  rate   = plugin.getConfig().getDouble(itemread.toLowerCase() + ".drop-rate", 0.3);

        if (rate.isNaN())
            plugin.getConfig().set(itemread + ".drop-rate", 0.3);

        if (status == true) {
            log.log(Level.INFO, "enabled " + itemread);
        } else {
            status = false;
            plugin.getConfig().set(itemread + ".enabled", status);
            log.log(Level.INFO, "disabled " + itemread);
        }

        ItemValues item = new ItemValues();
        item.status     = status;
        item.rate       = rate;

        return item;
    }

    ///makes config.yml
    private void loadDefaultConfig() {
        config.addDefault("enable-plugin", true);

        for (int i = 0; i < itemslist.size(); i++) {
            String name = itemslist.get(i).getitemvalues().name.toLowerCase();

            config.addDefault(name + ".enabled", true);
            config.addDefault(name + ".drop-rate", 0.3);
        }

        config.options().copyDefaults(true);
        saveConfig();
    }
}