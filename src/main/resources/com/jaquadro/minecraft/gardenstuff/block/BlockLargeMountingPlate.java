package com.jaquadro.minecraft.gardenstuff.block;

import com.jaquadro.minecraft.gardencore.core.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockLargeMountingPlate extends Block {
   public BlockLargeMountingPlate(String blockName) {
      super(Material.iron);
      this.setBlockName(blockName);
      this.setHardness(2.5F);
      this.setResistance(5.0F);
      this.setStepSound(Block.soundTypeMetal);
      this.setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setBlockTextureName("GardenStuff:iron_baseplate_4");
      this.setCreativeTab(ModCreativeTabs.tabGardenCore);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }
}
