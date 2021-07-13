package cc.nuplex.unite.bukkit;

import cc.nuplex.engine.Engine;
import cc.nuplex.engine.storage.cache.uuid.UUIDCache;
import cc.nuplex.engine.storage.mongo.Mongo;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.bukkit.listener.ConnectionListeners;

import cc.nuplex.unite.plugin.KeepAliveRunnable;
import cc.nuplex.unite.plugin.Plugin;
import cc.nuplex.unite.plugin.RefreshRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UniteBukkitPlugin extends JavaPlugin implements Plugin {

    @Override
    public void onEnable() {
        new Unite(this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new KeepAliveRunnable(), 0, 20  * 5);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new RefreshRunnable(), 0, 20 * 60);

        Bukkit.getPluginManager().registerEvents(new ConnectionListeners(), this);
    }

    @Override
    public void onDisable() { }

    @Override
    public Mongo getMongo() {
        return Engine.getInstance().getMongo();
    }

    @Override
    public UUIDCache getUUIDCache() {
        return Engine.getInstance().getUuidCache();
    }

    @Override
    public String getApiHost() {
        return "http://localhost:90";
    }

    @Override
    public void shutdown() {
        Bukkit.shutdown();
    }

}
