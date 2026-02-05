package gay.mountainspring.cauldronsplus.datagen;

import java.util.concurrent.CompletableFuture;

import gay.mountainspring.aquifer.loot.provider.number.BlockStatePropertyLootNumberProvider;
import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import gay.mountainspring.cauldronsplus.block.HoneyCauldronBlock;
import gay.mountainspring.cauldronsplus.block.SlimeCauldronBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class CauldronsBlockLootGen extends FabricBlockLootTableProvider {
	public CauldronsBlockLootGen(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}
	
	@Override
	public void generate() {
		this.addDrop(CauldronsBlocks.DYED_WATER_CAULDRON, Items.CAULDRON);
		this.addDrop(CauldronsBlocks.POTION_CAULDRON, Items.CAULDRON);
		this.addDrop(CauldronsBlocks.MILK_CAULDRON, Items.CAULDRON);
		this.addDrop(CauldronsBlocks.HONEY_CAULDRON, block -> honeyCauldronDrops(block, Items.CAULDRON));
		this.addDrop(CauldronsBlocks.SLIME_CAULDRON, block -> slimeCauldronDrops(block, Items.CAULDRON));
	}
	
	public LootTable.Builder honeyCauldronDrops(Block honeyCauldron, ItemConvertible emptyCauldron) {
		return LootTable.builder()
				.pool(this.addSurvivesExplosionCondition(emptyCauldron,
						LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0f))
						.with(ItemEntry.builder(emptyCauldron))))
				.pool(this.addSurvivesExplosionCondition(emptyCauldron,
						LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0f))
						.with(ItemEntry.builder(Items.HONEY_BLOCK))
						.conditionally(BlockStatePropertyLootCondition.builder(honeyCauldron).properties(StatePredicate.Builder.create().exactMatch(HoneyCauldronBlock.LEVEL, 4)))));
	}
	
	public LootTable.Builder slimeCauldronDrops(Block slimeCauldron, ItemConvertible emptyCauldron) {
		return LootTable.builder()
				.pool(this.addSurvivesExplosionCondition(emptyCauldron,
						LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0f))
						.with(ItemEntry.builder(emptyCauldron))))
				.pool(LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0f))
						.with(ItemEntry.builder(Items.SLIME_BLOCK)
								.conditionally(BlockStatePropertyLootCondition.builder(slimeCauldron).properties(StatePredicate.Builder.create().exactMatch(SlimeCauldronBlock.LEVEL, 9)))
								.conditionally(SurvivesExplosionLootCondition.builder())
								.alternatively(this.applyExplosionDecay(Items.SLIME_BALL,
										ItemEntry.builder(Items.SLIME_BALL)
										.apply(SetCountLootFunction.builder(BlockStatePropertyLootNumberProvider.create(slimeCauldron, SlimeCauldronBlock.LEVEL)))))));
	}
}