#Instruction to run project.
1. Clone this repository to your local machine.
2. Run "mvn package"
3. Run following command to crawl a website.

        java -jar target\web-crawler-1.0-SNAPSHOT.jar http://wiprodigital.com c:/temp

    Note : Above command has two parameter
    1) Website URL to be crawled.
    2) Directory where sitemaps would be saved.

4. Following files are generated in the supplied directory
    1) sitemap.xml (links excluding static content).
    2) sitemap-external.xml (external links).
    3) sitemap-static.xml (static content).



