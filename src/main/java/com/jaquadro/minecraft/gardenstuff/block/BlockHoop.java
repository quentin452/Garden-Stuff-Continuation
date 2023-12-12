package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.api.block.IChainAttachable;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import com.jaquadro.minecraft.gardenstuff.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;

public class BlockHoop extends Block implements IChainAttachable {
   private Vec3[] attachPoints = new Vec3[]{Vec3.createVectorHelper(0.03125D, 0.375D, 0.03125D), Vec3.createVectorHelper(0.03125D, 0.375D, 0.96875D), Vec3.createVectorHelper(0.96875D, 0.375D, 0.03125D), Vec3.createVectorHelper(0.96875D, 0.375D, 0.96875D)};

   public BlockHoop(String name) {
      super(Material.iron);
      this.setBlockName(name);
      this.setHardness(2.5F);
      this.setResistance(5.0F);
      this.setStepSound(soundTypeMetal);
      this.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.375F, 1.0F);
      this.setBlockTextureName("GardenStuffhoop");
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return ClientProxy.hoopRenderID;
   }

   public IIcon getIcon(int side, int meta) {
      return ModBlocks.metalBlock.getIcon(side, meta);
   }

   public Vec3[] getChainAttachPoints(int side) {
      return side == 1 ? this.attachPoints : null;
   }
}
