package cc.nuplex.unite.profile;

import cc.nuplex.engine.serializers.Serializers;
import cc.nuplex.engine.util.http.HttpHandler;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.UniteGeneral;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileHandler {

    private final Map<UUID, Profile> profileMap = new ConcurrentHashMap<>();

    public void load(Profile profile) {
        HttpHandler.get(UniteGeneral.getPlugin().getApiHost() + "/profile/" + profile.getUniqueId(),
                ImmutableMap.of("username", profile.getUsername()), (response, code) -> {
            if (response == null || response.isJsonNull()) {
                return;
            }

            Profile other = Serializers.GSON.fromJson(response.getAsJsonObject(), Profile.class);

            if (other != null) {
                profile.update(other);
                profile.setLastUpdateSuccessful(true);
            }
        });
    }

    public void addEntry(Profile profile) {
        this.profileMap.put(profile.getUniqueId(), profile);
    }

    public void removeEntry(Profile profile) {
        this.profileMap.remove(profile.getUniqueId());
    }

    public Profile get(UUID uuid, boolean load) {
        Profile profile = this.profileMap.get(uuid);

        if (profile != null) {
            return profile;
        }

        if (load) {
            profile = new Profile(uuid, Unite.getInstance().getPlugin().getUUIDCache().name(uuid));

            // Send API Request/Load
            // the profile from the web
            this.load(profile);

            // Makes sure the request
            // was successful
            // before giving back a
            // profile which might not be correct
            if (profile.wasLastUpdateSuccessful()) {
                return profile;
            }
        }
        return null;
    }

}
