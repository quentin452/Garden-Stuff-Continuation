package com.jaquadro.minecraft.gardencore.item;

import com.jaquadro.minecraft.gardencore.GardenCore;
import com.jaquadro.minecraft.gardencore.api.event.EnrichedSoilEvent;
import com.jaquadro.minecraft.gardencore.config.ConfigManager;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemCompost extends Item {
   public ItemCompost(String unlocalizedName) {
      this.setUnlocalizedName(unlocalizedName);
      this.setMaxStackSize(64);
      this.setTextureName("GardenCore:compost_pile");
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
   }

   public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
      return !player.canPlayerEdit(x, y, z, side, itemStack) ? false : this.applyEnrichment(itemStack, world, x, y, z, player);
   }

   public boolean applyEnrichment(ItemStack itemStack, World world, int x, int y, int z, EntityPlayer player) {
      ConfigManager config = GardenCore.config;
      Block block = world.getBlock(x, y, z);
      EnrichedSoilEvent event = new EnrichedSoilEvent(player, world, block, x, y, z);
      if (MinecraftForge.EVENT_BUS.post(event)) {
         return false;
      } else if (!config.enableCompostBonemeal) {
         return false;
      } else if (event.getResult() == Result.ALLOW) {
         if (!world.isRemote) {
            --itemStack.stackSize;
         }

         return true;
      } else {
         int prob = config.compostBonemealStrength == 0.0D ? 0 : (int)(1.0D / config.compostBonemealStrength);
         if (world.rand.nextInt(prob) == 0) {
            return ItemDye.applyBonemeal(itemStack, world, x, y, z, player);
         } else {
            --itemStack.stackSize;
            return true;
         }
      }
   }
}
