package cc.nuplex.unite;

import cc.nuplex.unite.plugin.Plugin;
import cc.nuplex.unite.profile.ProfileHandler;
import lombok.Getter;

public class Unite {

    @Getter private static Unite instance;

    @Getter private final Plugin plugin;

    @Getter private final ProfileHandler profileHandler;

    public Unite(Plugin plugin) {
        instance = this;

        this.plugin = plugin;

        this.profileHandler = new ProfileHandler();
    }
}
