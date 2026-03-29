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
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
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
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
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
    private static SpriteId getSideSprite(Optional<Item> item) {
        return null;
    }

    @Shadow
    @Final
    private SpriteGetter sprites;

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
    private ModelPart rightSide;

    @Shadow
    @Final
    private ModelPart leftSide;

    @Shadow
    @Final
    private ModelPart backSide;

    @Shadow
    @Final
    private ModelPart frontSide;

    @Override
    public DecoratedPotRenderState createRenderState() {
        return new StackDecoratedPotRenderState();
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/DecoratedPotRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", at = @At("TAIL"))
    public void extractStackDecorations(DecoratedPotBlockEntity blockEntity, DecoratedPotRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress, CallbackInfo ci) {
        StackPotDecorations stackPotDecorations = ((StackPotDecorationsHolder) blockEntity).sherdsapi$getDecorations();
        if (stackPotDecorations != null) {
            ((StackDecoratedPotRenderState) state).stackDecorations = stackPotDecorations;
        }
    }

    @WrapOperation(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/DecoratedPotRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/DecoratedPotRenderer;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IILnet/minecraft/world/level/block/entity/PotDecorations;I)V"))
    private void submitStackDecorations(DecoratedPotRenderer renderer, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, PotDecorations decorations, int outlineColor, Operation<Void> original, @Local(argsOnly = true) DecoratedPotRenderState state) {
        StackPotDecorations stackDecorations = ((StackDecoratedPotRenderState) state).stackDecorations;
        if (stackDecorations != null) {
            sherdsapi$submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, stackDecorations, outlineColor);
        } else {
            original.call(renderer, poseStack, submitNodeCollector, lightCoords, overlayCoords, decorations, outlineColor);
        }
    }

    @Override
    public void sherdsapi$submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, StackPotDecorations decorations, int outlineColor) {
        RenderType renderType = Sheets.DECORATED_POT_BASE.renderType(RenderTypes::entitySolid);
        TextureAtlasSprite sprite = this.sprites.get(Sheets.DECORATED_POT_BASE);
        submitNodeCollector.submitModelPart(this.neck, poseStack, renderType, lightCoords, overlayCoords, sprite, false, false, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.top, poseStack, renderType, lightCoords, overlayCoords, sprite, false, false, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.bottom, poseStack, renderType, lightCoords, overlayCoords, sprite, false, false, -1, null, outlineColor);
        SpriteId frontSprite = sherdsapi$getSideSprite(decorations.front());
        submitNodeCollector.submitModelPart(
                this.frontSide,
                poseStack,
                frontSprite.renderType(RenderTypes::entitySolid),
                lightCoords,
                overlayCoords,
                this.sprites.get(frontSprite),
                false,
                false,
                -1,
                null,
                outlineColor);
        SpriteId backSprite = sherdsapi$getSideSprite(decorations.back());
        submitNodeCollector.submitModelPart(
                this.backSide,
                poseStack,
                backSprite.renderType(RenderTypes::entitySolid),
                lightCoords,
                overlayCoords,
                this.sprites.get(backSprite),
                false,
                false,
                -1,
                null,
                outlineColor);
        SpriteId leftSprite = sherdsapi$getSideSprite(decorations.left());
        submitNodeCollector.submitModelPart(
                this.leftSide,
                poseStack,
                leftSprite.renderType(RenderTypes::entitySolid),
                lightCoords,
                overlayCoords,
                this.sprites.get(leftSprite),
                false,
                false,
                -1,
                null,
                outlineColor);
        SpriteId rightSprite = sherdsapi$getSideSprite(decorations.right());
        submitNodeCollector.submitModelPart(
                this.rightSide,
                poseStack,
                rightSprite.renderType(RenderTypes::entitySolid),
                lightCoords,
                overlayCoords,
                this.sprites.get(rightSprite),
                false,
                false,
                -1,
                null,
                outlineColor);
    }

    @Unique
    private static final Map<Identifier, SpriteId> sherdsapi$CUSTOM_SPRITES = new Object2ObjectOpenHashMap<>();

    @Unique
    private static SpriteId sherdsapi$getDecoratedPotSprite(Identifier id) {
        return sherdsapi$CUSTOM_SPRITES.computeIfAbsent(id, Sheets.DECORATED_POT_MAPPER::apply);
    }

    @Unique
    private static SpriteId sherdsapi$getSideSprite(Optional<ItemStack> optional) {
        return optional.map(stack -> {
            Identifier id = stack.get(SherdsApiDataComponents.SHERD_PATTERN.get());
            if (id != null) {
                return sherdsapi$getDecoratedPotSprite(id);
            } else {
                return getSideSprite(Optional.of(stack.getItem()));
            }
        }).orElse(Sheets.DECORATED_POT_SIDE);
    }
}
