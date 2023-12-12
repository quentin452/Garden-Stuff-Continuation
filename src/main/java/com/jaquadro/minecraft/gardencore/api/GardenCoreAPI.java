package com.jaquadro.minecraft.gardencore.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaSet;

public final class GardenCoreAPI {

    private UniqueMetaSet smallFlameHostBlocks = new UniqueMetaSet();
    private List bonemealHandlers = new ArrayList();
    private static GardenCoreAPI instance = new GardenCoreAPI();

    public static GardenCoreAPI instance() {
        return instance;
    }

    private GardenCoreAPI() {}

    public void registerBonemealHandler(IBonemealHandler handler) {
        this.bonemealHandlers.add(handler);
    }

    public List getBonemealHandlers() {
        return this.bonemealHandlers;
    }

    public void registerSmallFlameHostBlock(Block block, int meta) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        if (id != null) {
            this.smallFlameHostBlocks.register(id);
        }

    }

    public boolean blockCanHostSmallFlame(Block block, int meta) {
        UniqueMetaIdentifier id = ModBlocks.getUniqueMetaID(block, meta);
        return this.smallFlameHostBlocks.contains(id);
    }
}
