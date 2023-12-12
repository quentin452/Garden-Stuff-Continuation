package com.jaquadro.minecraft.gardenapi.api.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import java.util.Random;

public interface ILanternSource {

    String getSourceID();

    int getSourceMeta(ItemStack var1);

    boolean isValidSourceItem(ItemStack var1);

    ItemStack getRemovedItem(int var1);

    int getLightLevel(int var1);

    String getLanguageKey(int var1);

    @SideOnly(Side.CLIENT)
    void renderParticle(World var1, int var2, int var3, int var4, Random var5, int var6);

    @SideOnly(Side.CLIENT)
    void render(RenderBlocks var1, int var2, int var3, int var4, int var5, int var6);

    @SideOnly(Side.CLIENT)
    void renderItem(RenderBlocks var1, ItemRenderType var2, int var3);

    @SideOnly(Side.CLIENT)
    boolean renderInPass(int var1);
}
