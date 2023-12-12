package com.jaquadro.minecraft.gardencore.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class RenderHelperLL {

    private static final int TL = 0;
    private static final int BL = 1;
    private static final int BR = 2;
    private static final int TR = 3;
    private static final int MINX = 0;
    private static final int MAXX = 1;
    private static final int MINY = 2;
    private static final int MAXY = 3;
    private static final int MINZ = 4;
    private static final int MAXZ = 5;
    private static final int[][][] xyzuvMap = new int[][][] {
        { { 0, 2, 5, 0, 3 }, { 0, 2, 4, 0, 2 }, { 1, 2, 4, 1, 2 }, { 1, 2, 5, 1, 3 } },
        { { 1, 3, 5, 1, 3 }, { 1, 3, 4, 1, 2 }, { 0, 3, 4, 0, 2 }, { 0, 3, 5, 0, 3 } },
        { { 0, 3, 4, 1, 2 }, { 1, 3, 4, 0, 2 }, { 1, 2, 4, 0, 3 }, { 0, 2, 4, 1, 3 } },
        { { 0, 3, 5, 0, 2 }, { 0, 2, 5, 0, 3 }, { 1, 2, 5, 1, 3 }, { 1, 3, 5, 1, 2 } },
        { { 0, 3, 5, 1, 2 }, { 0, 3, 4, 0, 2 }, { 0, 2, 4, 0, 3 }, { 0, 2, 5, 1, 3 } },
        { { 1, 2, 5, 0, 3 }, { 1, 2, 4, 1, 3 }, { 1, 3, 4, 1, 2 }, { 1, 3, 5, 0, 2 } } };
    private RenderHelperState state;
    private double[] minUDiv = new double[24];
    private double[] maxUDiv = new double[24];
    private double[] minVDiv = new double[24];
    private double[] maxVDiv = new double[24];
    private int[][] brightnessLerp = new int[10][10];
    private double[] uv = new double[4];
    private double[] xyz = new double[6];

    public RenderHelperLL(RenderHelperState state) {
        this.state = state;
    }

    public void drawFace(int face, double x, double y, double z, IIcon icon) {
        boolean flip = this.state.flipTexture;
        switch (face) {
            case 0:
            case 1:
                this.drawFaceY(face, x, y, z, icon);
                break;
            case 2:
            case 3:
                if (this.state.rotateTransform == 2 || this.state.rotateTransform == 1) {
                    this.state.flipTexture = !this.state.flipTexture;
                }

                this.drawFaceZ(face, x, y, z, icon);
                break;
            case 4:
            case 5:
                if (this.state.rotateTransform == 2 || this.state.rotateTransform == 3) {
                    this.state.flipTexture = !this.state.flipTexture;
                }

                this.drawFaceX(face, x, y, z, icon);
        }

        this.state.flipTexture = flip;
    }

    private void drawFaceY(int face, double x, double y, double z, IIcon icon) {
        int rangeX = (int) (Math.ceil(this.state.renderMaxX + (double) this.state.shiftU)
            - Math.floor(this.state.renderMinX + (double) this.state.shiftU));
        int rangeZ = (int) (Math.ceil(this.state.renderMaxZ + (double) this.state.shiftV)
            - Math.floor(this.state.renderMinZ + (double) this.state.shiftV));
        this.setXYZ(x, y, z);
        if (this.state.renderFromInside) {
            this.xyz[0] = z + this.state.renderMaxX;
            this.xyz[1] = z + this.state.renderMinX;
        }

        if (rangeX <= 1 && rangeZ <= 1) {
            this.setUV(
                icon,
                this.state.renderMinX + (double) this.state.shiftU,
                this.state.renderMinZ + (double) this.state.shiftV,
                this.state.renderMaxX + (double) this.state.shiftU,
                this.state.renderMaxZ + (double) this.state.shiftV);
            if (this.state.enableAO) {
                this.renderXYZUVAO(xyzuvMap[face]);
            } else {
                this.renderXYZUV(xyzuvMap[face]);
            }

        } else {
            double uStart = (this.state.renderMinX + (double) this.state.shiftU + (double) rangeX) % 1.0D;
            double uStop = (this.state.renderMaxX + (double) this.state.shiftU + (double) rangeX) % 1.0D;
            double vStart = (this.state.renderMinZ + (double) this.state.shiftV + (double) rangeZ) % 1.0D;
            double vStop = (this.state.renderMaxZ + (double) this.state.shiftV + (double) rangeZ) % 1.0D;
            this.setupUVPoints(uStart, vStart, uStop, vStop, rangeX, rangeZ, icon);
            this.setupAOBrightnessLerp(
                this.state.renderMinX,
                this.state.renderMaxX,
                this.state.renderMinZ,
                this.state.renderMaxZ,
                rangeX,
                rangeZ);
            int rotate = face == 0 ? this.state.uvRotate[0] : this.state.uvRotate[1];

            for (int ix = 0; ix < rangeX; ++ix) {
                this.xyz[1] = this.xyz[0] + this.maxUDiv[ix] - this.minUDiv[ix];
                this.xyz[4] = z + this.state.renderMinZ;

                for (int iz = 0; iz < rangeZ; ++iz) {
                    this.xyz[5] = this.xyz[4] + this.maxVDiv[iz] - this.minVDiv[iz];
                    this.state.brightnessTopLeft = this.brightnessLerp[ix][iz];
                    this.state.brightnessTopRight = this.brightnessLerp[ix + 1][iz];
                    this.state.brightnessBottomLeft = this.brightnessLerp[ix][iz + 1];
                    this.state.brightnessBottomRight = this.brightnessLerp[ix + 1][iz + 1];
                    switch (rotate) {
                        case 1:
                            this.setUV(icon, this.maxVDiv[ix], this.minUDiv[iz], this.minVDiv[ix], this.maxUDiv[iz]);
                            break;
                        case 2:
                            this.setUV(icon, this.maxUDiv[ix], this.maxVDiv[iz], this.minUDiv[ix], this.minVDiv[iz]);
                            break;
                        case 3:
                            this.setUV(icon, this.minVDiv[ix], this.maxUDiv[iz], this.maxVDiv[ix], this.minUDiv[iz]);
                            break;
                        default:
                            this.setUV(icon, this.minUDiv[ix], this.minVDiv[iz], this.maxUDiv[ix], this.maxVDiv[iz]);
                    }

                    this.renderXYZUVAO(xyzuvMap[face]);
                    this.xyz[4] = this.xyz[5];
                }

                this.xyz[0] = this.xyz[1];
            }

        }
    }

    private void drawFaceZ(int face, double x, double y, double z, IIcon icon) {
        int rangeX = (int) (Math.ceil(this.state.renderMaxX + (double) this.state.shiftU)
            - Math.floor(this.state.renderMinX + (double) this.state.shiftU));
        int rangeY = (int) (Math.ceil(this.state.renderMaxY + (double) this.state.shiftV)
            - Math.floor(this.state.renderMinY + (double) this.state.shiftV));
        this.setXYZ(x, y, z);
        if (this.state.renderFromInside) {
            this.xyz[0] = z + this.state.renderMaxX;
            this.xyz[1] = z + this.state.renderMinX;
        }

        if (rangeX <= 1 && rangeY <= 1) {
            if (this.state.flipTexture) {
                this.setUV(
                    icon,
                    this.state.renderMaxX + (double) this.state.shiftU,
                    1.0D - this.state.renderMaxY + (double) this.state.shiftV,
                    this.state.renderMinX + (double) this.state.shiftU,
                    1.0D - this.state.renderMinY + (double) this.state.shiftV);
            } else {
                this.setUV(
                    icon,
                    this.state.renderMinX + (double) this.state.shiftU,
                    1.0D - this.state.renderMaxY + (double) this.state.shiftV,
                    this.state.renderMaxX + (double) this.state.shiftU,
                    1.0D - this.state.renderMinY + (double) this.state.shiftV);
            }

            if (this.state.enableAO) {
                this.renderXYZUVAO(xyzuvMap[face]);
            } else {
                this.renderXYZUV(xyzuvMap[face]);
            }

        } else {
            double uStart = (this.state.renderMinX + (double) this.state.shiftU + (double) rangeX) % 1.0D;
            double uStop = (this.state.renderMaxX + (double) this.state.shiftU + (double) rangeX) % 1.0D;
            double vStart = (this.state.renderMinY + (double) this.state.shiftV + (double) rangeY) % 1.0D;
            double vStop = (this.state.renderMaxY + (double) this.state.shiftV + (double) rangeY) % 1.0D;
            this.setupUVPoints(uStart, vStart, uStop, vStop, rangeX, rangeY, icon);
            this.setupAOBrightnessLerp(
                this.state.renderMinX,
                this.state.renderMaxX,
                this.state.renderMinY,
                this.state.renderMaxY,
                rangeX,
                rangeY);

            for (int ix = 0; ix < rangeX; ++ix) {
                this.xyz[1] = this.xyz[0] + this.maxUDiv[ix] - this.minUDiv[ix];
                this.xyz[2] = y + this.state.renderMinY;

                for (int iy = 0; iy < rangeY; ++iy) {
                    this.xyz[3] = this.xyz[2] + this.maxVDiv[iy] - this.minVDiv[iy];
                    this.state.brightnessTopLeft = this.brightnessLerp[ix][iy];
                    this.state.brightnessTopRight = this.brightnessLerp[ix + 1][iy];
                    this.state.brightnessBottomLeft = this.brightnessLerp[ix][iy + 1];
                    this.state.brightnessBottomRight = this.brightnessLerp[ix + 1][iy + 1];
                    if (this.state.flipTexture) {
                        this.setUV(
                            icon,
                            1.0D - this.minUDiv[ix],
                            1.0D - this.maxVDiv[iy],
                            1.0D - this.maxUDiv[ix],
                            1.0D - this.minVDiv[iy]);
                    } else {
                        this.setUV(
                            icon,
                            this.minUDiv[ix],
                            1.0D - this.maxVDiv[iy],
                            this.maxUDiv[ix],
                            1.0D - this.minVDiv[iy]);
                    }

                    this.renderXYZUVAO(xyzuvMap[face]);
                    this.xyz[2] = this.xyz[3];
                }

                this.xyz[0] = this.xyz[1];
            }

        }
    }

    private void drawFaceX(int face, double x, double y, double z, IIcon icon) {
        int rangeZ = (int) (Math.ceil(this.state.renderMaxZ + (double) this.state.shiftU)
            - Math.floor(this.state.renderMinZ + (double) this.state.shiftU));
        int rangeY = (int) (Math.ceil(this.state.renderMaxY + (double) this.state.shiftV)
            - Math.floor(this.state.renderMinY + (double) this.state.shiftV));
        this.setXYZ(x, y, z);
        if (this.state.renderFromInside) {
            this.xyz[4] = z + this.state.renderMaxZ;
            this.xyz[5] = z + this.state.renderMinZ;
        }

        if (rangeZ <= 1 && rangeY <= 1) {
            if (this.state.flipTexture) {
                this.setUV(
                    icon,
                    this.state.renderMaxZ + (double) this.state.shiftU,
                    1.0D - this.state.renderMaxY + (double) this.state.shiftV,
                    this.state.renderMinZ + (double) this.state.shiftU,
                    1.0D - this.state.renderMinY + (double) this.state.shiftV);
            } else {
                this.setUV(
                    icon,
                    this.state.renderMinZ + (double) this.state.shiftU,
                    1.0D - this.state.renderMaxY + (double) this.state.shiftV,
                    this.state.renderMaxZ + (double) this.state.shiftU,
                    1.0D - this.state.renderMinY + (double) this.state.shiftV);
            }

            if (this.state.enableAO) {
                this.renderXYZUVAO(xyzuvMap[face]);
            } else {
                this.renderXYZUV(xyzuvMap[face]);
            }

        } else {
            double uStart = (this.state.renderMinZ + (double) this.state.shiftU + (double) rangeZ) % 1.0D;
            double uStop = (this.state.renderMaxZ + (double) this.state.shiftU + (double) rangeZ) % 1.0D;
            double vStart = (this.state.renderMinY + (double) this.state.shiftV + (double) rangeY) % 1.0D;
            double vStop = (this.state.renderMaxY + (double) this.state.shiftV + (double) rangeY) % 1.0D;
            this.setupUVPoints(uStart, vStart, uStop, vStop, rangeZ, rangeY, icon);
            this.setupAOBrightnessLerp(
                this.state.renderMinZ,
                this.state.renderMaxZ,
                this.state.renderMinY,
                this.state.renderMaxY,
                rangeZ,
                rangeY);

            for (int iz = 0; iz < rangeZ; ++iz) {
                this.xyz[5] = this.xyz[4] + this.maxUDiv[iz] - this.minUDiv[iz];
                this.xyz[2] = y + this.state.renderMinY;

                for (int iy = 0; iy < rangeY; ++iy) {
                    this.xyz[3] = this.xyz[2] + this.maxVDiv[iy] - this.minVDiv[iy];
                    this.state.brightnessTopLeft = this.brightnessLerp[iz][iy];
                    this.state.brightnessTopRight = this.brightnessLerp[iz + 1][iy];
                    this.state.brightnessBottomLeft = this.brightnessLerp[iz][iy + 1];
                    this.state.brightnessBottomRight = this.brightnessLerp[iz + 1][iy + 1];
                    if (this.state.flipTexture) {
                        this.setUV(
                            icon,
                            1.0D - this.minUDiv[iz],
                            1.0D - this.maxVDiv[iy],
                            1.0D - this.maxUDiv[iz],
                            1.0D - this.minVDiv[iy]);
                    } else {
                        this.setUV(
                            icon,
                            this.minUDiv[iz],
                            1.0D - this.maxVDiv[iy],
                            this.maxUDiv[iz],
                            1.0D - this.minVDiv[iy]);
                    }

                    this.renderXYZUVAO(xyzuvMap[face]);
                    this.xyz[2] = this.xyz[3];
                }

                this.xyz[4] = this.xyz[5];
            }

        }
    }

    public void drawPartialFace(int face, double x, double y, double z, IIcon icon, double uMin, double vMin,
        double uMax, double vMax) {
        this.setXYZ(x, y, z);
        this.setUV(icon, uMin, vMin, uMax, vMax);
        if (this.state.enableAO) {
            this.renderXYZUVAO(xyzuvMap[face]);
        } else {
            this.renderXYZUV(xyzuvMap[face]);
        }

    }

    private void setupUVPoints(double uStart, double vStart, double uStop, double vStop, int rangeU, int rangeV,
        IIcon icon) {
        int i;
        if (rangeU <= 1) {
            this.minUDiv[0] = uStart;
            this.maxUDiv[0] = uStop;
        } else {
            this.minUDiv[0] = uStart;
            this.maxUDiv[0] = 1.0D;

            for (i = 1; i < rangeU - 1; ++i) {
                this.minUDiv[i] = 0.0D;
                this.maxUDiv[i] = 1.0D;
            }

            this.minUDiv[rangeU - 1] = 0.0D;
            this.maxUDiv[rangeU - 1] = uStop;
        }

        if (rangeV <= 1) {
            this.minVDiv[0] = vStart;
            this.maxVDiv[0] = vStop;
        } else {
            this.minVDiv[0] = vStart;
            this.maxVDiv[0] = 1.0D;

            for (i = 1; i < rangeV - 1; ++i) {
                this.minVDiv[i] = 0.0D;
                this.maxVDiv[i] = 1.0D;
            }

            this.minVDiv[rangeV - 1] = 0.0D;
            this.maxVDiv[rangeV - 1] = vStop;
        }

    }

    private void setupAOBrightnessLerp(double left, double right, double top, double bottom, int rangeLR, int rangeTB) {
        double diffLR = right - left;
        double diffTB = bottom - top;
        double posLR = 0.0D;

        for (int lr = 0; lr <= rangeLR; ++lr) {
            float lerpLR = (float) (posLR / diffLR);
            int brightTop = RenderHelperAO
                .mixAOBrightness(this.state.brightnessTopLeft, this.state.brightnessTopRight, 1.0F - lerpLR, lerpLR);
            int brightBottom = RenderHelperAO.mixAOBrightness(
                this.state.brightnessBottomLeft,
                this.state.brightnessBottomRight,
                1.0F - lerpLR,
                lerpLR);
            double posTB = 0.0D;

            for (int tb = 0; tb <= rangeTB; ++tb) {
                float lerpTB = (float) (posTB / diffTB);
                this.brightnessLerp[lr][tb] = RenderHelperAO
                    .mixAOBrightness(brightTop, brightBottom, 1.0F - lerpTB, lerpTB);
                if (tb < rangeTB) {
                    posTB += this.maxVDiv[tb] - this.minVDiv[tb];
                }
            }

            if (lr < rangeLR) {
                posLR += this.maxUDiv[lr] - this.minUDiv[lr];
            }
        }

    }

    private void setUV(IIcon icon, double uMin, double vMin, double uMax, double vMax) {
        this.uv[0] = (double) icon.getInterpolatedU(uMin * 16.0D);
        this.uv[1] = (double) icon.getInterpolatedU(uMax * 16.0D);
        this.uv[2] = (double) icon.getInterpolatedV(vMin * 16.0D);
        this.uv[3] = (double) icon.getInterpolatedV(vMax * 16.0D);
    }

    private void setUV(double uMin, double vMin, double uMax, double vMax) {
        this.uv[0] = uMin;
        this.uv[1] = uMax;
        this.uv[2] = vMin;
        this.uv[3] = vMax;
    }

    private void setXYZ(double x, double y, double z) {
        this.xyz[0] = x + this.state.renderOffsetX + this.state.renderMinX;
        this.xyz[1] = x + this.state.renderOffsetX + this.state.renderMaxX;
        this.xyz[2] = y + this.state.renderOffsetY + this.state.renderMinY;
        this.xyz[3] = y + this.state.renderOffsetY + this.state.renderMaxY;
        this.xyz[4] = z + this.state.renderOffsetZ + this.state.renderMinZ;
        this.xyz[5] = z + this.state.renderOffsetZ + this.state.renderMaxZ;
    }

    private void renderXYZUV(int[][] index) {
        Tessellator tessellator = Tessellator.instance;
        int[] tl = index[0];
        int[] bl = index[1];
        int[] br = index[2];
        int[] tr = index[3];
        tessellator.addVertexWithUV(this.xyz[tl[0]], this.xyz[tl[1]], this.xyz[tl[2]], this.uv[tl[3]], this.uv[tl[4]]);
        tessellator.addVertexWithUV(this.xyz[bl[0]], this.xyz[bl[1]], this.xyz[bl[2]], this.uv[bl[3]], this.uv[bl[4]]);
        tessellator.addVertexWithUV(this.xyz[br[0]], this.xyz[br[1]], this.xyz[br[2]], this.uv[br[3]], this.uv[br[4]]);
        tessellator.addVertexWithUV(this.xyz[tr[0]], this.xyz[tr[1]], this.xyz[tr[2]], this.uv[tr[3]], this.uv[tr[4]]);
    }

    private void renderXYZUVAO(int[][] index) {
        Tessellator tessellator = Tessellator.instance;
        int[] tl = index[0];
        int[] bl = index[1];
        int[] br = index[2];
        int[] tr = index[3];
        tessellator
            .setColorOpaque_F(this.state.colorTopLeft[0], this.state.colorTopLeft[1], this.state.colorTopLeft[2]);
        tessellator.setBrightness(this.state.brightnessTopLeft);
        tessellator.addVertexWithUV(this.xyz[tl[0]], this.xyz[tl[1]], this.xyz[tl[2]], this.uv[tl[3]], this.uv[tl[4]]);
        tessellator.setColorOpaque_F(
            this.state.colorBottomLeft[0],
            this.state.colorBottomLeft[1],
            this.state.colorBottomLeft[2]);
        tessellator.setBrightness(this.state.brightnessBottomLeft);
        tessellator.addVertexWithUV(this.xyz[bl[0]], this.xyz[bl[1]], this.xyz[bl[2]], this.uv[bl[3]], this.uv[bl[4]]);
        tessellator.setColorOpaque_F(
            this.state.colorBottomRight[0],
            this.state.colorBottomRight[1],
            this.state.colorBottomRight[2]);
        tessellator.setBrightness(this.state.brightnessBottomRight);
        tessellator.addVertexWithUV(this.xyz[br[0]], this.xyz[br[1]], this.xyz[br[2]], this.uv[br[3]], this.uv[br[4]]);
        tessellator
            .setColorOpaque_F(this.state.colorTopRight[0], this.state.colorTopRight[1], this.state.colorTopRight[2]);
        tessellator.setBrightness(this.state.brightnessTopRight);
        tessellator.addVertexWithUV(this.xyz[tr[0]], this.xyz[tr[1]], this.xyz[tr[2]], this.uv[tr[3]], this.uv[tr[4]]);
    }
}
