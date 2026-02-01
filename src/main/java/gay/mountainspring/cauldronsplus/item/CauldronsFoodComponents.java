package gay.mountainspring.cauldronsplus.item;

import net.minecraft.component.type.FoodComponent;

public class CauldronsFoodComponents {
	private CauldronsFoodComponents() {}
	
	public static final FoodComponent HONEY_BUCKET = new FoodComponent.Builder().nutrition(24).saturationModifier(0.4f).build();
	
	public static final FoodComponent MILK_BOTTLE = new FoodComponent.Builder().nutrition(1).saturationModifier(0.05f).build();
	public static final FoodComponent MILK_BUCKET = new FoodComponent.Builder().nutrition(4).saturationModifier(0.2f).build();
}