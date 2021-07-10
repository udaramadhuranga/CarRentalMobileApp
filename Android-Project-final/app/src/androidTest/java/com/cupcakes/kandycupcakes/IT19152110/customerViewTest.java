package com.cupcakes.kandycupcakes.IT19152110;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.cupcakes.kandycupcakes.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class customerViewTest {


    public ActivityTestRule<customerView> mActivityTestRule = new ActivityTestRule<customerView>(customerView.class);
    private customerView cv = null;

    @Before
    public void setUp() {
        cv = mActivityTestRule.getActivity();
    }

    @Test
    public void test() {
        try {
            View view = cv.findViewById(R.id.txtFaq);
            assertNotNull(view);
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @After
    public void tearDown() {
        cv = null;
    }

}