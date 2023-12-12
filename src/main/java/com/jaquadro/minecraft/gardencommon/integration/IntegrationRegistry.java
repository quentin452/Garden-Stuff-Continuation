package com.jaquadro.minecraft.gardencommon.integration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.jaquadro.minecraft.gardencommon.integration.mods.AgriCraft;
import com.jaquadro.minecraft.gardencommon.integration.mods.BiomesOPlenty;
import com.jaquadro.minecraft.gardencommon.integration.mods.Botania;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class IntegrationRegistry {

    private static IntegrationRegistry instance;
    private List registry = new ArrayList();

    private IntegrationRegistry() {}

    public static IntegrationRegistry instance() {
        if (instance == null) {
            instance = new IntegrationRegistry();
        }

        return instance;
    }

    public void add(IntegrationModule module) {
        this.registry.add(module);
    }

    public void init() {
        for (int i = 0; i < this.registry.size(); ++i) {
            IntegrationModule module = (IntegrationModule) this.registry.get(i);
            if (module.getModID() != null && !Loader.isModLoaded(module.getModID())) {
                this.registry.remove(i--);
            } else {
                try {
                    module.init();
                } catch (Throwable var4) {
                    this.registry.remove(i--);
                    FMLLog.log(
                        "GardenStuff",
                        Level.INFO,
                        "Could not load integration module: " + module.getClass()
                            .getName() + " (init)",
                        new Object[0]);
                }
            }
        }

    }

    public void postInit() {
        for (int i = 0; i < this.registry.size(); ++i) {
            IntegrationModule module = (IntegrationModule) this.registry.get(i);

            try {
                module.postInit();
            } catch (Throwable var4) {
                this.registry.remove(i--);
                FMLLog.log(
                    "GardenStuff",
                    Level.INFO,
                    "Could not load integration module: " + module.getClass()
                        .getName() + " (post-init)",
                    new Object[0]);
            }
        }

    }

    static {
        IntegrationRegistry reg = instance();
        if (Loader.isModLoaded("AgriCraft")) {
            reg.add(new AgriCraft());
        }

        if (Loader.isModLoaded("BiomesOPlenty")) {
            reg.add(new BiomesOPlenty());
        }

        if (Loader.isModLoaded("Botania")) {
            reg.add(new Botania());
        }

    }
}
