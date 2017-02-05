package com.wipro.web;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wipro.web.CrawlerConstants.*;


public class LinkCrawler {


    private static final String HREF_PATTERN = "href=\"(.*?)\"";
    private static final String STATIC_PATTERN = "src=\"(.*?)\"";

    private String baseUrl;

    private Set<String> linkVisited = new HashSet();
    private Set<String> uniqueLinks = new HashSet();
    private Set<String> staticLinks = new HashSet();
    private Set<String> externalLinks = new HashSet();
    private Map<String, Set<String>> linkMap = new HashMap<String, Set<String>>();


    private Logger logger = Logger.getLogger(LinkCrawler.class.toString());

    public static void main(String[] args) {
        LinkCrawler singleLinkCrawler = new LinkCrawler();
        singleLinkCrawler.getCrawlResults("http://wiprodigital.com");
    }

    public Map<String, Set<String>> getCrawlResults(String url) {
        this.baseUrl = url;
        return crawlRecursive(url);
    }

    private Map<String, Set<String>> crawlRecursive(String url) {
        logger.log(Level.INFO, "Visiting " + url);
        if (!linkVisited.contains(url)) {
            linkVisited.add(url);
            List<String> hrefList = new ArrayList();


            String pageContent = getResponse(url);

            Pattern linkPattern = Pattern.compile(HREF_PATTERN);
            Matcher linkMatch = linkPattern.matcher(pageContent);
            while (linkMatch.find()) {
                String nexPageUrl = linkMatch.group(1);
                if (isNewInternalLink(nexPageUrl)) {
                    uniqueLinks.add(nexPageUrl);
                    hrefList.add(nexPageUrl);
                } else if (isNewExternalLink(nexPageUrl)) {
                    externalLinks.add(nexPageUrl);
                }
            }

            Pattern staticPattern = Pattern.compile(STATIC_PATTERN);
            Matcher staticMatch = staticPattern.matcher(pageContent);
            while (staticMatch.find()) {
                String nexPageUrl = staticMatch.group(1);
                if (isNewStaticLink(nexPageUrl)) {
                    staticLinks.add(nexPageUrl);
                }
            }
            for (String link : hrefList) {
                crawlRecursive(link);
            }
        }

        linkMap.put(SITE_URLS, uniqueLinks);
        linkMap.put(EXTERNAL_URLS, externalLinks);
        linkMap.put(STATIC_URLS, staticLinks);
        return linkMap;
    }

    private boolean isNewInternalLink(String nexPageUrl) {
        return !uniqueLinks.contains(nexPageUrl) && nexPageUrl.startsWith(baseUrl);
    }

    private boolean isNewExternalLink(String nexPageUrl) {
        return !externalLinks.contains(nexPageUrl) && !nexPageUrl.startsWith(baseUrl) && nexPageUrl.startsWith(HTTP);
    }

    private boolean isNewStaticLink(String nexPageUrl) {
        return !staticLinks.contains(nexPageUrl) && nexPageUrl.startsWith(HTTP);
    }

    private String getResponse(String url) {
        HttpConnectionWrapper httpConnectionWrapper = new HttpConnectionWrapper(url);
        return httpConnectionWrapper.getPageContent();
    }
}
