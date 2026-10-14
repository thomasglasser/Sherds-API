package dev.thomasglasser.sherdsapi.impl.mixin;

import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotBlockEntity.class)
public abstract class DecoratedPotBlockEntityMixin extends BlockEntity implements StackPotDecorationsHolder {
    // Kept in 1.21.1 to avoid breaking existing worlds, changes in future versions
    @Unique
    private static final String sherdsapi$TAG_SHERDS = "patterns";

    @Unique
    private @Nullable StackPotDecorations sherdsapi$decorations = null;

    private DecoratedPotBlockEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (sherdsapi$decorations != null)
            sherdsapi$decorations.save(tag, sherdsapi$TAG_SHERDS);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void loadStackDecorations(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        sherdsapi$decorations = StackPotDecorations.load(tag, sherdsapi$TAG_SHERDS);
    }

    @Inject(method = "collectImplicitComponents", at = @At("TAIL"))
    private void collectStackDecorations(DataComponentMap.Builder components, CallbackInfo ci) {
        components.set(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), sherdsapi$decorations);
    }

    @Inject(method = "applyImplicitComponents", at = @At("TAIL"))
    private void applyStackDecorations(DataComponentInput componentInput, CallbackInfo ci) {
        sherdsapi$decorations = componentInput.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
    }

    @Inject(method = "removeComponentsFromTag", at = @At("TAIL"))
    private void removeStackDecorations(CompoundTag tag, CallbackInfo ci) {
        tag.remove(sherdsapi$TAG_SHERDS);
    }

    @Override
    public @Nullable StackPotDecorations sherdsapi$getDecorations() {
        return sherdsapi$decorations;
    }
}
