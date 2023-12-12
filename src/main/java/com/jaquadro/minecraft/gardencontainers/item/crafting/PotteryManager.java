package com.jaquadro.minecraft.gardencontainers.item.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

import com.jaquadro.minecraft.gardencontainers.GardenContainers;
import com.jaquadro.minecraft.gardencontainers.core.ModBlocks;
import com.jaquadro.minecraft.gardencontainers.core.ModItems;

public class PotteryManager {

    private static final PotteryManager instance = new PotteryManager();
    private List patternList = new ArrayList();
    private Map targetList = new HashMap();

    public static PotteryManager instance() {
        return instance;
    }

    private PotteryManager() {
        for (int i = 1; i < 256; ++i) {
            if (GardenContainers.config.hasPattern(i)) {
                this.registerPattern(new ItemStack(ModItems.potteryPattern, 1, i));
            }
        }

        this.registerTarget(new ItemStack(ModBlocks.largePot, 1, 1));
    }

    public void registerPattern(ItemStack itemStack) {
        if (itemStack != null) {
            this.patternList.add(itemStack);
        }

    }

    public void registerTarget(ItemStack itemStack) {
        if (itemStack != null) {
            this.registerTarget(itemStack, 8);
        }

    }

    public void registerTarget(ItemStack itemStack, int patternDataShift) {
        this.targetList.put(itemStack, patternDataShift);
    }

    public boolean isRegisteredPattern(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        } else {
            Iterator var2 = this.patternList.iterator();

            ItemStack item;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                item = (ItemStack) var2.next();
            } while (item.getItem() != itemStack.getItem() || item.getItemDamage() != itemStack.getItemDamage());

            return true;
        }
    }

    public boolean isRegisteredTarget(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        } else {
            Iterator var2 = this.targetList.keySet()
                .iterator();

            ItemStack item;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                item = (ItemStack) var2.next();
            } while (item.getItem() != itemStack.getItem() || item.getItemDamage() != itemStack.getItemDamage());

            return true;
        }
    }

    public ItemStack getStampResult(ItemStack pattern, ItemStack target) {
        if (target != null && this.isRegisteredPattern(pattern)) {
            Iterator var3 = this.targetList.entrySet()
                .iterator();

            Entry entry;
            ItemStack item;
            do {
                if (!var3.hasNext()) {
                    return null;
                }

                entry = (Entry) var3.next();
                item = (ItemStack) entry.getKey();
            } while (item.getItem() != target.getItem() || item.getItemDamage() != target.getItemDamage());

            ItemStack result = target.copy();
            result.stackSize = 1;
            result.setItemDamage(target.getItemDamage() | pattern.getItemDamage() << (Integer) entry.getValue());
            return result;
        } else {
            return null;
        }
    }
}
