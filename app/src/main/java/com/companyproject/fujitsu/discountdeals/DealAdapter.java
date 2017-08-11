package com.companyproject.fujitsu.discountdeals;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lue on 11-08-2017.
 */

public class DealAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<DealClass> movieItems;


    public DealAdapter(Context context ,List<DealClass> movieItems) {

        this.context = context;
        this.movieItems = movieItems;

    }



    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position)  {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    static class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView city;
        TextView state;
        TextView country;
        TextView credits;
        TextView category;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;

        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.deallayout, null);
            holder = new ViewHolder();

//            if (imageLoader == null)
//                imageLoader = AppController.getInstance().getImageLoader();
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.city = (TextView) convertView.findViewById(R.id.city);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.country = (TextView) convertView.findViewById(R.id.country);
            holder.credits = (TextView) convertView.findViewById(R.id.credit);
            holder.category = (TextView) convertView.findViewById(R.id.category);

            // getting movie data for the row
            DealClass m = movieItems.get(position);

            // thumbnail image
            // thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

            if(!URLUtil.isValidUrl(movieItems.get(position).getImage()))
            {
                holder.imageView.setVisibility(View.GONE);
            }
            else
            {
                holder.imageView.setVisibility(View.VISIBLE);//add this
                //      holder.thumbNail.setImageResource(Integer.parseInt(movieItems.get(position).getThumbnailUrl()));

                Picasso.with(context).load(movieItems.get(position).getImage()).resize(120, 80).into(holder.imageView);

                String str = "rjbhai";
                Log.d("rj123",str);
            }

            // title
            holder.title.setText(m.getDealtitle());

            // rating
            holder.city.setText(m.getCityname()+",");

            // genre
            holder.state.setText(m.getStatename()+",");

            // release year
            holder.country.setText(m.getCountryname());
            holder.credits.setText(m.getMinimumcredit()+" Credit");
            holder.category.setText(m.getCategoryname());
            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;


    }
}
