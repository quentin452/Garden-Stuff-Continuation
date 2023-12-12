package com.jaquadro.minecraft.gardentrees.integration;

import com.jaquadro.minecraft.gardencore.api.SaplingRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeFactory;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.item.Item;

public class ThaumcraftIntegration {
   public static final String MOD_ID = "Thaumcraft";

   public static void init() {
      if (Loader.isModLoaded("Thaumcraft")) {
         Map saplingBank1 = new HashMap();
         saplingBank1.put("large_oak", new int[]{0});
         saplingBank1.put("large_spruce", new int[]{1});
         Map banks = new HashMap();
         banks.put(Item.getItemFromBlock(GameRegistry.findBlock("Thaumcraft", "blockCustomPlant")), saplingBank1);
         SaplingRegistry saplingReg = SaplingRegistry.instance();
         Iterator var3 = banks.entrySet().iterator();

         label45:
         while(var3.hasNext()) {
            Entry entry = (Entry)var3.next();
            Item sapling = (Item)entry.getKey();
            Iterator var6 = ((Map)entry.getValue()).entrySet().iterator();

            while(true) {
               Entry bankEntry;
               OrnamentalTreeFactory factory;
               do {
                  if (!var6.hasNext()) {
                     continue label45;
                  }

                  bankEntry = (Entry)var6.next();
                  factory = OrnamentalTreeRegistry.getTree((String)bankEntry.getKey());
               } while(factory == null);

               int[] var9 = (int[])bankEntry.getValue();
               int var10 = var9.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  int i = var9[var11];
                  UniqueMetaIdentifier woodBlock = saplingReg.getWoodForSapling(sapling, i);
                  UniqueMetaIdentifier leafBlock = saplingReg.getLeavesForSapling(sapling, i);
                  if (woodBlock != null || leafBlock != null) {
                     saplingReg.putExtendedData(sapling, i, "sm_generator", factory.create(woodBlock.getBlock(), woodBlock.meta, leafBlock.getBlock(), leafBlock.meta));
                  }
               }
            }
         }

      }
   }
}