package me.oganesson.gregica.api.unification.materials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

import static gregtech.api.unification.material.Materials.Zinc;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static me.oganesson.gregica.api.unification.materials.GCYSMaterials.RoastedSphalerite;
import static me.oganesson.gregica.api.unification.materials.GCYSMaterials.ZincRichSphalerite;

public class GCYSThirdDegreeMaterials {

    public static void init() {
        ZincRichSphalerite = new Material.Builder(9001, "zinc_rich_sphalerite")
                .dust()
                .color(0xC3AC8F)
                .iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Zinc, 2, RoastedSphalerite, 3)
                .build()
                .setFormula("Zn2(GaGeO2)", true);
    }
}
