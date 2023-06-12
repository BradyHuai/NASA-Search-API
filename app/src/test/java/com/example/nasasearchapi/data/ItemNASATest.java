package com.example.nasasearchapi.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemNASATest {

    private ItemNASA itemNASA;

    @Before
    public void setUp() throws Exception {
        itemNASA = new ItemNASA();
    }

    @After
    public void tearDown() throws Exception {
        itemNASA = null;
    }

    @Test
    public void testGetTitle() {
        itemNASA.setTitle("Moon");
        String result = itemNASA.getTitle();
        assertEquals("Moon", result);
    }

    @Test
    public void testGetDescription() {
        itemNASA.setDescription("This is an image of a Moon");
        String result = itemNASA.getDescription();
        assertEquals("This is an image of a Moon", result);
    }

    @Test
    public void testGetDateCreated() {
        itemNASA.setDateCreated("2020-09-10");
        String result = itemNASA.getDateCreated();
        assertEquals("2020-09-10", result);
    }

    @Test
    public void testGetThumbLink() {
        itemNASA.setThumbLink("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg");
        String result = itemNASA.getThumbLink();
        assertEquals("https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg", result);
    }

    @Test
    public void testGetNasaID() {
        itemNASA.setNasaID("EC97-44347-15");
        String result = itemNASA.getNasaID();
        assertEquals("EC97-44347-15", result);
    }
}