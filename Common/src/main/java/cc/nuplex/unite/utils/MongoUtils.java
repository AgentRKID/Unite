package cc.nuplex.unite.utils;

import com.mongodb.client.model.ReplaceOptions;

public class MongoUtils {

    public static ReplaceOptions UPSERT_OPTIONS = new ReplaceOptions().upsert(true);

}
