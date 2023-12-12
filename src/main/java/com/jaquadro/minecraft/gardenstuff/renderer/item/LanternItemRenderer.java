package com.jaquadro.minecraft.gardenstuff.renderer.item;

import com.jaquadro.minecraft.gardenapi.api.component.ILanternSource;
import com.jaquadro.minecraft.gardenapi.internal.Api;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import com.jaquadro.minecraft.gardencore.util.RenderHelperState;
import com.jaquadro.minecraft.gardenstuff.block.BlockLantern;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class LanternItemRenderer implements IItemRenderer {

    private RenderHelper renderHelper = new RenderHelper();
    private float[] colorScratch = new float[3];

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        RenderBlocks renderer = this.getRenderer(data);
        if (renderer != null) {
            Block block = Block.getBlockFromItem(item.getItem());
            if (block instanceof BlockLantern) {
                Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                this.renderLantern((BlockLantern) block, item, renderer, type);
            }
        }
    }

    private void renderLantern(BlockLantern block, ItemStack item, RenderBlocks renderer, ItemRenderType renderType) {
        GL11.glEnable(3008);
        if (renderType == ItemRenderType.INVENTORY) {
            GL11.glScalef(1.2F, 1.2F, 1.2F);
        }

        block.setBlockBoundsForItemRender();
        this.renderHelper.setRenderBounds(block);
        this.renderHelper.renderBlock((IBlockAccess) null, block, item.getItemDamage());
        if (renderType != ItemRenderType.INVENTORY) {
            this.renderHelper.state.renderFromInside = true;
            this.renderHelper.state.renderMinY = 0.004999999888241291D;
            this.renderHelper.renderBlock((IBlockAccess) null, block, item.getItemDamage());
            this.renderHelper.state.renderFromInside = false;
        } else {
            this.renderHelper.state.renderMaxY = 0.004999999888241291D;
            this.renderHelper.renderFace(1, (IBlockAccess) null, block, block.getIcon(0, item.getItemDamage()), 0);
        }

        this.renderHelper.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        this.renderHelper.renderCrossedSquares(block, item.getItemDamage(), block.getIconTopCross());
        ILanternSource lanternSource = Api.instance.registries()
            .lanternSources()
            .getLanternSource(block.getLightSource(item));
        if (lanternSource != null) {
            lanternSource.renderItem(renderer, renderType, block.getLightSourceMeta(item));
        }

        if (block.isGlass(item)) {
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glDisable(2977);
            RenderHelper.calculateBaseColor(this.colorScratch, block.getBlockColor());
            RenderHelper.setTessellatorColor(Tessellator.instance, this.colorScratch);
            Tessellator.instance.setBrightness(15728880);
            IIcon glass = block.getIconStainedGlass(item.getItemDamage());
            this.renderHelper.setRenderBounds(block);
            RenderHelperState var10000 = this.renderHelper.state;
            var10000.renderMinX += 0.01D;
            var10000 = this.renderHelper.state;
            var10000.renderMinZ += 0.01D;
            var10000 = this.renderHelper.state;
            var10000.renderMaxX -= 0.01D;
            var10000 = this.renderHelper.state;
            var10000.renderMaxZ -= 0.01D;
            var10000 = this.renderHelper.state;
            var10000.renderMaxY -= 0.01D;
            this.renderHelper.renderFace(4, (IBlockAccess) null, block, glass, 0);
            this.renderHelper.renderFace(5, (IBlockAccess) null, block, glass, 0);
            this.renderHelper.renderFace(2, (IBlockAccess) null, block, glass, 0);
            this.renderHelper.renderFace(3, (IBlockAccess) null, block, glass, 0);
            this.renderHelper.renderFace(1, (IBlockAccess) null, block, glass, 0);
            GL11.glDisable(3042);
        }

    }

    private RenderBlocks getRenderer(Object[] data) {
        Object[] var2 = data;
        int var3 = data.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Object obj = var2[var4];
            if (obj instanceof RenderBlocks) {
                return (RenderBlocks) obj;
            }
        }

        return null;
    }
}
