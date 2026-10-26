package com.cms.service;

import com.cms.model.Complaint;
import com.cms.model.User;
import com.cms.store.DataStore;

import java.util.List;
import java.util.Optional;

public class ComplaintService {

    private final DataStore store = DataStore.getInstance();

    /** Submit a new complaint. Returns error string or null on success. */
    public String submit(String title, String description, String category,
                         String priority, int customerId, String customerName) {
        if (title == null || title.isBlank())       return "Title is required.";
        if (description == null || description.isBlank()) return "Description is required.";
        if (category == null || category.isBlank()) return "Category is required.";

        Complaint.Priority p;
        try { p = Complaint.Priority.valueOf(priority.toUpperCase()); }
        catch (Exception e) { p = Complaint.Priority.MEDIUM; }

        Complaint c = new Complaint(store.nextComplaintId(), title.trim(),
                description.trim(), category.trim(), p, customerId, customerName);
        store.saveComplaint(c);
        return null;
    }

    /** Assign a complaint to a staff member. */
    public String assign(int complaintId, int staffId) {
        Optional<Complaint> oc = store.findComplaintById(complaintId);
        if (oc.isEmpty()) return "Complaint not found.";

        Optional<User> ou = store.findUserById(staffId);
        if (ou.isEmpty()) return "Staff not found.";

        Complaint c = oc.get();
        User staff  = ou.get();
        c.setAssignedStaffId(staffId);
        c.setAssignedStaffName(staff.getName());
        c.setStatus(Complaint.Status.ASSIGNED);
        store.saveComplaint(c);
        return null;
    }

    /** Update status and resolution notes. */
    public String updateStatus(int complaintId, String status, String notes) {
        Optional<Complaint> oc = store.findComplaintById(complaintId);
        if (oc.isEmpty()) return "Complaint not found.";

        Complaint c = oc.get();
        try { c.setStatus(Complaint.Status.valueOf(status.toUpperCase())); }
        catch (Exception e) { return "Invalid status."; }

        if (notes != null && !notes.isBlank()) c.setResolutionNotes(notes.trim());
        store.saveComplaint(c);
        return null;
    }

    public Optional<Complaint> findById(int id)          { return store.findComplaintById(id); }
    public List<Complaint>     getAll()                  { return store.getAllComplaints(); }
    public List<Complaint>     getByCustomer(int id)     { return store.getComplaintsByCustomer(id); }
    public List<Complaint>     getByStaff(int id)        { return store.getComplaintsByStaff(id); }
    public long                countByStatus(Complaint.Status s) { return store.countByStatus(s); }
    public java.util.Map<String,Long> countByCategory()  { return store.countByCategory(); }
}
