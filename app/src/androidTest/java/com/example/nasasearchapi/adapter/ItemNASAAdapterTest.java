package com.example.nasasearchapi.adapter;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import com.example.nasasearchapi.MainActivity;
import com.example.nasasearchapi.R;
import com.example.nasasearchapi.data.ItemNASA;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;


@SmallTest
@RunWith(AndroidJUnit4.class)
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

        ItemNASA item1 = new ItemNASA();
        item1.setTitle("Moon");
        item1.setDescription("This is an image of a Moon");
        item1.setDateCreated("2020-09-10");
        item1.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
        item1.setNasaID("EC97-44347-15");
        dataList.add(item1);

        ItemNASA item2 = new ItemNASA();
        item1.setTitle("Apollo");
        item1.setDescription("This is an image of a Apollo");
        item1.setDateCreated("2000-01-01");
        item1.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
        item1.setNasaID("EC97-44347-16");
        dataList.add(item2);

        adapter = new ItemNASAAdapter(mActivity, R.layout.item_cell, dataList);
        mListView = (ListView) mActivity.findViewById(R.id.content_list);

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.setAdapter(adapter);
            }
        });
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
    public void getView() {
        ItemNASA item1 = new ItemNASA();
        item1.setTitle("Moon");
        item1.setDescription("This is an image of a Moon");
        item1.setDateCreated("2020-09-10");
        item1.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
        item1.setNasaID("EC97-44347-15");
        dataList.add(item1);

        ItemNASA item2 = new ItemNASA();
        item1.setTitle("Apollo");
        item1.setDescription("This is an image of a Apollo");
        item1.setDateCreated("2000-01-01");
        item1.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
        item1.setNasaID("EC97-44347-16");
        dataList.add(item2);
        adapter.notifyDataSetChanged();

        // Get the view at position 0
        assertEquals("link", adapter.getItem(0).getThumbLink());


    }
}