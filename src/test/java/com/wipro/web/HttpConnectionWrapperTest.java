package com.wipro.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpConnectionWrapperTest {

    @Mock
    HttpURLConnection mockHttpURLConnection;

    private String dummyUrl = "http://www.test-url.com";
    private String dummyContent = "<html><head></head><body><a href=\"http://www.test-url.com\">test</a></body></html>";

    @Test
    public void testHttpGetRequestSuccessful() {

        try {
            when(mockHttpURLConnection.getInputStream()).thenReturn(new ByteArrayInputStream(dummyContent.getBytes()));
            HttpConnectionWrapper httpConnectionWrapper = new HttpConnectionWrapper(dummyUrl);
            httpConnectionWrapper.setConnection(mockHttpURLConnection);
            assertTrue(httpConnectionWrapper.getPageContent().equals(dummyContent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
