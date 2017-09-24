package com.marjeyoun.jdeidetmarjeyoun.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.objects.Contact;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.List;

/**
 * Created by Charbel on 8/24/2017.
 */

public class ContactsAdapter extends BaseAdapter {

    private List<Contact> root;
    private Context context;
    private LayoutInflater mInflater;

    public ContactsAdapter(List<Contact> root, Context context) {
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

        Contact contact = (Contact) getItem(position);
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

        lblFullname.setText(contact.getName());
        lblMobileNumber.setText(contact.getMobile());
        lblPhoneNumber.setText(contact.getPhone());

        return convertView;
    }
}
