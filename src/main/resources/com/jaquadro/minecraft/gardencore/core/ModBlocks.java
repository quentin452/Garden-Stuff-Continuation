package com.jaquadro.minecraft.gardencore.core;

import com.jaquadro.minecraft.gardencore.block.*;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityCompostBin;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGardenFarmland;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGardenSoil;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import org.apache.logging.log4j.Level;

public class ModBlocks {
   public static BlockGardenSoil gardenSoil;
   public static BlockGardenFarmland gardenFarmland;
   public static BlockGardenProxy gardenProxy;
   public static BlockSmallFire smallFire;
   public static BlockCompostBin compostBin;

   public void init() {
      gardenSoil = new BlockGardenSoil(makeName("gardenSoil"));
      gardenFarmland = new BlockGardenFarmland(makeName("gardenFarmland"));
      gardenProxy = new BlockGardenProxy(makeName("gardenProxy"));
      smallFire = new BlockSmallFire(makeName("smallFire"));
      compostBin = new BlockCompostBin(makeName("compostBin"));
      GameRegistry.registerBlock(gardenSoil, "garden_soil");
      GameRegistry.registerBlock(gardenFarmland, "garden_farmland");
      GameRegistry.registerBlock(gardenProxy, "garden_proxy");
      GameRegistry.registerBlock(smallFire, "small_fire");
      GameRegistry.registerBlock(compostBin, "compost_bin");
      GameRegistry.registerTileEntity(TileEntityGardenSoil.class, getQualifiedName(gardenSoil));
      GameRegistry.registerTileEntity(TileEntityGardenFarmland.class, getQualifiedName(gardenFarmland));
      GameRegistry.registerTileEntity(TileEntityCompostBin.class, getQualifiedName(compostBin));
   }

   public static String makeName(String name) {
      return "GardenCore".toLowerCase() + "." + name;
   }

   public static Block get(String name) {
      return GameRegistry.findBlock("GardenCore", name);
   }

   public static String getQualifiedName(Block block) {
      return GameData.getBlockRegistry().getNameForObject(block);
   }

   public static UniqueMetaIdentifier getUniqueMetaID(Block block, int meta) {
      String name = GameData.getBlockRegistry().getNameForObject(block);
      if (name == null) {
         FMLLog.log("GardenCore", Level.WARN, "Tried to make a UniqueMetaIdentifier from an invalid block", new Object[0]);
         return null;
      } else {
         return new UniqueMetaIdentifier(name, meta);
      }
   }
}
