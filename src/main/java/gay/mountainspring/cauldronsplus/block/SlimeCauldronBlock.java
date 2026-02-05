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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SlimeCauldronBlock extends NineLeveledCauldronBlock {
	public static final MapCodec<SlimeCauldronBlock> CODEC = createCodec(SlimeCauldronBlock::new);
	
	public static final VoxelShape SHAPE_1 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 8.0D, 14.0D));
	public static final VoxelShape SHAPE_2 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 9.0D, 14.0D));
	public static final VoxelShape SHAPE_3 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 10.0D, 14.0D));
	public static final VoxelShape SHAPE_4 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 11.0D, 14.0D));
	public static final VoxelShape SHAPE_5 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 12.0D, 14.0D));
	public static final VoxelShape SHAPE_6 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 13.0D, 14.0D));
	public static final VoxelShape SHAPE_7 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 14.0D, 14.0D));
	public static final VoxelShape SHAPE_8 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 15.0D, 14.0D));
	public static final VoxelShape SHAPE_9 = VoxelShapes.union(OUTLINE_SHAPE, Block.createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D));
	
	@Override
	protected MapCodec<SlimeCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public SlimeCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, CauldronsPlusBehavior.SLIME_CAULDRON_BEHAVIOR);
	}
	
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (player.getStackInHand(Hand.MAIN_HAND).isEmpty() && player.getStackInHand(Hand.OFF_HAND).isEmpty() && this.isFull(state)) {
			if (!world.isClient) {
				player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.SLIME_BLOCK));
				world.setBlockState(pos, this.getGroup().getEmpty().getDefaultState());
				world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			}
			return ActionResult.success(world.isClient);
		} else {
			return super.onUse(state, world, pos, player, hit);
		}
	}

	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.SLIME;
	}
	
	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(LEVEL)) {
			case 1 -> SHAPE_1;
			case 2 -> SHAPE_2;
			case 3 -> SHAPE_3;
			case 4 -> SHAPE_4;
			case 5 -> SHAPE_5;
			case 6 -> SHAPE_6;
			case 7 -> SHAPE_7;
			case 8 -> SHAPE_8;
			case 9 -> SHAPE_9;
			default -> super.getOutlineShape(state, world, pos, context);
		};
	}
	
	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (entity.bypassesLandingEffects()) {
			super.onLandedUpon(world, state, pos, entity, fallDistance);
		} else {
			entity.handleFallDamage(fallDistance, 0.0F, world.getDamageSources().fall());
		}
	}
	
	@Override
	public void onEntityLand(BlockView world, Entity entity) {
		if (entity.bypassesLandingEffects()) {
			super.onEntityLand(world, entity);
		} else {
			this.bounce(entity);
		}
	}
	
	//shamelessly borrowed from SlimeBlock class :3
	private void bounce(Entity entity) {
		Vec3d vec3d = entity.getVelocity();
		if (vec3d.y < 0.0) {
			double d = entity instanceof LivingEntity ? 1.0 : 0.8;
			entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
		}
	}
}