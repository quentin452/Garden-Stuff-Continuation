package com.jaquadro.minecraft.gardenstuff.core;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.block.*;
import com.jaquadro.minecraft.gardenstuff.block.tile.*;
import com.jaquadro.minecraft.gardenstuff.item.*;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {

    public static BlockHeavyChain heavyChain;
    public static BlockLightChain lightChain;
    public static BlockLargeMountingPlate largeMountingPlate;
    public static BlockLatticeMetal latticeMetal;
    public static BlockLatticeWood latticeWood;
    public static BlockRootCover rootCover;
    public static BlockLantern lantern;
    public static Block metalBlock;
    public static BlockStoneType stoneBlock;
    public static BlockFence fence;
    public static BlockBloomeryFurnace bloomeryFurnace;
    public static BlockMossBrick mossBrick;
    public static BlockCandelabra candelabra;
    public static BlockHoop hoop;

    public void init() {
        heavyChain = new BlockHeavyChain(makeName("heavyChain"));
        lightChain = new BlockLightChain(makeName("lightChain"));
        largeMountingPlate = new BlockLargeMountingPlate(makeName("largeMountingPlate"));
        latticeMetal = new BlockLatticeMetal(makeName("latticeMetal"));
        latticeWood = new BlockLatticeWood(makeName("latticeWood"));
        rootCover = new BlockRootCover(makeName("rootCover"));
        lantern = new BlockLantern(makeName("lantern"));
        metalBlock = (new BlockCompressed(MapColor.blackColor)).setBlockName(makeName("metalBlock"))
            .setCreativeTab(ModCreativeTabs.tabGardenCore)
            .setBlockTextureName("GardenStuff:wrought_iron_block")
            .setHardness(5.0F)
            .setResistance(10.0F)
            .setStepSound(Block.soundTypeMetal);
        stoneBlock = new BlockStoneType(makeName("stoneBlock"));
        fence = new BlockFence(makeName("fence"));
        bloomeryFurnace = new BlockBloomeryFurnace(makeName("bloomeryFurnace"));
        mossBrick = new BlockMossBrick(makeName("mossBrick"));
        candelabra = new BlockCandelabra(makeName("candelabra"));
        hoop = new BlockHoop(makeName("hoop"));
        GameRegistry.registerBlock(heavyChain, ItemHeavyChain.class, "heavy_chain");
        GameRegistry.registerBlock(lightChain, ItemLightChain.class, "light_chain");
        GameRegistry.registerBlock(latticeMetal, ItemLatticeMetal.class, "lattice");
        GameRegistry.registerBlock(latticeWood, ItemLatticeWood.class, "lattice_wood");
        GameRegistry.registerBlock(rootCover, "root_cover");
        GameRegistry.registerBlock(lantern, ItemLantern.class, "lantern");
        GameRegistry.registerBlock(metalBlock, "metal_block");
        GameRegistry.registerBlock(stoneBlock, "stone_block");
        GameRegistry.registerBlock(fence, ItemFence.class, "fence");
        GameRegistry.registerBlock(bloomeryFurnace, "bloomery_furnace");
        GameRegistry.registerBlock(mossBrick, ItemMossBrick.class, "moss_brick");
        GameRegistry.registerBlock(candelabra, ItemCandelabra.class, "candelabra");
        GameRegistry.registerBlock(hoop, "hoop");
        GameRegistry.registerTileEntity(TileEntityLatticeMetal.class, getQualifiedName(latticeMetal));
        GameRegistry.registerTileEntity(TileEntityLatticeWood.class, getQualifiedName(latticeWood));
        GameRegistry.registerTileEntity(TileEntityLantern.class, getQualifiedName(lantern));
        GameRegistry.registerTileEntity(TileEntityBloomeryFurnace.class, getQualifiedName(bloomeryFurnace));
        GameRegistry.registerTileEntity(TileEntityCandelabra.class, getQualifiedName(candelabra));
        OreDictionary.registerOre("blockWroughtIron", metalBlock);
        OreDictionary.registerOre("blockCharcoal", stoneBlock);
        GameRegistry.registerFuelHandler(stoneBlock);
    }

    public static String makeName(String name) {
        return "GardenStuff".toLowerCase() + "." + name;
    }

    public static Block get(String name) {
        return GameRegistry.findBlock("GardenStuff", name);
    }

    public static String getQualifiedName(Block block) {
        return GameData.getBlockRegistry()
            .getNameForObject(block);
    }
}
