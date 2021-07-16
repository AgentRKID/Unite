package cc.nuplex.unite.profile;

import cc.nuplex.engine.util.http.HttpHandler;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.UniteGeneral;
import cc.nuplex.unite.util.Used;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Profile {

    @Getter private UUID uniqueId;
    @Getter private String username;

    @Getter @Setter private Map<String, Boolean> settings = new HashMap<>();
    @Getter @Setter private Set<String> ignored = new HashSet<>();

    @Setter private transient boolean lastUpdateSuccessful;

    @ConstructorProperties({ "uniqueId", "username" })
    public Profile(UUID uniqueId, String username) {
        this.uniqueId = uniqueId;
        this.username = username;
    }

    @Used
    public Profile() {}

    public void update(Profile other) {
        this.settings = other.settings;
        this.ignored = other.ignored;
    }

    public void saveSetting(Enum<?> setting, boolean value) {
        this.settings.put(setting.name(), value);
    }

    public void removeSetting(Enum<?> setting) {
        this.settings.remove(setting.name());
    }

    public boolean isSettingEnabled(Enum<?> setting) {
        return this.settings.getOrDefault(setting.name(), false);
    }

    public JsonElement createRequest(String endpoint, Map<Object, Object> bodyOrParams, boolean post) {
        AtomicReference<JsonElement> reference = new AtomicReference<>();

        if (post) {
            HttpHandler.post(UniteGeneral.getPlugin().getApiHost() + "/profile/" + this.uniqueId.toString(),
                    null, null, bodyOrParams, (response, code) -> {
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
