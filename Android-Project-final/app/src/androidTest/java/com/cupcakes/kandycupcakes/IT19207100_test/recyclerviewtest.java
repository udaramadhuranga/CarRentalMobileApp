package com.cupcakes.kandycupcakes.IT19207100_test;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.cupcakes.kandycupcakes.IT19207100.ImagesActivity;
import com.cupcakes.kandycupcakes.IT19207100.addvehical;
import com.cupcakes.kandycupcakes.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class recyclerviewtest {

    @Rule
    public ActivityTestRule<ImagesActivity> imagesActivityActivityTestRule = new ActivityTestRule<ImagesActivity>(ImagesActivity.class);
    private ImagesActivity imagesActivity = null;

    @Before
    public void setUp() throws Exception {
        imagesActivity = imagesActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = imagesActivity.findViewById(R.id.recycler);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        imagesActivity = null;
    }

    @Rule
    public ActivityTestRule<addvehical> addVehicleActivityActivityTestRule1 = new ActivityTestRule<addvehical>(addvehical.class);
    private addvehical addVehicleActivity1 = null;

    @Before
    public void setUp2() throws Exception {
        addVehicleActivity1 = addVehicleActivityActivityTestRule1.getActivity();
    }

    @Test
    public void testLaunch2(){
        View view = addVehicleActivity1.findViewById(R.id.edit_text_file_name);
       assertNotNull(view);
        //assertNull(view);
    }

    @After
    public void tearDown2() throws Exception {
        addVehicleActivity1 = null;
    }


}