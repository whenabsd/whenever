<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<div class="right">
            <div class="location">
                <strong>当前位置:</strong>
                <span>密码修改页面</span>
            </div>
            <div class="supplierAdd">
                <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/sys/user?method=savePassword">
                    <input type="hidden" name="id" value="${user.id}">
                    <!--div的class 为error是验证错误，ok是验证成功-->
                    <div class="info">${message}</div>
                    <div class="">
                        <label for="oldPassword">旧密码：</label>
                        <input type="password" name="oldPassword" id="oldpassword" value="">
						<font color="red"></font>
                    </div>
                    <div>
                        <label for="newPassword">新密码：</label>
                        <input type="password" name="newPassword" id="newpassword" value="">
						<font color="red"></font>
                    </div>
                    <div>
                        <label for="reNewPassword">确认新密码：</label>
                        <input type="password" name="reNewPassword" id="rnewpassword" value="">
						<font color="red"></font>
                    </div>
                    <div class="supplierAddBtn">
                        <!--<a href="#">保存</a>-->
                        <input type="button" name="save" id="save" value="保存" class="input-button">
                    </div>
                </form>
            </div>
        </div>
        <span style="color: red;font-size: 16px;font-weight: bold;">
            ${updatePwdError}
        </span>

    </section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/sysUser/updatePassword.js"></script>