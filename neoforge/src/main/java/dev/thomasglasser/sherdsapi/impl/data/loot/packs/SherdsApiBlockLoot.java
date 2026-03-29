package dev.thomasglasser.sherdsapi.impl.data.loot.packs;

import com.google.common.collect.ImmutableSet;
import dev.thomasglasser.sherdsapi.api.SherdsApiDataComponents;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class SherdsApiBlockLoot extends BlockLootSubProvider {
    public SherdsApiBlockLoot(HolderLookup.Provider registries) {
        super(ImmutableSet.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(Blocks.DECORATED_POT, this::createStackDecoratedPotTable);
    }

    private LootTable.Builder createStackDecoratedPotTable(Block original) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        DynamicLoot.dynamicEntry(DecoratedPotBlock.SHERDS_DYNAMIC_DROP_ID)
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(original)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DecoratedPotBlock.CRACKED, true)))
                                                .otherwise(
                                                        LootItem.lootTableItem(original)
                                                                .apply(
                                                                        CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                                                                .include(DataComponents.POT_DECORATIONS)
                                                                                .include(SherdsApiDataComponents.STACK_POT_DECORATIONS.get())))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ImmutableSet.of(Blocks.DECORATED_POT);
    }
}
