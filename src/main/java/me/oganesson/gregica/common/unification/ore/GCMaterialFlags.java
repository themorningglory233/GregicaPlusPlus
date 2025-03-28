package me.oganesson.gregica.common.unification.ore;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

public class GCMaterialFlags {

    public static final MaterialFlag GENERATE_MILLED = new MaterialFlag.Builder("generate_milled")
            .requireProps(PropertyKey.ORE)
            .build();
}