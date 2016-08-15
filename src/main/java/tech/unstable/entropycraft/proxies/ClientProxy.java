package tech.unstable.entropycraft.proxies;

import tech.unstable.entropycraft.EntropyCraftMod;
import tech.unstable.entropycraft.blocks.IRegistrable;

/**
 * Created by cody on 2016-08-10.
 */
public class ClientProxy implements BaseProxy {
    @Override
    public void doSidedRegister() {
        for(IRegistrable registrable : EntropyCraftMod.localRegistry){
            registrable.registerClient();
        }
    }

    @Override
    public void doSidedRegisterLate() {
        for(IRegistrable registrable : EntropyCraftMod.localRegistry){
            registrable.registerClientLate();
        }
    }

    @Override
    public void doSidedRegisterAfter() {
        for(IRegistrable registrable : EntropyCraftMod.localRegistry){
            registrable.registerClientAfter();
        }
    }
}
