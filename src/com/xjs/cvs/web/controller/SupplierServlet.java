package com.xjs.cvs.web.controller;
/**
 * @auther SaManDaShi
 * @date 2023/11/30 8:42
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
;
import com.xjs.cvs.pojo.Supplier;
import com.xjs.cvs.pojo.User;
import com.xjs.cvs.service.SupplierService;
import com.xjs.cvs.service.impl.SupplierServiceImpl;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "SupplierServlet", value = "/sys/supplier")
public class SupplierServlet extends HttpServlet {
    private SupplierService supplierService;

    @Override
    public void init() throws ServletException {
        if (supplierService == null){
            supplierService = new SupplierServiceImpl();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String method = request.getParameter("method");
        if("findAllSuppliers".equals(method)){
            findAllSuppliers(request,response);
        }else if("viewSupplier".equals(method)){
            viewSupplier(request,response);
        }else if("toUpdate".equals(method)){
            toUpdate(request,response);
        }else if("updateSupplier".equals(method)){
            updateSupplier(request,response);
        }else if("delSupplier".equals(method)){
            delSupplier(request,response);
        }else if("addSupplier".equals(method)){
            addSupplier(request,response);
        }else if("simpleList".equals(method)){
            simpleList(request,response);
        }

        //
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    /**
     * ajax形式获取所有供货商
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void simpleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Supplier> supplierList = supplierService.queryAllSuppliers(null, null);
        PrintWriter writer = response.getWriter();
        Gson gson =new Gson();
        writer.println(gson.toJson(supplierList));
        writer.close();
    }

    /**
     * 添加供货商信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(itemFactory);
        fileUpload.setHeaderEncoding("UTF-8");
        fileUpload.setFileSizeMax(500*1024);

        List<FileItem> list = null;
        try {
            list =  fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            //如果超出限制，不仅仅再控制台输出错误信息，而且也要再页面进行显示！
            request.setAttribute("error","上传的文件大小不能超过500KB");
            //注意：路径不能带项目名，因为请求转发只能在当前项目进行跳转
            request.getRequestDispatcher("/jsp/supplier/add.jsp").forward(request,response);
            return;
        }


        //声明封装supplier各个属性
        String supCode = null;
        String supName = null;
        String supContact = null;
        String supPhone = null;
        String supAddress = null;
        String supFax = null;
        String supDesc = null;
        String companyLicPic = null;
        String orgCodePic = null;

        //设置文件上传的目录
        String absolutePath = request.getSession().getServletContext().getRealPath("statics"+ File.separator+"uploadfiles");
        File dir = new File(absolutePath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //设置保存到数据库的文件路径
        String path = request.getContextPath()+"/statics/uploadfiles/";
        for (int i = 0; i <list.size() ; i++) {
            FileItem fileItem = list.get(i);
            //获取文件项的name名称 例如 供货商编码： name="supCode"
            String itemName = fileItem.getFieldName();
            //判断form表单项：普通表单项 还是文件表单项
            if (fileItem.isFormField()){
                //针对普通表单项获取数据
                if ("supCode".equals(itemName)){
                    supCode = fileItem.getString("UTF-8");
                }
                if ("supName".equals(itemName)){
                    supName = fileItem.getString("UTF-8");
                }
                if ("supContact".equals(itemName)){
                    supContact = fileItem.getString("UTF-8");
                }
                if ("supPhone".equals(itemName)){
                    supPhone = fileItem.getString("UTF-8");
                }
                if ("supAddress".equals(itemName)){
                    supAddress = fileItem.getString("UTF-8");
                }
                if ("supFax".equals(itemName)){
                    supFax = fileItem.getString("UTF-8");
                }
                if ("supDesc".equals(itemName)){
                    supDesc = fileItem.getString("UTF-8");
                }
            }else{
                //获取文件名称
                String fileName = fileItem.getName();
                //获取文件的后缀名 限制上传文件的类型 只能是: jpg jpeg png pneg类型图片
                if (fileName.indexOf(".jpg")<0
                        && fileName.indexOf(".jpeg")<0
                        && fileName.indexOf(".png")<0
                        && fileName.indexOf(".pneg")<0){
                    request.setAttribute("error","只能上传jpg,jpeg或者png,pneg结尾的文件");
                    //注意：路径不能带项目名，因为请求转发只能在当前项目进行跳转
                    request.getRequestDispatcher("/jsp/supplier/add.jsp").forward(request,response);
                    return;
                }
                //使用uuid 重命名文件 防止文件名称重复
                UUID uuid = UUID.randomUUID();
                //获取文件名的后缀
                int lastIndex = fileName.lastIndexOf(".");
                String suffixName = fileName.substring(lastIndex);
                //组合新文件的名称
                String newFileName = uuid.toString()+suffixName;
                //针对文件表单项获取数据，并上传
                if("companyLicPic".equals(itemName)){
                    File file = new File(dir,newFileName);
                    try {
                        fileItem.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //保存上传数据库表的文件路径
                    companyLicPic = path+newFileName;
                }
                if("orgCodePic".equals(itemName)){
                    File file = new File(dir,newFileName);
                    try {
                        fileItem.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //保存上传数据库表的文件路径
                    orgCodePic = path+newFileName;
                }

            }
        }
        //封装Supplier对象
        Supplier supplier = new Supplier();
        supplier.setSupCode(supCode);
        supplier.setSupName(supName);
        supplier.setSupContact(supContact);
        supplier.setSupPhone(supPhone);
        supplier.setSupAddress(supAddress);
        supplier.setSupFax(supFax);
        supplier.setSupDesc(supDesc);
        supplier.setCompanyLicPicPath(companyLicPic);
        supplier.setOrgCodePicPath(orgCodePic);
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        supplier.setCreatedUserId(loginUser.getId());
        supplier.setCreatedTime(new Date());

        //调用业务层方法
        int i = supplierService.addSupplier(supplier);
        if(i>0){
            response.sendRedirect(request.getContextPath()+"/sys/supplier?method=findAllSuppliers");
        }else{
            request.setAttribute("addSupError","添加供货商信息失败!");
            request.getRequestDispatcher("/jsp/supplier/add.jsp").forward(request,response);
        }
    }

    /**
     * 删除供货商信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String supId = request.getParameter("supId");
        Integer id = Integer.parseInt(supId);

        int i = supplierService.removeSupById(id);
        HashMap<String,String> hashMap = new HashMap<>();
        if (i>0){
            hashMap.put("delResult","true");
        }else{
            hashMap.put("delResult","false");
        }
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();
        writer.println(gson.toJson(hashMap));
        writer.close();
    }

    /**
     * 修改供货商信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从页面获取数据
        String id = request.getParameter("id");
        Integer supId = Integer.parseInt(id);
        String supCode = request.getParameter("supCode");
        String supName = request.getParameter("supName");
        String supPhone = request.getParameter("supPhone");
        String supContact = request.getParameter("supContact");
        String supAddress = request.getParameter("supAddress");
        String supDesc = request.getParameter("supDesc");

        //封装Supplier对象
        Supplier supplier = new Supplier();
        supplier.setId(supId);
        supplier.setSupCode(supCode);
        supplier.setSupName(supName);
        supplier.setSupPhone(supPhone);
        supplier.setSupContact(supContact);
        supplier.setSupAddress(supAddress);
        supplier.setSupDesc(supDesc);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        supplier.setUpdatedUserId(user.getId());
        supplier.setUpdatedTime(new Date());
        //调用业务层方法
        int i = supplierService.modifySupplier(supplier);

        //响应页面
        if (i>0){
            response.sendRedirect(request.getContextPath()+"/sys/supplier?method=findAllSuppliers");
        }else {
            request.setAttribute("updateSupError","修改供货商信息失败!");
            request.getRequestDispatcher("/jsp/supplier/update.jsp").forward(request,response);
        }

    }

    /**
     * 预修改操作  根据页面传递的供货商编号查询其对象，传递给update.jsp页面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String supId = request.getParameter("supId");
        Integer id = Integer.parseInt(supId);

        Supplier supplier =  supplierService.querySupById(id);
        HttpSession session = request.getSession();
        if (supplier!=null){
            session.setAttribute("supplier",supplier);
            response.sendRedirect(request.getContextPath()+"/jsp/supplier/update.jsp");
        }else{
            request.setAttribute("viewSupError","获取供货商信息失败!");
            request.getRequestDispatcher("/jsp/supplier/list.jsp").forward(request,response);
        }
    }

    /**
     * 查看指定编号的供货商
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void viewSupplier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String supId = request.getParameter("supId");
        Integer id = Integer.parseInt(supId);

        Supplier supplier = supplierService.querySupById(id);
        HttpSession session = request.getSession();
        if (supplier!=null){
            session.setAttribute("supplier",supplier);
            response.sendRedirect(request.getContextPath()+"/jsp/supplier/view.jsp");
        }else{
            request.setAttribute("viewSupError","查看供货商信息失败!");
            request.getRequestDispatcher("/jsp/supplier/list.jsp").forward(request,response);
        }
    }

    /**
     * 查询所有供货商信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllSuppliers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String querySupCode = request.getParameter("querySupCode");
        String querySupName = request.getParameter("querySupName");
        String pageNo = request.getParameter("pageIndex");
        if (pageNo == null || "".equals(pageNo)){
            pageNo = "1";
        }
        Integer pageIndex = Integer.parseInt(pageNo);
        PageHelper.startPage(pageIndex,5);
        List<Supplier> supplierList = supplierService.queryAllSuppliers(querySupCode, querySupName);
        PageInfo<Supplier> pageInfo = new PageInfo<>(supplierList);

        long totalCount = pageInfo.getTotal();
        int totalPageCount = pageInfo.getPages();
        int currentPageNo = pageInfo.getPageNum();

        HttpSession session = request.getSession();
        session.setAttribute("querySupCode",querySupCode);
        session.setAttribute("querySupName",querySupName);
        session.setAttribute("totalCount",totalCount);
        session.setAttribute("totalPageCount",totalPageCount);
        session.setAttribute("currentPageNo",currentPageNo);
        session.setAttribute("pageInfo",pageInfo);
        session.setAttribute("supplierList",supplierList);

        response.sendRedirect(request.getContextPath()+"/jsp/supplier/list.jsp");
    }
}
