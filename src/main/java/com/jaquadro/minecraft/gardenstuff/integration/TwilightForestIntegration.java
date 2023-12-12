package com.jaquadro.minecraft.gardenstuff.integration;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.component.ILanternSource;
import com.jaquadro.minecraft.gardenstuff.GardenStuff;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import com.jaquadro.minecraft.gardenstuff.integration.lantern.FireflyLanternSource;
import com.jaquadro.minecraft.gardenstuff.integration.twilightforest.EntityFireflyWrapper;
import com.jaquadro.minecraft.gardenstuff.integration.twilightforest.RenderFireflyWrapper;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Constructor;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.world.World;

public class TwilightForestIntegration {
   public static final String MOD_ID = "TwilightForest";
   static Class classEntityFirefly;
   static Class classRenderFirefly;
   public static Constructor constEntityFirefly;
   public static Constructor constRenderFirefly;
   private static boolean initialized;

   public static void init() {
      if (Loader.isModLoaded("TwilightForest")) {
         try {
            classEntityFirefly = Class.forName("twilightforest.entity.passive.EntityTFTinyFirefly");
            classRenderFirefly = Class.forName("twilightforest.client.renderer.entity.RenderTFTinyFirefly");
            constEntityFirefly = classEntityFirefly.getConstructor(World.class, Double.TYPE, Double.TYPE, Double.TYPE);
            constRenderFirefly = classRenderFirefly.getConstructor();
            if (GardenStuff.proxy instanceof ClientProxy) {
               registerEntity();
            }

            Block blockFirefly = GameRegistry.findBlock("TwilightForest", "tile.TFFirefly");
            ILanternSource fireflySource = new FireflyLanternSource(blockFirefly);
            GardenAPI.instance().registries().lanternSources().registerLanternSource(fireflySource);
            initialized = true;
         } catch (Throwable var2) {
         }

      }
   }

   @SideOnly(Side.CLIENT)
   private static void registerEntity() {
      RenderingRegistry.registerEntityRenderingHandler(EntityFireflyWrapper.class, new RenderFireflyWrapper());
   }

   public static boolean isLoaded() {
      return initialized;
   }

   public static void doFireflyEffect(World world, int x, int y, int z, Random rand) {
      try {
         for(int i = 0; i < 2; ++i) {
            double dx = (double)((float)x + (rand.nextFloat() - rand.nextFloat()) * 0.3F + 0.5F);
            double dy = (double)((float)y + ((rand.nextFloat() - rand.nextFloat()) * 0.4F - 0.05F));
            double dz = (double)((float)z + (rand.nextFloat() - rand.nextFloat()) * 0.3F + 0.5F);
            EntityWeatherEffect tinyfly = (EntityWeatherEffect)constEntityFirefly.newInstance(world, dx, dy, dz);
            world.spawnEntityInWorld(tinyfly);
         }
      } catch (Throwable var13) {
      }

   }
}
