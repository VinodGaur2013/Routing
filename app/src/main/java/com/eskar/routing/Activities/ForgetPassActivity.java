package com.eskar.routing.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eskar.routing.R;
import com.eskar.routing.Utils.CommonMethod;

public class ForgetPassActivity extends AppCompatActivity {

    EditText mEmail;
    TextView mTitle;
    Button mSignUpButton;
    ProgressBar pd;
    FrameLayout rootLinearlaout;
    private static final float BLUR_RADIUS = 25f;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle.setText(R.string.action_forget);

        rootLinearlaout=(FrameLayout)findViewById(R.id.rootLinearlaout);
        ImageView imageView = (ImageView) findViewById(R.id.iv_background);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.route);
        Bitmap blurredBitmap = blur(bitmap);

        assert imageView != null;
        imageView.setImageBitmap(blurredBitmap);
        rootLinearlaout.setBackground(imageView.getDrawable());



        mEmail = (EditText) findViewById(R.id.etEmail);
        pd=(ProgressBar)findViewById(R.id.pd);
        pd.setVisibility(View.GONE);
        mSignUpButton = (Button) findViewById(R.id.btnSignupSubmit);


        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptReg();
            }
        });
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

    private void attemptReg() {

        if (!CommonMethod.isValidEmail(mEmail.getText().toString()))
        { AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getResources().getString (R.string.error_email));
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // Toast.makeText(LoginActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            //mEmail.setError(getResources().getString (R.string.error_email));
            return;
        } else{ Toast.makeText(getApplicationContext(), "Password send to your email address.", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(ForgetPassActivity.this,LoginActivity.class);
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