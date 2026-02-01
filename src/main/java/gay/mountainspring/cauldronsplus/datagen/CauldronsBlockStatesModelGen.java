package gay.mountainspring.cauldronsplus.datagen;

import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class CauldronsBlockStatesModelGen extends FabricModelProvider {
	public CauldronsBlockStatesModelGen(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator gen) {
		
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator gen) {
		gen.register(CauldronsItems.HONEY_BUCKET, Models.GENERATED);
		gen.register(CauldronsItems.MILK_BOTTLE, Models.GENERATED);
		gen.register(CauldronsItems.SLIME_BUCKET, Models.GENERATED);
	}
}