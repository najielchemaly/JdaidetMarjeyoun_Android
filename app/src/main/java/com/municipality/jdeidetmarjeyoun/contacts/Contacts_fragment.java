package com.municipality.jdeidetmarjeyoun.contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.base.Predicate;
import android.support.test.espresso.core.deps.guava.collect.Collections2;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.news.News_Activity;
import com.municipality.jdeidetmarjeyoun.objects.Contact;
import com.municipality.jdeidetmarjeyoun.objects.Directory;
import com.municipality.jdeidetmarjeyoun.objects.Directory_;
import com.municipality.jdeidetmarjeyoun.objects.GlobalVariable;
import com.municipality.jdeidetmarjeyoun.objects.Link;
import com.municipality.jdeidetmarjeyoun.objects.News;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.municipality.jdeidetmarjeyoun.R.id.spinnerType;

/**
 * Created by Charbel on 9/24/2017.
 */

public class Contacts_fragment extends Fragment implements TextView.OnEditorActionListener {

    private View parentView;
    MaterialSpinner spinnerCategory;
    EditText txtPhone;
    TextView lblMobileNumber, lblPhoneNumber, lblFullname;
    ListView listContacts, listLinks;
    RelativeLayout viewNoSearch, viewLinks;
    LinearLayout viewSearch, viewContacts;
    Button btnSearch, btnContacts, btnLinks;
    ContactsAdapter contactsAdapter;
    LinksAdapter linksAdapter;
    ProgressDialog progress;
   List<Directory_> directorys_ = new ArrayList<>();

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
            initListeners();
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
        viewContacts = (LinearLayout) parentView.findViewById(R.id.viewContacts);
        listContacts = (ListView) parentView.findViewById(R.id.listContacts);
        listLinks = (ListView) parentView.findViewById(R.id.listLinks);
        viewNoSearch = (RelativeLayout) parentView.findViewById(R.id.viewNoSearch);
        viewLinks = (RelativeLayout) parentView.findViewById(R.id.viewLinks);
        viewSearch = (LinearLayout) parentView.findViewById(R.id.viewSearch);
        btnSearch = (Button) parentView.findViewById(R.id.btnSearch);
        btnContacts = (Button) parentView.findViewById(R.id.btnContacts);
        btnLinks = (Button) parentView.findViewById(R.id.btnLinks);

//        contactsAdapter = new ContactsAdapter("ar".equals(SelectLanguageActivity.lang) ? createDummyContactsAr() : createDummyContactsEN(), getActivity());
//        listContacts.setAdapter(contactsAdapter);

        if(AppUtil.globalVariable.getDirectoryCategories().size() > 0){
            spinnerCategory.setItems(AppUtil.transform(getResources().getString(R.string.select_category), AppUtil.globalVariable.getDirectoryCategories()));
        }else {
            spinnerCategory.setItems(getResources().getString(R.string.select_category));
        }

        viewSearch.setVisibility(View.VISIBLE);
        viewNoSearch.setVisibility(View.GONE);

        progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getResources().getString(R.string.loading_message));
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        ApiManager.getService().getDirectory().enqueue(new Callback<Directory>() {
            @Override
            public void onResponse(Call<Directory> call, Response<Directory> response) {
                Directory directory = response.body();
                if(directory.getStatus() == 1){
                    directorys_ = directory.getDirectories();
                    if(directorys_ != null && directorys_.size() > 0){
                        contactsAdapter = new ContactsAdapter(directorys_ , getActivity());
                        listContacts.setAdapter(contactsAdapter);
                    }
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Directory> call, Throwable t) {
                Log.e("e", t.getMessage());
                progress.dismiss();
            }
        });

        ApiManager.getService().getUsefullLinks().enqueue(new Callback<Link>() {
            @Override
            public void onResponse(Call<Link> call, Response<Link> response) {
                Link link = response.body();

                linksAdapter = new LinksAdapter(link.getLinks(), getActivity());
                listLinks.setAdapter(linksAdapter);
            }

            @Override
            public void onFailure(Call<Link> call, Throwable t) {
                Log.e("e", t.getMessage());
            }
        });

        txtPhone.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if(StringUtils.isValid(txtPhone.getText())){
                        List<Directory_> filteredContacts = new ArrayList<>();
                        for (int i = 0; i < directorys_.size(); i++) {
                            if (txtPhone.getText().toString().trim().toLowerCase().equals(directorys_.get(i).getMobileNumber())) {
                                filteredContacts.add(directorys_.get(i));
                            }
                        }

                        contactsAdapter = new ContactsAdapter(filteredContacts , getActivity());
                        listContacts.setAdapter(contactsAdapter);

                    }else {
                        contactsAdapter = new ContactsAdapter(directorys_ , getActivity());
                        listContacts.setAdapter(contactsAdapter);
                    }

                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    return true;
                }
                return false;
            }
        });
    }

    private void styleView() throws Exception {
        txtPhone.setTypeface(AppUtil.setFontRegular(getContext()));
        lblMobileNumber.setTypeface(AppUtil.setFontBold(getContext()));
        lblPhoneNumber.setTypeface(AppUtil.setFontBold(getContext()));
        lblFullname.setTypeface(AppUtil.setFontBold(getContext()));
        spinnerCategory.setTypeface(AppUtil.setFontRegular(getContext()));
        btnLinks.setTypeface(AppUtil.setFontRegular(getContext()));
        btnContacts.setTypeface(AppUtil.setFontRegular(getContext()));
    }

    private void initListeners() throws Exception {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();

//                if(StringUtils.isValid(txtPhone.getText())){
//                    List<Directory_> filteredContacts = new ArrayList<>();
//                    for (int i = 0; i < directorys_.size(); i++) {
//                        if (txtPhone.getText().toString().trim().toLowerCase().equals(directorys_.get(i).getMobileNumber())) {
//                            filteredContacts.add(directorys_.get(i));
//                        }
//                    }
//                    contactsAdapter = new ContactsAdapter(filteredContacts , getActivity());
//                    listContacts.setAdapter(contactsAdapter);
//
//                }else {
//                    contactsAdapter = new ContactsAdapter(directorys_ , getActivity());
//                    listContacts.setAdapter(contactsAdapter);
//                }
//                View view = getActivity().getCurrentFocus();
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//
//                viewNoSearch.setVisibility(View.GONE);
//                viewSearch.setVisibility(View.VISIBLE);
            }
        });

        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedButton(btnContacts);
                viewContacts.setVisibility(View.VISIBLE);
                viewLinks.setVisibility(View.GONE);
            }
        });

        btnLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedButton(btnLinks);
                viewLinks.setVisibility(View.VISIBLE);
                viewContacts.setVisibility(View.GONE);
            }
        });
    }

    private void setSelectedButton(Button button){
        try{
            btnLinks.setTextColor(getResources().getColor(R.color.blue));
            btnLinks.setBackgroundColor(getResources().getColor(R.color.white));
            btnContacts.setTextColor(getResources().getColor(R.color.blue));
            btnContacts.setBackgroundColor(getResources().getColor(R.color.white));
            button.setBackgroundColor(getResources().getColor(R.color.blue));
            button.setTextColor(getResources().getColor(R.color.white));
        }catch (Exception e){
            Log.e("e",e.getMessage());
        }
    }

    private void filterData() {
        List<Directory_> filteredContacts = this.directorys_;

        if(spinnerCategory.getText() != null && !spinnerCategory.getText().toString().isEmpty() &&
                spinnerCategory.getText().toString() != getResources().getString(R.string.select_category)) {
            filteredContacts = new ArrayList<>(Collections2.filter(filteredContacts, new Predicate<Directory_>() {
                @Override
                public boolean apply(@javax.annotation.Nullable Directory_ directory) {
//                    return directory.getCategory().contains(spinnerCategory.getText().toString());
                    return spinnerCategory.getText().toString().contains(directory.getCategory());
                }
            }));
        }

        if(txtPhone.getText() != null && !txtPhone.getText().toString().isEmpty()) {
            filteredContacts = new ArrayList<>(Collections2.filter(filteredContacts, new Predicate<Directory_>() {
                @Override
                public boolean apply(@javax.annotation.Nullable Directory_ directory) {
                    return directory.getFullname().contains(txtPhone.getText().toString());
                }
            }));
        }

        if(filteredContacts.size() == 0) {
            viewNoSearch.setVisibility(View.VISIBLE);
            viewSearch.setVisibility(View.GONE);
        } else {
            viewNoSearch.setVisibility(View.GONE);
            viewSearch.setVisibility(View.VISIBLE);

            contactsAdapter.root = filteredContacts;
            contactsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
