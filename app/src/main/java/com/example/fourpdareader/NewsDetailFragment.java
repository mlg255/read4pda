package com.example.fourpdareader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A fragment representing a single ANews detail screen.
 * This fragment is either contained in a {@link NewsListTwoPaneActivity}
 * in two-pane mode (on tablets) or a {@link NewsDetailSinglePaneActivity}
 * on handsets.
 */
public class NewsDetailFragment extends Fragment {
    /**
     * The fragment argument representing the news item URL
     */
    public static final String ARG_ITEM_URL = "item_url";
    /**
     * The fragment argument representing the news item title
     */
    public static final String ARG_ITEM_TITLE = "item_title";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // we don't set the title in two-pane mode
//        if (getArguments().containsKey(ARG_ITEM_TITLE)) {
//
// we don't need it... for now
//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(getArguments().getString(ARG_ITEM_TITLE));
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        WebView rootView = (WebView) inflater.inflate(R.layout.news_detail, container, false);
        rootView.getSettings().setJavaScriptEnabled(true);
        rootView.loadUrl(getArguments().getString(ARG_ITEM_URL));
        return rootView;
    }
}
