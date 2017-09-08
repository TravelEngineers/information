package com.zgs.information.mail;

import com.zgs.information.msg.BugInfo;
import com.zgs.information.utils.YamlUtils;

import java.util.List;

/**
 * @author Shmily
 * @email shmily_zgs@163.com
 * @date 2017/9/7 16:51
 */
public class TaskSendEmail {
    public static void sendMsg(List<BugInfo> todayBug){
        String toUser= YamlUtils.getValue("information.config.mail.toUser");
        StringBuffer content=new StringBuffer();
        content.append("\n" +
                "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-family:'微软雅黑',Helvetica,Arial,sans-serif;font-size:14px \" width=\"100%\">\n" +
                "     <tbody>\n" +
                "                <tr>\n" +
                "                    <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:14px;\">\n" +
                "                    <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >\n" +
                "                            <tbody>\n" +
                "                                <tr><td>\n" +
                "                                    漏洞名称\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t <td>\n" +
                "                                    漏洞详情\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>\n" +
                "                                    漏洞简介\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>\n" +
                "                                    漏洞类型\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t<td>\n" +
                "                                    漏洞来源\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "                               </tr>");
        for(BugInfo bugInfo:todayBug){
            content.append("<tr>");
            content.append("<td>"+bugInfo.getName()+"</td>");
            content.append("<td>"+bugInfo.getUrl()+"</td>");
            content.append("<td>"+bugInfo.getTitle()+"</td>");
            content.append("<td>"+bugInfo.getType()+"</td>");
            content.append("<td>"+bugInfo.getPlat()+"</td>");
            content.append("</tr>");
        }
        content.append("</tbody>\n" +
                "                    </table>                                                          \n" +
                "                   </td>\n" +
                "              </tr>\n" +
                "                \n" +
                "   </tbody>\n" +
                "</table>          ");
        for(String user:toUser.split(",")){
            SendEmailUtil sendEmailUtil=new SendEmailUtil(user,"最新互联网漏洞情报",content.toString());
            sendEmailUtil.send();
        }
    }
}
