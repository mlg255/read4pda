package com.example.fourpdareader;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class ReaderDataTest extends ApplicationTestCase<Application> {
    public ReaderDataTest() {
        super(Application.class);
    }

    @Test
    public void test_addition2_isCorrect() throws Exception {
        assertThat(5, is(2 + 2));
    }

    @Test
    public void test_loading() {
        assertThat(ReaderData.theInstance, notNullValue());
        Document document = ReaderData.theInstance.wtLoad(ReaderData.URL1);
        assertThat(document, notNullValue());
        Elements articles = ReaderData.getArticles(document);
        assertThat(articles, notNullValue());
        assertThat(articles.size() > 0, is(true));
        for (Element a : articles) {
            String s = a.toString();
            assertThat(s.startsWith("<article"), is(true));
            assertThat(s.endsWith("</article>"), is(true));
        }
        Log.d("~~~~~~~~~");
        assertThat(6, is(2 + 2));
    }
    @Test
    public void test_access() {
        String someHtml = "<html><head></head><body>" +
                "<article class=\"post\" data-ztm=\"0:275879:\" itemscope itemtype=\"http://schema.org/Article\">\n" +
                "\t<div class=\"visual\">\n" +
                "\t\t<a class=\"raIXg7tmf\" href=\"http://4pda.ru/2016/02/11/275879/\" title=\"Инсайды #441: HTC One M10, Apple A10, секрет Evleaks и Android Marshmallow для Samsung\">\n" +
                "\t\t\t<img itemprop=\"image\" id=\"hb275879\" width=\"400\" height=\"300\" src=\"http://s.4pda.to/9DGFsJbz1sLgqz11hYz1z1djtrxcfGvL1tKQy4W9.jpg\" alt=\"Инсайды #441: HTC One M10, Apple A10, секрет Evleaks и Android Marshmallow для Samsung\" title=\"Инсайды #441: HTC One M10, Apple A10, секрет Evleaks и Android Marshmallow для Samsung\"/>\n" +
                "\t\t</a>\n" +
                "\t\t<div class=\"v-panel\"><a href=\"http://4pda.ru/2016/02/11/275879/#comments\" class=\"v-count\" title=\"Комментарии к Инсайды #441: HTC One M10, Apple A10, секрет Evleaks и Android Marshmallow для Samsung\">39</a>\n" +
                "\t\t\t<div class=\"p-description\">\n" +
                "\t\t\t\t<em class=\"date\">11.02.16</em>\n" +
                "\t\t\t\t<span class=\"autor\"><a href=\"http://4pda.ru/forum/index.php?showuser=1796455\">Ekzer</a></span><span class=\"v-count1\" title=\"Количество просмотров\"></span>\n" +
                "\t\t\t\t<meta itemprop=\"datePublished\" content=\"2016-02-11T19:28:59+00:00\"/>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "\t<div class=\"description\">\n" +
                "\t\t<h1 class=\"list-post-title\" itemprop=\"headline\"><a rel=\"bookmark\" href=\"http://4pda.ru/2016/02/11/275879/\" title=\"Инсайды #441: HTC One M10, Apple A10, секрет Evleaks и Android Marshmallow для Samsung\" itemprop=\"url\"><span itemprop=\"name\">Инсайды #441: HTC One M10, Apple A10, секрет Evleaks и Android Marshmallow для Samsung</span></a></h1>\n" +
                "\t\t<div itemprop=\"description\"><p style=\"text-align: justify;\">В сети за последние 24 часа: Evleaks собирается \"взорвать мозг\" поклонникам Windows; TSMC может стать единственным производителем процессоров для iPhone 7; в Android 6.0 для Samsung Galaxy S6 и Galaxy S6 Edge будет внедрено множество новшеств; HTC One M10 оснастят ультрабыстрым гибридным автофокусом; смарт-часы HTC могут поступить в продажу до конца апреля; Samsung подарит Gear VR каждому предзаказавшему Galaxy S7.</p></div>\n" +
                "\t\t<span class=\"bg-shadow\">&nbsp;</span>\n" +
                "\t</div>\n" +
                "\t<div class=\"more-box\">\n" +
                "\t\t<div class=\"meta\"># <a href=\"/tag/samsung-galaxy-s6/\" rel=\"tag\">Samsung Galaxy S6</a>, <a href=\"/tag/insaydyi/\" rel=\"tag\">инсайды</a>, <a href=\"/tag/samsung-gear-vr/\" rel=\"tag\">Samsung Gear VR</a>, <a href=\"/tag/samsung-galaxy-s7/\" rel=\"tag\">Samsung Galaxy S7</a>, <a href=\"/tag/google-android-6-0-marshmallow/\" rel=\"tag\">Google Android 6.0 Marshmallow</a>, <a href=\"/tag/apple-a10/\" rel=\"tag\">Apple A10</a>, <a href=\"/tag/htc-one-m10/\" rel=\"tag\">HTC One M10</a></div>\n" +
                "\t\t<a href=\"http://4pda.ru/2016/02/11/275879/\" class=\"btn-more\">далее</a>\n" +
                "\t</div>\n" +
                "</article>" +
                "</body></html>"
                ;
        Document document = Jsoup.parse(someHtml);
        assertThat(document, notNullValue());
        Elements articles = ReaderData.getArticles(document);
        assertThat(articles, notNullValue());
        assertThat(articles.size(), is(1));
        Element article = articles.get(0);
        String s = article.toString();
        assertThat(s.startsWith("<article"), is(true));
        assertThat(s.endsWith("</article>"), is(true));
        String _title="Инсайды #441: HTC One M10, Apple A10, секрет Evleaks и Android Marshmallow для Samsung";
        String _content="В сети за последние 24 часа: Evleaks собирается \"взорвать мозг\" поклонникам Windows; TSMC может стать единственным производителем процессоров для iPhone 7; в Android 6.0 для Samsung Galaxy S6 и Galaxy S6 Edge будет внедрено множество новшеств; HTC One M10 оснастят ультрабыстрым гибридным автофокусом; смарт-часы HTC могут поступить в продажу до конца апреля; Samsung подарит Gear VR каждому предзаказавшему Galaxy S7.";
        String _image="http://s.4pda.to/9DGFsJbz1sLgqz11hYz1z1djtrxcfGvL1tKQy4W9.jpg";
        String _fullUrl ="http://4pda.ru/2016/02/11/275879/";
        assertThat(ReaderData.getName(article), is(_title));
        assertThat(ReaderData.getDescription(article), is(_content));
        assertThat(ReaderData.getImageUrl(article), is(_image));
        assertThat(ReaderData.getFullUrl(article), is(_fullUrl));
        assertThat("all done", 4, is(15));
    }
}
