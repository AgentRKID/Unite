package cc.nuplex.unite.runnable;

import cc.nuplex.unite.Unite;
import cc.nuplex.unite.UniteGeneral;
import cc.nuplex.unite.profile.Profile;

import java.util.Collection;

public class RefreshRunnable implements Runnable {

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        Collection<Profile> profiles = Unite.getInstance().getProfileHandler().getProfiles();

        for (Profile profile : profiles) {
            Unite.getInstance().getProfileHandler().load(profile);
        }
        UniteGeneral.getPlugin().getLogger().info("Refreshed " + profiles.size() + "'s in " + (System.currentTimeMillis() - start) + "ms!");
    }

}
