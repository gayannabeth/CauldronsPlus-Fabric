package gay.mountainspring.cauldronsplus.client;

import gay.mountainspring.aquifer.util.ColorSupplier;
import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.util.math.ColorHelper;

public class CauldronsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		var blockColorRegistry = ColorProviderRegistry.BLOCK;
		
		blockColorRegistry.register((state, world, pos, tintIndex) -> {
			if (world != null && pos != null) {
				if (world.getBlockEntity(pos) instanceof ColorSupplier entity) {
					return ColorHelper.Argb.fullAlpha(entity.getColor());
				}
			}
			
			return -1;
		},
		CauldronsBlocks.DYED_WATER_CAULDRON,
		CauldronsBlocks.POTION_CAULDRON);
	}
}