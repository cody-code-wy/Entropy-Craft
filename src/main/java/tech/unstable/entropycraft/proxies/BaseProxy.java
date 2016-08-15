package tech.unstable.entropycraft.proxies;

/**
 * Created by cody on 2016-08-10.
 */
public interface BaseProxy {

    void doSidedRegister(); // called in PreInit

    void doSidedRegisterLate(); // called in Init

    void doSidedRegisterAfter(); // called in PostInit

}
