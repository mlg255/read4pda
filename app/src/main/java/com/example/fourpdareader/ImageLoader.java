package com.example.fourpdareader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class ImageLoader {
    /**
     * Downsampling constant.
     * We know that the images are 300x400, while we need 30x40.
     */
    public static final int BITMAP_IN_SAMPLE_SIZE = 8;
    /**
     * An auxiliary wrapper for a bitmap.
     */
    static class BitmapHolder {
        Bitmap bitmap;
        // TODO attributes to support removal from the cache
        // E.g. if we add a weak reference to the bitmap, in onLowMemory()/onTrimMemory()
        // we can set all fields BitmapHolder.bitmap to null and be sure that only
        // currently referenced bitmaps survive garbage collection.
    }

    /** The bitmap cache */
    static LruCache<String, BitmapHolder> sCache = new LruCache<>(60);

    /**
     * Retrieve a BitmapHolder from the cache.
     * @param url
     * @return the BitmapHolder associated with url, or null
     */
    static BitmapHolder getBitmapHolder(String url) {
        synchronized (sCache) {
            return sCache.get(url);
        }
    }

    /**
     * If there's no entry for url in the cache, associate it with newBitmapHolder and return
     * newBitmapHolder, else return the existing association.
     * Why: the fact that the association exists means that probably some views use the
     * already existing bitmap, so we should use the existing copy of the bitmap.
     * @param url
     * @param newBitmapHolder
     * @return the BitmapHolder associated with the url
     */
    static BitmapHolder haveBitmapHolder(String url, BitmapHolder newBitmapHolder) {
        synchronized (sCache) {
            BitmapHolder bh = sCache.get(url);
            if (bh != null) {
                return bh;
            } else {
                sCache.put(url, newBitmapHolder);
                return newBitmapHolder;
            }
        }
    }

    /**
     * Load image to the view specified by the adapter view holder
     * @param holder an adapter's view holder
     * @return true if found the image in the cache, false if sheduled asynchronous loading
     */
    public static boolean loadImage(final NewsListTwoPaneActivity.NewsItemViewHolder holder) {
        final String url = holder.mItem.imageUri;
        if (setImageViewToBitmap(holder, url, getBitmapHolder(url))) {
            // could find in the cache, nothing to load!
            return true;
        }
        final BitmapHolder bh = new BitmapHolder();
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                // this may get executed a while later, so check again
                BitmapHolder existing = getBitmapHolder(url);
                if (existing != null) {
                    // some other AsyncTask have downloaded it
                    return existing.bitmap;
                }
                try {
                    Bitmap bitmap = downloadDownsampledBitmap(url);
                    return bitmap;
                } catch (OutOfMemoryError e) {
                    Log.d(e);
                    return null;
                } catch (IOException e) {
                    Log.d(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap == null) {
                    // it failed
                    return;
                }
                bh.bitmap = bitmap;
                setImageViewToBitmap(holder, url, haveBitmapHolder(url, bh));
            }
        }.execute();
        return false;
    }

    /**
     * Download and downsample a bitmap from 4pda.ru by BITMAP_IN_SAMPLE_SIZE.
     * The images at 4pda are 400x300, while we need 40x30 dpi.
     * @param url image URL
     * @return a downsampled bitmap
     * @throws IOException
     * @throws OutOfMemoryError
     */
    static Bitmap downloadDownsampledBitmap(String url) throws IOException {
        Bitmap bitmap=null;
        URL imageUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
        try {
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            try {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inSampleSize = BITMAP_IN_SAMPLE_SIZE;
                bitmap = BitmapFactory.decodeStream(is, null, opt);
                return bitmap;
            } finally {
                is.close();
            }
        } finally {
            conn.disconnect();
        }
    }
    /**
     * Set the ImageView specified by viewHolder to the bitmap specified by bitmapHolder,
     * provided that viewHolder is (still) associated with the url.
     * @param viewHolder a view holder for RecyclerView.Adapter that once was associated with url
     * @param url the image's location
     * @param bitmapHolder holds the bitmap downloaded from url
     * @return true if the image view was set to the bitmap, false if the checks failed
     */
    private static boolean setImageViewToBitmap(NewsListTwoPaneActivity.NewsItemViewHolder viewHolder, String url, BitmapHolder bitmapHolder) {
        // A lot could happen since this AsyncTask was scheduled for execution,
        // now the viewHolder may be bound to another list item,
        // the image view may be not on screen and waiting for GC,
        // the image may be already loaded by another AsyncTask
        boolean canSet = bitmapHolder != null
                      && bitmapHolder.bitmap != null
                      && viewHolder.mImageView != null
                      && viewHolder.mItem != null
                      && url.equals(viewHolder.mItem.imageUri);
        if (canSet) {
            viewHolder.mImageView.setImageBitmap(bitmapHolder.bitmap);
        }
        return canSet;
    }
}

