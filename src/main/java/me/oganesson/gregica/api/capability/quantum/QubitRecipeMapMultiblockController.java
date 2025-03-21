package me.oganesson.gregica.api.capability.quantum;

import com.google.common.collect.Lists;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import me.oganesson.gregica.api.capability.GCCapabilities;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public abstract class QubitRecipeMapMultiblockController extends RecipeMapMultiblockController {

    protected IQubitContainer inputQubit;
    protected IQubitContainer outputQubit;

    public QubitRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
    }

    public IQubitContainer getInputQubitContainer() {
        return inputQubit;
    }

    public IQubitContainer getOutputQubitContainer() {
        return outputQubit;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
    }

    protected void initializeAbilities() {
        super.initializeAbilities();
        this.inputQubit = new QubitContainerList(getAbilities(GCCapabilities.INPUT_QBIT));
        this.outputQubit = new QubitContainerList(getAbilities(GCCapabilities.OUTPUT_QBIT));
    }

    private void resetTileAbilities() {
        this.inputQubit = new QubitContainerList(Lists.newArrayList());
        this.outputQubit = new QubitContainerList(Lists.newArrayList());
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (recipeMapWorkable instanceof QubitConsumeRecipeLogic && !((QubitConsumeRecipeLogic) recipeMapWorkable).isHasEnoughQubit()) {
            textList.add((new TextComponentTranslation("gregica.multiblock.not_enough_qubit")).setStyle((new Style()).setColor(TextFormatting.RED)));
        }
    }
}