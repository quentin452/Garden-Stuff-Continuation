package com.jaquadro.minecraft.gardencore.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class RenderHelperAO {

    private RenderHelperState state;
    private int aoBrightnessXYNI;
    private int aoBrightnessYZIN;
    private int aoBrightnessYZIP;
    private int aoBrightnessXYPI;
    private int aoBrightnessXYZNIN;
    private int aoBrightnessXYZNIP;
    private int aoBrightnessXYZPIN;
    private int aoBrightnessXYZPIP;
    private int aoBrightnessXYNN;
    private int aoBrightnessYZNN;
    private int aoBrightnessYZNP;
    private int aoBrightnessXYPN;
    private int aoBrightnessXYNP;
    private int aoBrightnessXYPP;
    private int aoBrightnessYZPN;
    private int aoBrightnessYZPP;
    private int aoBrightnessXZNN;
    private int aoBrightnessXZPN;
    private int aoBrightnessXZNP;
    private int aoBrightnessXZPP;
    private int aoBrightnessXYZNNN;
    private int aoBrightnessXYZNNP;
    private int aoBrightnessXYZPNN;
    private int aoBrightnessXYZPNP;
    private int aoBrightnessXYZNPN;
    private int aoBrightnessXYZPPN;
    private int aoBrightnessXYZNPP;
    private int aoBrightnessXYZPPP;
    private int aoBrightnessXZNI;
    private int aoBrightnessYZNI;
    private int aoBrightnessYZPI;
    private int aoBrightnessXZPI;
    private int aoBrightnessXYIN;
    private int aoBrightnessXZIN;
    private int aoBrightnessXZIP;
    private int aoBrightnessXYIP;
    private int aoBrightnessXYZNNI;
    private int aoBrightnessXYZNPI;
    private int aoBrightnessXYZPNI;
    private int aoBrightnessXYZPPI;
    private int aoBrightnessXYZINN;
    private int aoBrightnessXYZINP;
    private int aoBrightnessXYZIPN;
    private int aoBrightnessXYZIPP;
    private float aoLightValueScratchXYNI;
    private float aoLightValueScratchYZIN;
    private float aoLightValueScratchYZIP;
    private float aoLightValueScratchXYPI;
    private float aoLightValueScratchXYZNIN;
    private float aoLightValueScratchXYZNIP;
    private float aoLightValueScratchXYZPIN;
    private float aoLightValueScratchXYZPIP;
    private float aoLightValueScratchXYNN;
    private float aoLightValueScratchYZNN;
    private float aoLightValueScratchYZNP;
    private float aoLightValueScratchXYPN;
    private float aoLightValueScratchXYNP;
    private float aoLightValueScratchXYPP;
    private float aoLightValueScratchYZPN;
    private float aoLightValueScratchYZPP;
    private float aoLightValueScratchXZNN;
    private float aoLightValueScratchXZPN;
    private float aoLightValueScratchXZNP;
    private float aoLightValueScratchXZPP;
    private float aoLightValueScratchXYZNNN;
    private float aoLightValueScratchXYZNNP;
    private float aoLightValueScratchXYZPNN;
    private float aoLightValueScratchXYZPNP;
    private float aoLightValueScratchXYZNPN;
    private float aoLightValueScratchXYZPPN;
    private float aoLightValueScratchXYZNPP;
    private float aoLightValueScratchXYZPPP;
    private float aoLightValueScratchXZNI;
    private float aoLightValueScratchYZNI;
    private float aoLightValueScratchYZPI;
    private float aoLightValueScratchXZPI;
    private float aoLightValueScratchXYIN;
    private float aoLightValueScratchXZIN;
    private float aoLightValueScratchXZIP;
    private float aoLightValueScratchXYIP;
    private float aoLightValueScratchXYZNNI;
    private float aoLightValueScratchXYZNPI;
    private float aoLightValueScratchXYZPNI;
    private float aoLightValueScratchXYZPPI;
    private float aoLightValueScratchXYZINN;
    private float aoLightValueScratchXYZINP;
    private float aoLightValueScratchXYZIPN;
    private float aoLightValueScratchXYZIPP;

    public RenderHelperAO(RenderHelperState state) {
        this.state = state;
    }

    public void setupYNegAOPartial(IBlockAccess blockAccess, Block block, int x, int y, int z, float r, float g,
        float b) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        int yGrass = this.state.renderMinY <= 0.0D ? y - 1 : y;
        boolean blocksGrassXYPN = !blockAccess.getBlock(x + 1, yGrass, z)
            .getCanBlockGrass();
        boolean blocksGrassXYNN = !blockAccess.getBlock(x - 1, yGrass, z)
            .getCanBlockGrass();
        boolean blocksGrassYZNP = !blockAccess.getBlock(x, yGrass, z + 1)
            .getCanBlockGrass();
        boolean blocksGrassYZNN = !blockAccess.getBlock(x, yGrass, z - 1)
            .getCanBlockGrass();
        if (this.state.renderMinY > 0.0D) {
            this.setupAOBrightnessYNeg(
                blockAccess,
                block,
                x,
                y,
                z,
                blocksGrassXYPN,
                blocksGrassXYNN,
                blocksGrassYZNP,
                blocksGrassYZNN);
        }

        this.setupAOBrightnessYPos(
            blockAccess,
            block,
            x,
            y - 1,
            z,
            blocksGrassXYPN,
            blocksGrassXYNN,
            blocksGrassYZNP,
            blocksGrassYZNN);
        float yClamp = MathHelper.clamp_float((float) this.state.renderMinY, 0.0F, 1.0F);
        this.mixAOBrightnessLightValueY(yClamp, 1.0F - yClamp);
        int blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        if (this.state.renderMinY <= 0.0D || !blockAccess.getBlock(x, y - 1, z)
            .isOpaqueCube()) {
            blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z);
        }

        float aoOpposingBlock = blockAccess.getBlock(x, y - 1, z)
            .getAmbientOcclusionLightValue();
        float aoXYZNNP = (this.aoLightValueScratchXYNI + this.aoLightValueScratchXYZNIP
            + aoOpposingBlock
            + this.aoLightValueScratchYZIP) / 4.0F;
        float aoXYZPNP = (aoOpposingBlock + this.aoLightValueScratchYZIP
            + this.aoLightValueScratchXYPI
            + this.aoLightValueScratchXYZPIP) / 4.0F;
        float aoXYZPNN = (this.aoLightValueScratchYZIN + aoOpposingBlock
            + this.aoLightValueScratchXYZPIN
            + this.aoLightValueScratchXYPI) / 4.0F;
        float aoXYZNNN = (this.aoLightValueScratchXYZNIN + this.aoLightValueScratchXYNI
            + this.aoLightValueScratchYZIN
            + aoOpposingBlock) / 4.0F;
        float aoTR = (float) ((double) aoXYZNNP * this.state.renderMinX * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZPNP * this.state.renderMinX * this.state.renderMaxZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMinX) * this.state.renderMaxZ
            + (double) aoXYZNNN * (1.0D - this.state.renderMinX) * (1.0D - this.state.renderMaxZ));
        float aoTL = (float) ((double) aoXYZNNP * this.state.renderMinX * (1.0D - this.state.renderMinZ)
            + (double) aoXYZPNP * this.state.renderMinX * this.state.renderMinZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMinX) * this.state.renderMinZ
            + (double) aoXYZNNN * (1.0D - this.state.renderMinX) * (1.0D - this.state.renderMinZ));
        float aoBL = (float) ((double) aoXYZNNP * this.state.renderMaxX * (1.0D - this.state.renderMinZ)
            + (double) aoXYZPNP * this.state.renderMaxX * this.state.renderMinZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMaxX) * this.state.renderMinZ
            + (double) aoXYZNNN * (1.0D - this.state.renderMaxX) * (1.0D - this.state.renderMinZ));
        float aoBR = (float) ((double) aoXYZNNP * this.state.renderMaxX * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZPNP * this.state.renderMaxX * this.state.renderMaxZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMaxX) * this.state.renderMaxZ
            + (double) aoXYZNNN * (1.0D - this.state.renderMaxX) * (1.0D - this.state.renderMaxZ));
        int brXYZNNP = getAOBrightness(
            this.aoBrightnessXYNI,
            this.aoBrightnessXYZNIP,
            this.aoBrightnessYZIP,
            blockBrightness);
        int brXYZPNP = getAOBrightness(
            this.aoBrightnessYZIP,
            this.aoBrightnessXYPI,
            this.aoBrightnessXYZPIP,
            blockBrightness);
        int brXYZPNN = getAOBrightness(
            this.aoBrightnessYZIN,
            this.aoBrightnessXYZPIN,
            this.aoBrightnessXYPI,
            blockBrightness);
        int brXYZNNN = getAOBrightness(
            this.aoBrightnessXYZNIN,
            this.aoBrightnessXYNI,
            this.aoBrightnessYZIN,
            blockBrightness);
        this.state.brightnessTopRight = mixAOBrightness(
            brXYZNNP,
            brXYZNNN,
            brXYZPNN,
            brXYZPNP,
            this.state.renderMaxX * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMaxX) * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMaxX) * this.state.renderMaxZ,
            this.state.renderMaxX * this.state.renderMaxZ);
        this.state.brightnessTopLeft = mixAOBrightness(
            brXYZNNP,
            brXYZNNN,
            brXYZPNN,
            brXYZPNP,
            this.state.renderMaxX * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMaxX) * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMaxX) * this.state.renderMinZ,
            this.state.renderMaxX * this.state.renderMinZ);
        this.state.brightnessBottomLeft = mixAOBrightness(
            brXYZNNP,
            brXYZNNN,
            brXYZPNN,
            brXYZPNP,
            this.state.renderMinX * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMinX) * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMinX) * this.state.renderMinZ,
            this.state.renderMinX * this.state.renderMinZ);
        this.state.brightnessBottomRight = mixAOBrightness(
            brXYZNNP,
            brXYZNNN,
            brXYZPNN,
            brXYZPNP,
            this.state.renderMinX * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMinX) * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMinX) * this.state.renderMaxZ,
            this.state.renderMinX * this.state.renderMaxZ);
        this.state.setColor(r * this.state.colorMultYNeg, g * this.state.colorMultYNeg, b * this.state.colorMultYNeg);
        this.state.scaleColor(this.state.colorTopLeft, aoTL);
        this.state.scaleColor(this.state.colorBottomLeft, aoBL);
        this.state.scaleColor(this.state.colorBottomRight, aoBR);
        this.state.scaleColor(this.state.colorTopRight, aoTR);
    }

    public void setupYPosAOPartial(IBlockAccess blockAccess, Block block, int x, int y, int z, float r, float g,
        float b) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        if (this.state.renderMaxY >= 1.0D) {
            ++y;
        }

        this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z);
        this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z);
        this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1);
        this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1);
        this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
        this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
        this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
        this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
        this.aoLightValueScratchXYNP = blockAccess.getBlock(x - 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYPP = blockAccess.getBlock(x + 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZPN = blockAccess.getBlock(x, y, z - 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZPP = blockAccess.getBlock(x, y, z + 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
        this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
        this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
        this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
        boolean blocksGrassXYPP = blockAccess.getBlock(x + 1, y + 1, z)
            .getCanBlockGrass();
        boolean blocksGrassXYNP = blockAccess.getBlock(x - 1, y + 1, z)
            .getCanBlockGrass();
        boolean blocksGrassYZPP = blockAccess.getBlock(x, y + 1, z + 1)
            .getCanBlockGrass();
        boolean blocksGrassYZPN = blockAccess.getBlock(x, y + 1, z - 1)
            .getCanBlockGrass();
        if (blocksGrassYZPN || blocksGrassXYNP) {
            this.aoLightValueScratchXYZNPN = blockAccess.getBlock(x - 1, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z - 1);
        }

        if (blocksGrassYZPN || blocksGrassXYPP) {
            this.aoLightValueScratchXYZPPN = blockAccess.getBlock(x + 1, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z - 1);
        }

        if (blocksGrassYZPP || blocksGrassXYNP) {
            this.aoLightValueScratchXYZNPP = blockAccess.getBlock(x - 1, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z + 1);
        }

        if (blocksGrassYZPP || blocksGrassXYPP) {
            this.aoLightValueScratchXYZPPP = blockAccess.getBlock(x + 1, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z + 1);
        }

        if (this.state.renderMaxY >= 1.0D) {
            --y;
        }

        int blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        if (this.state.renderMaxY >= 1.0D || !blockAccess.getBlock(x, y + 1, z)
            .isOpaqueCube()) {
            blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z);
        }

        float aoOpposingBlock = blockAccess.getBlock(x, y + 1, z)
            .getAmbientOcclusionLightValue();
        float aoXYZNPN = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP
            + this.aoLightValueScratchYZPP
            + aoOpposingBlock) / 4.0F;
        float aoXYZNPP = (this.aoLightValueScratchYZPP + aoOpposingBlock
            + this.aoLightValueScratchXYZPPP
            + this.aoLightValueScratchXYPP) / 4.0F;
        float aoXYZPPP = (aoOpposingBlock + this.aoLightValueScratchYZPN
            + this.aoLightValueScratchXYPP
            + this.aoLightValueScratchXYZPPN) / 4.0F;
        float aoXYZPPN = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN
            + aoOpposingBlock
            + this.aoLightValueScratchYZPN) / 4.0F;
        float aoTL = (float) ((double) aoXYZPPP * this.state.renderMaxX * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZNPP * this.state.renderMaxX * this.state.renderMaxZ
            + (double) aoXYZNPN * (1.0D - this.state.renderMaxX) * this.state.renderMaxZ
            + (double) aoXYZPPN * (1.0D - this.state.renderMaxX) * (1.0D - this.state.renderMaxZ));
        float aoBL = (float) ((double) aoXYZPPP * this.state.renderMaxX * (1.0D - this.state.renderMinZ)
            + (double) aoXYZNPP * this.state.renderMaxX * this.state.renderMinZ
            + (double) aoXYZNPN * (1.0D - this.state.renderMaxX) * this.state.renderMinZ
            + (double) aoXYZPPN * (1.0D - this.state.renderMaxX) * (1.0D - this.state.renderMinZ));
        float aoBR = (float) ((double) aoXYZPPP * this.state.renderMinX * (1.0D - this.state.renderMinZ)
            + (double) aoXYZNPP * this.state.renderMinX * this.state.renderMinZ
            + (double) aoXYZNPN * (1.0D - this.state.renderMinX) * this.state.renderMinZ
            + (double) aoXYZPPN * (1.0D - this.state.renderMinX) * (1.0D - this.state.renderMinZ));
        float aoTR = (float) ((double) aoXYZPPP * this.state.renderMinX * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZNPP * this.state.renderMinX * this.state.renderMaxZ
            + (double) aoXYZNPN * (1.0D - this.state.renderMinX) * this.state.renderMaxZ
            + (double) aoXYZPPN * (1.0D - this.state.renderMinX) * (1.0D - this.state.renderMaxZ));
        int brXYZPPN = getAOBrightness(
            this.aoBrightnessXYNP,
            this.aoBrightnessXYZNPP,
            this.aoBrightnessYZPP,
            blockBrightness);
        int brXYZNPN = getAOBrightness(
            this.aoBrightnessYZPP,
            this.aoBrightnessXYPP,
            this.aoBrightnessXYZPPP,
            blockBrightness);
        int brXYZNPP = getAOBrightness(
            this.aoBrightnessYZPN,
            this.aoBrightnessXYZPPN,
            this.aoBrightnessXYPP,
            blockBrightness);
        int brXYZPPP = getAOBrightness(
            this.aoBrightnessXYZNPN,
            this.aoBrightnessXYNP,
            this.aoBrightnessYZPN,
            blockBrightness);
        this.state.brightnessTopLeft = mixAOBrightness(
            brXYZPPP,
            brXYZPPN,
            brXYZNPN,
            brXYZNPP,
            this.state.renderMaxZ,
            this.state.renderMaxX);
        this.state.brightnessBottomLeft = mixAOBrightness(
            brXYZPPP,
            brXYZPPN,
            brXYZNPN,
            brXYZNPP,
            this.state.renderMinZ,
            this.state.renderMaxX);
        this.state.brightnessBottomRight = mixAOBrightness(
            brXYZPPP,
            brXYZPPN,
            brXYZNPN,
            brXYZNPP,
            this.state.renderMinZ,
            this.state.renderMinX);
        this.state.brightnessTopRight = mixAOBrightness(
            brXYZPPP,
            brXYZPPN,
            brXYZNPN,
            brXYZNPP,
            this.state.renderMaxZ,
            this.state.renderMinX);
        this.state.setColor(r * this.state.colorMultYPos, g * this.state.colorMultYPos, b * this.state.colorMultYPos);
        this.state.scaleColor(this.state.colorTopLeft, aoTL);
        this.state.scaleColor(this.state.colorBottomLeft, aoBL);
        this.state.scaleColor(this.state.colorBottomRight, aoBR);
        this.state.scaleColor(this.state.colorTopRight, aoTR);
    }

    public void setupZNegAOPartial(IBlockAccess blockAccess, Block block, int x, int y, int z, float r, float g,
        float b) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        int zGrass = this.state.renderMinZ <= 0.0D ? z - 1 : z;
        boolean blocksGrassXZPN = !blockAccess.getBlock(x + 1, y, zGrass)
            .getCanBlockGrass();
        boolean blocksGrassXZNN = !blockAccess.getBlock(x - 1, y, zGrass)
            .getCanBlockGrass();
        boolean blocksGrassYZPN = !blockAccess.getBlock(x, y + 1, zGrass)
            .getCanBlockGrass();
        boolean blocksGrassYZNN = !blockAccess.getBlock(x, y - 1, zGrass)
            .getCanBlockGrass();
        if (this.state.renderMinZ > 0.0D) {
            this.setupAOBrightnessZNeg(
                blockAccess,
                block,
                x,
                y,
                z,
                blocksGrassXZPN,
                blocksGrassXZNN,
                blocksGrassYZPN,
                blocksGrassYZNN);
        }

        this.setupAOBrightnessZPos(
            blockAccess,
            block,
            x,
            y,
            z - 1,
            blocksGrassXZPN,
            blocksGrassXZNN,
            blocksGrassYZPN,
            blocksGrassYZNN);
        float zClamp = MathHelper.clamp_float((float) this.state.renderMinZ, 0.0F, 1.0F);
        this.mixAOBrightnessLightValueZ(zClamp, 1.0F - zClamp);
        int blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        if (this.state.renderMinZ <= 0.0D || !blockAccess.getBlock(x, y, z - 1)
            .isOpaqueCube()) {
            blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1);
        }

        float aoOpposingBlock = blockAccess.getBlock(x, y, z - 1)
            .getAmbientOcclusionLightValue();
        float aoXYZNPN = (this.aoLightValueScratchXZNI + this.aoLightValueScratchXYZNPI
            + aoOpposingBlock
            + this.aoLightValueScratchYZPI) / 4.0F;
        float aoXYZPPN = (aoOpposingBlock + this.aoLightValueScratchYZPI
            + this.aoLightValueScratchXZPI
            + this.aoLightValueScratchXYZPPI) / 4.0F;
        float aoXYZPNN = (this.aoLightValueScratchYZNI + aoOpposingBlock
            + this.aoLightValueScratchXYZPNI
            + this.aoLightValueScratchXZPI) / 4.0F;
        float aoXYZNNN = (this.aoLightValueScratchXYZNNI + this.aoLightValueScratchXZNI
            + this.aoLightValueScratchYZNI
            + aoOpposingBlock) / 4.0F;
        float aoTL = (float) ((double) aoXYZNPN * this.state.renderMaxY * (1.0D - this.state.renderMinX)
            + (double) aoXYZPPN * this.state.renderMaxY * this.state.renderMinX
            + (double) aoXYZPNN * (1.0D - this.state.renderMaxY) * this.state.renderMinX
            + (double) aoXYZNNN * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinX));
        float aoBL = (float) ((double) aoXYZNPN * this.state.renderMaxY * (1.0D - this.state.renderMaxX)
            + (double) aoXYZPPN * this.state.renderMaxY * this.state.renderMaxX
            + (double) aoXYZPNN * (1.0D - this.state.renderMaxY) * this.state.renderMaxX
            + (double) aoXYZNNN * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxX));
        float aoBR = (float) ((double) aoXYZNPN * this.state.renderMinY * (1.0D - this.state.renderMaxX)
            + (double) aoXYZPPN * this.state.renderMinY * this.state.renderMaxX
            + (double) aoXYZPNN * (1.0D - this.state.renderMinY) * this.state.renderMaxX
            + (double) aoXYZNNN * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxX));
        float aoTR = (float) ((double) aoXYZNPN * this.state.renderMinY * (1.0D - this.state.renderMinX)
            + (double) aoXYZPPN * this.state.renderMinY * this.state.renderMinX
            + (double) aoXYZPNN * (1.0D - this.state.renderMinY) * this.state.renderMinX
            + (double) aoXYZNNN * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinX));
        int brXYZNPN = getAOBrightness(
            this.aoBrightnessXZNI,
            this.aoBrightnessXYZNPI,
            this.aoBrightnessYZPI,
            blockBrightness);
        int brXYZPPN = getAOBrightness(
            this.aoBrightnessYZPI,
            this.aoBrightnessXZPI,
            this.aoBrightnessXYZPPI,
            blockBrightness);
        int brXYZPNN = getAOBrightness(
            this.aoBrightnessYZNI,
            this.aoBrightnessXYZPNI,
            this.aoBrightnessXZPI,
            blockBrightness);
        int brXYZNNN = getAOBrightness(
            this.aoBrightnessXYZNNI,
            this.aoBrightnessXZNI,
            this.aoBrightnessYZNI,
            blockBrightness);
        this.state.brightnessTopLeft = mixAOBrightness(
            brXYZNPN,
            brXYZPPN,
            brXYZPNN,
            brXYZNNN,
            this.state.renderMaxY * (1.0D - this.state.renderMinX),
            this.state.renderMaxY * this.state.renderMinX,
            (1.0D - this.state.renderMaxY) * this.state.renderMinX,
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinX));
        this.state.brightnessBottomLeft = mixAOBrightness(
            brXYZNPN,
            brXYZPPN,
            brXYZPNN,
            brXYZNNN,
            this.state.renderMaxY * (1.0D - this.state.renderMaxX),
            this.state.renderMaxY * this.state.renderMaxX,
            (1.0D - this.state.renderMaxY) * this.state.renderMaxX,
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxX));
        this.state.brightnessBottomRight = mixAOBrightness(
            brXYZNPN,
            brXYZPPN,
            brXYZPNN,
            brXYZNNN,
            this.state.renderMinY * (1.0D - this.state.renderMaxX),
            this.state.renderMinY * this.state.renderMaxX,
            (1.0D - this.state.renderMinY) * this.state.renderMaxX,
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxX));
        this.state.brightnessTopRight = mixAOBrightness(
            brXYZNPN,
            brXYZPPN,
            brXYZPNN,
            brXYZNNN,
            this.state.renderMinY * (1.0D - this.state.renderMinX),
            this.state.renderMinY * this.state.renderMinX,
            (1.0D - this.state.renderMinY) * this.state.renderMinX,
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinX));
        this.state.setColor(r * this.state.colorMultZNeg, g * this.state.colorMultZNeg, b * this.state.colorMultZNeg);
        this.state.scaleColor(this.state.colorTopLeft, aoTL);
        this.state.scaleColor(this.state.colorBottomLeft, aoBL);
        this.state.scaleColor(this.state.colorBottomRight, aoBR);
        this.state.scaleColor(this.state.colorTopRight, aoTR);
    }

    public void setupZPosAOPartial(IBlockAccess blockAccess, Block block, int x, int y, int z, float r, float g,
        float b) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        int zGrass = this.state.renderMaxZ >= 1.0D ? z + 1 : z;
        boolean blocksGrassXZPP = !blockAccess.getBlock(x + 1, y, zGrass)
            .getCanBlockGrass();
        boolean blocksGrassXZNP = !blockAccess.getBlock(x - 1, y, zGrass)
            .getCanBlockGrass();
        boolean blocksGrassYZPP = !blockAccess.getBlock(x, y + 1, zGrass)
            .getCanBlockGrass();
        boolean blocksGrassYZNP = !blockAccess.getBlock(x, y - 1, zGrass)
            .getCanBlockGrass();
        if (this.state.renderMaxZ < 1.0D) {
            this.setupAOBrightnessZPos(
                blockAccess,
                block,
                x,
                y,
                z,
                blocksGrassXZPP,
                blocksGrassXZNP,
                blocksGrassYZPP,
                blocksGrassYZNP);
        }

        this.setupAOBrightnessZNeg(
            blockAccess,
            block,
            x,
            y,
            z + 1,
            blocksGrassXZPP,
            blocksGrassXZNP,
            blocksGrassYZPP,
            blocksGrassYZNP);
        float zClamp = MathHelper.clamp_float((float) this.state.renderMaxZ, 0.0F, 1.0F);
        this.mixAOBrightnessLightValueZ(zClamp, 1.0F - zClamp);
        int blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        if (this.state.renderMaxZ >= 1.0D || !blockAccess.getBlock(x, y, z + 1)
            .isOpaqueCube()) {
            blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1);
        }

        float aoOpposingBlock = blockAccess.getBlock(x, y, z + 1)
            .getAmbientOcclusionLightValue();
        float aoXYZNPP = (this.aoLightValueScratchXZNI + this.aoLightValueScratchXYZNPI
            + aoOpposingBlock
            + this.aoLightValueScratchYZPI) / 4.0F;
        float aoXYZPPP = (aoOpposingBlock + this.aoLightValueScratchYZPI
            + this.aoLightValueScratchXZPI
            + this.aoLightValueScratchXYZPPI) / 4.0F;
        float aoXYZPNP = (this.aoLightValueScratchYZNI + aoOpposingBlock
            + this.aoLightValueScratchXYZPNI
            + this.aoLightValueScratchXZPI) / 4.0F;
        float aoXYZNNP = (this.aoLightValueScratchXYZNNI + this.aoLightValueScratchXZNI
            + this.aoLightValueScratchYZNI
            + aoOpposingBlock) / 4.0F;
        float aoTL = (float) ((double) aoXYZNPP * this.state.renderMaxY * (1.0D - this.state.renderMinX)
            + (double) aoXYZPPP * this.state.renderMaxY * this.state.renderMinX
            + (double) aoXYZPNP * (1.0D - this.state.renderMaxY) * this.state.renderMinX
            + (double) aoXYZNNP * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinX));
        float aoBL = (float) ((double) aoXYZNPP * this.state.renderMinY * (1.0D - this.state.renderMinX)
            + (double) aoXYZPPP * this.state.renderMinY * this.state.renderMinX
            + (double) aoXYZPNP * (1.0D - this.state.renderMinY) * this.state.renderMinX
            + (double) aoXYZNNP * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinX));
        float aoBR = (float) ((double) aoXYZNPP * this.state.renderMinY * (1.0D - this.state.renderMaxX)
            + (double) aoXYZPPP * this.state.renderMinY * this.state.renderMaxX
            + (double) aoXYZPNP * (1.0D - this.state.renderMinY) * this.state.renderMaxX
            + (double) aoXYZNNP * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxX));
        float aoTR = (float) ((double) aoXYZNPP * this.state.renderMaxY * (1.0D - this.state.renderMaxX)
            + (double) aoXYZPPP * this.state.renderMaxY * this.state.renderMaxX
            + (double) aoXYZPNP * (1.0D - this.state.renderMaxY) * this.state.renderMaxX
            + (double) aoXYZNNP * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxX));
        int brXYZNPP = getAOBrightness(
            this.aoBrightnessXZNI,
            this.aoBrightnessXYZNPI,
            this.aoBrightnessYZPI,
            blockBrightness);
        int brXYZPPP = getAOBrightness(
            this.aoBrightnessYZPI,
            this.aoBrightnessXZPI,
            this.aoBrightnessXYZPPI,
            blockBrightness);
        int brXYZPNP = getAOBrightness(
            this.aoBrightnessYZNI,
            this.aoBrightnessXYZPNI,
            this.aoBrightnessXZPI,
            blockBrightness);
        int brXYZNNP = getAOBrightness(
            this.aoBrightnessXYZNNI,
            this.aoBrightnessXZNI,
            this.aoBrightnessYZNI,
            blockBrightness);
        this.state.brightnessTopLeft = mixAOBrightness(
            brXYZNPP,
            brXYZNNP,
            brXYZPNP,
            brXYZPPP,
            this.state.renderMaxY * (1.0D - this.state.renderMinX),
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinX),
            (1.0D - this.state.renderMaxY) * this.state.renderMinX,
            this.state.renderMaxY * this.state.renderMinX);
        this.state.brightnessBottomLeft = mixAOBrightness(
            brXYZNPP,
            brXYZNNP,
            brXYZPNP,
            brXYZPPP,
            this.state.renderMinY * (1.0D - this.state.renderMinX),
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinX),
            (1.0D - this.state.renderMinY) * this.state.renderMinX,
            this.state.renderMinY * this.state.renderMinX);
        this.state.brightnessBottomRight = mixAOBrightness(
            brXYZNPP,
            brXYZNNP,
            brXYZPNP,
            brXYZPPP,
            this.state.renderMinY * (1.0D - this.state.renderMaxX),
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxX),
            (1.0D - this.state.renderMinY) * this.state.renderMaxX,
            this.state.renderMinY * this.state.renderMaxX);
        this.state.brightnessTopRight = mixAOBrightness(
            brXYZNPP,
            brXYZNNP,
            brXYZPNP,
            brXYZPPP,
            this.state.renderMaxY * (1.0D - this.state.renderMaxX),
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxX),
            (1.0D - this.state.renderMaxY) * this.state.renderMaxX,
            this.state.renderMaxY * this.state.renderMaxX);
        this.state.setColor(r * this.state.colorMultZPos, g * this.state.colorMultZPos, b * this.state.colorMultZPos);
        this.state.scaleColor(this.state.colorTopLeft, aoTL);
        this.state.scaleColor(this.state.colorBottomLeft, aoBL);
        this.state.scaleColor(this.state.colorBottomRight, aoBR);
        this.state.scaleColor(this.state.colorTopRight, aoTR);
    }

    public void setupXNegAOPartial(IBlockAccess blockAccess, Block block, int x, int y, int z, float r, float g,
        float b) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        int xGrass = this.state.renderMinX <= 0.0D ? x - 1 : x;
        boolean blocksGrassXYNP = !blockAccess.getBlock(xGrass, y + 1, z)
            .getCanBlockGrass();
        boolean blocksGrassXYNN = !blockAccess.getBlock(xGrass, y - 1, z)
            .getCanBlockGrass();
        boolean blocksGrassXZNN = !blockAccess.getBlock(xGrass, y, z - 1)
            .getCanBlockGrass();
        boolean blocksGrassXZNP = !blockAccess.getBlock(xGrass, y, z + 1)
            .getCanBlockGrass();
        if (this.state.renderMinX > 0.0D) {
            this.setupAOBrightnessXNeg(
                blockAccess,
                block,
                x,
                y,
                z,
                blocksGrassXYNP,
                blocksGrassXYNN,
                blocksGrassXZNN,
                blocksGrassXZNP);
        }

        this.setupAOBrightnessXPos(
            blockAccess,
            block,
            x - 1,
            y,
            z,
            blocksGrassXYNP,
            blocksGrassXYNN,
            blocksGrassXZNN,
            blocksGrassXZNP);
        float xClamp = MathHelper.clamp_float((float) this.state.renderMinX, 0.0F, 1.0F);
        this.mixAOBrightnessLightValueX(xClamp, 1.0F - xClamp);
        int blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        if (this.state.renderMinX <= 0.0D || !blockAccess.getBlock(x - 1, y, z)
            .isOpaqueCube()) {
            blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z);
        }

        float aoOpposingBlock = blockAccess.getBlock(x - 1, y, z)
            .getAmbientOcclusionLightValue();
        float aoXYZNNP = (this.aoLightValueScratchXYIN + this.aoLightValueScratchXYZINP
            + aoOpposingBlock
            + this.aoLightValueScratchXZIP) / 4.0F;
        float aoXYZNPP = (aoOpposingBlock + this.aoLightValueScratchXZIP
            + this.aoLightValueScratchXYIP
            + this.aoLightValueScratchXYZIPP) / 4.0F;
        float aoXYZNPN = (this.aoLightValueScratchXZIN + aoOpposingBlock
            + this.aoLightValueScratchXYZIPN
            + this.aoLightValueScratchXYIP) / 4.0F;
        float aoXYZNNN = (this.aoLightValueScratchXYZINN + this.aoLightValueScratchXYIN
            + this.aoLightValueScratchXZIN
            + aoOpposingBlock) / 4.0F;
        float aoTL = (float) ((double) aoXYZNPP * this.state.renderMaxY * this.state.renderMaxZ
            + (double) aoXYZNPN * this.state.renderMaxY * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZNNN * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZNNP * (1.0D - this.state.renderMaxY) * this.state.renderMaxZ);
        float aoBL = (float) ((double) aoXYZNPP * this.state.renderMaxY * this.state.renderMinZ
            + (double) aoXYZNPN * this.state.renderMaxY * (1.0D - this.state.renderMinZ)
            + (double) aoXYZNNN * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinZ)
            + (double) aoXYZNNP * (1.0D - this.state.renderMaxY) * this.state.renderMinZ);
        float aoBR = (float) ((double) aoXYZNPP * this.state.renderMinY * this.state.renderMinZ
            + (double) aoXYZNPN * this.state.renderMinY * (1.0D - this.state.renderMinZ)
            + (double) aoXYZNNN * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinZ)
            + (double) aoXYZNNP * (1.0D - this.state.renderMinY) * this.state.renderMinZ);
        float aoTR = (float) ((double) aoXYZNPP * this.state.renderMinY * this.state.renderMaxZ
            + (double) aoXYZNPN * this.state.renderMinY * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZNNN * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZNNP * (1.0D - this.state.renderMinY) * this.state.renderMaxZ);
        int brXYZNNP = getAOBrightness(
            this.aoBrightnessXYIN,
            this.aoBrightnessXYZINP,
            this.aoBrightnessXZIP,
            blockBrightness);
        int brXYZNPP = getAOBrightness(
            this.aoBrightnessXZIP,
            this.aoBrightnessXYIP,
            this.aoBrightnessXYZIPP,
            blockBrightness);
        int brXYZNPN = getAOBrightness(
            this.aoBrightnessXZIN,
            this.aoBrightnessXYZIPN,
            this.aoBrightnessXYIP,
            blockBrightness);
        int brXYZNNN = getAOBrightness(
            this.aoBrightnessXYZINN,
            this.aoBrightnessXYIN,
            this.aoBrightnessXZIN,
            blockBrightness);
        this.state.brightnessTopLeft = mixAOBrightness(
            brXYZNPP,
            brXYZNPN,
            brXYZNNN,
            brXYZNNP,
            this.state.renderMaxY * this.state.renderMaxZ,
            this.state.renderMaxY * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMaxY) * this.state.renderMaxZ);
        this.state.brightnessBottomLeft = mixAOBrightness(
            brXYZNPP,
            brXYZNPN,
            brXYZNNN,
            brXYZNNP,
            this.state.renderMaxY * this.state.renderMinZ,
            this.state.renderMaxY * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMaxY) * this.state.renderMinZ);
        this.state.brightnessBottomRight = mixAOBrightness(
            brXYZNPP,
            brXYZNPN,
            brXYZNNN,
            brXYZNNP,
            this.state.renderMinY * this.state.renderMinZ,
            this.state.renderMinY * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinZ),
            (1.0D - this.state.renderMinY) * this.state.renderMinZ);
        this.state.brightnessTopRight = mixAOBrightness(
            brXYZNPP,
            brXYZNPN,
            brXYZNNN,
            brXYZNNP,
            this.state.renderMinY * this.state.renderMaxZ,
            this.state.renderMinY * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxZ),
            (1.0D - this.state.renderMinY) * this.state.renderMaxZ);
        this.state.setColor(r * this.state.colorMultXNeg, g * this.state.colorMultXNeg, b * this.state.colorMultXNeg);
        this.state.scaleColor(this.state.colorTopLeft, aoTL);
        this.state.scaleColor(this.state.colorBottomLeft, aoBL);
        this.state.scaleColor(this.state.colorBottomRight, aoBR);
        this.state.scaleColor(this.state.colorTopRight, aoTR);
    }

    public void setupXPosAOPartial(IBlockAccess blockAccess, Block block, int x, int y, int z, float r, float g,
        float b) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        int xGrass = this.state.renderMaxX >= 1.0D ? x + 1 : x;
        boolean blocksGrassXYNP = !blockAccess.getBlock(xGrass, y + 1, z)
            .getCanBlockGrass();
        boolean blocksGrassXYNN = !blockAccess.getBlock(xGrass, y - 1, z)
            .getCanBlockGrass();
        boolean blocksGrassXZNN = !blockAccess.getBlock(xGrass, y, z - 1)
            .getCanBlockGrass();
        boolean blocksGrassXZNP = !blockAccess.getBlock(xGrass, y, z + 1)
            .getCanBlockGrass();
        if (this.state.renderMaxX < 1.0D) {
            this.setupAOBrightnessXPos(
                blockAccess,
                block,
                x,
                y,
                z,
                blocksGrassXYNP,
                blocksGrassXYNN,
                blocksGrassXZNN,
                blocksGrassXZNP);
        }

        this.setupAOBrightnessXNeg(
            blockAccess,
            block,
            x + 1,
            y,
            z,
            blocksGrassXYNP,
            blocksGrassXYNN,
            blocksGrassXZNN,
            blocksGrassXZNP);
        float xClamp = MathHelper.clamp_float((float) this.state.renderMaxX, 0.0F, 1.0F);
        this.mixAOBrightnessLightValueX(xClamp, 1.0F - xClamp);
        int blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        if (this.state.renderMaxX >= 1.0D || !blockAccess.getBlock(x + 1, y, z)
            .isOpaqueCube()) {
            blockBrightness = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z);
        }

        float aoOpposingBlock = blockAccess.getBlock(x + 1, y, z)
            .getAmbientOcclusionLightValue();
        float aoXYZPNP = (this.aoLightValueScratchXYIN + this.aoLightValueScratchXYZINP
            + aoOpposingBlock
            + this.aoLightValueScratchXZIP) / 4.0F;
        float aoXYZPNN = (this.aoLightValueScratchXYZINN + this.aoLightValueScratchXYIN
            + this.aoLightValueScratchXZIN
            + aoOpposingBlock) / 4.0F;
        float aoXYZPPN = (this.aoLightValueScratchXZIN + aoOpposingBlock
            + this.aoLightValueScratchXYZIPN
            + this.aoLightValueScratchXYIP) / 4.0F;
        float aoXYZPPP = (aoOpposingBlock + this.aoLightValueScratchXZIP
            + this.aoLightValueScratchXYIP
            + this.aoLightValueScratchXYZIPP) / 4.0F;
        float aoTL = (float) ((double) aoXYZPNP * (1.0D - this.state.renderMinY) * this.state.renderMaxZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZPPN * this.state.renderMinY * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZPPP * this.state.renderMinY * this.state.renderMaxZ);
        float aoBL = (float) ((double) aoXYZPNP * (1.0D - this.state.renderMinY) * this.state.renderMinZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinZ)
            + (double) aoXYZPPN * this.state.renderMinY * (1.0D - this.state.renderMinZ)
            + (double) aoXYZPPP * this.state.renderMinY * this.state.renderMinZ);
        float aoBR = (float) ((double) aoXYZPNP * (1.0D - this.state.renderMaxY) * this.state.renderMinZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinZ)
            + (double) aoXYZPPN * this.state.renderMaxY * (1.0D - this.state.renderMinZ)
            + (double) aoXYZPPP * this.state.renderMaxY * this.state.renderMinZ);
        float aoTR = (float) ((double) aoXYZPNP * (1.0D - this.state.renderMaxY) * this.state.renderMaxZ
            + (double) aoXYZPNN * (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZPPN * this.state.renderMaxY * (1.0D - this.state.renderMaxZ)
            + (double) aoXYZPPP * this.state.renderMaxY * this.state.renderMaxZ);
        int brXYZPNP = getAOBrightness(
            this.aoBrightnessXYIN,
            this.aoBrightnessXYZINP,
            this.aoBrightnessXZIP,
            blockBrightness);
        int brXYZPNN = getAOBrightness(
            this.aoBrightnessXZIP,
            this.aoBrightnessXYIP,
            this.aoBrightnessXYZIPP,
            blockBrightness);
        int brXYZPPN = getAOBrightness(
            this.aoBrightnessXZIN,
            this.aoBrightnessXYZIPN,
            this.aoBrightnessXYIP,
            blockBrightness);
        int brXYZPPP = getAOBrightness(
            this.aoBrightnessXYZINN,
            this.aoBrightnessXYIN,
            this.aoBrightnessXZIN,
            blockBrightness);
        this.state.brightnessTopLeft = mixAOBrightness(
            brXYZPNP,
            brXYZPPP,
            brXYZPPN,
            brXYZPNN,
            (1.0D - this.state.renderMinY) * this.state.renderMaxZ,
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMaxZ),
            this.state.renderMinY * (1.0D - this.state.renderMaxZ),
            this.state.renderMinY * this.state.renderMaxZ);
        this.state.brightnessBottomLeft = mixAOBrightness(
            brXYZPNP,
            brXYZPPP,
            brXYZPPN,
            brXYZPNN,
            (1.0D - this.state.renderMinY) * this.state.renderMinZ,
            (1.0D - this.state.renderMinY) * (1.0D - this.state.renderMinZ),
            this.state.renderMinY * (1.0D - this.state.renderMinZ),
            this.state.renderMinY * this.state.renderMinZ);
        this.state.brightnessBottomRight = mixAOBrightness(
            brXYZPNP,
            brXYZPPP,
            brXYZPPN,
            brXYZPNN,
            (1.0D - this.state.renderMaxY) * this.state.renderMinZ,
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMinZ),
            this.state.renderMaxY * (1.0D - this.state.renderMinZ),
            this.state.renderMaxY * this.state.renderMinZ);
        this.state.brightnessTopRight = mixAOBrightness(
            brXYZPNP,
            brXYZPPP,
            brXYZPPN,
            brXYZPNN,
            (1.0D - this.state.renderMaxY) * this.state.renderMaxZ,
            (1.0D - this.state.renderMaxY) * (1.0D - this.state.renderMaxZ),
            this.state.renderMaxY * (1.0D - this.state.renderMaxZ),
            this.state.renderMaxY * this.state.renderMaxZ);
        this.state.setColor(r * this.state.colorMultXPos, g * this.state.colorMultXPos, b * this.state.colorMultXPos);
        this.state.scaleColor(this.state.colorTopLeft, aoTL);
        this.state.scaleColor(this.state.colorBottomLeft, aoBL);
        this.state.scaleColor(this.state.colorBottomRight, aoBR);
        this.state.scaleColor(this.state.colorTopRight, aoTR);
    }

    private void setupAOBrightnessYNeg(IBlockAccess blockAccess, Block block, int x, int y, int z, boolean bgXP,
        boolean bgXN, boolean bgZP, boolean bgZN) {
        this.aoLightValueScratchXYNN = blockAccess.getBlock(x - 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZNN = blockAccess.getBlock(x, y, z - 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZNP = blockAccess.getBlock(x, y, z + 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYPN = blockAccess.getBlock(x + 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
        this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
        this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
        this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
        this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z);
        this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1);
        this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1);
        this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z);
        this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
        this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
        this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
        this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
        if (bgXN || bgZN) {
            this.aoLightValueScratchXYZNNN = blockAccess.getBlock(x - 1, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z - 1);
        }

        if (bgXN || bgZP) {
            this.aoLightValueScratchXYZNNP = blockAccess.getBlock(x - 1, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z + 1);
        }

        if (bgXP || bgZN) {
            this.aoLightValueScratchXYZPNN = blockAccess.getBlock(x + 1, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z - 1);
        }

        if (bgXP || bgZP) {
            this.aoLightValueScratchXYZPNP = blockAccess.getBlock(x + 1, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z + 1);
        }

    }

    private void setupAOBrightnessYPos(IBlockAccess blockAccess, Block block, int x, int y, int z, boolean bgXP,
        boolean bgXN, boolean bgZP, boolean bgZN) {
        this.aoLightValueScratchXYNP = blockAccess.getBlock(x - 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZPN = blockAccess.getBlock(x, y, z - 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZPP = blockAccess.getBlock(x, y, z + 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYPP = blockAccess.getBlock(x + 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
        this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
        this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
        this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
        this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z);
        this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1);
        this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1);
        this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z);
        this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
        this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
        this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
        this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
        if (bgXN || bgZN) {
            this.aoLightValueScratchXYZNPN = blockAccess.getBlock(x - 1, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z - 1);
        }

        if (bgXN || bgZP) {
            this.aoLightValueScratchXYZNPP = blockAccess.getBlock(x - 1, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z + 1);
        }

        if (bgXP || bgZN) {
            this.aoLightValueScratchXYZPPN = blockAccess.getBlock(x + 1, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z - 1);
        }

        if (bgXP || bgZP) {
            this.aoLightValueScratchXYZPPP = blockAccess.getBlock(x + 1, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z + 1);
        }

    }

    private void setupAOBrightnessZNeg(IBlockAccess blockAccess, Block block, int x, int y, int z, boolean bgXP,
        boolean bgXN, boolean bgYP, boolean bgYN) {
        this.aoLightValueScratchXZNN = blockAccess.getBlock(x - 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZNN = blockAccess.getBlock(x, y - 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZPN = blockAccess.getBlock(x, y + 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXZPN = blockAccess.getBlock(x + 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
        this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
        this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
        this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
        this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z);
        this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z);
        this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z);
        this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z);
        this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
        this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
        this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
        this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
        if (bgXN || bgYN) {
            this.aoLightValueScratchXYZNNN = blockAccess.getBlock(x - 1, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockAccess, x - 1, y - 1, z);
        }

        if (bgXN || bgYP) {
            this.aoLightValueScratchXYZNPN = blockAccess.getBlock(x - 1, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, x - 1, y + 1, z);
        }

        if (bgXP || bgYN) {
            this.aoLightValueScratchXYZPNN = blockAccess.getBlock(x + 1, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockAccess, x + 1, y - 1, z);
        }

        if (bgXP || bgYP) {
            this.aoLightValueScratchXYZPPN = blockAccess.getBlock(x + 1, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, x + 1, y + 1, z);
        }

    }

    private void setupAOBrightnessZPos(IBlockAccess blockAccess, Block block, int x, int y, int z, boolean bgXP,
        boolean bgXN, boolean bgYP, boolean bgYN) {
        this.aoLightValueScratchXZNP = blockAccess.getBlock(x - 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXZPP = blockAccess.getBlock(x + 1, y, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZNP = blockAccess.getBlock(x, y - 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchYZPP = blockAccess.getBlock(x, y + 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
        this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
        this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
        this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
        this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z);
        this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z);
        this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z);
        this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z);
        this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
        this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
        this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
        this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
        if (bgXN || bgYN) {
            this.aoLightValueScratchXYZNNP = blockAccess.getBlock(x - 1, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y - 1, z);
        }

        if (bgXN || bgYP) {
            this.aoLightValueScratchXYZNPP = blockAccess.getBlock(x - 1, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, x - 1, y + 1, z);
        }

        if (bgXP || bgYN) {
            this.aoLightValueScratchXYZPNP = blockAccess.getBlock(x + 1, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y - 1, z);
        }

        if (bgXP || bgYP) {
            this.aoLightValueScratchXYZPPP = blockAccess.getBlock(x + 1, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, x + 1, y + 1, z);
        }

    }

    private void setupAOBrightnessXNeg(IBlockAccess blockAccess, Block block, int x, int y, int z, boolean bgYP,
        boolean bgYN, boolean bgZN, boolean bgZP) {
        this.aoLightValueScratchXYNN = blockAccess.getBlock(x, y - 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXZNN = blockAccess.getBlock(x, y, z - 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXZNP = blockAccess.getBlock(x, y, z + 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYNP = blockAccess.getBlock(x, y + 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
        this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
        this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
        this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
        this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z);
        this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1);
        this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1);
        this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z);
        this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
        this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
        this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
        this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
        if (bgZN || bgYN) {
            this.aoLightValueScratchXYZNNN = blockAccess.getBlock(x, y - 1, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z - 1);
        }

        if (bgZP || bgYN) {
            this.aoLightValueScratchXYZNNP = blockAccess.getBlock(x, y - 1, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z + 1);
        }

        if (bgZN || bgYP) {
            this.aoLightValueScratchXYZNPN = blockAccess.getBlock(x, y + 1, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z - 1);
        }

        if (bgZP || bgYP) {
            this.aoLightValueScratchXYZNPP = blockAccess.getBlock(x, y + 1, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z + 1);
        }

    }

    private void setupAOBrightnessXPos(IBlockAccess blockAccess, Block block, int x, int y, int z, boolean bgYP,
        boolean bgYN, boolean bgZN, boolean bgZP) {
        this.aoLightValueScratchXYPN = blockAccess.getBlock(x, y - 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXZPN = blockAccess.getBlock(x, y, z - 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXZPP = blockAccess.getBlock(x, y, z + 1)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYPP = blockAccess.getBlock(x, y + 1, z)
            .getAmbientOcclusionLightValue();
        this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
        this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
        this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
        this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
        this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z);
        this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1);
        this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1);
        this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z);
        this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
        this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
        this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
        this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
        if (bgYN || bgZN) {
            this.aoLightValueScratchXYZPNN = blockAccess.getBlock(x, y - 1, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z - 1);
        }

        if (bgYN || bgZP) {
            this.aoLightValueScratchXYZPNP = blockAccess.getBlock(x, y - 1, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z + 1);
        }

        if (bgYP || bgZN) {
            this.aoLightValueScratchXYZPPN = blockAccess.getBlock(x, y + 1, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z - 1);
        }

        if (bgYP || bgZP) {
            this.aoLightValueScratchXYZPPP = blockAccess.getBlock(x, y + 1, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z + 1);
        }

    }

    private void mixAOBrightnessLightValueY(float fMin, float fMax) {
        if (fMin == 1.0F && fMax == 0.0F) {
            this.aoLightValueScratchXYNI = this.aoLightValueScratchXYNN;
            this.aoLightValueScratchYZIN = this.aoLightValueScratchYZNN;
            this.aoLightValueScratchYZIP = this.aoLightValueScratchYZNP;
            this.aoLightValueScratchXYPI = this.aoLightValueScratchXYPN;
            this.aoLightValueScratchXYZNIN = this.aoLightValueScratchXYZNNN;
            this.aoLightValueScratchXYZNIP = this.aoLightValueScratchXYZNNP;
            this.aoLightValueScratchXYZPIN = this.aoLightValueScratchXYZPNN;
            this.aoLightValueScratchXYZPIP = this.aoLightValueScratchXYZPNP;
            this.aoBrightnessXYNI = this.aoBrightnessXYNN;
            this.aoBrightnessYZIN = this.aoBrightnessYZNN;
            this.aoBrightnessYZIP = this.aoBrightnessYZNP;
            this.aoBrightnessXYPI = this.aoBrightnessXYPN;
            this.aoBrightnessXYZNIN = this.aoBrightnessXYZNNN;
            this.aoBrightnessXYZNIP = this.aoBrightnessXYZNNP;
            this.aoBrightnessXYZPIN = this.aoBrightnessXYZPNN;
            this.aoBrightnessXYZPIP = this.aoBrightnessXYZPNP;
        } else if (fMin == 0.0F && fMax == 1.0F) {
            this.aoLightValueScratchXYNI = this.aoLightValueScratchXYNP;
            this.aoLightValueScratchYZIN = this.aoLightValueScratchYZPN;
            this.aoLightValueScratchYZIP = this.aoLightValueScratchYZPP;
            this.aoLightValueScratchXYPI = this.aoLightValueScratchXYPP;
            this.aoLightValueScratchXYZNIN = this.aoLightValueScratchXYZNPN;
            this.aoLightValueScratchXYZNIP = this.aoLightValueScratchXYZNPP;
            this.aoLightValueScratchXYZPIN = this.aoLightValueScratchXYZPPN;
            this.aoLightValueScratchXYZPIP = this.aoLightValueScratchXYZPPP;
            this.aoBrightnessXYNI = this.aoBrightnessXYNP;
            this.aoBrightnessYZIN = this.aoBrightnessYZPN;
            this.aoBrightnessYZIP = this.aoBrightnessYZPP;
            this.aoBrightnessXYPI = this.aoBrightnessXYPP;
            this.aoBrightnessXYZNIN = this.aoBrightnessXYZNPN;
            this.aoBrightnessXYZNIP = this.aoBrightnessXYZNPP;
            this.aoBrightnessXYZPIN = this.aoBrightnessXYZPPN;
            this.aoBrightnessXYZPIP = this.aoBrightnessXYZPPP;
        } else {
            this.aoLightValueScratchXYNI = this.aoLightValueScratchXYNN * fMin + this.aoLightValueScratchXYNP * fMax;
            this.aoLightValueScratchYZIN = this.aoLightValueScratchYZNN * fMin + this.aoLightValueScratchYZPN * fMax;
            this.aoLightValueScratchYZIP = this.aoLightValueScratchYZNP * fMin + this.aoLightValueScratchYZPP * fMax;
            this.aoLightValueScratchXYPI = this.aoLightValueScratchXYPN * fMin + this.aoLightValueScratchXYPP * fMax;
            this.aoLightValueScratchXYZNIN = this.aoLightValueScratchXYZNNN * fMin
                + this.aoLightValueScratchXYZNPN * fMax;
            this.aoLightValueScratchXYZNIP = this.aoLightValueScratchXYZNNP * fMin
                + this.aoLightValueScratchXYZNPP * fMax;
            this.aoLightValueScratchXYZPIN = this.aoLightValueScratchXYZPNN * fMin
                + this.aoLightValueScratchXYZPPN * fMax;
            this.aoLightValueScratchXYZPIP = this.aoLightValueScratchXYZPNP * fMin
                + this.aoLightValueScratchXYZPPP * fMax;
            this.aoBrightnessXYNI = mixAOBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYNP, fMin, fMax);
            this.aoBrightnessYZIN = mixAOBrightness(this.aoBrightnessYZNN, this.aoBrightnessYZPN, fMin, fMax);
            this.aoBrightnessYZIP = mixAOBrightness(this.aoBrightnessYZNP, this.aoBrightnessYZPP, fMin, fMax);
            this.aoBrightnessXYPI = mixAOBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYPP, fMin, fMax);
            this.aoBrightnessXYZNIN = mixAOBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYZNPN, fMin, fMax);
            this.aoBrightnessXYZNIP = mixAOBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYZNPP, fMin, fMax);
            this.aoBrightnessXYZPIN = mixAOBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYZPPN, fMin, fMax);
            this.aoBrightnessXYZPIP = mixAOBrightness(this.aoBrightnessXYZPNP, this.aoBrightnessXYZPPP, fMin, fMax);
        }

    }

    private void mixAOBrightnessLightValueZ(float fMin, float fMax) {
        if (fMin == 1.0F && fMax == 0.0F) {
            this.aoLightValueScratchXZNI = this.aoLightValueScratchXZNN;
            this.aoLightValueScratchYZNI = this.aoLightValueScratchYZNN;
            this.aoLightValueScratchYZPI = this.aoLightValueScratchYZPN;
            this.aoLightValueScratchXZPI = this.aoLightValueScratchXZPN;
            this.aoLightValueScratchXYZNNI = this.aoLightValueScratchXYZNNN;
            this.aoLightValueScratchXYZNPI = this.aoLightValueScratchXYZNPN;
            this.aoLightValueScratchXYZPNI = this.aoLightValueScratchXYZPNN;
            this.aoLightValueScratchXYZPPI = this.aoLightValueScratchXYZPPN;
            this.aoBrightnessXZNI = this.aoBrightnessXZNN;
            this.aoBrightnessYZNI = this.aoBrightnessYZNN;
            this.aoBrightnessYZPI = this.aoBrightnessYZPN;
            this.aoBrightnessXZPI = this.aoBrightnessXZPN;
            this.aoBrightnessXYZNNI = this.aoBrightnessXYZNNN;
            this.aoBrightnessXYZNPI = this.aoBrightnessXYZNPN;
            this.aoBrightnessXYZPNI = this.aoBrightnessXYZPNN;
            this.aoBrightnessXYZPPI = this.aoBrightnessXYZPPN;
        } else if (fMin == 0.0F && fMax == 1.0F) {
            this.aoLightValueScratchXZNI = this.aoLightValueScratchXZNP;
            this.aoLightValueScratchYZNI = this.aoLightValueScratchYZNP;
            this.aoLightValueScratchYZPI = this.aoLightValueScratchYZPP;
            this.aoLightValueScratchXZPI = this.aoLightValueScratchXZPP;
            this.aoLightValueScratchXYZNNI = this.aoLightValueScratchXYZNNP;
            this.aoLightValueScratchXYZNPI = this.aoLightValueScratchXYZNPP;
            this.aoLightValueScratchXYZPNI = this.aoLightValueScratchXYZPNP;
            this.aoLightValueScratchXYZPPI = this.aoLightValueScratchXYZPPP;
            this.aoBrightnessXZNI = this.aoBrightnessXZNP;
            this.aoBrightnessYZNI = this.aoBrightnessYZNP;
            this.aoBrightnessYZPI = this.aoBrightnessYZPP;
            this.aoBrightnessXZPI = this.aoBrightnessXZPP;
            this.aoBrightnessXYZNNI = this.aoBrightnessXYZNNP;
            this.aoBrightnessXYZNPI = this.aoBrightnessXYZNPP;
            this.aoBrightnessXYZPNI = this.aoBrightnessXYZPNP;
            this.aoBrightnessXYZPPI = this.aoBrightnessXYZPPP;
        } else {
            this.aoLightValueScratchXZNI = this.aoLightValueScratchXZNN * fMin + this.aoLightValueScratchXZNP * fMax;
            this.aoLightValueScratchYZNI = this.aoLightValueScratchYZNN * fMin + this.aoLightValueScratchYZNP * fMax;
            this.aoLightValueScratchYZPI = this.aoLightValueScratchYZPN * fMin + this.aoLightValueScratchYZPP * fMax;
            this.aoLightValueScratchXZPI = this.aoLightValueScratchXZPN * fMin + this.aoLightValueScratchXZPP * fMax;
            this.aoLightValueScratchXYZNNI = this.aoLightValueScratchXYZNNN * fMin
                + this.aoLightValueScratchXYZNNP * fMax;
            this.aoLightValueScratchXYZNPI = this.aoLightValueScratchXYZNPN * fMin
                + this.aoLightValueScratchXYZNPP * fMax;
            this.aoLightValueScratchXYZPNI = this.aoLightValueScratchXYZPNN * fMin
                + this.aoLightValueScratchXYZPNP * fMax;
            this.aoLightValueScratchXYZPPI = this.aoLightValueScratchXYZPPN * fMin
                + this.aoLightValueScratchXYZPPP * fMax;
            this.aoBrightnessXZNI = mixAOBrightness(this.aoBrightnessXZNN, this.aoBrightnessXZNP, fMin, fMax);
            this.aoBrightnessYZNI = mixAOBrightness(this.aoBrightnessYZNN, this.aoBrightnessYZNP, fMin, fMax);
            this.aoBrightnessYZPI = mixAOBrightness(this.aoBrightnessYZPN, this.aoBrightnessYZPP, fMin, fMax);
            this.aoBrightnessXZPI = mixAOBrightness(this.aoBrightnessXZPN, this.aoBrightnessXZPP, fMin, fMax);
            this.aoBrightnessXYZNNI = mixAOBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYZNNP, fMin, fMax);
            this.aoBrightnessXYZNPI = mixAOBrightness(this.aoBrightnessXYZNPN, this.aoBrightnessXYZNPP, fMin, fMax);
            this.aoBrightnessXYZPNI = mixAOBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYZPNP, fMin, fMax);
            this.aoBrightnessXYZPPI = mixAOBrightness(this.aoBrightnessXYZPPN, this.aoBrightnessXYZPPP, fMin, fMax);
        }

    }

    private void mixAOBrightnessLightValueX(float fMin, float fMax) {
        if (fMin == 1.0F && fMax == 0.0F) {
            this.aoLightValueScratchXYIN = this.aoLightValueScratchXYNN;
            this.aoLightValueScratchXZIN = this.aoLightValueScratchXZNN;
            this.aoLightValueScratchXZIP = this.aoLightValueScratchXZNP;
            this.aoLightValueScratchXYIP = this.aoLightValueScratchXYNP;
            this.aoLightValueScratchXYZINN = this.aoLightValueScratchXYZNNN;
            this.aoLightValueScratchXYZINP = this.aoLightValueScratchXYZNNP;
            this.aoLightValueScratchXYZIPN = this.aoLightValueScratchXYZNPN;
            this.aoLightValueScratchXYZIPP = this.aoLightValueScratchXYZNPP;
            this.aoBrightnessXYIN = this.aoBrightnessXYNN;
            this.aoBrightnessXZIN = this.aoBrightnessXZNN;
            this.aoBrightnessXZIP = this.aoBrightnessXZNP;
            this.aoBrightnessXYIP = this.aoBrightnessXYNP;
            this.aoBrightnessXYZINN = this.aoBrightnessXYZNNN;
            this.aoBrightnessXYZINP = this.aoBrightnessXYZNNP;
            this.aoBrightnessXYZIPN = this.aoBrightnessXYZNPN;
            this.aoBrightnessXYZIPP = this.aoBrightnessXYZNPP;
        } else if (fMin == 0.0F && fMax == 1.0F) {
            this.aoLightValueScratchXYIN = this.aoLightValueScratchXYPN;
            this.aoLightValueScratchXZIN = this.aoLightValueScratchXZPN;
            this.aoLightValueScratchXZIP = this.aoLightValueScratchXZPP;
            this.aoLightValueScratchXYIP = this.aoLightValueScratchXYPP;
            this.aoLightValueScratchXYZINN = this.aoLightValueScratchXYZPNN;
            this.aoLightValueScratchXYZINP = this.aoLightValueScratchXYZPNP;
            this.aoLightValueScratchXYZIPN = this.aoLightValueScratchXYZPPN;
            this.aoLightValueScratchXYZIPP = this.aoLightValueScratchXYZPPP;
            this.aoBrightnessXYIN = this.aoBrightnessXYPN;
            this.aoBrightnessXZIN = this.aoBrightnessXZPN;
            this.aoBrightnessXZIP = this.aoBrightnessXZPP;
            this.aoBrightnessXYIP = this.aoBrightnessXYPP;
            this.aoBrightnessXYZINN = this.aoBrightnessXYZPNN;
            this.aoBrightnessXYZINP = this.aoBrightnessXYZPNP;
            this.aoBrightnessXYZIPN = this.aoBrightnessXYZPPN;
            this.aoBrightnessXYZIPP = this.aoBrightnessXYZPPP;
        } else {
            this.aoLightValueScratchXYIN = this.aoLightValueScratchXYNN * fMin + this.aoLightValueScratchXYPN * fMax;
            this.aoLightValueScratchXZIN = this.aoLightValueScratchXZNN * fMin + this.aoLightValueScratchXZPN * fMax;
            this.aoLightValueScratchXZIP = this.aoLightValueScratchXZNP * fMin + this.aoLightValueScratchXZPP * fMax;
            this.aoLightValueScratchXYIP = this.aoLightValueScratchXYNP * fMin + this.aoLightValueScratchXYPP * fMax;
            this.aoLightValueScratchXYZINN = this.aoLightValueScratchXYZNNN * fMin
                + this.aoLightValueScratchXYZPNN * fMax;
            this.aoLightValueScratchXYZINP = this.aoLightValueScratchXYZNNP * fMin
                + this.aoLightValueScratchXYZPNP * fMax;
            this.aoLightValueScratchXYZIPN = this.aoLightValueScratchXYZNPN * fMin
                + this.aoLightValueScratchXYZPPN * fMax;
            this.aoLightValueScratchXYZIPP = this.aoLightValueScratchXYZNPP * fMin
                + this.aoLightValueScratchXYZPPP * fMax;
            this.aoBrightnessXYIN = mixAOBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYPN, fMin, fMax);
            this.aoBrightnessXZIN = mixAOBrightness(this.aoBrightnessXZNN, this.aoBrightnessXZPN, fMin, fMax);
            this.aoBrightnessXZIP = mixAOBrightness(this.aoBrightnessXZNP, this.aoBrightnessXZPP, fMin, fMax);
            this.aoBrightnessXYIP = mixAOBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYPP, fMin, fMax);
            this.aoBrightnessXYZINN = mixAOBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYZPNN, fMin, fMax);
            this.aoBrightnessXYZINP = mixAOBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYZPNP, fMin, fMax);
            this.aoBrightnessXYZIPN = mixAOBrightness(this.aoBrightnessXYZNPN, this.aoBrightnessXYZPPN, fMin, fMax);
            this.aoBrightnessXYZIPP = mixAOBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYZPPP, fMin, fMax);
        }

    }

    public static int getAOBrightness(int com1, int com2, int com3, int base) {
        if (com1 == 0) {
            com1 = base;
        }

        if (com2 == 0) {
            com2 = base;
        }

        if (com3 == 0) {
            com3 = base;
        }

        return com1 + com2 + com3 + base >> 2 & 16711935;
    }

    public static int mixAOBrightness(int part1, int part2, int part3, int part4, double weight1, double weight2,
        double weight3, double weight4) {
        int brightSky = (int) ((double) (part1 >> 16 & 255) * weight1 + (double) (part2 >> 16 & 255) * weight2
            + (double) (part3 >> 16 & 255) * weight3
            + (double) (part4 >> 16 & 255) * weight4) & 255;
        int brightBlk = (int) ((double) (part1 & 255) * weight1 + (double) (part2 & 255) * weight2
            + (double) (part3 & 255) * weight3
            + (double) (part4 & 255) * weight4) & 255;
        return brightSky << 16 | brightBlk;
    }

    public static int mixAOBrightness(int brightTL, int brightBL, int brightBR, int brightTR, double lerpTB,
        double lerpLR) {
        double brightSkyL = (double) (brightTL >> 16 & 255) * (1.0D - lerpTB)
            + (double) (brightBL >> 16 & 255) * lerpTB;
        double brightSkyR = (double) (brightTR >> 16 & 255) * (1.0D - lerpTB)
            + (double) (brightBR >> 16 & 255) * lerpTB;
        int brightSky = (int) (brightSkyL * (1.0D - lerpLR) + brightSkyR * lerpLR) & 255;
        double brightBlkL = (double) (brightTL & 255) * (1.0D - lerpTB) + (double) (brightBL & 255) * lerpTB;
        double brightBlkR = (double) (brightTR & 255) * (1.0D - lerpTB) + (double) (brightBR & 255) * lerpTB;
        int brightBlk = (int) (brightBlkL * (1.0D - lerpLR) + brightBlkR * lerpLR) & 255;
        return brightSky << 16 | brightBlk;
    }

    public static int mixAOBrightness(int brightMin, int brightMax, float fMin, float fMax) {
        if (brightMin == 0) {
            return 0;
        } else if (brightMax == 0) {
            return 0;
        } else {
            float brightSky = (float) (brightMin >> 16 & 255) * fMin + (float) (brightMax >> 16 & 255) * fMax;
            float brightBlk = (float) (brightMin & 255) * fMin + (float) (brightMax & 255) * fMax;
            return ((int) brightSky & 255) << 16 | (int) brightBlk & 255;
        }
    }
}
