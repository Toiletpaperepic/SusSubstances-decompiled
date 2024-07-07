package toiletpaperepic.SusSubstances;

import java.util.logging.Level;
import java.util.HashMap;
import java.util.UUID;

import com.google.common.collect.Maps;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Sauce implements Item {
	public HashMap<UUID, Integer> sauceList = Maps.newHashMap();
	public Plugin plugin = (Plugin)Main.getPlugin(Main.class);

	private ItemValues itemvalues = new ItemValues();

    public ItemValues getitemvalues() {
        itemvalues.block = Material.OAK_WOOD;
        itemvalues.material = Material.HONEY_BLOCK;
        itemvalues.deathmessage = "somehow died";
        itemvalues.ItemList = sauceList;
        itemvalues.name = "Sauce";
        return itemvalues;
    };
	  
	public ItemStack getitemsstack() {
	  ItemStack sauce = new ItemStack(getitemvalues().material);
	  sauce.setAmount(1);
	  ItemMeta m = sauce.getItemMeta();
	  assert m != null;
	  
	  m.setDisplayName(ChatColor.DARK_PURPLE + getitemvalues().name);
	  sauce.setItemMeta(m);
	  return sauce;
	}

	public void PlayerInteract(final Player p, ItemStack item) {
        p.sendMessage(ChatColor.RED + getitemvalues().name + " is not implemented!");
		Main.log.log(Level.SEVERE, getitemvalues().name + " is not implemented!");
    }
}
