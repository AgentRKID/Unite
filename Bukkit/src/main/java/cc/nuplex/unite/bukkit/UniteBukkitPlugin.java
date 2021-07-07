package cc.nuplex.unite.bukkit;

import cc.nuplex.engine.Engine;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.bukkit.listeners.ConnectionListeners;
import cc.nuplex.unite.profile.Profile;
import cc.nuplex.unite.profile.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class UniteBukkitPlugin extends JavaPlugin {

    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        new Unite(Engine.getInstance().getMongo(), Engine.getInstance().getUuidCache());

        this.loadListeners();
    }

    @Override
    public void onDisable() {
        ProfileHandler profileHandler = Unite.getInstance().getProfileHandler();

        for (Profile profile : profileHandler.getProfiles()) {
            profileHandler.save(profile);
            profileHandler.removeEntry(profile.getUniqueId());
        }
    }

    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new ConnectionListeners(), this);

        logger.info("Loaded listeners.");
    }

}
