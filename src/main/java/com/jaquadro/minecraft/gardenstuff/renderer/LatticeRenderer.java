package com.jaquadro.minecraft.gardenstuff.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachable;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardenstuff.block.BlockLattice;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LatticeRenderer implements ISimpleBlockRenderingHandler {

    private static final float UN4 = -0.25F;
    private static final float U7 = 0.4375F;
    private static final float U8 = 0.5F;
    private static final float U9 = 0.5625F;
    private static final float U20 = 1.25F;
    private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        this.boxRenderer.setUnit(0.0625D);
        this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
        this.boxRenderer.setIcon(block.getIcon(0, metadata));
        this.boxRenderer.renderSolidBox(
            (IBlockAccess) null,
            block,
            0.0D,
            0.0D,
            0.0D,
            0.0D,
            0.4475D,
            0.4475D,
            1.0D,
            0.5525D,
            0.5525D);
        this.boxRenderer.renderSolidBox(
            (IBlockAccess) null,
            block,
            0.0D,
            0.0D,
            0.0D,
            0.4475D,
            0.0D,
            0.4475D,
            0.5525D,
            1.0D,
            0.5525D);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return !(block instanceof BlockLattice) ? false
            : this.renderWorldBlock(world, x, y, z, (BlockLattice) block, modelId, renderer);
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockLattice block, int modelId,
        RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        this.boxRenderer.setUnit(0.0625D);
        this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
        this.boxRenderer.setIcon(block.getIcon(world, x, y, z, 0));
        int connectFlags = block.calcConnectionFlags(world, x, y, z);
        boolean connectYNeg = (connectFlags & 1) != 0;
        boolean connectYPos = (connectFlags & 2) != 0;
        boolean connectZNeg = (connectFlags & 4) != 0;
        boolean connectZPos = (connectFlags & 8) != 0;
        boolean connectXNeg = (connectFlags & 16) != 0;
        boolean connectXPos = (connectFlags & 32) != 0;
        boolean extYNeg = (connectFlags & 64) != 0;
        boolean extYPos = (connectFlags & 128) != 0;
        boolean extZNeg = (connectFlags & 256) != 0;
        boolean extZPos = (connectFlags & 512) != 0;
        boolean extXNeg = (connectFlags & 1024) != 0;
        boolean extXPos = (connectFlags & 2048) != 0;
        this.boxRenderer.renderSolidBox(
            world,
            block,
            (double) x,
            (double) y,
            (double) z,
            0.4375D,
            0.4375D,
            0.4375D,
            0.5625D,
            0.5625D,
            0.5625D);
        float yMin = extYNeg ? -0.25F : (connectYNeg ? 0.0F : 0.4375F);
        float yMax = extYPos ? 1.25F : (connectYPos ? 1.0F : 0.5625F);
        IAttachable attachableYN = GardenAPI.instance()
            .registries()
            .attachable()
            .getAttachable(world.getBlock(x, y - 1, z), world.getBlockMetadata(x, y - 1, z));
        if (attachableYN != null && attachableYN.isAttachable(world, x, y - 1, z, 1)) {
            yMin = (float) attachableYN.getAttachDepth(world, x, y - 1, z, 1) - 1.0F;
        }

        IAttachable attachableYP = GardenAPI.instance()
            .registries()
            .attachable()
            .getAttachable(world.getBlock(x, y + 1, z), world.getBlockMetadata(x, y + 1, z));
        if (attachableYP != null && attachableYP.isAttachable(world, x, y + 1, z, 0)) {
            yMax = (float) attachableYP.getAttachDepth(world, x, y + 1, z, 0) + 1.0F;
        }

        if (yMin < 0.4375F) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.4375D,
                (double) yMin,
                0.4375D,
                0.5625D,
                0.4375D,
                0.5625D);
        }

        if (yMax > 0.5625F) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.4375D,
                0.5625D,
                0.4375D,
                0.5625D,
                (double) yMax,
                0.5625D);
        }

        float zMin = extZNeg ? -0.25F : (connectZNeg ? 0.0F : 0.4375F);
        float zMax = extZPos ? 1.25F : (connectZPos ? 1.0F : 0.5625F);
        if (zMin < 0.4375F) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.4375D,
                0.4375D,
                (double) zMin,
                0.5625D,
                0.5625D,
                0.4375D);
        }

        if (zMax > 0.5625F) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.4375D,
                0.4375D,
                0.5625D,
                0.5625D,
                0.5625D,
                (double) zMax);
        }

        float xMin = extXNeg ? -0.25F : (connectXNeg ? 0.0F : 0.4375F);
        float xMax = extXPos ? 1.25F : (connectXPos ? 1.0F : 0.5625F);
        if (xMin < 0.4375F) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                (double) xMin,
                0.4375D,
                0.4375D,
                0.4375D,
                0.5625D,
                0.5625D);
        }

        if (xMax > 0.5625F) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.5625D,
                0.4375D,
                0.4375D,
                (double) xMax,
                0.5625D,
                0.5625D);
        }

        IIcon vineIcon = Blocks.vine.getIcon(0, 0);
        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ClientProxy.latticeRenderID;
    }
}
