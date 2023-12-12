package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.block.tile.TileEntityBlockMateralProxy;
import com.jaquadro.minecraft.gardenstuff.block.tile.TileEntityLatticeMetal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLatticeMetal extends BlockLattice {
   public static final String[] subNames = new String[]{"iron", "rust", "wrought_iron", "moss"};
   @SideOnly(Side.CLIENT)
   private IIcon[] icons;

   public BlockLatticeMetal(String blockName) {
      super(blockName, Material.iron);
      this.setStepSound(Block.soundTypeMetal);
      this.setBlockTextureName("GardenStuff:lattice");
   }

   public TileEntity createNewTileEntity(World world, int meta) {
      return new TileEntityLatticeMetal();
   }

   public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
      for(int i = 0; i < subNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      Block protoBlock = TileEntityLatticeMetal.instance.getBlockFromComposedMetadata(meta);
      return protoBlock != null && protoBlock != this ? protoBlock.getIcon(side, TileEntityLatticeMetal.instance.getMetaFromComposedMetadata(meta)) : this.icons[meta % this.icons.length];
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
      TileEntityBlockMateralProxy te = this.getTileEntity(blockAccess, x, y, z);
      if (te != null && te.getProtoBlock() != null) {
         Block protoBlock = te.getProtoBlock();
         if (protoBlock == null) {
            protoBlock = this;
         }

         return ((Block)protoBlock).getIcon(side, te.getProtoMeta());
      } else {
         return super.getIcon(blockAccess, x, y, z, side);
      }
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      this.icons = new IIcon[subNames.length];

      for(int i = 0; i < this.icons.length; ++i) {
         this.icons[i] = register.registerIcon(this.getTextureName() + "_" + subNames[i]);
      }

   }
}
