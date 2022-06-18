package com.municipality.jdeidetmarjeyoun.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.Contact;
import com.municipality.jdeidetmarjeyoun.objects.Directory;
import com.municipality.jdeidetmarjeyoun.objects.Directory_;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class ContactsAdapter extends BaseAdapter {

    public List<Directory_> root;
    private Context context;
    private LayoutInflater mInflater;

    public ContactsAdapter(List<Directory_> root, Context context) {
        this.root = root;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return root.size();
    }

    @Override
    public Object getItem(int position) {
        return root.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) Math.random();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Directory_ contact = (Directory_) getItem(position);
        if("ar".equals(SelectLanguageActivity.lang)){
            convertView = mInflater.inflate(R.layout.contact_row_item_ar, null);
        }else {
            convertView = mInflater.inflate(R.layout.contact_row_item_en, null);
        }

        TextView lblFullname = (TextView) convertView.findViewById(R.id.lblFullname);
        TextView lblMobileNumber = (TextView) convertView.findViewById(R.id.lblMobileNumber);
        TextView lblPhoneNumber = (TextView) convertView.findViewById(R.id.lblPhoneNumber);

        lblFullname.setTypeface(AppUtil.setFontRegular(context));
        lblMobileNumber.setTypeface(AppUtil.setFontRegular(context));
        lblPhoneNumber.setTypeface(AppUtil.setFontRegular(context));

        lblFullname.setText(contact.getFullname());
        lblMobileNumber.setText(contact.getMobileNumber());
        lblPhoneNumber.setText(contact.getPhoneNumber());

        return convertView;
    }
}
