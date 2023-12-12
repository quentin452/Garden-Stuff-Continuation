package com.jaquadro.minecraft.gardenapi.api;

public class GardenAPI {

    private static IGardenAPI instance;

    public static IGardenAPI instance() {
        if (instance == null) {
            try {
                Class classAPI = Class.forName("com.jaquadro.minecraft.gardenapi.internal.Api");
                instance = (IGardenAPI) classAPI.getField("instance")
                    .get((Object) null);
            } catch (Throwable var1) {
                return null;
            }
        }

        return instance;
    }
}
