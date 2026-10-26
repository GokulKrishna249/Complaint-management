package com.cms.servlet;

import com.cms.model.Complaint;
import com.cms.model.User;
import com.cms.service.ComplaintService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/customer/dashboard", "/customer/submit", "/customer/view"})
public class CustomerServlet extends HttpServlet {

    private final ComplaintService complaintService = new ComplaintService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = requireLogin(req, resp, User.Role.CUSTOMER);
        if (user == null) return;

        String path = req.getServletPath();

        if ("/customer/view".equals(path)) {
            int id = Integer.parseInt(req.getParameter("id"));
            complaintService.findById(id).ifPresent(c -> req.setAttribute("complaint", c));
            req.getRequestDispatcher("/views/customer/view.jsp").forward(req, resp);
            return;
        }

        // dashboard — list all complaints for this customer
        req.setAttribute("complaints", complaintService.getByCustomer(user.getId()));
        req.getRequestDispatcher("/views/customer/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = requireLogin(req, resp, User.Role.CUSTOMER);
        if (user == null) return;

        String title       = req.getParameter("title");
        String description = req.getParameter("description");
        String category    = req.getParameter("category");
        String priority    = req.getParameter("priority");

        String error = complaintService.submit(title, description, category,
                priority, user.getId(), user.getName());

        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/views/customer/submit.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/customer/dashboard?msg=submitted");
    }

    private User requireLogin(HttpServletRequest req, HttpServletResponse resp, User.Role role)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) { resp.sendRedirect(req.getContextPath() + "/login"); return null; }
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != role) {
            resp.sendRedirect(req.getContextPath() + "/login"); return null;
        }
        return user;
    }
}
