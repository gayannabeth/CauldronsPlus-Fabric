package gay.mountainspring.cauldronsplus.client;

import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

public class CauldronsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap renderLayerMap = BlockRenderLayerMap.INSTANCE;
		
		renderLayerMap.putBlocks(RenderLayer.getTranslucent(),
				CauldronsBlocks.HONEY_CAULDRON,
				CauldronsBlocks.SLIME_CAULDRON);
		
		var blockColorRegistry = ColorProviderRegistry.BLOCK;
		
		blockColorRegistry.register((state, world, pos, tintIndex) -> world != null && pos != null && world.getBlockEntityRenderData(pos) instanceof Integer i ? i : -1,
		CauldronsBlocks.DYED_WATER_CAULDRON,
		CauldronsBlocks.POTION_CAULDRON);
	}
}