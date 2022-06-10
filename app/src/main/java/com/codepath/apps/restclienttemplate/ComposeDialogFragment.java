package com.codepath.apps.restclienttemplate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComposeDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeDialogFragment extends Fragment {
    public static final String TAG = "ComposeDialogFragment";
    public static final int MAX_TWEET_LENGTH = 140;

    EditText etComposeScr;
    Button btnTweet;

    TwitterClient client;

    // textInputLayout
    // etComposeScr
    // btnTweet

    public ComposeDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ComposeDialogFragment newInstance(String title) {
        ComposeDialogFragment frag = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        etComposeScr = view.findViewById(R.id.etComposeScr);
        // Fetch arguments from bundle and set title
        assert getArguments() != null;
        String title = getArguments().getString("title", "Enter Name");
//        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        etComposeScr.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}