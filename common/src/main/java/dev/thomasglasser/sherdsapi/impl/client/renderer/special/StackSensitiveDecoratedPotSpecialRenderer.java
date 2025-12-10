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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

public class StackSensitiveDecoratedPotSpecialRenderer implements SpecialModelRenderer<StackPotDecorations> {
    private final StackPotRenderer stackPotRenderer;

    public StackSensitiveDecoratedPotSpecialRenderer(StackPotRenderer stackPotRenderer) {
        this.stackPotRenderer = stackPotRenderer;
    }

    @Nullable
    public StackPotDecorations extractArgument(ItemStack stack) {
        return stack.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
    }

    public void submit(@Nullable StackPotDecorations argument, ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        this.stackPotRenderer.sherdsapi$submit(poseStack, nodeCollector, packedLight, packedOverlay, argument, outlineColor);
    }

    public void getExtents(Consumer<Vector3fc> consumer) {
        this.stackPotRenderer.getExtents(consumer);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        public SpecialModelRenderer<?> bake(BakingContext context) {
            return new StackSensitiveDecoratedPotSpecialRenderer((StackPotRenderer) new DecoratedPotRenderer(context));
        }
    }
}
