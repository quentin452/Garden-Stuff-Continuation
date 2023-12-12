package com.jaquadro.minecraft.gardenstuff.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.jaquadro.minecraft.gardencore.api.block.IChain;
import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import com.jaquadro.minecraft.gardenstuff.block.BlockCandelabra;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityCandelabra;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CandelabraRenderer implements ISimpleBlockRenderingHandler {

    private ModularBoxRenderer boxrender = new ModularBoxRenderer();

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (block instanceof BlockCandelabra) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            this.renderInventoryBlock((BlockCandelabra) block, metadata, modelId, renderer);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }

    private void renderInventoryBlock(BlockCandelabra block, int metadata, int modelId, RenderBlocks renderer) {
        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
        this.renderCandle(renderer.blockAccess, block, metadata);
        if (metadata >= 1) {
            RenderHelper.instance.state.setRenderOffset(-0.34375D, 0.0D, 0.0D);
            this.renderCandle(renderer.blockAccess, block, metadata);
            RenderHelper.instance.state.setRenderOffset(0.34375D, 0.0D, 0.0D);
            this.renderCandle(renderer.blockAccess, block, metadata);
        }

        if (metadata >= 2) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, -0.34375D);
            this.renderCandle(renderer.blockAccess, block, metadata);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.34375D);
            this.renderCandle(renderer.blockAccess, block, metadata);
        }

        RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
        RenderHelper.instance.renderCrossedSquares(block, metadata, block.getIconBase());
        if (metadata >= 1) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(2, renderer.blockAccess, block, block.getIconArmExt(), metadata);
            RenderHelper.instance.state.setRenderOffset(0.5D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(3, renderer.blockAccess, block, block.getIconArmExt(), metadata);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(2, renderer.blockAccess, block, block.getIconArmExt(), metadata);
            RenderHelper.instance.state.setRenderOffset(-0.5D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(3, renderer.blockAccess, block, block.getIconArmExt(), metadata);
        }

        if (metadata >= 2) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, -0.5D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(4, renderer.blockAccess, block, block.getIconArmExt(), metadata);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(5, renderer.blockAccess, block, block.getIconArmExt(), metadata);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.5D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(4, renderer.blockAccess, block, block.getIconArmExt(), metadata);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(5, renderer.blockAccess, block, block.getIconArmExt(), metadata);
        }

        RenderHelper.instance.state.clearRenderOffset();
    }

    private void renderCandle(IBlockAccess world, BlockCandelabra block, int meta) {
        float unit = 0.0625F;
        RenderHelper.instance.state.setColorMult(1.0F, 0.9F, 0.8F, 0.5F);
        this.boxrender.setUnit(0.0D);
        this.boxrender.setColor(ModularBoxRenderer.COLOR_WHITE);
        this.boxrender.setIcon(block.getIconCandleTop(), 1);

        for (int i = 2; i < 6; ++i) {
            this.boxrender.setIcon(block.getIconCandleSide(), i);
        }

        int x = 0;
        int y = 0;
        int z = 0;
        this.boxrender.renderExterior(
            (IBlockAccess) null,
            block,
            (double) x,
            (double) y,
            (double) z,
            (double) (unit * 6.5F),
            (double) (unit * 7.0F),
            (double) (unit * 6.5F),
            (double) (unit * 9.5F),
            (double) (unit * 13.0F),
            (double) (unit * 9.5F),
            0,
            1);
        RenderHelper.instance.state.resetColorMult();
        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        RenderHelper.instance.renderCrossedSquares(block, meta, block.getIconCandleSide());
        RenderHelper.instance.setRenderBounds(
            (double) (unit * 5.75F),
            0.0D,
            (double) (unit * 5.75F),
            (double) (unit * 10.25F),
            (double) (unit * 7.0F),
            (double) (unit * 10.25F));
        RenderHelper.instance.renderFace(1, world, block, ModBlocks.metalBlock.getIcon(0, 0), meta);
        RenderHelper.instance.setRenderBounds(
            (double) (unit * 5.75F),
            (double) (unit * 7.0F),
            (double) (unit * 5.75F),
            (double) (unit * 10.25F),
            1.0D,
            (double) (unit * 10.25F));
        RenderHelper.instance.renderFace(0, world, block, ModBlocks.metalBlock.getIcon(0, 0), meta);
        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        RenderHelper.instance.renderCrossedSquares(block, meta, block.getIconHolderSide());
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return !(block instanceof BlockCandelabra) ? false
            : this.renderWorldBlock(world, x, y, z, (BlockCandelabra) block, modelId, renderer);
    }

    private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockCandelabra block, int modelId,
        RenderBlocks renderer) {
        TileEntityCandelabra tile = (TileEntityCandelabra) world.getTileEntity(x, y, z);
        if (tile == null) {
            return false;
        } else {
            RenderHelper.instance.setColorAndBrightness(world, block, x, y, z);
            RenderHelper.instance.state.setRotateTransform(2, tile.getDirection());
            if (tile.isSconce()) {
                this.renderSconce(world, x, y, z, block, tile.getLevel());
            } else {
                this.renderCandelabra(world, x, y, z, block, tile.getLevel());
            }

            RenderHelper.instance.state.clearRotateTransform();
            return true;
        }
    }

    private void renderCandle(IBlockAccess world, BlockCandelabra block, int x, int y, int z) {
        float unit = 0.0625F;
        RenderHelper.instance.state.setColorMult(1.0F, 0.9F, 0.8F, 0.5F);
        this.boxrender.setUnit(0.0D);
        this.boxrender.setColor(ModularBoxRenderer.COLOR_WHITE);
        this.boxrender.setIcon(block.getIconCandleTop(), 1);

        for (int i = 2; i < 6; ++i) {
            this.boxrender.setIcon(block.getIconCandleSide(), i);
        }

        this.boxrender.renderExterior(
            world,
            block,
            (double) x,
            (double) y,
            (double) z,
            (double) (unit * 6.5F),
            (double) (unit * 7.0F),
            (double) (unit * 6.5F),
            (double) (unit * 9.5F),
            (double) (unit * 13.0F),
            (double) (unit * 9.5F),
            0,
            1);
        RenderHelper.instance.state.resetColorMult();
        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        RenderHelper.instance.renderCrossedSquares(world, block, x, y, z, block.getIconCandleSide());
        RenderHelper.instance.setRenderBounds(
            (double) (unit * 5.75F),
            0.0D,
            (double) (unit * 5.75F),
            (double) (unit * 10.25F),
            (double) (unit * 7.0F),
            (double) (unit * 10.25F));
        RenderHelper.instance.renderFace(1, world, block, x, y, z, ModBlocks.metalBlock.getIcon(0, 0));
        RenderHelper.instance.setRenderBounds(
            (double) (unit * 5.75F),
            (double) (unit * 7.0F),
            (double) (unit * 5.75F),
            (double) (unit * 10.25F),
            1.0D,
            (double) (unit * 10.25F));
        RenderHelper.instance.renderFace(0, world, block, x, y, z, ModBlocks.metalBlock.getIcon(0, 0));
        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        RenderHelper.instance.drawCrossedSquares(block.getIconHolderSide(), (double) x, (double) y, (double) z, 1.0F);
    }

    private void renderCandelabra(IBlockAccess world, int x, int y, int z, BlockCandelabra block, int level) {
        Block blockUpper = world.getBlock(x, y + 1, z);
        boolean hanging = level > 0
            && (blockUpper instanceof IChain || blockUpper.isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN));
        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        if (!hanging) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0625D, 0.0D);
            this.renderCandle(world, block, x, y, z);
        }

        if (level >= 1) {
            RenderHelper.instance.state.setRenderOffset(-0.34375D, 0.0D, 0.0D);
            this.renderCandle(world, block, x, y, z);
            RenderHelper.instance.state.setRenderOffset(0.34375D, 0.0D, 0.0D);
            this.renderCandle(world, block, x, y, z);
        }

        if (level >= 2) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, -0.34375D);
            this.renderCandle(world, block, x, y, z);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.34375D);
            this.renderCandle(world, block, x, y, z);
        }

        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
        if (hanging) {
            RenderHelper.instance.drawCrossedSquares(block.getIconHang(), (double) x, (double) y, (double) z, 1.0F);
        } else {
            RenderHelper.instance.drawCrossedSquares(block.getIconBase(), (double) x, (double) y, (double) z, 1.0F);
        }

        if (level >= 1) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(2, world, block, x, y, z, block.getIconArmExt());
            RenderHelper.instance.state.setRenderOffset(0.5D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(3, world, block, x, y, z, block.getIconArmExt());
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(2, world, block, x, y, z, block.getIconArmExt());
            RenderHelper.instance.state.setRenderOffset(-0.5D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(3, world, block, x, y, z, block.getIconArmExt());
        }

        if (level >= 2) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, -0.5D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(4, world, block, x, y, z, block.getIconArmExt());
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(5, world, block, x, y, z, block.getIconArmExt());
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.5D);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            RenderHelper.instance.renderFace(4, world, block, x, y, z, block.getIconArmExt());
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.0D);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D);
            RenderHelper.instance.renderFace(5, world, block, x, y, z, block.getIconArmExt());
        }

        RenderHelper.instance.state.clearRenderOffset();
    }

    private void renderSconce(IBlockAccess world, int x, int y, int z, BlockCandelabra block, int level) {
        IIcon centerArmIcon = level == 0 ? block.getIconArm() : block.getIconArmExt();
        RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        if (level == 0) {
            RenderHelper.instance.state.setRenderOffset(0.0D, -0.004999999888241291D, -0.25D);
            this.renderCandle(world, block, x, y, z);
        }

        if (level == 1 || level == 2) {
            RenderHelper.instance.state.setRenderOffset(-0.25D, -0.004999999888241291D, -0.25D);
            this.renderCandle(world, block, x, y, z);
            RenderHelper.instance.state.setRenderOffset(0.25D, -0.004999999888241291D, -0.25D);
            this.renderCandle(world, block, x, y, z);
        }

        if (level == 2) {
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, -0.125D);
            this.renderCandle(world, block, x, y, z);
        }

        if (level == 1 || level == 2) {
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
            RenderHelper.instance.state.setRenderOffset(0.5D, 0.0D, 0.0D);
            RenderHelper.instance
                .drawCrossedSquaresBounded(block.getIconArm(), (double) x, (double) y, (double) z, 1.0F);
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            RenderHelper.instance.state.setRenderOffset(-0.5D, 0.0D, 0.0D);
            RenderHelper.instance
                .drawCrossedSquaresBounded(block.getIconArm(), (double) x, (double) y, (double) z, 1.0F);
        }

        if (level == 0 || level == 2) {
            RenderHelper.instance.setRenderBounds(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.03125D);
            RenderHelper.instance.renderFace(4, world, block, x, y, z, centerArmIcon);
            RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
            RenderHelper.instance.state.setRenderOffset(0.0D, 0.0D, 0.03125D);
            RenderHelper.instance.state.flipTexture = true;
            RenderHelper.instance.renderFace(5, world, block, x, y, z, centerArmIcon);
            RenderHelper.instance.state.flipTexture = false;
        }

        RenderHelper.instance.setRenderBounds(0.375D, 0.0625D, 0.0D, 0.625D, 0.375D, 0.0625D);
        RenderHelper.instance.state.clearRenderOffset();
        RenderHelper.instance.renderBlock(world, ModBlocks.metalBlock, x, y, z);
        RenderHelper.instance.state.clearRenderOffset();
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ClientProxy.sconceRenderID;
    }
}
