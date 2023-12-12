package com.jaquadro.minecraft.gardencore;

import com.jaquadro.minecraft.gardencore.api.GardenCoreAPI;
import com.jaquadro.minecraft.gardencore.config.ConfigManager;
import com.jaquadro.minecraft.gardencore.core.CommonProxy;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModIntegration;
import com.jaquadro.minecraft.gardencore.core.ModItems;
import com.jaquadro.minecraft.gardencore.core.ModRecipes;
import com.jaquadro.minecraft.gardencore.core.handlers.ForgeEventHandler;
import com.jaquadro.minecraft.gardencore.core.handlers.GuiHandler;
import com.jaquadro.minecraft.gardencore.core.handlers.VanillaBonemealHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import java.io.File;
import net.minecraftforge.common.MinecraftForge;

@Mod(
   modid = "GardenCore",
   name = "Garden Core",
   version = "1.7.10-1.7.0"
)
public class GardenCore {
   public static final String MOD_ID = "GardenCore";
   public static final String MOD_NAME = "Garden Core";
   public static final String MOD_VERSION = "1.7.10-1.7.0";
   static final String SOURCE_PATH = "com.jaquadro.minecraft.gardencore.";
   public static final ModIntegration integration = new ModIntegration();
   public static final ModBlocks blocks = new ModBlocks();
   public static final ModItems items = new ModItems();
   public static final ModRecipes recipes = new ModRecipes();
   public static ConfigManager config;
   @Instance("GardenCore")
   public static GardenCore instance;
   @SidedProxy(
      clientSide = "com.jaquadro.minecraft.gardencore.core.ClientProxy",
      serverSide = "com.jaquadro.minecraft.gardencore.core.ServerProxy"
   )
   public static CommonProxy proxy;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      config = new ConfigManager(new File(event.getModConfigurationDirectory(), "GardenStuff/GardenCore.cfg"));
      blocks.init();
      items.init();
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.registerRenderers();
      NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
      MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
      integration.init();
      GardenCoreAPI.instance().registerBonemealHandler(new VanillaBonemealHandler());
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      proxy.postInit();
      integration.postInit();
      recipes.init();
   }
}
