package wows;

import wows.bot.Core;
import arkwiki.bot.arkWikiMain;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import wows.bot.sbMain;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Chart {

    public static String chart(String msg,int key){
        String username = msg.split(" ")[1];
        String flag = msg.split(" ")[2];
        String base64 = "";
        String id;

        WowsInfosImpl wowsInfos = new WowsInfosImpl();
        if (key == 0){
            System.out.println("ŷ��ģʽ");
            id = wowsInfos.getUserId(username,0);
        }
        else {
            System.out.println("�Ƿ�ģʽ");
            id = wowsInfos.getUserId(username,1);
        }

        String url0 = "";
        //System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Application\\chromedriver.exe");
        WebDriver driver=null;

        try{
            ChromeOptions chromeOptions=new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            //chromeOptions.addArguments("--user-data-dir=C:\\Users\\Orisland\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
            chromeOptions.addArguments("--user-data-dir=C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
            driver = new ChromeDriver(chromeOptions);
            if (flag.equals("1") || flag.equals("0")){
                if (flag.equals("0")){
                    Core.sendGroupMessages(sbMain.selfQQ,sbMain.fromGroup,"�ܱ�ģʽ",0);
                    url0 = "https://wows-numbers.com/player/";
                    if (key == 1){
                        url0 = "https://asia.wows-numbers.com/player/";
                    }
                    driver.manage().window().setSize(new Dimension(1920,9100));
                }
                if (flag.equals("1")){
                    Core.sendGroupMessages(sbMain.selfQQ,sbMain.fromGroup,"С��ģʽ",0);
                    url0 = "https://wows-numbers.com/player/charts/";
                    if (key == 1){
                        url0 = "https://asia.wows-numbers.com/player/charts/";
                    }
                    driver.manage().window().setSize(new Dimension(1920,2600));
                }
            }
            else {
                Core.sendGroupMessages(sbMain.selfQQ,sbMain.fromGroup,"��ģʽ�����ڣ�",0);
                Core.sendGroupMessages(sbMain.selfQQ,sbMain.fromGroup,"0��ȫͼģʽ  1������ģʽ",0);
                return base64 = "?";
            }
            String url = url0 + id + "," + username + "/";
            System.out.println(url);
            driver.get(url);
            System.out.println("����ҳ��");
            driver.get(url);
            System.out.println("chromeҳ�����");
            Thread.sleep(5000);
            base64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            System.out.println("ת�����");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();      //����close������chromedriver������Ȼ���ڡ�
        }

        return base64;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i<10; i++){
            System.out.println("��"+i+"�����");
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
            WebDriver driver;
            ChromeOptions chromeOptions=new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            System.out.println(1);
            chromeOptions.addArguments("--user-data-dir=C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Default");
//        chromeOptions.addArguments("�Cno-sandbox");
//        chromeOptions.addArguments("--proxy-server");
//        chromeOptions.addArguments("--headless");
            System.out.println(1.5);
            driver = new ChromeDriver(chromeOptions);
            String url = "https://wows-numbers.com/player/charts/566316444,Orisland_EX/";
            driver.manage().window().setSize(new Dimension(1920,3000));
            driver.get(url);
            System.out.println(2);
//        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        String url1 = "https://wows-numbers.com/player/charts/";
//        String id = "";
//        String username = "";
//        String url = url1 + id + "," + username + "/";

            Thread.sleep(5000);
//        driver.findElements(By.xpath("/html/body/div[3]/p[2]/a[1]")).get(0).click();
//        driver.findElement(By.className("btn btn-primary btn-xs")).click();
//        driver.close();
            File pic = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try
            {
                org.apache.commons.io.FileUtils.copyFile(pic, new File("E:\\IdeaLib\\xiaolizi\\123.png"));  //ʹ��copyFile()���������ȡ���Ľ�ͼ�ļ�
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}
