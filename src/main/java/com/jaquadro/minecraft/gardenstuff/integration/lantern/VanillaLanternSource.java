package com.jaquadro.minecraft.gardenstuff.integration.lantern;

import java.util.Random;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import com.jaquadro.minecraft.gardenapi.api.component.IRedstoneSource;
import com.jaquadro.minecraft.gardenapi.api.component.StandardLanternSource;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLantern;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class VanillaLanternSource {

    public static class GlowstoneSource extends StandardLanternSource {

        public GlowstoneSource() {
            super(
                new StandardLanternSource.LanternSourceInfo(
                    "glowstone",
                    Items.glowstone_dust,
                    Blocks.glowstone.getLightValue()));
        }

        @SideOnly(Side.CLIENT)
        public void render(RenderBlocks renderer, int x, int y, int z, int meta, int pass) {
            renderer.setRenderBounds(0.3D, 0.0D, 0.3D, 0.7D, 0.4D, 0.7D);
            renderer.renderStandardBlock(Blocks.glowstone, x, y, z);
        }

        @SideOnly(Side.CLIENT)
        public void renderItem(RenderBlocks renderer, ItemRenderType renderType, int meta) {
            RenderHelper.instance.setRenderBounds(0.3D, 0.0D, 0.3D, 0.7D, 0.4D, 0.7D);
            RenderHelper.instance.renderBlock((IBlockAccess) null, Blocks.glowstone, 0);
        }
    }

    public static class RedstoneTorchSource extends StandardLanternSource implements IRedstoneSource {

        public RedstoneTorchSource() {
            super(
                new StandardLanternSource.LanternSourceInfo(
                    "redstoneTorch",
                    Item.getItemFromBlock(Blocks.redstone_torch),
                    Blocks.redstone_torch.getLightValue()));
        }

        @SideOnly(Side.CLIENT)
        public void render(RenderBlocks renderer, int x, int y, int z, int meta, int pass) {
            renderer.renderBlockAllFaces(Blocks.redstone_torch, x, y, z);
        }

        @SideOnly(Side.CLIENT)
        public void renderItem(RenderBlocks renderer, ItemRenderType renderType, int meta) {
            RenderHelper renderHelper = RenderHelper.instance;
            renderHelper.setRenderBounds(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D);
            renderHelper
                .renderFace(2, (IBlockAccess) null, Blocks.redstone_torch, Blocks.redstone_torch.getIcon(2, 0), meta);
            renderHelper
                .renderFace(3, (IBlockAccess) null, Blocks.redstone_torch, Blocks.redstone_torch.getIcon(3, 0), meta);
            renderHelper.setRenderBounds(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D);
            renderHelper
                .renderFace(4, (IBlockAccess) null, Blocks.redstone_torch, Blocks.redstone_torch.getIcon(4, 0), meta);
            renderHelper
                .renderFace(5, (IBlockAccess) null, Blocks.redstone_torch, Blocks.redstone_torch.getIcon(5, 0), meta);
            renderHelper.setRenderBounds(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.625D, 0.5625D);
            renderHelper
                .renderFace(1, (IBlockAccess) null, Blocks.redstone_torch, Blocks.redstone_torch.getIcon(1, 0), meta);
        }

        @SideOnly(Side.CLIENT)
        public void renderParticle(World world, int x, int y, int z, Random rand, int meta) {
            double px = (double) ((float) x + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 0.2D;
            double py = (double) ((float) y + 0.6F) + (double) (rand.nextFloat() - 0.5F) * 0.2D + 0.10000000149011612D;
            double pz = (double) ((float) z + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 0.2D;
            world.spawnParticle("reddust", px, py, pz, 0.0D, 0.0D, 0.0D);
        }

        public int strongPowerValue(int meta) {
            return 15;
        }

        public int weakPowerValue(int meta) {
            return 15;
        }
    }

    public static class TorchLanternSource extends StandardLanternSource {

        public TorchLanternSource() {
            super(
                new StandardLanternSource.LanternSourceInfo(
                    "torch",
                    Item.getItemFromBlock(Blocks.torch),
                    Blocks.torch.getLightValue()));
        }

        @SideOnly(Side.CLIENT)
        public void render(RenderBlocks renderer, int x, int y, int z, int meta, int pass) {
            renderer.renderBlockAllFaces(Blocks.torch, x, y, z);
        }

        @SideOnly(Side.CLIENT)
        public void renderItem(RenderBlocks renderer, ItemRenderType renderType, int meta) {
            RenderHelper renderHelper = RenderHelper.instance;
            renderHelper.setRenderBounds(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D);
            renderHelper.renderFace(2, (IBlockAccess) null, Blocks.torch, Blocks.torch.getIcon(2, 0), meta);
            renderHelper.renderFace(3, (IBlockAccess) null, Blocks.torch, Blocks.torch.getIcon(3, 0), meta);
            renderHelper.setRenderBounds(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D);
            renderHelper.renderFace(4, (IBlockAccess) null, Blocks.torch, Blocks.torch.getIcon(4, 0), meta);
            renderHelper.renderFace(5, (IBlockAccess) null, Blocks.torch, Blocks.torch.getIcon(5, 0), meta);
            renderHelper.setRenderBounds(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.625D, 0.5625D);
            renderHelper.renderFace(1, (IBlockAccess) null, Blocks.torch, Blocks.torch.getIcon(1, 0), meta);
        }

        @SideOnly(Side.CLIENT)
        public void renderParticle(World world, int x, int y, int z, Random rand, int meta) {
            double px = (double) ((float) x + 0.5F);
            double py = (double) ((float) y + 0.7F);
            double pz = (double) ((float) z + 0.5F);
            TileEntityLantern tile = (TileEntityLantern) world.getTileEntity(x, y, z);
            if (tile == null || !tile.hasGlass()) {
                world.spawnParticle("smoke", px, py, pz, 0.0D, 0.0D, 0.0D);
            }

            world.spawnParticle("flame", px, py, pz, 0.0D, 0.0D, 0.0D);
        }
    }
}
