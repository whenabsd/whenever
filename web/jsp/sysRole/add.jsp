<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>角色管理页面 >> 角色添加页面</span>
        </div>
        <div class="supplierAdd">
            <form id="roleForm" name="roleForm" method="post" action="${pageContext.request.contextPath }/sys/role?method=toAdd">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div>
                    <label for="code">角色编码：</label>
                    <input type="text" name="code" id="code" value="">
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="roleName">角色名称：</label>
                    <input type="text" name="roleName" id="roleName" value=""> 
					<font color="red"></font>
                </div>
                
                <div class="supplierAddBtn">
                    <input type="button" name="save" id="save" value="保存" >
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </form>
        </div>
        <span style="color: red;font-size: 16px;font-weight: bold;">
            ${addRoleError}
        </span>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/sysRole/add.js"></script>
