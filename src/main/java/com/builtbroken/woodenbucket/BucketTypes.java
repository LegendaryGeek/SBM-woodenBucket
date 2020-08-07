package com.builtbroken.woodenbucket;

import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;

public enum BucketTypes
{
    OAK(new BucketMaterial("oak", "oak")),
    ACACIA(new BucketMaterial("acacia", "acacia")),
    BIRCH(new BucketMaterial("birch", "birch")),
    JUNGLE(new BucketMaterial("jungle", "jungle")),
    SPRUCE(new BucketMaterial("spruce", "spruce")),
    BIG_OAK(new BucketMaterial("bigoak", "bigoak")),
    CHARRED(new BucketMaterial("charred", "charred"));

    public BucketMaterial material;

    BucketTypes(BucketMaterial bucketMaterial) {
    	this.material = bucketMaterial;
	}
	public ItemStack getBucket()
    {
    	return new ItemStack(new WoodBucketItem(new Properties(), material));
    }
}