package com.example.fourpdareader;

import android.app.Application;
import android.graphics.Bitmap;
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
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class ImageLoaderTest extends ApplicationTestCase<Application> {
    String _image="http://s.4pda.to/9DGFsJbz1sLgqz11hYz1z1djtrxcfGvL1tKQy4W9.jpg";
    public ImageLoaderTest() {
        super(Application.class);
    }

    @Test
    public void test_addition2_isCorrect() throws Exception {
        assertThat(5, is(2 + 2));
    }

    @Test
    public void test_download() throws Exception {
        Bitmap bm = ImageLoader.downloadDownsampledBitmap(_image);
        assertThat(bm, not(is((Object)null)));
        int w = bm.getWidth();
        int h = bm.getHeight();
        assertThat(w, is(400/8));
        assertThat(h, is(300/8));
    }

}
