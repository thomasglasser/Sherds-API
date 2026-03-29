package dev.thomasglasser.sherdsapi.impl;

import com.mojang.serialization.Codec;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record StackPotDecorations(Optional<ItemStack> back, Optional<ItemStack> left, Optional<ItemStack> right, Optional<ItemStack> front) implements TooltipProvider {

    public static final Codec<StackPotDecorations> CODEC = ItemStack.OPTIONAL_CODEC
            .sizeLimitedListOf(4)
            .xmap(StackPotDecorations::new, StackPotDecorations::ordered);
    public static final StreamCodec<RegistryFriendlyByteBuf, StackPotDecorations> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC
            .apply(ByteBufCodecs.list(4))
            .map(StackPotDecorations::new, StackPotDecorations::ordered);
    private StackPotDecorations(List<ItemStack> stacks) {
        this(getStack(stacks, 0), getStack(stacks, 1), getStack(stacks, 2), getStack(stacks, 3));
    }

    public StackPotDecorations(ItemStack back, ItemStack left, ItemStack right, ItemStack front) {
        this(List.of(back, left, right, front));
    }

    private static Optional<ItemStack> getStack(List<ItemStack> sherds, int i) {
        if (i >= sherds.size()) {
            return Optional.empty();
        } else {
            ItemStack item = sherds.get(i);
            return item.is(Items.BRICK) && !item.has(SherdsApiDataComponents.SHERD_PATTERN.get()) ? Optional.empty() : Optional.of(item);
        }
    }

    public List<ItemStack> ordered() {
        return Stream.of(this.back, this.left, this.right, this.front).map(stack -> stack.orElse(Items.BRICK.getDefaultInstance())).toList();
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(CommonComponents.EMPTY);
        addSideDetailsToTooltip(consumer, this.front);
        addSideDetailsToTooltip(consumer, this.left);
        addSideDetailsToTooltip(consumer, this.right);
        addSideDetailsToTooltip(consumer, this.back);
    }

    private static void addSideDetailsToTooltip(Consumer<Component> consumer, Optional<ItemStack> side) {
        consumer.accept(side.orElse(Items.BRICK.getDefaultInstance()).getHoverName().plainCopy().withStyle(ChatFormatting.GRAY));
    }
}
