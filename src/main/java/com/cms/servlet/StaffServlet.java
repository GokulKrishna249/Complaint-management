package com.cms.servlet;

import com.cms.model.Complaint;
import com.cms.model.User;
import com.cms.service.ComplaintService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/staff/dashboard", "/staff/update"})
public class StaffServlet extends HttpServlet {

    private final ComplaintService complaintService = new ComplaintService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = requireLogin(req, resp);
        if (user == null) return;

        req.setAttribute("complaints", complaintService.getByStaff(user.getId()));
        req.getRequestDispatcher("/views/staff/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = requireLogin(req, resp);
        if (user == null) return;

        int    complaintId = Integer.parseInt(req.getParameter("complaintId"));
        String status      = req.getParameter("status");
        String notes       = req.getParameter("notes");

        complaintService.updateStatus(complaintId, status, notes);
        resp.sendRedirect(req.getContextPath() + "/staff/dashboard?msg=updated");
    }

    private User requireLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) { resp.sendRedirect(req.getContextPath() + "/login"); return null; }
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() == User.Role.CUSTOMER) {
            resp.sendRedirect(req.getContextPath() + "/login"); return null;
        }
        return user;
    }
}
