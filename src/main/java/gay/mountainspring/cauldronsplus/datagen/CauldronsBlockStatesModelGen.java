package gay.mountainspring.cauldronsplus.datagen;

import gay.mountainspring.aquifer.datagen.AquiferBlockStates;
import gay.mountainspring.aquifer.datagen.AquiferTextureMaps;
import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class CauldronsBlockStatesModelGen extends FabricModelProvider {
	public CauldronsBlockStatesModelGen(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator gen) {
		AquiferBlockStates.registerLeveledCauldron(gen, CauldronsBlocks.DYED_WATER_CAULDRON, AquiferTextureMaps.cauldron(Blocks.CAULDRON, Blocks.WATER, "_still"));
		CauldronsBlockStates.registerTwentyFourLeveledCauldron(gen, CauldronsBlocks.POTION_CAULDRON, AquiferTextureMaps.cauldron(Blocks.CAULDRON, Blocks.WATER, "_still"));
		CauldronsBlockStates.registerFourLeveledCauldron(gen, CauldronsBlocks.MILK_CAULDRON, AquiferTextureMaps.cauldron(Blocks.CAULDRON, Blocks.WATER, "_still"));
		CauldronsBlockStates.registerHoneyCauldron(gen, CauldronsBlocks.HONEY_CAULDRON, Blocks.CAULDRON);
		CauldronsBlockStates.registerSlimeCauldron(gen, CauldronsBlocks.SLIME_CAULDRON, Blocks.CAULDRON);
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator gen) {
		gen.register(CauldronsItems.HONEY_BUCKET, Models.GENERATED);
		gen.register(CauldronsItems.MILK_BOTTLE, Models.GENERATED);
		gen.register(CauldronsItems.SLIME_BUCKET, Models.GENERATED);
	}
}