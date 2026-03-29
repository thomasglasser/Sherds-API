package dev.thomasglasser.sherdsapi.impl.mixin;

import dev.thomasglasser.sherdsapi.api.SherdsApiConstants;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotBlockEntity.class)
public abstract class DecoratedPotBlockEntityMixin extends BlockEntity implements StackPotDecorationsHolder {
    @Unique
    private static final String sherdsapi$TAG_SHERDS = SherdsApiConstants.MOD_ID + "_sherds";

    @Unique
    private @Nullable StackPotDecorations sherdsapi$decorations = null;

    private DecoratedPotBlockEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveStackDecorations(ValueOutput output, CallbackInfo ci) {
        if (sherdsapi$decorations != null) {
            output.store(sherdsapi$TAG_SHERDS, StackPotDecorations.CODEC, this.sherdsapi$decorations);
        }
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void loadStackDecorations(ValueInput input, CallbackInfo ci) {
        this.sherdsapi$decorations = input.read(sherdsapi$TAG_SHERDS, StackPotDecorations.CODEC).orElse(null);
    }

    @Inject(method = "collectImplicitComponents", at = @At("TAIL"))
    private void collectStackDecorations(DataComponentMap.Builder components, CallbackInfo ci) {
        components.set(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), this.sherdsapi$decorations);
    }

    @Inject(method = "applyImplicitComponents", at = @At("TAIL"))
    private void applyStackDecorations(DataComponentGetter components, CallbackInfo ci) {
        this.sherdsapi$decorations = components.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
    }

    @Inject(method = "removeComponentsFromTag", at = @At("TAIL"))
    private void removeStackDecorations(ValueOutput output, CallbackInfo ci) {
        output.discard(sherdsapi$TAG_SHERDS);
    }

    @Override
    public StackPotDecorations sherdsapi$getDecorations() {
        return sherdsapi$decorations;
    }
}
