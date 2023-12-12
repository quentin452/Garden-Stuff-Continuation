package com.jaquadro.minecraft.gardenstuff.core;

import com.jaquadro.minecraft.gardenstuff.renderer.CandelabraRenderer;
import com.jaquadro.minecraft.gardenstuff.renderer.FenceRenderer;
import com.jaquadro.minecraft.gardenstuff.renderer.HeavyChainRenderer;
import com.jaquadro.minecraft.gardenstuff.renderer.HoopRenderer;
import com.jaquadro.minecraft.gardenstuff.renderer.LanternRenderer;
import com.jaquadro.minecraft.gardenstuff.renderer.LatticeRenderer;
import com.jaquadro.minecraft.gardenstuff.renderer.LightChainRenderer;
import com.jaquadro.minecraft.gardenstuff.renderer.item.LanternItemRenderer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
   public static int heavyChainRenderID;
   public static int lightChainRenderID;
   public static int latticeRenderID;
   public static int lanternRenderID;
   public static int fenceRenderID;
   public static int sconceRenderID;
   public static int hoopRenderID;
   public static LanternRenderer lanternRenderer;

   public void registerRenderers() {
      heavyChainRenderID = RenderingRegistry.getNextAvailableRenderId();
      lightChainRenderID = RenderingRegistry.getNextAvailableRenderId();
      latticeRenderID = RenderingRegistry.getNextAvailableRenderId();
      lanternRenderID = RenderingRegistry.getNextAvailableRenderId();
      fenceRenderID = RenderingRegistry.getNextAvailableRenderId();
      sconceRenderID = RenderingRegistry.getNextAvailableRenderId();
      hoopRenderID = RenderingRegistry.getNextAvailableRenderId();
      lanternRenderer = new LanternRenderer();
      RenderingRegistry.registerBlockHandler(heavyChainRenderID, new HeavyChainRenderer());
      RenderingRegistry.registerBlockHandler(lightChainRenderID, new LightChainRenderer());
      RenderingRegistry.registerBlockHandler(latticeRenderID, new LatticeRenderer());
      RenderingRegistry.registerBlockHandler(lanternRenderID, lanternRenderer);
      RenderingRegistry.registerBlockHandler(fenceRenderID, new FenceRenderer());
      RenderingRegistry.registerBlockHandler(sconceRenderID, new CandelabraRenderer());
      RenderingRegistry.registerBlockHandler(hoopRenderID, new HoopRenderer());
      MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.lantern), new LanternItemRenderer());
   }
}
