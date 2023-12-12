package com.jaquadro.minecraft.gardencontainers.block.tile;

import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWindowBox extends TileEntityGarden {
   private int direction;

   protected int containerSlotCount() {
      return 6;
   }

   public int getDirection() {
      return this.direction & 7;
   }

   public boolean isUpper() {
      return (this.direction & 8) != 0;
   }

   public void setDirection(int direction) {
      this.direction = this.direction & 8 | direction & 7;
   }

   public void setUpper(boolean isUpper) {
      this.direction &= 7;
      if (isUpper) {
         this.direction |= 8;
      }

   }

   public boolean isSlotValid(int slot) {
      int dir = this.getDirection();
      BlockGarden garden = this.getGardenBlock();
      if (garden == null) {
         return false;
      } else {
         int facingDir;
         if (garden.getConnectionProfile().isAttachedNeighbor(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir)) {
            facingDir = this.getNeighborDirection(dir);
            switch(slot) {
            case 1:
               if (dir == 2 && facingDir == 5) {
                  return false;
               }

               if (dir == 4 && facingDir == 3) {
                  return false;
               }
               break;
            case 2:
               if (dir == 2 && facingDir == 4) {
                  return false;
               }

               if (dir == 5 && facingDir == 3) {
                  return false;
               }
               break;
            case 3:
               if (dir == 3 && facingDir == 5) {
                  return false;
               }

               if (dir == 4 && facingDir == 2) {
                  return false;
               }
               break;
            case 4:
               if (dir == 3 && facingDir == 4) {
                  return false;
               }

               if (dir == 5 && facingDir == 2) {
                  return false;
               }
            }
         }

         facingDir = dir % 2 == 0 ? dir + 1 : dir - 1;
         if (garden.getConnectionProfile().isAttachedNeighbor(this.worldObj, this.xCoord, this.yCoord, this.zCoord, facingDir)) {
            int facingDir = this.getNeighborDirection(facingDir);
            switch(slot) {
            case 1:
               if (dir == 3 && facingDir == 4) {
                  return true;
               }

               if (dir == 5 && facingDir == 2) {
                  return true;
               }
               break;
            case 2:
               if (dir == 3 && facingDir == 5) {
                  return true;
               }

               if (dir == 4 && facingDir == 2) {
                  return true;
               }
               break;
            case 3:
               if (dir == 2 && facingDir == 4) {
                  return true;
               }

               if (dir == 5 && facingDir == 3) {
                  return true;
               }
               break;
            case 4:
               if (dir == 2 && facingDir == 5) {
                  return true;
               }

               if (dir == 4 && facingDir == 3) {
                  return true;
               }
            }
         }

         switch(slot) {
         case 0:
            return true;
         case 1:
            if (dir != 2 && dir != 4) {
               break;
            }

            return true;
         case 2:
            if (dir == 2 || dir == 5) {
               return true;
            }
            break;
         case 3:
            if (dir == 3 || dir == 4) {
               return true;
            }
            break;
         case 4:
            if (dir == 3 || dir == 5) {
               return true;
            }
         }

         return false;
      }
   }

   private int getNeighborDirection(int direction) {
      TileEntity te = null;
      if (direction == 2) {
         te = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
      } else if (direction == 3) {
         te = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
      } else if (direction == 4) {
         te = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
      } else if (direction == 5) {
         te = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
      }

      if (te instanceof TileEntityWindowBox) {
         TileEntityWindowBox wb = (TileEntityWindowBox)te;
         return wb.getDirection();
      } else {
         return -1;
      }
   }

   public void readFromNBT(NBTTagCompound tag) {
      super.readFromNBT(tag);
      this.direction = 0;
      if (tag.hasKey("Dir")) {
         this.direction = tag.getByte("Dir");
      }

   }

   public void writeToNBT(NBTTagCompound tag) {
      super.writeToNBT(tag);
      tag.setByte("Dir", (byte)this.direction);
   }
}
