package com.jaquadro.minecraft.gardencore.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

public final class PlantUtil {

    private PlantUtil() {}

    public static IPlantable getPlantable(ItemStack plant) {
        if (plant != null && plant.getItem() != null) {
            IPlantable plantable = null;
            Item item = plant.getItem();
            if (item instanceof IPlantable) {
                plantable = (IPlantable) item;
            } else if (item instanceof ItemBlock) {
                Block itemBlock = Block.getBlockFromItem(item);
                if (itemBlock instanceof IPlantable) {
                    plantable = (IPlantable) itemBlock;
                }
            }

            return plantable;
        } else {
            return null;
        }
    }

    public static Block getPlantBlock(ItemStack plant) {
        return getPlantBlock(getPlantable(plant));
    }

    public static Block getPlantBlock(IPlantable plant) {
        try {
            return getPlantBlock((IBlockAccess) null, 0, 0, 0, (IPlantable) plant);
        } catch (Exception var2) {
            return null;
        }
    }

    public static Block getPlantBlock(IBlockAccess world, int x, int y, int z, ItemStack plant) {
        return getPlantBlock(world, x, y, z, getPlantable(plant));
    }

    public static Block getPlantBlock(IBlockAccess world, int x, int y, int z, IPlantable plant) {
        if (plant == null) {
            return null;
        } else {
            Block itemBlock = plant.getPlant(world, x, y, z);
            if (itemBlock == null && plant instanceof Block) {
                itemBlock = (Block) plant;
            }

            return itemBlock;
        }
    }

    public static int getPlantMetadata(ItemStack plant) {
        return getPlantMetadata(getPlantable(plant));
    }

    public static int getPlantMetadata(IPlantable plant) {
        try {
            return getPlantMetadata((IBlockAccess) null, 0, 0, 0, (IPlantable) plant);
        } catch (Exception var2) {
            return 0;
        }
    }

    public static int getPlantMetadata(IBlockAccess world, int x, int y, int z, ItemStack plant) {
        return plant == null ? 0 : getPlantMetadata(world, x, y, z, getPlantable(plant), plant.getItemDamage());
    }

    public static int getPlantMetadata(IBlockAccess world, int x, int y, int z, IPlantable plant) {
        return getPlantMetadata(world, x, y, z, plant, 0);
    }

    private static int getPlantMetadata(IBlockAccess world, int x, int y, int z, IPlantable plant, int defaultMeta) {
        if (plant == null) {
            return 0;
        } else {
            Block itemBlock = plant.getPlant(world, x, y, z);
            int itemMeta = defaultMeta;
            if (itemBlock != null) {
                int plantMeta = plant.getPlantMetadata(world, x, y, z);
                if (plantMeta > 0) {
                    itemMeta = plantMeta;
                }
            }

            return itemMeta;
        }
    }
}
