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

public class TwilightForestIntegration {

    public static final String MOD_ID = "TwilightForest";

    public static void init() {
        if (Loader.isModLoaded("TwilightForest")) {
            initWood();
            PlantRegistry plantReg = PlantRegistry.instance();
            plantReg.registerPlantInfo(
                "TwilightForest",
                "tile.TFPlant",
                3,
                new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
            plantReg.registerPlantInfo(
                "TwilightForest",
                "tile.TFPlant",
                5,
                new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
            plantReg.registerPlantInfo(
                "TwilightForest",
                "tile.TFPlant",
                13,
                new SimplePlantInfo(PlantType.HANGING, PlantSize.LARGE));
            plantReg.registerPlantInfo(
                "TwilightForest",
                "tile.TFPlant",
                14,
                new SimplePlantInfo(PlantType.HANGING, PlantSize.FULL));
            int[] var1 = new int[] { 4 };
            int var2 = var1.length;

            int var3;
            int i;
            for (var3 = 0; var3 < var2; ++var3) {
                i = var1[var3];
                plantReg.registerPlantInfo(
                    "TwilightForest",
                    "tile.TFPlant",
                    i,
                    new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
            }

            var1 = new int[] { 8, 9, 11 };
            var2 = var1.length;

            for (var3 = 0; var3 < var2; ++var3) {
                i = var1[var3];
                plantReg.registerPlantInfo(
                    "TwilightForest",
                    "tile.TFPlant",
                    i,
                    new SimplePlantInfo(PlantType.GROUND, PlantSize.MEDIUM));
            }

            var1 = new int[] { 10 };
            var2 = var1.length;

            for (var3 = 0; var3 < var2; ++var3) {
                i = var1[var3];
                plantReg.registerPlantInfo(
                    "TwilightForest",
                    "tile.TFPlant",
                    i,
                    new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            }

            plantReg.registerPlantInfo(
                "TwilightForest",
                "tile.TFSapling",
                new SimplePlantInfo(PlantType.GROUND, PlantSize.MEDIUM));
            var1 = new int[] { 8, 9, 10, 11 };
            var2 = var1.length;

            for (var3 = 0; var3 < var2; ++var3) {
                i = var1[var3];
                plantReg
                    .registerPlantRenderer("TwilightForest", "tile.TFPlant", i, PlantRegistry.CROSSED_SQUARES_RENDERER);
            }

        }
    }

    private static void initWood() {
        Block log = GameRegistry.findBlock("TwilightForest", "tile.TFLog");
        Block magicLog = GameRegistry.findBlock("TwilightForest", "tile.TFMagicLog");
        Block leaves = GameRegistry.findBlock("TwilightForest", "tile.TFLeaves");
        Block magicLeaves = GameRegistry.findBlock("TwilightForest", "tile.TFMagicLeaves");
        Block darkLeaves = GameRegistry.findBlock("TwilightForest", "tile.DarkLeaves");
        Item sapling = Item.getItemFromBlock(GameRegistry.findBlock("TwilightForest", "tile.TFSapling"));
        WoodRegistry woodReg = WoodRegistry.instance();
        woodReg.registerWoodType(log, 0);
        woodReg.registerWoodType(log, 1);
        woodReg.registerWoodType(log, 2);
        woodReg.registerWoodType(log, 3);
        woodReg.registerWoodType(magicLog, 0);
        woodReg.registerWoodType(magicLog, 1);
        woodReg.registerWoodType(magicLog, 2);
        woodReg.registerWoodType(magicLog, 3);
        SaplingRegistry saplingReg = SaplingRegistry.instance();
        saplingReg.registerSapling(sapling, 0, log, 0, leaves, 0);
        saplingReg.registerSapling(sapling, 1, log, 1, leaves, 1);
        saplingReg.registerSapling(sapling, 2, log, 2, leaves, 2);
        saplingReg.registerSapling(sapling, 3, log, 3, darkLeaves, 0);
        saplingReg.registerSapling(sapling, 4, log, 0, leaves, 0);
        saplingReg.registerSapling(sapling, 5, magicLog, 0, magicLeaves, 0);
        saplingReg.registerSapling(sapling, 6, magicLog, 1, magicLeaves, 1);
        saplingReg.registerSapling(sapling, 7, magicLog, 2, magicLeaves, 2);
        saplingReg.registerSapling(sapling, 8, magicLog, 3, magicLeaves, 3);
        saplingReg.registerSapling(sapling, 9, log, 0, leaves, 3);
    }
}
