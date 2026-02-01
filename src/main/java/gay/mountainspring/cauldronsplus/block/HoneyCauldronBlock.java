package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HoneyCauldronBlock extends FourLeveledCauldronBlock {
	public static final MapCodec<HoneyCauldronBlock> CODEC = createCodec(HoneyCauldronBlock::new);
	
	public static final VoxelShape SHAPE_1 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 7.25D, 14.0D));
	public static final VoxelShape SHAPE_2 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 9.5D, 14.0D));
	public static final VoxelShape SHAPE_3 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 11.75D, 14.0D));
	public static final VoxelShape SHAPE_4 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 14.0D, 14.0D));
	
	@Override
	protected MapCodec<HoneyCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public HoneyCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, null); //TODO: behavior map
	}

	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.HONEY;
	}
	
	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(LEVEL)) {
			case 1 -> SHAPE_1;
			case 2 -> SHAPE_2;
			case 3 -> SHAPE_3;
			case 4 -> SHAPE_4;
			default -> super.getCollisionShape(state, world, pos, context);
		};
	}
}