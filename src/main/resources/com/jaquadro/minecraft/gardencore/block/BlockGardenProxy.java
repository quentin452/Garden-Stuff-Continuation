package com.jaquadro.minecraft.gardencore.block;

import com.jaquadro.minecraft.gardencore.GardenCore;
import com.jaquadro.minecraft.gardencore.api.GardenCoreAPI;
import com.jaquadro.minecraft.gardencore.api.IBonemealHandler;
import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.core.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockGardenProxy extends Block implements IPlantProxy {
   @SideOnly(Side.CLIENT)
   private IIcon transpIcon;
   private boolean applyingBonemeal;
   private int reeLightValue;

   public BlockGardenProxy(String blockName) {
      super(Material.plants);
      this.setHardness(0.0F);
      this.setLightOpacity(0);
      this.setBlockName(blockName);
   }

   public float getPlantOffsetX(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      BlockGarden gardenBlock = this.getGardenBlock(blockAccess, x, y, z);
      return gardenBlock == null ? 0.0F : gardenBlock.getSlotProfile().getPlantOffsetX(blockAccess, x, this.getBaseBlockYCoord(blockAccess, x, y, z), z, slot);
   }

   public float getPlantOffsetY(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      BlockGarden gardenBlock = this.getGardenBlock(blockAccess, x, y, z);
      return gardenBlock == null ? 0.0F : gardenBlock.getSlotProfile().getPlantOffsetY(blockAccess, x, this.getBaseBlockYCoord(blockAccess, x, y, z), z, slot);
   }

   public float getPlantOffsetZ(IBlockAccess blockAccess, int x, int y, int z, int slot) {
      BlockGarden gardenBlock = this.getGardenBlock(blockAccess, x, y, z);
      return gardenBlock == null ? 0.0F : gardenBlock.getSlotProfile().getPlantOffsetZ(blockAccess, x, this.getBaseBlockYCoord(blockAccess, x, y, z), z, slot);
   }

   public void bindSlot(World world, int x, int y, int z, TileEntityGarden te, int slot) {
      int data = 0;
      if (this.getPlantBlock(te, slot) != null) {
         data = this.getPlantData(te, slot);
      }

      GardenCore.proxy.getBindingStack(world, this).bind(world, x, y, z, slot, data);
   }

   public void unbindSlot(World world, int x, int y, int z, TileEntityGarden te) {
      GardenCore.proxy.getBindingStack(world, this).unbind(world, x, y, z);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return ClientProxy.gardenProxyRenderID;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      if (te == null) {
         return null;
      } else {
         BlockGarden garden = this.getGardenBlock(world, x, y, z);
         if (garden == null) {
            return null;
         } else {
            int baseY = this.getBaseBlockYCoord(world, x, y, z);
            AxisAlignedBB aabb = null;
            int[] var9 = garden.getSlotProfile().getPlantSlots();
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               int slot = var9[var11];
               Block block = this.getPlantBlock(te, slot);
               if (block != null) {
                  this.bindSlot(world, x, y, z, te, slot);

                  try {
                     AxisAlignedBB sub = block.getCollisionBoundingBoxFromPool(world, x, y, z);
                     if (sub != null) {
                        float offsetX = garden.getSlotProfile().getPlantOffsetX(world, x, baseY, z, slot);
                        float offsetY = garden.getSlotProfile().getPlantOffsetY(world, x, baseY, z, slot);
                        float offsetZ = garden.getSlotProfile().getPlantOffsetZ(world, x, baseY, z, slot);
                        sub.offset((double)offsetX, (double)offsetY, (double)offsetZ);
                        if (aabb == null) {
                           aabb = sub;
                        } else {
                           aabb = aabb.func_111270_a(sub);
                        }
                     }
                  } catch (Exception var21) {
                  } finally {
                     this.unbindSlot(world, x, y, z, te);
                  }
               }
            }

            return aabb;
         }
      }
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      BlockGarden garden = this.getGardenBlock(world, x, y, z);
      if (te != null && garden != null) {
         int baseY = this.getBaseBlockYCoord(world, x, y, z);
         AxisAlignedBB aabb = null;
         int[] var9 = garden.getSlotProfile().getPlantSlots();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            int slot = var9[var11];
            Block block = this.getPlantBlock(te, slot);
            if (block != null) {
               this.bindSlot(world, x, y, z, te, slot);

               try {
                  AxisAlignedBB sub = block.getSelectedBoundingBoxFromPool(world, x, y, z);
                  if (sub != null) {
                     float offsetX = garden.getSlotProfile().getPlantOffsetX(world, x, baseY, z, slot);
                     float offsetY = garden.getSlotProfile().getPlantOffsetY(world, x, baseY, z, slot);
                     float offsetZ = garden.getSlotProfile().getPlantOffsetZ(world, x, baseY, z, slot);
                     sub.offset((double)offsetX, (double)offsetY, (double)offsetZ);
                     if (aabb == null) {
                        aabb = sub;
                     } else {
                        aabb = aabb.func_111270_a(sub);
                     }
                  }
               } catch (Exception var21) {
               } finally {
                  this.unbindSlot(world, x, y, z, te);
               }
            }
         }

         if (aabb == null) {
            aabb = super.getSelectedBoundingBoxFromPool(world, x, y, z);
         }

         return aabb;
      } else {
         return super.getSelectedBoundingBoxFromPool(world, x, y, z);
      }
   }

   public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity colliding) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      BlockGarden garden = this.getGardenBlock(world, x, y, z);
      if (te != null && garden != null) {
         int baseY = this.getBaseBlockYCoord(world, x, y, z);
         int[] var11 = garden.getSlotProfile().getPlantSlots();
         int var12 = var11.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            int slot = var11[var13];
            Block block = this.getPlantBlock(te, slot);
            if (block != null) {
               this.bindSlot(world, x, y, z, te, slot);

               try {
                  AxisAlignedBB sub = block.getCollisionBoundingBoxFromPool(world, x, y, z);
                  if (sub != null) {
                     float offsetX = garden.getSlotProfile().getPlantOffsetX(world, x, baseY, z, slot);
                     float offsetY = garden.getSlotProfile().getPlantOffsetY(world, x, baseY, z, slot);
                     float offsetZ = garden.getSlotProfile().getPlantOffsetZ(world, x, baseY, z, slot);
                     sub.offset((double)offsetX, (double)offsetY, (double)offsetZ);
                     if (mask.intersectsWith(sub)) {
                        list.add(sub);
                     }
                  }
               } catch (Exception var23) {
               } finally {
                  this.unbindSlot(world, x, y, z, te);
               }
            }
         }

         if (list.isEmpty()) {
            super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
         }

      } else {
         super.addCollisionBoxesToList(world, x, y, z, mask, list, colliding);
      }
   }

   public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      BlockGarden garden = this.getGardenBlock(world, x, y, z);
      if (te != null && garden != null) {
         int baseY = this.getBaseBlockYCoord(world, x, y, z);
         MovingObjectPosition mop = null;
         int[] var11 = garden.getSlotProfile().getPlantSlots();
         int var12 = var11.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            int slot = var11[var13];
            Block block = this.getPlantBlock(te, slot);
            if (block != null) {
               this.bindSlot(world, x, y, z, te, slot);

               try {
                  float offsetX = garden.getSlotProfile().getPlantOffsetX(world, x, baseY, z, slot);
                  float offsetY = garden.getSlotProfile().getPlantOffsetY(world, x, baseY, z, slot);
                  float offsetZ = garden.getSlotProfile().getPlantOffsetZ(world, x, baseY, z, slot);
                  Vec3 slotStartVec = Vec3.createVectorHelper(startVec.xCoord - (double)offsetX, startVec.yCoord - (double)offsetY, startVec.zCoord - (double)offsetZ);
                  Vec3 slotEndVec = Vec3.createVectorHelper(endVec.xCoord - (double)offsetX, endVec.yCoord - (double)offsetY, endVec.zCoord - (double)offsetZ);
                  MovingObjectPosition sub = block.collisionRayTrace(world, x, y, z, slotStartVec, slotEndVec);
                  if (mop == null || slotStartVec.squareDistanceTo(mop.hitVec) > slotStartVec.squareDistanceTo(sub.hitVec)) {
                     mop = sub;
                  }
               } catch (Exception var25) {
               } finally {
                  this.unbindSlot(world, x, y, z, te);
               }
            }
         }

         return mop;
      } else {
         return super.collisionRayTrace(world, x, y, z, startVec, endVec);
      }
   }

   public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      if (te != null) {
         BlockGarden.validateBlockState(te);
      } else {
         world.setBlockToAir(x, y, z);
      }

   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vx, float vy, float vz) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      BlockGarden garden = this.getGardenBlock(world, x, y, z);
      if (te != null && garden != null) {
         boolean flag = false;
         int[] var13 = garden.getSlotProfile().getPlantSlots();
         int var14 = var13.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            int slot = var13[var15];
            Block block = this.getPlantBlock(te, slot);
            if (block != null) {
               this.bindSlot(world, x, y, z, te, slot);

               try {
                  flag |= block.onBlockActivated(world, x, y, z, player, side, vx, vy, vz);
               } catch (Exception var22) {
               } finally {
                  this.unbindSlot(world, x, y, z, te);
               }
            }
         }

         if (flag) {
            return true;
         } else {
            BlockGarden block = this.getGardenBlock(world, x, y, z);
            if (block != null) {
               y = this.getBaseBlockYCoord(world, x, y, z);
               return block.applyItemToGarden(world, x, y, z, player, (ItemStack)null);
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public boolean applyBonemeal(World world, int x, int y, int z) {
      BlockGarden block = this.getGardenBlock(world, x, y, z);
      if (block == null) {
         return false;
      } else {
         y = this.getBaseBlockYCoord(world, x, y, z);
         block.getTileEntity(world, x, y, z);
         boolean handled = false;
         int[] var8 = block.getSlotProfile().getPlantSlots();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            int slot = var8[var10];
            Iterator var12 = GardenCoreAPI.instance().getBonemealHandlers().iterator();

            while(var12.hasNext()) {
               IBonemealHandler handler = (IBonemealHandler)var12.next();
               if (handler.applyBonemeal(world, x, y, z, block, slot)) {
                  handled = true;
                  break;
               }
            }
         }

         return handled;
      }
   }

   public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer player) {
      super.onBlockHarvested(world, x, y, z, p_149681_5_, player);
      if (player.capabilities.isCreativeMode) {
         TileEntityGarden te = this.getGardenEntity(world, x, y, z);
         if (te != null) {
            te.clearPlantedContents();
         }
      }

   }

   public void breakBlock(World world, int x, int y, int z, Block block, int data) {
      if (this.hasValidUnderBlock(world, x, y, z) && !this.isApplyingBonemealTo(x, y, z)) {
         TileEntityGarden te = this.getGardenEntity(world, x, y, z);
         BlockGarden garden = this.getGardenBlock(world, x, y, z);
         if (te != null && block != null) {
            int[] var9 = garden.getSlotProfile().getPlantSlots();
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               int slot = var9[var11];
               ItemStack item = te.getPlantInSlot(slot);
               if (item != null) {
                  this.dropBlockAsItem(world, x, y, z, item);
               }
            }

            te.clearPlantedContents();
         }
      }

      world.notifyBlockOfNeighborChange(x, y + 1, z, block);
      world.notifyBlockOfNeighborChange(x, y - 1, z, block);
      if (!this.isApplyingBonemealTo(x, y, z) && world.getBlock(x, y - 1, z) == this) {
         world.setBlockToAir(x, y - 1, z);
      }

      super.breakBlock(world, x, y, z, block, data);
   }

   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList drops = new ArrayList();
      return drops;
   }

   public int getLightValue(IBlockAccess world, int x, int y, int z) {
      if (this.reeLightValue > 0) {
         return -1;
      } else {
         ++this.reeLightValue;
         int value = 0;
         TileEntityGarden te = this.getGardenEntity(world, x, y, z);
         BlockGarden garden = this.getGardenBlock(world, x, y, z);
         if (te != null && garden != null) {
            int[] var8 = garden.getSlotProfile().getPlantSlots();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               int slot = var8[var10];
               Block block = this.getPlantBlock(te, slot);
               if (block != null) {
                  this.bindSlot(te.getWorldObj(), x, y, z, te, slot);

                  try {
                     int sub = block.getLightValue(world, x, y, z);
                     if (sub == -1) {
                        sub = block.getLightValue();
                     }

                     if (sub == -1) {
                        sub = this.getLightValue();
                     }

                     if (sub > value) {
                        value = sub;
                     }
                  } catch (Exception var17) {
                  } finally {
                     this.unbindSlot(te.getWorldObj(), x, y, z, te);
                  }
               }
            }
         } else {
            value = super.getLightValue(world, x, y, z);
         }

         --this.reeLightValue;
         return value;
      }
   }

   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      BlockGarden garden = this.getGardenBlock(world, x, y, z);
      if (te != null && garden != null) {
         int[] var8 = garden.getSlotProfile().getPlantSlots();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            int slot = var8[var10];
            Block block = this.getPlantBlock(te, slot);
            if (block != null) {
               this.bindSlot(world, x, y, z, te, slot);

               try {
                  block.onEntityCollidedWithBlock(world, x, y, z, entity);
               } catch (Exception var17) {
               } finally {
                  this.unbindSlot(world, x, y, z, te);
               }
            }
         }

      }
   }

   public void randomDisplayTick(World world, int x, int y, int z, Random random) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      BlockGarden garden = this.getGardenBlock(world, x, y, z);
      if (te != null && garden != null) {
         int[] var8 = garden.getSlotProfile().getPlantSlots();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            int slot = var8[var10];
            Block block = this.getPlantBlock(te, slot);
            if (block != null) {
               this.bindSlot(world, x, y, z, te, slot);

               try {
                  block.randomDisplayTick(world, x, y, z, random);
               } catch (Exception var17) {
               } finally {
                  this.unbindSlot(world, x, y, z, te);
               }
            }
         }

      }
   }

   @SideOnly(Side.CLIENT)
   public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
      int slot = GardenCore.proxy.getClientBindingStack(this).getSlot();
      TileEntityGarden te = this.getGardenEntity(blockAccess, x, y, z);
      if (te != null && slot != -1) {
         Block block = this.getPlantBlockRestricted(te, slot);
         if (block == null) {
            return super.colorMultiplier(blockAccess, x, y, z);
         } else {
            try {
               return block.colorMultiplier(blockAccess, x, y, z);
            } catch (Exception var9) {
               return super.colorMultiplier(blockAccess, x, y, z);
            }
         }
      } else {
         return super.colorMultiplier(blockAccess, x, y, z);
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
      int slot = GardenCore.proxy.getClientBindingStack(this).getSlot();
      TileEntityGarden te = this.getGardenEntity(blockAccess, x, y, z);
      if (te != null && slot != -1) {
         Block block = this.getPlantBlockRestricted(te, slot);
         if (block == null) {
            return super.getIcon(blockAccess, x, y, z, side);
         } else {
            try {
               return block.getIcon(blockAccess, x, y, z, side);
            } catch (Exception var10) {
               return super.getIcon(blockAccess, x, y, z, side);
            }
         }
      } else {
         return super.getIcon(blockAccess, x, y, z, side);
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int data) {
      return this.transpIcon;
   }

   @SideOnly(Side.CLIENT)
   public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
      TileEntityGarden te = this.getGardenEntity(world, x, y, z);
      BlockGarden garden = this.getGardenBlock(world, x, y, z);
      if (te != null && garden != null) {
         int[] var9 = garden.getSlotProfile().getPlantSlots();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            int slot = var9[var11];
            Block block = this.getPlantBlock(te, slot);
            int blockData = this.getPlantData(te, slot);
            if (block != null) {
               this.bindSlot(world, x, y, z, te, slot);

               try {
                  byte count = 4;

                  for(int ix = 0; ix < count; ++ix) {
                     for(int iy = 0; iy < count; ++iy) {
                        for(int iz = 0; iz < count; ++iz) {
                           double xOff = (double)x + ((double)ix + 0.5D) / (double)count;
                           double yOff = (double)y + ((double)iy + 0.5D) / (double)count;
                           double zOff = (double)z + ((double)iz + 0.5D) / (double)count;
                           EntityDiggingFX fx = new EntityDiggingFX(world, xOff, yOff, zOff, xOff - (double)x - 0.5D, yOff - (double)y - 0.5D, zOff - (double)z - 0.5D, this, meta);
                           fx.setParticleIcon(block.getIcon(world.rand.nextInt(6), blockData));
                           effectRenderer.addEffect(fx.applyColourMultiplier(x, y, z));
                        }
                     }
                  }
               } catch (Exception var29) {
               } finally {
                  this.unbindSlot(world, x, y, z, te);
               }
            }
         }

         return true;
      } else {
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister iconRegister) {
      this.transpIcon = iconRegister.registerIcon("GardenCore:proxy_transp");
   }

   private boolean hasValidUnderBlock(IBlockAccess world, int x, int y, int z) {
      if (y == 0) {
         return false;
      } else {
         Block underBlock = world.getBlock(x, y - 1, z);
         return underBlock instanceof BlockGarden || underBlock instanceof IPlantProxy;
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

   public TileEntityGarden getGardenEntity(IBlockAccess world, int x, int y, int z) {
      y = this.getBaseBlockYCoord(world, x, y, z);
      TileEntity tileEntity = world.getTileEntity(x, y, z);
      return !(tileEntity instanceof TileEntityGarden) ? null : (TileEntityGarden)tileEntity;
   }

   public Block getPlantBlock(TileEntityGarden tileEntity, int slot) {
      ItemStack itemStack = tileEntity.getPlantInSlot(slot);
      return itemStack == null ? null : this.getPlantBlock(tileEntity, itemStack.getItem());
   }

   public Block getPlantBlockRestricted(TileEntityGarden tileEntity, int slot) {
      ItemStack itemStack = tileEntity.getStackInSlotIsolated(slot);
      return itemStack == null ? null : this.getPlantBlock(tileEntity, itemStack.getItem());
   }

   private Block getPlantBlock(TileEntityGarden tileEntity, Item item) {
      if (item == null) {
         return null;
      } else if (item instanceof IPlantable) {
         return ((IPlantable)item).getPlant(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
      } else {
         return item instanceof ItemBlock ? Block.getBlockFromItem(item) : null;
      }
   }

   public int getPlantData(TileEntityGarden tileEntity, int slot) {
      ItemStack itemStack = tileEntity.getPlantInSlot(slot);
      return itemStack == null ? 0 : itemStack.getItemDamage();
   }

   private void setPlantData(TileEntityGarden tileEntity, int slot, int data) {
      ItemStack itemStack = tileEntity.getPlantInSlot(slot);
      if (itemStack != null) {
         itemStack.setItemDamage(data);
         tileEntity.setInventorySlotContents(slot, itemStack);
      }

   }

   private boolean isApplyingBonemealTo(int x, int y, int z) {
      return this.applyingBonemeal;
   }
}
