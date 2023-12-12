package com.jaquadro.minecraft.gardencommon.integration.mods;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.jaquadro.minecraft.gardencommon.integration.IntegrationModule;
import com.jaquadro.minecraft.gardencore.api.GardenCoreAPI;
import com.jaquadro.minecraft.gardencore.api.IBonemealHandler;
import com.jaquadro.minecraft.gardencore.api.IPlantRenderer;
import com.jaquadro.minecraft.gardencore.api.PlantRegistry;
import com.jaquadro.minecraft.gardencore.api.plant.DefaultPlantInfo;
import com.jaquadro.minecraft.gardencore.api.plant.IPlantInfo;
import com.jaquadro.minecraft.gardencore.api.plant.PlantItem;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.api.plant.SimplePlantInfo;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.client.renderer.plant.DoublePlantRenderer;

import cpw.mods.fml.common.registry.GameRegistry;

public class Botania extends IntegrationModule {

    public static final String MOD_ID = "Botania";
    private static Block flower1;
    private static Block flower2;

    public String getModID() {
        return "Botania";
    }

    public void init() throws Throwable {
        flower1 = GameRegistry.findBlock("Botania", "doubleFlower1");
        flower2 = GameRegistry.findBlock("Botania", "doubleFlower2");
        PlantRegistry plantReg = PlantRegistry.instance();
        IPlantRenderer doubleFlowerRender = new Botania.DoubleFlowerRenderer();
        plantReg.registerPlantRenderer("Botania", "flower", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantRenderer("Botania", "shinyFlower", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantRenderer("Botania", "doubleFlower1", doubleFlowerRender);
        plantReg.registerPlantRenderer("Botania", "doubleFlower2", doubleFlowerRender);
        IPlantInfo doubleFlowerInfo = new Botania.DoubleFlowerPlantInfo();
        plantReg.registerPlantInfo("Botania", "doubleFlower1", doubleFlowerInfo);
        plantReg.registerPlantInfo("Botania", "doubleFlower2", doubleFlowerInfo);
        GardenCoreAPI.instance()
            .registerBonemealHandler(new Botania.BonemealHandler());
        int[] var4 = new int[] { 2, 3, 6, 9, 15 };
        int var5 = var4.length;

        int var6;
        int i;
        for (var6 = 0; var6 < var5; ++var6) {
            i = var4[var6];
            plantReg.registerPlantInfo("Botania", "flower", i, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
            plantReg
                .registerPlantInfo("Botania", "shinyFlower", i, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        }

        var4 = new int[] { 4 };
        var5 = var4.length;

        for (var6 = 0; var6 < var5; ++var6) {
            i = var4[var6];
            plantReg.registerPlantInfo("Botania", "flower", i, new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
            plantReg
                .registerPlantInfo("Botania", "shinyFlower", i, new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
        }

        plantReg
            .registerPlantInfo("Botania", "specialFlower", new SimplePlantInfo(PlantType.INVALID, PlantSize.MEDIUM));
    }

    public void postInit() throws Throwable {}

    private static class DoubleFlowerRenderer extends DoublePlantRenderer {

        private DoubleFlowerRenderer() {}

        public IIcon getIcon(Block block, IBlockAccess blockAccess, int meta) {
            return block.getIcon(0, meta);
        }

        // $FF: synthetic method
        DoubleFlowerRenderer(Object x0) {
            this();
        }
    }

    private static class DoubleFlowerPlantInfo extends DefaultPlantInfo {

        private DoubleFlowerPlantInfo() {}

        public int getPlantHeight(Block block, int meta) {
            return block != Botania.flower1 && block != Botania.flower2 ? 1 : 2;
        }

        public int getPlantSectionMeta(Block block, int meta, int section) {
            if (block == Botania.flower1 || block == Botania.flower2) {
                switch (section) {
                    case 1:
                        return meta | 8;
                    case 2:
                        return meta & 7;
                }
            }

            return meta;
        }

        // $FF: synthetic method
        DoubleFlowerPlantInfo(Object x0) {
            this();
        }
    }

    private static class BonemealHandler implements IBonemealHandler {

        private BonemealHandler() {}

        public boolean applyBonemeal(World world, int x, int y, int z, BlockGarden hostBlock, int slot) {
            TileEntityGarden te = hostBlock.getTileEntity(world, x, y, z);
            Block block = hostBlock.getPlantBlockFromSlot(world, x, y, z, slot);
            int meta = hostBlock.getPlantMetaFromSlot(world, x, y, z, slot);
            Block flower = GameRegistry.findBlock("Botania", "flower");
            if (block == flower) {
                ItemStack upgrade = (meta & 8) == 0 ? new ItemStack(Botania.flower1, 1, meta)
                    : new ItemStack(Botania.flower2, 1, meta & 7);
                if (hostBlock.isPlantValidForSlot(world, x, y, z, slot, PlantItem.getForItem(upgrade))) {
                    te.setInventorySlotContents(slot, upgrade);
                    return true;
                }
            }

            return false;
        }

        // $FF: synthetic method
        BonemealHandler(Object x0) {
            this();
        }
    }
}
