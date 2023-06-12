package com.example.nasasearchapi.adapter;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.example.nasasearchapi.MainActivityTest.typeSearchViewText;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.example.nasasearchapi.MainActivity;
import com.example.nasasearchapi.R;
import com.example.nasasearchapi.data.ItemNASA;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class ItemNASAAdapterTest {

    private Activity mActivity;
    private ListView mListView;

    private ItemNASAAdapter adapter;
    private List<ItemNASA> dataList;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mActivity = mActivityRule.getActivity();

        dataList = new ArrayList<>();

        adapter = new ItemNASAAdapter(mActivity, R.layout.item_cell, dataList);
        mListView = (ListView) mActivity.findViewById(R.id.content_list);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        new ListView(mActivity);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullContext1() {
        new ListView(null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullContext2() {
        new ListView(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullContext3() {
        new ListView(null, null, -1);
    }

    @Test
    public void testGetItem() {
        ItemNASA item1 = new ItemNASA();
        item1.setTitle("Moon");
        item1.setDescription("This is an image of a Moon");
        item1.setDateCreated("2020-09-10");
        item1.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
        item1.setNasaID("EC97-44347-15");
        dataList.add(item1);

        ItemNASA item2 = new ItemNASA();
        item2.setTitle("Apollo");
        item2.setDescription("This is an image of Apollo");
        item2.setDateCreated("2000-01-01");
        item2.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
        item2.setNasaID("EC97-44347-16");
        dataList.add(item2);

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.setAdapter(adapter);
            }
        });

        assertEquals(adapter.getCount(), 2);

        // Get the view at position 0
        assertEquals("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg", adapter.getItem(0).getThumbLink());
        assertEquals("Moon", adapter.getItem(0).getTitle());
        assertEquals("This is an image of a Moon", adapter.getItem(0).getDescription());
        assertEquals("2020-09-10", adapter.getItem(0).getDateCreated());
        assertEquals("EC97-44347-15", adapter.getItem(0).getNasaID());

        // Get the view at position 1
        assertEquals("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg", adapter.getItem(1).getThumbLink());
        assertEquals("Apollo", adapter.getItem(1).getTitle());
        assertEquals("This is an image of Apollo", adapter.getItem(1).getDescription());
        assertEquals("2000-01-01", adapter.getItem(1).getDateCreated());
        assertEquals("EC97-44347-16", adapter.getItem(1).getNasaID());
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
    public void testItemGetView() {
        onView(withId(R.id.search_view)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.search_view)).perform(typeSearchViewText("Moon"));

        onData(anything()).inAdapterView(withId(R.id.content_list)).atPosition(0)
                .onChildView(withId(R.id.item_title))
                .check(matches(withText(containsString("Moon"))));

        onData(anything()).inAdapterView(withId(R.id.content_list)).atPosition(0)
                .onChildView(withId(R.id.item_image))
                .check(matches(isDisplayed()));
    }
}