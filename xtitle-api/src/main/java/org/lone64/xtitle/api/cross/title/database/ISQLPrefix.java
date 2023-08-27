package org.lone64.xtitle.api.cross.title.database;

import org.lone64.xtitle.api.cross.prefix.IPrefix;

import java.util.List;

public interface ISQLPrefix {

    ISQLPrefix init();

    void savePrefix(IPrefix prefix);

    void deletePrefix(String name);

    boolean isPrefix(String name);

    IPrefix fetchPrefix(String name);

    List<IPrefix> getPrefixList();

}
