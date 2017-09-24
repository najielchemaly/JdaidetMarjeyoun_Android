package com.marjeyoun.jdeidetmarjeyoun.complain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.marjeyoun.jdeidetmarjeyoun.R;

/**
 * Created by Charbel on 9/16/2017.
 */

public class Complain_fragment extends Fragment {

    private View parentView;
    EditText txtFullName, txtPhone, txtMessage;
    Button btnUpload, btnSend;
    MaterialSpinner spinnerType;

    public static Complain_fragment newInstance() {
        Complain_fragment fragment = new Complain_fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.complain_fragment, container, false);

        try {
            initView();
            initListeners();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e, Toast.LENGTH_SHORT).show();
        }

        return parentView;
    }

    private void initView() throws Exception {
        txtFullName = (EditText) parentView.findViewById(R.id.txtFullName);
        txtPhone = (EditText) parentView.findViewById(R.id.txtPhone);
        txtMessage = (EditText) parentView.findViewById(R.id.txtMessage);
        btnUpload = (Button) parentView.findViewById(R.id.btnUpload);
        btnSend = (Button) parentView.findViewById(R.id.btnSend);
        spinnerType = (MaterialSpinner) parentView.findViewById(R.id.spinnerType);
    }

    private void initListeners() throws Exception {

    }
}
