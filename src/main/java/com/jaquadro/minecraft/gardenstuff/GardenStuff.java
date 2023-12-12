package com.jaquadro.minecraft.gardenstuff;

import com.jaquadro.minecraft.gardenstuff.core.CommonProxy;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import com.jaquadro.minecraft.gardenstuff.core.ModIntegration;
import com.jaquadro.minecraft.gardenstuff.core.ModItems;
import com.jaquadro.minecraft.gardenstuff.core.ModRecipes;
import com.jaquadro.minecraft.gardenstuff.core.handlers.GuiHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "GardenStuff", name = "Garden Stuff", version = "1.7.10-1.7.0", dependencies = "required-after:GardenCore")
public class GardenStuff {

    public static final String MOD_ID = "GardenStuff";
    public static final String MOD_NAME = "Garden Stuff";
    public static final String MOD_VERSION = "1.7.10-1.7.0";
    static final String SOURCE_PATH = "com.jaquadro.minecraft.gardenstuff.";
    @Instance("GardenStuff")
    public static GardenStuff instance;
    @SidedProxy(
        clientSide = "com.jaquadro.minecraft.gardenstuff.core.ClientProxy",
        serverSide = "com.jaquadro.minecraft.gardenstuff.core.ServerProxy")
    public static CommonProxy proxy;
    public static final ModIntegration integration = new ModIntegration();
    public static final ModBlocks blocks = new ModBlocks();
    public static final ModItems items = new ModItems();
    public static final ModRecipes recipes = new ModRecipes();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        blocks.init();
        items.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRenderers();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        integration.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        recipes.init();
        integration.postInit();
    }
}
