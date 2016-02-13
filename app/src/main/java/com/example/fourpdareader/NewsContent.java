package com.example.fourpdareader;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NewsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<NewsItem> ITEMS = new ArrayList<NewsItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, NewsItem> ITEM_MAP = new HashMap<String, NewsItem>();

    public static void clear() {
        ITEMS.clear();
    }
    public static void addItem(String title, String descr, String imageUri, String mainUri) {
        Log.d("addNewsItem["+title+"]");
        Log.f();
        NewsItem item = new NewsItem(title, descr, imageUri, mainUri);
        ITEMS.add(item);
        Log.d("addNewsItem: size=" + ITEMS.size());
    }
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//    }

//    private static void addItem(NewsItem item) {
//        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
//    }

//    private static NewsItem createDummyItem(int position) {
//        return new NewsItem(title, descr, imageUri, mainUri, String.valueOf(position), "Item " + position, makeDetails(position));
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
    public static class NewsItem {

        public final String title;
        public final String descr;
        public final String imageUri;
        public final String mainUri;

        public NewsItem(String title, String descr, String imageUri, String mainUri) {
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
