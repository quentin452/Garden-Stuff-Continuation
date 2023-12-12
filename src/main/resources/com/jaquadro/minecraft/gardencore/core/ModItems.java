package com.jaquadro.minecraft.gardencore.core;

import com.jaquadro.minecraft.gardencore.item.ItemCompost;
import com.jaquadro.minecraft.gardencore.item.ItemSoilKit;
import com.jaquadro.minecraft.gardencore.item.ItemTrowel;
import com.jaquadro.minecraft.gardencore.item.ItemUsedSoilKit;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {
   public static Item gardenTrowel;
   public static ItemSoilKit soilTestKit;
   public static ItemUsedSoilKit usedSoilTestKit;
   public static Item compostPile;
   public static Item wax;

   public void init() {
      gardenTrowel = new ItemTrowel(makeName("gardenTrowel"), ToolMaterial.IRON);
      soilTestKit = new ItemSoilKit(makeName("soilTestKit"));
      usedSoilTestKit = new ItemUsedSoilKit(makeName("soilTestKitUsed"));
      compostPile = new ItemCompost(makeName("compostPile"));
      wax = (new Item()).setUnlocalizedName(makeName("wax")).setCreativeTab(ModCreativeTabs.tabGardenCore).setTextureName("GardenCore:wax");
      GameRegistry.registerItem(gardenTrowel, "garden_trowel");
      GameRegistry.registerItem(compostPile, "compost_pile");
      GameRegistry.registerItem(soilTestKit, "soil_test_kit");
      GameRegistry.registerItem(usedSoilTestKit, "soil_test_kit_used");
      GameRegistry.registerItem(wax, "wax");
      OreDictionary.registerOre("materialWax", wax);
      OreDictionary.registerOre("materialPressedwax", wax);
   }

   public static String makeName(String name) {
      return "GardenCore".toLowerCase() + "." + name;
   }

   public static UniqueMetaIdentifier getUniqueMetaID(Item item, int meta) {
      String name = GameData.getItemRegistry().getNameForObject(item);
      return name == null ? null : new UniqueMetaIdentifier(name, meta);
   }

   public static UniqueMetaIdentifier getUniqueMetaID(ItemStack itemStack) {
      return itemStack.getItem() == null ? null : getUniqueMetaID(itemStack.getItem(), itemStack.getItemDamage());
   }
}
