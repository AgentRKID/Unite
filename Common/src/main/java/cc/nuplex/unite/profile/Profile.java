package cc.nuplex.unite.profile;

import cc.nuplex.api.common.RankType;
import cc.nuplex.engine.util.http.HttpHandler;
import cc.nuplex.unite.util.Used;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Profile {

    @Getter private UUID uuid;
    @Getter @Setter private String username;

    @Getter @Setter private RankType rankType = RankType.DEFAULT;

    @Getter private Map<String, Boolean> settings = new HashMap<>();
    @Getter private Set<String> ignored = new HashSet<>();

    @Setter private transient boolean lastUpdateSuccessful;

    @ConstructorProperties({ "uuid", "username" })
    public Profile(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Used
    public Profile() {}

    public void update(Profile other) {
        this.rankType = other.rankType;
        this.settings = other.settings;
        this.ignored = other.ignored;
    }

    public void saveSetting(Enum<?> setting, boolean value) {
        this.settings.put(setting.name(), value);
    }

    public void removeSetting(Enum<?> setting) {
        this.settings.remove(setting.name());
    }

    public boolean hasSetting(Enum<?> setting) {
        return this.settings.containsKey(setting.name());
    }

    public boolean isSettingEnabled(Enum<?> setting) {
        return this.settings.getOrDefault(setting.name(), false);
    }

    public JsonElement createRequest(String endpoint, Map<Object, Object> bodyOrParams, boolean post) {
        AtomicReference<JsonElement> reference = new AtomicReference<>();

        if (post) {
            HttpHandler.post(endpoint, null, null, bodyOrParams, (response, code) -> {
                if (response == null || !response.isJsonNull()) {
                    return;
                }

                reference.set(response);
            });
        } else {
            HttpHandler.get(endpoint, bodyOrParams, (response, code) -> {
                if (response == null || !response.isJsonNull()) {
                    return;
                }

                reference.set(response);
            });
        }
        return reference.get();
    }

    public boolean wasLastUpdateSuccessful() {
        return this.lastUpdateSuccessful;
    }

}
