<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Complaints — CMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <span class="brand">&#9679; <span>CMS</span> — Customer Portal</span>
    <div class="nav-links">
        <span style="color:#cbd5e0; font-size:.9rem;">
            ${sessionScope.user.name}
        </span>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="page">

    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:1.5rem;">
        <h2 class="page-title" style="margin-bottom:0; border:none;">My Complaints</h2>
        <a href="${pageContext.request.contextPath}/customer/submit" class="btn btn-primary">
            + New Complaint
        </a>
    </div>

    <c:if test="${param.msg eq 'submitted'}">
        <div class="alert alert-success">Complaint submitted successfully!</div>
    </c:if>

    <div class="tbl-wrap">
        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Category</th>
                    <th>Priority</th>
                    <th>Status</th>
                    <th>Assigned To</th>
                    <th>Submitted</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty complaints}">
                    <tr><td colspan="7" style="text-align:center; color:#718096; padding:2rem;">
                        No complaints yet.
                        <a href="${pageContext.request.contextPath}/customer/submit">Submit one now.</a>
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
                            <td>${empty c.assignedStaffName ? '—' : c.assignedStaffName}</td>
                            <td>${c.createdAtFormatted}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
