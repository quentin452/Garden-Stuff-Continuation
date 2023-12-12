package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardenapi.api.GardenAPI;
import com.jaquadro.minecraft.gardenapi.api.connect.IAttachable;
import com.jaquadro.minecraft.gardenapi.api.connect.IChainSingleAttachable;
import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.api.block.IChain;
import com.jaquadro.minecraft.gardencore.api.block.IChainAttachable;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.core.ModBlocks;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightChain extends Block implements IPlantProxy, IChain {
   public static final String[] types = new String[]{"iron", "gold", "rope", "rust", "wrought_iron", "moss"};
   @SideOnly(Side.CLIENT)
   private static IIcon[] icons;
   private static final Vec3[] defaultAttachPoints = new Vec3[]{Vec3.createVectorHelper(0.03125D, 1.0D, 0.03125D), Vec3.createVectorHelper(0.03125D, 1.0D, 0.96875D), Vec3.createVectorHelper(0.96875D, 1.0D, 0.03125D), Vec3.createVectorHelper(0.96875D, 1.0D, 0.96875D)};
   private static final Vec3[] singleAttachPoint = new Vec3[]{Vec3.createVectorHelper(0.5D, 1.0D, 0.5D)};

   public BlockLightChain(String blockName) {
      super(Material.iron);
      this.setBlockName(blockName);
      this.setHardness(2.5F);
      this.setResistance(5.0F);
      this.setStepSound(Block.soundTypeMetal);
      this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
      this.setBlockTextureName("GardenStuff:chain_light");
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return ClientProxy.lightChainRenderID;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      return null;
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
      float minX = 1.0F;
      float minZ = 1.0F;
      float maxX = 0.0F;
      float maxZ = 0.0F;
      Vec3[] var9 = this.getAttachPoints(world, x, y, z);
      int var10 = var9.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         Vec3 point = var9[var11];
         if (point.xCoord < (double)minX) {
            minX = (float)point.xCoord;
         }

         if (point.zCoord < (double)minZ) {
            minZ = (float)point.zCoord;
         }

         if (point.xCoord > (double)maxX) {
            maxX = (float)point.xCoord;
         }

         if (point.zCoord > (double)maxZ) {
            maxZ = (float)point.zCoord;
         }
      }

      if ((double)(maxX - minX) < 0.125D) {
         minX = 0.4375F;
         maxX = 0.5625F;
      }

      if ((double)(maxZ - minZ) < 0.125D) {
         minZ = 0.4375F;
         maxZ = 0.5625F;
      }

      return AxisAlignedBB.getBoundingBox((double)((float)x + minX), (double)(y + 0), (double)((float)z + minZ), (double)((float)x + maxX), (double)(y + 1), (double)((float)z + maxZ));
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vx, float vy, float vz) {
      BlockGarden block = this.getGardenBlock(world, x, y, z);
      if (block != null) {
         y = this.getBaseBlockYCoord(world, x, y, z);
         return block.applyItemToGarden(world, x, y, z, player, (ItemStack)null);
      } else {
         return super.onBlockActivated(world, x, y, z, player, side, vx, vy, vz);
      }
   }

   public boolean applyBonemeal(World world, int x, int y, int z) {
      return ModBlocks.gardenProxy.applyBonemeal(world, x, y, z);
   }

   public TileEntityGarden getGardenEntity(IBlockAccess blockAccess, int x, int y, int z) {
      return ModBlocks.gardenProxy.getGardenEntity(blockAccess, x, y, z);
   }

   public int findMinY(IBlockAccess world, int x, int y, int z) {
      while(true) {
         if (y > 0) {
            --y;
            if (world.getBlock(x, y, z) == this) {
               continue;
            }

            return y + 1;
         }

         return y;
      }
   }

   public int findMaxY(IBlockAccess world, int x, int y, int z) {
      while(true) {
         if (y < world.getHeight() - 1) {
            ++y;
            if (world.getBlock(x, y, z) == this) {
               continue;
            }

            return y - 1;
         }

         return y;
      }
   }

   public Vec3[] getAttachPoints(IBlockAccess world, int x, int y, int z) {
      int yMin = this.findMinY(world, x, y, z);
      Block bottomBlock = world.getBlock(x, yMin - 1, z);
      IAttachable attachable = GardenAPI.instance().registries().attachable().getAttachable(bottomBlock, world.getBlockMetadata(x, y - 1, z));
      Vec3[] attachPoints = singleAttachPoint;
      if (bottomBlock instanceof IChainAttachable) {
         attachPoints = ((IChainAttachable)bottomBlock).getChainAttachPoints(1);
      } else if (attachable != null && attachable.isAttachable(world, x, y - 1, z, 1)) {
         attachPoints = new Vec3[]{Vec3.createVectorHelper(0.5D, attachable.getAttachDepth(world, x, y - 1, z, 1), 0.5D)};
      } else if (bottomBlock instanceof IChainSingleAttachable) {
         Vec3 attachPoint = ((IChainSingleAttachable)bottomBlock).getChainAttachPoint(world, x, y, z, 1);
         if (attachPoint != null) {
            attachPoints = new Vec3[]{attachPoint};
         }
      } else if (bottomBlock.renderAsNormalBlock() && bottomBlock.getMaterial() != Material.air) {
         attachPoints = defaultAttachPoints;
      }

      return attachPoints;
   }

   public int damageDropped(int meta) {
      return MathHelper.clamp_int(meta, 0, types.length - 1);
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
      list.add(new ItemStack(item, 1, 0));
      list.add(new ItemStack(item, 1, 1));
      list.add(new ItemStack(item, 1, 3));
      list.add(new ItemStack(item, 1, 4));
      list.add(new ItemStack(item, 1, 5));
   }

   public IIcon getIcon(int side, int meta) {
      return icons[MathHelper.clamp_int(meta, 0, types.length - 1)];
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      icons = new IIcon[types.length];

      for(int i = 0; i < types.length; ++i) {
         icons[i] = register.registerIcon(this.getTextureName() + "_" + types[i]);
      }

   }

   private int getBaseBlockYCoord(IBlockAccess world, int x, int y, int z) {
      if (y == 0) {
         return 0;
      } else {
         --y;

         for(Block underBlock = world.getBlock(x, y, z); y > 0 && underBlock instanceof IPlantProxy; underBlock = world.getBlock(x, y, z)) {
            --y;
         }

         return y;
      }
   }

   public BlockGarden getGardenBlock(IBlockAccess world, int x, int y, int z) {
      if (y == 0) {
         return null;
      } else {
         y = this.getBaseBlockYCoord(world, x, y, z);
         Block underBlock = world.getBlock(x, y, z);
         return !(underBlock instanceof BlockGarden) ? null : (BlockGarden)underBlock;
      }
   }

   public boolean isMultiAttach() {
      return true;
   }
}
