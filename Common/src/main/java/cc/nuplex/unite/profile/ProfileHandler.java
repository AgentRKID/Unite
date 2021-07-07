package cc.nuplex.unite.profile;

import cc.nuplex.engine.storage.cache.uuid.UUIDCache;
import cc.nuplex.engine.storage.mongo.Mongo;
import cc.nuplex.engine.util.Handler;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.utils.MongoUtils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.*;

public class ProfileHandler implements Handler<Profile> {

    private final Map<UUID, Profile> profileMap = new HashMap<>();
    private final MongoCollection<Document> collection;
    private final UUIDCache uuidCache;

    public ProfileHandler(Mongo mongo, UUIDCache uuidCache) {
        this.uuidCache = uuidCache;

        this.collection = mongo.getClient().getDatabase("Unite").getCollection("profiles");
    }

    @Override
    public void load(Profile profile) {
        Document document = this.collection.find(Filters.eq("uuid", profile.getUniqueId().toString())).first();

        if (document == null) {
            return;
        }
        // TODO: Load other things
    }

    @Override
    public void save(Profile profile) {
        Unite.EXECUTOR.execute(
                () -> this.collection.replaceOne(Filters.eq("uuid", profile.getUniqueId().toString()), profile.toDocument(), MongoUtils.UPSERT_OPTIONS)
        );
    }

    public void addEntry(Profile profile) {
        this.profileMap.put(profile.getUniqueId(), profile);
    }

    public void removeEntry(UUID uuid) {
        this.profileMap.remove(uuid);
    }

    public boolean isCached(UUID uuid) {
        return this.profileMap.containsKey(uuid);
    }

    public Collection<Profile> getProfiles() {
        return this.profileMap.values();
    }

    public Profile getProfile(UUID uuid, boolean find) {
        Profile profile = this.profileMap.get(uuid);

        if (profile != null) {
            return profile;
        }

        if (find) {
            if (this.uuidCache.isCached(uuid)) {
                return new Profile(uuid, this.uuidCache.name(uuid));
            }
        }
        return null;
    }

    public Profile getProfile(String username, boolean find) {
        Profile profile = this.profileMap.values().stream().filter(stream -> stream.getUsername().equals(username)).findFirst().orElse(null);

        if (profile != null) {
            return profile;
        }

        if (find) {
            UUID uuid = this.uuidCache.uuid(username);

            if (uuid != null) {
                return new Profile(uuid, username);
            }
        }
        return null;
    }

}
