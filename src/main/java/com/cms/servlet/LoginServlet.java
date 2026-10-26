package com.cms.servlet;

import com.cms.model.User;
import com.cms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.login(email, password);

        if (user == null) {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);

        switch (user.getRole()) {
            case ADMIN:  resp.sendRedirect(req.getContextPath() + "/admin/dashboard");  break;
            case STAFF:  resp.sendRedirect(req.getContextPath() + "/staff/dashboard");  break;
            default:     resp.sendRedirect(req.getContextPath() + "/customer/dashboard"); break;
        }
    }
}
