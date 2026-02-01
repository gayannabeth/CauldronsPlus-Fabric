package gay.mountainspring.cauldronsplus.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;

import gay.mountainspring.aquifer.block.InjectableFluidDrainable;
import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HoneyBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;

@Mixin(HoneyBlock.class)
public abstract class HoneyBlockMixin implements InjectableFluidDrainable {
	@Override
	public ItemStack tryDrainFluid(PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
		if (!world.isClient()) {
			world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
		}
		
		return new ItemStack(CauldronsItems.HONEY_BUCKET);
	}
	
	@Override
	public Optional<SoundEvent> getBucketFillSound() {
		return Optional.of(SoundEvents.BLOCK_HONEY_BLOCK_BREAK);
	}
}