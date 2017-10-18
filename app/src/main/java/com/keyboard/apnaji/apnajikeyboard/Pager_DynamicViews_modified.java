package com.keyboard.apnaji.apnajikeyboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;


public class Pager_DynamicViews_modified extends PagerAdapter implements View.OnClickListener {

    Context context;
    File mPngFile;
    String[] items = new String[100];
    int NUMBER_OF_COLUMNS = 7, NUMBER_OF_ROWS = 2;
    static int VIEWPAGER_HEIGHT_ONEROW = 110, STICKER_HEIGHT = 100, VIEWPAGER_HEIGHT_TWOROWS = (Pager_DynamicViews_modified.VIEWPAGER_HEIGHT_ONEROW * 2);


    public Pager_DynamicViews_modified(Context context, String[] items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        if (items.length % (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS) == 0) {
            return (items.length / (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS));
        } else {
            return (items.length / (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS)) + 1;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        System.out.println("Pager: instantiateItem(): " + position+" :: "+ collection.getChildCount());
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.food, collection, false);
        final File imagesDir = new File(context.getFilesDir(), "images");
        imagesDir.mkdirs();

        ImageView[] imageView = new ImageView[100];
        int width = MainActivity.WINDOW_WIDTH;
        layout.setBackgroundColor(0xFFFFFFFF);
        LinearLayout linearLayout1;
        LinearLayout linearLayout2,linearLayout3;
        LinearLayout.LayoutParams layoutParams1, layoutParams2, layoutParams3, layoutParams5, layoutParamsViewPager, layoutParamsEntireView;
        int loopStart, loopEnd;

        linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        layoutParamsViewPager = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, VIEWPAGER_HEIGHT_TWOROWS);//220
      //  ((CustomKeyboard) context).viewPager.setLayoutParams(layoutParamsViewPager);
        layoutParamsEntireView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ((CustomKeyboard) context).tabView.setLayoutParams(layoutParamsEntireView);
        layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, VIEWPAGER_HEIGHT_TWOROWS);
        linearLayout1.setLayoutParams(layoutParams1);
        linearLayout1.setBackgroundColor(0xff0ff000);

        linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setBackgroundColor(0xff00ff00);
        layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, VIEWPAGER_HEIGHT_ONEROW);
        linearLayout2.setLayoutParams(layoutParams2);

        linearLayout3 = new LinearLayout(context);
        linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout3.setBackgroundColor(0xffff00ff);

        layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, VIEWPAGER_HEIGHT_ONEROW);
        linearLayout3.setLayoutParams(layoutParams3);

       // layoutParams5 = new LinearLayout.LayoutParams((width / NUMBER_OF_COLUMNS), STICKER_HEIGHT);
        layoutParams5 = new LinearLayout.LayoutParams(50,50);
        layoutParams5.topMargin = 5;
        layoutParams5.gravity = Gravity.CENTER;

        if (items.length > (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS) * (position + 1)) {
            loopStart = (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS * position);
            loopEnd = (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS * (position + 1));
        } else {
            loopStart = (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS * position);
            loopEnd = items.length;
        }

        for (int l = loopStart; l < loopEnd; l++)
        {
            imageView[l] = new ImageView(context);
            imageView[l].setLayoutParams(layoutParams5);
            //imageView[l].setBackgroundColor(0xff009943);
            imageView[l].setOnClickListener(this);

            String mDrawableName = items[l];
            imageView[l].setTag(mDrawableName);


            System.out.println("PAGER IMAGENAME: "+ mDrawableName);
            System.out.println("PAGER IMAGENAME: "+ context.getPackageName());

            int resID = context.getResources().getIdentifier(mDrawableName, "mipmap", context.getPackageName());

            if (l >= (NUMBER_OF_COLUMNS * NUMBER_OF_ROWS * position) + NUMBER_OF_COLUMNS)
            {
                linearLayout3.addView(imageView[l]);
            } else {
                linearLayout2.addView(imageView[l]);
            }
        }
        System.out.println("pager linearLayout2: "+ linearLayout2.getChildCount());
        System.out.println("pager linearLayout3: "+ linearLayout3.getChildCount());
        linearLayout1.setBackgroundColor(0xffff0034);
        linearLayout1.addView(linearLayout2);
        layout.addView(linearLayout1);

        collection.addView(layout);
        System.out.println("pager layout: "+ layout.getChildCount());
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        System.out.println("PAGER DestroyITEM: " + position);
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        System.out.println("PAGER isViewFromObject: "+ view == object);
        return view == object;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        return context.getString(customPagerEnum.getTitleResId());
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context,"ganesh: "+view.getTag(), Toast.LENGTH_LONG).show();
        boolean supportedPng = false;

        final File imagesDir = new File(context.getFilesDir(), "images");
        imagesDir.mkdirs();
        mPngFile = ((CustomKeyboard) context).getFileForResource(context, context.getResources().getIdentifier(view.getTag().toString(), "mipmap", context.getPackageName()), imagesDir, "" + view.getTag() + ".gif");
        System.out.println("Pager: mPngFile onclikc PagerModifi: "+ mPngFile);
        {
            final EditorInfo editorInfo = ((CustomKeyboard) context).getCurrentInputEditorInfo();
            final Uri contentUri = FileProvider.getUriForFile(context, CustomKeyboard.AUTHORITY, mPngFile);
            System.out.println("Pager: mPngFile contentUri PagerModifi: "+ contentUri);

            final int flag;
            if (Build.VERSION.SDK_INT >= 25) {
                flag = InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION;
            } else {
                flag = 0;
                try {
                    context.grantUriPermission(editorInfo.packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (Exception e) {
                }
            }
            try {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                sendIntent.setPackage(editorInfo.packageName);
                sendIntent.putExtra(Intent.EXTRA_STREAM,contentUri);//.toString());
                sendIntent.setType(CustomKeyboard.MIME_TYPE_PNG);
                context.startActivity(sendIntent);//Intent.createChooser(sendIntent, "Send to"));
            }catch (Exception e){
                e.printStackTrace();
            }catch(Error e)
            {
                e.printStackTrace();
            }catch(Throwable e)
            {
                e.printStackTrace();
            }

        }
    }
}