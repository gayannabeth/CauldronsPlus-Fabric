package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import gay.mountainspring.cauldronsplus.block.entity.CauldronBlockEntityTypes;
import gay.mountainspring.cauldronsplus.block.entity.PotionCauldronBlockEntity;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class PotionCauldronBlock extends TwentyFourLevelCauldronBlock implements BlockEntityProvider {
	public static final MapCodec<PotionCauldronBlock> CODEC = createCodec(PotionCauldronBlock::new);
	
	@Override
	protected MapCodec<PotionCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public PotionCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings);
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PotionCauldronBlockEntity(pos, state);
	}
	
	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.POTION;
	}
	
	@Override
	protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && this.isEntityTouchingFluid(state, pos, entity) && entity instanceof LivingEntity livingEntity) {
			PotionContentsComponent potionContents = world.getBlockEntity(pos, CauldronBlockEntityTypes.POTION_CAULDRON).get().getPotion();
			
		}
	}
	
	public static boolean tryInsertPotion(BlockState state, World world, BlockPos pos, ItemStack stack) {
		PotionContentsComponent potionContents = stack.get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents != null && state.getBlock() instanceof AbstractCauldronBlock cauldron) {
			if (cauldron.aquifer$getContentsType() == CauldronContentsType.EMPTY) {
				world.setBlockState(pos, cauldron.aquifer$getGroup().get(CauldronsPlusContentsTypes.POTION).getDefaultState().with(LEVEL, 1), Block.NOTIFY_ALL_AND_REDRAW);
				world.getBlockEntity(pos, CauldronBlockEntityTypes.POTION_CAULDRON).get().setPotion(potionContents);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				return true;
			} else if (cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.POTION && cauldron instanceof TwentyFourLevelCauldronBlock twentyFourCauldron) {
				if (twentyFourCauldron.incrementFluidLevelBy8(state, world, pos)) {
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
					return true;
				}
			}
		}
		return false;
	}
}