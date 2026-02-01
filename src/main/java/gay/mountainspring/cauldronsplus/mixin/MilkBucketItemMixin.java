package gay.mountainspring.cauldronsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin extends Item {
	public MilkBucketItemMixin(Settings settings) {super(settings);}
	
	@Inject(at = @At("HEAD"), method = "finishUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/item/ItemStack;", cancellable=true)
	private void finishUsingInjected(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
		super.finishUsing(stack, world, user);
	}
}