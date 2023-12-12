package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.api.block.IChain;
import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import com.jaquadro.minecraft.gardencore.util.RenderHelper;
import com.jaquadro.minecraft.gardencore.util.RenderHelperState;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityCandelabra;
import com.jaquadro.minecraft.gardenstuff.core.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockCandelabra extends BlockContainer {
   @SideOnly(Side.CLIENT)
   private IIcon iconArm;
   @SideOnly(Side.CLIENT)
   private IIcon iconArmExt;
   @SideOnly(Side.CLIENT)
   private IIcon iconPlate;
   @SideOnly(Side.CLIENT)
   private IIcon iconCandle;
   @SideOnly(Side.CLIENT)
   private IIcon iconBase;
   @SideOnly(Side.CLIENT)
   private IIcon iconHang;
   @SideOnly(Side.CLIENT)
   private IIcon iconCandleSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconCandleTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconHolderSide;

   public BlockCandelabra(String blockName) {
      super(Material.iron);
      this.setBlockName(blockName);
      this.setTickRandomly(true);
      this.setHardness(2.5F);
      this.setResistance(5.0F);
      this.setLightLevel(0.0F);
      this.setBlockTextureName("GardenStuff:candelabra");
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return ClientProxy.sconceRenderID;
   }

   public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
      return true;
   }

   public int damageDropped(int meta) {
      return meta;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      TileEntityCandelabra tile = this.getTileEntity(world, x, y, z);
      float yMin = 0.0F;
      float yMax = 1.0F;
      float xMin = 0.0F;
      float xMax = 1.0F;
      float zMin = 0.0F;
      float zMax = 1.0F;
      float depth = tile.getLevel() == 2 ? 0.5F : 0.390625F;
      float hwidth = tile.getLevel() == 0 ? 0.125F : 0.390625F;
      if (tile.isSconce()) {
         yMin = 0.0625F;
         yMax = 0.875F;
         switch(tile.getDirection()) {
         case 2:
            zMax = depth;
            xMin = 0.5F - hwidth;
            xMax = 0.5F + hwidth;
            break;
         case 3:
            zMin = 1.0F - depth;
            xMin = 0.5F - hwidth;
            xMax = 0.5F + hwidth;
            break;
         case 4:
            xMax = depth;
            zMin = 0.5F - hwidth;
            zMax = 0.5F + hwidth;
            break;
         case 5:
            xMin = 1.0F - depth;
            zMin = 0.5F - hwidth;
            zMax = 0.5F + hwidth;
         }
      } else {
         yMax = 0.9375F;
         zMin = 0.359375F;
         xMin = 0.359375F;
         zMax = 0.640625F;
         xMax = 0.640625F;
         switch(tile.getLevel()) {
         case 0:
            yMax = 0.9375F;
            break;
         case 1:
            if (tile.getDirection() != 2 && tile.getDirection() != 3) {
               zMin = 0.03125F;
               zMax = 0.96875F;
            } else {
               xMin = 0.03125F;
               xMax = 0.96875F;
            }
            break;
         case 2:
            zMin = 0.03125F;
            xMin = 0.03125F;
            zMax = 0.96875F;
            xMax = 0.96875F;
         }
      }

      this.setBlockBounds(xMin, yMin, zMin, xMax, yMax, zMax);
   }

   public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
      this.setBlockBoundsBasedOnState(world, x, y, z);
      this.setBlockBounds((float)this.minX, (float)this.minY, (float)this.minZ, (float)this.maxX, (float)this.maxY - 0.09375F, (float)this.maxZ);
      super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
   }

   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
      TileEntityCandelabra te = (TileEntityCandelabra)world.getTileEntity(x, y, z);
      if (te != null && !te.isDirectionInitialized()) {
         int quadrant = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
         switch(quadrant) {
         case 0:
            te.setDirection(3);
            break;
         case 1:
            te.setDirection(4);
            break;
         case 2:
            te.setDirection(2);
            break;
         case 3:
            te.setDirection(5);
         }

         if (world.isRemote) {
            te.invalidate();
            world.markBlockForUpdate(x, y, z);
         }

      }
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
      TileEntityCandelabra tile = this.getTileEntity(world, x, y, z);
      if (tile != null) {
         double[] c = new double[3];
         int level = tile.getLevel();
         int dir = tile.getDirection();
         float flameDepth = 0.96875F;
         if (tile.isSconce()) {
            if (level == 0) {
               RenderHelper.instance.state.transformCoord(0.5D, (double)flameDepth, 0.25D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
            }

            if (level == 1 || level == 2) {
               RenderHelper.instance.state.transformCoord(0.25D, (double)flameDepth, 0.25D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
               RenderHelper.instance.state.transformCoord(0.75D, (double)flameDepth, 0.25D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
            }

            if (level == 2) {
               RenderHelper.instance.state.transformCoord(0.5D, (double)flameDepth, 0.375D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
            }
         } else {
            Block blockUpper = world.getBlock(x, y + 1, z);
            boolean hanging = level > 0 && (blockUpper instanceof IChain || blockUpper.isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN));
            if (level >= 0 && !hanging) {
               RenderHelper.instance.state.transformCoord(0.5D, (double)(flameDepth + 0.0625F), 0.5D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
            }

            if (level >= 1) {
               RenderHelper.instance.state.transformCoord(0.15625D, (double)flameDepth, 0.5D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
               RenderHelper.instance.state.transformCoord(0.84375D, (double)flameDepth, 0.5D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
            }

            if (level >= 2) {
               RenderHelper.instance.state.transformCoord(0.5D, (double)flameDepth, 0.15625D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
               RenderHelper.instance.state.transformCoord(0.5D, (double)flameDepth, 0.84375D, c, RenderHelperState.ROTATION_BY_FACE_FACE[2][dir]);
               this.renderParticleAt(world, (double)x + c[0], (double)y + c[1], (double)z + c[2]);
            }
         }

      }
   }

   private void renderParticleAt(World world, double x, double y, double z) {
      world.spawnParticle("flame", x, y, z, 0.0D, 0.0D, 0.0D);
   }

   public int getLightValue(IBlockAccess world, int x, int y, int z) {
      TileEntityCandelabra tile = this.getTileEntity(world, x, y, z);
      if (tile == null) {
         return 0;
      } else {
         switch(tile.getLevel()) {
         case 0:
            return 13;
         case 1:
            return 14;
         case 2:
            return 15;
         default:
            return 0;
         }
      }
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List blockList) {
      for(int i = 0; i < 3; ++i) {
         blockList.add(new ItemStack(item, 1, i));
      }

   }

   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
      return new TileEntityCandelabra();
   }

   public TileEntityCandelabra getTileEntity(IBlockAccess world, int x, int y, int z) {
      TileEntity te = world.getTileEntity(x, y, z);
      return te != null && te instanceof TileEntityCandelabra ? (TileEntityCandelabra)te : null;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconArm() {
      return this.iconArm;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconArmExt() {
      return this.iconArmExt;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconCandle() {
      return this.iconCandle;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconBase() {
      return this.iconBase;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconHang() {
      return this.iconHang;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconCandleSide() {
      return this.iconCandleSide;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconCandleTop() {
      return this.iconCandleTop;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconHolderSide() {
      return this.iconHolderSide;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      this.iconCandle = register.registerIcon(this.getTextureName() + "_candle");
      this.iconArm = register.registerIcon(this.getTextureName() + "_arm");
      this.iconArmExt = register.registerIcon(this.getTextureName() + "_arm_ext");
      this.iconBase = register.registerIcon(this.getTextureName() + "_base");
      this.iconHang = register.registerIcon(this.getTextureName() + "_hang");
      this.iconCandleSide = register.registerIcon(this.getTextureName() + "_candle_side");
      this.iconCandleTop = register.registerIcon(this.getTextureName() + "_candle_top");
      this.iconHolderSide = register.registerIcon(this.getTextureName() + "_holder_side");
   }
}
