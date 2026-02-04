package gay.mountainspring.cauldronsplus.block;

import gay.mountainspring.aquifer.block.AbstractAquiferCauldronBlock;
import gay.mountainspring.aquifer.block.AquiferLeveledCauldronExtensions;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior.CauldronBehaviorMap;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public abstract class NineLeveledCauldronBlock extends AbstractAquiferCauldronBlock implements AquiferLeveledCauldronExtensions {
	public static final IntProperty LEVEL = CauldronsBlocks.LEVEL_9;
	
	public NineLeveledCauldronBlock(CauldronGroup group, Settings settings, CauldronBehaviorMap behaviorMap) {
		super(group, settings, behaviorMap);
		this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
	}
	
	@Override
	public boolean isFull(BlockState state) {
		return state.get(LEVEL) == 9;
	}
	
	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(LEVEL);
	}
	
	@Override
	protected double getFluidHeight(BlockState state) {
		return (6.0D + state.get(LEVEL)) / 16.0D;
	}
	
	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}
	
	@Override
	public void aquifer$decrementFluidLevel(BlockState state, World world, BlockPos pos) {
		int i = state.get(LEVEL) - 1;
		BlockState blockState = i == 0 ? this.group.getEmpty().getDefaultState() : state.with(LEVEL, i);
		world.setBlockState(pos, blockState);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
	}
	
	@Override
	public boolean aquifer$incrementFluidLevel(BlockState state, World world, BlockPos pos) {
		int i = state.get(LEVEL);
		if (i == 9) {
			return false;
		} else {
			BlockState blockState = state.with(LEVEL, i + 1);
			world.setBlockState(pos, blockState);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
			return true;
		}
	}
}