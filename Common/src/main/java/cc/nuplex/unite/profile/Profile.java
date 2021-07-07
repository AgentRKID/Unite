package cc.nuplex.unite.profile;

import lombok.Getter;
import org.bson.Document;

import java.util.UUID;

public class Profile {

    @Getter private final UUID uniqueId;
    @Getter private final String username;

    public Profile(UUID uniqueId, String username) {
        this.uniqueId = uniqueId;
        this.username = username;
    }

    public Document toDocument() {
        Document document = new Document();

        document.put("uuid", uniqueId.toString());
        document.put("username", username);

        return document;
    }
}
