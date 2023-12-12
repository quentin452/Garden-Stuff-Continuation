package com.jaquadro.minecraft.gardenstuff.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import com.jaquadro.minecraft.gardencore.client.renderer.support.ModularBoxRenderer;
import com.jaquadro.minecraft.gardenstuff.block.BlockFence;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class FenceRenderer implements ISimpleBlockRenderingHandler {

    private static final float UN4 = -0.25F;
    private static final float U1 = 0.0615F;
    private static final float U7 = 0.4385F;
    private static final float U8 = 0.5F;
    private static final float U9 = 0.5615F;
    private static final float U15 = 0.9385F;
    private static final float U20 = 1.25F;
    private ModularBoxRenderer boxRenderer = new ModularBoxRenderer();

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        return !(block instanceof BlockFence) ? false
            : this.renderWorldBlock(world, x, y, z, (BlockFence) block, modelId, renderer);
    }

    private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockFence block, int modelId,
        RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        this.boxRenderer.setUnit(0.0D);
        this.boxRenderer.setColor(ModularBoxRenderer.COLOR_WHITE);
        this.boxRenderer.setIcon(block.getIcon(meta));
        this.boxRenderer.flipOpposite = false;
        int connectFlags = block.calcConnectionFlags(world, x, y, z);
        boolean connectYNeg = (connectFlags & 1) != 0;
        boolean connectYPos = (connectFlags & 2) != 0;
        boolean connectZNeg = (connectFlags & 4) != 0;
        boolean connectZPos = (connectFlags & 8) != 0;
        boolean connectXNeg = (connectFlags & 16) != 0;
        boolean connectXPos = (connectFlags & 32) != 0;
        this.boxRenderer.renderSolidBox(
            world,
            block,
            (double) x,
            (double) y,
            (double) z,
            0.43849998712539673D,
            0.0D,
            0.43849998712539673D,
            0.5615000128746033D,
            1.0D,
            0.5615000128746033D);
        if (block.getPostInterval(meta) == 8) {
            if (connectZNeg) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.43849998712539673D,
                    0.0D,
                    0.0D,
                    0.5615000128746033D,
                    1.0D,
                    0.061500001698732376D);
            }

            if (connectZPos) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.43849998712539673D,
                    0.0D,
                    0.9384999871253967D,
                    0.5615000128746033D,
                    1.0D,
                    1.0D);
            }

            if (connectXNeg) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.0D,
                    0.0D,
                    0.43849998712539673D,
                    0.061500001698732376D,
                    1.0D,
                    0.5615000128746033D);
            }

            if (connectXPos) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.9384999871253967D,
                    0.0D,
                    0.43849998712539673D,
                    1.0D,
                    1.0D,
                    0.5615000128746033D);
            }
        }

        this.boxRenderer.flipOpposite = true;
        boolean fenceBelow = block.isFenceBelow(world, x, y, z);
        boolean abNN = connectYNeg && !fenceBelow && !connectYPos;
        boolean abYY = connectYNeg && fenceBelow && connectYPos;
        if (abNN) {
            this.boxRenderer.setIcon(block.getIconTB(meta));
        }

        float yN = 0.0F;
        float yP = 1.0F;
        if (!abNN && !abYY) {
            if (connectYPos) {
                yN = 0.5F;
            } else if (connectYNeg) {
                yP = 0.5F;
            }
        }

        if (connectZNeg) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.5D,
                (double) yN,
                0.0D,
                0.5D,
                (double) yP,
                0.5D);
        }

        if (connectZPos) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.5D,
                (double) yN,
                0.5D,
                0.5D,
                (double) yP,
                1.0D);
        }

        if (connectXNeg) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.0D,
                (double) yN,
                0.5D,
                0.5D,
                (double) yP,
                0.5D);
        }

        if (connectXPos) {
            this.boxRenderer.renderSolidBox(
                world,
                block,
                (double) x,
                (double) y,
                (double) z,
                0.5D,
                (double) yN,
                0.5D,
                1.0D,
                (double) yP,
                0.5D);
        }

        if (!abNN && !abYY) {
            if (connectYPos) {
                yN = 0.0F;
                yP = 0.5F;
            } else if (connectYNeg) {
                yN = 0.5F;
                yP = 1.0F;
            }

            this.boxRenderer.setIcon(block.getIconTB(meta));
            if (connectZNeg) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.5D,
                    (double) yN,
                    0.0D,
                    0.5D,
                    (double) yP,
                    0.5D);
            }

            if (connectZPos) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.5D,
                    (double) yN,
                    0.5D,
                    0.5D,
                    (double) yP,
                    1.0D);
            }

            if (connectXNeg) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.0D,
                    (double) yN,
                    0.5D,
                    0.5D,
                    (double) yP,
                    0.5D);
            }

            if (connectXPos) {
                this.boxRenderer.renderSolidBox(
                    world,
                    block,
                    (double) x,
                    (double) y,
                    (double) z,
                    0.5D,
                    (double) yN,
                    0.5D,
                    1.0D,
                    (double) yP,
                    0.5D);
            }
        }

        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return ClientProxy.fenceRenderID;
    }
}
