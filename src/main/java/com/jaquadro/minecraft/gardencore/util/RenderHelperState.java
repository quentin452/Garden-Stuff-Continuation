package com.jaquadro.minecraft.gardencore.util;

public class RenderHelperState {
   public static final int ROTATE0 = 0;
   public static final int ROTATE90 = 1;
   public static final int ROTATE180 = 2;
   public static final int ROTATE270 = 3;
   public static final int[][] ROTATION_BY_FACE_FACE = new int[][]{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 2, 3, 1}, {0, 0, 2, 0, 1, 3}, {0, 0, 1, 3, 0, 2}, {0, 0, 3, 1, 2, 0}};
   public static final int[][] FACE_BY_FACE_ROTATION = new int[][]{{0, 0, 0, 0}, {1, 1, 1, 1}, {2, 5, 3, 4}, {3, 4, 2, 5}, {4, 2, 5, 3}, {5, 3, 4, 2}};
   public double renderMinX;
   public double renderMinY;
   public double renderMinZ;
   public double renderMaxX;
   public double renderMaxY;
   public double renderMaxZ;
   public double renderOffsetX;
   public double renderOffsetY;
   public double renderOffsetZ;
   public boolean flipTexture;
   public boolean renderFromInside;
   public boolean enableAO;
   public int rotateTransform;
   public float shiftU;
   public float shiftV;
   public final int[] uvRotate = new int[6];
   public float colorMultYNeg;
   public float colorMultYPos;
   public float colorMultZNeg;
   public float colorMultZPos;
   public float colorMultXNeg;
   public float colorMultXPos;
   public int brightnessTopLeft;
   public int brightnessBottomLeft;
   public int brightnessBottomRight;
   public int brightnessTopRight;
   public final float[] colorTopLeft = new float[3];
   public final float[] colorBottomLeft = new float[3];
   public final float[] colorBottomRight = new float[3];
   public final float[] colorTopRight = new float[3];
   private final double[] scratchIn = new double[3];
   private final double[] scratchOut = new double[3];

   public RenderHelperState() {
      this.resetColorMult();
   }

   public void setRenderBounds(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
      this.renderMinX = xMin;
      this.renderMinY = yMin;
      this.renderMinZ = zMin;
      this.renderMaxX = xMax;
      this.renderMaxY = yMax;
      this.renderMaxZ = zMax;
      if (this.rotateTransform != 0) {
         this.transformRenderBound(this.rotateTransform);
      }

   }

   public void setRenderOffset(double xOffset, double yOffset, double zOffset) {
      this.renderOffsetX = xOffset;
      this.renderOffsetY = yOffset;
      this.renderOffsetZ = zOffset;
      if (this.rotateTransform != 0) {
         this.transformRenderOffset(this.rotateTransform);
      }

   }

   public void clearRenderOffset() {
      this.renderOffsetX = 0.0D;
      this.renderOffsetY = 0.0D;
      this.renderOffsetZ = 0.0D;
   }

   public void setColorMult(float yPos, float z, float x, float yNeg) {
      this.colorMultYNeg = yNeg;
      this.colorMultYPos = yPos;
      this.colorMultZNeg = z;
      this.colorMultZPos = z;
      this.colorMultXNeg = x;
      this.colorMultXPos = x;
   }

   public void resetColorMult() {
      this.colorMultYNeg = 0.5F;
      this.colorMultYPos = 1.0F;
      this.colorMultZNeg = 0.8F;
      this.colorMultZPos = 0.8F;
      this.colorMultXNeg = 0.6F;
      this.colorMultXPos = 0.6F;
   }

   public float getColorMult(int side) {
      switch(side) {
      case 0:
         return this.colorMultYNeg;
      case 1:
         return this.colorMultYPos;
      case 2:
         return this.colorMultZNeg;
      case 3:
         return this.colorMultZPos;
      case 4:
         return this.colorMultXNeg;
      case 5:
         return this.colorMultXPos;
      default:
         return 0.0F;
      }
   }

   public void setTextureOffset(float u, float v) {
      this.shiftU = u;
      this.shiftV = v;
   }

   public void resetTextureOffset() {
      this.shiftU = 0.0F;
      this.shiftV = 0.0F;
   }

   public void setUVRotation(int face, int rotation) {
      this.uvRotate[face] = rotation;
   }

   public void clearUVRotation(int face) {
      this.uvRotate[face] = 0;
   }

   public void setColor(float r, float g, float b) {
      this.colorTopLeft[0] = r;
      this.colorTopLeft[1] = g;
      this.colorTopLeft[2] = b;
      this.colorBottomLeft[0] = r;
      this.colorBottomLeft[1] = g;
      this.colorBottomLeft[2] = b;
      this.colorBottomRight[0] = r;
      this.colorBottomRight[1] = g;
      this.colorBottomRight[2] = b;
      this.colorTopRight[0] = r;
      this.colorTopRight[1] = g;
      this.colorTopRight[2] = b;
   }

   public void scaleColor(float[] color, float scale) {
      for(int i = 0; i < color.length; ++i) {
         color[i] *= scale;
      }

   }

   public void setRotateTransform(int faceFrom, int faceTo) {
      this.rotateTransform = ROTATION_BY_FACE_FACE[faceFrom][faceTo];
      if (this.rotateTransform != 0) {
         this.transformRenderBound(this.rotateTransform);
         this.transformRenderOffset(this.rotateTransform);
      }

   }

   public void undoRotateTransform() {
      if (this.rotateTransform != 0) {
         this.transformRenderBound(4 - this.rotateTransform);
         this.transformRenderOffset(4 - this.rotateTransform);
      }

      this.clearRotateTransform();
   }

   public void clearRotateTransform() {
      this.rotateTransform = 0;
   }

   private void transformRenderOffset(int rotation) {
      double scratch;
      switch(rotation) {
      case 1:
         scratch = this.renderOffsetX;
         this.renderOffsetX = -this.renderOffsetZ;
         this.renderOffsetZ = scratch;
         break;
      case 2:
         this.renderOffsetX = -this.renderOffsetX;
         this.renderOffsetZ = -this.renderOffsetZ;
         break;
      case 3:
         scratch = this.renderOffsetX;
         this.renderOffsetX = this.renderOffsetZ;
         this.renderOffsetZ = -scratch;
      }

   }

   private void transformRenderBound(int rotation) {
      this.scratchIn[0] = this.renderMinX;
      this.scratchIn[1] = this.renderMinY;
      this.scratchIn[2] = this.renderMinZ;
      this.transformCoord(this.scratchIn, this.scratchOut, rotation);
      this.renderMinX = this.scratchOut[0];
      this.renderMinY = this.scratchOut[1];
      this.renderMinZ = this.scratchOut[2];
      this.scratchIn[0] = this.renderMaxX;
      this.scratchIn[1] = this.renderMaxY;
      this.scratchIn[2] = this.renderMaxZ;
      this.transformCoord(this.scratchIn, this.scratchOut, rotation);
      this.renderMaxX = this.scratchOut[0];
      this.renderMaxY = this.scratchOut[1];
      this.renderMaxZ = this.scratchOut[2];
      double temp;
      if (this.renderMinX > this.renderMaxX) {
         temp = this.renderMinX;
         this.renderMinX = this.renderMaxX;
         this.renderMaxX = temp;
      }

      if (this.renderMinZ > this.renderMaxZ) {
         temp = this.renderMinZ;
         this.renderMinZ = this.renderMaxZ;
         this.renderMaxZ = temp;
      }

   }

   public void transformCoord(double x, double y, double z, double[] coordCout, int rotation) {
      this.scratchIn[0] = x;
      this.scratchIn[1] = y;
      this.scratchIn[2] = z;
      this.transformCoord(this.scratchIn, coordCout, rotation);
   }

   public void transformCoord(double[] coordIn, double[] coordOut, int rotation) {
      coordOut[1] = coordIn[1];
      switch(rotation) {
      case 0:
      default:
         coordOut[0] = coordIn[0];
         coordOut[2] = coordIn[2];
         break;
      case 1:
         coordOut[0] = 1.0D - coordIn[2];
         coordOut[2] = coordIn[0];
         break;
      case 2:
         coordOut[0] = 1.0D - coordIn[0];
         coordOut[2] = 1.0D - coordIn[2];
         break;
      case 3:
         coordOut[0] = coordIn[2];
         coordOut[2] = 1.0D - coordIn[0];
      }

   }
}
