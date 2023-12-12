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

public class NaturaIntegration {

    public static final String MOD_ID = "Natura";

    public static void init() {
        if (Loader.isModLoaded("Natura")) {
            initWood();
            PlantRegistry plantReg = PlantRegistry.instance();
            int[] var1 = new int[] { 0, 1, 2 };
            int var2 = var1.length;

            int var3;
            int i;
            for (var3 = 0; var3 < var2; ++var3) {
                i = var1[var3];
                plantReg.registerPlantInfo(
                    "Natura",
                    "Glowshroom",
                    i,
                    new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
            }

            var1 = new int[] { 0 };
            var2 = var1.length;

            for (var3 = 0; var3 < var2; ++var3) {
                i = var1[var3];
                plantReg.registerPlantInfo(
                    "Natura",
                    "Bluebells",
                    i,
                    new SimplePlantInfo(PlantType.GROUND, PlantSize.MEDIUM));
            }

        }
    }

    private static void initWood() {
        Block logTree = GameRegistry.findBlock("Natura", "tree");
        Block logRedwood = GameRegistry.findBlock("Natura", "redwood");
        Block logWillow = GameRegistry.findBlock("Natura", "willow");
        Block logBlood = GameRegistry.findBlock("Natura", "bloodwood");
        Block logDark = GameRegistry.findBlock("Natura", "Dark Tree");
        Block logOverworld = GameRegistry.findBlock("Natura", "Rare Tree");
        Block leafNorm = GameRegistry.findBlock("Natura", "floraleaves");
        Block leafNoColor = GameRegistry.findBlock("Natura", "floraleavesnocolor");
        Block leafDark = GameRegistry.findBlock("Natura", "Dark Leaves");
        Block leafOverworld = GameRegistry.findBlock("Natura", "Rare Leaves");
        Item saplingNorm = GameRegistry.findItem("Natura", "florasapling");
        Item saplingOverworld = GameRegistry.findItem("Natura", "Rare Sapling");
        WoodRegistry woodReg = WoodRegistry.instance();
        woodReg.registerWoodType(logTree, 0);
        woodReg.registerWoodType(logTree, 1);
        woodReg.registerWoodType(logTree, 2);
        woodReg.registerWoodType(logTree, 3);
        woodReg.registerWoodType(logRedwood, 0);
        woodReg.registerWoodType(logRedwood, 1);
        woodReg.registerWoodType(logWillow, 0);
        woodReg.registerWoodType(logBlood, 15);
        woodReg.registerWoodType(logDark, 0);
        woodReg.registerWoodType(logDark, 1);
        woodReg.registerWoodType(logOverworld, 0);
        woodReg.registerWoodType(logOverworld, 1);
        woodReg.registerWoodType(logOverworld, 2);
        woodReg.registerWoodType(logOverworld, 3);
        SaplingRegistry saplingReg = SaplingRegistry.instance();
        saplingReg.registerSapling(saplingNorm, 0, logRedwood, 0, leafNorm, 0);
        saplingReg.registerSapling(saplingNorm, 1, logTree, 0, leafNorm, 1);
        saplingReg.registerSapling(saplingNorm, 2, logTree, 3, leafNorm, 2);
        saplingReg.registerSapling(saplingNorm, 3, logTree, 1, leafNoColor, 0);
        saplingReg.registerSapling(saplingNorm, 4, logTree, 2, leafNoColor, 1);
        saplingReg.registerSapling(saplingNorm, 6, logDark, 0, leafDark, 0);
        saplingReg.registerSapling(saplingNorm, 7, logDark, 1, leafDark, 3);
        saplingReg.registerSapling(saplingOverworld, 0, logOverworld, 0, leafOverworld, 0);
        saplingReg.registerSapling(saplingOverworld, 1, logOverworld, 1, leafOverworld, 1);
        saplingReg.registerSapling(saplingOverworld, 2, logOverworld, 2, leafOverworld, 2);
        saplingReg.registerSapling(saplingOverworld, 3, logOverworld, 3, leafOverworld, 3);
        saplingReg.registerSapling(saplingOverworld, 4, logWillow, 0, leafNoColor, 3);
    }
}
