package gay.mountainspring.cauldronsplus.datagen;

import java.util.concurrent.CompletableFuture;

import gay.mountainspring.cauldronsplus.Cauldrons;
import gay.mountainspring.cauldronsplus.item.CauldronsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;

public class CauldronsRecipeGen extends FabricRecipeProvider {
	public CauldronsRecipeGen(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	public void generate(RecipeExporter exporter) {
		ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.HONEY_BLOCK).input(CauldronsItems.HONEY_BUCKET).criterion(hasItem(CauldronsItems.HONEY_BUCKET), conditionsFromItem(CauldronsItems.HONEY_BUCKET)).offerTo(exporter, Identifier.of(Cauldrons.MOD_ID, convertBetween(Items.HONEY_BLOCK, CauldronsItems.HONEY_BUCKET)));
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, CauldronsItems.HONEY_BUCKET).input(Items.HONEY_BLOCK).input(Items.BUCKET).criterion(hasItem(Items.HONEY_BLOCK), conditionsFromItem(Items.HONEY_BLOCK)).criterion(hasItem(Items.BUCKET), conditionsFromItem(Items.BUCKET)).offerTo(exporter);
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, CauldronsItems.HONEY_BUCKET).input(Items.HONEY_BOTTLE).input(Items.HONEY_BOTTLE).input(Items.HONEY_BOTTLE).input(Items.HONEY_BOTTLE).input(Items.BUCKET).criterion(hasItem(Items.HONEY_BOTTLE), conditionsFromItem(Items.HONEY_BOTTLE)).criterion(hasItem(Items.BUCKET), conditionsFromItem(Items.BUCKET)).offerTo(exporter, Identifier.of(Cauldrons.MOD_ID, convertBetween(CauldronsItems.HONEY_BUCKET, Items.HONEY_BOTTLE) + "s"));
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, Items.HONEY_BOTTLE, 4).input(Items.GLASS_BOTTLE).input(Items.GLASS_BOTTLE).input(Items.GLASS_BOTTLE).input(Items.GLASS_BOTTLE).input(CauldronsItems.HONEY_BUCKET).criterion(hasItem(Items.GLASS_BOTTLE), conditionsFromItem(Items.GLASS_BOTTLE)).criterion(hasItem(CauldronsItems.HONEY_BUCKET), conditionsFromItem(CauldronsItems.HONEY_BUCKET)).offerTo(exporter, Identifier.of(Cauldrons.MOD_ID, convertBetween(Items.HONEY_BOTTLE, CauldronsItems.HONEY_BUCKET)));
		
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, CauldronsItems.MILK_BOTTLE, 4).input(Items.GLASS_BOTTLE).input(Items.GLASS_BOTTLE).input(Items.GLASS_BOTTLE).input(Items.GLASS_BOTTLE).input(Items.MILK_BUCKET).criterion(hasItem(Items.GLASS_BOTTLE), conditionsFromItem(Items.GLASS_BOTTLE)).criterion(hasItem(Items.MILK_BUCKET), conditionsFromItem(Items.MILK_BUCKET)).offerTo(exporter);
		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, Items.MILK_BUCKET).input(CauldronsItems.MILK_BOTTLE).input(CauldronsItems.MILK_BOTTLE).input(CauldronsItems.MILK_BOTTLE).input(CauldronsItems.MILK_BOTTLE).input(Items.BUCKET).criterion(hasItem(CauldronsItems.MILK_BOTTLE), conditionsFromItem(CauldronsItems.MILK_BOTTLE)).criterion(hasItem(Items.BUCKET), conditionsFromItem(Items.BUCKET)).offerTo(exporter, Identifier.of(Cauldrons.MOD_ID, getItemPath(Items.MILK_BUCKET)));
		
		ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.SLIME_BLOCK).input(CauldronsItems.SLIME_BUCKET).criterion(hasItem(CauldronsItems.SLIME_BUCKET), conditionsFromItem(CauldronsItems.SLIME_BUCKET)).offerTo(exporter);
		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, CauldronsItems.SLIME_BUCKET).input(Items.SLIME_BLOCK).input(Items.BUCKET).criterion(hasItem(Items.SLIME_BLOCK), conditionsFromItem(Items.SLIME_BLOCK)).criterion(hasItem(Items.BUCKET), conditionsFromItem(Items.BUCKET)).offerTo(exporter);
	}
}