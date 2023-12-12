package com.jaquadro.minecraft.gardencore.core.handlers;

import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.api.block.IGardenBlock;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.BlockSmallFire;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ForgeEventHandler {
   @SubscribeEvent
   public void applyBonemeal(BonemealEvent event) {
      if (event.block instanceof IPlantProxy) {
         IPlantProxy proxy = (IPlantProxy)event.block;
         if (proxy.applyBonemeal(event.world, event.x, event.y, event.z)) {
            event.setResult(Result.ALLOW);
         }
      }

   }

   @SubscribeEvent
   public void playerInteracts(PlayerInteractEvent event) {
      if (event.action == Action.RIGHT_CLICK_BLOCK) {
         ItemStack stack = event.entityPlayer.inventory.getCurrentItem();
         if (stack != null && stack.getItem() == GameData.getItemRegistry().getObject("Botania:flowerBag")) {
            Block block = event.world.getBlock(event.x, event.y, event.z);
            if (block instanceof IGardenBlock) {
               event.useItem = Result.DENY;
               return;
            }
         }
      }

      if (event.action == Action.LEFT_CLICK_BLOCK) {
         if (event.entityPlayer.capabilities.isCreativeMode) {
            if (BlockSmallFire.extinguishSmallFire(event.world, (EntityPlayer)null, event.x, event.y, event.z, event.face)) {
               event.setResult(Result.ALLOW);
               return;
            }
         } else {
            Block block = event.world.getBlock(event.x, event.y, event.z);
            if (!block.isAir(event.world, event.x, event.y, event.z) && BlockSmallFire.extinguishSmallFire(event.world, (EntityPlayer)null, event.x, event.y, event.z, event.face)) {
               event.setResult(Result.ALLOW);
               return;
            }
         }
      }

   }

   @SubscribeEvent
   public void useHoe(UseHoeEvent event) {
      Block block = event.world.getBlock(event.x, event.y, event.z);
      if (block instanceof BlockGarden && ((BlockGarden)block).applyHoe(event.world, event.x, event.y, event.z)) {
         event.setResult(Result.ALLOW);
      }

   }
}
