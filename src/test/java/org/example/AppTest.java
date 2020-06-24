package org.example;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class AppTest extends BaseClass
{

    String email = "yourEmailAddress";
    String pwd = "*******";
    String searchKey = "bilgisayar";



    @Test
    public void testCase() throws InterruptedException {
        setUp();
        driver.get("https://www.n11.com/");
        Assert.assertEquals(driver.getTitle(),"n11.com - Alışverişin Uğurlu Adresi");//kontrol
        Thread.sleep(2000);
        driver.get("https://www.n11.com/giris-yap");
        Thread.sleep(2000);

        WebElement user = driver.findElement(By.cssSelector("#email")); //Email
        user.sendKeys(email);
        Thread.sleep(2000);
        WebElement password = driver.findElement(By.cssSelector("#password")); //Password
        password.sendKeys(pwd);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#loginButton")).click(); //Login
        Thread.sleep(3000);
        

        driver.findElement(By.cssSelector("#searchData")).sendKeys(searchKey);// Arama kutucuğuna bilgisayar kelimesi girilir.
        Thread.sleep(4000);
        WebElement search = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div[2]/div[1]/a"));//search butonu
        search.click();
        Thread.sleep(3000);


        WebElement pages = driver.findElement(By.xpath("//*[@id=\"contentListing\"]/div/div/div[2]/div[4]"));
        WebElement secondPage = pages.findElement(By.xpath("//*[@id=\"contentListing\"]/div/div/div[2]/div[4]/a[2]"));//2.sayfa butonu
        secondPage.click();//2.sayfa açılır.
        Thread.sleep(3000);
        String actualUrl = "https://www.n11.com/arama?q=bilgisayar&pg=2";
        String expectedUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);//2.sayfanın açıldığı kontrol edilir.


        WebElement area = driver.findElement(By.xpath("//*[@id=\"view\"]/ul"));
        List<WebElement> links = area.findElements(By.tagName("img"));
        WebElement random = links.get(new Random().nextInt(links.size()));//Rastgele ürün seçimi
        random.click();
        String lastPage = driver.getCurrentUrl();//Random olarak açtığım bilgisayarın url'i
        Thread.sleep(5000);

        driver.findElement(By.xpath("//a[@class='btn btnGrey btnAddBasket']")).click();//Sepete eklenir
        Thread.sleep(4000);
        String priceUrun = driver.findElement(By.cssSelector("#contentProDetail > div > div.proDetailArea > div.proDetail > div.paymentDetail > div.price-cover > div > div.priceDetail > div > ins")).getAttribute("content");

        driver.get("https://www.n11.com/sepetim");
        String priceSepet= driver.findElement(By.xpath("//*[@id=\"newCheckout\"]/div/div[1]/div[2]/div[1]/section/table[2]/tbody/tr/td[3]/div[2]/div/span")).getText();

        if(priceUrun==priceSepet)//Fiyatları karşılaştır.
                System.out.println("Fiyatlar aynı");
        else
                System.out.println("Fiyatlar farklı");


        //ürünün adetini arttır.
        driver.findElement(By.xpath("//span[@class='spinnerUp spinnerArrow']")).click();
        Thread.sleep(3000);
        String adet = driver.findElement(By.xpath("//input[@class='quantity']")).getAttribute("value");
        int sayi= 2;
        String x = String.valueOf(sayi);
        if(adet == x)
            System.out.println("ürün adeti 2");
        else
            System.out.println("ürün adeti 2 değil.");

        //ürünü sil
        driver.findElement(By.xpath("//span[@class ='removeProd svgIcon svgIcon_trash']")).click();
        Thread.sleep(5000);
        String bosSepetUrl = "https://www.n11.com/sepetim";
        Assert.assertEquals(bosSepetUrl,driver.getCurrentUrl());

        stop();
    }

}

