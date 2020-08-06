package com.builtbroken.woodenbucket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

/**
 * Created by Dark on 7/25/2015.
 */
//@Mod(modid = WoodenBucket.DOMAIN, name = "Wooden Bucket", version = "@MAJOR@.@MINOR@.@REVIS@.@BUILD@", dependencies = "after:vefluids")
@Mod.EventBusSubscriber(modid = WoodenBucket.DOMAIN)
@Mod(WoodenBucket.DOMAIN)
public class WoodenBucket {
	public static final String DOMAIN = "woodenbucket";
	public static final String PREFIX = DOMAIN + ":";

	public static Logger LOGGER = LogManager.getLogger("WoodenBucket");

	// public static ForgeConfig config;

	public static ForgeConfigSpec.ConfigValue<Boolean> PREVENT_HOT_FLUID_USAGE;
	public static ForgeConfigSpec.ConfigValue<Boolean> DAMAGE_BUCKET_WITH_HOT_FLUID;
	public static ForgeConfigSpec.ConfigValue<Boolean> BURN_ENTITY_WITH_HOT_FLUID;
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_FLUID_LEAKING;
	public static ForgeConfigSpec.ConfigValue<Boolean> ALLOW_LEAK_TO_CAUSE_FIRES;

	public static ForgeConfigSpec.ConfigValue<Integer> VISCOSITY_TO_IGNORE_LEAKING;
	public static ForgeConfigSpec.ConfigValue<Integer> AMOUNT_TO_LEAK;
	public static ForgeConfigSpec.ConfigValue<Integer> CAPACITY;

	public static ForgeConfigSpec.ConfigValue<Float> CHANCE_TO_LEAK;
	public static ForgeConfigSpec.ConfigValue<Float> LEAK_FIRE_CHANCE;

	public WoodenBucket() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		ConfigSetup();
	}

	public void setup(final FMLCommonSetupEvent event) {
		LOGGER = LogManager.getLogger("WoodenBucket");

//        ShapedRecipeBuilder.shapedRecipe(resultIn)
	}

//	@SubscribeEvent
//	public void registerBucketMaterials(BucketMaterialRegistryEvent.Pre event) {
//		for (BucketTypes type : BucketTypes.values()) {
//			type.material = new WoodenBucketMaterial(type);
//			BucketMaterialHandler.addMaterial(type.name().toLowerCase(), type.material, type.ordinal());
//		}
//	}

//	@SubscribeEvent
//	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
//		// TODO add crafting recipes for milk bucket
//		ResourceLocation location = new ResourceLocation(DOMAIN, "woodenbucket");
//		event.getRegistry()
//				.register(new ShapedOreRecipe(location, BucketTypes.OAK.getBucket(), " s ", "wcw", " w ", 'w',
//						new ItemStack(Blocks.PLANKS, 1, 0), 's', "stickWood", 'c', "dye")
//								.setRegistryName("bucket.wood.oak"));
//		event.getRegistry()
//				.register(new ShapedOreRecipe(location, BucketTypes.SPRUCE.getBucket(), " s ", "wcw", " w ", 'w',
//						new ItemStack(Blocks.PLANKS, 1, 1), 's', "stickWood", 'c', "dye")
//								.setRegistryName("bucket.wood.spruce"));
//		event.getRegistry()
//				.register(new ShapedOreRecipe(location, BucketTypes.BIRCH.getBucket(), " s ", "wcw", " w ", 'w',
//						new ItemStack(Blocks.PLANKS, 1, 2), 's', "stickWood", 'c', "dye")
//								.setRegistryName("bucket.wood.birch"));
//		event.getRegistry()
//				.register(new ShapedOreRecipe(location, BucketTypes.JUNGLE.getBucket(), " s ", "wcw", " w ", 'w',
//						new ItemStack(Blocks.PLANKS, 1, 3), 's', "stickWood", 'c', "dye")
//								.setRegistryName("bucket.wood.jungle"));
//		event.getRegistry()
//				.register(new ShapedOreRecipe(location, BucketTypes.ACACIA.getBucket(), " s ", "wcw", " w ", 'w',
//						new ItemStack(Blocks.PLANKS, 1, 4), 's', "stickWood", 'c', "dye")
//								.setRegistryName("bucket.wood.acacia"));
//		event.getRegistry()
//				.register(new ShapedOreRecipe(location, BucketTypes.BIG_OAK.getBucket(), " s ", "wcw", " w ", 'w',
//						new ItemStack(Blocks.PLANKS, 1, 5), 's', "stickWood", 'c', "dye")
//								.setRegistryName("bucket.wood.big_oak"));
//		for (ItemStack itemstack : OreDictionary.getOres("planks")) {
//			if (itemstack != null && itemstack.getItem() != Item.getItemFromBlock(Blocks.PLANKS)) {
//				event.getRegistry()
//						.register(new ShapedOreRecipe(location, BucketTypes.OAK.getBucket(), " s ", "wcw", " w ", 'w',
//								itemstack, 's', "stickWood", 'c', "dye")
//										.setRegistryName("bucket.wood." + itemstack.getTranslationKey()));
//			}
//		}
//	}

