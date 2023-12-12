package com.jaquadro.minecraft.gardencore.client.gui;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityCompostBin;
import com.jaquadro.minecraft.gardencore.inventory.ContainerCompostBin;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCompostBin extends GuiContainer {
   private static final ResourceLocation compostBinGuiTextures = new ResourceLocation("gardencore", "textures/gui/compostBin.png");
   private TileEntityCompostBin tileCompost;
   private Slot hoveredSlot;

   public GuiCompostBin(InventoryPlayer inventory, TileEntityCompostBin tileEntity) {
      super(new ContainerCompostBin(inventory, tileEntity));
      this.tileCompost = tileEntity;
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
      String name = this.tileCompost.hasCustomInventoryName() ? this.tileCompost.getInventoryName() : I18n.format(this.tileCompost.getInventoryName(), new Object[0]);
      this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
      this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
      int i = 0;

      for(int n = this.inventorySlots.inventorySlots.size(); i < n; ++i) {
         this.drawSlotHighlight(this.inventorySlots.getSlot(i));
      }

      GL11.glPopAttrib();
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(compostBinGuiTextures);
      int halfW = (this.width - this.xSize) / 2;
      int halfH = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(halfW, halfH, 0, 0, this.xSize, this.ySize);
      if (this.tileCompost.binDecomposeTime > 0 || this.tileCompost.itemDecomposeCount > 0) {
         int timeRemaining = this.tileCompost.getDecomposeTimeRemainingScaled(24);
         this.drawTexturedModalRect(halfW + 89, halfH + 34, 176, 0, 24 - timeRemaining + 1, 16);
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
