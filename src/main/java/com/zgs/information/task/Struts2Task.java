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
 * @date 2017/9/6 9:57
 */
public class Struts2Task extends java.util.TimerTask{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static Map<String,String> checkRecord=new HashMap<>();
    @Override
    public void run() {
        String checkurl=YamlUtils.getValue("information.task.struts2.checkurl");
        String details=YamlUtils.getValue("information.task.struts2.details");
        try {
            Document doc = Jsoup.parse(new URL(checkurl), 10000);
            Elements elList=doc.getElementsByAttributeValueContaining("class","childpages-macro");
            Element el=elList.first();
            Elements liLists = el.select("li");
            String lastNumber=checkRecord.get("lastNumber");
            if(!StringUtil.isBlank(lastNumber)&&(liLists.size()+"").equals(lastNumber)){
                log.info("struts2 bug not update");
            }else{
                if(StringUtil.isBlank(lastNumber)){
                    log.info("struts2 bug first check , no send email");
                }else{
                    log.info("struts2 bug check update data , send email");
                }
                List<BugInfo> todayBug=new ArrayList<>();
                for (int i = 0; i < liLists.size(); i++) {
                    Elements link = liLists.get(i).getElementsByTag("a");
                    Elements smalltext = liLists.get(i).getElementsByTag("span");
                    if(!StringUtil.isBlank(lastNumber)){
                          if(!checkRecord.containsKey(link.text())){
                              BugInfo bugInfo=new BugInfo(link.text(),details+link.attr("href"),smalltext.text(),"Struts2","Struts2官方源",new Date());
                              todayBug.add(bugInfo);
                              log.info("[0day]漏洞编号："+link.text()+",漏洞简介："+smalltext.text()+",漏洞详情："+details+link.attr("href"));
                          }
                    }
                    checkRecord.put(link.text(),details+link.attr("href"));
                    log.info("漏洞编号："+link.text()+",漏洞简介："+smalltext.text()+",漏洞详情："+details+link.attr("href"));
                }
                checkRecord.put("lastNumber",liLists.size()+"");
                if(todayBug.size()>0){
                    TaskSendEmail.sendMsg(todayBug);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
