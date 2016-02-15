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
     * An array of news items.
     */
    public static final List<NewsItem> ITEMS = new ArrayList<NewsItem>();

    /**
     * Clear the list of known news items.
     */
    public static void clear() {
        ITEMS.clear();
    }

    /**
     * Add a news item to the collection of known news items.
     * @param title news title
     * @param descr description, that is, beginning of the text
     * @param imageUri URL of the image
     * @param mainUri URL of the full article text
     */
    public static void addItem(String title, String descr, String imageUri, String mainUri) {
        Log.d("addNewsItem["+title+"]");
        Log.f();
        NewsItem item = new NewsItem(title, descr, imageUri, mainUri);
        ITEMS.add(item);
        Log.d("addNewsItem: size=" + ITEMS.size());
    }

    /**
     * A news item. The objects of this class are immutable.
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

        /**
         * Comparison by the title and the main URI.
         * @param o another object
         * @return true if o is a NewsItem whose title and mainUri match.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NewsItem newsItem = (NewsItem) o;

            if (title != null ? !title.equals(newsItem.title) : newsItem.title != null)
                return false;
            return !(mainUri != null ? !mainUri.equals(newsItem.mainUri) : newsItem.mainUri != null);

        }

        /**
         * Calculate the hash code, only title and mainUri are used.
         * @return hash code
         */
        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (mainUri != null ? mainUri.hashCode() : 0);
            return result;
        }

//        @Override
//        public String toString() {
//            return title;
//        }
    }
}
