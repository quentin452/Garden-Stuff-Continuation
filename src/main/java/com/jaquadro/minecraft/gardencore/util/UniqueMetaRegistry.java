package com.jaquadro.minecraft.gardencore.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UniqueMetaRegistry {

    private Map registry = new HashMap();

    public void register(UniqueMetaIdentifier id, Object entry) {
        this.registry.put(id, entry);
    }

    public Object getEntry(UniqueMetaIdentifier id) {
        if (id == null) {
            return null;
        } else if (this.registry.containsKey(id)) {
            return this.registry.get(id);
        } else {
            if (id.meta != 32767) {
                id = new UniqueMetaIdentifier(id.modId, id.name);
                if (this.registry.containsKey(id)) {
                    return this.registry.get(id);
                }

                if (!id.name.isEmpty()) {
                    id = new UniqueMetaIdentifier(id.modId);
                    if (this.registry.containsKey(id)) {
                        return this.registry.get(id);
                    }
                }
            }

            return null;
        }
    }

    public void remove(UniqueMetaIdentifier id) {
        if (id != null) {
            this.registry.remove(id);
        }

    }

    public Set entrySet() {
        return this.registry.entrySet();
    }

    public void clear() {
        this.registry.clear();
    }
}
