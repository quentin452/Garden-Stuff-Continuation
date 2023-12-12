package com.jaquadro.minecraft.gardenapi.api.util;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;

public interface IUniqueID {

    String getModId();

    String getName();

    int getMeta();

    UniqueIdentifier getUniqueIdentifier();

    Block getBlock();
}
