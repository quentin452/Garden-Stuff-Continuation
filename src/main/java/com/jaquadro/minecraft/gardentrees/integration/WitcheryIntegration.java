package com.jaquadro.minecraft.gardentrees.integration;

import com.jaquadro.minecraft.gardencore.api.SaplingRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeFactory;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WitcheryIntegration {

    public static final String MOD_ID = "witchery";

    public static void init() {
        if (Loader.isModLoaded("witchery")) {
            Map saplingBank1 = new HashMap();
            saplingBank1.put("small_oak", new int[] { 0, 1, 2 });
            Map banks = new HashMap();
            banks.put(Item.getItemFromBlock(GameRegistry.findBlock("witchery", "witchsapling")), saplingBank1);
            SaplingRegistry saplingReg = SaplingRegistry.instance();
            Iterator var3 = banks.entrySet()
                .iterator();

            label45: while (var3.hasNext()) {
                Entry entry = (Entry) var3.next();
                Item sapling = (Item) entry.getKey();
                Iterator var6 = ((Map) entry.getValue()).entrySet()
                    .iterator();

                while (true) {
                    Entry bankEntry;
                    OrnamentalTreeFactory factory;
                    do {
                        if (!var6.hasNext()) {
                            continue label45;
                        }

                        bankEntry = (Entry) var6.next();
                        factory = OrnamentalTreeRegistry.getTree((String) bankEntry.getKey());
                    } while (factory == null);

                    int[] var9 = (int[]) bankEntry.getValue();
                    int var10 = var9.length;

                    for (int var11 = 0; var11 < var10; ++var11) {
                        int i = var9[var11];
                        UniqueMetaIdentifier woodBlock = saplingReg.getWoodForSapling(sapling, i);
                        UniqueMetaIdentifier leafBlock = saplingReg.getLeavesForSapling(sapling, i);
                        if (woodBlock != null || leafBlock != null) {
                            saplingReg.putExtendedData(
                                sapling,
                                i,
                                "sm_generator",
                                factory.create(
                                    woodBlock.getBlock(),
                                    woodBlock.meta,
                                    leafBlock.getBlock(),
                                    leafBlock.meta));
                        }
                    }
                }
            }

        }
    }
}
