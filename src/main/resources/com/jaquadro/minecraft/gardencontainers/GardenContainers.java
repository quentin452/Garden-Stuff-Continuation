package com.jaquadro.minecraft.gardencontainers;

import com.jaquadro.minecraft.gardencontainers.config.ConfigManager;
import com.jaquadro.minecraft.gardencontainers.core.CommonProxy;
import com.jaquadro.minecraft.gardencontainers.core.ModBlocks;
import com.jaquadro.minecraft.gardencontainers.core.ModItems;
import com.jaquadro.minecraft.gardencontainers.core.ModRecipes;
import com.jaquadro.minecraft.gardencontainers.core.handlers.GuiHandler;
import com.jaquadro.minecraft.gardencontainers.core.handlers.VillagerTradeHandler;
import com.jaquadro.minecraft.gardencore.api.GardenCoreAPI;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import java.io.File;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

@Mod(
   modid = "GardenContainers",
   name = "Garden Containers",
   version = "1.7.10-1.7.0",
   dependencies = "required-after:GardenCore"
)
public class GardenContainers {
   public static final String MOD_ID = "GardenContainers";
   public static final String MOD_NAME = "Garden Containers";
   public static final String MOD_VERSION = "1.7.10-1.7.0";
   static final String SOURCE_PATH = "com.jaquadro.minecraft.gardencontainers.";
   public static final ModBlocks blocks = new ModBlocks();
   public static final ModItems items = new ModItems();
   public static final ModRecipes recipes = new ModRecipes();
   public static ConfigManager config;
   @Instance("GardenContainers")
   public static GardenContainers instance;
   @SidedProxy(
      clientSide = "com.jaquadro.minecraft.gardencontainers.core.ClientProxy",
      serverSide = "com.jaquadro.minecraft.gardencontainers.core.CommonProxy"
   )
   public static CommonProxy proxy;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      config = new ConfigManager(new File(event.getModConfigurationDirectory(), "GardenContainers.patterns.cfg"));
      blocks.init();
      items.init();
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.registerRenderers();
      NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

      for(int j = 0; j < config.getPatternLocationCount(); ++j) {
         String var10000 = config.getPatternLocation(j);
         ModItems var10003 = items;
         ChestGenHooks.addItem(var10000, new WeightedRandomChestContent(ModItems.potteryPatternDirty, 0, 1, 1, config.getPatternLocationRarity(j)));
      }

      VillagerTradeHandler.instance().load();
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      GardenCoreAPI.instance().registerSmallFlameHostBlock(ModBlocks.decorativePot, 0);
      GardenCoreAPI.instance().registerSmallFlameHostBlock(ModBlocks.decorativePot, 1);
      GardenCoreAPI.instance().registerSmallFlameHostBlock(ModBlocks.decorativePot, 2);
      recipes.init();
   }
}
