package com.cms.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Complaint {

    public enum Status   { OPEN, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED, ESCALATED }
    public enum Priority { LOW, MEDIUM, HIGH, CRITICAL }

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    private int      id;
    private String   title;
    private String   description;
    private String   category;
    private Status   status;
    private Priority priority;
    private int      customerId;
    private String   customerName;
    private Integer  assignedStaffId;
    private String   assignedStaffName;
    private String   resolutionNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Complaint() {}

    public Complaint(int id, String title, String description, String category,
                     Priority priority, int customerId, String customerName) {
        this.id           = id;
        this.title        = title;
        this.description  = description;
        this.category     = category;
        this.priority     = priority;
        this.customerId   = customerId;
        this.customerName = customerName;
        this.status       = Status.OPEN;
        this.createdAt    = LocalDateTime.now();
        this.updatedAt    = LocalDateTime.now();
    }

    // ── getters / setters ─────────────────────────────────────────────────────
    public int     getId()                    { return id; }
    public void    setId(int id)              { this.id = id; }

    public String  getTitle()                 { return title; }
    public void    setTitle(String t)         { this.title = t; }

    public String  getDescription()           { return description; }
    public void    setDescription(String d)   { this.description = d; }

    public String  getCategory()              { return category; }
    public void    setCategory(String c)      { this.category = c; }

    public Status  getStatus()                { return status; }
    public void    setStatus(Status s)        { this.status = s; this.updatedAt = LocalDateTime.now(); }

    public Priority getPriority()             { return priority; }
    public void     setPriority(Priority p)   { this.priority = p; }

    public int     getCustomerId()            { return customerId; }
    public void    setCustomerId(int id)      { this.customerId = id; }

    public String  getCustomerName()          { return customerName; }
    public void    setCustomerName(String n)  { this.customerName = n; }

    public Integer getAssignedStaffId()       { return assignedStaffId; }
    public void    setAssignedStaffId(Integer id) { this.assignedStaffId = id; }

    public String  getAssignedStaffName()         { return assignedStaffName; }
    public void    setAssignedStaffName(String n)  { this.assignedStaffName = n; }

    public String  getResolutionNotes()            { return resolutionNotes; }
    public void    setResolutionNotes(String n)    { this.resolutionNotes = n; }

    public LocalDateTime getCreatedAt()            { return createdAt; }
    public void          setCreatedAt(LocalDateTime t) { this.createdAt = t; }

    public LocalDateTime getUpdatedAt()            { return updatedAt; }
    public void          setUpdatedAt(LocalDateTime t) { this.updatedAt = t; }

    public String getCreatedAtFormatted() {
        return createdAt != null ? createdAt.format(FMT) : "";
    }
    public String getUpdatedAtFormatted() {
        return updatedAt != null ? updatedAt.format(FMT) : "";
    }
}
