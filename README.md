# Running the application
1. Clone the repository with git (if you don't have git please follow: https://www.linode.com/docs/development/version-control/how-to-install-git-on-linux-mac-and-windows/)
2. If you don't have maven please follow: https://www.baeldung.com/install-maven-on-windows-linux-mac
3. Go to cloned repository via terminal
4. Type in command <b>mvn spring-boot:run</b> and hit Enter.
5. Open your browser and type in: **http://localhost:8080/scanURL?url=<URL_TO_SCAN>** where:
URL_TO_SCAN - swap with the desired url you want to dig through (please look at Point 7 in "Future development")
6. Wait for the output.
7. The result is JSON - go to **https://jsonformatter.curiousconcept.com/** and paste the result to make it more readable (formatted)
8. Any errors are displayed in the console.

##What was not yet done
The crawler recognizes the same URL with the trailing slash as two different sites. However technically these are two different cases - for the user are not and the result can be misleading.

##Future development
1. Make more complicated tests.
2. Make integration tests with the existing site (not HTML file).
3. Different type of assets handling - now crawler only catches images tag
4. Different point of entry - passing the URL to scan in different way (not like request param) - in ReST call body for example
5. Create a GUI for the user - don't use external JSON formatters to make an output readable
6. Consider expanding CORS - now only local machine can call the scan.
7. Possibility to scan from some subpage - **now you need to pass a main page to scan the whole site**.