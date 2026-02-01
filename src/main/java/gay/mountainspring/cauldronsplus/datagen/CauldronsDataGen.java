package gay.mountainspring.cauldronsplus.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;

public class CauldronsDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		Pack pack = fabricDataGenerator.createPack();
		
		pack.addProvider(CauldronsBlockLootGen::new);
		pack.addProvider(CauldronsBlockStatesModelGen::new);
		pack.addProvider(CauldronsLangGen::new);
		pack.addProvider(CauldronsRecipeGen::new);
	}
}