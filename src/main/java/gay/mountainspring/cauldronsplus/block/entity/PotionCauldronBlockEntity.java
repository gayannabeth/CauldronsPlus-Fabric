package gay.mountainspring.cauldronsplus.block.entity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import gay.mountainspring.aquifer.util.ColorSupplier;
import gay.mountainspring.cauldronsplus.Cauldrons;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
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
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionCauldronBlockEntity extends BlockEntity implements ColorSupplier {
	private PotionContentsComponent potion = PotionContentsComponent.DEFAULT;
	private Object2LongMap<UUID> recentlyAffectedEntities = new Object2LongOpenHashMap<>();
	
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
	public int getColor() {
		return this.potion.getColor();
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		nbt.put("potion", PotionContentsComponent.CODEC.encodeStart(NbtOps.INSTANCE, potion).getOrThrow());
		NbtList nbtList = new NbtList();
		
		for (Object2LongMap.Entry<UUID> entry : this.recentlyAffectedEntities.object2LongEntrySet()) {
			NbtCompound nbtCompound = new NbtCompound();
			nbtCompound.putUuid("Entity", entry.getKey());
			nbtCompound.putLong("Time", entry.getLongValue());
		}
		
		nbt.put("RecentEntities", nbtList);
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
		if (nbt.contains("RecentEntities") && nbt.get("RecentEntities") instanceof NbtList nbtList) {
			this.recentlyAffectedEntities.clear();
			for (NbtElement nbtElement : nbtList) {
				if (nbtElement instanceof NbtCompound nbtCompound) {
					if (nbtCompound.contains("Entity") && nbtCompound.contains("Time")) {
						try {
							UUID uuid = nbtCompound.getUuid("Entity");
							long time = nbtCompound.getLong("Time");
							this.recentlyAffectedEntities.put(uuid, time);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
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
	
	public ItemStack fillPotion(ItemStack stack) {
		ItemStack newStack = stack.copy();
		if (stack.isOf(Items.GLASS_BOTTLE)) {
			newStack = newStack.withItem(Items.POTION);
		}
		if (stack.isOf(Items.ARROW)) {
			newStack = newStack.withItem(Items.TIPPED_ARROW);
		}
		newStack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(this.potion.potion(), Optional.of(this.getColor()), this.potion.customEffects()));
		return newStack;
	}
	
	public boolean applyEffectsToEntity(LivingEntity entity, World world) {
		List<StatusEffectInstance> effects = Lists.newArrayList();
		if (this.potion.potion().isPresent()) {
			for (StatusEffectInstance effect : this.potion.potion().get().value().getEffects()) {
				effects.add(effect);
			}
		}
		effects.addAll(this.potion.customEffects());
		boolean hasEffectedEntity = false;
		if (!effects.isEmpty() && entity.isAffectedBySplashPotions() && this.recentlyAffectedEntities.containsKey(entity.getUuid())) {
			for (StatusEffectInstance effect : effects) {
				if (entity.canHaveStatusEffect(effect)) {
					if (effect.getEffectType().value().isInstant()) {
						effect.getEffectType().value().applyInstantEffect(null, null, entity, effect.getAmplifier(), 1.0f);
					} else {
						entity.addStatusEffect(effect);
					}
					hasEffectedEntity = true;
				}
			}
		}
		if (hasEffectedEntity) {
			this.recentlyAffectedEntities.put(entity.getUuid(), world.getTime() + 400L);
		}
		return hasEffectedEntity;
	}
	
	public static void tick(World world, BlockPos pos, BlockState state, PotionCauldronBlockEntity blockEntity) {
		if (!world.isClient) {
			if (blockEntity.potion.hasEffects()) {
				Set<UUID> toRemove = Sets.newHashSet();
				for (Object2LongMap.Entry<UUID> entry : blockEntity.recentlyAffectedEntities.object2LongEntrySet()) {
					if (entry.getLongValue() >= world.getTime()) {
						toRemove.add(entry.getKey());
					}
				}
				for (UUID uuid : toRemove) {
					blockEntity.recentlyAffectedEntities.removeLong(uuid);
				}
			} else {
				blockEntity.recentlyAffectedEntities.clear();
			}
		}
	}
}