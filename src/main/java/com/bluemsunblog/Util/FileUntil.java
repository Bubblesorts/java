package com.bluemsunblog.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUntil {
    public static String address(HttpServletRequest request) throws ServletException, IOException {
        Part part = request.getPart("file");
        // 获取上传的文件名扩展名
        String disposition = part.getSubmittedFileName();
        String suffix = disposition.substring(disposition.lastIndexOf("."));

        // 随机的生成uuid，作为文件名的一部分。 加上刚才获取到的后缀作为最终文件名。
        String filename = UUID.randomUUID()+suffix;
        // 获取文件上传的位置，绝对路径URL
        String serverpath = request.getServletContext().getRealPath("upload");

//            System.out.println(filename);
//            System.out.println(serverpath);

        //不存在文件夹则新建一个
        File fileDisk = new File(serverpath);
        if (!fileDisk.exists()){
            fileDisk.mkdir();
        }

        // 文件的真正绝对路径 = 文件存储位置 + 文件名
        String fileparts = serverpath + "/" + filename;
        // 将文件内容写入指定的磁盘位置
        part.write(fileparts);

        // 准备给前端返回的文件访问的URL
        String projectServerPath = request.getScheme()+"://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath()+"/upload/"+filename;
        // request.getSchema()可以返回当前页面使用的协议，http 或是 https;
        // request.getServerName()可以返回当前页面所在的服务器的名字;
        // request.getServerPort()可以返回当前页面所在的服务器使用的端口,就是8080;
        // request.getContextPath()可以返回当前页面所在的应用的名字;
        // 拼接起来后就是完整的文件访问路径了！

        // 返回给前端文件访问的URL
        return projectServerPath;
    }

}
