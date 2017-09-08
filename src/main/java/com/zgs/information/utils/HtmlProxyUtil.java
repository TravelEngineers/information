package com.zgs.information.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.net.URL;
import java.util.Set;

/**
 * @author Shmily
 * @email shmily_zgs@163.com
 * @date 2017/9/7 13:29
 */
public class HtmlProxyUtil {

    public static Set<Cookie> getCookies(String url,int timeout){
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try{
            webClient.getBrowserVersion().setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.setRefreshHandler(new ThreadedRefreshHandler());//应对网站反爬虫神器设置
            webClient.getOptions().setJavaScriptEnabled(true);//开启js解析。对于变态网页，这个是必须的
            webClient.getOptions().setCssEnabled(false);//开启css解析。对于变态网页，这个是必须的。
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setTimeout(timeout);
            webClient.getPage(url);
            return webClient.getCookieManager().getCookies();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            webClient.close();
        }
        return null;
    }
}
