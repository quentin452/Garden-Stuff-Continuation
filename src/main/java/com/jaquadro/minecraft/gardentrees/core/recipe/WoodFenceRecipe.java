package com.jaquadro.minecraft.gardentrees.core.recipe;

import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.block.tile.TileEntityWoodProxy;
import com.jaquadro.minecraft.gardentrees.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class WoodFenceRecipe implements IRecipe {

    private UniqueMetaIdentifier woodType;

    public WoodFenceRecipe(UniqueMetaIdentifier uid) {
        this.woodType = uid;
    }

    public boolean matches(InventoryCrafting inventory, World world) {
        return this.getCraftingResult(inventory) != null;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        int size = this.getGridSize(inventory);

        for (int row = 0; row < size - 1; ++row) {
            for (int col = 0; col < size - 2; ++col) {
                ItemStack string1 = inventory.getStackInRowAndColumn(col, row);
                ItemStack string2 = inventory.getStackInRowAndColumn(col + 2, row);
                if (string1 != null && string2 != null
                    && string1.getItem() == Items.string
                    && string1.isItemEqual(string2)) {
                    ItemStack wood1 = inventory.getStackInRowAndColumn(col + 1, row);
                    ItemStack wood2 = inventory.getStackInRowAndColumn(col + 1, row + 1);
                    if (wood1 != null && wood2 != null
                        && wood1.isItemEqual(wood2)
                        && Block.getBlockFromItem(wood1.getItem()) == ModBlocks.thinLog) {
                        Block woodBlock = TileEntityWoodProxy.getBlockFromComposedMetadata(wood1.getItemDamage());
                        int woodMeta = TileEntityWoodProxy.getMetaFromComposedMetadata(wood1.getItemDamage());
                        if (WoodRegistry.instance()
                            .contains(woodBlock, woodMeta) && woodBlock == this.woodType.getBlock()
                            && woodMeta == this.woodType.meta) {
                            return new ItemStack(
                                ModBlocks.thinLogFence,
                                3,
                                TileEntityWoodProxy.composeMetadata(woodBlock, woodMeta));
                        }
                    }
                }
            }
        }

        return null;
    }

    public int getRecipeSize() {
        return 9;
    }

    public ItemStack getRecipeOutput() {
        return new ItemStack(
            ModBlocks.thinLogFence,
            2,
            TileEntityWoodProxy.composeMetadata(this.woodType.getBlock(), this.woodType.meta));
    }

    private int getGridSize(InventoryCrafting inventory) {
        return (int) Math.sqrt((double) inventory.getSizeInventory());
    }
}
