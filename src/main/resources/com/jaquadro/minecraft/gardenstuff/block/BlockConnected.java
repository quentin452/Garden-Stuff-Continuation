package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardentrees.block.BlockThinLog;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockConnected extends Block {
   public BlockConnected(String blockName, Material material) {
      super(material);
      this.setBlockName(blockName);
      this.setHardness(2.5F);
      this.setResistance(5.0F);
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setBlockBoundsForItemRender();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int damageDropped(int meta) {
      return meta;
   }

   public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
      boolean flagN = this.canConnectTo(world, x, y, z - 1, ForgeDirection.NORTH);
      boolean flagS = this.canConnectTo(world, x, y, z + 1, ForgeDirection.SOUTH);
      boolean flagW = this.canConnectTo(world, x - 1, y, z, ForgeDirection.WEST);
      boolean flagE = this.canConnectTo(world, x + 1, y, z, ForgeDirection.EAST);
      float height = this.getCollisionHeight();
      if (!(flagW | flagE | flagN | flagS)) {
         this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, height, 0.5625F);
         super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
      } else {
         if (flagW || flagE) {
            if (flagW && flagE) {
               this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, height, 0.5625F);
            } else if (flagW) {
               this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, height, 0.5625F);
            } else if (flagE) {
               this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, height, 0.5625F);
            }

            super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
         }

         if (flagN || flagS) {
            if (flagN && flagS) {
               this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, height, 1.0F);
            } else if (flagN) {
               this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, height, 0.5F);
            } else if (flagS) {
               this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, height, 1.0F);
            }

            super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
         }

      }
   }

   protected float getCollisionHeight() {
      return 1.0F;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      boolean flagN = this.canConnectTo(world, x, y, z - 1, ForgeDirection.NORTH);
      boolean flagS = this.canConnectTo(world, x, y, z + 1, ForgeDirection.SOUTH);
      boolean flagW = this.canConnectTo(world, x - 1, y, z, ForgeDirection.WEST);
      boolean flagE = this.canConnectTo(world, x + 1, y, z, ForgeDirection.EAST);
      float margin = 0.375F;
      float zs = flagN ? 0.0F : margin;
      float ze = flagS ? 1.0F : 1.0F - margin;
      float xs = flagW ? 0.0F : margin;
      float xe = flagE ? 1.0F : 1.0F - margin;
      this.setBlockBounds(xs, 0.0F, zs, xe, 1.0F, ze);
   }

   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList ret = new ArrayList();
      int count = this.quantityDropped(metadata, fortune, world.rand);

      for(int i = 0; i < count; ++i) {
         Item item = this.getItemDropped(metadata, world.rand, fortune);
         if (item != null) {
            ret.add(new ItemStack(item, 1, this.damageDropped(metadata)));
         }
      }

      return ret;
   }

   public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
      return this.canConnectTo(world.getBlock(x, y, z)) || world.isSideSolid(x, y, z, dir.getOpposite(), false);
   }

   public boolean canConnectTo(Block block) {
      return block.getMaterial().isOpaque() && block.renderAsNormalBlock() || block == this;
   }

   public int calcConnectionFlags(IBlockAccess world, int x, int y, int z) {
      Block blockYNeg = world.getBlock(x, y - 1, z);
      Block blockYPos = world.getBlock(x, y + 1, z);
      Block blockZNeg = world.getBlock(x, y, z - 1);
      Block blockZPos = world.getBlock(x, y, z + 1);
      Block blockXNeg = world.getBlock(x - 1, y, z);
      Block blockXPos = world.getBlock(x + 1, y, z);
      boolean hardYNeg = this.isNeighborHardConnection(world, x, y - 1, z, blockYNeg, ForgeDirection.DOWN);
      boolean hardYPos = this.isNeighborHardConnection(world, x, y + 1, z, blockYPos, ForgeDirection.UP);
      boolean hardZNeg = this.isNeighborHardConnection(world, x, y, z - 1, blockZNeg, ForgeDirection.NORTH);
      boolean hardZPos = this.isNeighborHardConnection(world, x, y, z + 1, blockZPos, ForgeDirection.SOUTH);
      boolean hardXNeg = this.isNeighborHardConnection(world, x - 1, y, z, blockXNeg, ForgeDirection.WEST);
      boolean hardXPos = this.isNeighborHardConnection(world, x + 1, y, z, blockXPos, ForgeDirection.EAST);
      boolean extYNeg = this.isNeighborExtConnection(world, x, y - 1, z, blockYNeg, ForgeDirection.DOWN);
      boolean extYPos = this.isNeighborExtConnection(world, x, y + 1, z, blockYPos, ForgeDirection.UP);
      boolean extZNeg = this.isNeighborExtConnection(world, x, y, z - 1, blockZNeg, ForgeDirection.NORTH);
      boolean extZPos = this.isNeighborExtConnection(world, x, y, z + 1, blockZPos, ForgeDirection.SOUTH);
      boolean extXNeg = this.isNeighborExtConnection(world, x - 1, y, z, blockXNeg, ForgeDirection.WEST);
      boolean extXPos = this.isNeighborExtConnection(world, x + 1, y, z, blockXPos, ForgeDirection.EAST);
      return (hardYNeg ? 1 : 0) | (hardYPos ? 2 : 0) | (hardZNeg ? 4 : 0) | (hardZPos ? 8 : 0) | (hardXNeg ? 16 : 0) | (hardXPos ? 32 : 0) | (extYNeg ? 64 : 0) | (extYPos ? 128 : 0) | (extZNeg ? 256 : 0) | (extZPos ? 512 : 0) | (extXNeg ? 1024 : 0) | (extXPos ? 2048 : 0);
   }

   public boolean isFenceBelow(IBlockAccess world, int x, int y, int z) {
      Block blockYNeg = world.getBlock(x, y - 1, z);
      return blockYNeg == this;
   }

   private boolean isNeighborHardConnection(IBlockAccess world, int x, int y, int z, Block block, ForgeDirection side) {
      if (block.getMaterial().isOpaque() && block.renderAsNormalBlock()) {
         return true;
      } else if (block.isSideSolid(world, x, y, z, side.getOpposite())) {
         return true;
      } else {
         return block == this;
      }
   }

   private boolean isNeighborExtConnection(IBlockAccess world, int x, int y, int z, Block block, ForgeDirection side) {
      return block instanceof BlockThinLog;
   }
}
