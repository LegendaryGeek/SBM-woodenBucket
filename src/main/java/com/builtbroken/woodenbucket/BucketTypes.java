package com.builtbroken.woodenbucket;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public enum BucketTypes
{
    OAK(new BucketMaterial("oak", Blocks.OAK_PLANKS)),
    ACACIA(new BucketMaterial("acacia", Blocks.ACACIA_PLANKS)),
    BIRCH(new BucketMaterial("birch", Blocks.BIRCH_PLANKS)),
    JUNGLE(new BucketMaterial("jungle", Blocks.JUNGLE_PLANKS)),
    SPRUCE(new BucketMaterial("spruce", Blocks.SPRUCE_PLANKS)),
    BIG_OAK(new BucketMaterial("bigoak", Blocks.DARK_OAK_PLANKS)),
    WARPED(new BucketMaterial("warped", Blocks.WARPED_PLANKS)),
    CRIMSON(new BucketMaterial("crimson", Blocks.CRIMSON_PLANKS)),
    CHARRED(new BucketMaterial("charred", Blocks.AIR));

    public BucketMaterial material;

    BucketTypes(BucketMaterial bucketMaterial) {
    	this.material = bucketMaterial;
    	
	}
	public ItemStack getBucket()
    {
    	return new ItemStack(new WoodBucketItem(new Properties().group(ItemGroup.MISC), material));
    }
}