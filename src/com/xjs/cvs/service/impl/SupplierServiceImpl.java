package com.xjs.cvs.service.impl;


import com.xjs.cvs.mapper.SupplierMapper;
import com.xjs.cvs.pojo.Supplier;
import com.xjs.cvs.service.SupplierService;
import com.xjs.cvs.util.MybatisUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther SaManDaShi
 * @date 2023/11/30 8:30
 */
public class SupplierServiceImpl implements SupplierService {

    private SupplierMapper supplierMapper;

    public SupplierMapper getSupplierMapper() {
        return supplierMapper;
    }

    public void setSupplierMapper(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    @Override
    public List<Supplier> queryAllSuppliers(String supCode, String supName) {
        List<Supplier> supplierList = new ArrayList<>();
        supplierMapper = MybatisUtil.getSqlSession().getMapper(SupplierMapper.class);
        try {
            supplierList = supplierMapper.selectAllSuppliers(supCode, supName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            MybatisUtil.closeSqlSession();
        }

        return supplierList;
    }

    @Override
    public Supplier querySupById(Integer id) {
        Supplier supplier = null;
        if (id!=null && id>0){
            supplierMapper = MybatisUtil.getSqlSession().getMapper(SupplierMapper.class);
            try {
                supplier = supplierMapper.selectSupById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return supplier;
    }

    @Override
    public int addSupplier(Supplier supplier) {
        int n = 0;
        if (supplier!=null){
            supplierMapper = MybatisUtil.getSqlSession().getMapper(SupplierMapper.class);
            try {
                n = supplierMapper.insertSupplier(supplier);
                MybatisUtil.getSqlSession().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return n;
    }

    @Override
    public int modifySupplier(Supplier supplier) {
        int n = 0;
        if (supplier!=null){
            supplierMapper = MybatisUtil.getSqlSession().getMapper(SupplierMapper.class);
            try {
                n = supplierMapper.updateSupplier(supplier);
                MybatisUtil.getSqlSession().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return n;
    }

    @Override
    public int removeSupById(Integer id) {
        int n = 0;
        if (id!=null){
            supplierMapper = MybatisUtil.getSqlSession().getMapper(SupplierMapper.class);
            try {
                n = supplierMapper.deleteSupById(id);
                MybatisUtil.getSqlSession().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                MybatisUtil.closeSqlSession();
            }
        }
        return n;
    }
}
