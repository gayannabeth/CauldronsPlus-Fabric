package gay.mountainspring.cauldronsplus.block;

import gay.mountainspring.aquifer.block.AquiferLeveledCauldronBlock;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import net.minecraft.block.cauldron.CauldronBehavior.CauldronBehaviorMap;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.biome.Biome;

public abstract class FourLeveledCauldronBlock extends AquiferLeveledCauldronBlock {
	public static final IntProperty LEVEL = CauldronsBlocks.LEVEL_4;
	
	public FourLeveledCauldronBlock(Biome.Precipitation precipitation, CauldronGroup group, Settings settings, CauldronBehaviorMap behaviorMap) {
		super(precipitation, group, settings, behaviorMap);
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
	}
	
	public FourLeveledCauldronBlock(CauldronGroup group, Settings settings, CauldronBehaviorMap behaviorMap) {
		this(null, group, settings, behaviorMap);
	}
	
	@Override
	public IntProperty aquifer$getLevelProperty() {
		return LEVEL;
	}
	
	@Override
	public int aquifer$getMaxLevel() {
		return 4;
	}
}