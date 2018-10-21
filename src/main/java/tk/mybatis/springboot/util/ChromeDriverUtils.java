package tk.mybatis.springboot.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class ChromeDriverUtils {
//    private static ChromeDriverService service;

    public static WebDriver getChromeDriver() {
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:/Python/chromedriver.exe")) .usingAnyFreePort().build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }

    public static void main(String[] args) throws IOException {

        WebDriver driver = getChromeDriver();
        // 让浏览器访问 Baidu
        driver.get("https://www.taobao.com/");
        // 用下面代码也可以实现
        //driver.navigate().to("http://www.baidu.com");
        // 获取 网页的 title
        System.out.println(" Page title is: " +driver.getTitle());
        // 通过 id 找到 input 的 DOM
        WebElement element =driver.findElement(By.id("q"));
        // 输入关键字
        element.sendKeys("东鹏瓷砖");
        // 提交 input 所在的 form
        element.submit();
        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
        new WebDriverWait(driver, 10).until(new ExpectedCondition() {
            @Override
            public Object apply(Object input) {
                return ((WebDriver)input).getTitle().toLowerCase().startsWith("东鹏瓷砖");
            }
        });
        // 显示搜索结果页面的 title
        System.out.println(" Page title is: " +driver.getTitle());
        // 关闭浏览器
        driver.quit();
        // 关闭 ChromeDriver 接口
//        service.stop();
    }
}
