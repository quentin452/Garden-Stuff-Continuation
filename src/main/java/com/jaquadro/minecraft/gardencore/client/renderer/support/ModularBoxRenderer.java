package com.jaquadro.minecraft.gardencore.client.renderer.support;

import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class ModularBoxRenderer {
   public static final int CONNECT_YNEG = 1;
   public static final int CONNECT_YPOS = 2;
   public static final int CONNECT_ZNEG = 4;
   public static final int CONNECT_ZPOS = 8;
   public static final int CONNECT_XNEG = 16;
   public static final int CONNECT_XPOS = 32;
   public static final int CONNECT_YNEG_ZNEG = 64;
   public static final int CONNECT_YNEG_ZPOS = 128;
   public static final int CONNECT_YNEG_XNEG = 256;
   public static final int CONNECT_YNEG_XPOS = 512;
   public static final int CONNECT_YPOS_ZNEG = 1024;
   public static final int CONNECT_YPOS_ZPOS = 2048;
   public static final int CONNECT_YPOS_XNEG = 4096;
   public static final int CONNECT_YPOS_XPOS = 8192;
   public static final int CONNECT_ZNEG_XNEG = 16384;
   public static final int CONNECT_ZNEG_XPOS = 32768;
   public static final int CONNECT_ZPOS_XNEG = 65536;
   public static final int CONNECT_ZPOS_XPOS = 131072;
   public static final int CUT_YNEG = 1;
   public static final int CUT_YPOS = 2;
   public static final int CUT_ZNEG = 4;
   public static final int CUT_ZPOS = 8;
   public static final int CUT_XNEG = 16;
   public static final int CUT_XPOS = 32;
   public static final int CUT_ALL = 63;
   public static final int FACE_YNEG = 0;
   public static final int FACE_YPOS = 1;
   public static final int FACE_ZNEG = 2;
   public static final int FACE_ZPOS = 3;
   public static final int FACE_XNEG = 4;
   public static final int FACE_XPOS = 5;
   private static final int TEST_YNEG_ZNEG = 5;
   private static final int TEST_YNEG_ZPOS = 9;
   private static final int TEST_YNEG_XNEG = 17;
   private static final int TEST_YNEG_XPOS = 33;
   private static final int TEST_YPOS_ZNEG = 6;
   private static final int TEST_YPOS_ZPOS = 10;
   private static final int TEST_YPOS_XNEG = 18;
   private static final int TEST_YPOS_XPOS = 34;
   private static final int TEST_ZNEG_XNEG = 20;
   private static final int TEST_ZNEG_XPOS = 36;
   private static final int TEST_ZPOS_XNEG = 24;
   private static final int TEST_ZPOS_XPOS = 40;
   private static final int TEST_YNEG_ZNEG_XNEG = 21;
   private static final int TEST_YNEG_ZNEG_XPOS = 37;
   private static final int TEST_YNEG_ZPOS_XNEG = 25;
   private static final int TEST_YNEG_ZPOS_XPOS = 41;
   private static final int TEST_YPOS_ZNEG_XNEG = 22;
   private static final int TEST_YPOS_ZNEG_XPOS = 38;
   private static final int TEST_YPOS_ZPOS_XNEG = 26;
   private static final int TEST_YPOS_ZPOS_XPOS = 42;
   private static final int PLANE_YNEG = 961;
   private static final int PLANE_YPOS = 15362;
   public static final float[] COLOR_WHITE = new float[]{1.0F, 1.0F, 1.0F};
   private double unit = 0.0625D;
   private float[][] exteriorColor = new float[6][3];
   private float[][] interiorColor = new float[6][3];
   private float[][] cutColor = new float[6][3];
   private IIcon[] exteriorIcon = new IIcon[6];
   private IIcon[] interiorIcon = new IIcon[6];
   private IIcon[] cutIcon = new IIcon[6];
   public boolean flipOpposite;

   private void copyFrom(float[] target, float[] source) {
      target[0] = source[0];
      target[1] = source[1];
      target[2] = source[2];
   }

   private void copyFrom(float[] target, float r, float g, float b) {
      target[0] = r;
      target[1] = g;
      target[2] = b;
   }

   public void setColor(float[] color) {
      this.setExteriorColor(color);
      this.setInteriorColor(color);
      this.setCutColor(color);
   }

   public void setScaledColor(float[] color, float scale) {
      this.setScaledExteriorColor(color, scale);
      this.setScaledInteriorColor(color, scale);
      this.setScaledCutColor(color, scale);
   }

   public void setExteriorColor(float[] color) {
      for(int i = 0; i < 6; ++i) {
         this.copyFrom(this.exteriorColor[i], color);
      }

   }

   public void setExteriorColor(float[] color, int side) {
      this.copyFrom(this.exteriorColor[side], color);
   }

   public void setScaledExteriorColor(float[] color, float scale) {
      for(int i = 0; i < 6; ++i) {
         this.copyFrom(this.exteriorColor[i], color[0] * scale, color[1] * scale, color[2] * scale);
      }

   }

   public void setScaledExteriorColor(float[] color, float scale, int side) {
      this.copyFrom(this.exteriorColor[side], color[0] * scale, color[1] * scale, color[2] * scale);
   }

   public void setInteriorColor(float[] color) {
      for(int i = 0; i < 6; ++i) {
         this.copyFrom(this.interiorColor[i], color);
      }

   }

   public void setInteriorColor(float[] color, int side) {
      side = side % 2 == 0 ? side + 1 : side - 1;
      this.copyFrom(this.interiorColor[side], color);
   }

   public void setScaledInteriorColor(float[] color, float scale) {
      for(int i = 0; i < 6; ++i) {
         this.copyFrom(this.interiorColor[i], color[0] * scale, color[1] * scale, color[2] * scale);
      }

   }

   public void setCutColor(float[] color) {
      for(int i = 0; i < 6; ++i) {
         this.copyFrom(this.cutColor[i], color);
      }

   }

   public void setCutColor(float[] color, int side) {
      this.copyFrom(this.cutColor[side], color);
   }

   public void setScaledCutColor(float[] color, float scale) {
      for(int i = 0; i < 6; ++i) {
         this.copyFrom(this.cutColor[i], color[0] * scale, color[1] * scale, color[2] * scale);
      }

   }

   public void setIcon(IIcon icon) {
      this.setExteriorIcon(icon);
      this.setInteriorIcon(icon);
      this.setCutIcon(icon);
   }

   public void setIcon(IIcon icon, int side) {
      this.setExteriorIcon(icon, side);
      this.setInteriorIcon(icon, side);
      this.setCutIcon(icon, side);
   }

   public void setExteriorIcon(IIcon icon) {
      for(int i = 0; i < 6; ++i) {
         this.exteriorIcon[i] = icon;
      }

   }

   public void setExteriorIcon(IIcon icon, int side) {
      this.exteriorIcon[side] = icon;
   }

   public void setInteriorIcon(IIcon icon) {
      for(int i = 0; i < 6; ++i) {
         this.interiorIcon[i] = icon;
      }

   }

   public void setInteriorIcon(IIcon icon, int side) {
      side = side % 2 == 0 ? side + 1 : side - 1;
      this.interiorIcon[side] = icon;
   }

   public void setCutIcon(IIcon icon) {
      for(int i = 0; i < 6; ++i) {
         this.cutIcon[i] = icon;
      }

   }

   public void setCutIcon(IIcon icon, int side) {
      this.cutIcon[side] = icon;
   }

   public void setUnit(double unit) {
      this.unit = unit;
   }

   public void renderOctant(IBlockAccess blockAccess, Block block, double x, double y, double z, int connectedFlags, int cutFlags) {
      double xBase = Math.floor(x);
      double yBase = Math.floor(y);
      double zBase = Math.floor(z);
      double xNeg = x - xBase;
      double yNeg = y - yBase;
      double zNeg = z - zBase;
      double xPos = xNeg + 0.5D;
      double yPos = yNeg + 0.5D;
      double zPos = zNeg + 0.5D;
      this.renderExterior(blockAccess, block, xBase, yBase, zBase, xNeg, yNeg, zNeg, xPos, yPos, zPos, connectedFlags, cutFlags);
      this.renderInterior(blockAccess, block, xBase, yBase, zBase, xNeg, yNeg, zNeg, xPos, yPos, zPos, connectedFlags, cutFlags);
   }

   public void renderBox(IBlockAccess blockAccess, Block block, double x, double y, double z, double xNeg, double yNeg, double zNeg, double xPos, double yPos, double zPos, int connectedFlags, int cutFlags) {
      this.renderExterior(blockAccess, block, x, y, z, xNeg, yNeg, zNeg, xPos, yPos, zPos, connectedFlags, cutFlags);
      this.renderInterior(blockAccess, block, x, y, z, xNeg, yNeg, zNeg, xPos, yPos, zPos, connectedFlags, cutFlags);
   }

   public void renderSolidBox(IBlockAccess blockAccess, Block block, double x, double y, double z, double xNeg, double yNeg, double zNeg, double xPos, double yPos, double zPos) {
      this.renderExterior(blockAccess, block, x, y, z, xNeg, yNeg, zNeg, xPos, yPos, zPos, 0, 0);
   }

   public void renderExterior(IBlockAccess blockAccess, Block block, double x, double y, double z, double xNeg, double yNeg, double zNeg, double xPos, double yPos, double zPos, int connectedFlags, int cutFlags) {
      RenderHelper renderHelper = RenderHelper.instance;
      if ((cutFlags & 1) != 0) {
         connectedFlags |= 1;
      }

      if ((cutFlags & 2) != 0) {
         connectedFlags |= 2;
      }

      if ((cutFlags & 4) != 0) {
         connectedFlags |= 4;
      }

      if ((cutFlags & 8) != 0) {
         connectedFlags |= 8;
      }

      if ((cutFlags & 16) != 0) {
         connectedFlags |= 16;
      }

      if ((cutFlags & 32) != 0) {
         connectedFlags |= 32;
      }

      renderHelper.setRenderBounds(xNeg, yNeg, zNeg, xPos, yPos, zPos);
      if ((connectedFlags & 1) == 0) {
         this.renderExteriorFace(0, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 2) == 0) {
         this.renderExteriorFace(1, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 4) == 0) {
         this.renderExteriorFace(2, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 8) == 0) {
         this.renderExteriorFace(3, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 16) == 0) {
         this.renderExteriorFace(4, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 32) == 0) {
         this.renderExteriorFace(5, blockAccess, block, x, y, z);
      }

      if (this.unit != 0.0D) {
         if ((cutFlags & 5) != 0) {
            renderHelper.setRenderBounds(xNeg + this.unit, yNeg, zNeg, xPos - this.unit, yNeg + this.unit, zNeg + this.unit);
            if ((cutFlags & 1) != 0 && (connectedFlags & 4) == 0) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 4) != 0 && (connectedFlags & 1) == 0) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 9) != 0) {
            renderHelper.setRenderBounds(xNeg + this.unit, yNeg, zPos - this.unit, xPos - this.unit, yNeg + this.unit, zPos);
            if ((cutFlags & 1) != 0 && (connectedFlags & 8) == 0) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 8) != 0 && (connectedFlags & 1) == 0) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 17) != 0) {
            renderHelper.setRenderBounds(xNeg, yNeg, zNeg + this.unit, xNeg + this.unit, yNeg + this.unit, zPos - this.unit);
            if ((cutFlags & 1) != 0 && (connectedFlags & 16) == 0) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags & 1) == 0) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 33) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg, zNeg + this.unit, xPos, yNeg + this.unit, zPos - this.unit);
            if ((cutFlags & 1) != 0 && (connectedFlags & 32) == 0) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags & 1) == 0) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 6) != 0) {
            renderHelper.setRenderBounds(xNeg + this.unit, yPos - this.unit, zNeg, xPos - this.unit, yPos, zNeg + this.unit);
            if ((cutFlags & 2) != 0 && (connectedFlags & 4) == 0) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 4) != 0 && (connectedFlags & 2) == 0) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 10) != 0) {
            renderHelper.setRenderBounds(xNeg + this.unit, yPos - this.unit, zPos - this.unit, xPos - this.unit, yPos, zPos);
            if ((cutFlags & 2) != 0 && (connectedFlags & 8) == 0) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 8) != 0 && (connectedFlags & 2) == 0) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 18) != 0) {
            renderHelper.setRenderBounds(xNeg, yPos - this.unit, zNeg + this.unit, xNeg + this.unit, yPos, zPos - this.unit);
            if ((cutFlags & 2) != 0 && (connectedFlags & 16) == 0) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags & 2) == 0) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 34) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yPos - this.unit, zNeg + this.unit, xPos, yPos, zPos - this.unit);
            if ((cutFlags & 2) != 0 && (connectedFlags & 32) == 0) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags & 2) == 0) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 20) != 0) {
            renderHelper.setRenderBounds(xNeg, yNeg + this.unit, zNeg, xNeg + this.unit, yPos - this.unit, zNeg + this.unit);
            if ((cutFlags & 4) != 0 && (connectedFlags & 16) == 0) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags & 4) == 0) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 36) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg + this.unit, zNeg, xPos, yPos - this.unit, zNeg + this.unit);
            if ((cutFlags & 4) != 0 && (connectedFlags & 32) == 0) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags & 4) == 0) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 24) != 0) {
            renderHelper.setRenderBounds(xNeg, yNeg + this.unit, zPos - this.unit, xNeg + this.unit, yPos - this.unit, zPos);
            if ((cutFlags & 8) != 0 && (connectedFlags & 16) == 0) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags & 8) == 0) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 40) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg + this.unit, zPos - this.unit, xPos, yPos - this.unit, zPos);
            if ((cutFlags & 8) != 0 && (connectedFlags & 32) == 0) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags & 8) == 0) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 21) != 0) {
            renderHelper.setRenderBounds(xNeg, yNeg, zNeg, xNeg + this.unit, yNeg + this.unit, zNeg + this.unit);
            if ((cutFlags & 1) != 0 && (connectedFlags | 4 | 16 | 16384) != connectedFlags) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 4) != 0 && (connectedFlags | 1 | 16 | 256) != connectedFlags) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags | 1 | 4 | 64) != connectedFlags) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 37) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg, zNeg, xPos, yNeg + this.unit, zNeg + this.unit);
            if ((cutFlags & 1) != 0 && (connectedFlags | 4 | 32 | '耀') != connectedFlags) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 4) != 0 && (connectedFlags | 1 | 32 | 512) != connectedFlags) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags | 1 | 4 | 64) != connectedFlags) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 25) != 0) {
            renderHelper.setRenderBounds(xNeg, yNeg, zPos - this.unit, xNeg + this.unit, yNeg + this.unit, zPos);
            if ((cutFlags & 1) != 0 && (connectedFlags | 8 | 16 | 65536) != connectedFlags) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 8) != 0 && (connectedFlags | 1 | 16 | 256) != connectedFlags) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags | 1 | 8 | 128) != connectedFlags) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 41) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg, zPos - this.unit, xPos, yNeg + this.unit, zPos);
            if ((cutFlags & 1) != 0 && (connectedFlags | 8 | 32 | 131072) != connectedFlags) {
               this.renderCutFace(0, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 8) != 0 && (connectedFlags | 1 | 32 | 512) != connectedFlags) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags | 1 | 8 | 128) != connectedFlags) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 22) != 0) {
            renderHelper.setRenderBounds(xNeg, yPos - this.unit, zNeg, xNeg + this.unit, yPos, zNeg + this.unit);
            if ((cutFlags & 2) != 0 && (connectedFlags | 4 | 16 | 16384) != connectedFlags) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 4) != 0 && (connectedFlags | 2 | 16 | 4096) != connectedFlags) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags | 2 | 4 | 1024) != connectedFlags) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 38) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yPos - this.unit, zNeg, xPos, yPos, zNeg + this.unit);
            if ((cutFlags & 2) != 0 && (connectedFlags | 4 | 32 | '耀') != connectedFlags) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 4) != 0 && (connectedFlags | 2 | 32 | 8192) != connectedFlags) {
               this.renderCutFace(2, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags | 2 | 4 | 1024) != connectedFlags) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 26) != 0) {
            renderHelper.setRenderBounds(xNeg, yPos - this.unit, zPos - this.unit, xNeg + this.unit, yPos, zPos);
            if ((cutFlags & 2) != 0 && (connectedFlags | 8 | 16 | 65536) != connectedFlags) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 8) != 0 && (connectedFlags | 2 | 16 | 4096) != connectedFlags) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 16) != 0 && (connectedFlags | 2 | 8 | 2048) != connectedFlags) {
               this.renderCutFace(4, blockAccess, block, x, y, z);
            }
         }

         if ((cutFlags & 42) != 0) {
            renderHelper.setRenderBounds(xPos - this.unit, yPos - this.unit, zPos - this.unit, xPos, yPos, zPos);
            if ((cutFlags & 2) != 0 && (connectedFlags | 8 | 32 | 131072) != connectedFlags) {
               this.renderCutFace(1, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 8) != 0 && (connectedFlags | 2 | 32 | 8192) != connectedFlags) {
               this.renderCutFace(3, blockAccess, block, x, y, z);
            }

            if ((cutFlags & 32) != 0 && (connectedFlags | 2 | 8 | 2048) != connectedFlags) {
               this.renderCutFace(5, blockAccess, block, x, y, z);
            }
         }

      }
   }

   public void renderInterior(IBlockAccess blockAccess, Block block, double x, double y, double z, double xNeg, double yNeg, double zNeg, double xPos, double yPos, double zPos, int connectedFlags, int cutFlags) {
      RenderHelper renderHelper = RenderHelper.instance;
      if ((cutFlags & 1) != 0) {
         connectedFlags |= 961;
      }

      if ((cutFlags & 2) != 0) {
         connectedFlags |= 15362;
      }

      if ((cutFlags & 4) != 0) {
         connectedFlags |= 4;
      }

      if ((cutFlags & 8) != 0) {
         connectedFlags |= 8;
      }

      if ((cutFlags & 16) != 0) {
         connectedFlags |= 16;
      }

      if ((cutFlags & 32) != 0) {
         connectedFlags |= 32;
      }

      renderHelper.setRenderBounds(xNeg + this.unit, yNeg + this.unit, zNeg + this.unit, xPos - this.unit, yPos - this.unit, zPos - this.unit);
      if ((connectedFlags & 1) == 0) {
         renderHelper.setRenderBounds(xNeg + this.unit, yNeg, zNeg + this.unit, xPos - this.unit, yNeg + this.unit, zPos - this.unit);
         this.renderInteriorFace(1, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 2) == 0) {
         renderHelper.setRenderBounds(xNeg + this.unit, yPos - this.unit, zNeg + this.unit, xPos - this.unit, yPos, zPos - this.unit);
         this.renderInteriorFace(0, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 4) == 0) {
         renderHelper.setRenderBounds(xNeg + this.unit, yNeg + this.unit, zNeg, xPos - this.unit, yPos - this.unit, zNeg + this.unit);
         this.renderInteriorFace(3, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 8) == 0) {
         renderHelper.setRenderBounds(xNeg + this.unit, yNeg + this.unit, zPos - this.unit, xPos - this.unit, yPos - this.unit, zPos);
         this.renderInteriorFace(2, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 16) == 0) {
         renderHelper.setRenderBounds(xNeg, yNeg + this.unit, zNeg + this.unit, xNeg + this.unit, yPos - this.unit, zPos - this.unit);
         this.renderInteriorFace(5, blockAccess, block, x, y, z);
      }

      if ((connectedFlags & 32) == 0) {
         renderHelper.setRenderBounds(xPos - this.unit, yNeg + this.unit, zNeg + this.unit, xPos, yPos - this.unit, zPos - this.unit);
         this.renderInteriorFace(4, blockAccess, block, x, y, z);
      }

      if (this.unit != 0.0D) {
         if ((connectedFlags & 5) != 0 && (connectedFlags | 1 | 4 | 64) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg + this.unit, yNeg, zNeg, xPos - this.unit, yNeg + this.unit, zNeg + this.unit);
            if ((connectedFlags & 1) != 0) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 4) != 0) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 9) != 0 && (connectedFlags | 1 | 8 | 128) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg + this.unit, yNeg, zPos - this.unit, xPos - this.unit, yNeg + this.unit, zPos);
            if ((connectedFlags & 1) != 0) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 8) != 0) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 17) != 0 && (connectedFlags | 1 | 16 | 256) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yNeg, zNeg + this.unit, xNeg + this.unit, yNeg + this.unit, zPos - this.unit);
            if ((connectedFlags & 1) != 0) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 16) != 0) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 33) != 0 && (connectedFlags | 1 | 32 | 512) != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg, zNeg + this.unit, xPos, yNeg + this.unit, zPos - this.unit);
            if ((connectedFlags & 1) != 0) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 32) != 0) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 6) != 0 && (connectedFlags | 2 | 4 | 1024) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg + this.unit, yPos - this.unit, zNeg, xPos - this.unit, yPos, zNeg + this.unit);
            if ((connectedFlags & 2) != 0) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 4) != 0) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 10) != 0 && (connectedFlags | 2 | 8 | 2048) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg + this.unit, yPos - this.unit, zPos - this.unit, xPos - this.unit, yPos, zPos);
            if ((connectedFlags & 2) != 0) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 8) != 0) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 18) != 0 && (connectedFlags | 2 | 16 | 4096) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yPos - this.unit, zNeg + this.unit, xNeg + this.unit, yPos, zPos - this.unit);
            if ((connectedFlags & 2) != 0) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 16) != 0) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 34) != 0 && (connectedFlags | 2 | 32 | 8192) != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yPos - this.unit, zNeg + this.unit, xPos, yPos, zPos - this.unit);
            if ((connectedFlags & 2) != 0) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 32) != 0) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 20) != 0 && (connectedFlags | 4 | 16 | 16384) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yNeg + this.unit, zNeg, xNeg + this.unit, yPos - this.unit, zNeg + this.unit);
            if ((connectedFlags & 4) != 0) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 16) != 0) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 36) != 0 && (connectedFlags | 4 | 32 | '耀') != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg + this.unit, zNeg, xPos, yPos - this.unit, zNeg + this.unit);
            if ((connectedFlags & 4) != 0) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 32) != 0) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 24) != 0 && (connectedFlags | 8 | 16 | 65536) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yNeg + this.unit, zPos - this.unit, xNeg + this.unit, yPos - this.unit, zPos);
            if ((connectedFlags & 8) != 0) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 16) != 0) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 40) != 0 && (connectedFlags | 8 | 32 | 131072) != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg + this.unit, zPos - this.unit, xPos, yPos - this.unit, zPos);
            if ((connectedFlags & 8) != 0) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags & 32) != 0) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 21) != 0 && (connectedFlags | 1 | 4 | 16 | 64 | 256 | 16384) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yNeg, zNeg, xNeg + this.unit, yNeg + this.unit, zNeg + this.unit);
            if ((connectedFlags | 1 | 4) == connectedFlags) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 1 | 16) == connectedFlags) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 4 | 16) == connectedFlags) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 37) != 0 && (connectedFlags | 1 | 4 | 32 | 64 | 512 | '耀') != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg, zNeg, xPos, yNeg + this.unit, zNeg + this.unit);
            if ((connectedFlags | 1 | 4) == connectedFlags) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 1 | 32) == connectedFlags) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 4 | 32) == connectedFlags) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 25) != 0 && (connectedFlags | 1 | 8 | 16 | 128 | 256 | 65536) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yNeg, zPos - this.unit, xNeg + this.unit, yNeg + this.unit, zPos);
            if ((connectedFlags | 1 | 8) == connectedFlags) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 1 | 16) == connectedFlags) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 8 | 16) == connectedFlags) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 41) != 0 && (connectedFlags | 1 | 8 | 32 | 128 | 512 | 131072) != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yNeg, zPos - this.unit, xPos, yNeg + this.unit, zPos);
            if ((connectedFlags | 1 | 8) == connectedFlags) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 1 | 32) == connectedFlags) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 8 | 32) == connectedFlags) {
               this.renderInteriorFace(1, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 22) != 0 && (connectedFlags | 2 | 4 | 16 | 1024 | 4096 | 16384) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yPos - this.unit, zNeg, xNeg + this.unit, yPos, zNeg + this.unit);
            if ((connectedFlags | 2 | 4) == connectedFlags) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 2 | 16) == connectedFlags) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 4 | 16) == connectedFlags) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 38) != 0 && (connectedFlags | 2 | 4 | 32 | 1024 | 8192 | '耀') != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yPos - this.unit, zNeg, xPos, yPos, zNeg + this.unit);
            if ((connectedFlags | 2 | 4) == connectedFlags) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 2 | 32) == connectedFlags) {
               this.renderInteriorFace(3, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 4 | 32) == connectedFlags) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 26) != 0 && (connectedFlags | 2 | 8 | 16 | 2048 | 4096 | 65536) != connectedFlags) {
            renderHelper.setRenderBounds(xNeg, yPos - this.unit, zPos - this.unit, xNeg + this.unit, yPos, zPos);
            if ((connectedFlags | 2 | 8) == connectedFlags) {
               this.renderInteriorFace(5, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 2 | 16) == connectedFlags) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 8 | 16) == connectedFlags) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

         if ((connectedFlags & 42) != 0 && (connectedFlags | 2 | 8 | 32 | 2048 | 8192 | 131072) != connectedFlags) {
            renderHelper.setRenderBounds(xPos - this.unit, yPos - this.unit, zPos - this.unit, xPos, yPos, zPos);
            if ((connectedFlags | 2 | 8) == connectedFlags) {
               this.renderInteriorFace(4, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 2 | 32) == connectedFlags) {
               this.renderInteriorFace(2, blockAccess, block, x, y, z);
            }

            if ((connectedFlags | 8 | 32) == connectedFlags) {
               this.renderInteriorFace(0, blockAccess, block, x, y, z);
            }
         }

      }
   }

   private void renderFace(int face, IBlockAccess blockAccess, Block block, double x, double y, double z, IIcon icon, float r, float g, float b) {
      RenderHelper renderHelper = RenderHelper.instance;
      switch(face) {
      case 0:
      case 1:
      case 3:
      case 4:
         renderHelper.renderFace(face, blockAccess, block, (int)x, (int)y, (int)z, icon, r, g, b);
         break;
      case 2:
      case 5:
         renderHelper.state.flipTexture = this.flipOpposite;
         renderHelper.renderFace(face, blockAccess, block, (int)x, (int)y, (int)z, icon, r, g, b);
         renderHelper.state.flipTexture = false;
      }

   }

   private void renderExteriorFace(int face, IBlockAccess blockAccess, Block block, double x, double y, double z) {
      this.renderFace(face, blockAccess, block, x, y, z, this.exteriorIcon[face], this.exteriorColor[face][0], this.exteriorColor[face][1], this.exteriorColor[face][2]);
   }

   private void renderInteriorFace(int face, IBlockAccess blockAccess, Block block, double x, double y, double z) {
      IIcon icon = this.interiorIcon[face];
      float r = this.interiorColor[face][0];
      float g = this.interiorColor[face][1];
      float b = this.interiorColor[face][2];
      this.renderFace(face, blockAccess, block, x, y, z, icon, r, g, b);
   }

   private void renderCutFace(int face, IBlockAccess blockAccess, Block block, double x, double y, double z) {
      this.renderFace(face, blockAccess, block, x, y, z, this.cutIcon[face], this.cutColor[face][0], this.cutColor[face][1], this.cutColor[face][2]);
   }
}
