package com.builtbroken.tests.woodenbuckets;

import com.builtbroken.mc.testing.junit.AbstractTest;
import com.builtbroken.mc.testing.junit.VoltzTestRunner;
import com.builtbroken.woodenbucket.WoodenBucket;
import com.builtbroken.woodenbucket.bucket.ItemWoodenBucket;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 5/28/2016.
 */
@RunWith(VoltzTestRunner.class)
public class TestFluidContainer extends AbstractTest
{
    @Override
    public void setUpForEntireClass()
    {
        super.setUpForEntireClass();
        if(WoodenBucket.itemBucket == null)
        {
            WoodenBucket.itemBucket = new ItemWoodenBucket();
            GameRegistry.registerItem(WoodenBucket.itemBucket, "testWoodenBucketII");
        }
    }

    @Test
    public void testFill()
    {
        final Fluid fluid = FluidRegistry.WATER;
        final FluidStack stack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);

        for(ItemWoodenBucket.BucketTypes type : ItemWoodenBucket.BucketTypes.values())
        {
            //Created filled item
            ItemStack item = new ItemStack(WoodenBucket.itemBucket, 1, type.ordinal());
            int f = WoodenBucket.itemBucket.fill(item, stack, true);

            //Ensure it filled
            assertEquals(f, FluidContainerRegistry.BUCKET_VOLUME);
        }
    }
}