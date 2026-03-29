package dev.thomasglasser.sherdsapi.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.DecoratedPotRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotRecipe.class)
public abstract class DecoratedPotRecipeMixin {
    @Shadow
    private static ItemStack back(CraftingInput input) {
        return null;
    }

    @Shadow
    private static ItemStack left(CraftingInput input) {
        return null;
    }

    @Shadow
    private static ItemStack right(CraftingInput input) {
        return null;
    }

    @Shadow
    private static ItemStack front(CraftingInput input) {
        return null;
    }

    @Shadow
    @Final
    private ItemStackTemplate result;

    @WrapOperation(method = "matches(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/world/level/Level;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/Ingredient;test(Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean allowPatterned(Ingredient pattern, ItemStack stack, Operation<Boolean> original) {
        return stack.has(SherdsApiDataComponents.SHERD_PATTERN.get()) || original.call(pattern, stack);
    }

    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void assembleStackDecorations(CraftingInput input, CallbackInfoReturnable<ItemStack> cir) {
        if (input.items().stream().anyMatch(stack -> stack.has(SherdsApiDataComponents.SHERD_PATTERN.get()))) {
            StackPotDecorations decorations = new StackPotDecorations(back(input).copyWithCount(1), left(input).copyWithCount(1), right(input).copyWithCount(1), front(input).copyWithCount(1));
            DataComponentPatch components = DataComponentPatch.builder().set(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), decorations).build();
            cir.setReturnValue(this.result.apply(components));
        }
    }
}
