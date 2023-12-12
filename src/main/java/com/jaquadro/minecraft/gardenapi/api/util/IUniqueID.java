package com.jaquadro.minecraft.gardenapi.api.util;

import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;

public interface IUniqueID {

    String getModId();

    String getName();

    int getMeta();

    UniqueIdentifier getUniqueIdentifier();

    Block getBlock();
}
