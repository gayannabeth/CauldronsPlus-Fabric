package gay.mountainspring.cauldronsplus.datagen;

import java.util.concurrent.CompletableFuture;

import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class CauldronsTagGen {
	public static class BlockTagGen extends BlockTagProvider {

		public BlockTagGen(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}
		
		@Override
		protected void configure(WrapperLookup wrapperLookup) {
			this.getOrCreateTagBuilder(BlockTags.CAULDRONS)
			.add(CauldronsBlocks.DYED_WATER_CAULDRON,
					CauldronsBlocks.POTION_CAULDRON,
					CauldronsBlocks.MILK_CAULDRON,
					CauldronsBlocks.HONEY_CAULDRON,
					CauldronsBlocks.SLIME_CAULDRON);
		}
	}
	
	public static class ItemTagGen extends ItemTagProvider {
		public ItemTagGen(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}
		
		@Override
		protected void configure(WrapperLookup wrapperLookup) {
			this.getOrCreateTagBuilder(ConventionalItemTags.DRINK_CONTAINING_BOTTLE)
			.add(CauldronsItems.MILK_BOTTLE);
			
			this.getOrCreateTagBuilder(ConventionalItemTags.BUCKETS)
			.add(CauldronsItems.HONEY_BUCKET,
					CauldronsItems.SLIME_BUCKET);
		}
	}
	
	private CauldronsTagGen() {}
}