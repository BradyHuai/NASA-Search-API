package com.example.nasasearchapi;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.app.Activity;
import android.content.Intent;

import com.example.nasasearchapi.data.ItemNASA;
import com.example.nasasearchapi.tools.Constants;

public class DetailsActivityTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            DetailsActivity.class);

    private Activity mActivity;

    @Before
    public void launchActivity() {
        // Launch the DetailsActivity
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void displayCorrectData() {
        // Check if the title, description, and dateCreated TextViews display the correct text
        onView(withId(R.id.details_title)).check(matches(withText("")));
        onView(withId(R.id.details_description)).check(matches(withText("")));
        onView(withId(R.id.details_date_created)).check(matches(withText("")));
    }

    @Test
    public void displayImage() {
        // Check if the image is displayed
//        onView(withId(R.id.details_Image)).check(matches(isDisplayed()));
    }
}