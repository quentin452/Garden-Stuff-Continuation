package com.jaquadro.minecraft.gardenapi.api.plant;

import java.util.Set;

import net.minecraft.block.Block;

import com.jaquadro.minecraft.gardenapi.api.util.IUniqueID;

public interface IWoodRegistry {

    void registerWoodType(Block var1, int var2);

    void registerWoodType(IUniqueID var1);

    Set registeredTypes();

    boolean contains(Block var1, int var2);

    boolean contains(IUniqueID var1);
}
