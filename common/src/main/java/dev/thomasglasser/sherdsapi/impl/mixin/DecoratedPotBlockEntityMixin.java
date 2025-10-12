package dev.thomasglasser.sherdsapi.impl.mixin;

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
    private @Nullable StackPotDecorations sherdsapi$decorations = null;

    private DecoratedPotBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveAdditional(ValueOutput output, CallbackInfo ci) {
        if (sherdsapi$decorations != null) {
            output.store("stack_sherds", StackPotDecorations.CODEC, this.sherdsapi$decorations);
        }
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void loadAdditional(ValueInput input, CallbackInfo ci) {
        this.sherdsapi$decorations = input.read("stack_sherds", StackPotDecorations.CODEC).orElse(null);
    }

    @Inject(method = "collectImplicitComponents", at = @At("TAIL"))
    private void collectImplicitComponents(DataComponentMap.Builder components, CallbackInfo ci) {
        components.set(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), this.sherdsapi$decorations);
    }

    @Inject(method = "applyImplicitComponents", at = @At("TAIL"))
    private void applyImplicitComponents(DataComponentGetter dataComponentGetter, CallbackInfo ci) {
        this.sherdsapi$decorations = dataComponentGetter.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
    }

    @Inject(method = "removeComponentsFromTag", at = @At("TAIL"))
    private void removeComponentsFromTag(ValueOutput output, CallbackInfo ci) {
        output.discard("stack_sherds");
    }

    @Override
    public StackPotDecorations sherdsapi$getDecorations() {
        return sherdsapi$decorations;
    }
}
