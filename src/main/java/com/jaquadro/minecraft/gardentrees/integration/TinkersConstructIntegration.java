package com.jaquadro.minecraft.gardentrees.integration;

import net.minecraft.item.Item;

import com.jaquadro.minecraft.gardentrees.core.recipe.WoodPostRecipe;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class TinkersConstructIntegration {

    public static final String MODID = "TConstruct";

    public static void init() {
        if (Loader.isModLoaded("TConstruct")) {
            Item toolHatchet = GameRegistry.findItem("TConstruct", "hatchet");
            Item toolLumberAxe = GameRegistry.findItem("TConstruct", "lumberaxe");
            WoodPostRecipe.axeList.add(toolHatchet);
            WoodPostRecipe.axeList.add(toolLumberAxe);
        }
    }
}
