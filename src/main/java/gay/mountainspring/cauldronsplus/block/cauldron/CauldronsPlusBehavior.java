package gay.mountainspring.cauldronsplus.block.cauldron;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import gay.mountainspring.aquifer.block.AquiferLeveledCauldronExtensions;
import gay.mountainspring.aquifer.block.cauldron.AquiferCauldronBehavior;
import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.Cauldrons;
import gay.mountainspring.cauldronsplus.block.CauldronsBlocks;
import gay.mountainspring.cauldronsplus.block.DyedWaterCauldronBlock;
import gay.mountainspring.cauldronsplus.block.FourLeveledCauldronBlock;
import gay.mountainspring.cauldronsplus.block.NineLeveledCauldronBlock;
import gay.mountainspring.cauldronsplus.block.TwentyFourLeveledCauldronBlock;
import gay.mountainspring.cauldronsplus.block.entity.DyedWaterCauldronBlockEntity;
import gay.mountainspring.cauldronsplus.block.entity.PotionCauldronBlockEntity;
import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import gay.mountainspring.cauldronsplus.stat.CauldronsPlusStats;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class CauldronsPlusBehavior {
	private CauldronsPlusBehavior() {}
	
	public static void init() {}
	
	public static final CauldronBehavior.CauldronBehaviorMap DYED_WATER_CAULDRON_BEHAVIOR = CauldronBehavior.createMap(Cauldrons.MOD_ID + ":dyed_water");
	public static final CauldronBehavior.CauldronBehaviorMap POTION_CAULDRON_BEHAVIOR = CauldronBehavior.createMap(Cauldrons.MOD_ID + ":potion");
	public static final CauldronBehavior.CauldronBehaviorMap MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap(Cauldrons.MOD_ID + ":milk");
	public static final CauldronBehavior.CauldronBehaviorMap HONEY_CAULDRON_BEHAVIOR = CauldronBehavior.createMap(Cauldrons.MOD_ID + ":honey");
	public static final CauldronBehavior.CauldronBehaviorMap SLIME_CAULDRON_BEHAVIOR = CauldronBehavior.createMap(Cauldrons.MOD_ID + ":slime");
	
	public static final CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> fillCauldron(state, world, pos, player, hand, stack, group -> group.get(CauldronsPlusContentsTypes.MILK).getDefaultState().with(CauldronsBlocks.LEVEL_4, 4), SoundEvents.ITEM_BUCKET_FILL);
	
	public static final CauldronBehavior FILL_WITH_HONEY = (state, world, pos, player, hand, stack) -> fillCauldron(state, world, pos, player, hand, stack, group -> group.get(CauldronsPlusContentsTypes.HONEY).getDefaultState().with(CauldronsBlocks.LEVEL_4, 4), SoundEvents.BLOCK_HONEY_BLOCK_PLACE);
	
	public static final CauldronBehavior FILL_WITH_SLIME = (state, world, pos, player, hand, stack) -> fillCauldron(state, world, pos, player, hand, stack, group -> group.get(CauldronsPlusContentsTypes.SLIME).getDefaultState().with(CauldronsBlocks.LEVEL_9, 9), SoundEvents.BLOCK_SLIME_BLOCK_PLACE);
	
	public static final CauldronBehavior FILL_WITH_HONEY_BLOCK = (state, world, pos, player, hand, stack) -> fillCauldronWithBlock(state, world, pos, player, hand, stack, CauldronsPlusContentsTypes.HONEY, FourLeveledCauldronBlock.LEVEL, 4, SoundEvents.BLOCK_HONEY_BLOCK_PLACE);
	
	public static final CauldronBehavior FILL_WITH_SLIME_BLOCK = (state, world, pos, player, hand, stack) -> fillCauldronWithBlock(state, world, pos, player, hand, stack, CauldronsPlusContentsTypes.SLIME, NineLeveledCauldronBlock.LEVEL, 9, SoundEvents.BLOCK_SLIME_BLOCK_PLACE);
	
	public static final CauldronBehavior EMPTY_MILK = (state, world, pos, player, hand, stack) -> emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), cauldronState -> cauldronState.get(CauldronsBlocks.LEVEL_4) == 4, group -> group.getEmpty().getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
	
	public static final CauldronBehavior EMPTY_HONEY = (state, world, pos, player, hand, stack) -> emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(CauldronsItems.HONEY_BUCKET), cauldronState -> cauldronState.get(CauldronsBlocks.LEVEL_4) == 4, group -> group.getEmpty().getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
	
	public static final CauldronBehavior EMPTY_SLIME = (state, world, pos, player, hand, stack) -> emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(CauldronsItems.SLIME_BUCKET), cauldronState -> cauldronState.get(CauldronsBlocks.LEVEL_9) == 9, group -> group.getEmpty().getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
	
	public static final CauldronBehavior FILL_EMPTY_WITH_MILK_FROM_BOTTLE = (state, world, pos, player, hand, stack) -> fillEmptyCauldronFromBottle(state, world, pos, player, hand, stack, CauldronsPlusContentsTypes.MILK, SoundEvents.ITEM_BOTTLE_EMPTY);
	
	public static final CauldronBehavior FILL_EMPTY_WITH_HONEY_FROM_BOTTLE = (state, world, pos, player, hand, stack) -> fillEmptyCauldronFromBottle(state, world, pos, player, hand, stack, CauldronsPlusContentsTypes.HONEY, SoundEvents.BLOCK_HONEY_BLOCK_SLIDE);
	
	public static final CauldronBehavior FILL_EMPTY_WITH_SLIME_BALL = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron && cauldron.aquifer$getContentsType() == CauldronContentsType.EMPTY) {
			if (!world.isClient) {
				Item item = stack.getItem();
				stack.decrementUnlessCreative(1, player);
				player.incrementStat(Stats.FILL_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, cauldron.aquifer$getGroup().get(CauldronsPlusContentsTypes.SLIME).getDefaultState());
				world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			}
			
			return ItemActionResult.success(world.isClient);
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	public static final CauldronBehavior FILL_MILK_FROM_BOTTLE = (state, world, pos, player, hand, stack) -> fillCauldronFromBottle(state, world, pos, player, hand, stack, SoundEvents.ITEM_BOTTLE_EMPTY);
	
	public static final CauldronBehavior FILL_HONEY_FROM_BOTTLE = (state, world, pos, player, hand, stack) -> fillCauldronFromBottle(state, world, pos, player, hand, stack, SoundEvents.BLOCK_HONEY_BLOCK_SLIDE);
	
	public static final CauldronBehavior FILL_WITH_SLIME_BALL = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof NineLeveledCauldronBlock cauldron && cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.SLIME) {
			if (!world.isClient) {
				Item item = stack.getItem();
				if (cauldron.aquifer$incrementFluidLevel(state, world, pos)) {
					stack.decrementUnlessCreative(1, player);
					player.incrementStat(Stats.FILL_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
				} else {
					return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
				}
			}
			
			return ItemActionResult.success(world.isClient);
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	public static final CauldronBehavior EMPTY_MILK_WITH_BOTTLE = (state, world, pos, player, hand, stack) -> emptyCauldronWithBottle(state, world, pos, player, hand, stack, new ItemStack(CauldronsItems.MILK_BOTTLE), SoundEvents.ITEM_BOTTLE_FILL);
	
	public static final CauldronBehavior EMPTY_HONEY_WITH_BOTTLE = (state, world, pos, player, hand, stack) -> emptyCauldronWithBottle(state, world, pos, player, hand, stack, new ItemStack(Items.HONEY_BOTTLE), SoundEvents.ITEM_BOTTLE_FILL);
	
	public static final CauldronBehavior DYE_WATER = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron && (cauldron.aquifer$getContentsType() == CauldronContentsType.WATER || cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.DYED_WATER) && stack.getItem() instanceof DyeItem dye) {
			if (!world.isClient) {
				if (!DyedWaterCauldronBlock.tryDye(state, pos, world, dye)) {
					return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
				} else {
					player.incrementStat(Stats.USED.getOrCreateStat(dye));
					stack.decrementUnlessCreative(1, player);
				}
			} else {
				return ItemActionResult.success(world.isClient);
			}
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	public static final CauldronBehavior DYE_DYEABLE_ITEM = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron && cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.DYED_WATER) {
			if (world.getBlockEntity(pos) instanceof DyedWaterCauldronBlockEntity blockEntity) {
				if (!world.isClient) {
					ItemStack newStack = blockEntity.applyColor(stack);
					if (newStack != stack) {
						player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, newStack, false));
						player.incrementStat(CauldronsPlusStats.DYED_ITEM_WITH_CAULDRON);
						if (cauldron instanceof AquiferLeveledCauldronExtensions cauldron2) {
							cauldron2.aquifer$decrementFluidLevel(state, world, pos);
						}
					} else {
						return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
					}
				}
				
				return ItemActionResult.success(world.isClient);
			}
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	public static final CauldronBehavior FILL_EMPTY_WITH_POTION = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron) {
			PotionContentsComponent potionContents = stack.get(DataComponentTypes.POTION_CONTENTS);
			
			if (potionContents != null) {
				if (!world.isClient) {
					Item item = stack.getItem();
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.FILL_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					BlockState blockState;
					world.setBlockState(pos, blockState = cauldron.aquifer$getGroup().get(CauldronsPlusContentsTypes.POTION).getDefaultState());
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
					if (world.getBlockEntity(pos) instanceof PotionCauldronBlockEntity entity) {
						entity.setPotion(potionContents);
						entity.markDirty();
						world.updateListeners(pos, blockState, blockState, Block.REDRAW_ON_MAIN_THREAD);
					}
				}
				
				return ItemActionResult.success(world.isClient);
			}
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	public static final CauldronBehavior FILL_WITH_POTION = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof TwentyFourLeveledCauldronBlock cauldron && world.getBlockEntity(pos) instanceof PotionCauldronBlockEntity entity && cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.POTION) {
			PotionContentsComponent potionContents = stack.get(DataComponentTypes.POTION_CONTENTS);
			
			if (potionContents != null && potionContents.equals(entity.getPotion())) {
				if (!world.isClient) {
					if (cauldron.incrementFluidLevelBy8(state, world, pos)) {
						Item item = stack.getItem();
						player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
						player.incrementStat(Stats.FILL_CAULDRON);
						player.incrementStat(Stats.USED.getOrCreateStat(item));
						world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS);
					}
				}
				
				return ItemActionResult.success(world.isClient);
			}
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	public static final CauldronBehavior EMPTY_POTION = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof TwentyFourLeveledCauldronBlock cauldron && world.getBlockEntity(pos) instanceof PotionCauldronBlockEntity entity && cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.POTION) {
			PotionContentsComponent potionContents = entity.getPotion();
			
			if (potionContents != null) {
				if (!world.isClient) {
					if (cauldron.decrementFluidLevelBy8(state, world, pos)) {
						Item item = stack.getItem();
						ItemStack resultStack = new ItemStack(Items.POTION);
						resultStack.set(DataComponentTypes.POTION_CONTENTS, potionContents);
						player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, resultStack));
						player.incrementStat(Stats.USE_CAULDRON);
						player.incrementStat(Stats.USED.getOrCreateStat(item));
						world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS);
					}
				}
				
				return ItemActionResult.success(world.isClient);
			}
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	public static final CauldronBehavior APPLY_POTION_TO_ARROW = (state, world, pos, player, hand, stack) -> {
		if (state.getBlock() instanceof TwentyFourLeveledCauldronBlock cauldron && world.getBlockEntity(pos) instanceof PotionCauldronBlockEntity entity && cauldron.aquifer$getContentsType() == CauldronsPlusContentsTypes.POTION) {
			PotionContentsComponent potionContents = entity.getPotion();
			
			if (potionContents != null) {
				if (!world.isClient) {
					Item item = stack.getItem();
					ItemStack resultStack = new ItemStack(Items.TIPPED_ARROW);
					resultStack.set(DataComponentTypes.POTION_CONTENTS, potionContents);
					player.incrementStat(Stats.USE_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					player.incrementStat(Stats.CRAFTED.getOrCreateStat(Items.TIPPED_ARROW));
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, resultStack));
					cauldron.aquifer$decrementFluidLevel(state, world, pos);
					world.playSound(null, pos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS);
					world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
				}
				
				return ItemActionResult.success(world.isClient);
			}
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	};
	
	private static ItemActionResult fillCauldron(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, Function<CauldronGroup, BlockState> filledCauldronGetter, SoundEvent sound) {
		BlockState filledState = state;
		
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron) {
			filledState = filledCauldronGetter.apply(cauldron.aquifer$getGroup());
		}
		
		if (!world.isClient) {
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
			player.incrementStat(Stats.FILL_CAULDRON);
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			world.setBlockState(pos, filledState);
			world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
		}

		return ItemActionResult.success(world.isClient);
	}
	
	private static ItemActionResult fillCauldronWithBlock(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, CauldronContentsType resultType, IntProperty levelProperty, int maxLevel, SoundEvent sound) {
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron) {
			if (!world.isClient) {
				Item item = stack.getItem();
				stack.decrementUnlessCreative(1, player);
				player.incrementStat(Stats.FILL_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, cauldron.aquifer$getGroup().get(resultType).getDefaultState().with(levelProperty, maxLevel));
				world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.BLOCK_PLACE, pos);
			}
			
			return ItemActionResult.success(world.isClient);
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
	
	private static ItemActionResult fillCauldronFromBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, SoundEvent sound) {
		if (state.getBlock() instanceof AquiferLeveledCauldronExtensions cauldron) {
			if (!world.isClient) {
				Item item = stack.getItem();
				if (cauldron.aquifer$incrementFluidLevel(state, world, pos)) {
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.incrementStat(Stats.FILL_CAULDRON);
					player.incrementStat(Stats.USED.getOrCreateStat(item));
					world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
				} else {
					return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
				}
			}
			
			return ItemActionResult.success(world.isClient);
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
	
	private static ItemActionResult fillEmptyCauldronFromBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, CauldronContentsType resultType, SoundEvent sound) {
		if (state.getBlock() instanceof AbstractCauldronBlock cauldron && cauldron.aquifer$getContentsType() == CauldronContentsType.EMPTY) {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.FILL_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				world.setBlockState(pos, cauldron.aquifer$getGroup().get(resultType).getDefaultState());
				world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
			}
			
			return ItemActionResult.success(world.isClient);
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
	
	private static ItemActionResult emptyCauldron(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, ItemStack output, Predicate<BlockState> fullPredicate, Function<CauldronGroup, BlockState> emptyStateGetter, SoundEvent sound) {
		if (!fullPredicate.test(state)) {
			return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		} else {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, output));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				if (state.getBlock() instanceof AbstractCauldronBlock cauldron)
					world.setBlockState(pos, emptyStateGetter.apply(cauldron.aquifer$getGroup()));
				world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
			}
			
			return ItemActionResult.success(world.isClient);
		}
	}
	
	private static ItemActionResult emptyCauldronWithBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, ItemStack resultStack, SoundEvent sound) {
		if (state.getBlock() instanceof AquiferLeveledCauldronExtensions cauldron) {
			if (!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, resultStack));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				cauldron.aquifer$decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
			}
			
			return ItemActionResult.success(world.isClient);
		}
		
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
	
	private static void initBucketBehaviorsNonVanillaOnly(Map<Item, CauldronBehavior> map) {
		map.put(Items.MILK_BUCKET, FILL_WITH_MILK);
		map.put(CauldronsItems.HONEY_BUCKET, FILL_WITH_HONEY);
		map.put(CauldronsItems.SLIME_BUCKET, FILL_WITH_SLIME);
	}
	
	private static void initBucketBehaviors(Map<Item, CauldronBehavior> map) {
		map.put(Items.WATER_BUCKET, AquiferCauldronBehavior.AQUIFER_FILL_WITH_WATER);
		map.put(Items.LAVA_BUCKET, AquiferCauldronBehavior.AQUIFER_FILL_WITH_LAVA);
		map.put(Items.POWDER_SNOW_BUCKET, AquiferCauldronBehavior.AQUIFER_FILL_WITH_POWDER_SNOW);
		initBucketBehaviorsNonVanillaOnly(map);
	}
	
	static {
		var emptyMap = CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map();
		initBucketBehaviorsNonVanillaOnly(emptyMap);
		emptyMap.put(CauldronsItems.MILK_BOTTLE, FILL_EMPTY_WITH_MILK_FROM_BOTTLE);
		emptyMap.put(Items.HONEY_BOTTLE, FILL_EMPTY_WITH_HONEY_FROM_BOTTLE);
		emptyMap.put(Items.SLIME_BALL, FILL_EMPTY_WITH_SLIME_BALL);
		emptyMap.put(Items.HONEY_BLOCK, FILL_WITH_HONEY_BLOCK);
		emptyMap.put(Items.SLIME_BLOCK, FILL_WITH_SLIME_BLOCK);
		AquiferCauldronBehavior.setNonWaterPotionOnEmptyBehavior(FILL_EMPTY_WITH_POTION);
		
		var waterMap = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();
		initBucketBehaviorsNonVanillaOnly(waterMap);
		waterMap.put(Items.WHITE_DYE, DYE_WATER);
		waterMap.put(Items.LIGHT_GRAY_DYE, DYE_WATER);
		waterMap.put(Items.GRAY_DYE, DYE_WATER);
		waterMap.put(Items.BLACK_DYE, DYE_WATER);
		waterMap.put(Items.BROWN_DYE, DYE_WATER);
		waterMap.put(Items.RED_DYE, DYE_WATER);
		waterMap.put(Items.ORANGE_DYE, DYE_WATER);
		waterMap.put(Items.YELLOW_DYE, DYE_WATER);
		waterMap.put(Items.LIME_DYE, DYE_WATER);
		waterMap.put(Items.GREEN_DYE, DYE_WATER);
		waterMap.put(Items.CYAN_DYE, DYE_WATER);
		waterMap.put(Items.LIGHT_BLUE_DYE, DYE_WATER);
		waterMap.put(Items.BLUE_DYE, DYE_WATER);
		waterMap.put(Items.PURPLE_DYE, DYE_WATER);
		waterMap.put(Items.MAGENTA_DYE, DYE_WATER);
		waterMap.put(Items.PINK_DYE, DYE_WATER);
		waterMap.put(Items.HONEY_BLOCK, FILL_WITH_HONEY_BLOCK);
		waterMap.put(Items.SLIME_BLOCK, FILL_WITH_SLIME_BLOCK);
		
		var lavaMap = CauldronBehavior.LAVA_CAULDRON_BEHAVIOR.map();
		initBucketBehaviorsNonVanillaOnly(lavaMap);
		lavaMap.put(Items.HONEY_BLOCK, FILL_WITH_HONEY_BLOCK);
		lavaMap.put(Items.SLIME_BLOCK, FILL_WITH_SLIME_BLOCK);
		
		var powderSnowMap = CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.map();
		initBucketBehaviorsNonVanillaOnly(powderSnowMap);
		powderSnowMap.put(Items.HONEY_BLOCK, FILL_WITH_HONEY_BLOCK);
		powderSnowMap.put(Items.SLIME_BLOCK, FILL_WITH_SLIME_BLOCK);
		
		var dyedWaterMap = DYED_WATER_CAULDRON_BEHAVIOR.map();
		initBucketBehaviors(dyedWaterMap);
		dyedWaterMap.put(Items.WHITE_DYE, DYE_WATER);
		dyedWaterMap.put(Items.LIGHT_GRAY_DYE, DYE_WATER);
		dyedWaterMap.put(Items.GRAY_DYE, DYE_WATER);
		dyedWaterMap.put(Items.BLACK_DYE, DYE_WATER);
		dyedWaterMap.put(Items.BROWN_DYE, DYE_WATER);
		dyedWaterMap.put(Items.RED_DYE, DYE_WATER);
		dyedWaterMap.put(Items.ORANGE_DYE, DYE_WATER);
		dyedWaterMap.put(Items.YELLOW_DYE, DYE_WATER);
		dyedWaterMap.put(Items.LIME_DYE, DYE_WATER);
		dyedWaterMap.put(Items.GREEN_DYE, DYE_WATER);
		dyedWaterMap.put(Items.CYAN_DYE, DYE_WATER);
		dyedWaterMap.put(Items.LIGHT_BLUE_DYE, DYE_WATER);
		dyedWaterMap.put(Items.BLUE_DYE, DYE_WATER);
		dyedWaterMap.put(Items.PURPLE_DYE, DYE_WATER);
		dyedWaterMap.put(Items.MAGENTA_DYE, DYE_WATER);
		dyedWaterMap.put(Items.PINK_DYE, DYE_WATER);
		dyedWaterMap.put(Items.LEATHER_BOOTS, DYE_DYEABLE_ITEM);
		dyedWaterMap.put(Items.LEATHER_CHESTPLATE, DYE_DYEABLE_ITEM);
		dyedWaterMap.put(Items.LEATHER_HELMET, DYE_DYEABLE_ITEM);
		dyedWaterMap.put(Items.LEATHER_HORSE_ARMOR, DYE_DYEABLE_ITEM);
		dyedWaterMap.put(Items.LEATHER_LEGGINGS, DYE_DYEABLE_ITEM);
		dyedWaterMap.put(Items.WOLF_ARMOR, DYE_DYEABLE_ITEM);
		dyedWaterMap.put(Items.HONEY_BLOCK, FILL_WITH_HONEY_BLOCK);
		dyedWaterMap.put(Items.SLIME_BLOCK, FILL_WITH_SLIME_BLOCK);
		
		var potionMap = POTION_CAULDRON_BEHAVIOR.map();
		initBucketBehaviors(potionMap);
		potionMap.put(Items.POTION, FILL_WITH_POTION);
		potionMap.put(Items.GLASS_BOTTLE, EMPTY_POTION);
		potionMap.put(Items.ARROW, APPLY_POTION_TO_ARROW);
		potionMap.put(Items.HONEY_BLOCK, FILL_WITH_HONEY_BLOCK);
		potionMap.put(Items.SLIME_BLOCK, FILL_WITH_SLIME_BLOCK);
		
		var milkMap = MILK_CAULDRON_BEHAVIOR.map();
		initBucketBehaviors(milkMap);
		milkMap.put(Items.BUCKET, EMPTY_MILK);
		milkMap.put(Items.GLASS_BOTTLE, EMPTY_MILK_WITH_BOTTLE);
		milkMap.put(CauldronsItems.MILK_BOTTLE, FILL_MILK_FROM_BOTTLE);
		milkMap.put(Items.HONEY_BLOCK, FILL_WITH_HONEY_BLOCK);
		milkMap.put(Items.SLIME_BLOCK, FILL_WITH_SLIME_BLOCK);
		
		var honeyMap = HONEY_CAULDRON_BEHAVIOR.map();
		initBucketBehaviors(honeyMap);
		honeyMap.put(Items.BUCKET, EMPTY_HONEY);
		honeyMap.put(Items.GLASS_BOTTLE, EMPTY_HONEY_WITH_BOTTLE);
		honeyMap.put(Items.HONEY_BOTTLE, FILL_HONEY_FROM_BOTTLE);
		
		var slimeMap = SLIME_CAULDRON_BEHAVIOR.map();
		initBucketBehaviors(slimeMap);
		slimeMap.put(Items.BUCKET, EMPTY_SLIME);
		slimeMap.put(Items.SLIME_BALL, FILL_WITH_SLIME_BALL);
	}
}