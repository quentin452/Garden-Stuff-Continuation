package com.jaquadro.minecraft.gardentrees.config;

import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
   private final Configuration config;
   private ItemStack[] strangePlantDrops = null;
   public double strangePlantDropChance;
   public int strangePlantDropMin;
   public int strangePlantDropMax;
   public boolean compostGrowsOrnamentalTrees;
   public boolean generateCandelilla;

   public ConfigManager(File file) {
      this.config = new Configuration(file);
      Property propStrangePlantDrops = this.config.get("general", "strangePlantDrops", new String[0]);
      propStrangePlantDrops.comment = "A list of zero or more item IDs.  Breaking the plant will drop an item picked at random from the list.  Ex: minecraft:coal:1";
      Property propStrangePlantDropChance = this.config.get("general", "strangePlantDropChance", 1.0D);
      propStrangePlantDropChance.comment = "The probability from 0.0 - 1.0 that breaking a strange plant will drop its contents.";
      this.strangePlantDropChance = propStrangePlantDropChance.getDouble();
      Property propStrangePlantDropMin = this.config.get("general", "strangePlantDropMin", 1);
      propStrangePlantDropMin.comment = "The minimum number of items dropped when breaking a strange plant.";
      this.strangePlantDropMin = propStrangePlantDropMin.getInt();
      Property propStrangePlantDropMax = this.config.get("general", "strangePlantDropMax", 1);
      propStrangePlantDropMax.comment = "The maximum number of items dropped when breaking a strange plant.";
      this.strangePlantDropMax = propStrangePlantDropMax.getInt();
      Property propCompostGrowsOrnamentalTrees = this.config.get("general", "compostGrowsOrnamentalTrees", true);
      propCompostGrowsOrnamentalTrees.comment = "Using compost on saplings will grow ornamental (miniature) trees instead of normal trees.";
      this.compostGrowsOrnamentalTrees = propCompostGrowsOrnamentalTrees.getBoolean();
      Property propGenerateCandelilla = this.config.get("general", "generateCandelilla", true);
      propGenerateCandelilla.comment = "Generates clusters of candelilla shrub in warm, sandy biomes.";
      this.generateCandelilla = propGenerateCandelilla.getBoolean();
      this.config.save();
   }

   public void postInit() {
   }

   private void parseStrangePlantItems(Property property) {
      String[] entries = property.getStringList();
      if (entries != null && entries.length != 0) {
         List results = new ArrayList();
         String[] var4 = entries;
         int var5 = entries.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String entry = var4[var6];
            UniqueMetaIdentifier uid = new UniqueMetaIdentifier(entry);
            int meta = uid.meta == 32767 ? 0 : uid.meta;
            Item item = GameRegistry.findItem(uid.modId, uid.name);
            if (item != null) {
               results.add(new ItemStack(item, 1, meta));
            } else {
               Block block = GameRegistry.findBlock(uid.modId, uid.name);
               if (block != null) {
                  item = Item.getItemFromBlock(block);
                  if (item != null) {
                     results.add(new ItemStack(item, 1, meta));
                  }
               }
            }
         }

         this.strangePlantDrops = new ItemStack[results.size()];

         for(int i = 0; i < results.size(); ++i) {
            this.strangePlantDrops[i] = (ItemStack)results.get(i);
         }

      } else {
         this.strangePlantDrops = new ItemStack[0];
      }
   }

   public ItemStack[] getStrangePlantDrops() {
      if (this.strangePlantDrops == null) {
         Property propStrangePlantDrops = this.config.get("general", "strangePlantDrops", new String[0]);
         this.parseStrangePlantItems(propStrangePlantDrops);
      }

      return this.strangePlantDrops;
   }
}
