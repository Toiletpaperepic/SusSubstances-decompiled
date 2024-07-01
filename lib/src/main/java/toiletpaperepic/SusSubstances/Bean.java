package toiletpaperepic.SusSubstances;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.collect.Maps;
import org.bukkit.potion.PotionEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import java.util.HashMap;
import org.bukkit.Sound;
import java.util.UUID;

public class Bean implements Item {
  //hashmaps are what player is using the item.
  public HashMap<UUID, Integer> BeanList = Maps.newHashMap();
  public Plugin plugin = (Plugin)Main.getPlugin(Main.class);

  public String getname() {return "Bean";};
  public Material getmaterial() {return Material.COCOA_BEANS;};
  
  public ItemStack getitem() {
    ItemStack bean = new ItemStack(getmaterial());
    bean.setAmount(1);

    ItemMeta m = bean.getItemMeta();
    assert m != null;

    m.setDisplayName(ChatColor.DARK_PURPLE + getname());
    bean.setItemMeta(m);
    return bean;
  }
  
  public void triggerHigh(final Player p) {
    final UUID id = p.getUniqueId();
    if (this.BeanList.get(id) == null) {
      this.BeanList.put(id, Integer.valueOf(0));
      p.sendMessage(ChatColor.GREEN + "Yummy");
      p.sendMessage("" + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "*stomach growls*");
      p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
      // removed from the game or spigot
      // p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 50, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 50, true));
      (new BukkitRunnable() {
          public void run() {
            Bean.this.BeanList.remove(id);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 100.0F);
            p.setGliding(false);
          }
        }).runTaskLater(this.plugin, 50L);
    } 
  }
  
  public void PlayerInteract(final Player p, ItemStack item) {
    if (Main.BeanItemValues.getStatus() == false) {
      p.sendMessage(ChatColor.RED + "Beans is not enabled on this server!");
      return;
    }
    Bean bean = this;
	  p.getWorld().spawnParticle(Particle.ITEM_SLIME, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1.0D, p.getLocation().getZ()), 10);
      p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 100.0F, 1.0F);
      p.playSound(p.getLocation(), Sound.ENTITY_PARROT_IMITATE_CREEPER, 100.0F, 1.0F);
      if (bean.BeanList.get(p.getUniqueId()) != null) {
        bean.BeanList.replace(p.getUniqueId(), Integer.valueOf(((Integer)bean.BeanList.get(p.getUniqueId())).intValue() + 1));
        if (((Integer)bean.BeanList.get(p.getUniqueId())).intValue() == 1) {
          p.sendMessage(ChatColor.RED + "Damn. Ambitious");
        } else if (((Integer)bean.BeanList.get(p.getUniqueId())).intValue() == 2) {
          p.sendMessage(ChatColor.RED + "Jeez");
        } else if (((Integer)bean.BeanList.get(p.getUniqueId())).intValue() >= 5) {
          p.getWorld().createExplosion(p.getLocation(), 3.0F, false);
        } else {
          p.sendMessage(ChatColor.RED + "SHEEEEEEEEEEEEEEEEESH");
        } 
      } else {
          bean.triggerHigh(p);
      } 
      item.setAmount(item.getAmount() - 1);
      if (item.getAmount() < 1)
          item = null; 
      p.getInventory().setItemInMainHand(item);
  }
}
