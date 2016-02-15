package com.example.fourpdareader;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ReaderData {
    /** the singleton */
    public static ReaderData theInstance = new ReaderData();
    /** URL of the 1st page */
    static final String URL1 = "http://4pda.ru/page/1/";
    /** The task that loads and parses the first page */
    private AsyncTask mLoadTask;
    /** A Listener to inform the Activity about arrived data */
    static OnChangeListener sListener = null;

    /**
     * Asynchronously load and parse the first page, then call the current OnChangeListener
     * on the UI thread.
     * If an error happens, the result Document is null.
     * TODO handle null result
     */
    void load() {
        if (mLoadTask == null) {
            AsyncTask<Void, Void, Document> at = new AsyncTask<Void, Void, Document>() {
                @Override
                protected void onPreExecute() {
                    Log.d("ReaderData.load.onPreExecute");
                    super.onPreExecute();
                }

                @Override
                protected Document doInBackground(Void... params) {
                    Log.d("ReaderData.load.doInBackground");
                    return wtLoad(URL1);
                }

                @Override
                protected void onPostExecute(Document document) {
                    Log.d("ReaderData.load.onPostExecute");
                    super.onPostExecute(document);
                    mLoadTask = null;
                    Elements articles = getArticles(document);
                    setContent(articles);
                }
            };
            at.execute();
            mLoadTask = at;
        }
    }

    /**
     * Get the list of articles
     * @param document
     * @return a list, each article is an Element in it
     */
    static Elements getArticles(Document document) {
        if (document == null) {
            return null;
        }
        return document.select("article.post");
    }

    /**
     * Get the title of an article.
     * @param e an article from the list of articles
     * @return
     */
    static String getName(Element e) {
        return e.getElementsByAttributeValue("itemprop","name").text();
    }

    /**
     * Get a short "preview" version of the text of the article.
     * @param e an article from the list of articles
     * @return
     */
    static String getDescription(Element e) {
        return e.getElementsByAttributeValue("itemprop","description").text();
    }

    /**
     * Get the URL of the image shown in the list of news.
     * @param e an article from the list of articles
     * @return
     */
    static String getImageUrl(Element e) {
        return e.getElementsByAttributeValue("itemprop","image").attr("src");
    }

    /**
     * Get the URL of the full version of the article.
     * @param e an article from the list of articles
     * @return
     */
    static String getFullUrl(Element e) {
        return e.getElementsByAttributeValue("itemprop","url").attr("href");
    }

    /**
     * Extract information from articles to NewsContent.ITEMS.
     *
     * @param articles a list of articles
     */
    static void setContent(Elements articles) {
        NewsContent.clear();
        if (articles != null) {
            for (Element a : articles) {
                NewsContent.addItem(getName(a), getDescription(a), getImageUrl(a), getFullUrl(a));
            }
        }
        Log.d("sListener="+sListener);
        if (sListener != null) {
            sListener.onDataSetChanged();
        }
    }

    /**
     * Download on the worker thread.
     * @param url
     * @return
     */
    Document wtLoad(String url) {
        try {
            Document doc  = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This interface lets the Activity know that the loading is complete.
     */
    public interface OnChangeListener {
        /**
         * Invoked after the page is loaded and parsed.
         * The method is called on the UI thread.
         */
        void onDataSetChanged();
    }

    /**
     * Set the listener to refresh the RecyclerView adapter
     * To be run on the UI thread.
     * @param listener
     */
    public static void setOnChangeListener(OnChangeListener listener) {
        sListener = listener;
        if (sListener != null) {
            sListener.onDataSetChanged();
        }
    }
}
