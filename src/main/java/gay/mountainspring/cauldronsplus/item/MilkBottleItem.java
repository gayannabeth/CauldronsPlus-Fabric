package gay.mountainspring.cauldronsplus.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MilkBottleItem extends Item {
	public MilkBottleItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		super.finishUsing(stack, world, user);
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		
		if (!world.isClient) {
			Set<RegistryEntry<StatusEffect>> effectsToRemove = Sets.newHashSet();
			for (StatusEffectInstance instance : user.getStatusEffects()) {
				if (world.random.nextFloat() < 0.25f) {
					effectsToRemove.add(instance.getEffectType());
				}
			}
			for (RegistryEntry<StatusEffect> effect : effectsToRemove) {
				user.removeStatusEffect(effect);
			}
		}
		
		if (stack.isEmpty()) {
			return new ItemStack(Items.GLASS_BOTTLE);
		} else {
			if (user instanceof PlayerEntity playerEntity && !playerEntity.isInCreativeMode()) {
				ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
				if (!playerEntity.getInventory().insertStack(itemStack)) {
					playerEntity.dropItem(itemStack, false);
				}
			}
			
			return stack;
		}
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 22;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
	
	@Override
	public SoundEvent getEatSound() {
		return this.getDrinkSound();
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}