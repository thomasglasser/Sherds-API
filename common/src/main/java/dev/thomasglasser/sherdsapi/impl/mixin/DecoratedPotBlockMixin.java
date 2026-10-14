package dev.thomasglasser.sherdsapi.impl.mixin;

import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {
    @Inject(method = "appendHoverText", at = @At("RETURN"))
    private void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag, CallbackInfo ci) {
        StackPotDecorations decorations = stack.get(SherdsApiDataComponents.STACK_POT_DECORATIONS.get());
        if (decorations != null) {
            tooltip.add(CommonComponents.EMPTY);
            Stream.of(decorations.front(), decorations.left(), decorations.right(), decorations.back())
                    .forEach(sherd -> tooltip.add(sherd.orElseGet(Items.BRICK::getDefaultInstance).getHoverName().plainCopy().withStyle(ChatFormatting.GRAY)));
        }
    }

    @Inject(method = { "lambda$getDrops$0", "method_49815" }, at = @At("HEAD"), cancellable = true)
    private static void overrideDecorationsDrops(DecoratedPotBlockEntity entity, Consumer<ItemStack> output, CallbackInfo ci) {
        StackPotDecorations decorations = StackPotDecorationsHolder.of(entity).sherdsapi$getDecorations();
        if (decorations != null) {
            for (ItemStack stack : decorations.ordered())
                output.accept(stack);
            ci.cancel();
        }
    }
}
