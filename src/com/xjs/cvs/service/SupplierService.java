package com.xjs.cvs.service;


import com.xjs.cvs.pojo.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther SaManDaShi
 * @date 2023/11/30 8:05
 */
public interface SupplierService {

    public List<Supplier> queryAllSuppliers(@Param("supCode") String supCode,
                                            @Param("supName") String supName);

    public Supplier querySupById(@Param("id") Integer id);

    public int addSupplier(Supplier supplier);

    public int modifySupplier(Supplier supplier);

    public int removeSupById(@Param("id")Integer id);
}
