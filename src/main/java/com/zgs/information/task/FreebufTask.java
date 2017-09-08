package com.zgs.information.task;

import com.alibaba.fastjson.JSONArray;
import com.zgs.information.mail.TaskSendEmail;
import com.zgs.information.msg.BugInfo;
import com.zgs.information.utils.YamlUtils;
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
public class FreebufTask  extends java.util.TimerTask{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static Map<String,String> checkRecord=new HashMap<>();
    @Override
    public void run() {
        String checkurl= YamlUtils.getValue("information.task.freebuf.checkurl");
        String details=YamlUtils.getValue("information.task.freebuf.details");
        try {
            Document doc = Jsoup.parse(new URL(checkurl), 10000);
            Elements elList=doc.getElementsByAttributeValueContaining("class","news_inner news-list");
            String lastNumber=checkRecord.get("lastNumber");
            log.info("freebuf bug check update data");
            List<BugInfo> todayBug=new ArrayList<>();
            for (int i = 0; i < elList.size(); i++) {
                Elements infoBlock = elList.get(i).getElementsByAttributeValue("class","news-info");
                Elements link=infoBlock.first().getElementsByTag("dt").first().getElementsByTag("a");
                Elements smalltext=infoBlock.first().getElementsByAttributeValue("class","text");
                if(!StringUtil.isBlank(lastNumber)){
                    if(!checkRecord.containsKey(link.text())){
                        BugInfo bugInfo=new BugInfo(link.text(),link.attr("href"),smalltext.text(),"ALL","Freebuf源",new Date());
                        todayBug.add(bugInfo);
                        log.info("[0day]漏洞编号：" + link.text() + ",漏洞简介：" + smalltext.text() + ",漏洞详情：" + link.attr("href"));
                    }
                }
                checkRecord.put(link.text(),link.attr("href"));
                if(StringUtil.isBlank(lastNumber)) {//只有第一次采集数据时才打印初始化信息
                    log.info("漏洞编号：" + link.text() + ",漏洞简介：" + smalltext.text() + ",漏洞详情：" + link.attr("href"));
                }
            }
            checkRecord.put("lastNumber",elList.size()+"");
            if(todayBug.size()>0&&!StringUtil.isBlank(lastNumber)){
                TaskSendEmail.sendMsg(todayBug);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
