package com.jaquadro.minecraft.gardencore.core;

import com.jaquadro.minecraft.gardencommon.integration.IntegrationRegistry;
import com.jaquadro.minecraft.gardencore.integration.ExtraBiomesXLIntegration;
import com.jaquadro.minecraft.gardencore.integration.MineTweakerIntegration;
import com.jaquadro.minecraft.gardencore.integration.NaturaIntegration;
import com.jaquadro.minecraft.gardencore.integration.PlantMegaPackIntegration;
import com.jaquadro.minecraft.gardencore.integration.ThaumcraftIntegration;
import com.jaquadro.minecraft.gardencore.integration.TwilightForestIntegration;
import com.jaquadro.minecraft.gardencore.integration.WeeeFlowersIntegration;
import com.jaquadro.minecraft.gardencore.integration.WitcheryIntegration;

import cpw.mods.fml.common.Loader;

public class ModIntegration {

    public void init() {
        IntegrationRegistry.instance()
            .init();
    }

    public void postInit() {
        IntegrationRegistry.instance()
            .postInit();
        ExtraBiomesXLIntegration.init();
        NaturaIntegration.init();
        TwilightForestIntegration.init();
        WeeeFlowersIntegration.init();
        ThaumcraftIntegration.init();
        MineTweakerIntegration.init();
        WitcheryIntegration.init();
        if (Loader.isModLoaded("plantmegapack")) {
            PlantMegaPackIntegration.init();
        }

    }
}
