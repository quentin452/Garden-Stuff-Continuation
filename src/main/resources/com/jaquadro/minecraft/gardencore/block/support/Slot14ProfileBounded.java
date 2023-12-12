package com.jaquadro.minecraft.gardencore.block.support;

import com.jaquadro.minecraft.gardencore.api.block.IGardenBlock;
import com.jaquadro.minecraft.gardencore.api.block.garden.IConnectionProfile;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

public class Slot14ProfileBounded extends Slot14Profile {
   private IGardenBlock garden;
   private AxisAlignedBB[] bound = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
   private AxisAlignedBB[] boundS = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.6875D), AxisAlignedBB.getBoundingBox(0.0D, 0.0625D, 0.0D, 1.0D, 0.125D, 0.75D), AxisAlignedBB.getBoundingBox(0.0D, 0.125D, 0.0D, 1.0D, 1.0D, 0.8125D)};
   private AxisAlignedBB[] boundN = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.3125D, 1.0D, 0.0625D, 1.0D), AxisAlignedBB.getBoundingBox(0.0D, 0.0625D, 0.25D, 1.0D, 0.125D, 1.0D), AxisAlignedBB.getBoundingBox(0.0D, 0.125D, 0.1875D, 1.0D, 1.0D, 1.0D)};
   private AxisAlignedBB[] boundE = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.6875D, 0.0625D, 1.0D), AxisAlignedBB.getBoundingBox(0.0D, 0.0625D, 0.0D, 0.75D, 0.125D, 1.0D), AxisAlignedBB.getBoundingBox(0.0D, 0.125D, 0.0D, 0.8125D, 1.0D, 1.0D)};
   private AxisAlignedBB[] boundW = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.3125D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), AxisAlignedBB.getBoundingBox(0.25D, 0.0625D, 0.0D, 1.0D, 0.125D, 1.0D), AxisAlignedBB.getBoundingBox(0.1875D, 0.125D, 0.0D, 1.0D, 1.0D, 1.0D)};
   private AxisAlignedBB[] boundSW = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.3125D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.6875D), AxisAlignedBB.getBoundingBox(0.25D, 0.0625D, 0.0D, 1.0D, 0.125D, 0.75D), AxisAlignedBB.getBoundingBox(0.1875D, 0.125D, 0.0D, 1.0D, 1.0D, 0.8125D)};
   private AxisAlignedBB[] boundSE = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.6875D, 0.0625D, 0.6875D), AxisAlignedBB.getBoundingBox(0.0D, 0.0625D, 0.0D, 0.75D, 0.125D, 0.75D), AxisAlignedBB.getBoundingBox(0.0D, 0.125D, 0.0D, 0.8125D, 1.0D, 0.8125D)};
   private AxisAlignedBB[] boundNW = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.3125D, 0.0D, 0.3125D, 1.0D, 0.0625D, 1.0D), AxisAlignedBB.getBoundingBox(0.25D, 0.0625D, 0.25D, 1.0D, 0.125D, 1.0D), AxisAlignedBB.getBoundingBox(0.1875D, 0.125D, 0.1875D, 1.0D, 1.0D, 1.0D)};
   private AxisAlignedBB[] boundNE = new AxisAlignedBB[]{AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.3125D, 0.8125D, 0.0625D, 1.0D), AxisAlignedBB.getBoundingBox(0.0D, 0.0625D, 0.25D, 0.75D, 0.125D, 1.0D), AxisAlignedBB.getBoundingBox(0.0D, 0.125D, 0.1875D, 0.8125D, 1.0D, 1.0D)};

   public Slot14ProfileBounded(IGardenBlock garden, BasicSlotProfile.Slot[] slots) {
      super(slots);
      this.garden = garden;
   }

   public AxisAlignedBB[] getClippingBounds(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      IConnectionProfile connection = this.garden.getConnectionProfile();
      AxisAlignedBB[] activeBound = this.bound;
      boolean connectedS;
      boolean connectedE;
      switch(slot) {
      case 2:
         activeBound = this.boundNW;
         if (connection.isAttachedNeighbor(blockAccess, x, y, z, x - 1, y, z - 1)) {
            connectedS = connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z - 1);
            connectedE = connection.isAttachedNeighbor(blockAccess, x, y, z, x - 1, y, z);
            if (connectedS && connectedE) {
               activeBound = this.bound;
            } else if (connectedS) {
               activeBound = this.boundN;
            } else if (connectedE) {
               activeBound = this.boundW;
            }
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z - 1)) {
            activeBound = this.boundW;
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x - 1, y, z)) {
            activeBound = this.boundN;
         }
         break;
      case 3:
         activeBound = this.boundNE;
         if (connection.isAttachedNeighbor(blockAccess, x, y, z, x + 1, y, z - 1)) {
            connectedS = connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z - 1);
            connectedE = connection.isAttachedNeighbor(blockAccess, x, y, z, x + 1, y, z);
            if (connectedS && connectedE) {
               activeBound = this.bound;
            } else if (connectedS) {
               activeBound = this.boundN;
            } else if (connectedE) {
               activeBound = this.boundE;
            }
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z - 1)) {
            activeBound = this.boundE;
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x + 1, y, z)) {
            activeBound = this.boundN;
         }
         break;
      case 4:
         activeBound = this.boundSW;
         if (connection.isAttachedNeighbor(blockAccess, x, y, z, x - 1, y, z + 1)) {
            connectedS = connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z + 1);
            connectedE = connection.isAttachedNeighbor(blockAccess, x, y, z, x - 1, y, z);
            if (connectedS && connectedE) {
               activeBound = this.bound;
            } else if (connectedS) {
               activeBound = this.boundS;
            } else if (connectedE) {
               activeBound = this.boundW;
            }
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z + 1)) {
            activeBound = this.boundW;
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x - 1, y, z)) {
            activeBound = this.boundS;
         }
         break;
      case 5:
         activeBound = this.boundSE;
         if (connection.isAttachedNeighbor(blockAccess, x, y, z, x + 1, y, z + 1)) {
            connectedS = connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z + 1);
            connectedE = connection.isAttachedNeighbor(blockAccess, x, y, z, x + 1, y, z);
            if (connectedS && connectedE) {
               activeBound = this.bound;
            } else if (connectedS) {
               activeBound = this.boundS;
            } else if (connectedE) {
               activeBound = this.boundE;
            }
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x, y, z + 1)) {
            activeBound = this.boundE;
         } else if (connection.isAttachedNeighbor(blockAccess, x, y, z, x + 1, y, z)) {
            activeBound = this.boundS;
         }
      }

      return activeBound;
   }
}
