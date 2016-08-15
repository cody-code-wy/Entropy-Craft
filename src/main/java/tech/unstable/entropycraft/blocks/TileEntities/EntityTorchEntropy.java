package tech.unstable.entropycraft.blocks.TileEntities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tech.unstable.entropycraft.EntropyCraftMod;
import tech.unstable.entropycraft.blocks.BlockBurntTorchEntropy;
import tech.unstable.entropycraft.blocks.IRegistrable;

import javax.annotation.Nullable;

/**
 * Created by cody on 2016-08-08.
 */
public class EntityTorchEntropy extends TileEntity implements IRegistrable{

    byte torchLight = 15;
    static final String torchLightId = "torchLight";

    @Override
    public void registerAll() {
        GameRegistry.registerTileEntity(EntityTorchEntropy.class, "EC:TEtorch");
    }

    @Override
    public void registerClient() {

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
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writeSyncDataToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readSyncDataFromNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        writeSyncDataToNBT(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readSyncDataFromNBT(tag);
        super.handleUpdateTag(tag);
    }

    private void writeSyncDataToNBT(NBTTagCompound compound){
        compound.setByte(torchLightId,torchLight);
    }

    private void readSyncDataFromNBT(NBTTagCompound compound){
        torchLight = compound.getByte(torchLightId);
    }


    public void setTorchLight(int lightLevel){
        byte blight = (byte) lightLevel;
        if ( blight > 1 && blight <= 15 ) { // I just don't want to deal with _those_ bugs
            torchLight = blight;
            markDirty();
            worldObj.addBlockEvent(pos, this.getBlockType(), 1, torchLight);
        } else if (blight <= 1) {
            IBlockState state = worldObj.getBlockState(pos);
            IBlockState burnt = EntropyCraftMod.blockMap.get(BlockBurntTorchEntropy.class).getStateFromMeta(state.getBlock().getMetaFromState(state));
            worldObj.setBlockState(pos,burnt);
            worldObj.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,0.5f,2.6F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.8F);
            worldObj.removeTileEntity(pos);
        } else {
            EntropyCraftMod.logger.error("Attempted to set invalid light level " + lightLevel + "(" + blight + ") staying @ " + torchLight);
            EntropyCraftMod.logger.trace("In " + worldObj + " @ " + pos);
        }
    }

    public int getTorchLight(){
        return (int) torchLight;
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        switch (id){
            case 1:
                byte blight = (byte) type;
                if ( blight >= 0 && blight <= 15 ) { // I just don't want to deal with _those_ bugs
                    torchLight = blight;
                    worldObj.checkLight(pos);
                }
                break;
        }

        return true;
    }
}
