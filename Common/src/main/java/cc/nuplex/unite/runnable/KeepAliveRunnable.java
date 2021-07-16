package cc.nuplex.unite.runnable;

import cc.nuplex.engine.util.http.HttpHandler;
import cc.nuplex.unite.Unite;
import cc.nuplex.unite.UniteGeneral;

public class KeepAliveRunnable implements Runnable {

    @Override
    public void run() {
        HttpHandler.get(UniteGeneral.getPlugin().getApiHost() + "/status", null, (response, code) -> {
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
