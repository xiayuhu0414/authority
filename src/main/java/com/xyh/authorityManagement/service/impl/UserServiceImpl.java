package com.xyh.authorityManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyh.authorityManagement.annotation.LogAnno;
import com.xyh.authorityManagement.mapper.RoleMapper;
import com.xyh.authorityManagement.mapper.UserMapper;
import com.xyh.authorityManagement.pojo.Role;
import com.xyh.authorityManagement.pojo.User;
import com.xyh.authorityManagement.service.IUserService;
import com.xyh.authorityManagement.vo.EasyUiDataGridResult;
import com.xyh.authorityManagement.vo.EasyUiOptionalTreeNode;
import com.xyh.authorityManagement.vo.GlobalResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务实现类
 *
 * @author xyh
 * @date 2021/11/14 10:21
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RoleMapper roleMapper;


    /**
     * @param source:
     * @param salt:
     * @description: 加密
     * @return: java.lang.String
     * @author xyh
     * @date: 2021/11/14 14:47
     */
    private String encrypt(String source, String salt) {
        int hashIterations = 2;
        Md5Hash md5 = new Md5Hash(source, salt, hashIterations);
        return md5.toString();
    }

    @Override
    public User findUserByCodeAndPwd(String username, String pwd) {
        //密码加密
        String userPwd = encrypt(pwd, username);
        //获取数据库用户信息
        return userMapper.selectUserByCodeAndPwd(username, userPwd);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public EasyUiDataGridResult findUserListByPage(User user, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<User> list = userMapper.selectUserListByPage(user);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        EasyUiDataGridResult result = new EasyUiDataGridResult();
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<User> findUserName(String str) {
        return userMapper.selectUserName(str);
    }

    @RequiresPermissions("用户管理")
    @Override
    public GlobalResult addUser(User user) {
        if (user == null) {
            return new GlobalResult(400, "用户信息为空，添加失败！", null);
        }
        //密码不为空时，对密码进行加密
        if ("".equals(user.getPassword())) {
            user.setPassword(null);
        } else {
            String password = encrypt(user.getPassword(), user.getUserCode());
            user.setPassword(password);
        }
        Integer integer = userMapper.insertUser(user);
        if (integer == 0) {
            return new GlobalResult(400, "用户添加失败", null);
        } else {
            return new GlobalResult(200, "用户添加成功", null);

        }
    }

    @RequiresPermissions("用户管理")
    @Override
    public GlobalResult updateUser(User user) {
        if (user == null) {
            return new GlobalResult(400, "用户信息为空，修改失败！", null);
        }
        //密码不为空时，对密码进行加密
        if ("".equals(user.getPassword())) {
            user.setPassword(null);
        } else {
            String password = encrypt(user.getPassword(), user.getUserCode());
            user.setPassword(password);
        }
        Integer integer = userMapper.updateUser(user);
        if (integer == 0) {
            return new GlobalResult(400, "用户信息更新失败", null);
        } else {
            return new GlobalResult(200, "用户信息更新成功", null);

        }
    }

    @LogAnno(operateType = "更新用户密码")
    @Override
    public GlobalResult updatePassword(User user, String oldPassword, String newPassword) {
        String msg = "用户未登陆";
        //用户登录
        if (user != null) {
            String encryptOldPassword = encrypt(oldPassword, user.getPassword());
            if (encryptOldPassword.equals(user.getPassword())) {
                String password = encrypt(newPassword, user.getUserCode());
                Integer row = userMapper.updatePasswordById(user.getUserId(), password);
                if (row > 0) {
                    return new GlobalResult(200, "密码修改成功", null);
                } else {
                    msg = "密码修改失败";
                }
            } else {
                msg = "密码错误";
            }
        }
        return new GlobalResult(400, msg, null);
    }

    @RequiresPermissions("用户管理")
    @Override
    public GlobalResult deleteUser(Integer userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (userId == null) {
                return new GlobalResult(400, "用户id为空，添加失败！", 400);
            }
            Integer integer = userMapper.deleteUserById(userId);
            if (integer == 0) {
                return new GlobalResult(400, "用户删除失败", null);
            } else {
                //删除用户下的所有角色
                userMapper.deleteUserRole(userId);
                //删除用户下的所有缓存
                jedis.del("menuEasyUi_" + userId);
                jedis.del("menuList_" + userId);
                return new GlobalResult(200, "用户删除成功", null);
            }
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<EasyUiOptionalTreeNode> findUserRole(Integer userId) {
        //1.获取当前用户的所有角色
        List<Role> userRoleList = userMapper.selectUserRole(userId);
        //2.获取系统中所有的角色
        List<Role> rolesList = roleMapper.selectRoleList();
        //3.设置返回值
        List<EasyUiOptionalTreeNode> treeList = new ArrayList<>();
        EasyUiOptionalTreeNode t1 = null;
        //4.封装返回值将用户对应的角色设置为true
        for (Role role : rolesList) {
            t1 = new EasyUiOptionalTreeNode();
            t1.setId(role.getId() + "");
            t1.setText(role.getName());
            //如果用户拥有这个角色，设为true
            for (Role userRole : userRoleList) {
                if (userRole.getId().equals(role.getId())) {
                    t1.setChecked(true);
                }
            }
            treeList.add(t1);
        }
        return treeList;
    }

    @LogAnno(operateType = "更新用户对应角色")
    @Override
    public GlobalResult updateUserRole(Integer userId, String checkedIds) {
        try (Jedis jedis = jedisPool.getResource()) {
            //先删除用户下的所有角色
            userMapper.deleteUserRole(userId);
            if (checkedIds != null) {
                String[] ids = checkedIds.split(",");
                for (String roleId : ids) {
                    //设置用户的角色
                    userMapper.insertUserRole(userId, Integer.parseInt(roleId));
                }
            }
            //清除缓存
            jedis.del("menusEasyUi_" + userId);
            jedis.del("menusList_" + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalResult.build(200, "保存成功");
    }

    @LogAnno(operateType = "EXCEL导出用户信息")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void export(OutputStream outputStream, User user) {
        // 获取所有供应商信息
        List<User> UserList = userMapper.selectUserlistByPage(user);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = { "账号", "密码", "真实姓名 ", "出生日期 " };
        // 列宽
        int[] columnWidths = { 6000, 6000, 6000, 6000 };
        HSSFCell cell = null;
        for (int i = 0; i < headerName.length; i++) {
            // 创建表头单元格
            cell = row.createCell(i);
            // 向表头单元格写值
            cell.setCellValue(headerName[i]);
            sheet.setColumnWidth(i, columnWidths[i]);
        }
        // 4.向内容单元格写值
        int i = 1;
        for (User u : UserList) {
            row = sheet.createRow(i);
            // 账号
            row.createCell(0).setCellValue(u.getUserCode());
            // 密码
            row.createCell(1).setCellValue("********");
            if (u.getUserName() != null) {
                // "真实姓名
                row.createCell(2).setCellValue(u.getUserName());
            }
            if (u.getUserBirthday() != null) {
                HSSFCellStyle style_date = wk.createCellStyle();
                DataFormat df = wk.createDataFormat();
                style_date.setDataFormat(df.getFormat("yyyy-MM-dd"));
                // 出生日期
                row.createCell(3).setCellValue(u.getUserBirthday());
                sheet.getRow(i).getCell(3).setCellStyle(style_date);
            }
            i++;
        }
        try {
            // 写入到输出流中
            wk.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭工作簿
                wk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @LogAnno(operateType = "excel导入用户信息")
    @Override
    public void doImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            User user = null;
            for (int i = 1; i <= lastRow; i++) {
                // 账号
                user = new User();
                user.setUserCode(sheet.getRow(i).getCell(0).getStringCellValue());
                // 判断是否已经存在，通过账号来判断
                List<User> list = userMapper.selectUserByUserCode(user.getUserCode());
                if (list.size() > 0) {
                    // 说明存在用户，需要更新
                    user = list.get(0);
                }
                HSSFCell cell = null;
                // 密码
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(CellType.STRING);
                if(!cell.getStringCellValue().equals("********")) {
                    user.setPassword(encrypt(cell.getStringCellValue(), user.getUserCode()));
                }
                // 真实姓名
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(CellType.STRING);
                user.setUserName(sheet.getRow(i).getCell(2).getStringCellValue());
                // 出生日期
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(CellType.NUMERIC);
                user.setUserBirthday(sheet.getRow(i).getCell(3).getDateCellValue());
                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    userMapper.insertUser(user);
                } else {
                    // 更新用户信息
                    userMapper.updateUserByUserCode(user);
                }
            }
        } finally {
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
