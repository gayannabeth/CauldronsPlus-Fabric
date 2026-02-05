package gay.mountainspring.cauldronsplus.block.entity;

import gay.mountainspring.cauldronsplus.Cauldrons;
import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CauldronBlockEntityTypes {
	private CauldronBlockEntityTypes() {}
	
	public static void init() {}
	
	public static final BlockEntityType<DyedWaterCauldronBlockEntity> DYED_WATER_CAULDRON = register("dyed_water_cauldron", BlockEntityType.Builder.create(DyedWaterCauldronBlockEntity::new, CauldronsBlocks.DYED_WATER_CAULDRON));
	public static final BlockEntityType<PotionCauldronBlockEntity> POTION_CAULDRON = register("potion_cauldron", BlockEntityType.Builder.create(PotionCauldronBlockEntity::new, CauldronsBlocks.POTION_CAULDRON));
	
	public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.Builder<T> builder) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Cauldrons.MOD_ID, name), builder.build());
	}
}