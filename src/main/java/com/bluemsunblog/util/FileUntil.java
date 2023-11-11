package com.bluemsunblog.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.druid.util.StringUtils;
import com.bluemsunblog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@Slf4j
public class FileUntil {

    public static String uploadImage(MultipartFile headImage,HttpServletRequest request){
        if(headImage.isEmpty()){
            return "没上传图片";
        }
        //获取上传文件的后缀
        String fileName = headImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if(StringUtils.isEmpty(suffix)){
            throw new RuntimeException("文件类型错误");
        }
        //生成随机的存储文件的名字
        fileName = UUID.randomUUID()+suffix;

        File dest = new File("/root/photo" +"/"+  fileName);
        try { //上传文件
            headImage.transferTo(dest);
        } catch (IOException e) {
            log.info("上传文件失败" + e.getMessage());
            throw new RuntimeException("上传文件失败" + e.getMessage());
        }
//        //更新数据库的图像地址(web地址，客户端进行访问的)
//        //http://localhost:8080/community/user/header/xxx.png
//        User user = hostHolder.getValue();
//        String headerUrl = domain + contextPath + "/user/header/" + fileName;
//        userService.updateHeaderUrl(user.getId(),headerUrl);

        String projectServerPath = request.getScheme()+"://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath()+"/show/" +fileName;
        return projectServerPath;
    }
    public static String uploadFile(MultipartFile headImage,HttpServletRequest request){
        if(headImage.isEmpty()){
            return "没上传文件";
        }
        //获取上传文件的后缀
        String fileName = headImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if(StringUtils.isEmpty(suffix)){
            throw new RuntimeException("文件类型错误");
        }
        //生成随机的存储文件的名字
        fileName = UUID.randomUUID()+suffix;

        File dest = new File("/root/file" +"/"+  fileName);
        try { //上传文件
            headImage.transferTo(dest);
        } catch (IOException e) {
            log.info("上传文件失败" + e.getMessage());
            throw new RuntimeException("上传文件失败" + e.getMessage());
        }

        String projectServerPath = request.getScheme()+"://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath()+"/show/" +fileName;
        return projectServerPath;
    }

//    public static String downloadImage(String fileUrl ,String file) {
//        long l = 0L;
//        String path = null;
//        String staticAndMksDir = null;
//        if (fileUrl != null) {
//            //下载时文件名称
//            String fileName = fileUrl.substring(fileUrl.lastIndexOf("."));
//            try {
//                String uuidName = UUID.randomUUID().toString();
//                path = file+"/"+uuidName+fileName;
//                staticAndMksDir = Paths.get(file).toString();
//                HttpUtil.downloadFile(fileUrl, staticAndMksDir + File.separator + uuidName + fileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//
//            }
//        }
//        System.out.println(System.currentTimeMillis()-l);
//        return path;
//    }
}
