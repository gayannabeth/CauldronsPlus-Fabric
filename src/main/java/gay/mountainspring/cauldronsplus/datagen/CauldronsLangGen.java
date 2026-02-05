package gay.mountainspring.cauldronsplus.datagen;

import java.util.concurrent.CompletableFuture;

import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import gay.mountainspring.cauldronsplus.stat.CauldronsPlusStats;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class CauldronsLangGen extends FabricLanguageProvider {
	public CauldronsLangGen(FabricDataOutput dataOutput, CompletableFuture<WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}
	
	@Override
	public void generateTranslations(WrapperLookup registryLookup, TranslationBuilder builder) {
		builder.add(CauldronsItems.HONEY_BUCKET, "Honey Bucket");
		builder.add(CauldronsItems.MILK_BOTTLE, "Milk Bottle");
		builder.add(CauldronsItems.SLIME_BUCKET, "Slime Bucket");
		
		builder.add(CauldronsPlusStats.DYED_ITEM_WITH_CAULDRON, "Items dyed in a Cauldron");
	}
}