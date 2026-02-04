package gay.mountainspring.cauldronsplus.stat;

import gay.mountainspring.cauldronsplus.Cauldrons;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class CauldronsPlusStats {
	private CauldronsPlusStats() {}
	
	public static void init() {}
	
	public static final Identifier DYED_ITEM_WITH_CAULDRON = register("dyed_item_with_cauldron", StatFormatter.DEFAULT);
	
	private static Identifier register(String name, StatFormatter formatter) {
		Identifier id = Identifier.of(Cauldrons.MOD_ID, name);
		Registry.register(Registries.CUSTOM_STAT, id, id);
		Stats.CUSTOM.getOrCreateStat(id, formatter);
		return id;
	}
}