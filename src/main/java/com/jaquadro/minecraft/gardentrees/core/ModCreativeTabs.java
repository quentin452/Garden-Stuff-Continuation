package com.jaquadro.minecraft.gardentrees.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModCreativeTabs {

    public static final CreativeTabs tabGardenTrees = new CreativeTabs("gardenTrees") {

        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.thinLogFence);
        }
    };

    private ModCreativeTabs() {}
}
