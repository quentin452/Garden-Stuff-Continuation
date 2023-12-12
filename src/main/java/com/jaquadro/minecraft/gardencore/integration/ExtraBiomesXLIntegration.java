package com.jaquadro.minecraft.gardencore.integration;

import com.jaquadro.minecraft.gardencore.api.PlantRegistry;
import com.jaquadro.minecraft.gardencore.api.SaplingRegistry;
import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.api.plant.SimplePlantInfo;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ExtraBiomesXLIntegration {
   public static final String MOD_ID = "ExtrabiomesXL";

   public static void init() {
      if (Loader.isModLoaded("ExtrabiomesXL")) {
         initWood();
         PlantRegistry plantReg = PlantRegistry.instance();
         int[] var1 = new int[]{2, 3, 6};
         int var2 = var1.length;

         int var3;
         int i;
         for(var3 = 0; var3 < var2; ++var3) {
            i = var1[var3];
            plantReg.registerPlantInfo("ExtrabiomesXL", "flower1", i, new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
         }

         plantReg.registerPlantInfo("ExtrabiomesXL", "flower1", 4, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
         var1 = new int[]{4, 5, 6};
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            i = var1[var3];
            plantReg.registerPlantInfo("ExtrabiomesXL", "flower3", i, new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
         }

         plantReg.registerPlantInfo("ExtrabiomesXL", "grass", new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
         plantReg.registerPlantInfo("ExtrabiomesXL", "leaf_pile", new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
         plantReg.registerPlantInfo("ExtrabiomesXL", "vines", new SimplePlantInfo(PlantType.HANGING_SIDE, PlantSize.FULL));
         plantReg.registerPlantInfo("ExtrabiomesXL", "plants4", new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
         plantReg.registerPlantInfo("ExtrabiomesXL", "waterplant1", new SimplePlantInfo(PlantType.AQUATIC, PlantSize.FULL));
      }
   }

   private static void initWood() {
      Block log1 = GameRegistry.findBlock("ExtrabiomesXL", "log1");
      Block log2 = GameRegistry.findBlock("ExtrabiomesXL", "log2");
      Block log3 = GameRegistry.findBlock("ExtrabiomesXL", "mini_log_1");
      Block leaf1 = GameRegistry.findBlock("ExtrabiomesXL", "leaves_1");
      Block leaf2 = GameRegistry.findBlock("ExtrabiomesXL", "leaves_2");
      Block leaf3 = GameRegistry.findBlock("ExtrabiomesXL", "leaves_3");
      Block leaf4 = GameRegistry.findBlock("ExtrabiomesXL", "leaves_4");
      Item sapling = GameRegistry.findItem("ExtrabiomesXL", "saplings_1");
      Item sapling2 = GameRegistry.findItem("ExtrabiomesXL", "saplings_2");
      WoodRegistry woodReg = WoodRegistry.instance();
      woodReg.registerWoodType(log1, 0);
      woodReg.registerWoodType(log1, 1);
      woodReg.registerWoodType(log1, 2);
      woodReg.registerWoodType(log1, 3);
      woodReg.registerWoodType(log2, 0);
      woodReg.registerWoodType(log2, 1);
      woodReg.registerWoodType(log2, 2);
      woodReg.registerWoodType(log2, 3);
      woodReg.registerWoodType(log3, 0);
      SaplingRegistry saplingReg = SaplingRegistry.instance();
      saplingReg.registerSapling(sapling, 0, log2, 1, leaf1, 0);
      saplingReg.registerSapling(sapling, 1, log2, 1, leaf1, 1);
      saplingReg.registerSapling(sapling, 2, log2, 1, leaf1, 2);
      saplingReg.registerSapling(sapling, 3, log2, 1, leaf1, 3);
      saplingReg.registerSapling(sapling, 4, log1, 0, leaf4, 0);
      saplingReg.registerSapling(sapling, 5, log2, 3, leaf4, 1);
      saplingReg.registerSapling(sapling, 6, log1, 1, leaf4, 2);
      saplingReg.registerSapling(sapling, 7, log1, 2, leaf4, 3);
      saplingReg.registerSapling(sapling2, 0, log2, 2, leaf2, 0);
      saplingReg.registerSapling(sapling2, 1, log1, 3, leaf2, 1);
      saplingReg.registerSapling(sapling2, 2, log1, 3, leaf2, 2);
      saplingReg.registerSapling(sapling2, 3, log2, 0, leaf2, 3);
      saplingReg.registerSapling(sapling2, 4, log3, 0, leaf3, 0);
   }
}