//    @Mod.EventHandler
	public void ConfigSetup() {

		final ForgeConfigSpec.Builder BUILD = new ForgeConfigSpec.Builder();
		final ForgeConfigSpec SPEC;
		Path configs = FMLPaths.CONFIGDIR.get();
		Path woodconfig = Paths.get(configs.toAbsolutePath().toString(), "bbm", "woodbucket");
		try {
			Files.createDirectories(woodconfig);
		} catch (IOException e) {
			LOGGER.catching(e);
		}
		String path = "bucket ";

		for (BucketTypes type : BucketTypes.values()) {
			String path2 = path + type.name().toLowerCase();
			BUILD.push(path2);
			PREVENT_HOT_FLUID_USAGE = BUILD.comment(
					"Enables settings that attempt to prevent players from wanting to use the bucket for moving hot fluids")
					.define("prevent hot fluid usage", true);

			DAMAGE_BUCKET_WITH_HOT_FLUID = BUILD
					.comment("Will randomly destroy the bucket if it contains hot fluid, lava in other words")
					.define("damage bucket with hot fluid", true);

			BURN_ENTITY_WITH_HOT_FLUID = BUILD
					.comment("Will light the player on fire if the bucket contains a hot fluid, lava in other words")
					.define("burn entity with hot fluid", true);

			ENABLE_FLUID_LEAKING = BUILD
					.comment("Allows fluid to slowly leak out of the bucket as a nerf. Requested by Darkosto")
					.define("enable fluid leaking", false);

			VISCOSITY_TO_IGNORE_LEAKING = BUILD.comment(
					"At which point it the flow rate so slow that the leak is plugged, higher values are slower")
					.define("viscosity to ignore leaking", 3000);

			AMOUNT_TO_LEAK = BUILD.comment(
					"How much can leak from the bucket each time a leak happens, number is max amount and is randomly ranged between 0 - #")
					.define("amount to leak", 1);

			CHANCE_TO_LEAK = BUILD.comment(
					"What is the chance that a leak will happen, calculated each tick with high numbers being more often")
					.define("chance to leak", 0.03f);

			ALLOW_LEAK_TO_CAUSE_FIRES = BUILD.comment("If molten fluid leaks, should there be a chance to cause fires?")
					.define("allow leak to cause fires", true);

			LEAK_FIRE_CHANCE = BUILD.comment("How often to cause fire from molten fluids leaking").define("leak fire chance", 0.4f);

			CAPACITY = BUILD.comment("How much liquid the bucket should hold").define("capacity", 1000);
			BUILD.pop();
		}

		SPEC = BUILD.build();
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, SPEC,
				"bbm/woodbucket/wood.toml");

//			for (BucketTypes type : BucketTypes.values()) {
//				type.material.preventHotFluidUsage = PREVENT_HOT_FLUID_USAGE.get();
//				type.material.damageBucketWithHotFluid = DAMAGE_BUCKET_WITH_HOT_FLUID.get();
//				type.material.burnEntityWithHotFluid = BURN_ENTITY_WITH_HOT_FLUID.get();
//				type.material.enableFluidLeaking = ENABLE_FLUID_LEAKING.get();
//				type.material.viscosityToIgnoreLeaking = VISCOSITY_TO_IGNORE_LEAKING.get();
//				type.material.amountToLeak = AMOUNT_TO_LEAK.get();
//				type.material.chanceToLeak = CHANCE_TO_LEAK.get();
//				type.material.allowLeakToCauseFires = ALLOW_LEAK_TO_CAUSE_FIRES.get();
//				type.material.leakFireChance = LEAK_FIRE_CHANCE.get();
//				type.material.capacity = CAPACITY.get();
//			}

	}

//    @Mod.EventHandler
//    public void postInit(FMLPostInitializationEvent event)
//    {
//	public static int AMOUNT_TO_LEAK = 1;
//	public static float CHANCE_TO_LEAK = 0.03f;
//	public static float LEAK_FIRE_CHANCE = 0.4f;

//
//    }
}
