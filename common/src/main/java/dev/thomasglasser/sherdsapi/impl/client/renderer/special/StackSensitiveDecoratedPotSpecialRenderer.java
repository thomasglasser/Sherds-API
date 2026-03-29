package dev.thomasglasser.sherdsapi.impl.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotRenderer;
import java.util.function.Consumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

public class StackSensitiveDecoratedPotSpecialRenderer implements SpecialModelRenderer<StackPotDecorations> {
    private final StackPotRenderer stackPotRenderer;

    public StackSensitiveDecoratedPotSpecialRenderer(StackPotRenderer stackPotRenderer) {
        this.stackPotRenderer = stackPotRenderer;
    }

    @Override
    @Nullable
    public StackPotDecorations extractArgument(ItemStack stack) {
        return stack.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
    }

    @Override
    public void submit(@Nullable StackPotDecorations decorations, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        this.stackPotRenderer.sherdsapi$submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, decorations, outlineColor);
    }

    public void getExtents(Consumer<Vector3fc> output) {
        this.stackPotRenderer.getExtents(output);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<StackPotDecorations> {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        public StackSensitiveDecoratedPotSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
            return new StackSensitiveDecoratedPotSpecialRenderer((StackPotRenderer) new DecoratedPotRenderer(context));
        }
    }
}
