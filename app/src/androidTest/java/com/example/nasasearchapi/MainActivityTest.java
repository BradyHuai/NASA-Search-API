package com.example.nasasearchapi;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nasasearchapi.adapter.ItemNASAAdapter;
import com.example.nasasearchapi.data.ItemNASA;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    public static ViewAction typeSearchViewText(final String text){
        return new ViewAction(){
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
            }

            @Override
            public String getDescription() {
                return "Change view text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((SearchView) view).setQuery(text,true);
            }
        };
    }

    @Test
    public void testSearchViewInput() {
        onView(withId(R.id.search_view)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.search_view)).perform(typeSearchViewText("Moon"));

        // Verify that the ListView has at least one item
        onView(withId(R.id.content_list)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void testSearchViewInputEmpty() {
        onView(withId(R.id.search_view)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.search_view)).perform(typeSearchViewText(""));

        // Verify that the ListView has no item
        onView(withId(R.id.content_list)).check(matches(hasChildCount(0)));
    }

    @Test
    public void testSearchViewContinuousInput() {
        onView(withId(R.id.search_view)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.search_view)).perform(typeSearchViewText("Moon"));

        // Verify that the ListView has at least one item
        onView(withId(R.id.content_list)).check(matches(hasMinimumChildCount(1)));

        onView(withId(R.id.search_view)).perform(typeSearchViewText("Earth"));

        // Verify that the ListView has at least one item
        onView(withId(R.id.content_list)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void testSearchViewInvalidInput() {
        onView(withId(R.id.search_view)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.search_view)).perform(typeSearchViewText("doesnotexist"));

        // Verify that the ListView has at least one item
        onView(withId(R.id.content_list)).check(matches(hasChildCount(0)));
    }

    @Test
    public void testListViewCountEmpty() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ListView listView = mActivityRule.getActivity().findViewById(R.id.content_list);

                // Perform UI operations on the main thread
                // Add items to the ListView
                ArrayList<ItemNASA> dataset = new ArrayList<>();
                ItemNASAAdapter adapter = new ItemNASAAdapter(getApplicationContext(), R.layout.item_cell, dataset);
                listView.setAdapter(adapter);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });

        onView(withId(R.id.content_list)).check(matches(hasChildCount(0)));
    }

    @Test
    public void testListViewCount() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ListView listView = mActivityRule.getActivity().findViewById(R.id.content_list);

                // Perform UI operations on the main thread
                // Add items to the ListView
                ArrayList<ItemNASA> dataset = new ArrayList<>();
                dataset.add(new ItemNASA());
                dataset.add(new ItemNASA());
                dataset.add(new ItemNASA());
                ItemNASAAdapter adapter = new ItemNASAAdapter(getApplicationContext(), R.layout.item_cell, dataset);
                listView.setAdapter(adapter);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });

        onView(withId(R.id.content_list)).check(matches(hasChildCount(3)));
    }

    @Test
    public void testShouldShowItemDetailWhenClicked() {
        onView(withId(R.id.search_view)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.search_view)).perform(typeSearchViewText("Moon"));

        Intents.init();
        onData(anything()).inAdapterView(withId(R.id.content_list)).atPosition(0).perform(click());
        Intents.release();

        onView(withId(R.id.details_title)).check(matches(withText(containsString("Moon"))));
    }
}