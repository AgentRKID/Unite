package cc.nuplex.unite.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Profile {

    @Getter private final transient UUID uniqueId;
    @Getter private final transient String username;

    @Setter private transient boolean lastUpdateSuccessful;

    public Profile(UUID uniqueId, String username) {
        this.uniqueId = uniqueId;
        this.username = username;
    }

    public void update(Profile other) {}

    public boolean wasLastUpdateSuccessful() {
        return this.lastUpdateSuccessful;
    }

}
