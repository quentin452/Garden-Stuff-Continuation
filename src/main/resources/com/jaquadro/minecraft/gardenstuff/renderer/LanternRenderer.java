package com.jaquadro.minecraft.gardenstuff.renderer;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.component.ILanternSource;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachable;
import com.jaquadro.minecraft.gardenapi.api.connect.IChainSingleAttachable;
import com.jaquadro.minecraft.gardenapi.internal.Api;
import com.jaquadro.minecraft.gardencore.util.BindingStack;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import com.jaquadro.minecraft.gardenstuff.GardenStuff;
import com.jaquadro.minecraft.gardenstuff.block.BlockLantern;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLantern;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class LanternRenderer implements ISimpleBlockRenderingHandler {
   public int renderPass = 0;
   private float[] colorScratch = new float[3];
   private static final Vec3 defaultAttachPoint = Vec3.createVectorHelper(0.5D, 0.0D, 0.5D);

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return !(block instanceof BlockLantern) ? false : this.renderWorldBlock(world, x, y, z, (BlockLantern)block, modelId, renderer);
   }

   private boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockLantern block, int modelId, RenderBlocks renderer) {
      TileEntityLantern tile;
      if (this.renderPass == 0) {
         renderer.setRenderBoundsFromBlock(block);
         renderer.renderStandardBlock(block, x, y, z);
         renderer.renderFromInside = true;
         renderer.renderMinY = 0.004999999888241291D;
         renderer.renderStandardBlock(block, x, y, z);
         renderer.renderFromInside = false;
         renderer.overrideBlockTexture = block.getIconTopCross();
         renderer.renderCrossedSquares(block, x, y, z);
         renderer.overrideBlockTexture = null;
         tile = block.getTileEntity(world, x, y, z);
         if (tile != null) {
            BindingStack binding = GardenStuff.proxy.getClientBindingStack(block);
            binding.setDefaultMeta(world.getBlockMetadata(x, y, z));
            binding.bind(tile.getWorldObj(), x, y, z, 0, tile.getLightSourceMeta());
            Tessellator.instance.addTranslation(0.0F, 0.001F, 0.0F);
            if (tile.getLightSource() != null) {
               ILanternSource lanternSource = Api.instance.registries().lanternSources().getLanternSource(tile.getLightSource());
               if (lanternSource != null && lanternSource.renderInPass(this.renderPass)) {
                  lanternSource.render(renderer, x, y, z, tile.getLightSourceMeta(), this.renderPass);
               }
            }

            Tessellator.instance.addTranslation(0.0F, -0.001F, 0.0F);
            binding.unbind(tile.getWorldObj(), x, y, z);
         }

         this.renderChain(world, renderer, block, x, y, z);
      } else if (this.renderPass == 1) {
         tile = block.getTileEntity(world, x, y, z);
         if (tile != null && tile.hasGlass()) {
            IIcon glass = block.getIconStainedGlass(world.getBlockMetadata(x, y, z));
            RenderHelper.calculateBaseColor(this.colorScratch, block.getBlockColor());
            RenderHelper.setTessellatorColor(Tessellator.instance, this.colorScratch);
            Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
            renderer.setRenderBoundsFromBlock(block);
            renderer.renderMinX += 0.01D;
            renderer.renderMinZ += 0.01D;
            renderer.renderMaxX -= 0.01D;
            renderer.renderMaxZ -= 0.01D;
            renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, glass);
            renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, glass);
            renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, glass);
            renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, glass);
            renderer.renderMaxY -= 0.01D;
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, glass);
         } else {
            RenderHelper.instance.renderEmptyPlane(x, y, z);
         }

         if (tile != null && tile.getLightSource() != null) {
            ILanternSource lanternSource = Api.instance.registries().lanternSources().getLanternSource(tile.getLightSource());
            if (lanternSource != null && lanternSource.renderInPass(this.renderPass)) {
               lanternSource.render(renderer, x, y, z, tile.getLightSourceMeta(), this.renderPass);
            }
         }
      }

      return true;
   }

   private void renderChain(IBlockAccess world, RenderBlocks renderer, BlockLantern block, int x, int y, int z) {
      Block lowerBlock = world.getBlock(x, y - 1, z);
      if (!lowerBlock.isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
         Block upperBlock = world.getBlock(x, y + 1, z);
         if (upperBlock instanceof IChainSingleAttachable) {
            Vec3 attach = ((IChainSingleAttachable)upperBlock).getChainAttachPoint(world, x, y + 1, z, 0);
            if (attach != null && attach != defaultAttachPoint) {
               RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, attach.yCoord, 1.0D);
               RenderHelper.instance.renderCrossedSquares(world, ModBlocks.heavyChain, x, y + 1, z, ModBlocks.lightChain.getIcon(0, 4));
               return;
            }
         }

         IAttachable attachable = GardenAPI.instance().registries().attachable().getAttachable(upperBlock, world.getBlockMetadata(x, y + 1, z));
         if (attachable != null && attachable.isAttachable(world, x, y + 1, z, 0)) {
            double depth = attachable.getAttachDepth(world, x, y + 1, z, 0);
            if (depth > 0.0D) {
               RenderHelper.instance.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, depth, 1.0D);
               RenderHelper.instance.renderCrossedSquares(world, ModBlocks.heavyChain, x, y + 1, z, ModBlocks.lightChain.getIcon(0, 4));
            }
         }

      }
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return false;
   }

   public int getRenderId() {
      return ClientProxy.lanternRenderID;
   }
}
