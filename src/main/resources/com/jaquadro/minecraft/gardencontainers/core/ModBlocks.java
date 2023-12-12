package com.jaquadro.minecraft.gardencontainers.core;

import com.jaquadro.minecraft.gardencontainers.block.BlockDecorativePot;
import com.jaquadro.minecraft.gardencontainers.block.BlockLargePot;
import com.jaquadro.minecraft.gardencontainers.block.BlockLargePotColored;
import com.jaquadro.minecraft.gardencontainers.block.BlockLargePotStandard;
import com.jaquadro.minecraft.gardencontainers.block.BlockMediumPot;
import com.jaquadro.minecraft.gardencontainers.block.BlockMediumPotColored;
import com.jaquadro.minecraft.gardencontainers.block.BlockMediumPotStandard;
import com.jaquadro.minecraft.gardencontainers.block.BlockPotteryTable;
import com.jaquadro.minecraft.gardencontainers.block.BlockWindowBox;
import com.jaquadro.minecraft.gardencontainers.block.BlockWindowBoxStone;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityDecorativePot;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityLargePot;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityMediumPot;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityPotteryTable;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityWindowBox;
import com.jaquadro.minecraft.gardencontainers.item.ItemDecorativePot;
import com.jaquadro.minecraft.gardencontainers.item.ItemLargePot;
import com.jaquadro.minecraft.gardencontainers.item.ItemMediumPot;
import com.jaquadro.minecraft.gardencontainers.item.ItemWindowBox;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.apache.logging.log4j.Level;

public class ModBlocks {
   public static BlockWindowBox woodWindowBox;
   public static BlockWindowBoxStone stoneWindowBox;
   public static BlockDecorativePot decorativePot;
   public static BlockLargePot largePot;
   public static BlockLargePot largePotColored;
   public static BlockMediumPot mediumPot;
   public static BlockMediumPot mediumPotColored;
   public static BlockPotteryTable potteryTable;

   public void init() {
      woodWindowBox = new BlockWindowBox(makeName("woodWindowBox"), Material.wood);
      stoneWindowBox = new BlockWindowBoxStone(makeName("stoneWindowBox"));
      decorativePot = new BlockDecorativePot(makeName("decorativePot"));
      largePot = new BlockLargePotStandard(makeName("largePot"));
      largePotColored = new BlockLargePotColored(makeName("largePotColored"));
      mediumPot = new BlockMediumPotStandard(makeName("mediumPot"));
      mediumPotColored = new BlockMediumPotColored(makeName("mediumPotColored"));
      potteryTable = new BlockPotteryTable(makeName("potteryTable"));
      GameRegistry.registerBlock(woodWindowBox, ItemWindowBox.class, "wood_window_box");
      GameRegistry.registerBlock(stoneWindowBox, ItemWindowBox.class, "stone_window_box");
      GameRegistry.registerBlock(decorativePot, ItemDecorativePot.class, "decorative_pot");
      GameRegistry.registerBlock(largePot, ItemLargePot.class, "large_pot");
      GameRegistry.registerBlock(largePotColored, ItemLargePot.class, "large_pot_colored");
      GameRegistry.registerBlock(mediumPot, ItemMediumPot.class, "medium_pot");
      GameRegistry.registerBlock(mediumPotColored, ItemMediumPot.class, "medium_pot_colored");
      GameRegistry.registerBlock(potteryTable, "pottery_table");
      GameRegistry.registerTileEntity(TileEntityWindowBox.class, getQualifiedName(woodWindowBox));
      GameRegistry.registerTileEntity(TileEntityDecorativePot.class, getQualifiedName(decorativePot));
      GameRegistry.registerTileEntity(TileEntityLargePot.class, getQualifiedName(largePot));
      GameRegistry.registerTileEntity(TileEntityLargePot.class, getQualifiedName(largePotColored));
      GameRegistry.registerTileEntity(TileEntityMediumPot.class, getQualifiedName(mediumPot));
      GameRegistry.registerTileEntity(TileEntityMediumPot.class, getQualifiedName(mediumPotColored));
      GameRegistry.registerTileEntity(TileEntityPotteryTable.class, getQualifiedName(potteryTable));
   }

   public static String makeName(String name) {
      return "GardenContainers".toLowerCase() + "." + name;
   }

   public static Block get(String name) {
      return GameRegistry.findBlock("GardenContainers", name);
   }

   public static String getQualifiedName(Block block) {
      return GameData.getBlockRegistry().getNameForObject(block);
   }

   public static UniqueMetaIdentifier getUniqueMetaID(Block block, int meta) {
      String name = GameData.getBlockRegistry().getNameForObject(block);
      if (name == null) {
         FMLLog.log("GardenContainers", Level.WARN, "Tried to make a UniqueMetaIdentifier from an invalid block", new Object[0]);
         return null;
      } else {
         return new UniqueMetaIdentifier(name, meta);
      }
   }
}
