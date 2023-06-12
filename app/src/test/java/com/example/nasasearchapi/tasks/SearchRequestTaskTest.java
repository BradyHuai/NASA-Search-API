package com.example.nasasearchapi.tasks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.example.nasasearchapi.data.ItemNASA;
import com.example.nasasearchapi.eventListener.SearchResultListener;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SearchRequestTaskTest {
    @Before
    public void setup() {
    }

    @Test
    public void testDoInBackgroundSuccess() throws Exception {
        SearchRequestTask task = new SearchRequestTask();

        String result = task.doInBackground("Earth");

        assertTrue(result.contains("collection"));
        assertTrue(result.contains("items"));
    }

    @Test
    public void testDoInBackgroundFail() throws Exception {
        SearchRequestTask task = new SearchRequestTask();

        String result = task.doInBackground("doesnotexist");

        assertTrue(result.contains("collection"));
        assertTrue(result.contains("items"));
        assertTrue(result.contains("\"total_hits\":0"));
    }

    @Test
    public void testDoInBackgroundIOException() throws Exception {
        // TODO
//        SearchRequestTask task = new SearchRequestTask();
//
//        // ProtocolException
//        // MalformedURLException
//        // IOException
//
//        // new URL
//        // url.openConnection();
//        // reader.readLine()
//        // connection.getInputStream()
////        connection.setRequestMethod("GET");
//
//        URL mockUrl = PowerMockito.mock(URL.class);
//        doThrow(new IOException("error")).when(mockUrl).openConnection().getInputStream();
//        String result = task.doInBackground("");
//        assertNull(result);
    }

    @Test
    public void testOnPostExecuteWithNonNullInput() {
        // Mock the necessary dependencies and inputs
        String input = "{\"collection\":{\"items\":[{\"data\":[{\"title\":\"Moon\",\"date_created\":\"2023-06-10\",\"description\":\"This is Moon\",\"nasa_id\":\"12345\"}],\"links\":[{\"href\":\"https://example.com/image.jpg\"}]}]}}";
        ItemNASA expected = new ItemNASA();
        expected.setNasaID("12345");
        expected.setTitle("Moon");
        expected.setDescription("This is Moon");
        expected.setDateCreated("2023-06-10");
        expected.setThumbLink("https://example.com/image.jpg");

        // Create a mock SearchResultListener
        SearchResultListener listenerMock = Mockito.mock(SearchResultListener.class);

        // Create an instance of SearchRequestTask
        SearchRequestTask searchTask = new SearchRequestTask();
        searchTask.addListener(listenerMock);

        // Simulate the execution of the AsyncTask and trigger the onPostExecute method
        searchTask.onPostExecute(input);

        // Verify that the onDataAdded method is called on the MainActivity with the expected parameters
        verify(listenerMock).onDataAdded("", expected);

        // Verify that no other methods on the listener were called
        verifyNoMoreInteractions(listenerMock);
    }

    @Test
    public void testOnPostExecuteWithNullInput() {
        // Mock the necessary dependencies and inputs
        String input = "{\"collection\":{\"items\":[{\"data\":[{\"title\":\"Moon\",\"date_created\":\"2023-06-10\",\"description\":\"This is Moon\",\"nasa_id\":\"12345\"}]}]}}";
        ItemNASA expected = new ItemNASA();
        expected.setNasaID("12345");
        expected.setTitle("Moon");
        expected.setDescription("This is Moon");
        expected.setDateCreated("2023-06-10");
        expected.setThumbLink("NA");

        // Create a mock SearchResultListener
        SearchResultListener listenerMock = Mockito.mock(SearchResultListener.class);

        // Create an instance of SearchRequestTask
        SearchRequestTask searchTask = new SearchRequestTask();
        searchTask.addListener(listenerMock);

        // Simulate the execution of the AsyncTask and trigger the onPostExecute method
        searchTask.onPostExecute(input);

        // Verify that the onDataAdded method is called on the MainActivity with the expected parameters
        verify(listenerMock).onDataAdded("", expected);

        // Verify that no other methods on the listener were called
        verifyNoMoreInteractions(listenerMock);
    }

    @Test
    public void testOnPostExecute_JSONException() {
        //TODO
    }
}