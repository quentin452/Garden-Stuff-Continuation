package com.jaquadro.minecraft.gardencommon.integration;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;

import com.jaquadro.minecraft.gardencore.api.SaplingRegistry;
import com.jaquadro.minecraft.gardencore.util.UniqueMetaIdentifier;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeFactory;
import com.jaquadro.minecraft.gardentrees.world.gen.OrnamentalTreeRegistry;

public class SmallTreeRegistryHelper {

    public static void registerSaplings(Map banks) {
        SaplingRegistry saplingReg = SaplingRegistry.instance();
        Iterator var2 = banks.entrySet()
            .iterator();

        label41: while (var2.hasNext()) {
            Entry entry = (Entry) var2.next();
            Item sapling = (Item) entry.getKey();
            Iterator var5 = ((Map) entry.getValue()).entrySet()
                .iterator();

            while (true) {
                Entry bankEntry;
                OrnamentalTreeFactory factory;
                do {
                    if (!var5.hasNext()) {
                        continue label41;
                    }

                    bankEntry = (Entry) var5.next();
                    factory = OrnamentalTreeRegistry.getTree((String) bankEntry.getKey());
                } while (factory == null);

                int[] var8 = (int[]) bankEntry.getValue();
                int var9 = var8.length;

                for (int var10 = 0; var10 < var9; ++var10) {
                    int i = var8[var10];
                    UniqueMetaIdentifier woodBlock = saplingReg.getWoodForSapling(sapling, i);
                    UniqueMetaIdentifier leafBlock = saplingReg.getLeavesForSapling(sapling, i);
                    if (woodBlock != null || leafBlock != null) {
                        saplingReg.putExtendedData(
                            sapling,
                            i,
                            "sm_generator",
                            factory.create(woodBlock.getBlock(), woodBlock.meta, leafBlock.getBlock(), leafBlock.meta));
                    }
                }
            }
        }

    }
}
