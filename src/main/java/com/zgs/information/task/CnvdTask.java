package com.zgs.information.task;

import com.alibaba.fastjson.JSONArray;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.zgs.information.mail.SendEmailUtil;
import com.zgs.information.mail.TaskSendEmail;
import com.zgs.information.msg.BugInfo;
import com.zgs.information.utils.HtmlProxyUtil;
import com.zgs.information.utils.MD5Util;
import com.zgs.information.utils.YamlUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

/**
 * @author Shmily
 * @email shmily_zgs@163.com
 * @date 2017/9/6 14:32
 */
public class CnvdTask extends java.util.TimerTask{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static Map<String,String> checkRecord=new HashMap<>();
    @Override
    public void run() {
        String checkurl= YamlUtils.getValue("information.task.cnvd.checkurl");
        String details=YamlUtils.getValue("information.task.cnvd.details");
        try {
            Set<Cookie> cookies=HtmlProxyUtil.getCookies(checkurl,10000);
            Connection connection=Jsoup.connect(checkurl)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                    .header("Host","www.cnvd.org.cn")
                    .header("Referer","http://www.cnvd.org.cn/flaw/list.htm")
                    .header("Upgrade-Insecure-Requests","0")
                    .timeout(10000);
            for(Cookie cookie:cookies){
                connection.cookie(cookie.getName(),cookie.getValue());
            }
            Document doc = connection.get();
            Elements elList=doc.getElementsByAttributeValueContaining("class","tlist");
            Element el=elList.first();
            Element tbody = el.getElementsByTag("tbody").first();
            Elements trLists = tbody.getElementsByTag("tr");
            String lastNumber=checkRecord.get("lastNumber");
            log.info("cnvd bug check update data");
            List<BugInfo> todayBug=new ArrayList<>();
            for (int i = 0; i < trLists.size(); i++) {
                Elements link = trLists.get(i).getElementsByTag("a");
                if(!StringUtil.isBlank(lastNumber)){
                    if(!checkRecord.containsKey(link.attr("href").replace("/flaw/show/",""))){
                        BugInfo bugInfo=new BugInfo(link.text(),details+link.attr("href"),link.text(),"All News","国家信息安全漏洞共享平台",new Date());
                        todayBug.add(bugInfo);
                        log.info("[0day]漏洞编号：" + link.attr("href").replace("/flaw/show/", "") + ",漏洞简介：" + link.text() + ",漏洞详情：" + details + link.attr("href"));
                    }
                }
                checkRecord.put(link.attr("href").replace("/flaw/show/",""),details+link.attr("href"));
                if(StringUtil.isBlank(lastNumber)) {//只有第一次采集数据时才打印初始化信息
                    log.info("漏洞编号：" + link.attr("href").replace("/flaw/show/", "") + ",漏洞简介：" + link.text() + ",漏洞详情：" + details + link.attr("href"));
                }
            }
            checkRecord.put("lastNumber",trLists.size()+"");
            if(todayBug.size()>0&&!StringUtil.isBlank(lastNumber)){
                TaskSendEmail.sendMsg(todayBug);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
