var code = null;
var roleName = null;
var saveBtn = null;
var backBtn = null;

$(function(){
    code = $("#code");
	roleName = $("#roleName");
	saveBtn = $("#save");
	backBtn = $("#back");
	
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    code.next().html("*");
	roleName.next().html("*");


    code.bind("blur",function(){

		if(code.val() === null || code.val() === ''){
            //提示语句
			validateTip(code.next(), {"color": "red"}, imgNo + " 该角色编码不能为空!", false);
            return;
		}

		//ajax后台验证--roleCode是否已存在
		$.ajax({
			type:"POST",//请求类型
			url:path+"/sys/role?method=codeExist",//请求的url
			data:{code:code.val()},//请求参数
			dataType:"json",//ajax接口（请求url）返回的数据类型
			success:function(data){//data：返回数据（json对象）
				if(data.exist == 1) {//账号已存在，错误提示
                    validateTip(code.next(), {"color": "red"}, imgNo + " 该角色编码已存在", false);
                }else if(data.exist == 0){//账号可用，正确提示
					validateTip(code.next(),{"color":"green"},imgYes+" 该角色编码可以使用",true);
				}else if(data.exist == -1){//错误
                    validateTip(code.next(),{"color":"green"},imgNo + data.msg  ,true);
                }
			},
			error:function(data){//当访问时候，404，500 等非200的错误状态码
				validateTip(code.next(),{"color":"red"},imgNo+" 您访问的页面不存在",false);
			}
		});
	}).bind("focus",function(){
		//显示友情提示
		validateTip(code.next(),{"color":"#666666"},"* 角色编码长度必须是大于1小于10的字符",false);
	});
	
	roleName.bind("focus",function(){
		validateTip(roleName.next(),{"color":"#666666"},"* 角色名称长度必须是大于1小于10的字符",false);
	}).bind("blur",function(){
		if(roleName.val() != null && roleName.val().length > 1
				&& roleName.val().length < 10){
			validateTip(roleName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(roleName.next(),{"color":"red"},imgNo+" 角色名称输入的不符合规范，请重新输入",false);
		}
		
	});
	
	
	saveBtn.bind("click",function(){
		if(code.attr("validateStatus") != "true"){
            code.blur();
		}else if(roleName.attr("validateStatus") != "true"){
			roleName.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#roleForm").submit();
			}
		}
	});
	
	backBtn.on("click",function(){
		if(referer != undefined 
			&& null != referer 
			&& "" != referer
			&& "null" != referer
			&& referer.length > 4){
		 window.location.href = referer;
		}else{
			history.back(-1);
		}
	});
});