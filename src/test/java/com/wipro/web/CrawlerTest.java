package com.wipro.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.wipro.web.CrawlerConstants.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class
)
public class CrawlerTest {

    @Mock
    LinkCrawler linkCrawler;


    @Test
    public void testCrawlerIsCalled() {
        Set<String> results = new HashSet<String>();
        results.add("http://wiprodigital.com");
        Map<String, Set<String>> linkMap = new HashMap<String, Set<String>>();
        linkMap.put(SITE_URLS, results);
        linkMap.put(EXTERNAL_URLS, new HashSet<String>());
        linkMap.put(STATIC_URLS, new HashSet<String>());

        when(linkCrawler.getCrawlResults("http://wiprodigital.com")).thenReturn(linkMap);
        Crawler crawler = new Crawler("http://wiprodigital.com", "c:/temp/");
        crawler.setLinkCrawler(linkCrawler);
        crawler.crawl();
        verify(linkCrawler, times(1)).getCrawlResults(anyString());
    }

    @Test
    public void testInternalUrlsAreCalledAtleastOnce() {
        Set<String> results = new HashSet<String>();
        results.add("http://external-url");
        Map<String, Set<String>> linkMap = mock(Map.class);
        when(linkMap.get(SITE_URLS)).thenReturn(results);
        when(linkMap.get(EXTERNAL_URLS)).thenReturn(new HashSet<String>());
        when(linkMap.get(STATIC_URLS)).thenReturn(new HashSet<String>());

        when(linkCrawler.getCrawlResults("http://wiprodigital.com")).thenReturn(linkMap);
        Crawler crawler = new Crawler("http://wiprodigital.com", "c:/temp/");
        crawler.setLinkCrawler(linkCrawler);
        crawler.crawl();
        verify(linkMap, atLeastOnce()).get(SITE_URLS);
    }

    @Test
    public void testExternalUrlsAreCalledAtleastOnce() {
        Set<String> results = new HashSet<String>();
        results.add("http://external-url");
        Map<String, Set<String>> linkMap = mock(Map.class);
        when(linkMap.get(SITE_URLS)).thenReturn(new HashSet<String>());
        when(linkMap.get(EXTERNAL_URLS)).thenReturn(results);
        when(linkMap.get(STATIC_URLS)).thenReturn(new HashSet<String>());

        when(linkCrawler.getCrawlResults("http://wiprodigital.com")).thenReturn(linkMap);
        Crawler crawler = new Crawler("http://wiprodigital.com", "c:/temp/");
        crawler.setLinkCrawler(linkCrawler);
        crawler.crawl();
        verify(linkMap, atLeastOnce()).get(EXTERNAL_URLS);
    }

    @Test
    public void testStaticUrlsAreCalledAtleastOnce() {
        Set<String> results = new HashSet<String>();
        results.add("http://static-url");
        Map<String, Set<String>> linkMap = mock(Map.class);
        when(linkMap.get(SITE_URLS)).thenReturn(new HashSet<String>());
        when(linkMap.get(EXTERNAL_URLS)).thenReturn(new HashSet<String>());
        when(linkMap.get(STATIC_URLS)).thenReturn(results);
        when(linkCrawler.getCrawlResults("http://wiprodigital.com")).thenReturn(linkMap);
        Crawler crawler = new Crawler("http://wiprodigital.com", "c:/temp/");
        crawler.setLinkCrawler(linkCrawler);
        crawler.crawl();
        verify(linkMap, atLeastOnce()).get(SITE_URLS);
    }
}
