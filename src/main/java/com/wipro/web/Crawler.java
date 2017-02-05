package com.wipro.web;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static com.wipro.web.CrawlerConstants.*;

/**
 * This class is the starting point to crawl a website.
 */
public class Crawler {

    private String url;
    private String outputDirectory;


    private LinkCrawler linkCrawler;


    public Crawler(String url, String outputDirectory) {
        this.url = url;
        this.outputDirectory = outputDirectory;
        linkCrawler = new LinkCrawler();
    }



    public void crawl() {
        Map<String, Set<String>> linkMap = linkCrawler.getCrawlResults(this.url);
        generateSiteMap(linkMap.get(SITE_URLS), SITE_URLS_FILE);
        generateSiteMap(linkMap.get(EXTERNAL_URLS), EXTERNAL_URLS_FILE);
        generateSiteMap(linkMap.get(STATIC_URLS), STATIC_URLS_FILE);

    }

    private void generateSiteMap(Set<String> linkSet, String fileName) {

        try {
            Path path = Paths.get(outputDirectory);
            Files.createDirectories(path);

            BufferedWriter bw = getWriter(fileName);
            for (String url : linkSet) {
                bw.write(url);
                bw.newLine();
            }
            bw.flush();
            bw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private BufferedWriter getWriter(String fileName) throws IOException {


        File file = new File(outputDirectory, fileName);
        file.createNewFile();
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }

    public void setLinkCrawler(LinkCrawler linkCrawler) {
        this.linkCrawler = linkCrawler;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("###########################################################################################");
            System.out.println("Usage : java -jar <jar-name> <site-url> <output-directory>");
            System.out.println("Example : java -jar target\\web-crawler-1.0-SNAPSHOT.jar http://wiprodigital.com c:/temp/");
            System.out.println("###########################################################################################");
            System.exit(-1);
        }

        Crawler crawler = new Crawler(args[0], args[1]);
        crawler.crawl();
    }

}
