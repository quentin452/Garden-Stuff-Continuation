package com.jaquadro.minecraft.gardentrees.world.gen;

import com.jaquadro.minecraft.gardencore.api.WoodRegistry;
import com.jaquadro.minecraft.gardentrees.block.tile.TileEntityWoodProxy;
import com.jaquadro.minecraft.gardentrees.core.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public abstract class WorldGenOrnamentalTree extends WorldGenAbstractTree {
   private final Block wood;
   private final Block leaves;
   private final int metaWood;
   private final int metaLeaves;
   protected static final String PAT_1X1 = "T";
   protected static final String PAT_3X3 = "XXXXTXXXX";
   protected static final String PAT_3X3PLUS = " X XTX X ";
   protected static final String PAT_3X3IPLUS = "X X T X X";
   protected static final String PAT_3X3UNBAL = "0X XTX X ";
   protected static final String PAT_3X3OPT = " 0 1X2 3 ";
   protected static final String PAT_5X5 = "XXXXXXXXXXXXTXXXXXXXXXXXX";
   protected static final String PAT_5X5PLUS = "  X   XXX XXTXX XXX   X  ";
   protected static final String PAT_5X5PLUS2 = " XXX XXXXXXXTXXXXXXX XXX ";
   protected static final String PAT_5X5PLUS2N = " X X XXXXX XTX XXXXX X X ";
   protected static final String PAT_5X5PLUS2T = " XXX XXTXXXTTTXXXTXX XXX ";
   protected static final String PAT_5X5UNBAL = " 00  0XXX 0XTX  XXX      ";

   protected static String transform(String pattern, WorldGenOrnamentalTree.LayerType type) {
      return transform(pattern, type, 0);
   }

   protected static String transform(String pattern, WorldGenOrnamentalTree.LayerType type, int option) {
      int groups = countOptionGroups(pattern);

      for(int i = 0; i < groups; ++i) {
         if ((option >> i & 1) == 0) {
            pattern = pattern.replace((char)(48 + i), ' ');
         } else {
            pattern = pattern.replace((char)(48 + i), 'X');
         }
      }

      if (type == WorldGenOrnamentalTree.LayerType.LEAF) {
         pattern = pattern.replace('T', 'X');
      } else if (type == WorldGenOrnamentalTree.LayerType.TRUNK) {
         pattern = pattern.replace('X', 'T');
      }

      boolean flipH = (option >>> 30 & 1) == 1;
      boolean flipV = (option >>> 31 & 1) == 1;
      if (flipH && flipV) {
         pattern = (new StringBuilder(pattern)).reverse().toString();
      } else {
         int dim;
         StringBuilder sb;
         int y;
         int base;
         int base2;
         if (flipH) {
            dim = getPatternDim(pattern);
            sb = new StringBuilder(pattern);

            for(y = 0; y < dim; ++y) {
               base = y * dim;

               for(base2 = 0; base2 < dim; ++base2) {
                  sb.setCharAt(base + base2, pattern.charAt(base + dim - base2 - 1));
               }
            }

            pattern = sb.toString();
         } else if (flipV) {
            dim = getPatternDim(pattern);
            sb = new StringBuilder(pattern);

            for(y = 0; y < dim; ++y) {
               base = y * dim;
               base2 = (dim - y - 1) * dim;

               for(int x = 0; x < dim; ++x) {
                  sb.setCharAt(base + x, pattern.charAt(base2 + x));
               }
            }

            pattern = sb.toString();
         }
      }

      return pattern;
   }

   private static int countOptionGroups(String pattern) {
      char high = '/';
      int i = 0;

      for(int n = pattern.length(); i < n; ++i) {
         char c = pattern.charAt(i);
         if (c >= '0' && c <= '9' && c > high) {
            high = c;
         }
      }

      return high - 48 + 1;
   }

   public WorldGenOrnamentalTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
      super(blockNotify);
      this.wood = wood;
      this.leaves = leaves;
      this.metaWood = metaWood;
      this.metaLeaves = metaLeaves;
   }

   public boolean generate(World world, Random rand, int x, int y, int z) {
      int height = this.getNeighborAirCount(world, x, y - 1, z) >= 6 ? 5 : 6;
      int trunkHeight = height - 4;
      this.prepare(world, rand, x, y, z, trunkHeight);
      if (!this.canGenerate(world, x, y, z, height)) {
         return false;
      } else {
         this.generateCanopy(world, x, y, z, trunkHeight);
         this.generateTrunk(world, x, y, z, trunkHeight);
         return true;
      }
   }

   private int getNeighborAirCount(World world, int x, int y, int z) {
      int count = 0;
      int count = count + (world.isAirBlock(x - 1, y, z - 1) ? 1 : 0);
      count += world.isAirBlock(x - 1, y, z) ? 1 : 0;
      count += world.isAirBlock(x - 1, y, z + 1) ? 1 : 0;
      count += world.isAirBlock(x, y, z - 1) ? 1 : 0;
      count += world.isAirBlock(x, y, z + 1) ? 1 : 0;
      count += world.isAirBlock(x + 1, y, z - 1) ? 1 : 0;
      count += world.isAirBlock(x + 1, y, z) ? 1 : 0;
      count += world.isAirBlock(x + 1, y, z + 1) ? 1 : 0;
      return count;
   }

   private boolean canGenerate(World world, int x, int y, int z, int height) {
      int trunkHeight = height - 4;
      if (y + height > 256) {
         return false;
      } else {
         return this.canGenerateTrunk(world, x, y, z, trunkHeight) && this.canGenerateCanopy(world, x, y, z, trunkHeight);
      }
   }

   protected void prepare(World world, Random rand, int x, int y, int z, int trunkHeight) {
   }

   protected boolean canGenerateTrunk(World world, int x, int y, int z, int trunkHeight) {
      for(int iy = y; iy < y + trunkHeight; ++iy) {
         if (!this.isReplaceable(world, x, iy, z)) {
            return false;
         }
      }

      return true;
   }

   protected abstract boolean canGenerateCanopy(World var1, int var2, int var3, int var4, int var5);

   protected void generateTrunk(World world, int x, int y, int z, int trunkHeight) {
      for(int iy = y; iy < y + trunkHeight + 1; ++iy) {
         this.generateBlock(world, x, iy, z, this.wood, this.metaWood);
      }

   }

   protected abstract void generateCanopy(World var1, int var2, int var3, int var4, int var5);

   private static int getPatternDim(String pattern) {
      return (int)Math.floor(Math.sqrt((double)pattern.length()));
   }

   protected boolean canGeneratePattern(World world, int x, int y, int z, String pattern) {
      int dim = getPatternDim(pattern) / 2;

      for(int ix = x - dim; ix <= x + dim; ++ix) {
         for(int iz = z - dim; iz <= z + dim; ++iz) {
            int index = (iz - z + dim) * (dim * 2 + 1) + ix - x + dim;
            if ((pattern.charAt(index) == 'X' || pattern.charAt(index) == 'T') && !this.isReplaceable(world, ix, y, iz)) {
               return false;
            }
         }
      }

      return true;
   }

   protected void generatePattern(World world, int x, int y, int z, String pattern) {
      int dim = getPatternDim(pattern) / 2;

      for(int ix = x - dim; ix <= x + dim; ++ix) {
         for(int iz = z - dim; iz <= z + dim; ++iz) {
            int index = (iz - z + dim) * (dim * 2 + 1) + ix - x + dim;
            if (pattern.charAt(index) == 'X') {
               this.generateBlock(world, ix, y, iz);
            } else if (pattern.charAt(index) == 'T') {
               this.generateTrunk(world, ix, y, iz);
            }
         }
      }

   }

   private void generateBlock(World world, int x, int y, int z) {
      Block block = world.getBlock(x, y, z);
      if (block.isAir(world, x, y, z) || block.isLeaves(world, x, y, z)) {
         this.setBlockAndNotifyAdequately(world, x, y, z, this.leaves, this.metaLeaves);
      }

   }

   private void generateTrunk(World world, int x, int y, int z) {
      this.generateBlock(world, x, y, z, this.wood, this.metaWood);
   }

   private void generateBlock(World world, int x, int y, int z, Block block, int meta) {
      Block existingBlock = world.getBlock(x, y, z);
      if (existingBlock.isAir(world, x, y, z) || existingBlock.isLeaves(world, x, y, z)) {
         if (block == Blocks.log) {
            this.setBlockAndNotifyAdequately(world, x, y, z, ModBlocks.thinLog, meta % 4);
         } else if (block == Blocks.log2) {
            this.setBlockAndNotifyAdequately(world, x, y, z, ModBlocks.thinLog, meta % 4 + 4);
         } else if (WoodRegistry.instance().contains(block, meta)) {
            this.setBlockAndNotifyAdequately(world, x, y, z, block, 0);
            TileEntityWoodProxy te = new TileEntityWoodProxy();
            te.setProtoBlock(block, meta);
            this.setBlockAndNotifyAdequately(world, x, y, z, ModBlocks.thinLog, 0);
            world.setTileEntity(x, y, z, te);
         } else {
            this.setBlockAndNotifyAdequately(world, x, y, z, block, meta);
         }
      }

   }

   protected boolean isReplaceable(World world, int x, int y, int z) {
      world.getBlock(x, y, z);
      return super.isReplaceable(world, x, y, z);
   }

   protected static enum LayerType {
      LEAF,
      TRUNK,
      CORE;
   }
}
