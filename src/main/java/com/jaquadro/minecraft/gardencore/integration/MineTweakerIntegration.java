package com.jaquadro.minecraft.gardencore.integration;

import com.jaquadro.minecraft.gardenstuff.integration.minetweaker.CompostBin;

import cpw.mods.fml.common.Loader;
import minetweaker.MineTweakerAPI;

public class MineTweakerIntegration {

    public static final String MOD_ID = "MineTweaker3";

    public static void init() {
        if (Loader.isModLoaded("MineTweaker3")) {
            MineTweakerAPI.registerClass(CompostBin.class);
        }
    }
}
