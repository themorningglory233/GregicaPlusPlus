package me.oganesson.gregica.common.tileentities.mte.multipart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.client.utils.PipelineUtil;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import me.oganesson.gregica.api.GCValues;
import me.oganesson.gregica.api.capability.GCCapabilities;
import me.oganesson.gregica.api.capability.quantum.IQubitContainer;
import me.oganesson.gregica.api.capability.quantum.QubitContainerHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MTEQubitHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IQubitContainer> {

    private final boolean isExportHatch;
    private final int parallel;
    private final IQubitContainer qubitContainer;

    public MTEQubitHatch(ResourceLocation metaTileEntityId, int tier, int parallel, boolean isExportHatch) {
        super(metaTileEntityId, tier);
        this.parallel = parallel;
        this.isExportHatch = isExportHatch;
        if (isExportHatch) {
            this.qubitContainer = QubitContainerHandler.emitterContainer(this, (long) GCValues.QUBIT[tier] * parallel, GCValues.QUBIT[tier], parallel);
        } else {
            this.qubitContainer = QubitContainerHandler.receiverContainer(this, (long) GCValues.QUBIT[tier] * parallel, GCValues.QUBIT[tier], parallel);
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity paramIGregTechTileEntity) {
        return new MTEQubitHatch(metaTileEntityId, getTier(), parallel, isExportHatch);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            SimpleOverlayRenderer renderer = isExportHatch ? Textures.ENERGY_OUT_MULTI : Textures.ENERGY_IN_MULTI;
            renderer.renderSided(getFrontFacing(), renderState, translation, PipelineUtil.color(pipeline, GTValues.VC[getTier()]));
        }
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        MultiblockControllerBase controller = this.getController();
        return controller == null ? Textures.VOLTAGE_CASINGS[GTValues.ZPM] : controller.getBaseTexture(this);
    }

    @Override
    public MultiblockAbility<IQubitContainer> getAbility() {
        return isExportHatch ? GCCapabilities.OUTPUT_QBIT : GCCapabilities.INPUT_QBIT;
    }

    @Override
    public void registerAbilities(List<IQubitContainer> abilityList) {
        abilityList.add(qubitContainer);
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        String tierName = GTValues.VN[getTier()];

        if (isExportHatch) {
            tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_out", qubitContainer.getOutputQubit(), tierName));
            tooltip.add(I18n.format("gregtech.universal.tooltip.amperage_out_till", qubitContainer.getOutputParallel()));
        } else {
            tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", qubitContainer.getInputQubit(), tierName));
            tooltip.add(I18n.format("gregtech.universal.tooltip.amperage_in_till", qubitContainer.getInputParallel()));
        }
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", qubitContainer.getQubitCapacity()));
    }
}