package me.oganesson.gregica.common.recipes.recipemap;


import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.Recipe;
import me.oganesson.gregica.api.capability.quantum.IQubitContainer;
import me.oganesson.gregica.api.capability.quantum.InputQubitProperty;
import me.oganesson.gregica.api.capability.quantum.QubitRecipeMapMultiblockController;
import net.minecraft.nbt.NBTTagCompound;

public class QubitProducerRecipeLogic extends MultiblockRecipeLogic {

    private int recipeOutputQubit;

    public QubitProducerRecipeLogic(RecipeMapMultiblockController metaTileEntity) {
        super(metaTileEntity);
        setAllowOverclocking(false);
    }

    public IQubitContainer getOutputQubitContainer() {
        QubitRecipeMapMultiblockController controller = (QubitRecipeMapMultiblockController) metaTileEntity;
        return controller.getOutputQubitContainer();
    }

    @Override
    protected void completeRecipe() {
        super.completeRecipe();
        this.recipeOutputQubit = 0;
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        this.recipeOutputQubit = recipe.getProperty(InputQubitProperty.getInstance(), 0);
    }

    @Override
    protected void updateRecipeProgress() {
        if (getOutputQubitContainer().getQubitCanBeInserted() < recipeOutputQubit) {
            return;
        }
        boolean drawEnergy = drawEnergy(recipeEUt, false);
        if (drawEnergy || (recipeEUt < 0)) {
            getOutputQubitContainer().addQubit(recipeOutputQubit);
            if (++progressTime >= maxProgressTime) {
                completeRecipe();
            }
        } else if (recipeEUt > 0) {
            //only set hasNotEnoughEnergy if this recipe is consuming recipe
            //generators always have enough energy
            this.hasNotEnoughEnergy = true;
            progressTime = 0;
        }
    }


    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        if (progressTime > 0) {
            compound.setInteger("RecipeQubit", this.recipeOutputQubit);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        super.deserializeNBT(compound);
        this.isActive = false;
        if (progressTime > 0) {
            this.isActive = true;
            this.recipeOutputQubit = compound.getInteger("RecipeQubit");
        }
    }

}