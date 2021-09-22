package kr.msleague.mslibrary.database;

import kr.msleague.bootstrap.MSPlugin;

public class DatabaseServerHandler extends MSPlugin {
    @Override
    public void onDisable() {
        MSLDatabases.getInst().onShutdown();
    }
}
