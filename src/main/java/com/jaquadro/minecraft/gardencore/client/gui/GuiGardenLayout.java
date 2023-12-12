package com.jaquadro.minecraft.gardencore.client.gui;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.inventory.ContainerGarden;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiGardenLayout extends GuiContainer {
   private static final ResourceLocation guiTextures = new ResourceLocation("gardencore", "textures/gui/gardenLayout.png");
   private TileEntityGarden tileGarden;
   private Slot hoveredSlot;
   private static final int slotBaseX = 44;
   private static final int slotBaseY = 18;
   private static final int smDisabledX = 176;
   private static final int smDisabledY = 0;

   public GuiGardenLayout(InventoryPlayer inventory, TileEntityGarden tileEntity) {
      super(new ContainerGarden(inventory, tileEntity));
      this.tileGarden = tileEntity;
      this.xSize = 176;
      this.ySize = 204;
   }

   public void drawScreen(int mouseX, int mouseY, float dt) {
      this.hoveredSlot = null;
      int i = 0;

      for(int n = this.inventorySlots.inventorySlots.size(); i < n; ++i) {
         Slot slot = this.inventorySlots.getSlot(i);
         if (this.func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY)) {
            this.hoveredSlot = slot;
         }
      }

      super.drawScreen(mouseX, mouseY, dt);
   }

   protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
      GL11.glPushAttrib(1048575);
      String name = this.tileGarden.hasCustomInventoryName() ? this.tileGarden.getInventoryName() : I18n.format(this.tileGarden.getInventoryName(), new Object[0]);
      this.fontRendererObj.drawString(name, 8, 6, 4210752);
      this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
      int i = 0;

      for(int n = this.inventorySlots.inventorySlots.size(); i < n; ++i) {
         this.drawSlotHighlight(this.inventorySlots.getSlot(i));
      }

      GL11.glPopAttrib();
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(guiTextures);
      int guiX = (this.width - this.xSize) / 2;
      int guiY = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);
      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord - 1, this.tileGarden.yCoord, this.tileGarden.zCoord - 1)) {
         this.drawTexturedModalRect(guiX + 44, guiY + 18, 176, 0, 16, 16);
      }

      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord, this.tileGarden.yCoord, this.tileGarden.zCoord - 1)) {
         this.drawTexturedModalRect(guiX + 44 + 36, guiY + 18, 176, 0, 16, 16);
      }

      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord + 1, this.tileGarden.yCoord, this.tileGarden.zCoord - 1)) {
         this.drawTexturedModalRect(guiX + 44 + 72, guiY + 18, 176, 0, 16, 16);
      }

      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord + 1, this.tileGarden.yCoord, this.tileGarden.zCoord)) {
         this.drawTexturedModalRect(guiX + 44 + 72, guiY + 18 + 36, 176, 0, 16, 16);
      }

      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord + 1, this.tileGarden.yCoord, this.tileGarden.zCoord + 1)) {
         this.drawTexturedModalRect(guiX + 44 + 72, guiY + 18 + 72, 176, 0, 16, 16);
      }

      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord, this.tileGarden.yCoord, this.tileGarden.zCoord + 1)) {
         this.drawTexturedModalRect(guiX + 44 + 36, guiY + 18 + 72, 176, 0, 16, 16);
      }

      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord - 1, this.tileGarden.yCoord, this.tileGarden.zCoord + 1)) {
         this.drawTexturedModalRect(guiX + 44, guiY + 18 + 72, 176, 0, 16, 16);
      }

      if (!this.tileGarden.isAttachedNeighbor(this.tileGarden.xCoord - 1, this.tileGarden.yCoord, this.tileGarden.zCoord)) {
         this.drawTexturedModalRect(guiX + 44, guiY + 18 + 36, 176, 0, 16, 16);
      }

   }

   protected void drawSlotHighlight(Slot slot) {
      if (this.hoveredSlot != null && !this.isInPlayerInventory(this.hoveredSlot) && slot != this.hoveredSlot) {
         if (this.mc.thePlayer.inventory.getItemStack() == null && slot != null && slot.getHasStack() && this.hoveredSlot.isItemValid(slot.getStack())) {
            this.zLevel += 100.0F;
            this.drawGradientRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, -2130706433, -2130706433);
            this.zLevel -= 100.0F;
         }

      }
   }

   private boolean isInPlayerInventory(Slot slot) {
      return slot.inventory == this.mc.thePlayer.inventory;
   }
}
