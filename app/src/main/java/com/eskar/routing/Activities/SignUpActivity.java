package com.eskar.routing.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.eskar.routing.R;
import com.eskar.routing.Utils.CommonMethod;
import com.eskar.routing.Utils.CommonUtils;
import com.eskar.routing.Utils.SessionManager;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mTitle, mTerms;
    EditText mFirstName,mLastName,mEmail,mTelephone,mPassword,mConfirmPass;
    CheckBox mCbTerm;
    Button mSignUpButton;
    SessionManager session;
    ProgressBar pd;
    Spinner spUserType;
    String userType;
    private static final float BLUR_RADIUS = 25f;
    FrameLayout rootLinearlaout;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle=(TextView)toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle.setText("Register");

        rootLinearlaout=(FrameLayout)findViewById(R.id.rootLinearlaout);
        ImageView imageView = (ImageView) findViewById(R.id.iv_background);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.route);
        Bitmap blurredBitmap = blur(bitmap);

        assert imageView != null;
        imageView.setImageBitmap(blurredBitmap);
        rootLinearlaout.setBackground(imageView.getDrawable());

        CommonUtils.hideSoftKeyboard(SignUpActivity.this);

        session = new SessionManager(getApplicationContext());

        mTerms=(TextView)findViewById(R.id.tvTerms);
        mCbTerm=(CheckBox)findViewById(R.id.cbTerms);
        mFirstName=(EditText)findViewById(R.id.etFirstName);
        mLastName=(EditText)findViewById(R.id.etLastName);
        mEmail=(EditText)findViewById(R.id.etEmail);
        mTelephone=(EditText)findViewById(R.id.etTelephone);
        mPassword=(EditText)findViewById(R.id.etPass);
        mConfirmPass=(EditText)findViewById(R.id.etConfirmPass);
        pd=(ProgressBar)findViewById(R.id.pd);
        pd.setVisibility(View.GONE);

        mSignUpButton=(Button)findViewById(R.id.btnSignupSubmit);
        setSpanable(mTerms, "I agree to terms & conditions", 11, 29);
        mSignUpButton.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
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

    public void setSpanable(TextView tv,String title, int from, int to){
        Spannable wordtoSpan = new SpannableString(title);//11,28
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent=new Intent(SignUpActivity.this, TermsActivity.class);
                startActivity(intent);
                //  Toast.makeText(SignUpActivity.this, "Clicked", Toast.LENGTH_LONG).show();
            }
        };
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.rgb(9, 107, 244)), from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(clickableSpan, from, to, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(wordtoSpan);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
            Intent intent=new Intent(SignUpActivity.this,DrawerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}