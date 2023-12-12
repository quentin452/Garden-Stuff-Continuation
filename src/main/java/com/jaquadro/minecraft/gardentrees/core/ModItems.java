package com.jaquadro.minecraft.gardentrees.core;

import net.minecraft.item.Item;

import com.jaquadro.minecraft.gardentrees.item.ItemSeeds;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

    public static Item candelilla_seeds;
    public static Item candelilla;

    public void init() {
        candelilla_seeds = (new ItemSeeds(ModBlocks.candelilla)).setUnlocalizedName(makeName("candelillaSeeds"))
            .setTextureName("GardenTrees:candelilla_seeds");
        candelilla = (new Item()).setUnlocalizedName(makeName("candelilla"))
            .setCreativeTab(ModCreativeTabs.tabGardenTrees)
            .setTextureName("GardenTrees:candelilla");
        GameRegistry.registerItem(candelilla_seeds, "candelilla_seeds");
        GameRegistry.registerItem(candelilla, "candelilla");
    }

    public static String makeName(String name) {
        return "GardenTrees".toLowerCase() + "." + name;
    }
}
