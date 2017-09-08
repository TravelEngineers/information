information:
    task:
       struts2: #漏洞库
          checkurl: https://cwiki.apache.org/confluence/display/WW/Security+Bulletins #检测地址
          details: https://cwiki.apache.org
          checkcycle: 60000 #检测间隔，单位为ms
    config:
        mail:
          account: *****   #发送人账号
          pass: *****      #发送人密码
          host: smtp.exmail.qq.com
          port: 465
          protocol: smtp  #协议
          toUser: 邮箱1,邮箱2