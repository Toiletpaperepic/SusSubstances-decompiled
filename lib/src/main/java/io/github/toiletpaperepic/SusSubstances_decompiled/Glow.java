package io.github.toiletpaperepic.SusSubstances_decompiled;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

//very odd file

//seems like it came from 
//https://bukkit.org/threads/how-to-make-an-item-glow-with-no-enchant.374594/

public class Glow extends Enchantment {
  public Glow(int id) {
    super(id);
  }
  
  public String getName() {
    return null;
  }
  
  public int getMaxLevel() {
    return 0;
  }
  
  public int getStartLevel() {
    return 0;
  }
  
  public EnchantmentTarget getItemTarget() {
    return null;
  }
  
  public boolean isTreasure() {
    return false;
  }
  
  public boolean isCursed() {
    return false;
  }
  
  public boolean conflictsWith(Enchantment other) {
    return false;
  }
  
  public boolean canEnchantItem(ItemStack item) {
    return false;
  }
}
