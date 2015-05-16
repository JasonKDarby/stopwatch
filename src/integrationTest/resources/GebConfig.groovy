import org.openqa.selenium.phantomjs.PhantomJSDriver

baseUrl = "http://localhost:5050/"
reportsDir = "build/geb-reports"

waiting {
    timeout = 2
}

environments {
    phantomJs {
        driver = { new PhantomJSDriver() }
    }
}