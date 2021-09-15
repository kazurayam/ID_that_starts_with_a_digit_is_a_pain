import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path fixtureHtml = projectDir.resolve("fixture.html")
URL fixtureURL = fixtureHtml.toFile().toURI().toURL()

WebUI.openBrowser('')
WebUI.navigateToUrl(fixtureURL.toExternalForm())

// check the rendering mode of the current browser
String compatMode = WebUI.executeJavaScript("return document.compatMode;",null)
WebUI.comment("compatMode is " + compatMode)    // we expect "CSS1Compat"

//boolean b = WebUI.verifyElementHasAttribute(findTestObject("TC_06_/div_add_5fae2920d068b224134037e8"), "style", 1)
//WebUI.comment("WebUI.verifyElementHasAttribute() returned " + b)
WebUI.comment("class is \"" + WebUI.getAttribute(findTestObject("TC_06_/div_add_5fae2920d068b224134037e8"), "class") + "\"")

// try to find the div tag by a XPath with ID value
WebDriver driver = DriverFactory.getWebDriver()
WebElement subimage = driver.findElement(By.xpath("//*[@id = '5fae2920d068b224134037e8']"))
assert subimage != null   // SUCCESS !

String js1 = 'return document.querySelector("#5fae2920d068b224134037e8");'
try {
	String divStyle = WebUI.executeJavaScript(js1, null)
} catch (Exception e) {
	WebUI.comment("failed to find the div tag by JavaScript \"${js1}\"")
}

// Surprise! 
// https://stackoverflow.com/questions/5672903/can-i-have-an-element-with-an-id-that-starts-with-a-number
String js2 = 'return document.querySelector(\\"#\\\\35fae2920d068b224134037e8\\").style;'
String divStyle = WebUI.executeJavaScript(js2, null)
WebUI.comment("divStyle is " + divStyle)


WebUI.closeBrowser()