package com.zgs.information;

import com.zgs.information.mail.SendEmailUtil;
import com.zgs.information.task.CnvdTask;
import com.zgs.information.task.FreebufTask;
import com.zgs.information.task.Struts2Task;
import com.zgs.information.utils.YamlUtils;

import java.util.Timer;

/**
 * @author Shmily
 * @email shmily_zgs@163.com
 * @date 2017/9/6 9:55
 */
public class InformationApp {
    public static void main(String[] args) {
        Timer timer = new Timer();
        //在1秒后执行此任务,每次间隔5秒执行一次
        timer.schedule(new Struts2Task(), 1000,Long.parseLong(YamlUtils.getValue("information.task.struts2.checkcycle")));
        timer.schedule(new CnvdTask(), 1000,Long.parseLong(YamlUtils.getValue("information.task.cnvd.checkcycle")));
        timer.schedule(new FreebufTask(), 1000,Long.parseLong(YamlUtils.getValue("information.task.freebuf.checkcycle")));
        subscribe();
    }
    public static void subscribe() {
        String toUser = YamlUtils.getValue("information.config.mail.toUser");
        for (String user : toUser.split(",")) {
            SendEmailUtil sendEmailUtil = new SendEmailUtil(user, "最新互联网漏洞情报订阅通知", "欢迎您订阅漏洞情报信息，每次情报系统初始化后都会发送一封订阅告知邮件，请知晓。");
            sendEmailUtil.send();
        }
    }
}
