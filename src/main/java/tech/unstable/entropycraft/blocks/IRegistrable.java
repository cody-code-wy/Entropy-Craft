package tech.unstable.entropycraft.blocks;

/**
 * Created by cody on 2016-08-13.
 */
public interface IRegistrable { // Used to allow any class that needs to be registered to register themselves.

    // Handy Chart! (Shows Method called in stage for side!
    //
    //                             FML initialization stages
    //            ┌─────────────────┬────────────────────┬─────────────────────┐
    //            │     PreInit     │        Init        │       PostInit      │
    //   ┌────────┼─────────────────┼────────────────────┼─────────────────────┤
    //   │  Both  │ RegisterAll     │ RegisterAllLate    │ RegisterAllAfter    │
    // S ├────────┼─────────────────┼────────────────────┼─────────────────────┤
    // I │ CLIENT │ RegisterClient  │ RegisterClientLate │ RegisterClientAfter │
    // D ├────────┼─────────────────┼────────────────────┼─────────────────────┤
    // E │ SERVER │ RegisterServer  │ RegisterServerLate │ RegisterServerAfter │
    //   └────────┴─────────────────┴────────────────────┴─────────────────────┘
    //
    // I am SO sorry if you don't use a monospace font. But it looks pretty good if you do! You probably should...

    void registerAll(); // This gets run on BOTH sides (PREINIT)

    void registerClient(); // This gets run on CLIENT only (PREINIT)

    void registerServer(); // This gets run on SERVER only (PREINIT)

    void registerAllLate(); // Run on BOTH sides at INIT

    void registerClientLate(); // Run on CLIENT at INIT

    void registerServerLate(); // Run on SERVER at INIT

    void registerAllAfter(); // Run on BOTH dies at POSTINIT

    void registerClientAfter(); // Run on CLIENT at POSTINIT

    void registerServerAfter(); // Run on SERVER at POSTINIT
}
