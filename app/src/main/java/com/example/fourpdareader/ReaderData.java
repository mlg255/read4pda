package com.example.fourpdareader;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ReaderData {
    public static ReaderData theInstance = new ReaderData();
    static final String URL1 = "http://4pda.ru/page/1/";
    private AsyncTask mLoadTask;
    Document mDocument;
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
                    mDocument = document;
                    Log.d("document="+document);
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("\n\n\n=====================\n\n\n");
                    Elements articles = getArticles(document);
                    Log.d("sel: "+articles);
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("els size = " + articles.size());
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("name:"+articles.get(0).getElementsByAttributeValue("itemprop","name"));
                    Log.d("name:"+getName(articles.get(0)));
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("descr:"+articles.get(0).getElementsByAttributeValue("itemprop","description"));
                    Log.d("descr:"+getDescription(articles.get(0)));
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("\n\n\n=====================\n\n\n");
                    Log.d("\n\n\n=====================\n\n\n");
                }
            };
            at.execute();
            mLoadTask = at;
        }


    }

    static Elements getArticles(Document document) {
        return document.select("article.post");
    }
    static String getName(Element e) {
        return e.getElementsByAttributeValue("itemprop","name").text();
    }
    static String getDescription(Element e) {
        return e.getElementsByAttributeValue("itemprop","description").text();
    }
    static String getImageUrl(Element e) {
        return e.getElementsByAttributeValue("itemprop","image").attr("src");
    }
    static String getFullUrl(Element e) {
        return e.getElementsByAttributeValue("itemprop","url").attr("href");
    }

    Document wtLoad(String url) {
        try {
            Document doc  = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
