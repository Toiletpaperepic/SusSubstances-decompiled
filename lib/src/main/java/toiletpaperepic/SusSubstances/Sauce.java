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
	public HashMap<UUID, Integer> CrystalList = Maps.newHashMap();
	public Plugin plugin = (Plugin)Main.getPlugin(Main.class);

	public String getname() {return "Sauce";};
    public Material getmaterial() {return Material.HONEY_BLOCK;};
	  
	public ItemStack getitem() {
	  ItemStack sauce = new ItemStack(getmaterial());
	  sauce.setAmount(1);
	  ItemMeta m = sauce.getItemMeta();
	  assert m != null;
	  
	  m.setDisplayName(ChatColor.DARK_PURPLE + getname());
	  sauce.setItemMeta(m);
	  return sauce;
	}

	public void PlayerInteract(final Player p, ItemStack item) {
        p.sendMessage(ChatColor.RED + "Sauce is not implemented!");
		Main.log.log(Level.SEVERE, "Sauce is not implemented!");
    }
}
