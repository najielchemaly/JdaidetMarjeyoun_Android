package com.marjeyoun.jdeidetmarjeyoun.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.marjeyoun.jdeidetmarjeyoun.R;
import com.marjeyoun.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.marjeyoun.jdeidetmarjeyoun.objects.Contact;
import com.marjeyoun.jdeidetmarjeyoun.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charbel on 9/24/2017.
 */

public class Contacts_fragment extends Fragment {

    private View parentView;
    MaterialSpinner spinnerCategory;
    EditText txtPhone;
    TextView lblMobileNumber, lblPhoneNumber, lblFullname;
    ListView listContacts;
    ContactsAdapter contactsAdapter;

    public static Contacts_fragment newInstance() {
        Contacts_fragment fragment = new Contacts_fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if("ar".equals(SelectLanguageActivity.lang)){
            parentView = inflater.inflate(R.layout.contacts_fragment_ar, container, false);
        }else {
            parentView = inflater.inflate(R.layout.contacts_fragment_en, container, false);
        }

        try {
            initView();
            styleView();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e, Toast.LENGTH_SHORT).show();
        }

        return parentView;
    }

    private void initView() throws Exception{
        spinnerCategory = (MaterialSpinner) parentView.findViewById(R.id.spinnerCategory);
        txtPhone = (EditText) parentView.findViewById(R.id.txtPhone);
        lblMobileNumber = (TextView) parentView.findViewById(R.id.lblMobileNumber);
        lblPhoneNumber = (TextView) parentView.findViewById(R.id.lblPhoneNumber);
        lblFullname = (TextView) parentView.findViewById(R.id.lblFullname);
        listContacts = (ListView) parentView.findViewById(R.id.listContacts);

        contactsAdapter = new ContactsAdapter("ar".equals(SelectLanguageActivity.lang) ? createDummyContactsAr() : createDummyContactsEN(), getActivity());
        listContacts.setAdapter(contactsAdapter);
    }

    private void styleView() throws Exception {
        txtPhone.setTypeface(AppUtil.setFontRegular(getContext()));
        lblMobileNumber.setTypeface(AppUtil.setFontBold(getContext()));
        lblPhoneNumber.setTypeface(AppUtil.setFontBold(getContext()));
        lblFullname.setTypeface(AppUtil.setFontBold(getContext()));
        spinnerCategory.setTypeface(AppUtil.setFontRegular(getContext()));
    }

    private List<Contact> createDummyContactsEN() {
        List<Contact> itemList = new ArrayList<>();

        Contact contact = new Contact();
        contact.setName("Charbel Chidiac");
        contact.setMobile("70680298");
        contact.setPhone("04272729");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("Cindy Hayek");
        contact.setMobile("70685677");
        contact.setPhone("04982101");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("Naji Chemaly");
        contact.setMobile("72180127");
        contact.setPhone("01987256");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("Anis Delon");
        contact.setMobile("81728301");
        contact.setPhone("09725182");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("haifa Wehbe");
        contact.setMobile("03481813");
        contact.setPhone("01947942");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("Charbel Chidiac");
        contact.setMobile("70680298");
        contact.setPhone("04272729");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("Cindy Hayek");
        contact.setMobile("70685677");
        contact.setPhone("04982101");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("Naji Chemaly");
        contact.setMobile("72180127");
        contact.setPhone("01987256");
        itemList.add(contact);

        return itemList;
    }

    private List<Contact> createDummyContactsAr() {
        List<Contact> itemList = new ArrayList<>();

        Contact contact = new Contact();
        contact.setName("شربل شدياق");
        contact.setMobile("٧٠٦٨٠٢٩٨");
        contact.setPhone("٠٤٢٧٢٧٢٩");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("سندي حايك");
        contact.setMobile("٧٠٦٨٥٦٧٧");
        contact.setPhone("٠٤٩٨٢١٠١");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("ناجي شمالي");
        contact.setMobile("٧٢١٨٠١٢٧");
        contact.setPhone("٠١٩٨٧٢٥٦");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("أنيس دلون ");
        contact.setMobile("٨١٧٢٨٣٠١");
        contact.setPhone("٠٩٧٢٥١٨٢");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("هيفاء وهبي ");
        contact.setMobile("٠٣٤٨١٨١٣");
        contact.setPhone("٠١٩٤٧٩٤٢");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("شربل شدياق");
        contact.setMobile("٧٠٦٨٠٢٩٨");
        contact.setPhone("٠٤٢٧٢٧٢٩");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("سندي حايك");
        contact.setMobile("٧٠٦٨٥٦٧٧");
        contact.setPhone("٠٤٩٨٢١٠١");
        itemList.add(contact);

        contact = new Contact();
        contact.setName("ناجي شمالي");
        contact.setMobile("٧٢١٨٠١٢٧");
        contact.setPhone("٠١٩٨٧٢٥٦");
        itemList.add(contact);

        return itemList;
    }
}
