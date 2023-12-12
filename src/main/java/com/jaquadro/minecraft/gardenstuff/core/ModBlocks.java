package com.jaquadro.minecraft.gardenstuff.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraftforge.oredict.OreDictionary;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.block.BlockBloomeryFurnace;
import com.jaquadro.minecraft.gardenstuff.block.BlockCandelabra;
import com.jaquadro.minecraft.gardenstuff.block.BlockFence;
import com.jaquadro.minecraft.gardenstuff.block.BlockHeavyChain;
import com.jaquadro.minecraft.gardenstuff.block.BlockHoop;
import com.jaquadro.minecraft.gardenstuff.block.BlockLantern;
import com.jaquadro.minecraft.gardenstuff.block.BlockLargeMountingPlate;
import com.jaquadro.minecraft.gardenstuff.block.BlockLatticeMetal;
import com.jaquadro.minecraft.gardenstuff.block.BlockLatticeWood;
import com.jaquadro.minecraft.gardenstuff.block.BlockLightChain;
import com.jaquadro.minecraft.gardenstuff.block.BlockMossBrick;
import com.jaquadro.minecraft.gardenstuff.block.BlockRootCover;
import com.jaquadro.minecraft.gardenstuff.block.BlockStoneType;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityBloomeryFurnace;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityCandelabra;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLantern;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLatticeMetal;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLatticeWood;
import com.jaquadro.minecraft.gardenstuff.item.ItemCandelabra;
import com.jaquadro.minecraft.gardenstuff.item.ItemFence;
import com.jaquadro.minecraft.gardenstuff.item.ItemHeavyChain;
import com.jaquadro.minecraft.gardenstuff.item.ItemLantern;
import com.jaquadro.minecraft.gardenstuff.item.ItemLatticeMetal;
import com.jaquadro.minecraft.gardenstuff.item.ItemLatticeWood;
import com.jaquadro.minecraft.gardenstuff.item.ItemLightChain;
import com.jaquadro.minecraft.gardenstuff.item.ItemMossBrick;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

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
