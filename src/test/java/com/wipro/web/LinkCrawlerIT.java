package com.wipro.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class LinkCrawlerIT {

    @Test
    public void testInternalURL() {
        LinkCrawler crawler = new LinkCrawler();
        Map<String, Set<String>> map = crawler.getCrawlResults("http://wiprodigital.com/");
        assertTrue(map.get(CrawlerConstants.SITE_URLS).contains("http://wiprodigital.com/partners/microsoft/"));
    }

    @Test
    public void testExternalURL() {
        LinkCrawler crawler = new LinkCrawler();
        Map<String, Set<String>> map = crawler.getCrawlResults("http://wiprodigital.com/");
        assertTrue(map.get(CrawlerConstants.EXTERNAL_URLS).contains("https://www.callme.dk/"));
    }

    @Test
    public void testStaticlURL() {
        LinkCrawler crawler = new LinkCrawler();
        Map<String, Set<String>> map = crawler.getCrawlResults("http://wiprodigital.com/");
        assertTrue(map.get(CrawlerConstants.STATIC_URLS).contains("http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/uploads/2016/05/cisco.png"));
    }
}
