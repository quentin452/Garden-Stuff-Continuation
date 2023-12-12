package com.jaquadro.minecraft.gardentrees.core;

import com.jaquadro.minecraft.gardentrees.integration.ExtraBiomesXLIntegration;
import com.jaquadro.minecraft.gardentrees.integration.GardenCoreIntegration;
import com.jaquadro.minecraft.gardentrees.integration.NaturaIntegration;
import com.jaquadro.minecraft.gardentrees.integration.ThaumcraftIntegration;
import com.jaquadro.minecraft.gardentrees.integration.TinkersConstructIntegration;
import com.jaquadro.minecraft.gardentrees.integration.TreecapitatorIntegration;
import com.jaquadro.minecraft.gardentrees.integration.TwilightForestIntegration;
import com.jaquadro.minecraft.gardentrees.integration.WitcheryIntegration;

public class ModIntegration {

    public void init() {
        TreecapitatorIntegration.init();
    }

    public void postInit() {
        GardenCoreIntegration.init();
        NaturaIntegration.init();
        TwilightForestIntegration.init();
        ThaumcraftIntegration.init();
        ExtraBiomesXLIntegration.init();
        TinkersConstructIntegration.init();
        WitcheryIntegration.init();
    }
}
