package io.github.toiletpaperepic.SusSubstances_decompiled;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class Glow extends Enchantment {
  public Glow(NamespacedKey i) {
    super(i);
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
