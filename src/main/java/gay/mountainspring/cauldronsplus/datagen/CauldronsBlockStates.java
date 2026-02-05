package gay.mountainspring.cauldronsplus.datagen;

import gay.mountainspring.aquifer.datagen.AquiferModels;
import gay.mountainspring.aquifer.datagen.AquiferTextureMaps;
import gay.mountainspring.cauldronsplus.block.FourLeveledCauldronBlock;
import gay.mountainspring.cauldronsplus.block.NineLeveledCauldronBlock;
import gay.mountainspring.cauldronsplus.block.TwentyFourLeveledCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.util.Identifier;

public class CauldronsBlockStates {
	private CauldronsBlockStates() {}
	
	public static void registerFourLeveledCauldron(BlockStateModelGenerator gen, Block block, Block empty, Identifier contents) {
		registerFourLeveledCauldron(gen, block, AquiferTextureMaps.cauldron(TextureMap.getId(empty), contents));
	}
	
	public static void registerFourLeveledCauldron(BlockStateModelGenerator gen, Block block, TextureMap textures) {
		gen.blockStateCollector.accept(createFourLeveledCauldronBlockState(block,
				CauldronsModels.TEMPLATE_CAULDRON4_LEVEL1.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON4_LEVEL2.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON4_LEVEL3.upload(block, textures, gen.modelCollector),
				AquiferModels.TEMPLATE_CAULDRON_FULL.upload(block, textures, gen.modelCollector)));
	}
	
	public static void registerHoneyCauldron(BlockStateModelGenerator gen, Block block, Block empty) {
		registerHoneyCauldron(gen, block, AquiferTextureMaps.cauldron(empty));
	}
	
	public static void registerHoneyCauldron(BlockStateModelGenerator gen, Block block, TextureMap textures) {
		gen.blockStateCollector.accept(createFourLeveledCauldronBlockState(block,
				CauldronsModels.TEMPLATE_HONEY_CAULDRON_LEVEL1.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_HONEY_CAULDRON_LEVEL2.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_HONEY_CAULDRON_LEVEL3.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_HONEY_CAULDRON_FULL.upload(block, textures, gen.modelCollector)));
	}
	
	public static void registerNineLeveledCauldron(BlockStateModelGenerator gen, Block block, Block empty, Identifier contents) {
		registerNineLeveledCauldron(gen, block, AquiferTextureMaps.cauldron(TextureMap.getId(empty), contents));
	}
	
	public static void registerNineLeveledCauldron(BlockStateModelGenerator gen, Block block, TextureMap textures) {
		gen.blockStateCollector.accept(createNineLeveledCauldronBlockState(block,
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL1.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL2.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL3.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL4.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL5.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL6.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL7.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON9_LEVEL8.upload(block, textures, gen.modelCollector),
				AquiferModels.TEMPLATE_CAULDRON_FULL.upload(block, textures, gen.modelCollector)));
	}
	
	public static void registerSlimeCauldron(BlockStateModelGenerator gen, Block block, Block empty) {
		registerSlimeCauldron(gen, block, AquiferTextureMaps.cauldron(empty));
	}
	
	public static void registerSlimeCauldron(BlockStateModelGenerator gen, Block block, TextureMap textures) {
		gen.blockStateCollector.accept(createNineLeveledCauldronBlockState(block,
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL1.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL2.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL3.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL4.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL5.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL6.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL7.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_LEVEL8.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_SLIME_CAULDRON_FULL.upload(block, textures, gen.modelCollector)));
	}
	
	public static void registerTwentyFourLeveledCauldron(BlockStateModelGenerator gen, Block block, Block empty, Identifier contents) {
		registerTwentyFourLeveledCauldron(gen, block, AquiferTextureMaps.cauldron(TextureMap.getId(block), contents));
	}
	
	public static void registerTwentyFourLeveledCauldron(BlockStateModelGenerator gen, Block block, TextureMap textures) {
		gen.blockStateCollector.accept(createTwentyFourLeveledCauldronBlockState(block,
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL1.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL2.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL3.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL4.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL5.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL6.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL7.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL8.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL9.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL10.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL11.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL12.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL13.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL14.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL15.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL16.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL17.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL18.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL19.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL20.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL21.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL22.upload(block, textures, gen.modelCollector),
				CauldronsModels.TEMPLATE_CAULDRON24_LEVEL23.upload(block, textures, gen.modelCollector),
				AquiferModels.TEMPLATE_CAULDRON_FULL.upload(block, textures, gen.modelCollector)));
	}
	
	public static BlockStateSupplier createFourLeveledCauldronBlockState(Block block, Identifier model1, Identifier model2, Identifier model3, Identifier modelFull) {
		return VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(FourLeveledCauldronBlock.LEVEL)
						.register(1, BlockStateVariant.create().put(VariantSettings.MODEL, model1))
						.register(2, BlockStateVariant.create().put(VariantSettings.MODEL, model2))
						.register(3, BlockStateVariant.create().put(VariantSettings.MODEL, model3))
						.register(4, BlockStateVariant.create().put(VariantSettings.MODEL, modelFull)));
	}
	
	public static BlockStateSupplier createNineLeveledCauldronBlockState(Block block, Identifier model1, Identifier model2, Identifier model3, Identifier model4, Identifier model5, Identifier model6, Identifier model7, Identifier model8, Identifier modelFull) {
		return VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(NineLeveledCauldronBlock.LEVEL)
						.register(1, BlockStateVariant.create().put(VariantSettings.MODEL, model1))
						.register(2, BlockStateVariant.create().put(VariantSettings.MODEL, model2))
						.register(3, BlockStateVariant.create().put(VariantSettings.MODEL, model3))
						.register(4, BlockStateVariant.create().put(VariantSettings.MODEL, model4))
						.register(5, BlockStateVariant.create().put(VariantSettings.MODEL, model5))
						.register(6, BlockStateVariant.create().put(VariantSettings.MODEL, model6))
						.register(7, BlockStateVariant.create().put(VariantSettings.MODEL, model7))
						.register(8, BlockStateVariant.create().put(VariantSettings.MODEL, model8))
						.register(9, BlockStateVariant.create().put(VariantSettings.MODEL, modelFull)));
	}
	
	public static BlockStateSupplier createTwentyFourLeveledCauldronBlockState(Block block, Identifier... models) {
		if (models.length != 24) throw new IllegalArgumentException("Twenty Four Leveled Cauldrons must have twenty four models!");
		return VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(TwentyFourLeveledCauldronBlock.LEVEL)
						.register(i -> BlockStateVariant.create().put(VariantSettings.MODEL, models[i-1])));
	}
}