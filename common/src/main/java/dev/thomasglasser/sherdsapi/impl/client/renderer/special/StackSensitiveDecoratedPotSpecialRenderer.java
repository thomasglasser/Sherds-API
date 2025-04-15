package dev.thomasglasser.sherdsapi.impl.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class StackSensitiveDecoratedPotSpecialRenderer implements SpecialModelRenderer<StackPotDecorations> {
    private final StackPotRenderer stackPotRenderer;

    public StackSensitiveDecoratedPotSpecialRenderer(StackPotRenderer stackPotRenderer) {
        this.stackPotRenderer = stackPotRenderer;
    }

    @Override
    public void render(@Nullable StackPotDecorations patterns, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
        this.stackPotRenderer.sherdsapi$render(poseStack, bufferSource, packedLight, packedOverlay, patterns);
    }

    @Override
    public @Nullable StackPotDecorations extractArgument(ItemStack stack) {
        return stack.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<StackSensitiveDecoratedPotSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public MapCodec<StackSensitiveDecoratedPotSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            return new StackSensitiveDecoratedPotSpecialRenderer((StackPotRenderer) new DecoratedPotRenderer(entityModelSet));
        }
    }
}
