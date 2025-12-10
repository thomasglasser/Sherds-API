package dev.thomasglasser.sherdsapi.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import dev.thomasglasser.sherdsapi.impl.StackPotRenderer;
import dev.thomasglasser.sherdsapi.impl.client.renderer.blockentity.state.StackDecoratedPotRenderState;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotRenderer.class)
public abstract class DecoratedPotRendererMixin implements BlockEntityRenderer<DecoratedPotBlockEntity, DecoratedPotRenderState>, StackPotRenderer {
    @Shadow
    private static Material getSideMaterial(Optional<Item> item) {
        return null;
    }

    @Shadow
    @Final
    private MaterialSet materials;

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

    @Override
    public DecoratedPotRenderState createRenderState() {
        return new StackDecoratedPotRenderState();
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/DecoratedPotRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", at = @At("TAIL"))
    public void extractRenderState(DecoratedPotBlockEntity blockEntity, DecoratedPotRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress, CallbackInfo ci) {
        if (blockEntity.getDecorations() == PotDecorations.EMPTY && blockEntity instanceof StackPotDecorationsHolder holder) {
            StackPotDecorations stackPotDecorations = holder.sherdsapi$getDecorations();
            if (stackPotDecorations != null) {
                ((StackDecoratedPotRenderState) (renderState)).stackDecorations = stackPotDecorations;
            }
        }
    }

    @WrapOperation(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/DecoratedPotRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/DecoratedPotRenderer;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IILnet/minecraft/world/level/block/entity/PotDecorations;I)V"))
    private void submit(DecoratedPotRenderer instance, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, PotDecorations decorations, int outlineColor, Operation<Void> original, @Local(argsOnly = true) DecoratedPotRenderState renderState) {
        StackPotDecorations stackDecorations = ((StackDecoratedPotRenderState) renderState).stackDecorations;
        if (stackDecorations != null) {
            sherdsapi$submit(poseStack, nodeCollector, packedLight, packedOverlay, stackDecorations, outlineColor);
        } else {
            original.call(instance, poseStack, nodeCollector, packedLight, packedOverlay, decorations, outlineColor);
        }
    }

    @Override
    public void sherdsapi$submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, StackPotDecorations decorations, int outlineColor) {
        RenderType rendertype = Sheets.DECORATED_POT_BASE.renderType(RenderTypes::entitySolid);
        TextureAtlasSprite textureatlassprite = this.materials.get(Sheets.DECORATED_POT_BASE);
        nodeCollector.submitModelPart(this.neck, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, false, false, -1, null, outlineColor);
        nodeCollector.submitModelPart(this.top, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, false, false, -1, null, outlineColor);
        nodeCollector.submitModelPart(this.bottom, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, false, false, -1, null, outlineColor);
        Material material = sherdsapi$getSideMaterial(decorations.front());
        nodeCollector.submitModelPart(this.frontSide, poseStack, material.renderType(RenderTypes::entitySolid), packedLight, packedOverlay, this.materials.get(material), false, false, -1, null, outlineColor);
        Material material1 = sherdsapi$getSideMaterial(decorations.back());
        nodeCollector.submitModelPart(this.backSide, poseStack, material1.renderType(RenderTypes::entitySolid), packedLight, packedOverlay, this.materials.get(material1), false, false, -1, null, outlineColor);
        Material material2 = sherdsapi$getSideMaterial(decorations.left());
        nodeCollector.submitModelPart(this.leftSide, poseStack, material2.renderType(RenderTypes::entitySolid), packedLight, packedOverlay, this.materials.get(material2), false, false, -1, null, outlineColor);
        Material material3 = sherdsapi$getSideMaterial(decorations.right());
        nodeCollector.submitModelPart(this.rightSide, poseStack, material3.renderType(RenderTypes::entitySolid), packedLight, packedOverlay, this.materials.get(material3), false, false, -1, null, outlineColor);
    }

    @Unique
    private static final Map<Identifier, Material> CUSTOM_MATERIALS = new HashMap<>();

    @Unique
    private static Material sherdsapi$getDecoratedPotMaterial(Identifier id) {
        return CUSTOM_MATERIALS.computeIfAbsent(id, Sheets.DECORATED_POT_MAPPER::apply);
    }

    @Unique
    private static Material sherdsapi$getSideMaterial(Optional<ItemStack> optional) {
        if (optional.isPresent()) {
            ItemStack stack = optional.get();
            Identifier id = stack.get(SherdsApiDataComponents.SHERD_PATTERN.get());
            if (id != null) {
                return sherdsapi$getDecoratedPotMaterial(id);
            } else {
                return getSideMaterial(Optional.of(stack.getItem()));
            }
        }

        return Sheets.DECORATED_POT_SIDE;
    }
}
