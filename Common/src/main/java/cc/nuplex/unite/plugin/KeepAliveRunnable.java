package cc.nuplex.unite.plugin;

import cc.nuplex.engine.util.http.HttpHandler;
import cc.nuplex.unite.Unite;

public class KeepAliveRunnable implements Runnable {

    @Override
    public void run() {
        HttpHandler.get(Unite.getInstance().getPlugin().getApiHost() + "/status", null, (response, code) -> {
            if (response == null || response.isJsonNull()) {
                Unite.getInstance().getPlugin().shutdown();
                return;
            }

            if (!response.getAsJsonObject().get("success").getAsBoolean()) {
                Unite.getInstance().getPlugin().shutdown();
                return;
            }
            Unite.getInstance().getPlugin().getLogger().info("API Server is still up and alive!");
        });
    }

}
