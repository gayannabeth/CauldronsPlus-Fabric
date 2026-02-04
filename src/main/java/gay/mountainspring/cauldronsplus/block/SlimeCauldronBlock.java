package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusBehavior;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SlimeCauldronBlock extends NineLeveledCauldronBlock {
	public static final MapCodec<SlimeCauldronBlock> CODEC = createCodec(SlimeCauldronBlock::new);
	
	@Override
	protected MapCodec<SlimeCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public SlimeCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, CauldronsPlusBehavior.SLIME_CAULDRON_BEHAVIOR);
		// TODO Auto-generated constructor stub
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
}