package com.gmail.jahont.pavel;

import java.lang.invoke.MethodHandles;

import com.gmail.jahont.pavel.service.HomeWorkService;
import com.gmail.jahont.pavel.service.impl.HomeWorkServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App 
{
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeWorkService homeWorkService = new HomeWorkServiceImpl();
        homeWorkService.runFirstTask();

        homeWorkService.runSecondTask();
    }
}