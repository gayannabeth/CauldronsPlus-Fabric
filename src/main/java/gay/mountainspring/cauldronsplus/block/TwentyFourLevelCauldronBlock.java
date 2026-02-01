package gay.mountainspring.cauldronsplus.block;

import gay.mountainspring.aquifer.block.AbstractAquiferCauldronBlock;
import gay.mountainspring.aquifer.block.AquiferLeveledCauldronExtensions;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public abstract class TwentyFourLevelCauldronBlock extends AbstractAquiferCauldronBlock implements AquiferLeveledCauldronExtensions {
	public static final IntProperty LEVEL = CauldronsBlocks.LEVEL_24;
	
	public TwentyFourLevelCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, null); //TODO: behavior map
	}

	@Override
	public boolean isFull(BlockState state) {
		return state.get(LEVEL) == 24;
	}
	
	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return MathHelper.ceil((double) state.get(LEVEL).intValue() / 8.0D);
	}
	
	@Override
	protected double getFluidHeight(BlockState state) {
		return (6.0D + state.get(LEVEL) * 0.375D) / 16.0D;
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
		if (i == 24) {
			return false;
		} else {
			BlockState blockState = state.with(LEVEL, i + 1);
			world.setBlockState(pos, blockState);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
			return true;
		}
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