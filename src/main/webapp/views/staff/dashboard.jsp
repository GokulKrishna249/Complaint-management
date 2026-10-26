<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard — CMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .modal-overlay { display:none; position:fixed; inset:0;
                         background:rgba(0,0,0,.45); z-index:100;
                         justify-content:center; align-items:center; }
        .modal-overlay.open { display:flex; }
        .modal { background:#fff; border-radius:10px; padding:2rem;
                 width:100%; max-width:460px; box-shadow:0 8px 32px rgba(0,0,0,.2); }
        .modal h3 { margin-bottom:1.2rem; color:#1a2e4a; }
    </style>
</head>
<body>

<nav class="navbar">
    <span class="brand">&#9679; <span>CMS</span> — Staff Portal</span>
    <div class="nav-links">
        <span style="color:#cbd5e0; font-size:.9rem;">${sessionScope.user.name}</span>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="page">
    <h2 class="page-title">Assigned Complaints</h2>

    <c:if test="${param.msg eq 'updated'}">
        <div class="alert alert-success">Status updated successfully!</div>
    </c:if>

    <div class="tbl-wrap">
        <table>
            <thead>
                <tr>
                    <th>#</th><th>Title</th><th>Category</th>
                    <th>Priority</th><th>Status</th><th>Customer</th>
                    <th>Submitted</th><th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty complaints}">
                    <tr><td colspan="8" style="text-align:center; color:#718096; padding:2rem;">
                        No complaints assigned to you yet.
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
                            <td>${c.createdAtFormatted}</td>
                            <td>
                                <button class="btn btn-primary"
                                    onclick="openModal(${c.id}, '${c.status}', '${c.resolutionNotes}')">
                                    Update
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Update Status Modal -->
<div class="modal-overlay" id="modal">
    <div class="modal">
        <h3>Update Complaint Status</h3>
        <form method="post" action="${pageContext.request.contextPath}/staff/update">
            <input type="hidden" name="complaintId" id="modalId">
            <div class="form-group">
                <label>Status</label>
                <select name="status" id="modalStatus">
                    <option value="ASSIGNED">Assigned</option>
                    <option value="IN_PROGRESS">In Progress</option>
                    <option value="RESOLVED">Resolved</option>
                    <option value="CLOSED">Closed</option>
                </select>
            </div>
            <div class="form-group">
                <label>Resolution Notes</label>
                <textarea name="notes" id="modalNotes" rows="3"
                          placeholder="Describe what was done…"></textarea>
            </div>
            <div style="display:flex; gap:1rem;">
                <button type="submit" class="btn btn-primary">Save</button>
                <button type="button" class="btn btn-outline" onclick="closeModal()">Cancel</button>
            </div>
        </form>
    </div>
</div>

<script>
function openModal(id, status, notes) {
    document.getElementById('modalId').value    = id;
    document.getElementById('modalStatus').value = status;
    document.getElementById('modalNotes').value  = notes || '';
    document.getElementById('modal').classList.add('open');
}
function closeModal() {
    document.getElementById('modal').classList.remove('open');
}
</script>
</body>
</html>
