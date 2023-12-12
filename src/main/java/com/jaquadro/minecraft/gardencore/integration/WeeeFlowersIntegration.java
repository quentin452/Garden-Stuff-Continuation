package com.jaquadro.minecraft.gardencore.integration;

import com.jaquadro.minecraft.gardencore.api.PlantRegistry;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.api.plant.SimplePlantInfo;
import cpw.mods.fml.common.Loader;

public class WeeeFlowersIntegration {

    public static String MOD_ID = "weeeflowers";

    public static void init() {
        if (Loader.isModLoaded(MOD_ID)) {
            String[] pamColors = new String[] { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink",
                "Light Grey", "Dark Grey", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
            PlantRegistry plantReg = PlantRegistry.instance();
            int[] var2 = new int[] { 3, 4, 5, 6, 8, 9, 10, 11, 12 };
            int var3 = var2.length;

            int var4;
            for (var4 = 0; var4 < var3; ++var4) {
                int i = var2[var4];
                plantReg.registerPlantInfo(MOD_ID, "Flower", i, new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            }

            String[] var6 = pamColors;
            var3 = pamColors.length;

            for (var4 = 0; var4 < var3; ++var4) {
                String color = var6[var4];
                plantReg.registerPlantInfo(
                    MOD_ID,
                    color + " Flower Crop",
                    new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
                plantReg.registerPlantInfo(
                    MOD_ID,
                    color + " Vine",
                    new SimplePlantInfo(PlantType.HANGING_SIDE, PlantSize.LARGE));
            }

        }
    }
}
