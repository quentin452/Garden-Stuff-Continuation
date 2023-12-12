package com.jaquadro.minecraft.gardenapi.api.machine;

public class StandardCompostMaterial implements ICompostMaterial {

    private int decompTime = 150;
    private float yield = 0.125F;

    public StandardCompostMaterial() {}

    public StandardCompostMaterial(int decompTime, float yield) {
        this.decompTime = decompTime;
        this.yield = yield;
    }

    public int getDecomposeTime() {
        return this.decompTime;
    }

    public float getCompostYield() {
        return this.yield;
    }
}
