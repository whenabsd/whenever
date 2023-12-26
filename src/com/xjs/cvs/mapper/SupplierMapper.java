package com.xjs.cvs.mapper;


import com.xjs.cvs.pojo.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther SaManDaShi
 * @date 2023/11/29 16:12
 */
public interface SupplierMapper {

    //根据指定条件完成查询操作
    public List<Supplier> selectAllSuppliers(@Param("supCode") String supCode,
                                             @Param("supName") String supName)throws Exception;

    //根据供货商编号进行查询
    public Supplier selectSupById(@Param("id") Integer id)throws Exception;

    //添加供货商信息到数据库表中
    public int insertSupplier(Supplier supplier)throws Exception;

    //修改指定供货商信息
    public int updateSupplier(Supplier supplier)throws Exception;

    //删除指定编号的供货商
    public int deleteSupById(@Param("id")Integer id)throws Exception;

}
