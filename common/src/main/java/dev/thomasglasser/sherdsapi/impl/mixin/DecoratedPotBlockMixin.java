package dev.thomasglasser.sherdsapi.impl.mixin;

import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {
    @Inject(method = { "lambda$getDrops$0", "method_49815" }, at = @At("HEAD"), cancellable = true)
    private static void getDrops(DecoratedPotBlockEntity decoratedpotblockentity, Consumer<ItemStack> p_330132_, CallbackInfo ci) {
        if (decoratedpotblockentity.getDecorations() == PotDecorations.EMPTY) {
            StackPotDecorations decorations = ((StackPotDecorationsHolder) decoratedpotblockentity).sherdsapi$getDecorations();
            if (decorations != null) {
                for (ItemStack item : decorations.ordered()) {
                    p_330132_.accept(item);
                }
            }
            ci.cancel();
        }
    }
}
