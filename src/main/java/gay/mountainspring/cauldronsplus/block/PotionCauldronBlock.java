package gay.mountainspring.cauldronsplus.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusBehavior;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import gay.mountainspring.cauldronsplus.block.entity.CauldronBlockEntityTypes;
import gay.mountainspring.cauldronsplus.block.entity.PotionCauldronBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionCauldronBlock extends TwentyFourLeveledCauldronBlock implements BlockEntityProvider {
	public static final MapCodec<PotionCauldronBlock> CODEC = createCodec(PotionCauldronBlock::new);
	
	@Override
	protected MapCodec<? extends PotionCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public PotionCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, CauldronsPlusBehavior.POTION_CAULDRON_BEHAVIOR);
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PotionCauldronBlockEntity(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient ? null : validateTicker(type, CauldronBlockEntityTypes.POTION_CAULDRON, PotionCauldronBlockEntity::tick);
	}
	
	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.POTION;
	}
	
	@Override
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && state.get(LEVEL) >= 8 && this.isEntityTouchingFluid(state, pos, entity) && entity instanceof LivingEntity livingEntity && world.getBlockEntity(pos) instanceof PotionCauldronBlockEntity potionCauldronBlockEntity) {
			if (potionCauldronBlockEntity.applyEffectsToEntity(livingEntity, world)) {
				this.decrementFluidLevelBy8(state, world, pos);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
		return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
	}
}