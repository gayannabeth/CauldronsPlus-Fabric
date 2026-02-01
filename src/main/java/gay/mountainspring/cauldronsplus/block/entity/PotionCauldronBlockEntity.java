package gay.mountainspring.cauldronsplus.block.entity;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import gay.mountainspring.cauldronsplus.Cauldrons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap.Builder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.BlockPos;

public class PotionCauldronBlockEntity extends BlockEntity {
	private PotionContentsComponent potion = PotionContentsComponent.DEFAULT;
	
	public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(CauldronBlockEntityTypes.POTION_CAULDRON, pos, state);
	}
	
	public void setPotion(PotionContentsComponent potion) {
		this.potion = potion;
	}
	
	public PotionContentsComponent getPotion() {
		return this.potion;
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		nbt.put("potion", PotionContentsComponent.CODEC.encodeStart(NbtOps.INSTANCE, potion).getOrThrow());
	}
	
	@Override
	protected void readNbt(NbtCompound nbt, WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		if (nbt.contains("potion")) {
			PotionContentsComponent.CODEC
			.parse(NbtOps.INSTANCE, nbt.get("potion"))
			.resultOrPartial(string -> Cauldrons.LOGGER.error("Failed to parse potion: '{}'", string))
			.ifPresent(pot -> this.potion = pot);
		}
	}
	
	@Override
	protected void addComponents(Builder componentMapBuilder) {
		super.addComponents(componentMapBuilder);
		componentMapBuilder.add(DataComponentTypes.POTION_CONTENTS, this.potion);
	}
	
	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
		this.potion = components.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
	}
	
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	@Override
	public NbtCompound toInitialChunkDataNbt(WrapperLookup registryLookup) {
		return this.createNbt(registryLookup);
	}
	
	public Optional<Integer> getColor() {
		return this.potion.customColor();
	}
	
	public ItemStack fillPotion(ItemStack stack) {
		ItemStack newStack = stack.copy();
		if (stack.isOf(Items.GLASS_BOTTLE)) {
			newStack = newStack.withItem(Items.POTION);
		}
		if (stack.isOf(Items.ARROW)) {
			newStack = newStack.withItem(Items.TIPPED_ARROW);
		}
		newStack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(this.potion.potion(), this.getColor(), this.potion.customEffects()));
		return newStack;
	}
	
	public boolean applyEffectsToEntity(LivingEntity entity) {
		List<StatusEffectInstance> effects = Lists.newArrayList();
		if (this.potion.potion().isPresent()) {
			for (StatusEffectInstance effect : this.potion.potion().get().value().getEffects()) {
				effects.add(effect);
			}
		}
		effects.addAll(this.potion.customEffects());
		boolean hasEffectedEntity = false;
		if (entity.isAffectedBySplashPotions()) {
			for (StatusEffectInstance effect : effects) {
				if (entity.canHaveStatusEffect(effect)) {
					if (effect.getEffectType().value().isInstant()) {
						effect.getEffectType().value().applyInstantEffect(null, null, entity, effect.getAmplifier(), 1.0f);
						hasEffectedEntity = true;
					} else {
						entity.addStatusEffect(effect);
					}
				}
			}
		}
		return hasEffectedEntity;
	}
}