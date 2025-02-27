<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="email code">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
<!--邮箱验证码模板-->
    <body>
        <table cellpadding="0" align="center"
               style="width: 800px;height: 100%; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;">
            <tbody>
            <tr>
                <td style="word-break:break-all">
                    <div style="background-color:#ECECEC; padding: 35px;">
                        <div style="padding:25px 35px 40px; background-color:#fff;opacity:0.8;">
                            <h2 style="margin: 5px 0px; ">
                                <span style="line-height: 20px;  color: #333333; ">
                                    <span style="line-height: 22px;  font-size: medium; ">
                                        尊敬的用户：
                                    </span>
                                </span>
                            </h2>
                            <p>您好！感谢您使用<span
                                        style="color: rgb(0, 120, 191)">HohaiPan</span>，您的账号正在进行邮箱验证，验证码为：<span
                                        style="color: #ff8c00; ">{0}</span>，有效期5分钟，请尽快填写验证码完成验证！</p>
                            <br>
                            <div style="width:100%;margin:0 auto;">
                                <div style="padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;">
                                    <p>HohaiPan</p>
                                    <p>通过QQ联系我：3296299414</p>                            <br>
                                    <p>此为系统邮件，请勿回复<br> Please do not reply to this system email </p></div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </body>
</html>

