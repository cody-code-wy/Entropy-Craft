package tech.unstable.entropycraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by cody on 2016-08-13.
 */

public class BlockBurntTorchEntropy extends BlockTorch implements IRegistrable {

    public BlockBurntTorchEntropy(){
        super();
        this.setRegistryName("burnt_torch");
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setCreativeTab(CreativeTabs.DECORATIONS);

    }

    @Override
    public void registerAll() {
         GameRegistry.register(this);
         GameRegistry.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerClient() {
        Item item = Item.getItemFromBlock(this);
        int meta = 0;
        ModelResourceLocation loc = new ModelResourceLocation(this.getRegistryName(),"inventory");
        ModelLoader.setCustomModelResourceLocation(item,meta,loc);
    }

    @Override
    public void registerServer() {

    }

    @Override
    public void registerAllLate() {

    }

    @Override
    public void registerClientLate() {

    }

    @Override
    public void registerServerLate() {

    }

    @Override
    public void registerAllAfter() {

    }

    @Override
    public void registerClientAfter() {

    }

    @Override
    public void registerServerAfter() {

    }


    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        EnumFacing enumfacing = stateIn.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.7D;
        double d2 = (double)pos.getZ() + 0.5D;

        if (enumfacing.getAxis().isHorizontal())
        {
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D * (double)enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
        }
        else
        {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
}
