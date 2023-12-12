package com.jaquadro.minecraft.gardenapi.api.plant;

import com.jaquadro.minecraft.gardenapi.api.util.IUniqueID;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface ISaplingRegistry {

    void registerSapling(Item var1, int var2, Block var3, int var4, Block var5, int var6);

    void registerSapling(IUniqueID var1, IUniqueID var2, IUniqueID var3);

    IUniqueID getLeavesForSapling(Item var1);

    IUniqueID getLeavesForSapling(Item var1, int var2);

    IUniqueID getLeavesForSapling(IUniqueID var1);

    IUniqueID getWoodForSapling(Item var1);

    IUniqueID getWoodForSapling(Item var1, int var2);

    IUniqueID getWoodForSapling(IUniqueID var1);

    Object getExtendedData(Item var1, String var2);

    Object getExtendedData(Item var1, int var2, String var3);

    Object getExtendedData(IUniqueID var1, String var2);

    void putExtendedData(Item var1, String var2, Object var3);

    void putExtendedData(Item var1, int var2, String var3, Object var4);

    void putExtendedData(IUniqueID var1, String var2, Object var3);
}
