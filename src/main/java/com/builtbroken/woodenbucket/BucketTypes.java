package com.builtbroken.woodenbucket;

import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;

public enum BucketTypes
{
    OAK,
    ACACIA,
    BIRCH,
    JUNGLE,
    SPRUCE,
    BIG_OAK,
    CHARRED;

    public BucketMaterial material;

    public ItemStack getBucket()
    {
    	return new ItemStack(new WoodBucketItem(new Properties(), material));
    }
}