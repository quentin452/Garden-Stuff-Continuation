package com.jaquadro.minecraft.gardencontainers.client.renderer;

import com.jaquadro.minecraft.gardencontainers.block.BlockMediumPot;
import com.jaquadro.minecraft.gardencontainers.block.tile.TileEntityMediumPot;
import com.jaquadro.minecraft.gardencontainers.core.ClientProxy;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class MediumPotRenderer implements ISimpleBlockRenderingHandler {

    private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();
    private float[] colorScratch = new float[3];

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (block instanceof BlockMediumPot) {
            this.renderInventoryBlock((BlockMediumPot) block, metadata, modelId, renderer);
        }
    }

    private void renderInventoryBlock(BlockMediumPot block, int metadata, int modelId, RenderBlocks renderer) {
        IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, metadata & 15);
        boolean blendEnabled = GL11.glIsEnabled(3042);
        if (blendEnabled) {
            GL11.glDisable(3042);
        }

        this.boxRenderer.setUnit(0.0625D);
        this.boxRenderer.setIcon(icon);
        this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        this.boxRenderer
            .renderBox((IBlockAccess) null, block, 0.0D, 0.0D, 0.0D, 0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D, 0, 2);
        if (!blendEnabled) {
            GL11.glDisable(3042);
        }

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (!(block instanceof BlockMediumPot)) {
            return false;
        } else {
            try {
                return this.renderWorldBlockPass0(world, x, y, z, (BlockMediumPot) block, modelId, renderer);
            } catch (Exception var9) {
                return false;
            }
        }
    }

    private boolean renderWorldBlockPass0(IBlockAccess world, int x, int y, int z, BlockMediumPot block, int modelId,
        RenderBlocks renderer) {
        int metadata = world.getBlockMetadata(x, y, z);
        this.boxRenderer.setUnit(0.0625D);
        this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);

        for (int i = 0; i < 6; ++i) {
            this.boxRenderer.setIcon(renderer.getBlockIconFromSideAndMetadata(block, i, metadata), i);
        }

        this.boxRenderer.renderBox(
            world,
            block,
            (double) x,
            (double) y,
            (double) z,
            0.125D,
            0.0D,
            0.125D,
            0.875D,
            0.75D,
            0.875D,
            0,
            2);
        TileEntityMediumPot te = block.getTileEntity(world, x, y, z);
        if (te != null && te.getSubstrate() != null
            && te.getSubstrate()
                .getItem() instanceof ItemBlock) {
            Block substrate = Block.getBlockFromItem(
                te.getSubstrate()
                    .getItem());
            int substrateData = te.getSubstrate()
                .getItemDamage();
            if (substrate != Blocks.water) {
                IIcon substrateIcon = renderer.getBlockIconFromSideAndMetadata(substrate, 1, substrateData);
                int color = substrate.colorMultiplier(world, x, y, z);
                if (color == Blocks.grass.colorMultiplier(world, x, y, z)) {
                    color = ColorizerGrass
                        .getGrassColor((double) te.getBiomeTemperature(), (double) te.getBiomeHumidity());
                }

                RenderHelper.calculateBaseColor(this.colorScratch, color);
                RenderHelper.instance.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.875D);
                RenderHelper.instance.renderFace(
                    1,
                    world,
                    block,
                    x,
                    y,
                    z,
                    substrateIcon,
                    this.colorScratch[0],
                    this.colorScratch[1],
                    this.colorScratch[2]);
            }
        }

        return true;
    }

    private boolean renderWorldBlockPass1(IBlockAccess world, int x, int y, int z, BlockMediumPot block, int modelId,
        RenderBlocks renderer) {
        RenderHelper.instance.renderEmptyPlane(x, y, z);
        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ClientProxy.mediumPotRenderID;
    }
}
