package net.korvin.utils.TagStack;

import java.util.Objects;

public class TagStackBase {
    static final int UNEQUAL = -100;

    static int checkSubset(TagStack ths, TagStack ots, int offset) {
        TagStack.Chunk a = ths.chunk, b = ots.chunk;
        int ai = a.added, bi = b.added;

        while (ai > 0 && offset-- > 0) {
            ai--;
            if (ai == 0 && a.parent != null) { a = a.parent; ai = a.added; }
        }

        while (ai > 0 && bi > 0) {
            String aCmp = a.tags[--ai], bCmp = b.tags[--bi];
            if (!Objects.equals(aCmp, bCmp)) return UNEQUAL;

            if (ai == 0 && a.parent != null) { a = a.parent; ai = a.added; }
            if (bi == 0 && b.parent != null) { b = b.parent; bi = b.added; }
        }
        return Integer.compare(ai, bi);
    }

    static int compare(TagStack aObj, TagStack bObj) {
//        int diff = aObj.size() - bObj.size();
//        if (0 != diff) return diff;
        TagStack.Chunk a = aObj.chunk, b = bObj.chunk;
        int ai = a.added, bi = b.added;

        while (ai > 0 && bi > 0) {
            String aCmp = a.tags[--ai], bCmp = b.tags[--bi];

            int cmp = aCmp.compareTo(bCmp);
            if (0 != cmp) return cmp;

            if (ai == 0 && a.parent != null) { a = a.parent; ai = a.added; }
            if (bi == 0 && b.parent != null) { b = b.parent; bi = b.added; }
        }
        return Integer.compare(ai, bi);
    }
}
