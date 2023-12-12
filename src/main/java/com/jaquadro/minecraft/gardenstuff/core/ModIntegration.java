package com.jaquadro.minecraft.gardenstuff.core;

import com.jaquadro.minecraft.gardenstuff.integration.ColoredLightsIntegration;
import com.jaquadro.minecraft.gardenstuff.integration.NEIIntegration;
import com.jaquadro.minecraft.gardenstuff.integration.ThaumcraftIntegration;
import com.jaquadro.minecraft.gardenstuff.integration.TwilightForestIntegration;
import com.jaquadro.minecraft.gardenstuff.integration.VanillaIntegration;

import cpw.mods.fml.common.Loader;

public class ModIntegration {

    public void init() {}

    public void postInit() {
        if (Loader.isModLoaded("NotEnoughItems")) {
            NEIIntegration.init();
        }

        VanillaIntegration.init();
        ColoredLightsIntegration.init();
        TwilightForestIntegration.init();
        ThaumcraftIntegration.init();
    }
}
