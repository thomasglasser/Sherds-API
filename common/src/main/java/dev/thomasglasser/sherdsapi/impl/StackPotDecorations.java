package dev.thomasglasser.sherdsapi.impl;

import com.mojang.serialization.Codec;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public record StackPotDecorations(Optional<ItemStack> back, Optional<ItemStack> left, Optional<ItemStack> right, Optional<ItemStack> front) {

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

    public static ItemStack createDecoratedPotItem(StackPotDecorations decorations) {
        ItemStack itemstack = Items.DECORATED_POT.getDefaultInstance();
        if (!decorations.equals(EMPTY))
            itemstack.set(SherdsApiDataComponents.STACK_POT_DECORATIONS.get(), decorations);
        return itemstack;
    }

    public static @Nullable StackPotDecorations load(@Nullable CompoundTag tag, String key) {
        return tag != null && tag.contains(key) ? CODEC.parse(NbtOps.INSTANCE, tag.get(key)).result().filter(d -> !d.equals(EMPTY)).orElse(null) : null;
    }

    private static Optional<ItemStack> getItem(List<ItemStack> decorations, int index) {
        if (index >= decorations.size())
            return Optional.empty();
        ItemStack item = decorations.get(index);
        return item.isEmpty() || item.is(Items.BRICK) ? Optional.empty() : Optional.of(item);
    }

    public List<ItemStack> ordered() {
        return Stream.of(back, left, right, front).map(stack -> stack.map(ItemStack::copy).orElseGet(Items.BRICK::getDefaultInstance)).toList();
    }

    public CompoundTag save(CompoundTag tag, String key) {
        if (!equals(EMPTY))
            tag.put(key, CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow());
        return tag;
    }

    @Override
    public boolean equals(Object o) {
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
