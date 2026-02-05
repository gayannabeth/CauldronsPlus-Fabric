package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.AquiferThreeLeveledCauldronBlock;
import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusBehavior;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import gay.mountainspring.cauldronsplus.block.entity.DyedWaterCauldronBlockEntity;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class DyedWaterCauldronBlock extends AquiferThreeLeveledCauldronBlock implements BlockEntityProvider {
	public static final MapCodec<DyedWaterCauldronBlock> CODEC = createCodec(DyedWaterCauldronBlock::new);
	
	@Override
	protected MapCodec<DyedWaterCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public DyedWaterCauldronBlock(CauldronGroup group, Settings settings) {
		super(Biome.Precipitation.RAIN, group, settings, CauldronsPlusBehavior.DYED_WATER_CAULDRON_BEHAVIOR);
	}
	
	@Override
	protected boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		super.onSyncedBlockEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.onSyncedBlockEvent(type, data);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DyedWaterCauldronBlockEntity(pos, state);
	}
	
	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.DYED_WATER;
	}
	
	public static boolean tryDye(BlockState state, BlockPos pos, World world, DyeItem dye) {
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron) {
			if (cauldron.aquifer$getContentsType() == CauldronContentsType.WATER) {
				world.setBlockState(pos, cauldron.aquifer$getGroup().get(CauldronsPlusContentsTypes.DYED_WATER).getStateWithProperties(state), Block.NOTIFY_ALL_AND_REDRAW);
				DyedWaterCauldronBlockEntity entity = ((DyedWaterCauldronBlockEntity) world.getBlockEntity(pos));
				entity.setColor(dye.getColor().getEntityColor());
				entity.markDirty();
				world.updateListeners(pos, state, state, Block.REDRAW_ON_MAIN_THREAD);
				return true;
			} else if (cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.DYED_WATER && world.getBlockEntity(pos) instanceof DyedWaterCauldronBlockEntity entity) {
				entity.applyDye(dye);
				entity.markDirty();
				world.updateListeners(pos, state, state, Block.REDRAW_ON_MAIN_THREAD);
				return true;
			}
		}
		return false;
	}
}