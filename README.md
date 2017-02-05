#Instruction to run project.
1. Clone this repository to your local machine.
2. Run "mvn package"
3. Run following command to crawl a website.

        java -jar target\web-crawler-1.0-SNAPSHOT.jar http://wiprodigital.com c:/temp

    Note : Above command has two parameter
    * Website URL to be crawled.
    * Directory where sitemaps would be saved.

4. Following files are generated in the supplied directory
    * sitemap.xml (links excluding static content).
    * sitemap-external.xml (external links).
    * sitemap-static.xml (static content).



#Limitation
1. This program is suitable for small/medium website. It could run into memory issues for some websites with lots of content/links(e.g. Big Data website).
2. This is not multithreaded application so performance is slow as compared to available crawler in the market.





