package com.example.leoaki;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

        private Button AlreadyHaveAnAccount;
        private FrameLayout ParentFrameLayout;
        private EditText email,fullname,password,confirmpassword;

        private ImageButton closebutton;
        private Button signupbutton;
        private FirebaseFirestore firebaseFirestore;
        private FirebaseAuth firebaseAuth;
        private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        AlreadyHaveAnAccount = view.findViewById(R.id.singin_button_singup_page);

        ParentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);



        email = view.findViewById(R.id.Email_field_signup);
        fullname = view.findViewById(R.id.fullname_signup_page);
        password = view.findViewById(R.id.password_signup_page);
        confirmpassword = view.findViewById(R.id.confirm_password_signup_page);
        closebutton = view.findViewById(R.id.cancel_button_sign_up_page);
        signupbutton = view.findViewById(R.id.signup_button_singup_page);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 setFragment(new SigninFragment());
            }
        });

        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),MainActivity.class);
                startActivity(a);
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
        fullname.addTextChangedListener(new TextWatcher() {
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
        confirmpassword.addTextChangedListener(new TextWatcher() {
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


        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///todo - send data from field to fire base
                 CheckEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(ParentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }


    private void checkinputs(){
        if(!TextUtils.isEmpty(email.getText())){

            if (!TextUtils.isEmpty(fullname.getText())){

                if (!TextUtils.isEmpty(password.getText()) && password.length()>=8){

                            if(!TextUtils.isEmpty(confirmpassword.getText())){
                                    signupbutton.setEnabled(true);
                                    signupbutton.setTextColor(Color.rgb(255,255,255));



                            }else {
                                signupbutton.setEnabled(false);
                                signupbutton.setTextColor(Color.argb(50,255,255,255));

                                    }
                    }else{
                                 signupbutton.setEnabled(false);
                                  signupbutton.setTextColor(Color.argb(50,255,255,255));

                            }
                }else{
                                signupbutton.setEnabled(false);
                                  signupbutton.setTextColor(Color.argb(50,255,255,255));
                        }
            }else {
                             signupbutton.setEnabled(false);
                             signupbutton.setTextColor(Color.argb(50,255,255,255));
                    }
        }
    private void CheckEmailAndPassword(){


        if (email.getText().toString().matches(emailpattern)){
            if(password.getText().toString().equals(confirmpassword.getText().toString())){
                signupbutton.setEnabled(false);
                signupbutton.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put("Full Name",fullname.getText().toString());
                                    firebaseFirestore.collection("USERS")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful()){
                                                        Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                                                        startActivity(mainIntent);
                                                        getActivity().finish();

                                                    }else {
                                                        signupbutton.setEnabled(true);
                                                        signupbutton.setTextColor(Color.rgb(255,255,255));
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();

                                                            }

                                                }
                                            });

                                }else {
                                    signupbutton.setEnabled(true);
                                    signupbutton.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                                        }

                            }
                        });

            }else {
                        confirmpassword.setError("Password does not match");
                    }

        }else {
                    email.setError("Invalid email!");
                }
    }
}
