package com.xjs.cvs.web.controller;

import com.google.gson.Gson;
import com.xjs.cvs.pojo.Role;
import com.xjs.cvs.pojo.User;
import com.xjs.cvs.service.RoleService;
import com.xjs.cvs.service.impl.RoleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "RoleServlet",value = "/sys/role")
public class RoleServlet extends HttpServlet {

    private RoleService roleService;


    @Override
    public void init() throws ServletException {
        if (roleService == null){
            roleService = new RoleServiceImpl();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置字符集
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //2.从前端页面或者请求路径当中，获取参数，目的：执行执行的方法
        String method = req.getParameter("method");
        if("findAllRoles".equals(method)){
            //执行查询所有角色信息的操作
            findAllRoles(req,resp);//codeExist
        }else if("codeExist".equals(method)){
            //执行判断是否有重复的角色的操作
            codeExist(req,resp);//codeExist
        }else if("toAdd".equals(method)){
            //执行添加角色的操作
            toAdd(req,resp);//codeExist
        }else if("toUpdate".equals(method)){
            //执行添加角色的操作
            toUpdate(req,resp);//codeExist
        }else if("updateRole".equals(method)){
            //执行添加角色的操作
            updateRole(req,resp);//codeExist
        }else if("delRole".equals(method)){
            //执行添加角色的操作
            delRole(req,resp);//codeExist
        }else if("simpleList".equals(method)){
            //执行添加角色的操作
            simpleList(req,resp);//codeExist
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }


    /**
     * ajax形式 获取所有角色列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void simpleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roleList = roleService.queryAllRoles();
    /*HttpSession session = request.getSession();
    session.setAttribute("roleList",roleList);*/
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(roleList));
    }

    /**
     * 删除指定编号的角色信息
     * 从页面获取要删除的角色id,然后向下传递 (业务层  持久层)
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从 list.js文件  ajax技术  url访问路径
        String roleid = req.getParameter("roleid");
        Integer id = Integer.parseInt(roleid);

        //调用业务层
        int i = roleService.removeRoleById(id);
        HashMap<String,String> map = new HashMap<>();
        if (i>0){
            //删除成功
            map.put("delResult","true");
        }else{
            //删除失败
            map.put("delResult","false");
        }
        //把map集合，转换成json字符串
        Gson gson = new Gson();
        PrintWriter writer = resp.getWriter();
        writer.println(gson.toJson(map));
        writer.close();
    }

    /**
     * 修改指定角色信息    先从update.jsp页面通过form表单的形式，把修改好的
     * 信息，封装一个Role对象，然后再向下传递 （service层  mapper层）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.从前端页面 update.jsp  封装一个Role对象 (已经修改好的Role对象)
        String id = req.getParameter("id");
        Integer roleId = Integer.parseInt(id);

        String code = req.getParameter("code");
        String roleName = req.getParameter("roleName");

        //2.封装对象
        Role role = new Role();
        role.setId(roleId);
        role.setCode(code);
        role.setRoleName(roleName);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        role.setUpdateUserId(user.getId()); // 谁登录谁是修改者

        role.setUpdateDateTime(new Date());//修改时间

        //3.向下传递  service
        int i = roleService.modifyRole(role);

        //4.页面跳转
        if (i>0){
            //修改成功   跳转到list.jsp页面  需要借助servlet完成，不应该直接跳转list.jsp
            resp.sendRedirect(req.getContextPath()+"/sys/role?method=findAllRoles");
        }else{
            //需改失败  跳转到update.jsp页面  并且给出提示信息
            req.setAttribute("updateError","修改失败!");
            req.getRequestDispatcher("/jsp/sysRole/update.jsp").forward(req,resp);

        }
    }

    /**
     * 为修改操作之前作准备  -- 吧页面当中指定修改的一条记录，转换成一个Role对象
     * 然后渲染显示到update.jsp页面
     * @param req
     * @param resp
     */
    protected void toUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleid = req.getParameter("roleid");
        Integer id = Integer.parseInt(roleid); //把字符串类型的id转换成Integer类型的id

        //根据id查询指定的Role对象
        Role role = roleService.queryRoleById(id);
        //把查询得出的role对象，渲染到update.jsp页面
        HttpSession session = req.getSession();
        session.setAttribute("sysRole",role);
        //跳转页面
        resp.sendRedirect(req.getContextPath()+"/jsp/sysRole/update.jsp");
    }

    /**
     * 通过add.jsp页面获取角色的各个信息，封装成一个Role对象，传递给下面的各个阶段（业务层，持久层）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void toAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1从add.jsp页面获取添加角色的数据。
        String code = req.getParameter("code");
        String roleName = req.getParameter("roleName");
        //2创建角色从用户编号，以及创建时间。
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        //3封装一个role对象
        Role role = new Role();
        role.setCode(code);
        role.setRoleName(roleName);
        role.setCreateUserId(user.getId());
        role.setCreateDateTime(new Date());
        //4调用service到业务层
        int i = roleService.insertRole(role);
        //5 应该进行跳转页面
        if(i>0){
            //添加成功 不要直接跳转list.jsp页面。而是通过Servlet控制器，自动跳转
            resp.sendRedirect(req.getContextPath()+"/sys/role?method=findAllRoles");
        }else{
            req.setAttribute("addRoleError","添加角色失败");
            req.getRequestDispatcher("/jsp/sysRole/add.jsp").forward(req,resp);
        }



    }

    /**
     * 判断用户输入角色编码是否存在 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void codeExist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        Role role = roleService.queryRoleByCode(code);
        HashMap<String, Integer> map = new HashMap<>();
        if (role != null){
            map.put("exist",1);
        }else{
            map.put("exist",0);
        }


        Gson gson = new Gson();
        String s = gson.toJson(map);
        PrintWriter writer = resp.getWriter();
        writer.print(s);
        writer.close();


    }

    /**
     *  查询数据库表 t_sys_role中所有的角色信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllRoles(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //调用业务层方法
        List<Role> roleList = roleService.queryAllRoles();
        //把获取的集合，渲染到页面 -- list.jsp页面
        HttpSession session = req.getSession();
        session.setAttribute("roleList",roleList);
        //跳转页面
        resp.sendRedirect(req.getContextPath()+"/jsp/sysRole/list.jsp");
    }

    //
}