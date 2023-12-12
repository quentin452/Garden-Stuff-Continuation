package com.jaquadro.minecraft.gardentrees.integration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;

import com.jaquadro.minecraft.gardencore.api.SaplingRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeFactory;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeRegistry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class NaturaIntegration {

    public static final String MOD_ID = "Natura";

    public static void init() {
        if (Loader.isModLoaded("Natura")) {
            Map saplingBank1 = new HashMap();
            saplingBank1.put("small_oak", new int[] { 1, 3, 4, 6, 7 });
            saplingBank1.put("large_spruce", new int[] { 0 });
            saplingBank1.put("small_shrub", new int[] { 2 });
            Map saplingBank2 = new HashMap();
            saplingBank2.put("small_oak", new int[] { 0, 1, 3 });
            saplingBank2.put("tall_small_oak", new int[] { 2 });
            saplingBank2.put("small_willow", new int[] { 4 });
            Map banks = new HashMap();
            banks.put(GameRegistry.findItem("Natura", "florasapling"), saplingBank1);
            banks.put(GameRegistry.findItem("Natura", "Rare Sapling"), saplingBank2);
            SaplingRegistry saplingReg = SaplingRegistry.instance();
            Iterator var4 = banks.entrySet()
                .iterator();

            label45: while (var4.hasNext()) {
                Entry entry = (Entry) var4.next();
                Item sapling = (Item) entry.getKey();
                Iterator var7 = ((Map) entry.getValue()).entrySet()
                    .iterator();

                while (true) {
                    Entry bankEntry;
                    OrnamentalTreeFactory factory;
                    do {
                        if (!var7.hasNext()) {
                            continue label45;
                        }

                        bankEntry = (Entry) var7.next();
                        factory = OrnamentalTreeRegistry.getTree((String) bankEntry.getKey());
                    } while (factory == null);

                    int[] var10 = (int[]) bankEntry.getValue();
                    int var11 = var10.length;

                    for (int var12 = 0; var12 < var11; ++var12) {
                        int i = var10[var12];
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
