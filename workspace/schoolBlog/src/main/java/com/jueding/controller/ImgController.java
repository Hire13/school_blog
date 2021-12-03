package com.jueding.controller;


import com.jueding.common.Result;
import com.jueding.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 北落燕门
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/api/img")
public class ImgController {

    @Autowired
    private ImgService imgService;

    @PostMapping("/imgUpload")
    public Result imgUpload() {
        return null;
    }


}
