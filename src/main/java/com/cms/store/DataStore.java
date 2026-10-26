package com.cms.store;

import com.cms.model.Complaint;
import com.cms.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Singleton in-memory data store — no database needed.
 * Data lives for the lifetime of the application (server session).
 */
public class DataStore {

    private static final DataStore INSTANCE = new DataStore();

    private final Map<Integer, User>      users      = new LinkedHashMap<>();
    private final Map<Integer, Complaint> complaints = new LinkedHashMap<>();

    private final AtomicInteger userIdSeq      = new AtomicInteger(1);
    private final AtomicInteger complaintIdSeq = new AtomicInteger(1);

    private DataStore() {
        seedData();
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    // ── seed demo accounts ────────────────────────────────────────────────────

    private void seedData() {
        User admin = new User(nextUserId(), "Admin",    "admin@cms.com",    "admin123",    User.Role.ADMIN);
        User staff = new User(nextUserId(), "Alice",    "alice@cms.com",    "staff123",    User.Role.STAFF);
        User cust  = new User(nextUserId(), "Gokul T",  "gokul@cms.com",    "customer123", User.Role.CUSTOMER);
        users.put(admin.getId(), admin);
        users.put(staff.getId(), staff);
        users.put(cust.getId(),  cust);

        // Demo complaints
        Complaint c1 = new Complaint(nextComplaintId(), "Internet not working",
                "Broadband connection drops every 30 minutes.", "Network",
                Complaint.Priority.HIGH, cust.getId(), cust.getName());
        c1.setStatus(Complaint.Status.ASSIGNED);
        c1.setAssignedStaffId(staff.getId());
        c1.setAssignedStaffName(staff.getName());

        Complaint c2 = new Complaint(nextComplaintId(), "Billing overcharge",
                "My bill is double the expected amount this month.", "Billing",
                Complaint.Priority.MEDIUM, cust.getId(), cust.getName());

        Complaint c3 = new Complaint(nextComplaintId(), "Login page not loading",
                "The login page shows a blank screen on Chrome.", "Technical",
                Complaint.Priority.CRITICAL, cust.getId(), cust.getName());
        c3.setStatus(Complaint.Status.RESOLVED);
        c3.setAssignedStaffId(staff.getId());
        c3.setAssignedStaffName(staff.getName());
        c3.setResolutionNotes("Cleared CDN cache. Issue resolved.");

        complaints.put(c1.getId(), c1);
        complaints.put(c2.getId(), c2);
        complaints.put(c3.getId(), c3);
    }

    // ── User operations ───────────────────────────────────────────────────────

    public int nextUserId()      { return userIdSeq.getAndIncrement(); }
    public int nextComplaintId() { return complaintIdSeq.getAndIncrement(); }

    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findUserByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<User> getUsersByRole(User.Role role) {
        return users.values().stream()
                .filter(u -> u.getRole() == role)
                .collect(Collectors.toList());
    }

    // ── Complaint operations ──────────────────────────────────────────────────

    public void saveComplaint(Complaint c) {
        complaints.put(c.getId(), c);
    }

    public Optional<Complaint> findComplaintById(int id) {
        return Optional.ofNullable(complaints.get(id));
    }

    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>(complaints.values());
        list.sort(Comparator.comparingInt(Complaint::getId).reversed());
        return list;
    }

    public List<Complaint> getComplaintsByCustomer(int customerId) {
        return complaints.values().stream()
                .filter(c -> c.getCustomerId() == customerId)
                .sorted(Comparator.comparingInt(Complaint::getId).reversed())
                .collect(Collectors.toList());
    }

    public List<Complaint> getComplaintsByStaff(int staffId) {
        return complaints.values().stream()
                .filter(c -> staffId == (c.getAssignedStaffId() != null ? c.getAssignedStaffId() : -1))
                .sorted(Comparator.comparingInt(Complaint::getId).reversed())
                .collect(Collectors.toList());
    }

    public List<Complaint> getComplaintsByStatus(Complaint.Status status) {
        return complaints.values().stream()
                .filter(c -> c.getStatus() == status)
                .collect(Collectors.toList());
    }

    public long countByStatus(Complaint.Status status) {
        return complaints.values().stream()
                .filter(c -> c.getStatus() == status).count();
    }

    public Map<String, Long> countByCategory() {
        return complaints.values().stream()
                .collect(Collectors.groupingBy(Complaint::getCategory, Collectors.counting()));
    }
}
