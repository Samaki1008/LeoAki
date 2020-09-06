package com.example.leoaki;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button DontHaveAnAccount;
    private FrameLayout ParentFrameLayout;

    private EditText email,password;

    private ImageButton closebutton;
    private Button signinbutton;

    private FirebaseAuth firebaseAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SigninFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signin, container, false);
        DontHaveAnAccount = view.findViewById(R.id.signup_button_signin_page);
        ParentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);
        email = view.findViewById(R.id.email_sign_in_page);
        password = view.findViewById(R.id.password_field_signin);

        closebutton = view.findViewById(R.id.cancel_button_sign_up_page);
        signinbutton = view.findViewById(R.id.signin_button_signin_page);
        firebaseAuth = FirebaseAuth.getInstance();





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignupFragment());
            }
        });

        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getActivity(),MainActivity.class);
                startActivity(b);
                getActivity().finish();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    checkinputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkemailandpassword();
            }
        });
    }



    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(ParentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkinputs() {
        if(!TextUtils.isEmpty(email.getText().toString())){
            if(!TextUtils.isEmpty(password.getText())){
                signinbutton.setEnabled(true);
                signinbutton.setTextColor(Color.rgb(255,255,255));

            }else{
                signinbutton.setEnabled(false);
                signinbutton.setTextColor(Color.argb(50,255,255,255));

                    }

        }else {
            signinbutton.setEnabled(false);
            signinbutton.setTextColor(Color.argb(50,255,255,255));

             }


    }

    private void checkemailandpassword() {

        if(email.getText().toString().matches(emailpattern)){
            if (password.length()>=8){

                signinbutton.setEnabled(false);
                signinbutton.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent newintent = new Intent(getActivity(),MainActivity.class);
                                    startActivity(newintent);
                                    getActivity().finish();

                                }else{

                                    signinbutton.setEnabled(true);
                                    signinbutton.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                                        }

                            }
                        });


            }else {
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }

        }else{
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
    }

}