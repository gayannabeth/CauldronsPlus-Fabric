package gay.mountainspring.cauldronsplus.item.group;

import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class CauldronsVanillaGroups {
	private CauldronsVanillaGroups() {}
	
	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
			entries.addAfter(Items.MILK_BUCKET,
					CauldronsItems.HONEY_BUCKET,
					CauldronsItems.SLIME_BUCKET);
		});
		
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
			entries.addAfter(Items.HONEY_BOTTLE, CauldronsItems.HONEY_BUCKET);
			entries.addBefore(Items.MILK_BUCKET, CauldronsItems.MILK_BOTTLE);
		});
	}
}