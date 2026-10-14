package dev.thomasglasser.sherdsapi.impl;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Objects;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public record StackPotDecorations(Optional<ItemStack> back, Optional<ItemStack> left, Optional<ItemStack> right, Optional<ItemStack> front) implements TooltipProvider {
    public static final StackPotDecorations EMPTY = new StackPotDecorations(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

    public static final Codec<StackPotDecorations> CODEC = ItemStack.OPTIONAL_CODEC
            .sizeLimitedListOf(4)
            .xmap(StackPotDecorations::new, StackPotDecorations::ordered);
    public static final StreamCodec<RegistryFriendlyByteBuf, StackPotDecorations> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC
            .apply(ByteBufCodecs.list(4))
            .map(StackPotDecorations::new, StackPotDecorations::ordered);

    public StackPotDecorations {
        back = back.filter(s -> !s.isEmpty()).map(ItemStack::copy);
        left = left.filter(s -> !s.isEmpty()).map(ItemStack::copy);
        right = right.filter(s -> !s.isEmpty()).map(ItemStack::copy);
        front = front.filter(s -> !s.isEmpty()).map(ItemStack::copy);
    }

    public StackPotDecorations(ItemStack back, ItemStack left, ItemStack right, ItemStack front) {
        this(List.of(back, left, right, front));
    }

    private StackPotDecorations(List<ItemStack> stacks) {
        this(getItem(stacks, 0), getItem(stacks, 1), getItem(stacks, 2), getItem(stacks, 3));
    }

    public static @Nullable StackPotDecorations load(ValueInput input, String key) {
        return input.read(key, CODEC).filter(d -> !d.equals(EMPTY)).orElse(null);
    }

    private static Optional<ItemStack> getItem(List<ItemStack> decorations, int index) {
        if (index >= decorations.size())
            return Optional.empty();
        ItemStack stack = decorations.get(index);
        return stack.isEmpty() || ItemStack.isSameItemSameComponents(stack, Items.BRICK.getDefaultInstance()) ? Optional.empty() : Optional.of(stack);
    }

    public List<ItemStack> ordered() {
        return Stream.of(back, left, right, front).map(stack -> stack.map(ItemStack::copy).orElseGet(Items.BRICK::getDefaultInstance)).toList();
    }

    public void save(ValueOutput output, String key) {
        if (!equals(EMPTY))
            output.store(key, CODEC, this);
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
        consumer.accept(side.orElseGet(Items.BRICK::getDefaultInstance).getHoverName().plainCopy().withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StackPotDecorations other = (StackPotDecorations) o;
        return areItemStacksEqual(back, other.back) &&
                areItemStacksEqual(left, other.left) &&
                areItemStacksEqual(right, other.right) &&
                areItemStacksEqual(front, other.front);
    }

    private static boolean areItemStacksEqual(Optional<ItemStack> a, Optional<ItemStack> b) {
        return a.isPresent() == b.isPresent() && (a.isEmpty() || ItemStack.isSameItemSameComponents(a.get(), b.get()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getStackHashCode(back),
                getStackHashCode(left),
                getStackHashCode(right),
                getStackHashCode(front));
    }

    private static int getStackHashCode(Optional<ItemStack> stack) {
        return stack.map(ItemStack::hashItemAndComponents).orElse(0);
    }
}
