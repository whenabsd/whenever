<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Insert title here</title>
   <!-- 引入JS文件  jQuery文件 -->
   <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-1.8.3.min.js"></script>
   <script type="text/javascript">

      $(function (){
         $("#account").bind("blur",function (){
            $.ajax({
               type: "POST",
               url:"/cvs/sys/user",
               data:{method:"userExist",account:$("#account").val()},
               dataType:"json",
               success:function (data) {
                  if(data.exist ==0){

                     $("#nameSpan").html('<span style="color:green;font-weight: bold">账号可用!</span>')
                  }else if(data.exist == 1){

                     $("#nameSpan").html('<span style="color:red;font-weight: bold">账号已存在!</span>')
                  }
               }
            });
         });
      });

   </script>

</head>
<body>
<form action="${pageContext.request.contextPath}/sys/user?method=login" method="post">
   账号:<input type="text" name="account" id="account" value="" />
   <span id="nameSpan"></span>
   <br/>
   密码:<input type="password" name="password" id="password" value=""/>
   <br/>
   <input type="submit" value="登录" />
   <input type="reset" value="重置" />
</form>


</body>
</html>