package com.jaquadro.minecraft.gardencore.api.plant;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

import com.jaquadro.minecraft.gardencore.api.PlantRegistry;

public class PlantItem {

    private ItemStack plantSourceItem;
    private Block plantBlock;
    private int plantMeta;
    private IPlantInfo plantInfo;

    private PlantItem(ItemStack plantSourceItem, Block plantBlock, int plantMeta) {
        this.plantSourceItem = plantSourceItem;
        this.plantBlock = plantBlock;
        this.plantMeta = plantMeta;
        this.plantInfo = PlantRegistry.instance()
            .getPlantInfoOrDefault(plantBlock, plantMeta);
    }

    public static PlantItem getForItem(IBlockAccess blockAccess, ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() != null) {
            IPlantable plantable = PlantRegistry.getPlantable(itemStack);
            if (plantable == null) {
                return getForItem(itemStack);
            } else {
                Block block = plantable.getPlant(blockAccess, 0, -1, 0);
                if (block == null) {
                    return getForItem(itemStack);
                } else {
                    int meta = plantable.getPlantMetadata(blockAccess, 0, -1, 0);
                    if (meta == 0) {
                        meta = itemStack.getItemDamage();
                    }

                    return new PlantItem(itemStack, block, meta);
                }
            }
        } else {
            return null;
        }
    }

    public static PlantItem getForItem(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() != null) {
            Block block = Block.getBlockFromItem(itemStack.getItem());
            return block != null && block instanceof IPlantable
                ? new PlantItem(itemStack, block, itemStack.getItemDamage())
                : null;
        } else {
            return null;
        }
    }

    public ItemStack getPlantSourceItem() {
        return this.plantSourceItem;
    }

    public Block getPlantBlock() {
        return this.plantBlock;
    }

    public int getPlantMeta() {
        return this.plantMeta;
    }

    public IPlantInfo getPlantInfo() {
        return this.plantInfo;
    }

    public PlantType getPlantTypeClass() {
        return this.plantInfo.getPlantTypeClass(this.plantBlock, this.plantMeta);
    }

    public PlantSize getPlantSizeClass() {
        return this.plantInfo.getPlantSizeClass(this.plantBlock, this.plantMeta);
    }

    public int getPlantMaxHeight() {
        return this.plantInfo.getPlantMaxHeight(this.plantBlock, this.plantMeta);
    }

    public int getPlantHeight() {
        return this.plantInfo.getPlantHeight(this.plantBlock, this.plantMeta);
    }

    public int getPlantSectionMeta(int section) {
        return this.plantInfo.getPlantSectionMeta(this.plantBlock, this.plantMeta, section);
    }
}
