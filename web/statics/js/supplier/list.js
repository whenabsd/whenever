var supplierObj;

//供货商管理页面上点击删除按钮弹出删除框(supplierList.jsp)
function deleteSupplier(obj){
	$.ajax({
		type:"POST",
		url:path+"/sys/supplier?method=delSupplier&supId="+obj.attr("supId"),
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();// 在页面当中删除指定的行记录  但是后续的总记录数没有改变
				// 增删改操作之后，一般情况下，需要再次访问控制器的查询全部的方法，通过控制器指定方法向页面渲染数据
				//跳转到指定控制器的目标方法  list
				window.location.href=path+"/sys/supplier?method=findAllSuppliers";
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除供货商【"+obj.attr("supName")+"】失败");
				changeDLGContent("对不起，删除供货商【"+obj.attr("supName")+"】失败");
			}else{
				//alert("对不起，该供货商【"+obj.attr("supName")+"】下有【"+data.delResult+"】条订单，不能删除");
				changeDLGContent("对不起，该供货商【"+obj.attr("supName")+"】下有【"+data.delResult+"】条订单，不能删除");
			}
		},
		error:function(data){
			//alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeProv').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeProv').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}
$(function(){
	$(".viewSupplier").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/sys/supplier?method=viewSupplier&supId="+ obj.attr("supId");
	});
	
	$(".modifySupplier").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/sys/supplier?method=toUpdate&supId="+ obj.attr("supId");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteSupplier(supplierObj);
	});

	$(".deleteSupplier").on("click",function(){
		supplierObj = $(this);
		changeDLGContent("确定删除供货商【"+supplierObj.attr("supName")+"】吗？");
		openYesOrNoDLG();
	});
	
/*	$(".deleteSupplier").on("click",function(){
		var obj = $(this);
		if(confirm("确定删除供货商【"+obj.attr("supName")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/jsp/Supplier.do",
				data:{method:"delSupplier",proid:obj.attr("proid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除供货商【"+obj.attr("supName")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，供货商【"+obj.attr("supName")+"】不存在");
					}else{
						alert("对不起，该供货商【"+obj.attr("supName")+"】下有【"+data.delResult+"】条订单，不能删除");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});*/
});