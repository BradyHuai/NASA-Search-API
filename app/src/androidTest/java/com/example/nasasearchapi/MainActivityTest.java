package com.example.nasasearchapi;

import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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

    private CountingIdlingResource idlingResource = new CountingIdlingResource("SearchRequestTask");

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
//        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void tearDown() throws Exception {
//        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void testSearchViewInput() throws InterruptedException {
        // Type a search query in the SearchView
//        onView(withId(R.id.search_view)).check(matches(isCompletelyDisplayed()));
//        onView(withId(R.id.search_view)).perform(typeText("Moon"));
//
//        // Click the submit button on the keyboard
//        onView(withId(R.id.search_view)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        // Verify that the ListView is displayed
        onView(withId(R.id.content_list)).check(matches(isClickable()));
        onView(withId(R.id.content_list)).check(matches(isEnabled()));

//        Espresso.onData(ViewMatchers.withId(R.id.content_list))
//                .atPosition(0)
//                .check(matches(isClickable()));

//        // Verify that the ListView has at least one item
        onView(withId(R.id.content_list)).check(matches(hasMinimumChildCount(1)));
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
                ItemNASAAdapter adapter = new ItemNASAAdapter(getApplicationContext(), R.layout.item_cell, dataset);
                listView.setAdapter(adapter);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });

        // Continue with your test assertions and verifications
        onView(withId(R.id.content_list)).check(matches(hasChildCount(1)));
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}