Replace:

```text
src/main/java/me/loving11ish/stormerwarpsreloaded/models/Warp.java
```

with this whole file:

```java
package me.loving11ish.stormerwarpsreloaded.models;

import me.loving11ish.stormerwarpsreloaded.StormerWarpsReloaded;
import me.loving11ish.stormerwarpsreloaded.utils.Message;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Warp {

    private Location location;
    private String name;
    private boolean opOnly;
    public static List<Warp> all = new ArrayList();

    public Warp(@Nonnull Location loc, String n) {
        this(loc, n, false);
    }

    public Warp(@Nonnull Location loc, String n, boolean opOnly) {
        Iterator var5 = all.iterator();

        while(var5.hasNext()) {
            Warp warp = (Warp) var5.next();
            if (warp.name == n) {
                warp.setLocation(loc);
                warp.setName(n);
                warp.setOpOnly(opOnly);
                createWarp(warp);
                return;
            }
        }

        this.location = loc;
        this.name = n;
        this.opOnly = opOnly;
        createWarp(this);
        all.add(this);
    }

    private static void createWarp(Warp warp) {
        String path = "warps." + warp.name + ".";
        StormerWarpsReloaded.i.getConfig().set(path + "x", warp.location.getX());
        StormerWarpsReloaded.i.getConfig().set(path + "y", warp.location.getY());
        StormerWarpsReloaded.i.getConfig().set(path + "z", warp.location.getZ());
        StormerWarpsReloaded.i.getConfig().set(path + "yaw", warp.location.getYaw());
        StormerWarpsReloaded.i.getConfig().set(path + "pitch", warp.location.getPitch());
        StormerWarpsReloaded.i.getConfig().set(path + "world", warp.location.getWorld().getName());
        StormerWarpsReloaded.i.getConfig().set(path + "opOnly", warp.opOnly);
        StormerWarpsReloaded.i.loadConfig();
    }

    public static void deleteWarp(Warp warp) {
        String path = "warps." + warp.name;
        StormerWarpsReloaded.i.getConfig().set(path, (Object)null);
        StormerWarpsReloaded.i.loadConfig();
        all.remove(warp);
    }

    public void delete() {
        deleteWarp(this);
    }

    public static Warp findWarp(String name) {
        Iterator var2 = all.iterator();

        while(var2.hasNext()) {
            Warp warp = (Warp) var2.next();
            if (warp.getName().equalsIgnoreCase(name)) {
                return warp;
            }
        }

        return null;
    }

    public static List<Warp> getWarps(boolean isOP) {
        if (isOP) {
            return all;
        } else {
            List<Warp> warps = new ArrayList();
            Iterator var3 = all.iterator();

            while(var3.hasNext()) {
                Warp warp = (Warp)var3.next();
                if (!warp.opOnly) {
                    warps.add(warp);
                }
            }

            return warps;
        }
    }

    public void warp(Player p) {
        Message.normal(p, "Going to " + this.getName());
        StormerWarpsReloaded.i.getFoliaLib().getImpl().teleportAsync(p, this.getLocation());
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "[Warp {" + this.location.toString() + "," + this.name + "}]";
    }

    public boolean isOpOnly() {
        return this.opOnly;
    }

    public void setOpOnly(boolean opOnly) {
        this.opOnly = opOnly;
    }
}
```
