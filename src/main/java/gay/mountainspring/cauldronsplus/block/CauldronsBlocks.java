package gay.mountainspring.cauldronsplus.block;

import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.Cauldrons;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

public class CauldronsBlocks {
	private CauldronsBlocks() {}
	
	public static final IntProperty LEVEL_4 = IntProperty.of("level", 1, 4);
	public static final IntProperty LEVEL_9 = IntProperty.of("level", 1, 9);
	public static final IntProperty LEVEL_24 = IntProperty.of("level", 1, 24);
	
	public static void init() {
		
	}
	
	public static final Block DYED_WATER_CAULDRON = new DyedWaterCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON));
	public static final Block POTION_CAULDRON = new PotionCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON).luminance(state -> 1));
	public static final Block MILK_CAULDRON = new MilkCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON));
	public static final Block HONEY_CAULDRON = new HoneyCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON));
	public static final Block SLIME_CAULDRON = new SlimeCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON));
	
	public static Block register(String name, Block block) {
		return Registry.register(Registries.BLOCK, Identifier.of(Cauldrons.MOD_ID, name), block);
	}
}