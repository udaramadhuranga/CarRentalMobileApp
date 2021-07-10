package com.cupcakes.kandycupcakes.IT19207100_test;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.cupcakes.kandycupcakes.IT19207100.ImagesActivity;
import com.cupcakes.kandycupcakes.IT19207100.addvehical;
import com.cupcakes.kandycupcakes.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class showuploadstest {

    @Rule
    public ActivityTestRule<addvehical> mAcvtivityTestRule = new ActivityTestRule<addvehical>(addvehical.class);

    private addvehical mactivity=null;


    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ImagesActivity.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        mactivity=mAcvtivityTestRule.getActivity();
    }

    @Test
    public  void testLaunch(){
        View view = mactivity.findViewById(R.id.mTextViewShowUploads);
        assertNotNull(view);
        onView(withId(R.id.mTextViewShowUploads)).perform(click());
        Activity second = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(second);
        second.finish();



    }

    @After
    public void tearDown() throws Exception {
    mactivity=null;
    }

}