package gay.mountainspring.cauldronsplus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusBehavior;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusGroups;
import gay.mountainspring.cauldronsplus.block.entity.CauldronBlockEntityTypes;
import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import gay.mountainspring.cauldronsplus.item.group.CauldronsVanillaGroups;
import gay.mountainspring.cauldronsplus.stat.CauldronsPlusStats;
import net.fabricmc.api.ModInitializer;

public class Cauldrons implements ModInitializer {
	public static final String MOD_ID = "cauldronsplus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitialize() {
		CauldronBlockEntityTypes.init();
		CauldronsPlusContentsTypes.init();
		CauldronsPlusGroups.init();
		CauldronsPlusBehavior.init();
		CauldronsPlusGroups.init();
		CauldronsBlocks.init();
		CauldronsItems.init();
		CauldronsPlusStats.init();
		CauldronsVanillaGroups.init();
	}
}