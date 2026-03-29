package dev.thomasglasser.sherdsapi.impl.mixin;

import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {
    @Inject(method = "lambda$getDrops$0", at = @At("HEAD"), cancellable = true)
    private static void overrideDecorationsDrops(DecoratedPotBlockEntity entity, Consumer<ItemStack> output, CallbackInfo ci) {
        StackPotDecorations decorations = ((StackPotDecorationsHolder) entity).sherdsapi$getDecorations();
        if (decorations != null) {
            for (ItemStack stack : decorations.ordered()) {
                output.accept(stack);
            }
            ci.cancel();
        }
    }
}
