package com.jaquadro.minecraft.gardentrees.world.gen.feature;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.Random;

public class WorldGenCandelilla extends WorldGenerator implements IWorldGenerator {
   Block plantBlock;

   public WorldGenCandelilla(Block block) {
      this.plantBlock = block;
   }

   public boolean generate(World world, Random rand, int x, int y, int z) {
      do {
         Block block = world.getBlock(x, y, z);
         if (!block.isLeaves(world, x, y, z) && !block.isAir(world, x, y, z)) {
            break;
         }

         --y;
      } while(y > 0);

      int range = 5;

      for(int l = 0; l < 32; ++l) {
         int x1 = x + rand.nextInt(range) - rand.nextInt(range);
         int y1 = y + rand.nextInt(4) - rand.nextInt(4);
         int z1 = z + rand.nextInt(range) - rand.nextInt(range);
         int level = 1 + rand.nextInt(7);
         if (world.isAirBlock(x1, y1, z1) && (!world.provider.hasNoSky || y1 < 255) && this.plantBlock.canBlockStay(world, x1, y1, z1)) {
            world.setBlock(x1, y1, z1, this.plantBlock, level, 2);
         }
      }

      return true;
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
      int x = chunkX * 16;
      int z = chunkZ * 16;
      BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
      if (!BiomeDictionary.isBiomeOfType(biome, Type.COLD) && !BiomeDictionary.isBiomeOfType(biome, Type.NETHER) && !BiomeDictionary.isBiomeOfType(biome, Type.WET) && !BiomeDictionary.isBiomeOfType(biome, Type.WASTELAND) && !BiomeDictionary.isBiomeOfType(biome, Type.SNOWY)) {
         if (BiomeDictionary.isBiomeOfType(biome, Type.SANDY)) {
            if (random.nextInt(10) <= 0) {
               this.generate(world, random, x, world.getActualHeight() - 1, z);
            }
         }
      }
   }
}
