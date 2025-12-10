package dev.thomasglasser.sherdsapi.impl.data.models;

import dev.thomasglasser.sherdsapi.SherdsApi;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.client.renderer.special.StackSensitiveDecoratedPotSpecialRenderer;
import dev.thomasglasser.tommylib.api.data.models.ExtendedModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.client.renderer.special.DecoratedPotSpecialRenderer;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class SherdsApiModelProvider extends ExtendedModelProvider {
    public SherdsApiModelProvider(PackOutput output) {
        super(output, SherdsApi.MOD_NAMESPACE);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModelGenerators, ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.itemModelOutput.accept(Items.DECORATED_POT, ItemModelUtils.conditional(
                new HasComponent(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), false),
                ItemModelUtils.specialModel(ModelLocationUtils.getModelLocation(Items.DECORATED_POT), new StackSensitiveDecoratedPotSpecialRenderer.Unbaked()),
                ItemModelUtils.specialModel(ModelLocationUtils.getModelLocation(Items.DECORATED_POT), new DecoratedPotSpecialRenderer.Unbaked())));
    }
}
