package org.lone64.xtitle.util;

import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static Iterable<MatchResult> allMatches(final Pattern p, final CharSequence input) {
        return () -> new Iterator<>() {
            final Matcher matcher = p.matcher(input);
            MatchResult pending;

            public boolean hasNext() {
                if (pending == null && matcher.find()) {
                    pending = matcher.toMatchResult();
                }
                return pending != null;
            }

            public MatchResult next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                MatchResult next = pending;
                pending = null;
                return next;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static String fetchString(String s, String s1, String s2) {
        s = s.substring(s.indexOf(s1) + 1);
        s = s.substring(0, s.indexOf(s2));
        return s;
    }

    public static ItemStack getUsed(ItemStack is, int amount) {
        if (is.getAmount() == 1) {
            return null;
        } else {
            is.setAmount(is.getAmount() - amount);
            return is;
        }
    }

}
