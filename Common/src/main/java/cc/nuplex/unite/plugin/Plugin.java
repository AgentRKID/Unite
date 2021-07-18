package cc.nuplex.unite.plugin;

import cc.nuplex.engine.storage.cache.uuid.UUIDCache;
import cc.nuplex.engine.storage.mongo.Mongo;

import java.util.logging.Logger;

public interface Plugin {

    Logger getLogger();

    Mongo getMongo();

    UUIDCache getUUIDCache();

    default String getApiHost() { return "https://localhost:90"; }

    default void onKeepAlive(int keepAliveId) {}

    default void shutdown() {}

}
