package com.jaquadro.minecraft.gardentrees.core.recipe;

import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.block.tile.TileEntityWoodProxy;
import com.jaquadro.minecraft.gardentrees.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class WoodBlockRecipe implements IRecipe {

    private UniqueMetaIdentifier woodType;

    public WoodBlockRecipe(UniqueMetaIdentifier uid) {
        this.woodType = uid;
    }

    public boolean matches(InventoryCrafting inventory, World world) {
        return this.getCraftingResult(inventory) != null;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        int size = this.getGridSize(inventory);

        for (int row = 0; row < size - 1; ++row) {
            for (int col = 0; col < size - 1; ++col) {
                ItemStack wood1 = inventory.getStackInRowAndColumn(col, row);
                ItemStack wood2 = inventory.getStackInRowAndColumn(col, row + 1);
                ItemStack wood3 = inventory.getStackInRowAndColumn(col + 1, row);
                ItemStack wood4 = inventory.getStackInRowAndColumn(col + 1, row + 1);
                if (wood1 != null && wood2 != null
                    && wood3 != null
                    && wood4 != null
                    && wood1.isItemEqual(wood2)
                    && wood1.isItemEqual(wood3)
                    && wood1.isItemEqual(wood4)
                    && Block.getBlockFromItem(wood1.getItem()) == ModBlocks.thinLog) {
                    Block woodBlock = TileEntityWoodProxy.getBlockFromComposedMetadata(wood1.getItemDamage());
                    int woodMeta = TileEntityWoodProxy.getMetaFromComposedMetadata(wood1.getItemDamage());
                    if (WoodRegistry.instance()
                        .contains(woodBlock, woodMeta) && woodBlock == this.woodType.getBlock()
                        && woodMeta == this.woodType.meta) {
                        return new ItemStack(woodBlock, 1, woodMeta);
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
        return new ItemStack(this.woodType.getBlock(), 1, this.woodType.meta);
    }

    private int getGridSize(InventoryCrafting inventory) {
        return (int) Math.sqrt((double) inventory.getSizeInventory());
    }
}
