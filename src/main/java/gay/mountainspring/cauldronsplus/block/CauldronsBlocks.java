package gay.mountainspring.cauldronsplus.block;

import gay.mountainspring.cauldronsplus.Cauldrons;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

public class CauldronsBlocks {
	private CauldronsBlocks() {}
	
	public static final IntProperty LEVEL_4 = IntProperty.of("level", 1, 4);
	public static final IntProperty LEVEL_9 = IntProperty.of("level", 1, 9);
	public static final IntProperty LEVEL_24 = IntProperty.of("level", 1, 24);
	
	public static void init() {
		
	}
	
	
	
	public static Block register(String name, Block block) {
		return Registry.register(Registries.BLOCK, Identifier.of(Cauldrons.MOD_ID, name), block);
	}
}