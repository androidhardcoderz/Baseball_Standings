package com.baseballstandings.logify;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

/**
 * Created by Scott on 10/17/2015.
 */
public class Underliner {

    public static TextView underlineTextViewContents(TextView tv){


        SpannableString spanString = new SpannableString(tv.getText().toString());
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        //spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        //spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
       tv.setText(spanString);
        return tv;
    }
}
