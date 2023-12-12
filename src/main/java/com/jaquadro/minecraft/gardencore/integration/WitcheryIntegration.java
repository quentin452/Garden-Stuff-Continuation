package com.jaquadro.minecraft.gardencore.integration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencore.api.GardenCoreAPI;
import com.jaquadro.minecraft.gardencore.api.IBonemealHandler;
import com.jaquadro.minecraft.gardencore.api.PlantRegistry;
import com.jaquadro.minecraft.gardencore.api.SaplingRegistry;
import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.api.plant.SimplePlantInfo;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class WitcheryIntegration {

    public static final String MOD_ID = "witchery";

    public static void init() {
        if (Loader.isModLoaded("witchery")) {
            initWood();
            GardenCoreAPI.instance()
                .registerBonemealHandler(new WitcheryIntegration.BonemealHandler());
            PlantRegistry plantReg = PlantRegistry.instance();
            plantReg.registerPlantInfo("witchery", "embermoss", new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            plantReg.registerPlantInfo(
                "witchery",
                "leapinglily",
                new SimplePlantInfo(PlantType.AQUATIC_COVER, PlantSize.LARGE));
            plantReg.registerPlantInfo(
                "witchery",
                "spanishmoss",
                new SimplePlantInfo(PlantType.HANGING_SIDE, PlantSize.LARGE));
            plantReg.registerPlantInfo("witchery", "bramble", new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
            plantReg.registerPlantInfo("witchery", "glintweed", new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            plantReg
                .registerPlantInfo("witchery", "voidbramble", new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
            plantReg.registerPlantInfo(
                "witchery",
                "seedsartichoke",
                0,
                new SimplePlantInfo(PlantType.AQUATIC_SURFACE, PlantSize.LARGE));
            plantReg.registerPlantInfo(
                "witchery",
                "seedswormwood",
                0,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            plantReg.registerPlantInfo(
                "witchery",
                "seedsmandrake",
                0,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
            plantReg.registerPlantInfo(
                "witchery",
                "seedswolfsbane",
                0,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            plantReg.registerPlantInfo(
                "witchery",
                "seedsbelladonna",
                0,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
            plantReg.registerPlantInfo(
                "witchery",
                "seedssnowbell",
                0,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            plantReg.registerPlantInfo("witchery", "garlic", 0, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        }
    }

    private static void initWood() {
        Block log = GameRegistry.findBlock("witchery", "witchlog");
        Block leaf = GameRegistry.findBlock("witchery", "witchleaves");
        Item sapling = GameRegistry.findItem("witchery", "witchsapling");
        WoodRegistry woodReg = WoodRegistry.instance();

        for (int i = 0; i < 3; ++i) {
            woodReg.registerWoodType(log, i);
        }

        SaplingRegistry saplingReg = SaplingRegistry.instance();

        for (int i = 0; i < 3; ++i) {
            saplingReg.registerSapling(sapling, i, log, i, leaf, i);
        }

    }

    private static class BonemealHandler implements IBonemealHandler {

        private BonemealHandler() {}

        public boolean applyBonemeal(World world, int x, int y, int z, BlockGarden hostBlock, int slot) {
            TileEntityGarden te = hostBlock.getTileEntity(world, x, y, z);
            ItemStack plantItem = te.getPlantInSlot(slot);
            if (plantItem == null) {
                return false;
            } else {
                ItemStack upgrade;
                if (plantItem.getItemDamage() < 4
                    && (plantItem.getItem() == GameRegistry.findItem("witchery", "seedsbelladonna")
                        || plantItem.getItem() == GameRegistry.findItem("witchery", "seedsartichoke")
                        || plantItem.getItem() == GameRegistry.findItem("witchery", "seedswormwood")
                        || plantItem.getItem() == GameRegistry.findItem("witchery", "seedsmandrake")
                        || plantItem.getItem() == GameRegistry.findItem("witchery", "seedssnowbell"))) {
                    upgrade = new ItemStack(plantItem.getItem(), 1, plantItem.getItemDamage() + 1);
                    if (hostBlock.isPlantValidForSlot(world, x, y, z, slot, PlantItem.getForItem(world, upgrade))) {
                        te.setInventorySlotContents(slot, upgrade);
                        return true;
                    }
                } else if (plantItem.getItemDamage() < 5
                    && plantItem.getItem() == GameRegistry.findItem("witchery", "garlic")) {
                        upgrade = new ItemStack(plantItem.getItem(), 1, plantItem.getItemDamage() + 1);
                        if (hostBlock.isPlantValidForSlot(world, x, y, z, slot, PlantItem.getForItem(world, upgrade))) {
                            te.setInventorySlotContents(slot, upgrade);
                            return true;
                        }
                    } else if (plantItem.getItemDamage() < 7
                        && plantItem.getItem() == GameRegistry.findItem("witchery", "seedswolfsbane")) {
                            upgrade = new ItemStack(plantItem.getItem(), 1, plantItem.getItemDamage() + 1);
                            if (hostBlock
                                .isPlantValidForSlot(world, x, y, z, slot, PlantItem.getForItem(world, upgrade))) {
                                te.setInventorySlotContents(slot, upgrade);
                                return true;
                            }
                        }

                return false;
            }
        }

        // $FF: synthetic method
        BonemealHandler(Object x0) {
            this();
        }
    }
}
