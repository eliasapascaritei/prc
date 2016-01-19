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

import com.prc.dao.UserDao;
import com.prc.model.User;
import com.google.gson.Gson;

public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listUser.jsp";
    private static String SEARCH_USER = "/searchUser.jsp";
    
    private UserDao dao;

    public UserController() {
        super();
        dao = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            dao.deleteUser(userId);
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());    
        }
        else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int userId = Integer.parseInt(request.getParameter("userId"));
            User user = dao.getUserById(userId);
            request.setAttribute("user", user);
        }
        else if(action.equalsIgnoreCase("search")) {
        	forward = SEARCH_USER;
        }
        else if(action.equalsIgnoreCase("show")) {
        	String s = request.getParameter("cnp");
        	System.out.println("param:   "+s);
        	User user = dao.getUserByCnp(s);
        	List<User> users = new ArrayList<User>();
        	forward = LIST_USER;
        	users.add(user);
        	request.setAttribute("users", users);
        }
        else if (action.equalsIgnoreCase("listUser")) {
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
        } 
        else if(action.equalsIgnoreCase("listJson")) {
        	response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
        	User u = new User();
        	String s = u.JsToString();
        	out.println(s);
        	forward = "Json";
        }
        else if(action.equalsIgnoreCase("showJ")) {
        	response.setContentType("application/json");
        	PrintWriter out = response.getWriter();
        	String s = request.getParameter("cnp");
        	System.out.println("param:   "+s);
        	User users = dao.getUserByCnp(s);
        	out.println(users.toString());
        	forward = "Json";
        }
        else {
            forward = INSERT_OR_EDIT;
        }

        if(!forward.equals("Json")) {
        	RequestDispatcher view = request.getRequestDispatcher(forward);
        	view.forward(request, response);
        }
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	System.out.println("params: " + request.getParameterMap());
    	String action = request.getParameter("action");
    	if (action != null && action.equalsIgnoreCase("addJson")) {
    		User user = new User();
	        user.setFirstName(request.getParameter("firstName"));
	        user.setLastName(request.getParameter("lastName"));
	        user.setCnp(request.getParameter("cnp"));
	        String userid = request.getParameter("userid");
	        boolean saved = false;
	        if (userid == null || userid.isEmpty()) {
	            saved = dao.addUser(user);
	        }
	        else {
	            user.setUserid(Integer.parseInt(userid));
	            saved = dao.updateUser(user);
	        }
	        
	        PrintWriter out = response.getWriter();
	        String res = "{ \"saved\":" + saved + " }";
	        out.println(res);
    	}
    	else {
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
	        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
	        request.setAttribute("users", dao.getAllUsers());
	        view.forward(request, response);
    	}
    }
}