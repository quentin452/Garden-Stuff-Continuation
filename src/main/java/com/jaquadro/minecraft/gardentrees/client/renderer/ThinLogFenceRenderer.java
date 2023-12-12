package com.jaquadro.minecraft.gardentrees.client.renderer;

import com.jaquadro.minecraft.gardentrees.block.BlockThinLogFence;
import com.jaquadro.minecraft.gardentrees.core.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class ThinLogFenceRenderer implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (block instanceof BlockThinLogFence) {
            this.renderInventoryBlock((BlockThinLogFence) block, metadata, modelId, renderer);
        }
    }

    public void renderInventoryBlock(BlockThinLogFence block, int metadata, int modelId, RenderBlocks renderer) {
        block.setBlockBoundsForItemRender();
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
        renderer.setRenderBoundsFromBlock(block);
        this.renderPostAtOrigin(block, metadata, renderer, tessellator);
        this.renderSideAtOrigin(block, metadata, renderer, tessellator, 0.5F, 1.0F);
        GL11.glTranslatef(1.0F, 0.0F, 0.0F);
        renderer.setRenderBoundsFromBlock(block);
        this.renderPostAtOrigin(block, metadata, renderer, tessellator);
        this.renderSideAtOrigin(block, metadata, renderer, tessellator, 0.0F, 0.5F);
        GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    private void renderSideAtOrigin(BlockThinLogFence block, int metadata, RenderBlocks renderer,
        Tessellator tessellator, float xs, float xe) {
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.setRenderBounds((double) xs, 0.0D, 0.0D, (double) xe, 1.0D, 1.0D);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.5D, block.getSideIcon());
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.setRenderBounds((double) xs, 0.0D, 0.0D, (double) xe, 1.0D, 1.0D);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, -0.5D, block.getSideIcon());
        tessellator.draw();
    }

    private void renderPostAtOrigin(BlockThinLogFence block, int metadata, RenderBlocks renderer,
        Tessellator tessellator) {
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.5D, block.getSideIcon());
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
        renderer.renderFaceZPos(block, 0.0D, 0.0D, -0.5D, block.getSideIcon());
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
        renderer.renderFaceXNeg(block, 0.5D, 0.0D, 0.0D, block.getSideIcon());
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
        renderer.renderFaceXPos(block, -0.5D, 0.0D, 0.0D, block.getSideIcon());
        tessellator.draw();
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return !(block instanceof BlockThinLogFence) ? false
            : this.renderWorldBlock(world, x, y, z, (BlockThinLogFence) block, modelId, renderer);
    }

    private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockThinLogFence block, int modelId,
        RenderBlocks renderer) {
        float margin = block.getMargin();
        renderer.setRenderBounds(
            (double) margin,
            0.0D,
            (double) margin,
            (double) (1.0F - margin),
            1.0D,
            (double) (1.0F - margin));
        renderer.renderStandardBlock(block, x, y, z);
        IIcon sideIcon = block.getSideIcon();
        boolean connectedZNeg = block.canConnectFenceTo(world, x, y, z - 1);
        boolean connectedZPos = block.canConnectFenceTo(world, x, y, z + 1);
        boolean connectedXNeg = block.canConnectFenceTo(world, x - 1, y, z);
        boolean connectedXPos = block.canConnectFenceTo(world, x + 1, y, z);
        if (connectedXNeg || connectedXPos) {
            if (connectedXNeg && connectedXPos) {
                renderer.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 0.5D);
            } else if (connectedXNeg) {
                renderer.setRenderBounds(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 0.5D);
            } else if (connectedXPos) {
                renderer.setRenderBounds(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 0.5D);
            }

            renderer.flipTexture = true;
            renderer.renderFaceZNeg(block, (double) x, (double) y, (double) z, sideIcon);
            renderer.flipTexture = false;
            renderer.renderFaceZPos(block, (double) x, (double) y, (double) z, sideIcon);
        }

        if (connectedZNeg || connectedZPos) {
            if (connectedZNeg && connectedZPos) {
                renderer.setRenderBounds(0.5D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
            } else if (connectedZNeg) {
                renderer.setRenderBounds(0.5D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
            } else if (connectedZPos) {
                renderer.setRenderBounds(0.5D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D);
            }

            renderer.flipTexture = true;
            renderer.renderFaceXPos(block, (double) x, (double) y, (double) z, sideIcon);
            renderer.flipTexture = false;
            renderer.renderFaceXNeg(block, (double) x, (double) y, (double) z, sideIcon);
        }

        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return ClientProxy.thinLogFenceRenderID;
    }
}
