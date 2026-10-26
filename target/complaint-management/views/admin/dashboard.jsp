<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard — CMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .modal-overlay { display:none; position:fixed; inset:0;
                         background:rgba(0,0,0,.45); z-index:100;
                         justify-content:center; align-items:center; }
        .modal-overlay.open { display:flex; }
        .modal { background:#fff; border-radius:10px; padding:2rem;
                 width:100%; max-width:420px; box-shadow:0 8px 32px rgba(0,0,0,.2); }
        .modal h3 { margin-bottom:1.2rem; color:#1a2e4a; }
    </style>
</head>
<body>

<nav class="navbar">
    <span class="brand">&#9679; <span>CMS</span> — Admin Panel</span>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/admin/users">Users</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="page">

    <h2 class="page-title">Dashboard</h2>

    <c:if test="${param.msg eq 'assigned'}">
        <div class="alert alert-success">Complaint assigned successfully!</div>
    </c:if>

    <!-- KPI Cards -->
    <div class="kpi-grid">
        <div class="kpi">
            <div class="num">${open}</div>
            <div class="label">Open</div>
        </div>
        <div class="kpi">
            <div class="num">${assigned}</div>
            <div class="label">Assigned</div>
        </div>
        <div class="kpi">
            <div class="num">${resolved}</div>
            <div class="label">Resolved</div>
        </div>
        <div class="kpi">
            <div class="num">${complaints.size()}</div>
            <div class="label">Total</div>
        </div>
    </div>

    <!-- Category Breakdown -->
    <div class="card" style="margin-bottom:2rem;">
        <h3 style="color:#1a2e4a; margin-bottom:1rem;">By Category</h3>
        <div style="display:flex; gap:1rem; flex-wrap:wrap;">
        <c:forEach var="entry" items="${byCategory}">
            <div style="background:#f0f2f5; padding:.5rem 1.1rem;
                        border-radius:999px; font-size:.85rem;">
                <strong>${entry.key}</strong>: ${entry.value}
            </div>
        </c:forEach>
        </div>
    </div>

    <!-- All Complaints Table -->
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:.75rem;">
        <h3 style="color:#1a2e4a;">All Complaints</h3>
    </div>

    <div class="tbl-wrap">
        <table>
            <thead>
                <tr>
                    <th>#</th><th>Title</th><th>Category</th>
                    <th>Priority</th><th>Status</th>
                    <th>Customer</th><th>Assigned To</th>
                    <th>Submitted</th><th>Assign</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty complaints}">
                    <tr><td colspan="9" style="text-align:center; color:#718096; padding:2rem;">
                        No complaints yet.
                    </td></tr>
                </c:when>
                <c:otherwise>
                <c:forEach var="c" items="${complaints}">
                    <tr>
                        <td>#${c.id}</td>
                        <td><strong>${c.title}</strong></td>
                        <td>${c.category}</td>
                        <td><span class="badge badge-${c.priority}">${c.priority}</span></td>
                        <td><span class="badge badge-${c.status}">${c.status}</span></td>
                        <td>${c.customerName}</td>
                        <td>${empty c.assignedStaffName ? '—' : c.assignedStaffName}</td>
                        <td>${c.createdAtFormatted}</td>
                        <td>
                            <button class="btn btn-outline"
                                onclick="openModal(${c.id})">Assign</button>
                        </td>
                    </tr>
                </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Assign Modal -->
<div class="modal-overlay" id="modal">
    <div class="modal">
        <h3>Assign Complaint</h3>
        <form method="post" action="${pageContext.request.contextPath}/admin/assign">
            <input type="hidden" name="complaintId" id="modalId">
            <div class="form-group">
                <label>Select Staff Member</label>
                <select name="staffId" required>
                    <option value="">— Choose —</option>
                    <c:forEach var="s" items="${staffList}">
                        <option value="${s.id}">${s.name} (${s.email})</option>
                    </c:forEach>
                </select>
            </div>
            <div style="display:flex; gap:1rem;">
                <button type="submit" class="btn btn-primary">Assign</button>
                <button type="button" class="btn btn-outline" onclick="closeModal()">Cancel</button>
            </div>
        </form>
    </div>
</div>

<script>
function openModal(id) {
    document.getElementById('modalId').value = id;
    document.getElementById('modal').classList.add('open');
}
function closeModal() {
    document.getElementById('modal').classList.remove('open');
}
</script>
</body>
</html>
