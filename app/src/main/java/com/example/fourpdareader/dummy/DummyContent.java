package com.example.fourpdareader.dummy;

import com.example.fourpdareader.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static void clear() {
        ITEMS.clear();
    }
    public static void addItem(String title, String descr, String imageUri, String mainUri) {
        Log.d("addNewsItem["+title+"]");
        Log.f();
        DummyItem item = new DummyItem(title, descr, imageUri, mainUri);
        ITEMS.add(item);
        Log.d("addNewsItem: size=" + ITEMS.size());
    }
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//    }

//    private static void addItem(DummyItem item) {
//        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
//    }

//    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(title, descr, imageUri, mainUri, String.valueOf(position), "Item " + position, makeDetails(position));
//    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {

        public final String title;
        public final String descr;
        public final String imageUri;
        public final String mainUri;

        public DummyItem(String title, String descr, String imageUri, String mainUri) {
            this.title = title;
            this.descr = descr;
            this.imageUri = imageUri;
            this.mainUri = mainUri;
        }

//        @Override
//        public String toString() {
//            return content;
//        }
    }
}
