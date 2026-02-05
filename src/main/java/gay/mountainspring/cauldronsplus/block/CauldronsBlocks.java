package gay.mountainspring.cauldronsplus.block;

import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.aquifer.util.BlockUtil;
import gay.mountainspring.cauldronsplus.Cauldrons;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestTypes;

public class CauldronsBlocks {
	private CauldronsBlocks() {}
	
	public static final IntProperty LEVEL_4 = IntProperty.of("level", 1, 4);
	public static final IntProperty LEVEL_9 = IntProperty.of("level", 1, 9);
	public static final IntProperty LEVEL_24 = IntProperty.of("level", 1, 24);
	
	public static void init() {
		
	}
	
	public static final Block DYED_WATER_CAULDRON = register("dyed_water_cauldron", new DyedWaterCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON)));
	public static final Block POTION_CAULDRON = register("potion_cauldron", new PotionCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON).luminance(state -> 1)));
	public static final Block MILK_CAULDRON = register("milk_cauldron", new MilkCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON)));
	public static final Block HONEY_CAULDRON = register("honey_cauldron", new HoneyCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON).jumpVelocityMultiplier(0.5f).velocityMultiplier(0.4f)));
	public static final Block SLIME_CAULDRON = register("slime_cauldron", new SlimeCauldronBlock(CauldronGroup.VANILLA_IRON, AbstractBlock.Settings.copy(Blocks.CAULDRON).slipperiness(0.8f)));
	
	public static Block register(String name, Block block) {
		return Registry.register(Registries.BLOCK, Identifier.of(Cauldrons.MOD_ID, name), block);
	}
	
	static {
		BlockUtil.addBlocksToPOI(PointOfInterestTypes.LEATHERWORKER,
				DYED_WATER_CAULDRON,
				POTION_CAULDRON,
				MILK_CAULDRON,
				HONEY_CAULDRON,
				SLIME_CAULDRON);
	}
}