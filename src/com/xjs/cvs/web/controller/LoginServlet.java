package com.xjs.cvs.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.xjs.cvs.pojo.Role;
import com.xjs.cvs.pojo.User;
import com.xjs.cvs.service.RoleService;
import com.xjs.cvs.service.UserService;
import com.xjs.cvs.service.impl.RoleServiceImpl;
import com.xjs.cvs.service.impl.UserServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "LoginServlet",value = "/sys/user")
public class LoginServlet extends HttpServlet {
    //定义业务层接口对象，目的：调用业务层方法，再调用持久层方法，再查询数据，直到完成查询操作
    private UserService userService;
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        if(roleService == null){
            roleService = new RoleServiceImpl();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //接收login.jsp页面的数据
        String method = req.getParameter("method");
        if ("login".equals(method)) {
            //执行登录的操作
            login(req, resp);//
        } else if ("logout".equals(method)) {
            //执行退出的操作
            logout(req, resp);//logout
        } else if ("userExist".equals(method)) {
            //判断是否有指定账户的操作
            userExist(req, resp);//logout
        } else if ("checkOldPwd".equals(method)) {
            //检验旧密码是否正确
            checkOldPwd(req, resp);//
        }else if ("savePassword".equals(method)) {
            //检验旧密码是否正确
            savePassword(req, resp);//savePassword
        }else if ("findAllUsers".equals(method)){
            findAllUsers(req, resp);
        }else if ("addUser".equals(method)){
            addUser(req, resp);
        }else if ("viewUser".equals(method)){
            viewUser(req, resp);
        }else if ("delUser".equals(method)){
            delUser(req, resp);
        }else if ("toUpdate".equals(method)){
            toUpdate(req, resp);
        }else if ("updateUser".equals(method)){
            updateUser(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Integer id = Integer.parseInt(request.getParameter("id"));
        String account = request.getParameter("account");
        String realName = request.getParameter("realName");
        Integer sex = Integer.parseInt(request.getParameter("sex"));
        Date birthday = null;
        try {
            birthday = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        Integer roleId = Integer.parseInt(request.getParameter("roleId"));

        //封装修改之后的user对象
        User user = new User();
        user.setId(id);
        user.setAccount(account);
        user.setRealName(realName);
        user.setSex(sex);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRoleId(roleId);
        User loginUser = (User) session.getAttribute("user");
        if (loginUser!=null){
            user.setUpdateUserId(loginUser.getId());
            user.setUpdateDateTime(new Date());
        }

        int i = userService.modifyUser(user);
        if (i>0){
            response.sendRedirect(request.getContextPath()+"/sys/user?method=findAllUsers");
        }else{
            request.setAttribute("updateUserError","修改用户失败!");
            request.getRequestDispatcher("/jsp/sysUser/update.jsp").forward(request,response);
        }
    }

    /**
     * 预修改操作 根据id获取用户信息，并传递给update.jsp页面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("userid"));
        User user = userService.queryUserById(id);

        request.setAttribute("sysUser",user);
        request.getRequestDispatcher("/jsp/sysUser/update.jsp").forward(request,response);
    }

    /**
     * 删除指定用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("userid"));
        int i = userService.removeUserById(id);
        HashMap<String,String> delHashMap = new HashMap<>();
        if (i>0){
            delHashMap.put("delResult","true");
        }else{
            delHashMap.put("delResult","false");
        }
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(delHashMap));
        writer.close();

    }

    /**
     * 根据id查看用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void viewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        Integer id = Integer.parseInt(userid);

        User user = userService.queryUserById(id);
        HttpSession session = request.getSession();
        session.setAttribute("sysUser",user);

        response.sendRedirect(request.getContextPath()+"/jsp/sysUser/view.jsp");
    }

    /**
     * 添加用户信息  并且文件上传
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
        fileUpload.setHeaderEncoding("UTF-8");
        fileUpload.setFileSizeMax(500*1024);

        List<FileItem> list = null;
        try {
            list = fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            //如果超出限制，不仅仅再控制台输出错误信息，而且也要再页面进行显示！
            request.setAttribute("error","上传的文件大小不能超过500KB");
            //注意：路径不能带项目名，因为请求转发只能在当前项目进行跳转
            request.getRequestDispatcher("/jsp/sysUser/add.jsp").forward(request,response);
            return;
        }
        //声明user对象各个属性
        String account = null;
        String realName = null;
        String password = null;
        Integer sex = null;
        Date birthday = null;
        String phone = null;
        String address = null;
        Integer roleId = null;
        String idPic = null;
        String workPic = null;

        //指定上传文件的绝对路径
        String absolutePath = request.getSession().getServletContext().getRealPath("statics"+ File.separator+"uploadfiles");
        //指定上传目录，如果不存在，则创建
        File dir = new File(absolutePath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //遍历循环
        for (int i = 0; i < list.size(); i++) {
            //每一个表单项
            FileItem item = list.get(i);
            //每一个表单项 name的属性值
            String fieldName = item.getFieldName();
            //增加判断 如果是普通表单项 例如: account password等，则为上面的变量赋值
            if (item.isFormField()){
                if ("account".equals(fieldName)){
                    account = item.getString("UTF-8");
                }
                if("realName".equals(fieldName)){
                    realName = item.getString("UTF-8");
                }
                if("password".equals(fieldName)){
                    password = item.getString("UTF-8");
                }
                if("sex".equals(fieldName)){
                    sex = Integer.parseInt(item.getString("UTF-8"));
                }

                if("birthday".equals(fieldName)){
                    try {
                        birthday = new SimpleDateFormat("yyyy-MM-dd").parse(item.getString("UTF-8"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if("phone".equals(fieldName)){
                    phone = item.getString("UTF-8");
                }
                if("address".equals(fieldName)){
                    address = item.getString("UTF-8");
                }
                if("roleId".equals(fieldName)){
                    roleId = Integer.parseInt(item.getString("UTF-8"));
                }
            }else{
                //获取文件名称
                String fileName = item.getName();
                System.out.println("------"+fileName+"-------");
                //限制文件上传类型
                if (fileName.indexOf(".jpg")<0 && fileName.indexOf(".jpeg")<0 && fileName.indexOf(".png")<0 && fileName.indexOf(".pneg")<0){
                    request.setAttribute("error","只能上传jpg,jpeg或者png,pneg结尾的文件");
                    //注意：路径不能带项目名，因为请求转发只能在当前项目进行跳转
                    request.getRequestDispatcher("/jsp/sysUser/add.jsp").forward(request,response);
                    return;
                }

                //使用UUID 重新为上传的文件重命名
                String uuid =  UUID.randomUUID().toString();
                //获取文件后缀名
                int lastIndex = fileName.lastIndexOf(".");
                System.out.println("--------"+lastIndex+"--------");
                String suffixName = fileName.substring(lastIndex);
                System.out.println("========================");
                System.out.println("fileName="+fileName);
                System.out.println("suffixName="+suffixName);
                System.out.println("========================");
                //新文件名称
                String newFileName = uuid+suffixName;
                //如果是证件照
                if("idPic".equals(fieldName)){
                    //开始上传
                    File file = new File(dir,newFileName);
                    try {
                        item.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    idPic = request.getContextPath()+"/statics/uploadfiles/"+newFileName;
                }
                //如果是工作照
                if ("workPic".equals(fieldName)){
                    //开始上传
                    File file = new File(dir,newFileName);
                    try {
                        item.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    workPic = request.getContextPath()+"/statics/uploadfiles/"+newFileName;
                }
            }
        }
        //封装User对象
        User user = new User();
        user.setAccount(account);
        user.setRealName(realName);
        user.setPassword(password);
        user.setSex(sex);
        user.setAge(18);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRoleId(roleId);
        user.setIdPicPath(idPic);
        user.setWorkPicPath(workPic);
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        if (loginUser!=null){
            user.setCreateUserId(loginUser.getId());
            user.setCreateDateTime(new Date());
        }
        //调用业务层方法
        int i = userService.addUser(user);
        if (i>0){
            response.sendRedirect(request.getContextPath()+"/sys/user?method=findAllUsers");
        }else{
            request.setAttribute("error","添加用户失败!");
            request.getRequestDispatcher("/jsp/sysUser/add.jsp").forward(request,response);
        }
    }

    /**
     * 带条件查询所有用户并分页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryRealName = request.getParameter("queryRealName");
        String roleId = request.getParameter("queryRoleId");
        if (roleId==null || "".equals(roleId)){
            roleId = "0";
        }
        Integer queryRoleId = Integer.parseInt(roleId);
        String pageNo = request.getParameter("pageIndex");
        if (pageNo==null || "".equals(pageNo)){
            pageNo = "1";
        }
        Integer pageIndex = Integer.parseInt(pageNo);


        HttpSession session = request.getSession();

        //查询所有的角色列表
        List<Role> roleList = roleService.queryAllRoles();
        //查询满足条件的用户
        PageHelper.startPage(pageIndex,5);
        List<User> userList = userService.queryAllUsers(queryRealName, queryRoleId);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        //获取分页相关信息
        long totalCount = pageInfo.getTotal();
        int totalPageCount = pageInfo.getPages();
        int currentPageNo = pageInfo.getPageNum();


        session.setAttribute("queryRealName",queryRealName);
        session.setAttribute("queryRoleId",queryRoleId);
        session.setAttribute("pageInfo",pageInfo);
        session.setAttribute("totalCount",totalCount);
        session.setAttribute("totalPageCount",totalPageCount);
        session.setAttribute("currentPageNo",currentPageNo);
        session.setAttribute("roleList",roleList);
        session.setAttribute("userList",userList);

        response.sendRedirect(request.getContextPath()+"/jsp/sysUser/list.jsp");

    }

    /**
     * 修改密码操作   从前端页面 updatePassword.jsp  获取用户的编号，以及新密码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void savePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.获取用户编号   新密码
        String id = req.getParameter("id");
        Integer userId = Integer.parseInt(id);

        String newPassword = req.getParameter("newPassword");

        //2.调用业务层
        int i = userService.modifyUserPwd(userId, newPassword);

        //3.页面跳转
        if (i>0){
            //修改成功   跳转到登录页面
            resp.sendRedirect(req.getContextPath()+"/jsp/login.jsp");
        }else{
            //修改失败   跳转到当前修改密码的页面 updatePassword.jsp
            req.setAttribute("updatePwdError","修改密码失败!");
            req.getRequestDispatcher("/jsp/sysUser/updatePassword.jsp").forward(req,resp);
        }


    }

    /**
     * 判断检验用户输入的旧密码是否正确
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkOldPwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取用户输入的旧密码
        String oldPassword = req.getParameter("oldPassword");

        //2.向业务层传递参数 -- 账户  密码
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String account = user.getAccount();

        //3.调用业务层 -- login的方法 -- 输入账号和密码
        // 如果传入的账号密码正确，返回的user对象不为空，说明旧密码正确，反之，旧密码错误
        User user1 = userService.login(account, oldPassword);

        //4.创建HashMap集合  存储是否正确输入旧密码  把该集合转换成json数据
        HashMap<String,String> map = new HashMap<>();
        if (user1!=null){
            //旧密码正确
            map.put("result","true");
        }else{
            //旧密码错误
            map.put("result","false");
        }

        Gson gson = new Gson();
        String data = gson.toJson(map);
        PrintWriter writer = resp.getWriter();
        writer.println(data);
        writer.close();
    }

    /**
     * 执行登录的操作   获取账号和密码
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        String password = req.getParameter("password");

        User user = userService.login(account, password);
        if (user != null) {
            //完善  在登录成功之后，显示真正的用户名
            // 要求; 可以使用session对象
            HttpSession session = req.getSession();
            session.setAttribute("user", user);


            //输入正确的账号和密码，则跳转到frame.jsp页面
            resp.sendRedirect(req.getContextPath() + "/jsp/frame.jsp");
        } else {
            //输入的账号密码不正确，应该停留在login.jsp页面，并给出相关提示
            req.setAttribute("loginError", "账号密码错误!");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }

    }

    /**
     * 退出系统的方法    效果: 点击超链接 "登出"  跳转到登录页面
     * <p>
     * 涉及到一个session   会话
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //结束会话
        HttpSession session = req.getSession();
        session.invalidate();

        //跳转页面 -- 登录页面
        resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");


    }

    /**
     * 该方法的作用：判断用户输入的账户是否存在
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void userExist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //通过请求对象获取ajax传递的account账户
        String account = req.getParameter("account");

        User user = userService.queryUser(account);
        HashMap<String, Integer> map = new HashMap<>();
        if (user != null) {
            map.put("exist", 1);
        } else {
            map.put("exist", 0);
        }

        Gson gson = new Gson();
        PrintWriter writer = resp.getWriter();
        String data = gson.toJson(map);
        writer.println(data);
        writer.close();

    }
}