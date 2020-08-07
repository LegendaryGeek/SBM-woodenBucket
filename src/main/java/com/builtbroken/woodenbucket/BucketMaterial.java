package com.builtbroken.woodenbucket;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fluids.FluidStack;

/**
 * Handles customization for a material value
 *
 * @see <a href=
 *      "https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a>
 *      for what you can and can't do with the code. Created by
 *      Dark(DarkGuardsman, Robert) on 3/3/2017.
 */
public class BucketMaterial {
	// Settings
	public ForgeConfigSpec.BooleanValue preventHotFluidUsage;
	public ForgeConfigSpec.BooleanValue damageBucketWithHotFluid;
	public ForgeConfigSpec.BooleanValue burnEntityWithHotFluid;
	public ForgeConfigSpec.BooleanValue enableFluidLeaking;
	public ForgeConfigSpec.BooleanValue allowLeakToCauseFires;

	/** Does the material support liquid fluids */
	public ForgeConfigSpec.BooleanValue supportsLiquids;
	/** Does the material support gas based fluids */
	public ForgeConfigSpec.BooleanValue supportsGases;

	/** Should the bucket limits what fluids can be used */
	public ForgeConfigSpec.BooleanValue restrictFluids;
	/** Is the restriction list an allow or deny list */
	public ForgeConfigSpec.BooleanValue restrictFluidsAllowList;
	/** Restriction list */
	public List<String> restrictFluidList = new ArrayList();

	public ForgeConfigSpec.IntValue viscosityToIgnoreLeaking;
	public ForgeConfigSpec.IntValue amountToLeak;
	public ForgeConfigSpec.IntValue capacity;
	public ForgeConfigSpec.DoubleValue chanceToLeak;
	public ForgeConfigSpec.DoubleValue leakFireChance;

	/** Localization to translate, prefixed with 'item.' */
	public String localization;

	/** Name of the material, set on register */
	public String materialName;
	/** Item meta value this material is registered to */
	public int metaValue;

	protected ResourceLocation bucketResourceLocation;
	protected ResourceLocation fluidResourceLocation;

	public BucketMaterial(String localization, ResourceLocation bucketResourceLocation) {
		this.localization = localization;
		this.bucketResourceLocation = bucketResourceLocation;
	}

	public BucketMaterial(String localization, String bucketResourceLocation) {
		this.localization = localization;
		this.bucketResourceLocation = new ResourceLocation(bucketResourceLocation);
	}

	/**
	 * Gets the damaged (from fire, or heat) version of the bucket
	 *
	 * @param stack - current bucket
	 * @return - material to switch to
	 */
	public BucketMaterial getDamagedBucket(ItemStack stack) {
		return null;
	}

	
	public ResourceLocation getBucketResourceLocation() {
		return bucketResourceLocation;
	}

	public ResourceLocation getFluidResourceLocation() {
		return fluidResourceLocation;
	}

	/**
	 * Checks if the bucket material can support the fluid
	 * <p>
	 * By default this is always true, but users can define there own settings per
	 * material.
	 *
	 * @param container - bucket stack
	 * @param resource  - fluid
	 * @return true if is supported
	 */
	public boolean supportsFluid(ItemStack container, FluidStack resource) {
		if (resource != null && resource.getFluid() != null) {
			final Fluid fluid = resource.getFluid();
			final String name = fluid.getRegistryName().toString();

			// Check for gas support
			if (fluid.getAttributes().isGaseous(resource) && !supportsGases.get()) {
				return false;
			}

			// Check for liquid support
			if (!fluid.getAttributes().isGaseous(resource) && !supportsLiquids.get()) {
				return false;
			}

			// Check if the fluid is restricted
			if (restrictFluids.get()) {
				if (restrictFluidsAllowList.get()) {
					return restrictFluidList.contains(name);
				} else {
					return !restrictFluidList.contains(name);
				}
			}
			return true;
		}
		return false;
	}
}