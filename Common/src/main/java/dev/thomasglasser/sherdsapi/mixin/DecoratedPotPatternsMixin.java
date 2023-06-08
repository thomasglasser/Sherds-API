package dev.thomasglasser.sherdsapi.mixin;

import dev.thomasglasser.sherdsapi.impl.PotterySherdRegistryImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin
{
    @Inject(method = "getResourceKey", at = @At("RETURN"), cancellable = true)
    private static void sherdsapi_checkAddedPatterns(Item item, CallbackInfoReturnable<ResourceKey<String>> cir)
    {
        if (cir.getReturnValue() == null)
            cir.setReturnValue(PotterySherdRegistryImpl.getFor(item));
    }
}
