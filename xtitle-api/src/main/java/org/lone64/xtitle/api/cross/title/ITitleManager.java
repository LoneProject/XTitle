package org.lone64.xtitle.api.cross.title;

import org.bukkit.entity.Player;
import org.lone64.xtitle.api.cross.prefix.IPrefix;

import java.util.List;

public interface ITitleManager {

    ITitleManager enable();

    void disable();

    boolean addNewPrefix(String name, String displayName);

    boolean deletePrefix(String name);

    boolean addNewPlayer(String name, Player player);

    boolean isPrefix(String name);

    boolean isDisplayName(String displayName);

    void setDisplayName(String name, String displayName);

    void setPrefix(String name, String prefix);

    void setSuffix(String name, String suffix);

    IPrefix fetchPrefix(String name);

    IPrefix fetchDisplayName(String displayName);

    List<IPrefix> getPrefixList();

}
