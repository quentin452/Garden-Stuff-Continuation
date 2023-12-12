package com.jaquadro.minecraft.gardencore.config;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigManager {
   private final Configuration config;
   public boolean enableCompostBonemeal;
   public double compostBonemealStrength;
   public boolean enableTilledSoilGrowthBonus;

   public ConfigManager(File file) {
      this.config = new Configuration(file);
      Property propEnableCompostBonemeal = this.config.get("general", "enableCompostBonemeal", true);
      propEnableCompostBonemeal.comment = "Allows compost trigger plant growth like bonemeal.";
      this.enableCompostBonemeal = propEnableCompostBonemeal.getBoolean();
      Property propCompostBonemealStrength = this.config.get("general", "compostBonemealStrength", 0.5D);
      propCompostBonemealStrength.comment = "The probability that compost will succeed when used as bonemeal relative to bonemeal.";
      this.compostBonemealStrength = propCompostBonemealStrength.getDouble();
      Property propEnableTilledSoilGrowthBonus = this.config.get("general", "enableTilledSoilGrowthBonus", true).setRequiresMcRestart(true);
      propEnableTilledSoilGrowthBonus.comment = "Allows tilled garden soil to advance crop growth more quickly.  Enables random ticks.";
      this.enableTilledSoilGrowthBonus = propEnableTilledSoilGrowthBonus.getBoolean();
      this.config.save();
   }
}
