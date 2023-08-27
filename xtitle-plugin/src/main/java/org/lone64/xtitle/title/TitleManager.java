package org.lone64.xtitle.title;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.cross.prefix.IPrefix;
import org.lone64.xtitle.api.cross.title.ITitleManager;
import org.lone64.xtitle.prefix.Prefix;

import java.util.*;

public class TitleManager implements ITitleManager {

    private final Map<String, IPrefix> prefixMap = new HashMap<>();

    @Override
    public ITitleManager enable() {
        this.prefixMap.clear();
        for (IPrefix prefix : XTitle.getSQLPrefix().getPrefixList()) {
            this.prefixMap.put(prefix.getName(), prefix);
        }
        return this;
    }

    @Override
    public void disable() {
        for (Map.Entry<String, IPrefix> entry : this.prefixMap.entrySet()) {
            IPrefix prefix = entry.getValue();
            XTitle.getSQLPrefix().savePrefix(prefix);
            for (UUID uuid : prefix.getPlayerList()) {
                if (XTitle.getSQLPlayer().hasPlayer(Bukkit.getOfflinePlayer(uuid), prefix.getName())) continue;
                XTitle.getSQLPlayer().addPlayer(uuid, prefix.getName());
            }
        }

        for (IPrefix prefix : XTitle.getSQLPrefix().getPrefixList()) {
            if (this.prefixMap.get(prefix.getName()) == null) {
                for (UUID uuid : XTitle.getSQLPrefixPlayer().getPlayerList()) {
                    if (prefix.getName().equals(XTitle.getSQLPrefixPlayer().fetchPrefix(Bukkit.getOfflinePlayer(uuid)))) {
                        XTitle.getSQLPrefixPlayer().delPrefixPlayer(Bukkit.getOfflinePlayer(uuid));
                    }
                }
                XTitle.getSQLPrefix().deletePrefix(prefix.getName());
            }
        }
    }

    @Override
    public boolean addNewPrefix(String name, String displayName) {
        if (this.prefixMap.get(name) != null) return false;
        this.prefixMap.put(name, new Prefix(name, displayName, "", ""));
        return true;
    }

    @Override
    public boolean deletePrefix(String name) {
        if (this.prefixMap.get(name) == null) return false;
        IPrefix prefix = this.prefixMap.get(name);
        for (UUID uuid : prefix.getPlayerList()) {
            if (XTitle.getPlayerManager().isPrefixPlayer(Bukkit.getOfflinePlayer(uuid))) {
                XTitle.getPlayerManager().delPrefixPlayer(Bukkit.getOfflinePlayer(uuid));
            }
        }
        this.prefixMap.remove(name);
        return true;
    }

    @Override
    public boolean addNewPlayer(String name, Player player) {
        if (fetchPrefix(name).getPlayerList().contains(player.getUniqueId())) return false;
        IPrefix prefix = fetchPrefix(name);
        prefix.addPlayer(player.getUniqueId());
        this.prefixMap.put(name, prefix);
        return true;
    }

    @Override
    public boolean isPrefix(String name) {
        return this.prefixMap.get(name) != null;
    }

    @Override
    public boolean isDisplayName(String displayName) {
        for (IPrefix prefix : getPrefixList()) {
            if (prefix.getDisplayName().equals(displayName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setDisplayName(String name, String displayName) {
        IPrefix pf = this.prefixMap.get(name);
        pf.setDisplayName(displayName);
        this.prefixMap.put(name, pf);
    }

    @Override
    public void setPrefix(String name, String prefix) {
        IPrefix pf = this.prefixMap.get(name);
        pf.setPrefix(prefix);
        this.prefixMap.put(name, pf);
    }

    @Override
    public void setSuffix(String name, String suffix) {
        IPrefix pf = this.prefixMap.get(name);
        pf.setSuffix(suffix);
        this.prefixMap.put(name, pf);
    }

    @Override
    public IPrefix fetchPrefix(String name) {
        return this.prefixMap.get(name);
    }

    @Override
    public IPrefix fetchDisplayName(String displayName) {
        for (IPrefix prefix : getPrefixList()) {
            if (prefix.getDisplayName().equals(displayName)) {
                return prefix;
            }
        }
        return null;
    }

    @Override
    public List<IPrefix> getPrefixList() {
        List<IPrefix> prefixList = new ArrayList<>();
        for (String name : this.prefixMap.keySet()) {
            prefixList.add(this.prefixMap.get(name));
        }
        return prefixList;
    }

}
