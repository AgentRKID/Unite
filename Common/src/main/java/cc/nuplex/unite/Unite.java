package cc.nuplex.unite;

import cc.nuplex.engine.storage.cache.uuid.UUIDCache;
import cc.nuplex.engine.storage.mongo.Mongo;
import cc.nuplex.unite.profile.ProfileHandler;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Unite {
    public static ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    @Getter private static Unite instance;

    @Getter private final ProfileHandler profileHandler;

    public Unite(Mongo mongo, UUIDCache uuidCache) {
        instance = this;

        this.profileHandler = new ProfileHandler(mongo, uuidCache);
    }
}
