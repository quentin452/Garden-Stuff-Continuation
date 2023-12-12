package com.jaquadro.minecraft.gardentrees.block;

import com.jaquadro.minecraft.gardentrees.core.ModCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockGTSapling extends BlockSapling {
   public static final String[] types = new String[]{"pine", "swamp", "tallbirch"};
   @SideOnly(Side.CLIENT)
   private static IIcon[] icons;

   public BlockGTSapling(String name) {
      this.setBlockName(name);
      this.setBlockTextureName("sapling");
      this.setStepSound(Block.soundTypeGrass);
      this.setCreativeTab(ModCreativeTabs.tabGardenTrees);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      meta &= 7;
      return icons[MathHelper.clamp_int(meta, 0, types.length - 1)];
   }

   public void func_149878_d(World world, int x, int y, int z, Random random) {
      if (TerrainGen.saplingGrowTree(world, random, x, y, z)) {
         int id = world.getBlockMetadata(x, y, z) & 7;
         WorldGenerator generator = null;
         switch(id) {
         case 0:
            generator = new WorldGenTaiga1() {
               protected void setBlockAndNotifyAdequately(World world, int x, int y, int z, Block block, int meta) {
                  world.setBlock(x, y, z, block, meta, 3);
               }
            };
            break;
         case 1:
            generator = new WorldGenSwamp() {
               protected void setBlockAndNotifyAdequately(World world, int x, int y, int z, Block block, int meta) {
                  world.setBlock(x, y, z, block, meta, 3);
               }
            };
            break;
         case 2:
            generator = new WorldGenForest(true, true);
            break;
         default:
            return;
         }

         world.setBlock(x, y, z, Blocks.air, 0, 4);
         if (!((WorldGenerator)generator).generate(world, random, x, y, z)) {
            world.setBlock(x, y, z, this, id, 4);
         }

      }
   }

   public int damageDropped(int meta) {
      return MathHelper.clamp_int(meta & 7, 0, types.length);
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
      list.add(new ItemStack(item, 1, 0));
      list.add(new ItemStack(item, 1, 1));
      list.add(new ItemStack(item, 1, 2));
   }

   public void registerBlockIcons(IIconRegister register) {
      icons = new IIcon[types.length];

      for(int i = 0; i < types.length; ++i) {
         icons[i] = register.registerIcon("GardenTrees:" + this.getTextureName() + "_" + types[i]);
      }

   }
}
