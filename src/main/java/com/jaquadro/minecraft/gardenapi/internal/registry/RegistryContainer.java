package com.jaquadro.minecraft.gardenapi.internal.registry;

import com.jaquadro.minecraft.gardenapi.api.IRegistryContainer;
import com.jaquadro.minecraft.gardenapi.api.component.ILanternSourceRegistry;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachableRegistry;
import com.jaquadro.minecraft.gardenapi.api.machine.ICompostRegistry;
import com.jaquadro.minecraft.gardenapi.api.plant.IPlantRegistry;
import com.jaquadro.minecraft.gardenapi.api.plant.ISaplingRegistry;
import com.jaquadro.minecraft.gardenapi.api.plant.IWoodRegistry;

public class RegistryContainer implements IRegistryContainer {

    public LanternSourceRegistry lanternSources = new LanternSourceRegistry();
    public CompostRegistry compost = new CompostRegistry();
    public AttachableRegistry attachable = new AttachableRegistry();

    public IPlantRegistry plants() {
        return null;
    }

    public ISaplingRegistry saplings() {
        return null;
    }

    public IWoodRegistry wood() {
        return null;
    }

    public ILanternSourceRegistry lanternSources() {
        return this.lanternSources;
    }

    public ICompostRegistry compost() {
        return this.compost;
    }

    public IAttachableRegistry attachable() {
        return this.attachable;
    }
}
