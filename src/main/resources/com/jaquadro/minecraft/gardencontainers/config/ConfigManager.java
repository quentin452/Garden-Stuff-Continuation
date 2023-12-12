package com.jaquadro.minecraft.gardencontainers.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigManager {
   private static final String CAT_PATTERNS = "1_patterns";
   private static final String CAT_SETTINGS = "2_pattern_settings";
   private final Configuration config;
   private ConfigCategory categoryPatterns;
   private ConfigCategory categoryPatternSettings;
   private ConfigCategory defaultPatternSettings;
   private List genLocations = new ArrayList();
   private List genRarity = new ArrayList();
   private PatternConfig defaultPattern;
   private PatternConfig[] patterns = new PatternConfig[256];
   private int patternCount;
   public boolean enableVillagerTrading;

   public ConfigManager(File file) {
      this.config = new Configuration(file);
      Property propEnableVillagerTrading = this.config.get("general", "enableVillagerStampTrading", true);
      propEnableVillagerTrading.comment = "Allows some villagers to buy and sell pattern stamps.";
      this.enableVillagerTrading = propEnableVillagerTrading.getBoolean();
      boolean firstGen = !this.config.hasCategory("1_patterns");
      this.categoryPatterns = this.config.getCategory("1_patterns");
      this.categoryPatterns.setComment("Patterns are additional textures that can be overlaid on top of large pots, both normal and colored.\nFor each pattern defined, a corresponding 'stamp' item is registered.  The stamp is used with the\npottery table to apply patterns to raw clay pots.\n\nThis mod can support up to 255 registered patterns.  To add a new pattern, create a new entry in the\nconfig below using the form:\n\n  S:pattern.#=texture_name; A Name\n\nWhere # is an id between 1 and 255 inclusive.\nPlace a corresponding texture_name.png file into the mod's jar file in assets/modularpots/textures/blocks.\nTo further control aspects of the pattern, seeing the next section, pattern_settings.\n\nNote: Future versions of this mod may add new patterns.  If you haven't made any changes to this\nconfiguration, simply delete it and let it regenerate.  Otherwise visit the mod's development thread\non Minecraft Forums to see what's changed.");
      this.categoryPatternSettings = this.config.getCategory("2_pattern_settings");
      this.categoryPatternSettings.setComment("Specifies all the attributes for patterns.  Attributes control how patterns can be found in the world.\nIn the future, they might control other aspects, such as how patterns are rendered.\n\nBy default, all patterns will take their attributes from the 'pattern_default' subcategory.  To\ncustomize some or all attributes for a pattern, create a new subcategory modeled like this:\n\n  pattern_# {\n      I:weight=5\n  }\n\nThe S:pattern_gen option controls what kinds of dungeon chests the pattern's stamp item will appear in, and the\nrarity of the item appearing.  The location and rarity are separated by a comma (,), and multiple locations\nare separated with a semicolon (;).  Rarity is a value between 1 and 100, with 1 being very rare.  Golden\napples and diamond horse armor also have a rarity of 1.  Most vanilla items have a rarity of 10.  The valid\nlocation strings are:\n\n  mineshaftCorridor, pyramidDesertChest, pyramidJungleChest, strongholdCorridor, strongholdLibrary,\n  strongholdCrossing, villageBlacksmith, dungeonChest");
      this.populateDefaultPattern();
      if (firstGen) {
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.1", "large_pot_1; Serpent");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.2", "large_pot_2; Lattice");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.3", "large_pot_3; Offset Squares");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.4", "large_pot_4; Inset");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.5", "large_pot_5; Turtle");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.6", "large_pot_6; Creeper");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.7", "large_pot_7; Freewheel");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.8", "large_pot_8; Creepy Castle");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.9", "large_pot_9; Savannah");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.10", "large_pot_10; Scales");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.11", "large_pot_11; Growth");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.12", "large_pot_12; Fern");
         this.config.get(this.categoryPatterns.getQualifiedName(), "pattern.13", "large_pot_13; Diamond");
         this.config.getCategory("2_pattern_settings.pattern_2");
         this.config.get("2_pattern_settings.pattern_2", "weight", 8);
      }

      this.config.save();

      for(int i = 1; i < 256; ++i) {
         if (this.config.hasKey(this.categoryPatterns.getQualifiedName(), "pattern." + i)) {
            String entry = this.config.get(this.categoryPatterns.getQualifiedName(), "pattern." + i, "").getString();
            String[] parts = entry.split("[ ]*;[ ]*");
            String overlay = parts[0];
            String name = parts.length > 1 ? parts[1] : null;
            this.patterns[i] = new PatternConfig(i, overlay, name);
            if (this.config.hasCategory("2_pattern_settings.pattern_" + i)) {
               this.parsePatternAttribs(this.patterns[i], "2_pattern_settings.pattern_" + i);
            } else {
               if (this.patterns[i].getName() == null) {
                  this.patterns[i].setName(this.defaultPattern.getName());
               }

               this.patterns[i].setWeight(this.defaultPattern.getWeight());
            }

            ++this.patternCount;
         }
      }

   }

   private void populateDefaultPattern() {
      this.defaultPattern = new PatternConfig(0, "", "");
      this.defaultPatternSettings = this.config.getCategory("2_pattern_settings.pattern_default");
      String name = this.config.get(this.defaultPatternSettings.getQualifiedName(), "name", "Unknown").getString();
      int weight = this.config.get(this.defaultPatternSettings.getQualifiedName(), "weight", 5).getInt();
      this.defaultPattern.setName(name);
      this.defaultPattern.setWeight(weight);
      String gen = this.config.get("2_pattern_settings", "pattern_gen", "dungeonChest, 1; mineshaftCorridor, 1").getString();
      this.parseGenString(gen);
   }

   private void parsePatternAttribs(PatternConfig pattern, String category) {
      if (this.config.hasKey(category, "name")) {
         String name = this.config.get(this.defaultPatternSettings.getQualifiedName(), "name", "Unknown").getString();
         pattern.setName(name);
      } else if (pattern.getName() == null) {
         pattern.setName(this.defaultPattern.getName());
      }

      if (this.config.hasKey(category, "weight")) {
         int weight = this.config.get(this.defaultPatternSettings.getQualifiedName(), "weight", 1).getInt();
         pattern.setWeight(weight);
      } else {
         pattern.setWeight(this.defaultPattern.getWeight());
      }

   }

   private void parseGenString(String genString) {
      String[] strParts = genString.split("[ ]*;[ ]*");

      for(int i = 0; i < strParts.length; ++i) {
         String[] locParts = strParts[i].split("[ ]*,[ ]*");
         if (locParts.length == 2) {
            String location = this.mapGenLocation(locParts[0]);
            int rarity = Integer.parseInt(locParts[1]);
            if (location != null) {
               this.genLocations.add(location);
               this.genRarity.add(rarity);
            }
         }
      }

   }

   private String mapGenLocation(String location) {
      if (location.equals("mineshaftCorridor")) {
         return "mineshaftCorridor";
      } else if (location.equals("pyramidDesertChest")) {
         return "pyramidDesertyChest";
      } else if (location.equals("pyramidJungleChest")) {
         return "pyramidJungleChest";
      } else if (location.equals("strongholdCorridor")) {
         return "strongholdCorridor";
      } else if (location.equals("strongholdLibrary")) {
         return "strongholdLibrary";
      } else if (location.equals("strongholdCrossing")) {
         return "strongholdCrossing";
      } else if (location.equals("villageBlacksmith")) {
         return "villageBlacksmith";
      } else {
         return location.equals("dungeonChest") ? "dungeonChest" : null;
      }
   }

   public boolean hasPattern(int index) {
      return this.patterns[index] != null;
   }

   public PatternConfig getPattern(int index) {
      return this.patterns[index];
   }

   public int getPatternCount() {
      return this.patternCount;
   }

   public int getPatternLocationCount() {
      return this.genLocations.size();
   }

   public String getPatternLocation(int index) {
      return (String)this.genLocations.get(index);
   }

   public int getPatternLocationRarity(int index) {
      return (Integer)this.genRarity.get(index);
   }
}
