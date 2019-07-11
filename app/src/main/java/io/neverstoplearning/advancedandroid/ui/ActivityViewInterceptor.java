package io.neverstoplearning.advancedandroid.ui;

import android.app.Activity;
import android.support.annotation.LayoutRes;

//allows us to intercept the setContentView() of our activity
//so we can wrap the view in a layout that has a debug drawer
//add to MainActivityComponent
//inject in BaseActivity
public interface ActivityViewInterceptor {

    void setContentView(Activity activity, @LayoutRes int layoutRes);

    //clear any listers and bindings we have
    void clear();

    //a default implementation
    //only fo a normal setContentViewCall()
    ActivityViewInterceptor DEFAULT = new ActivityViewInterceptor() {
        @Override
        public void setContentView(Activity activity, int layoutRes) {
            activity.setContentView(layoutRes);
        }

        @Override
        public void clear() {

        }
    };
}
