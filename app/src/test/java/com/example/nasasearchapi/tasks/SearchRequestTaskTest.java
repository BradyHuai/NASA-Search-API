package com.example.nasasearchapi.tasks;

import com.example.nasasearchapi.data.ItemNASA;
import com.example.nasasearchapi.eventListener.SearchResultListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchRequestTaskTest {

    @Mock
    private HttpURLConnection mockConnection;

    @Mock
    private BufferedReader mockReader;

    @Mock
    private SearchResultListener mockListener;

    private SearchRequestTask searchRequestTask;

    @Before
    public void setup() {
        searchRequestTask = new SearchRequestTask();
        searchRequestTask.addListener(mockListener);
    }

    @Test
    public void doInBackground_shouldReturnSearchResult() throws IOException {
        String expectedResult = "{\"collection\":{\"items\":[]}}";

        // Mock the connection and reader
        when(mockConnection.getInputStream()).thenReturn(getMockInputStream(expectedResult));
        when(mockReader.readLine()).thenReturn(expectedResult, null);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        // Mock the URL and connection
        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        // Set the mocked URL in the searchRequestTask
        SearchRequestTask taskSpy = spy(searchRequestTask);
        doReturn(mockUrl).when(taskSpy).execute(any(String.class));

        // Invoke the doInBackground method
        String result = taskSpy.doInBackground("query");

        // Verify the expected result
        assertEquals(expectedResult, result);
    }

    @Test
    public void onPostExecute_shouldNotifyListenersWithDataAdded() throws JSONException {
        String searchResult = "{\"collection\":{\"items\":[" +
                "{\"data\":[{\"title\":\"Title\",\"date_created\":\"2023-01-01\",\"description\":\"Description\",\"nasa_id\":\"123\"}],\"links\":[{\"href\":\"thumbnail_link\"}]}," +
                "{\"data\":[{\"title\":\"Title2\",\"date_created\":\"2023-01-02\",\"description\":\"Description2\",\"nasa_id\":\"456\"}]}]}}";

        // Invoke the onPostExecute method
        searchRequestTask.onPostExecute(searchResult);

        // Verify that the onDataAdded method of the listener is called with the expected ItemNASA objects
        ItemNASA expectedItem1 = new ItemNASA();
        expectedItem1.setTitle("Title");
        expectedItem1.setDateCreated("2023-01-01");
        expectedItem1.setDescription("Description");
        expectedItem1.setNasaID("123");
        expectedItem1.setThumbLink("thumbnail_link");

        ItemNASA expectedItem2 = new ItemNASA();
        expectedItem2.setTitle("Title2");
        expectedItem2.setDateCreated("2023-01-02");
        expectedItem2.setDescription("Description2");
        expectedItem2.setNasaID("456");
        expectedItem2.setThumbLink("NA");

        verify(mockListener, times(2)).onDataAdded("", expectedItem1);
        verify(mockListener, times(1)).onDataAdded("", expectedItem2);
    }

    // Helper method to mock an InputStream with a given string
    private InputStream getMockInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes());
    }
}