package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


/**
 * Created by wxs on 2016/8/25.
 */
public class AssetNumDialog extends Dialog {
    static OnSelectSetListener onSelectSetListener;

    public AssetNumDialog(Context context) {
        super(context);
    }

    public AssetNumDialog(Context context, int theme) {
        super(context, theme);
    }

    public AssetNumDialog(Context context, String title, String buttonName) {
        super(context);
        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setButtonName(buttonName);
        builder.create().show();
    }

    public void setOnSelectSetListener(OnSelectSetListener onSelectSetListener) {
        AssetNumDialog.onSelectSetListener = onSelectSetListener;
    }

    public interface  OnSelectSetListener {
        /**
         * This method will be invoked when a button in the dialog is clicked.
         *
         */
        /* TODO: Change to use BUTTON_POSITIVE after API council */
        void OnSelectSet(String assetNum) ;
    }

    public static  class Builder <E> {
        private Context context;
        //标题
        private String title;
        //按钮名称
        private String buttonName;

        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public AssetNumDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final AssetNumDialog dialog = new AssetNumDialog(context, R.style.Dialog);
            final View layout = inflater.inflate(R.layout.activity_aseetnum_input, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            TextView searchTextView = ((TextView) layout.findViewById(R.id.search));
            searchTextView.setText(buttonName);
            // set the content message
            //关闭dialog
            layout.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            TextView titleTextView = ((TextView) layout.findViewById(R.id.title));
            titleTextView.setText(title);

            final EditText editText = ((EditText)layout.findViewById(R.id.stock_num));
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            LinearLayout addbuycar = ((LinearLayout)layout.findViewById(R.id.addbuycar));
            addbuycar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectSetListener.OnSelectSet(editText.getText().toString());
                }
            });

            return dialog;
        }
    }
}
