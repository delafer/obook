package net.korvin.utils;

import java.util.Objects;

public class TagStack {

    private static final int UNEQUAL = -100;
    private static final int CHUNK_SIZE = 8;

    Chunk chunk = new Chunk();
    private int size = 0;

    public int size() {
        return this.size;
    }

    public boolean empty() {
        return size() == 0;
    }

    /**
     * Pushes an item onto the top of this stack.
     */
    public void push(String tag) {
        if (chunk.added < CHUNK_SIZE) {
            size++;
            chunk.tags[chunk.added++] = tag;
        } else {
            chunk = new Chunk(chunk);
            push(tag);
        }
    }

    /**
     * Removes the object at the top of this stack and returns that object as the value of this function.
     */
    public String pop() {

        if (chunk.added > 1) {
            size--;
            return chunk.tags[--chunk.added];
        }
        else
        if (chunk.added > 0) {
            var ret = chunk.tags[0];

            if (chunk.parent != null) {
                chunk = chunk.parent;
            } else {
                chunk.added = 0;
            }
            size--;
            return ret;
        }
        else {
            return null;
        }
    }

    /**
     * Removes the given object if such exists at the top of this stack and returns that object as the value of this function.
     */
    public String pop(String tag) {
        return tag.equals(this.peek()) ? this.pop() : null;
    }

    /**
     * Looks at the object at the top of this stack without removing it from the stack.
     */
    public String peek() {
        return chunk.added > 0 ? chunk.tags[chunk.added-1] : null;
    }

    /**
     * Parses xml path string like this "html/head/title"
     * and converts it to TackStack object
     */
    public static TagStack parseTags(String tagString) {
        TagStack ret = new TagStack();

        int at = 0;
        for (int j = 0; j < tagString.length(); j++) {
            if ('/' == tagString.charAt(j)) {
                addParsedTag(tagString, ret, at, j);
                at = j+1;
            }
        }
        addParsedTag(tagString, ret, at, tagString.length());

        return ret;
    }

    private static void addParsedTag(String tagStr, TagStack ret, int at, int to) {
        if (to > at)
            ret.push(tagStr.substring(at, to));
    }

    private int checkSubset(TagStack ots) {
        Chunk a = this.chunk, b = ots.chunk;
        int ai = a.added, bi = b.added;

        while (ai > 0 && bi > 0) {
            String aCmp = a.tags[--ai], bCmp = b.tags[--bi];

            if (!Objects.equals(aCmp, bCmp)) return UNEQUAL;

            if (ai == 0 && a.parent != null) { a = a.parent; ai = a.added; }
            if (bi == 0 && b.parent != null) { b = b.parent; bi = b.added; }
        }
        return Integer.compare(ai, bi);
    }

    public boolean contains(TagStack toCheck) {
        return this.size() >= toCheck.size() && checkSubset(toCheck) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof TagStack ots) {

            if (this.size() != ots.size()) return false;
            return this.checkSubset(ots) == 0;

        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        var curr = this.chunk;
        int j = curr.added;
        while (j > 0) {
            hash = hash * 31 + curr.tags[--j].hashCode();
            if (j == 0 && curr.parent != null) {
                curr = curr.parent;
                j = curr.added;
            }
        }
        return hash;
    }

    @Override
    public String toString() {
        return "TagStack(" + chunk + ')';
    }

    static class Chunk {
        String[] tags = new String[CHUNK_SIZE];
        int added;
        Chunk parent;

        Chunk(Chunk parent) {
            this.parent = parent;
        }

        Chunk() {}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (parent != null) {
                sb.append(parent.toString());
                sb.append('/');
            }

            for (int j = 0; j<added; j++) {
                if (j != 0) sb.append('/');
                sb.append(tags[j]);
            }

            return sb.toString();
        }
    }
}
