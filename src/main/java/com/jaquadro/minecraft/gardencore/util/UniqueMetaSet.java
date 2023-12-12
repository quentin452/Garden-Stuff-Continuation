package com.jaquadro.minecraft.gardencore.util;

import java.util.HashSet;
import java.util.Set;

public class UniqueMetaSet {

    private Set registry = new HashSet();

    public void register(UniqueMetaIdentifier id) {
        this.registry.add(id);
    }

    public boolean contains(UniqueMetaIdentifier id) {
        if (this.registry.contains(id)) {
            return true;
        } else {
            if (id.meta != 32767) {
                id = new UniqueMetaIdentifier(id.modId, id.name, 32767);
                if (this.registry.contains(id)) {
                    return true;
                }
            }

            return false;
        }
    }
}
