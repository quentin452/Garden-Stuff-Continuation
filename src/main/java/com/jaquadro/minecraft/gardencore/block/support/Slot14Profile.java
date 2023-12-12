package com.jaquadro.minecraft.gardencore.block.support;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.client.gui.GuiGardenLayout;
import com.jaquadro.minecraft.gardencore.inventory.ContainerGarden;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class Slot14Profile extends BasicSlotProfile {
   public static final int SLOT_CENTER = 0;
   public static final int SLOT_COVER = 1;
   public static final int SLOT_NW = 2;
   public static final int SLOT_NE = 3;
   public static final int SLOT_SW = 4;
   public static final int SLOT_SE = 5;
   public static final int SLOT_TOP_LEFT = 6;
   public static final int SLOT_TOP = 7;
   public static final int SLOT_TOP_RIGHT = 8;
   public static final int SLOT_RIGHT = 9;
   public static final int SLOT_BOTTOM_RIGHT = 10;
   public static final int SLOT_BOTTOM = 11;
   public static final int SLOT_BOTTOM_LEFT = 12;
   public static final int SLOT_LEFT = 13;
   private static float[] plantOffsetX = new float[]{0.0F, 0.0F, -0.252F, 0.25F, -0.25F, 0.252F, -0.5F, -0.001F, 0.5F, 0.5F, 0.5F, -0.001F, -0.5F, -0.5F};
   private static float[] plantOffsetZ = new float[]{0.0F, 0.0F, -0.25F, -0.252F, 0.252F, 0.25F, -0.501F, -0.5F, -0.501F, 0.0F, 0.449F, 0.5F, 0.449F, 0.0F};

   public Slot14Profile(BasicSlotProfile.Slot[] slots) {
      super(slots);
      if (slots.length != 14) {
         throw new IllegalArgumentException("Invalid slot count");
      }
   }

   public float getPlantOffsetX(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      return plantOffsetX[slot];
   }

   public float getPlantOffsetZ(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      return plantOffsetZ[slot];
   }

   public Object openPlantGUI(InventoryPlayer playerInventory, TileEntity gardenTile, boolean client) {
      if (gardenTile instanceof TileEntityGarden) {
         return client ? new GuiGardenLayout(playerInventory, (TileEntityGarden)gardenTile) : new ContainerGarden(playerInventory, (TileEntityGarden)gardenTile);
      } else {
         return null;
      }
   }
}
