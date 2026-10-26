package com.cms.servlet;

import com.cms.model.Complaint;
import com.cms.model.User;
import com.cms.service.ComplaintService;
import com.cms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/dashboard", "/admin/assign", "/admin/users"})
public class AdminServlet extends HttpServlet {

    private final ComplaintService complaintService = new ComplaintService();
    private final UserService      userService      = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = requireAdmin(req, resp);
        if (user == null) return;

        String path = req.getServletPath();

        if ("/admin/users".equals(path)) {
            req.setAttribute("users", userService.getAll());
            req.getRequestDispatcher("/views/admin/users.jsp").forward(req, resp);
            return;
        }

        // dashboard
        req.setAttribute("complaints",  complaintService.getAll());
        req.setAttribute("staffList",   userService.getStaffList());
        req.setAttribute("open",        complaintService.countByStatus(Complaint.Status.OPEN));
        req.setAttribute("assigned",    complaintService.countByStatus(Complaint.Status.ASSIGNED));
        req.setAttribute("resolved",    complaintService.countByStatus(Complaint.Status.RESOLVED));
        req.setAttribute("byCategory",  complaintService.countByCategory());
        req.getRequestDispatcher("/views/admin/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = requireAdmin(req, resp);
        if (user == null) return;

        int complaintId = Integer.parseInt(req.getParameter("complaintId"));
        int staffId     = Integer.parseInt(req.getParameter("staffId"));
        complaintService.assign(complaintId, staffId);
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard?msg=assigned");
    }

    private User requireAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) { resp.sendRedirect(req.getContextPath() + "/login"); return null; }
        User u = (User) session.getAttribute("user");
        if (u == null || u.getRole() != User.Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/login"); return null;
        }
        return u;
    }
}
