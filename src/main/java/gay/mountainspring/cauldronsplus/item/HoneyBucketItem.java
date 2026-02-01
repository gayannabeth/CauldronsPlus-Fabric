package gay.mountainspring.cauldronsplus.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class HoneyBucketItem extends PowderSnowBucketItem {
	public HoneyBucketItem(Block block, SoundEvent placeSound, Settings settings) {
		super(block, placeSound, settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		super.finishUsing(stack, world, user);
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		
		if (!world.isClient) {
			user.removeStatusEffect(StatusEffects.POISON);
		}
		
		if (stack.isEmpty()) {
			return new ItemStack(Items.BUCKET);
		} else {
			if (user instanceof PlayerEntity playerEntity && !playerEntity.isInCreativeMode()) {
				ItemStack itemStack = new ItemStack(Items.BUCKET);
				if (!playerEntity.getInventory().insertStack(itemStack)) {
					playerEntity.dropItem(itemStack, false);
				}
			}
			
			return stack;
		}
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 60;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
	
	@Override
	public SoundEvent getDrinkSound() {
		return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
	}
	
	@Override
	public SoundEvent getEatSound() {
		return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}