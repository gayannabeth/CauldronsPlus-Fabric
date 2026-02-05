package gay.mountainspring.cauldronsplus.block.cauldron;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.registry.AquiferRegistries;
import gay.mountainspring.aquifer.util.BlockUtil;
import gay.mountainspring.cauldronsplus.Cauldrons;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class CauldronsPlusContentsTypes {
	private CauldronsPlusContentsTypes() {}
	
	public static void init() {}
	
	public static final CauldronContentsType MILK = register("milk");
	public static final CauldronContentsType HONEY = register("honey");
	public static final CauldronContentsType SLIME = register("slime");
	public static final CauldronContentsType DYED_WATER = register("dyed_water");
	public static final CauldronContentsType POTION = register("potion");
	
	public static CauldronContentsType register(String name) {
		Identifier id = Identifier.of(Cauldrons.MOD_ID, name);
		return Registry.register(AquiferRegistries.CAULDRON_CONTENTS_TYPE, id, new CauldronContentsType(id));
	}
	
	static {
		BlockUtil.addPrecipitationForCauldronContents(DYED_WATER, Biome.Precipitation.RAIN);
	}
}