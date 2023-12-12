package com.jaquadro.minecraft.gardenapi.api.machine;

import net.minecraft.item.ItemStack;

public interface ICompostRegistry {

    void registerCompostMaterial(String var1, String var2, int var3, ICompostMaterial var4);

    void registerCompostMaterial(String var1, String var2, ICompostMaterial var3);

    void registerCompostMaterial(ItemStack var1, ICompostMaterial var2);

    void registerCompostMaterial(String var1, ICompostMaterial var2);

    void registerCompostMaterial(Class var1, ICompostMaterial var2);

    void removeCompostMaterial(ItemStack var1);

    void removeCompostMaterial(String var1);

    void removeCompostMaterial(Class var1);

    void clear();

    ICompostMaterial getCompostMaterialInfo(ItemStack var1);
}
