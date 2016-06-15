package com.eskar.routing.Fragments;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.eskar.routing.Activities.DrawerActivity;
import com.eskar.routing.Activities.SignUpActivity;
import com.eskar.routing.R;
import com.eskar.routing.Utils.CommonMethod;
import com.eskar.routing.Utils.CommonUtils;
import com.eskar.routing.Utils.SessionManager;

public class UpdateProfileFragment extends Fragment implements View.OnClickListener{

    TextView mTitle, mTerms;
    TextView mFirstName,mLastName,mEmail,mTelephone,mPassword,mConfirmPass;
    CheckBox mCbTerm;
    Button mSignUpButton;
    SessionManager session;
    ProgressBar pd;
    String userType;
    private static final float BLUR_RADIUS = 25f;
    FrameLayout rootLinearlaout;

    public UpdateProfileFragment() {}

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update_profile, container, false);

        rootLinearlaout=(FrameLayout)view.findViewById(R.id.rootLinearlaout);
        ImageView imageView = (ImageView)view. findViewById(R.id.iv_background);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.route);
        Bitmap blurredBitmap = blur(bitmap);

        assert imageView != null;
        imageView.setImageBitmap(blurredBitmap);
        rootLinearlaout.setBackground(imageView.getDrawable());

        CommonUtils.hideSoftKeyboard(getActivity());

        session = new SessionManager(getActivity().getApplicationContext());

        mTerms=(TextView)view.findViewById(R.id.tvTerms);
        mCbTerm=(CheckBox)view.findViewById(R.id.cbTerms);
        mFirstName=(TextView)view.findViewById(R.id.etFirstName);
        mLastName=(TextView)view.findViewById(R.id.etLastName);
        mEmail=(TextView)view.findViewById(R.id.etEmail);
        mTelephone=(TextView)view.findViewById(R.id.etTelephone);
        mPassword=(TextView)view.findViewById(R.id.etPass);
        mConfirmPass=(TextView)view.findViewById(R.id.etConfirmPass);

        pd=(ProgressBar)view.findViewById(R.id.pd);
        pd.setVisibility(View.GONE);

        mSignUpButton=(Button)view.findViewById(R.id.btnSignupSubmit);

        mSignUpButton.setOnClickListener(this);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(getActivity());
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignupSubmit:
                attemptReg();
                break;
        }
    }


    private void attemptReg() {
        if (!CommonMethod.validateName(mFirstName.getText().toString())){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Please input first name...");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            // mUserName.setError(getResources().getString (R.string.error_username));

        }else if (!CommonMethod.validateName(mLastName.getText().toString())){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Please input last name...");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            // mUserName.setError(getResources().getString (R.string.error_username));

        }else if (!CommonMethod.isValidEmail(mEmail.getText().toString())){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Please input valid email address...");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            //mEmail.setError(getResources().getString (R.string.error_email));

        }else if (!CommonMethod.isValidTelephone(mTelephone.getText().toString())){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Please input your 10 digits mobile number ...");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            //mTelephone.setError(getResources().getString (R.string.error_telephone));

        }else if (!CommonMethod.isValidPass(mPassword.getText().toString())){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Please input your secure password...");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            //mPassword.setError(getResources().getString (R.string.error_pass));

        }else if (!mConfirmPass.getText().toString().equalsIgnoreCase(mPassword.getText().toString())){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Your entered password does not matched...");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            // mConfirmPass.setError(getResources().getString (R.string.error_pass_not_match));

        } else if (!mCbTerm.isChecked()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Please accept the terms to register...");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            //mCbTerm.setError(getResources().getString (R.string.error_checkbox));

        } else{
            Intent intent=new Intent(getActivity(),DrawerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

}