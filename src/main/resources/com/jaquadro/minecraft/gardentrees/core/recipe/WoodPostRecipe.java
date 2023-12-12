package com.jaquadro.minecraft.gardentrees.core.recipe;

import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.block.tile.TileEntityWoodProxy;
import com.jaquadro.minecraft.gardentrees.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WoodPostRecipe implements IRecipe {
   public static final List axeList = new ArrayList();
   private UniqueMetaIdentifier woodType;

   public WoodPostRecipe(UniqueMetaIdentifier uid) {
      this.woodType = uid;
   }

   public boolean matches(InventoryCrafting inventory, World world) {
      return this.getCraftingResult(inventory) != null;
   }

   public ItemStack getCraftingResult(InventoryCrafting inventory) {
      int size = this.getGridSize(inventory);

      for(int row = 0; row < size - 1; ++row) {
         for(int col = 0; col < size; ++col) {
            ItemStack axe = inventory.getStackInRowAndColumn(col, row);
            if (this.isValidAxe(axe)) {
               ItemStack wood = inventory.getStackInRowAndColumn(col, row + 1);
               if (wood != null) {
                  Block woodBlock = Block.getBlockFromItem(wood.getItem());
                  int woodMeta = wood.getItemDamage();
                  if (WoodRegistry.instance().contains(woodBlock, woodMeta) && woodBlock == this.woodType.getBlock() && woodMeta == this.woodType.meta) {
                     return new ItemStack(ModBlocks.thinLog, 4, TileEntityWoodProxy.composeMetadata(woodBlock, woodMeta));
                  }
               }
            }
         }
      }

      return null;
   }

   public int getRecipeSize() {
      return 4;
   }

   public ItemStack getRecipeOutput() {
      return new ItemStack(ModBlocks.thinLog, 4, TileEntityWoodProxy.composeMetadata(this.woodType.getBlock(), this.woodType.meta));
   }

   private int getGridSize(InventoryCrafting inventory) {
      return (int)Math.sqrt((double)inventory.getSizeInventory());
   }

   private boolean isValidAxe(ItemStack itemStack) {
      if (itemStack == null) {
         return false;
      } else {
         Item item = itemStack.getItem();
         int i = 0;

         for(int n = axeList.size(); i < n; ++i) {
            if (item == axeList.get(i)) {
               return true;
            }
         }

         return false;
      }
   }

   static {
      axeList.add(Items.wooden_axe);
      axeList.add(Items.stone_axe);
      axeList.add(Items.iron_axe);
      axeList.add(Items.golden_axe);
      axeList.add(Items.diamond_axe);
   }
}
