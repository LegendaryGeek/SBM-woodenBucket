package com.builtbroken.woodenbucket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

	public static ForgeConfigSpec.BooleanValue PREVENT_HOT_FLUID_USAGE;
	public static ForgeConfigSpec.BooleanValue DAMAGE_BUCKET_WITH_HOT_FLUID;
	public static ForgeConfigSpec.BooleanValue BURN_ENTITY_WITH_HOT_FLUID;
	public static ForgeConfigSpec.BooleanValue ENABLE_FLUID_LEAKING;
	public static ForgeConfigSpec.BooleanValue ALLOW_LEAK_TO_CAUSE_FIRES;

	public static ForgeConfigSpec.IntValue VISCOSITY_TO_IGNORE_LEAKING;
	public static ForgeConfigSpec.IntValue AMOUNT_TO_LEAK;
	public static ForgeConfigSpec.IntValue CAPACITY;

	public static ForgeConfigSpec.DoubleValue CHANCE_TO_LEAK;
	public static ForgeConfigSpec.DoubleValue LEAK_FIRE_CHANCE;

	public WoodenBucket() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		MinecraftForge.EVENT_BUS.addGenericListener(Item.class, this::registerItems);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		ConfigSetup();
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		Arrays.stream(BucketTypes.values()).forEach(
				type ->{ event.getRegistry().register(new WoodBucketItem(new Item.Properties().group(ItemGroup.TOOLS), type.material));
				LOGGER.info("Registering Item for bucket type {}", type.name());
				});
		
	}

	public void setup(final FMLCommonSetupEvent event) {
		LOGGER = LogManager.getLogger("WoodenBucket");

//        ShapedRecipeBuilder.shapedRecipe(resultIn)
	}

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
		// String path = "bucket";

		for (BucketTypes type : BucketTypes.values()) {
			String path2 = type.name();
			LOGGER.info("begining config restration for type {}, with material {}", type.name(), type.material.materialName);
			BUILD.push(path2);
			type.material.preventHotFluidUsage = BUILD
					.comment("Enables settings that attempt to prevent players from wanting to use the bucket for moving hot fluids")
					.define("prevent hot fluid usage", true);

			type.material.damageBucketWithHotFluid = BUILD
					.comment("Will randomly destroy the bucket if it contains hot fluid, lava in other words")
					.define("damage bucket with hot fluid", true);

			type.material.burnEntityWithHotFluid = BUILD
					.comment("Will light the player on fire if the bucket contains a hot fluid, lava in other words")
					.define("burn entity with hot fluid", true);

			type.material.enableFluidLeaking = BUILD
					.comment("Allows fluid to slowly leak out of the bucket as a nerf. Requested by Darkosto")
					.define("enable fluid leaking", false);

			type.material.viscosityToIgnoreLeaking = BUILD.comment(
					"At which point it the flow rate so slow that the leak is plugged, higher values are slower")
					.defineInRange("viscosity to ignore leaking", 3000, -1, 10000);

			type.material.amountToLeak = BUILD.comment(
					"How much can leak from the bucket each time a leak happens, number is max amount and is randomly ranged between 0 - #")
					.defineInRange("amount to leak", 1, 0, 1000);

			type.material.chanceToLeak = BUILD.comment(
					"What is the chance that a leak will happen, calculated each tick with high numbers being more often")
					.defineInRange("chance to leak", 0.03d, 0d, 1d);

			type.material.allowLeakToCauseFires = BUILD
					.comment("If molten fluid leaks, should there be a chance to cause fires?")
					.define("allow leak to cause fires", true);

			type.material.leakFireChance = BUILD.comment("How often to cause fire from molten fluids leaking")
					.defineInRange("leak fire chance", 0.4d, 0d, 1d);

			type.material.capacity = BUILD.comment("How much liquid the bucket should hold").defineInRange("capacity",
					1000, 1000, 10000);
			BUILD.pop();
		}

		SPEC = BUILD.build();
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, SPEC,
				"bbm/woodbucket/buckets.toml");

		LOGGER.log(Level.INFO, "oak hot fluid usage test: {}", SPEC.get("OAK.prevent hot fluid usage").toString());

//			for (BucketTypes type : BucketTypes.values()) {
//				type.material.preventHotFluidUsage = SPEC.get(type.name() + ".prevent hot fluid usage");
//				type.material.damageBucketWithHotFluid = SPEC.get(type.name() + ".damage bucket with hot fluid");
//				type.material.burnEntityWithHotFluid = SPEC.get(type.name() + ".burn entity with hot fluid");
//				type.material.enableFluidLeaking = SPEC.get(type.name() + ".enable fluid leaking");
//				type.material.viscosityToIgnoreLeaking = SPEC.get(type.name() + ".viscosity to ignore leaking");
//				type.material.amountToLeak = SPEC.get(type.name() + ".amount to leak");
//				type.material.chanceToLeak = SPEC.get(type.name() + ".chance to leak");
//				type.material.allowLeakToCauseFires = SPEC.get(type.name() + ".allow leak to cause fires");
//				type.material.leakFireChance = SPEC.get(type.name() + ".leak fire chance");
//				type.material.capacity = SPEC.get(type.name() + ".capacity");
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
