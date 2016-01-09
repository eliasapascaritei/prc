package com.prc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.prc.dao.UserDao;
import com.prc.model.User;

public class JsonController extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    private UserDao dao;

    public JsonController() {
        super();
        dao = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if(action.equalsIgnoreCase("show")){
        	response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
        	String s = request.getParameter("cnp");
        	System.out.println("param:   "+s);
        	User users = dao.getUserByCnp(s);
        	out.println(users.toString());
        	
        }
        else if (action.equalsIgnoreCase("listUser")){
        	response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
        	User res = new User();
        	out.println(res.JsToString());
        } else {
        	response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
        	out.println("Ok");
        }

       
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	
    	
    	User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setCnp(request.getParameter("cnp"));
        String userid = request.getParameter("userid");
        if(userid == null || userid.isEmpty())
        {
            dao.addUser(user);
        }
        else
        {
            user.setUserid(Integer.parseInt(userid));
            dao.updateUser(user);
        }
        //RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        //request.setAttribute("users", dao.getAllUsers());
       // view.forward(request, response);
        response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	out.println("User Inserted");
        
    }
}