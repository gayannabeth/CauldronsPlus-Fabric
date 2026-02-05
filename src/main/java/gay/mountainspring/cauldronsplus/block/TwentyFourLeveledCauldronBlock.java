package gay.mountainspring.cauldronsplus.block;

import gay.mountainspring.aquifer.block.AquiferLeveledCauldronBlock;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

public abstract class TwentyFourLeveledCauldronBlock extends AquiferLeveledCauldronBlock {
	public static final IntProperty LEVEL = CauldronsBlocks.LEVEL_24;
	
	public TwentyFourLeveledCauldronBlock(Biome.Precipitation precipitation, CauldronGroup group, Settings settings, CauldronBehavior.CauldronBehaviorMap behaviorMap) {
		super(precipitation, group, settings, behaviorMap);
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 8));
	}
	
	public TwentyFourLeveledCauldronBlock(CauldronGroup group, Settings settings, CauldronBehavior.CauldronBehaviorMap behaviorMap) {
		this(null, group, settings, behaviorMap);
	}

	@Override
	public IntProperty aquifer$getLevelProperty() {
		return LEVEL;
	}
	
	@Override
	public int aquifer$getMaxLevel() {
		return 24;
	}
	
	public boolean decrementFluidLevelBy8(BlockState state, World world, BlockPos pos) {
		int i = state.get(LEVEL) - 8;
		if (i < 0) {
			return false;
		} else {
			BlockState blockState = i == 0 ? this.group.getEmpty().getDefaultState() : state.with(LEVEL, i);
			world.setBlockState(pos, blockState);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
			return true;
		}
	}
	
	public boolean incrementFluidLevelBy8(BlockState state, World world, BlockPos pos) {
		int i = state.get(LEVEL) + 8;
		if (i > 24) {
			return false;
		} else {
			BlockState blockState = state.with(LEVEL, i);
			world.setBlockState(pos, blockState);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
			return true;
		}
	}
}