package tech.unstable.entropycraft.proxies;

import tech.unstable.entropycraft.EntropyCraftMod;
import tech.unstable.entropycraft.blocks.IRegistrable;

/**
 * Created by cody on 2016-08-10.
 */
public class ServerProxy implements BaseProxy {
    @Override
    public void doSidedRegister() {
        for(IRegistrable registrable : EntropyCraftMod.localRegistry){
            registrable.registerServer();
        }
    }

    @Override
    public void doSidedRegisterLate() {
        for(IRegistrable registrable : EntropyCraftMod.localRegistry){
            registrable.registerServerLate();
        }
    }

    @Override
    public void doSidedRegisterAfter() {
        for(IRegistrable registrable : EntropyCraftMod.localRegistry){
            registrable.registerServerAfter();
        }
    }
}
