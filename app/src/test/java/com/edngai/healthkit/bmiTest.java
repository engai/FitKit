/**
 * unit test located in /app/src/test/java/com/edngai/healthkit/bmiTest.java
 *
 * To run unit test on Android Studio, do the following:
 *
 * 1. navigate to FitKit/app/src/test/java/com/edngai/healthkit/bmiTest.java
 * 2. Right click bmiTest.java and click "Run bmiTest"
 * 3. 4 test cases should run and process should finish with exit code 0.
 */

package com.edngai.healthkit;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class bmiTest {

    private bmi mActivity;
    private Button mbmiButton;
    private TextView mWeightIn, mHeightIn;

    @Before
    public void setup() {
        mActivity = Robolectric.buildActivity(bmi.class).create().get();
        mbmiButton = (Button) mActivity.findViewById(R.id.BMIbutton);
        mWeightIn = (TextView) mActivity.findViewById(R.id.userWeight);
        mHeightIn = (TextView) mActivity.findViewById(R.id.userHeight);
    }

    /**
     * Given I am on the BMI page
     * And I enter in 0 as the weight
     * When I click the "Calculate New BMI" button
     * Then an AlertDialog is displayed with "Input cannot be 0" message
     */
    @Test
    public void showAlertIfUserEntersZeroWeight() {

        mWeightIn.setText("0", TextView.BufferType.EDITABLE);
        mHeightIn.setText("123", TextView.BufferType.EDITABLE);
        mbmiButton.performClick();

        assertTrue(mActivity.alert11.isShowing());
        assertTrue(mActivity.message.equals("Input cannot be 0"));
    }

    /**
     * Given I am on the BMI page
     * And I enter in 0 as the height
     * When I click the "Calculate New BMI" button
     * Then an AlertDialog is displayed with "Input cannot be 0" message
     */
    @Test
    public void showAlertIfUserEntersZeroHeight() {
        mWeightIn.setText("123", TextView.BufferType.EDITABLE);
        mHeightIn.setText("0", TextView.BufferType.EDITABLE);
        mbmiButton.performClick();

        assertTrue(mActivity.alert11.isShowing());
        assertTrue(mActivity.message.equals("Input cannot be 0"));
    }

    /**
     * Given I am on the BMI page
     * And I enter in an out of bounds input as the weight
     * When I click the "Calculate New BMI" button
     * Then an AlertDialog is displayed with
     * "Are you sure that is the correct weight/height?" message
     */
    @Test
    public void showAlertIfUserEntersOutOfBoundsWeight() {
        mWeightIn.setText("9000", TextView.BufferType.EDITABLE);
        mHeightIn.setText("70", TextView.BufferType.EDITABLE);
        mbmiButton.performClick();

        assertTrue(mActivity.alert11.isShowing());
        assertTrue(mActivity.message.equals("Are you sure that is the correct weight/height?"));
    }

    /**
     * Given I am on the BMI page
     * And I enter in an out of bounds input as the height
     * When I click the "Calculate New BMI" button
     * Then an AlertDialog is displayed with
     * "Are you sure that is the correct weight/height?" message
     */
    @Test
    public void showAlertIfUserEntersOutOfBoundsHeight() {
        mWeightIn.setText("180", TextView.BufferType.EDITABLE);
        mHeightIn.setText("9000", TextView.BufferType.EDITABLE);
        mbmiButton.performClick();

        assertTrue(mActivity.alert11.isShowing());
        assertTrue(mActivity.message.equals("Are you sure that is the correct weight/height?"));
    }
}
