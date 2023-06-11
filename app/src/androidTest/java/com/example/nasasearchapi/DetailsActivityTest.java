package com.example.nasasearchapi;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.content.Intent;

import com.example.nasasearchapi.data.ItemNASA;
import com.example.nasasearchapi.tools.Constants;

public class DetailsActivityTest {

    @Rule
    public ActivityTestRule<DetailsActivity> mActivityRule =
            new ActivityTestRule<DetailsActivity>(DetailsActivity.class){

                @Override
                protected Intent getActivityIntent() {
                    ItemNASA selected = new ItemNASA();
                    selected.setTitle("Apollo");
                    selected.setDescription("This is an image of Apollo");
                    selected.setDateCreated("2000-01-01");
                    selected.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
                    selected.setNasaID("EC97-44347-16");

                    Intent intent = new Intent();
                    intent.putExtra(Constants.INTENT_SELECTED, selected);
                    return intent;
                }
            };

    @Test
    public void testDisplayCorrectData() {
        // Check if the title, description, and dateCreated TextViews display the correct text
        onView(withId(R.id.details_title)).check(matches(withText("Apollo")));
        onView(withId(R.id.details_description)).check(matches(withText("This is an image of Apollo")));
        onView(withId(R.id.details_date_created)).check(matches(withText("2000-01-01")));
    }

    @Test
    public void testDisplayImage() {
        // Check if the image is displayed
        onView(withId(R.id.details_Image)).check(matches(isDisplayed()));
    }
}