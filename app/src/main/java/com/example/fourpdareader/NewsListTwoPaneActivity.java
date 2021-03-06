package com.example.fourpdareader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * An activity representing a list of NewsList. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NewsDetailSinglePaneActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NewsListTwoPaneActivity extends AppCompatActivity {

    /** Whether or not the activity is in two-pane mode, i.e. running on a tablet device. */
    private boolean mTwoPane;

    /** The adapter for the list of articles */
    private SimpleItemRecyclerViewAdapter mAdapter;

    /** The selected article. NewsItem instances are immutable. */
    private static NewsContent.NewsItem sLastSelectedNewsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("NewsListTwoPaneActivity.onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.news_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.news_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            if (sLastSelectedNewsItem != null) {
                mAdapter.openInThePane2(sLastSelectedNewsItem);
            }
        }

        ReaderData.theInstance.load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ReaderData.setOnChangeListener(mOnChangeListener);
    }

    @Override
    protected void onPause() {
        ReaderData.setOnChangeListener(null);
        super.onPause();
    }

    private ReaderData.OnChangeListener mOnChangeListener = new ReaderData.OnChangeListener() {
        @Override
        public void onDataSetChanged() {
            Log.d("mAdapter.notifyDataSetChanged();");
            mAdapter.notifyDataSetChanged();
        }
    };

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new SimpleItemRecyclerViewAdapter(NewsContent.ITEMS);
        recyclerView.setAdapter(mAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<NewsItemViewHolder> {

        private final List<NewsContent.NewsItem> mValues;
        private final int TYPE_UNSELECTED = 0;
        private final int TYPE_SELECTED = 1;

        public SimpleItemRecyclerViewAdapter(List<NewsContent.NewsItem> items) {
            mValues = items;
        }

        /**
         * Check if this news item is selected in the title list
         * @param newsItem
         * @return true if the uri and the title of newsItem match
         */
        boolean isSelected(NewsContent.NewsItem newsItem) {
            return newsItem != null && newsItem.equals(sLastSelectedNewsItem);
        }

        /**
         * Set selection on the news item
         * @param newsItem
         * @param selected false to clear selection
         */
        void setSelected(NewsContent.NewsItem newsItem, boolean selected) {
            if (selected) {
                sLastSelectedNewsItem = newsItem;
            } else {
                sLastSelectedNewsItem = null;
            }
        }
        @Override
        public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(viewType == TYPE_UNSELECTED
                            ? R.layout.news_list_content
                            : R.layout.news_list_content_sel, parent, false);
            return new NewsItemViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(final NewsItemViewHolder holder, int position) {
            Log.d("onBindViewHolder(" + holder + "," + position + ")");
            NewsContent.NewsItem item = mValues.get(position);
            holder.mItem = item;
            holder.mTitleView.setText(item.title);
            holder.mContentView.setText(item.descr);
            if (!ImageLoader.loadImage(holder)) {
                holder.mImageView.setImageResource(R.drawable.default_4pda);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsContent.NewsItem newsItem = holder.mItem;
                    if (isSelected(newsItem) && !mTwoPane) {
                        setSelected(newsItem, false);
                        notifyDataSetChanged();
                        return;
                    }
                    setSelected(newsItem, true);
                    notifyDataSetChanged();

                    if (mTwoPane) {
                        openInThePane2(newsItem);
                    } else {
                        openInActivity(newsItem);
                    }
                }
            });
        }

        private void openInActivity(NewsContent.NewsItem newsItem) {
            Context context = NewsListTwoPaneActivity.this;
            Intent intent = new Intent(context, NewsDetailSinglePaneActivity.class);
            intent.putExtra(NewsDetailFragment.ARG_ITEM_URL, newsItem.mainUri);
            intent.putExtra(NewsDetailFragment.ARG_ITEM_TITLE, newsItem.title);

            context.startActivity(intent);
        }

        private void openInThePane2(NewsContent.NewsItem newsItem) {
            Bundle arguments = new Bundle();
            arguments.putString(NewsDetailFragment.ARG_ITEM_URL, newsItem.mainUri);
            arguments.putString(NewsDetailFragment.ARG_ITEM_TITLE, newsItem.title);
            NewsDetailFragment fragment = new NewsDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.news_detail_container, fragment)
                    .commit();
        }

        @Override
        public int getItemCount() {
            Log.f();
            Log.d("getItemCount ==> "+mValues.size());
            return mValues.size();
        }

        @Override
        public int getItemViewType(int position) {
            NewsContent.NewsItem item = mValues.get(position);
            return isSelected(item) ? TYPE_SELECTED : TYPE_UNSELECTED;
        }
    }

    public static class NewsItemViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mTitleView;
        public final TextView mContentView;
        public final int mType;
        public NewsContent.NewsItem mItem;

        public NewsItemViewHolder(View view, int type) {
            super(view);
            mType = type;
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
