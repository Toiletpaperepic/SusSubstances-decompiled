package toiletpaperepic.SusSubstances;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
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
  public HashMap<UUID, Integer> beanlist = Maps.newHashMap();
  public Plugin plugin = (Plugin)Main.getPlugin(Main.class);

  private ItemValues itemvalues = new ItemValues();

  public ItemValues getitemvalues() {
      itemvalues.block = Material.COCOA;
      itemvalues.material = Material.COCOA_BEANS;
      itemvalues.deathmessage = "eated too much BEAN";
      itemvalues.name = "Bean";
      itemvalues.ItemList = beanlist;
      return itemvalues;
  };
  
  public ItemStack getitemsstack() {
    ItemStack bean = new ItemStack(getitemvalues().material);
    bean.setAmount(1);

    ItemMeta m = bean.getItemMeta();
    assert m != null;

    m.setDisplayName(ChatColor.DARK_PURPLE + getitemvalues().name);
    bean.setItemMeta(m);
    return bean;
  }
  
  public void triggerHigh(final Player p) {
    final UUID id = p.getUniqueId();
    if (beanlist.get(id) == null) {
      beanlist.put(id, Integer.valueOf(0));
      p.sendMessage(ChatColor.GREEN + "Yummy");
      p.sendMessage("" + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "*stomach growls*");
      p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 1.0F);
      // removed from the game or spigot
      // p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 50, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 50, true));
      (new BukkitRunnable() {
          public void run() {
            beanlist.remove(id);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 100.0F, 100.0F);
            p.setGliding(false);
          }
        }).runTaskLater(this.plugin, 50L);
    } 
  }
  
  public void PlayerInteract(final Player p, ItemStack item) {
    if (itemvalues.status == false) {
      p.sendMessage(ChatColor.RED + "Beans is not enabled on this server!");
      return;
    }
    Bean bean = this;
	  p.getWorld().spawnParticle(Particle.ITEM_SLIME, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1.0D, p.getLocation().getZ()), 10);
      p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, 100.0F, 1.0F);
      p.playSound(p.getLocation(), Sound.ENTITY_PARROT_IMITATE_CREEPER, 100.0F, 1.0F);
      if (beanlist.get(p.getUniqueId()) != null) {
        beanlist.replace(p.getUniqueId(), Integer.valueOf(((Integer)beanlist.get(p.getUniqueId())).intValue() + 1));
        if (((Integer)beanlist.get(p.getUniqueId())).intValue() == 1) {
          p.sendMessage(ChatColor.RED + "Damn. Ambitious");
        } else if (((Integer)beanlist.get(p.getUniqueId())).intValue() == 2) {
          p.sendMessage(ChatColor.RED + "Jeez");
        } else if (((Integer)beanlist.get(p.getUniqueId())).intValue() >= 5) {
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

  @SuppressWarnings("deprecation")
  public void HandleVelEvent(Player p) {
    if (beanlist.containsKey(p.getUniqueId())) {
      if (!p.isOnGround() | (p.getFallDistance() >= 5.0F)) {
          p.setGliding(true);
      } else {
          //this does not mater if player used sugar
          //only for bean
          //jumps
          p.setVelocity(new Vector(0, 1, 0));
      }

      p.getWorld().spawnParticle(
          Particle.FLAME, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()),10);

      p.getWorld().spawnParticle(
          Particle.CAMPFIRE_COSY_SMOKE, new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), 10);
    }
}

  @SuppressWarnings("deprecation")
  public void HandlePlayerMove(Player p) {
        if (beanlist.containsKey(p.getUniqueId())) {
            double vec;
            if (beanlist.get(p.getUniqueId()).intValue() == 0) {
                vec = 0.1D;
            } else {
                vec = beanlist.get(p.getUniqueId()) * 0.3D;
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
  }
}
