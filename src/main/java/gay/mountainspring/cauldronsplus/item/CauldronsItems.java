package gay.mountainspring.cauldronsplus.item;

import gay.mountainspring.aquifer.util.ItemUtil;
import gay.mountainspring.cauldronsplus.Cauldrons;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronsItems {
	private CauldronsItems() {}
	
	public static void init() {
		DispenserBehavior bucketPlaceBehavior = new ItemDispenserBehavior() {
			private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				FluidModificationItem fluidModificationItem = (FluidModificationItem)stack.getItem();
				BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				World world = pointer.world();
				if (fluidModificationItem.placeFluid(null, world, blockPos, null)) {
					fluidModificationItem.onEmptied(null, world, stack, blockPos);
					return this.decrementStackWithRemainder(pointer, stack, new ItemStack(Items.BUCKET));
				} else {
					return this.fallbackBehavior.dispense(pointer, stack);
				}
			}
		};
		
		DispenserBlock.registerBehavior(HONEY_BUCKET, bucketPlaceBehavior);
		DispenserBlock.registerBehavior(SLIME_BUCKET, bucketPlaceBehavior);
		
		ItemUtil.setItemFoodComponent(Items.MILK_BUCKET, CauldronsFoodComponents.MILK_BUCKET);
	}
	
	public static final Item HONEY_BUCKET = register("honey_bucket", new HoneyBucketItem(Blocks.HONEY_BLOCK, SoundEvents.BLOCK_HONEY_BLOCK_PLACE, new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET).food(CauldronsFoodComponents.HONEY_BUCKET)));
	public static final Item MILK_BOTTLE = register("milk_bottle", new MilkBottleItem(new Item.Settings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE).food(CauldronsFoodComponents.MILK_BOTTLE)));
	public static final Item SLIME_BUCKET = register("slime_bucket", new PowderSnowBucketItem(Blocks.SLIME_BLOCK, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
	
	public static Item register(String name, Item item) {
		return Registry.register(Registries.ITEM, Identifier.of(Cauldrons.MOD_ID, name), item);
	}
}