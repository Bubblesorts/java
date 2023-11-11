package com.bluemsunblog.service;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
@Transactional
public interface RandomValidateCodeService {
    Font getFont();
    Color getRandColor(int fc, int bc);
    void getRandcode(HttpServletRequest request, HttpServletResponse response);
    String drowString(Graphics g, String randomString, int i);
    void drowLine(Graphics g);
    String getRandomString(int num);
}
