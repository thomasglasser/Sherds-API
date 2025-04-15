package dev.thomasglasser.sherdsapi.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import dev.thomasglasser.sherdsapi.impl.StackPotRenderer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DecoratedPotRenderer.class)
public abstract class DecoratedPotRendererMixin implements StackPotRenderer {
    @Shadow
    @Final
    private ModelPart neck;

    @Shadow
    @Final
    private ModelPart top;

    @Shadow
    @Final
    private ModelPart bottom;

    @Shadow
    @Final
    private ModelPart frontSide;
    @Shadow
    @Final
    private ModelPart backSide;
    @Shadow
    @Final
    private ModelPart leftSide;
    @Shadow
    @Final
    private ModelPart rightSide;

    @Shadow
    protected abstract void renderSide(ModelPart modelPart, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Material material);

    @Shadow
    private static Material getSideMaterial(Optional<Item> item) {
        return null;
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/DecoratedPotRenderer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/level/block/entity/PotDecorations;)V"))
    private void render(DecoratedPotRenderer instance, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, PotDecorations decorations, Operation<Void> original, @Local(argsOnly = true) DecoratedPotBlockEntity blockEntity) {
        if (blockEntity.getDecorations() == PotDecorations.EMPTY && blockEntity instanceof StackPotDecorationsHolder holder) {
            StackPotDecorations stackPotDecorations = holder.sherdsapi$getDecorations();
            if (stackPotDecorations != null) {
                sherdsapi$render(poseStack, bufferSource, packedLight, packedOverlay, stackPotDecorations);
            } else {
                original.call(instance, poseStack, bufferSource, packedLight, packedOverlay, decorations);
            }
        } else {
            original.call(instance, poseStack, bufferSource, packedLight, packedOverlay, decorations);
        }
    }

    @Override
    public void sherdsapi$render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, StackPotDecorations decorations) {
        VertexConsumer vertexConsumer = Sheets.DECORATED_POT_BASE.buffer(bufferSource, RenderType::entitySolid);
        neck.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        top.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        renderSide(frontSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.front()));
        renderSide(backSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.back()));
        renderSide(leftSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.left()));
        renderSide(rightSide, poseStack, bufferSource, packedLight, packedOverlay, sherdsapi$getSideMaterial(decorations.right()));
    }

    @Unique
    private static final Map<ResourceLocation, Material> CUSTOM_MATERIALS = new HashMap<>();

    @Unique
    private static Material sherdsapi$getDecoratedPotMaterial(ResourceLocation key) {
        return CUSTOM_MATERIALS.computeIfAbsent(key, Sheets.DECORATED_POT_MAPPER::apply);
    }

    @Unique
    private static Material sherdsapi$getSideMaterial(Optional<ItemStack> optional) {
        if (optional.isPresent()) {
            ItemStack stack = optional.get();
            if (stack.has(SherdsApiDataComponents.SHERD_PATTERN.get())) {
                return sherdsapi$getDecoratedPotMaterial(stack.get(SherdsApiDataComponents.SHERD_PATTERN.get()));
            } else {
                return getSideMaterial(Optional.of(stack.getItem()));
            }
        }

        return Sheets.DECORATED_POT_SIDE;
    }
}
