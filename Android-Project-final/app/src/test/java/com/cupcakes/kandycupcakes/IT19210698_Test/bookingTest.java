package com.cupcakes.kandycupcakes.IT19210698_Test;

import com.cupcakes.kandycupcakes.IT19210698.booking;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class bookingTest {

    private com.cupcakes.kandycupcakes.IT19210698.booking booking;

    @Before
    public void setUp() {

        booking = new booking();
    }

    @Test
    public void total_iscorrect() {
        Double result = booking.calculateTotal(6550.0, 1);
        assertEquals(6550.0, result, 0.1);
    }


}