package com.builtbroken.woodenbucket;

import net.minecraftforge.fluids.capability.ItemFluidContainer;

public class WoodBucketItem extends ItemFluidContainer {
	
	int capacity;
	BucketMaterial mat;

	public WoodBucketItem(Properties properties, BucketMaterial material) {
		super(properties, material.capacity);
		mat = material;
		this.capacity = material.capacity;
	}

}
