package com.jaquadro.minecraft.gardentrees;

import com.jaquadro.minecraft.gardentrees.config.ConfigManager;
import com.jaquadro.minecraft.gardentrees.core.CommonProxy;
import com.jaquadro.minecraft.gardentrees.core.ModBlocks;
import com.jaquadro.minecraft.gardentrees.core.ModIntegration;
import com.jaquadro.minecraft.gardentrees.core.ModItems;
import com.jaquadro.minecraft.gardentrees.core.ModRecipes;
import com.jaquadro.minecraft.gardentrees.core.handlers.ForgeEventHandler;
import com.jaquadro.minecraft.gardentrees.core.handlers.FuelHandler;
import com.jaquadro.minecraft.gardentrees.world.gen.feature.WorldGenCandelilla;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import net.minecraftforge.common.MinecraftForge;

@Mod(
   modid = "GardenTrees",
   name = "Garden Trees",
   version = "1.7.10-1.7.0",
   dependencies = "required-after:GardenCore"
)
public class GardenTrees {
   public static final String MOD_ID = "GardenTrees";
   public static final String MOD_NAME = "Garden Trees";
   public static final String MOD_VERSION = "1.7.10-1.7.0";
   static final String SOURCE_PATH = "com.jaquadro.minecraft.gardentrees.";
   public static final ModIntegration integration = new ModIntegration();
   public static final ModBlocks blocks = new ModBlocks();
   public static final ModItems items = new ModItems();
   public static final ModRecipes recipes = new ModRecipes();
   public static ConfigManager config;
   @Instance("GardenTrees")
   public static GardenTrees instance;
   @SidedProxy(
      clientSide = "com.jaquadro.minecraft.gardentrees.core.ClientProxy",
      serverSide = "com.jaquadro.minecraft.gardentrees.core.CommonProxy"
   )
   public static CommonProxy proxy;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      config = new ConfigManager(new File(event.getModConfigurationDirectory(), "GardenStuff/GardenTrees.cfg"));
      blocks.init();
      items.init();
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.registerRenderers();
      integration.init();
      MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
      FMLCommonHandler.instance().bus().register(new ForgeEventHandler());
      GameRegistry.registerFuelHandler(new FuelHandler());
      if (config.generateCandelilla) {
         GameRegistry.registerWorldGenerator(new WorldGenCandelilla(ModBlocks.candelilla), 10);
      }

   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      config.postInit();
      integration.postInit();
      recipes.init();
   }
}
