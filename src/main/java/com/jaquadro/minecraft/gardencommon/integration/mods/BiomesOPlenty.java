package com.jaquadro.minecraft.gardencommon.integration.mods;

import com.jaquadro.minecraft.gardencommon.integration.IntegrationModule;
import com.jaquadro.minecraft.gardencommon.integration.SmallTreeRegistryHelper;
import com.jaquadro.minecraft.gardencore.api.*;
import com.jaquadro.minecraft.gardencore.api.plant.PlantSize;
import com.jaquadro.minecraft.gardencore.api.plant.PlantType;
import com.jaquadro.minecraft.gardencore.api.plant.SimplePlantInfo;
import com.jaquadro.minecraft.gardencore.client.renderer.plant.CrossedSquaresPlantRenderer;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.HashMap;
import java.util.Map;

public class BiomesOPlenty extends IntegrationModule {

    private static final String MOD_ID = "BiomesOPlenty";
    private static BiomesOPlenty.BOPMetaResolver metaResolver = new BiomesOPlenty.BOPMetaResolver();

    public String getModID() {
        return "BiomesOPlenty";
    }

    public void init() throws Throwable {
        this.initWood();
        PlantRegistry plantReg = PlantRegistry.instance();
        plantReg.registerPlantMetaResolver("BiomesOPlenty", "foliage", metaResolver);
        int[] var2 = new int[] { 0, 1, 2, 3 };
        int var3 = var2.length;

        int var4;
        int i;
        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantInfo(
                "BiomesOPlenty",
                "plants",
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
        }

        plantReg.registerPlantInfo("BiomesOPlenty", "plants", 6, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "plants",
            7,
            new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL, 1, 2));
        plantReg.registerPlantInfo("BiomesOPlenty", "plants", 8, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "plants",
            10,
            new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL, 2, 2, new int[] { 10, 9 }));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "plants", 11, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "plants", 12, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "plants", 13, new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "plants",
            14,
            new SimplePlantInfo(PlantType.AQUATIC_EMERGENT, PlantSize.FULL, 2, 2));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "plants", 15, new SimplePlantInfo(PlantType.HANGING, PlantSize.LARGE));
        plantReg.registerPlantRenderer("BiomesOPlenty", "plants", PlantRegistry.CROSSED_SQUARES_RENDERER);
        var2 = new int[] { 6, 7, 8, 9, 10, 11, 13, 14 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantRenderer("BiomesOPlenty", "plants", i, PlantRegistry.CROPS_RENDERER);
        }

        var2 = new int[] { 5, 9, 10, 12 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantInfo(
                "BiomesOPlenty",
                "foliage",
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
        }

        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "foliage",
            0,
            new SimplePlantInfo(PlantType.AQUATIC_COVER, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "foliage",
            3,
            new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE, 2, 2, new int[] { 3, 6 }));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "foliage",
            13,
            new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "foliage",
            14,
            new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "foliage",
            15,
            new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
        plantReg.registerPlantRenderer("BiomesOPlenty", "foliage", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantRenderer("BiomesOPlenty", "foliage", 3, new BiomesOPlenty.BOPShrubRenderer());
        plantReg.registerPlantRenderer("BiomesOPlenty", "foliage", 6, new BiomesOPlenty.BOPShrubRenderer());
        var2 = new int[] { 0, 13, 14, 15 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantRenderer("BiomesOPlenty", "foliage", i, PlantRegistry.GROUND_COVER_RENDERER);
        }

        var2 = new int[] { 1, 11, 12 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantInfo(
                "BiomesOPlenty",
                "flowers",
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
        }

        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "flowers",
            0,
            new SimplePlantInfo(PlantType.GROUND_COVER, PlantSize.FULL));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "flowers", 2, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "flowers", 3, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "flowers", 5, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "flowers", 6, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "flowers",
            10,
            new SimplePlantInfo(PlantType.AQUATIC_SURFACE, PlantSize.SMALL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "flowers",
            13,
            new SimplePlantInfo(PlantType.GROUND, PlantSize.MEDIUM, 2, 2, new int[] { 13, 14 }));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "flowers", 15, new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        plantReg.registerPlantRenderer("BiomesOPlenty", "flowers", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantRenderer("BiomesOPlenty", "flowers", 0, PlantRegistry.GROUND_COVER_RENDERER);
        plantReg.registerPlantRenderer("BiomesOPlenty", "flowers", 10, PlantRegistry.GROUND_COVER_RENDERER);
        var2 = new int[] { 1, 2, 5 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantInfo(
                "BiomesOPlenty",
                "flowers2",
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
        }

        var2 = new int[] { 4, 8 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantInfo(
                "BiomesOPlenty",
                "flowers2",
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        }

        plantReg.registerPlantRenderer("BiomesOPlenty", "flowers2", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "coral1",
            8,
            new SimplePlantInfo(PlantType.AQUATIC, PlantSize.LARGE, 2, 2, new int[] { 8, 10 }));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "coral1",
            11,
            new SimplePlantInfo(PlantType.AQUATIC, PlantSize.LARGE, 1, 2));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "coral1", 12, new SimplePlantInfo(PlantType.AQUATIC, PlantSize.LARGE));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "coral1", 13, new SimplePlantInfo(PlantType.AQUATIC, PlantSize.LARGE));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "coral1", 14, new SimplePlantInfo(PlantType.AQUATIC, PlantSize.LARGE));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "coral1", 15, new SimplePlantInfo(PlantType.AQUATIC, PlantSize.LARGE));
        plantReg
            .registerPlantInfo("BiomesOPlenty", "coral2", 8, new SimplePlantInfo(PlantType.AQUATIC, PlantSize.LARGE));
        plantReg.registerPlantRenderer("BiomesOPlenty", "coral1", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantRenderer("BiomesOPlenty", "coral2", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantInfo("BiomesOPlenty", "ivy", new SimplePlantInfo(PlantType.HANGING_SIDE, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "flowervine",
            new SimplePlantInfo(PlantType.HANGING_SIDE, PlantSize.FULL));
        plantReg.registerPlantInfo("BiomesOPlenty", "moss", new SimplePlantInfo(PlantType.SIDE_COVER, PlantSize.FULL));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "stoneFormations",
            0,
            new SimplePlantInfo(PlantType.GROUND, PlantSize.LARGE));
        plantReg.registerPlantInfo(
            "BiomesOPlenty",
            "stoneFormations",
            1,
            new SimplePlantInfo(PlantType.HANGING, PlantSize.LARGE));
        plantReg.registerPlantRenderer("BiomesOPlenty", "stoneFormations", PlantRegistry.CROSSED_SQUARES_RENDERER);
        var2 = new int[] { 0, 1, 2 };
        var3 = var2.length;

        for (var4 = 0; var4 < var3; ++var4) {
            i = var2[var4];
            plantReg.registerPlantInfo(
                "BiomesOPlenty",
                "mushrooms",
                i,
                new SimplePlantInfo(PlantType.GROUND, PlantSize.SMALL));
        }

        plantReg.registerPlantRenderer("BiomesOPlenty", "mushrooms", PlantRegistry.CROSSED_SQUARES_RENDERER);
        plantReg.registerPlantInfo("BiomesOPlenty", "turnip", new SimplePlantInfo(PlantType.GROUND, PlantSize.FULL));
        plantReg.registerPlantRenderer("BiomesOPlenty", "turnip", PlantRegistry.CROPS_RENDERER);
    }

    public void postInit() throws Throwable {}

    private void initWood() {
        Block log1 = GameRegistry.findBlock("BiomesOPlenty", "logs1");
        Block log2 = GameRegistry.findBlock("BiomesOPlenty", "logs2");
        Block log3 = GameRegistry.findBlock("BiomesOPlenty", "logs3");
        Block log4 = GameRegistry.findBlock("BiomesOPlenty", "logs4");
        Block bamboo = GameRegistry.findBlock("BiomesOPlenty", "bamboo");
        Block leaf1 = GameRegistry.findBlock("BiomesOPlenty", "leaves1");
        Block leaf2 = GameRegistry.findBlock("BiomesOPlenty", "leaves2");
        Block leaf3 = GameRegistry.findBlock("BiomesOPlenty", "leaves3");
        Block leaf4 = GameRegistry.findBlock("BiomesOPlenty", "leaves4");
        Block leafc1 = GameRegistry.findBlock("BiomesOPlenty", "colorizedLeaves1");
        Block leafc2 = GameRegistry.findBlock("BiomesOPlenty", "colorizedLeaves2");
        Block leafApple = GameRegistry.findBlock("BiomesOPlenty", "appleLeaves");
        Block leafPersimmon = GameRegistry.findBlock("BiomesOPlenty", "persimmonLeaves");
        Item sapling = GameRegistry.findItem("BiomesOPlenty", "saplings");
        Item sapling2 = GameRegistry.findItem("BiomesOPlenty", "colorizedSaplings");
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
        woodReg.registerWoodType(log3, 1);
        woodReg.registerWoodType(log3, 2);
        woodReg.registerWoodType(log3, 3);
        woodReg.registerWoodType(log4, 0);
        woodReg.registerWoodType(log4, 1);
        woodReg.registerWoodType(log4, 2);
        woodReg.registerWoodType(log4, 3);
        SaplingRegistry saplingReg = SaplingRegistry.instance();
        saplingReg.registerSapling(sapling, 0, Blocks.log, 0, leafApple, 0);
        saplingReg.registerSapling(sapling, 1, Blocks.log, 2, leaf1, 0);
        saplingReg.registerSapling(sapling, 2, bamboo, 0, leaf1, 1);
        saplingReg.registerSapling(sapling, 3, log2, 1, leaf1, 2);
        saplingReg.registerSapling(sapling, 4, log1, 2, leaf1, 3);
        saplingReg.registerSapling(sapling, 5, log3, 2, leaf2, 0);
        saplingReg.registerSapling(sapling, 6, log1, 3, leaf2, 1);
        saplingReg.registerSapling(sapling, 7, log2, 0, leaf2, 2);
        saplingReg.registerSapling(sapling, 8, Blocks.log2, 1, leaf2, 3);
        saplingReg.registerSapling(sapling, 9, Blocks.log, 0, leaf3, 0);
        saplingReg.registerSapling(sapling, 10, log1, 1, leaf3, 1);
        saplingReg.registerSapling(sapling, 11, Blocks.log, 0, leaf3, 2);
        saplingReg.registerSapling(sapling, 12, log1, 1, leaf3, 3);
        saplingReg.registerSapling(sapling, 13, log4, 1, leaf4, 0);
        saplingReg.registerSapling(sapling, 14, log4, 2, leaf4, 1);
        saplingReg.registerSapling(sapling, 15, Blocks.log, 0, leafPersimmon, 0);
        saplingReg.registerSapling(sapling2, 0, log1, 0, leafc1, 0);
        saplingReg.registerSapling(sapling2, 1, log2, 2, leafc1, 1);
        saplingReg.registerSapling(sapling2, 2, log2, 3, leafc1, 6);
        saplingReg.registerSapling(sapling2, 3, log3, 0, leafc1, 3);
        saplingReg.registerSapling(sapling2, 4, log3, 1, leafc2, 0);
        saplingReg.registerSapling(sapling2, 5, log4, 0, leafc2, 1);
        saplingReg.registerSapling(sapling2, 6, log4, 3, leafc2, 2);
        Map saplingBank1 = new HashMap();
        saplingBank1.put("small_oak", new int[] { 0, 1, 3, 5, 8, 9, 10, 11, 12, 14, 15 });
        saplingBank1.put("small_pine", new int[] { 2 });
        saplingBank1.put("small_spruce", new int[] { 4, 6, 7 });
        Map saplingBank2 = new HashMap();
        saplingBank2.put("small_oak", new int[] { 1 });
        saplingBank2.put("small_pine", new int[] { 3, 5 });
        saplingBank2.put("small_palm", new int[] { 2 });
        saplingBank2.put("small_willow", new int[] { 4 });
        saplingBank2.put("small_mahogany", new int[] { 6 });
        saplingBank2.put("large_oak", new int[] { 0 });
        Map banks = new HashMap();
        banks.put(GameRegistry.findItem("BiomesOPlenty", "saplings"), saplingBank1);
        banks.put(GameRegistry.findItem("BiomesOPlenty", "colorizedSaplings"), saplingBank2);
        SmallTreeRegistryHelper.registerSaplings(banks);
    }

    private static class BOPShrubRenderer implements IPlantRenderer {

        private CrossedSquaresPlantRenderer crossRender;

        private BOPShrubRenderer() {
            this.crossRender = new CrossedSquaresPlantRenderer();
        }

        public void render(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Block block, int meta,
            int height, AxisAlignedBB[] bounds) {
            this.crossRender.render(world, x, y, z, renderer, block, meta, height, bounds);
            if (meta == 3 && height <= 1) {
                IIcon hedgeTrunk = renderer.minecraftRB.getTextureMapBlocks()
                    .getTextureExtry("biomesoplenty:hedge_trunk");
                if (hedgeTrunk != null) {
                    Tessellator tesselator = Tessellator.instance;
                    tesselator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                    renderer.drawCrossedSquares(hedgeTrunk, (double) x, (double) y, (double) z, 1.0F);
                }
            }
        }

        // $FF: synthetic method
        BOPShrubRenderer(Object x0) {
            this();
        }
    }

    private static class BOPMetaResolver implements IPlantMetaResolver {

        private BOPMetaResolver() {}

        public int getPlantHeight(Block block, int meta) {
            UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(block);
            return uid.name.equals("foliage") && meta == 3 ? 2 : 1;
        }

        public int getPlantSectionMeta(Block block, int meta, int section) {
            UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(block);
            if (uid.name.equals("foliage") && meta == 3) {
                return section == 1 ? 3 : 6;
            } else {
                return meta;
            }
        }

        // $FF: synthetic method
        BOPMetaResolver(Object x0) {
            this();
        }
    }
}
