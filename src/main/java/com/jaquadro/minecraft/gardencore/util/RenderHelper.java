package com.jaquadro.minecraft.gardencore.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class RenderHelper {

    public static final int YNEG = 0;
    public static final int YPOS = 1;
    public static final int ZNEG = 2;
    public static final int ZPOS = 3;
    public static final int XNEG = 4;
    public static final int XPOS = 5;
    public static final int FULL_BRIGHTNESS = 15728880;
    private static final float[][] normMap = new float[][] { { 0.0F, -1.0F, 0.0F }, { 0.0F, 1.0F, 0.0F },
        { 0.0F, 0.0F, -1.0F }, { 0.0F, 0.0F, 1.0F }, { -1.0F, 0.0F, 0.0F }, { 1.0F, 0.0F, 0.0F } };
    public RenderHelperState state = new RenderHelperState();
    private RenderHelperAO aoHelper;
    private RenderHelperLL llHelper;
    private float[] colorScratch;
    public static RenderHelper instance = new RenderHelper();

    public RenderHelper() {
        this.aoHelper = new RenderHelperAO(this.state);
        this.llHelper = new RenderHelperLL(this.state);
        this.colorScratch = new float[3];
    }

    public static void calculateBaseColor(float[] target, int color) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float gray = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
            float rg = (r * 30.0F + g * 70.0F) / 100.0F;
            float rb = (r * 30.0F + b * 70.0F) / 100.0F;
            r = gray;
            g = rg;
            b = rb;
        }

        target[0] = r;
        target[1] = g;
        target[2] = b;
    }

    public static void scaleColor(float[] target, float[] source, float scale) {
        target[0] = source[0] * scale;
        target[1] = source[1] * scale;
        target[2] = source[2] * scale;
    }

    public static void setTessellatorColor(Tessellator tessellator, float[] color) {
        tessellator.setColorOpaque_F(color[0], color[1], color[2]);
    }

    public void renderEmptyPlane(int x, int y, int z) {
        this.state.setRenderBounds(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        this.llHelper.drawFace(0, (double) x, (double) y, (double) z, Blocks.dirt.getIcon(0, 0));
    }

    public void setRenderBounds(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        this.state.setRenderBounds(xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public void setRenderBounds(Block block) {
        this.setRenderBounds(
            block.getBlockBoundsMinX(),
            block.getBlockBoundsMinY(),
            block.getBlockBoundsMinZ(),
            block.getBlockBoundsMaxX(),
            block.getBlockBoundsMaxY(),
            block.getBlockBoundsMaxZ());
    }

    public void setColorAndBrightness(IBlockAccess blockAccess, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
        calculateBaseColor(this.colorScratch, block.colorMultiplier(blockAccess, x, y, z));
        setTessellatorColor(tessellator, this.colorScratch);
    }

    public void renderBlock(IBlockAccess blockAccess, Block block, int meta) {
        calculateBaseColor(this.colorScratch, block.getRenderColor(meta));
        float r = this.colorScratch[0];
        float g = this.colorScratch[1];
        float b = this.colorScratch[2];
        this.renderFace(0, blockAccess, block, block.getIcon(0, meta), r, g, b);
        this.renderFace(1, blockAccess, block, block.getIcon(1, meta), r, g, b);
        this.renderFace(2, blockAccess, block, block.getIcon(2, meta), r, g, b);
        this.renderFace(3, blockAccess, block, block.getIcon(3, meta), r, g, b);
        this.renderFace(4, blockAccess, block, block.getIcon(4, meta), r, g, b);
        this.renderFace(5, blockAccess, block, block.getIcon(5, meta), r, g, b);
    }

    public void renderBlock(IBlockAccess blockAccess, Block block, int x, int y, int z) {
        calculateBaseColor(this.colorScratch, block.colorMultiplier(blockAccess, x, y, z));
        float r = this.colorScratch[0];
        float g = this.colorScratch[1];
        float b = this.colorScratch[2];
        this.renderFace(0, blockAccess, block, x, y, z, block.getIcon(blockAccess, x, y, z, 0), r, g, b);
        this.renderFace(1, blockAccess, block, x, y, z, block.getIcon(blockAccess, x, y, z, 1), r, g, b);
        this.renderFace(2, blockAccess, block, x, y, z, block.getIcon(blockAccess, x, y, z, 2), r, g, b);
        this.renderFace(3, blockAccess, block, x, y, z, block.getIcon(blockAccess, x, y, z, 3), r, g, b);
        this.renderFace(4, blockAccess, block, x, y, z, block.getIcon(blockAccess, x, y, z, 4), r, g, b);
        this.renderFace(5, blockAccess, block, x, y, z, block.getIcon(blockAccess, x, y, z, 5), r, g, b);
    }

    public void renderFace(int face, IBlockAccess blockAccess, Block block, IIcon icon, int meta) {
        calculateBaseColor(this.colorScratch, block.getRenderColor(meta));
        this.renderFaceColorMult(
            face,
            blockAccess,
            block,
            0,
            0,
            0,
            icon,
            this.colorScratch[0],
            this.colorScratch[1],
            this.colorScratch[2]);
    }

    public void renderFace(int face, IBlockAccess blockAccess, Block block, IIcon icon, float r, float g, float b) {
        this.renderFaceColorMult(face, blockAccess, block, 0, 0, 0, icon, r, g, b);
    }

    public void renderFace(int face, IBlockAccess blockAccess, Block block, int x, int y, int z, IIcon icon) {
        calculateBaseColor(this.colorScratch, block.colorMultiplier(blockAccess, x, y, z));
        this.renderFace(
            face,
            blockAccess,
            block,
            x,
            y,
            z,
            icon,
            this.colorScratch[0],
            this.colorScratch[1],
            this.colorScratch[2]);
    }

    public void renderFace(int face, IBlockAccess blockAccess, Block block, int x, int y, int z, IIcon icon, float r,
        float g, float b) {
        if (Minecraft.isAmbientOcclusionEnabled() && blockAccess != null
            && block.getLightValue(blockAccess, x, y, z) == 0) {
            this.renderFaceAOPartial(face, blockAccess, block, x, y, z, icon, r, g, b);
        } else {
            this.renderFaceColorMult(face, blockAccess, block, x, y, z, icon, r, g, b);
        }

    }

    public void renderFaceColorMult(int face, IBlockAccess blockAccess, Block block, int x, int y, int z, IIcon icon,
        float r, float g, float b) {
        this.setupColorMult(face, blockAccess, block, x, y, z, r, g, b);
        face = RenderHelperState.FACE_BY_FACE_ROTATION[face][this.state.rotateTransform];
        this.llHelper.drawFace(face, (double) x, (double) y, (double) z, icon);
        if (blockAccess == null) {
            Tessellator.instance.draw();
        }

    }

    public void renderFaceAOPartial(int face, IBlockAccess blockAccess, Block block, int x, int y, int z, IIcon icon,
        float r, float g, float b) {
        this.state.enableAO = true;
        face = RenderHelperState.FACE_BY_FACE_ROTATION[face][this.state.rotateTransform];
        switch (face) {
            case 0:
                this.aoHelper.setupYNegAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 1:
                this.aoHelper.setupYPosAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 2:
                this.aoHelper.setupZNegAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 3:
                this.aoHelper.setupZPosAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 4:
                this.aoHelper.setupXNegAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 5:
                this.aoHelper.setupXPosAOPartial(blockAccess, block, x, y, z, r, g, b);
        }

        this.llHelper.drawFace(face, (double) x, (double) y, (double) z, icon);
        this.state.enableAO = false;
    }

    public void renderPartialFace(int face, IBlockAccess blockAccess, Block block, int x, int y, int z, IIcon icon,
        double uMin, double vMin, double uMax, double vMax) {
        calculateBaseColor(this.colorScratch, block.colorMultiplier(blockAccess, x, y, z));
        this.renderPartialFace(
            face,
            blockAccess,
            block,
            x,
            y,
            z,
            icon,
            uMin,
            vMin,
            uMax,
            vMax,
            this.colorScratch[0],
            this.colorScratch[1],
            this.colorScratch[2]);
    }

    public void renderPartialFace(int face, IBlockAccess blockAccess, Block block, int x, int y, int z, IIcon icon,
        double uMin, double vMin, double uMax, double vMax, float r, float g, float b) {
        if (Minecraft.isAmbientOcclusionEnabled() && blockAccess != null
            && block.getLightValue(blockAccess, x, y, z) == 0) {
            this.renderPartialFaceAOPartial(face, blockAccess, block, x, y, z, icon, uMin, vMin, uMax, vMax, r, g, b);
        } else {
            this.renderPartialFaceColorMult(face, blockAccess, block, x, y, z, icon, uMin, vMin, uMax, vMax, r, g, b);
        }

    }

    public void renderPartialFaceColorMult(int face, IIcon icon, double uMin, double vMin, double uMax, double vMax,
        float r, float g, float b) {
        this.setupColorMult(face, r, g, b);
        this.renderPartialFace(face, icon, uMin, vMin, uMax, vMax);
        Tessellator.instance.draw();
    }

    public void renderPartialFaceColorMult(int face, IBlockAccess blockAccess, Block block, int x, int y, int z,
        IIcon icon, double uMin, double vMin, double uMax, double vMax, float r, float g, float b) {
        this.setupColorMult(face, blockAccess, block, x, y, z, r, g, b);
        this.renderPartialFace(face, (double) x, (double) y, (double) z, icon, uMin, vMin, uMax, vMax);
        if (blockAccess == null) {
            Tessellator.instance.draw();
        }

    }

    public void renderPartialFaceAOPartial(int face, IBlockAccess blockAccess, Block block, int x, int y, int z,
        IIcon icon, double uMin, double vMin, double uMax, double vMax, float r, float g, float b) {
        this.state.enableAO = true;
        switch (RenderHelperState.FACE_BY_FACE_ROTATION[face][this.state.rotateTransform]) {
            case 0:
                this.aoHelper.setupYNegAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 1:
                this.aoHelper.setupYPosAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 2:
                this.aoHelper.setupZNegAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 3:
                this.aoHelper.setupZPosAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 4:
                this.aoHelper.setupXNegAOPartial(blockAccess, block, x, y, z, r, g, b);
                break;
            case 5:
                this.aoHelper.setupXPosAOPartial(blockAccess, block, x, y, z, r, g, b);
        }

        this.renderPartialFace(face, (double) x, (double) y, (double) z, icon, uMin, vMin, uMax, vMax);
        this.state.enableAO = false;
    }

    public void renderPartialFace(int face, IIcon icon, double uMin, double vMin, double uMax, double vMax) {
        this.state.enableAO = false;
        face = RenderHelperState.FACE_BY_FACE_ROTATION[face][this.state.rotateTransform];
        this.llHelper.drawPartialFace(face, 0.0D, 0.0D, 0.0D, icon, uMin, vMin, uMax, vMax);
    }

    public void renderPartialFace(int face, double x, double y, double z, IIcon icon, double uMin, double vMin,
        double uMax, double vMax) {
        face = RenderHelperState.FACE_BY_FACE_ROTATION[face][this.state.rotateTransform];
        this.llHelper.drawPartialFace(face, x, y, z, icon, uMin, vMin, uMax, vMax);
    }

    public void renderCrossedSquares(Block block, int meta) {
        this.renderCrossedSquares(block, meta, this.getBlockIconFromSideAndMetadata(block, 0, meta));
    }

    public void renderCrossedSquares(Block block, int meta, IIcon icon) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(15728880);
        calculateBaseColor(this.colorScratch, block.getRenderColor(meta));
        setTessellatorColor(tessellator, this.colorScratch);
        boolean lighting = GL11.glIsEnabled(2896);
        GL11.glDisable(2896);
        tessellator.startDrawingQuads();
        this.drawCrossedSquares(icon, 0.0D, 0.0D, 0.0D, 1.0F);
        tessellator.draw();
        if (lighting) {
            GL11.glEnable(2896);
        }

    }

    public void renderCrossedSquares(IBlockAccess blockAccess, Block block, int x, int y, int z) {
        this.renderCrossedSquares(
            blockAccess,
            block,
            x,
            y,
            z,
            this.getBlockIconFromSideAndMetadata(block, 0, blockAccess.getBlockMetadata(x, y, z)));
    }

    public void renderCrossedSquares(IBlockAccess blockAccess, Block block, int x, int y, int z, IIcon icon) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
        calculateBaseColor(this.colorScratch, block.colorMultiplier(blockAccess, x, y, z));
        setTessellatorColor(tessellator, this.colorScratch);
        this.drawCrossedSquares(icon, (double) x, (double) y, (double) z, 1.0F);
    }

    public void drawCrossedSquares(IIcon icon, double x, double y, double z, float scale) {
        Tessellator tessellator = Tessellator.instance;
        x += this.state.renderOffsetX;
        y += this.state.renderOffsetY;
        z += this.state.renderOffsetZ;
        double uMin = (double) icon.getInterpolatedU(this.state.renderMinX * 16.0D);
        double uMax = (double) icon.getInterpolatedU(this.state.renderMaxX * 16.0D);
        double vMin = (double) icon.getInterpolatedV(16.0D - this.state.renderMaxY * 16.0D);
        double vMax = (double) icon.getInterpolatedV(16.0D - this.state.renderMinY * 16.0D);
        double d7 = 0.45D * (double) scale;
        double xMin = x + 0.5D - d7;
        double xMax = x + 0.5D + d7;
        double yMin = y + this.state.renderMinY * (double) scale;
        double yMax = y + this.state.renderMaxY * (double) scale;
        double zMin = z + 0.5D - d7;
        double zMax = z + 0.5D + d7;
        tessellator.addVertexWithUV(xMin, yMax, zMin, uMin, vMin);
        tessellator.addVertexWithUV(xMin, yMin, zMin, uMin, vMax);
        tessellator.addVertexWithUV(xMax, yMin, zMax, uMax, vMax);
        tessellator.addVertexWithUV(xMax, yMax, zMax, uMax, vMin);
        tessellator.addVertexWithUV(xMax, yMax, zMax, uMin, vMin);
        tessellator.addVertexWithUV(xMax, yMin, zMax, uMin, vMax);
        tessellator.addVertexWithUV(xMin, yMin, zMin, uMax, vMax);
        tessellator.addVertexWithUV(xMin, yMax, zMin, uMax, vMin);
        tessellator.addVertexWithUV(xMin, yMax, zMax, uMin, vMin);
        tessellator.addVertexWithUV(xMin, yMin, zMax, uMin, vMax);
        tessellator.addVertexWithUV(xMax, yMin, zMin, uMax, vMax);
        tessellator.addVertexWithUV(xMax, yMax, zMin, uMax, vMin);
        tessellator.addVertexWithUV(xMax, yMax, zMin, uMin, vMin);
        tessellator.addVertexWithUV(xMax, yMin, zMin, uMin, vMax);
        tessellator.addVertexWithUV(xMin, yMin, zMax, uMax, vMax);
        tessellator.addVertexWithUV(xMin, yMax, zMax, uMax, vMin);
    }

    public void drawCrossedSquaresBounded(IIcon icon, double x, double y, double z, float scale) {
        Tessellator tessellator = Tessellator.instance;
        x += this.state.renderOffsetX;
        y += this.state.renderOffsetY;
        z += this.state.renderOffsetZ;
        double vMin = (double) icon.getInterpolatedV(16.0D - this.state.renderMaxY * 16.0D);
        double vMax = (double) icon.getInterpolatedV(16.0D - this.state.renderMinY * 16.0D);
        double xzNN = Math.max(this.state.renderMinX, this.state.renderMinZ);
        double xzPP = Math.min(this.state.renderMaxX, this.state.renderMaxZ);
        double xNN = x + 0.5D - (0.5D - xzNN) * 0.9D;
        double zNN = z + 0.5D - (0.5D - xzNN) * 0.9D;
        double xNP = x + 0.5D - (0.5D - Math.max(this.state.renderMinX, 1.0D - this.state.renderMaxZ)) * 0.9D;
        double zNP = z + 0.5D - (0.5D - Math.min(1.0D - this.state.renderMinX, this.state.renderMaxZ)) * 0.9D;
        double xPN = x + 0.5D - (0.5D - Math.min(this.state.renderMaxX, 1.0D - this.state.renderMinZ)) * 0.9D;
        double zPN = z + 0.5D - (0.5D - Math.max(1.0D - this.state.renderMaxX, this.state.renderMinZ)) * 0.9D;
        double xPP = x + 0.5D - (0.5D - xzPP) * 0.9D;
        double zPP = z + 0.5D - (0.5D - xzPP) * 0.9D;
        double yMin = y + this.state.renderMinY * (double) scale;
        double yMax = y + this.state.renderMaxY * (double) scale;
        double uNN = (double) icon.getInterpolatedU(xzNN * 16.0D);
        double uPP = (double) icon.getInterpolatedU(xzPP * 16.0D);
        tessellator.addVertexWithUV(xNN, yMax, zNN, uNN, vMin);
        tessellator.addVertexWithUV(xNN, yMin, zNN, uNN, vMax);
        tessellator.addVertexWithUV(xPP, yMin, zPP, uPP, vMax);
        tessellator.addVertexWithUV(xPP, yMax, zPP, uPP, vMin);
        uNN = (double) icon.getInterpolatedU(16.0D - xzNN * 16.0D);
        uPP = (double) icon.getInterpolatedU(16.0D - xzPP * 16.0D);
        tessellator.addVertexWithUV(xPP, yMax, zPP, uPP, vMin);
        tessellator.addVertexWithUV(xPP, yMin, zPP, uPP, vMax);
        tessellator.addVertexWithUV(xNN, yMin, zNN, uNN, vMax);
        tessellator.addVertexWithUV(xNN, yMax, zNN, uNN, vMin);
        double uNP = (double) icon
            .getInterpolatedU(Math.max(this.state.renderMinX, 1.0D - this.state.renderMaxZ) * 16.0D);
        double uPN = (double) icon
            .getInterpolatedU(Math.min(this.state.renderMaxX, 1.0D - this.state.renderMinZ) * 16.0D);
        tessellator.addVertexWithUV(xNP, yMax, zNP, uNP, vMin);
        tessellator.addVertexWithUV(xNP, yMin, zNP, uNP, vMax);
        tessellator.addVertexWithUV(xPN, yMin, zPN, uPN, vMax);
        tessellator.addVertexWithUV(xPN, yMax, zPN, uPN, vMin);
        uNP = (double) icon
            .getInterpolatedU(16.0D - Math.max(this.state.renderMinX, 1.0D - this.state.renderMaxZ) * 16.0D);
        uPN = (double) icon
            .getInterpolatedU(16.0D - Math.min(this.state.renderMaxX, 1.0D - this.state.renderMinZ) * 16.0D);
        tessellator.addVertexWithUV(xPN, yMax, zPN, uPN, vMin);
        tessellator.addVertexWithUV(xPN, yMin, zPN, uPN, vMax);
        tessellator.addVertexWithUV(xNP, yMin, zNP, uNP, vMax);
        tessellator.addVertexWithUV(xNP, yMax, zNP, uNP, vMin);
    }

    private void setupColorMult(int face, float r, float g, float b) {
        Tessellator tessellator = Tessellator.instance;
        float[] norm = normMap[face];
        float scale = this.state.getColorMult(face);
        tessellator.setColorOpaque_F(scale * r, scale * g, scale * b);
        tessellator.startDrawingQuads();
        tessellator.setNormal(norm[0], norm[1], norm[2]);
        this.state.enableAO = false;
    }

    private void setupColorMult(int face, IBlockAccess blockAccess, Block block, int x, int y, int z, float r, float g,
        float b) {
        Tessellator tessellator = Tessellator.instance;
        float[] norm = normMap[face];
        float scale = this.state.getColorMult(face);
        if (blockAccess == null) {
            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_F(r, g, b);
            tessellator.setNormal(norm[0], norm[1], norm[2]);
        } else {
            int brightX = x;
            int brightY = y;
            int brightZ = z;
            switch (face) {
                case 0:
                    brightY = this.state.renderMinY > 0.0D ? y : y - 1;
                    break;
                case 1:
                    brightY = this.state.renderMaxY < 1.0D ? y : y + 1;
                    break;
                case 2:
                    brightZ = this.state.renderMinZ > 0.0D ? z : z - 1;
                    break;
                case 3:
                    brightZ = this.state.renderMaxZ < 1.0D ? z : z + 1;
                    break;
                case 4:
                    brightX = this.state.renderMinX > 0.0D ? x : x - 1;
                    break;
                case 5:
                    brightX = this.state.renderMaxX < 1.0D ? x : x + 1;
            }

            tessellator.setColorOpaque_F(scale * r, scale * g, scale * b);
            tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, brightX, brightY, brightZ));
        }

        this.state.enableAO = false;
    }

    private IIcon getBlockIconFromSideAndMetadata(Block block, int side, int meta) {
        return this.getIconSafe(block.getIcon(side, meta));
    }

    private IIcon getIconSafe(IIcon icon) {
        return (IIcon) (icon == null ? ((TextureMap) Minecraft.getMinecraft()
            .getTextureManager()
            .getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno") : icon);
    }
}
