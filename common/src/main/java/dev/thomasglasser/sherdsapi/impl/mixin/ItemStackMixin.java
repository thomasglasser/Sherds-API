package dev.thomasglasser.sherdsapi.impl.mixin;

import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> component, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag);

    @Inject(method = "addToTooltip", at = @At(value = "HEAD"))
    private <T extends TooltipProvider> void addToTooltip(DataComponentType<T> component, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag, CallbackInfo ci) {
        if (component == DataComponents.POT_DECORATIONS)
            addToTooltip(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), context, display, consumer, flag);
    }
}
