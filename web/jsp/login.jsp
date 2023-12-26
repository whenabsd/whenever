<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 724便利店管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/style.css" />
    <script type="text/javascript">
        /* if(top.location!=self.location){
              top.location=self.location;
         } */
    </script>
</head>
<body class="login_bg">
<section class="loginBox">
    <header class="loginHeader">
        <h1>724便利店管理系统</h1>
    </header>
    <section class="loginCont">
        <form class="loginForm" action="${pageContext.request.contextPath }/sys/user?method=login"  name="actionForm" id="actionForm"  method="post" >
            <div class="inputbox">
                <label for="account">账号：</label>
                <input type="text" class="input-text" id="account" name="account" value="waixia"  placeholder="请输入账号" required/>
            </div>
            <div class="inputbox">
                <label for="password">密码：</label>
                <input type="password" id="password" name="password" value="111111"  placeholder="请输入密码" required/>
            </div>
            <div class="info">${loginError }</div>
            <div class="subBtn">

                <input type="submit" value="登录"/>
                <input type="reset" value="重置"/>
            </div>
        </form>
    </section>
</section>
</body>
</html>
