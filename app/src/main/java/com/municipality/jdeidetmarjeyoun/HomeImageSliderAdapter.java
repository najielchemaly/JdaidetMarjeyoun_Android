package com.municipality.jdeidetmarjeyoun;

/**
 * Created by Charbel on 9/23/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.municipality.jdeidetmarjeyoun.news.NewsDetailsActivity;
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeImageSliderAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private ArrayList<String> desc;
    private List<News> news;
    private LayoutInflater inflater;
    private Context context;

    public HomeImageSliderAdapter(Context context, ArrayList<String> images, ArrayList<String> desc, List<News> news) {
        this.context = context;
        this.images=images;
        this.news=news;
        this.desc=desc;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.home_image, view, false);

        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);

        TextView lblDescription = (TextView) myImageLayout.findViewById(R.id.lblDescription);

//        myImage.setImageResource(images.get(position));
        lblDescription.setText(desc.get(position));

        Picasso.with(context)
                .load(images.get(position))
                .into(myImage);

        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("news", news.get(position));
                context.startActivity(intent);
//                Toast.makeText(context, news.get(position).getShortDescription(), Toast.LENGTH_SHORT).show();
            }
        });

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}