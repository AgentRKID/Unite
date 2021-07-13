package cc.nuplex.unite.plugin;

import cc.nuplex.engine.storage.cache.uuid.UUIDCache;
import cc.nuplex.engine.storage.mongo.Mongo;

import java.util.logging.Logger;

public interface Plugin {

    Logger getLogger();

    Mongo getMongo();

    UUIDCache getUUIDCache();

    String getApiHost();

    void shutdown();

}
