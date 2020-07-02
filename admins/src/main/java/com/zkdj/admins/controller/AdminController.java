package com.zkdj.admins.controller;

import com.github.pagehelper.PageInfo;
import com.zkdj.admins.domain.Admin;
import com.zkdj.admins.service.AdminService;
import com.zkdj.admins.util.ResultObject;
import org.apache.ibatis.mapping.ResultMap;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author SJXY
 */

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {

    @Value("${server.port}")
    private String myPort;
    @Autowired
    private AdminService adminService;

    @GetMapping("/adminfindAll")
    public  Object adminfindAll(){
        Map<String,Object> result=new HashMap<>();
        result.put("port",myPort);
        result.put("data",adminService.adminfindAll());
        return  result;
    }

    @GetMapping("/adminfindById/{adminId}")
    public  Object adminfindById(@PathVariable("adminId") Integer adminId){

        Admin admin=adminService.adminfindById(adminId);
        if(!StringUtils.isEmpty(admin)){
            return new ResultObject(200,"查询成功",admin);
        }
        return new ResultObject(400,"查询失败",null);
    }
    @PostMapping("/adminInsert")
    @ResponseBody
    public ResultObject insertAdmin(@RequestBody Admin admin){
        String name =admin.getAdminName();
        System.out.println(name+"添加管理员1"+admin.toString());
        ResultObject result=new ResultObject<>();
        try {
            if (!name.isEmpty()&& !name.equals("")) {
                int m = adminService.insertAdmin(admin);
                if (m > 0) {
                    admin.setAdminState(1);
                    result.setCode(200);
                    result.setMessage("添加成功");
                    result.setResultData(admin);
                    return result;
                }

            }
            }catch (Exception e){
                e.printStackTrace();
            }


        result.setCode(400);
        result.setMessage("添加失败");
        return result;
    }

    @PostMapping("/adminDelete")
    public  ResultObject adminDelete(@RequestParam("adminId") @RequestBody Integer adminId){
        ResultObject result =new ResultObject<>();
        try {
            System.out.println("------"+adminId);
            int m=adminService.adminDelete(adminId);
            if (adminId!=null) {
                if (m > 0) {
                    result.setCode(200);
                    result.setMessage("删除成功");
                    result.setResultData(adminId);
                    System.out.println("adminId为" + adminId + "的数据已经删除");
                    return result;
                }
            }else {
                result.setCode(400);
                result.setMessage("id为空，删除失败");
                return result;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(400);
        result.setMessage("删除失败");
        return result;
    }


    @PostMapping("/adminUpdate")
    public ResultObject adminUpdate(Admin admin){
         ResultObject result= new ResultObject<>();
         try {
             System.out.println("_-------------------"+admin.toString());
            int m= adminService.adminUpdate(admin);
          if (m>0) {
              result.setCode(200);
              result.setMessage("修改成功");
              result.setResultData(admin.getAdminId());
              System.out.println("修改了adminId为" + admin.getAdminId() + "的数据");
              return  result;
          }
          } catch (Exception e) {
             e.printStackTrace();
         }
         result.setCode(400);
         result.setMessage("修改失败");
         return result;
    }

    @PostMapping("/login")
    public ResultObject login(@RequestBody Admin admin){
        ResultObject result =new ResultObject<>();
        Map<String,Object> data =new HashMap<>();
        String token= UUID.randomUUID().toString().replace("-","").toUpperCase().substring(0,16);

        try {

            Admin admin1=this.adminService.login(admin);
            System.out.println(admin1);


            if (admin1!=null){
                if (admin1.getAdminState()!=1){
                    System.out.println(admin1.getAdminState()+"------**********-----------");
                    result.setCode(400);
                    result.setMessage("当前账户已经登录");
                    return  result;
                }
               /* result.setCode(200);
                result.setMessage("登录成功");
                Map<String,Admin> map=new HashMap<>();
                map.put("loginAdmin",admin1);
                result.setResultData(map);
                return result;*/
                System.out.println(admin1.getAdminState()+"------**********--+++---------");
                adminService.State(admin1);
                data.put("adminId",admin1.getAdminId());
                data.put("adminName",admin1.getAdminName());
                adminService.stockIntoRedis(admin1);

                int i=adminService.addTokenToRedis(token, data);
                data.clear();
                data.put("token", token);
//
                data.put("Loginadmin", admin1);
                System.out.println(data+"------");
                result.setResultData(data);
                result.setCode(200);
                result.setMessage("登录成功");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       result.setCode(400);
       result.setMessage("登录失败，账号或密码错误");
       return  result;

   }


   @PostMapping("/exit")
    public ResultObject exit( @RequestBody Admin admin){
        ResultObject result = new ResultObject<>();
//        int m=admin.getAdminState();

        try {

         Admin admin1 =adminService.select(admin);
            System.out.println(admin1);
//         System.out.println(admin1.getAdminState()+"--------------");
            if (admin1!=null) {
                if (admin1.getAdminState()!=1) {
                    adminService.exit(admin1);
                    result.setCode(200);
                    result.setMessage("用户退出成功");
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(400);
        result.setMessage("该用户未登录");
       return  result;
   }

   @GetMapping("/selectAdmin/{pageNum}")
    public ResultObject selectAdmin(@PathVariable Integer pageNum){
        ResultObject result=new ResultObject();
        try {
            PageInfo<Admin> pageInfo=this.adminService.selectPages(pageNum);
            List<Admin> list=pageInfo.getList();
            if (list!=null && list.size()>0){
                result.setCode(200);
                result.setMessage("分页查询成功");
                result.setResultData(pageInfo);
                return  result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(400);
        result.setMessage("分页查询失败");
        return  result;
   }


   @PostMapping("/selectByLike")
    public ResultObject selectByLike(@RequestBody Admin admin){
        ResultObject result=new ResultObject<>();
        try {
            System.out.println("-----------"+admin);
                List<Admin> admins=adminService.selectByLike(admin);
            if (admins!=null && admins.size()>0){
                result.setCode(200);
                result.setMessage("查询成功");
                result.setResultData(admins);
                return  result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(400);
        result.setMessage("查询失败");
       return  result;
   }
@PostMapping("/sendEmail/{adminEmail}")
    public  ResultObject sendEmail(@PathVariable("adminEmail") String adminEmail){
        String regex="[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.(com|cn|com\\.cn)";
        if (!adminEmail.matches(regex)){
            return  new ResultObject(400,"邮箱格式有误",null);
        }
        return new ResultObject(adminService.sendVerifyCode(adminEmail),"发送成功",null);
}
}
