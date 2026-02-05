package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusBehavior;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class HoneyCauldronBlock extends FourLeveledCauldronBlock {
	public static final MapCodec<HoneyCauldronBlock> CODEC = createCodec(HoneyCauldronBlock::new);
	
	public static final VoxelShape COLLISION_SHAPE_1 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 7.25D, 14.0D));
	public static final VoxelShape COLLISION_SHAPE_2 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 9.5D, 14.0D));
	public static final VoxelShape COLLISION_SHAPE_3 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 11.75D, 14.0D));
	public static final VoxelShape COLLISION_SHAPE_4 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 14.0D, 14.0D));
	public static final VoxelShape SHAPE_1 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 9.25D, 14.0D));
	public static final VoxelShape SHAPE_2 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 11.5D, 14.0D));
	public static final VoxelShape SHAPE_3 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 13.75D, 14.0D));
	public static final VoxelShape SHAPE_4 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D));
	
	@Override
	protected MapCodec<? extends HoneyCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public HoneyCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, CauldronsPlusBehavior.HONEY_CAULDRON_BEHAVIOR);
	}
	
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (player.getStackInHand(Hand.MAIN_HAND).isEmpty() && player.getStackInHand(Hand.OFF_HAND).isEmpty() && this.isFull(state)) {
			if (!world.isClient) {
				player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.HONEY_BLOCK));
				world.setBlockState(pos, this.getGroup().getEmpty().getDefaultState());
				world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			}
			return ActionResult.success(world.isClient);
		} else {
			return super.onUse(state, world, pos, player, hit);
		}
	}

	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.HONEY;
	}
	
	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(LEVEL)) {
			case 1 -> SHAPE_1;
			case 2 -> SHAPE_2;
			case 3 -> SHAPE_3;
			case 4 -> SHAPE_4;
			default -> super.getOutlineShape(state, world, pos, context);
		};
	}
	
	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(LEVEL)) {
			case 1 -> COLLISION_SHAPE_1;
			case 2 -> COLLISION_SHAPE_2;
			case 3 -> COLLISION_SHAPE_3;
			case 4 -> COLLISION_SHAPE_4;
			default -> super.getCollisionShape(state, world, pos, context);
		};
	}
	
	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (entity.handleFallDamage(fallDistance, 0.2f, world.getDamageSources().fall())) {
			entity.playSound(BlockSoundGroup.HONEY.getFallSound(), BlockSoundGroup.HONEY.getVolume() * 0.5F, BlockSoundGroup.HONEY.getPitch() * 0.75F);
		}
	}
}