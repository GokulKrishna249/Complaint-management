<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users — CMS Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <span class="brand">&#9679; <span>CMS</span> — Admin Panel</span>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="page">
    <h2 class="page-title">All Users</h2>

    <div class="tbl-wrap">
        <table>
            <thead>
                <tr>
                    <th>#</th><th>Name</th><th>Email</th><th>Role</th><th>Registered</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.id}</td>
                    <td><strong>${u.name}</strong></td>
                    <td>${u.email}</td>
                    <td>
                        <span class="badge
                            ${u.role eq 'ADMIN' ? 'badge-CRITICAL' :
                              u.role eq 'STAFF' ? 'badge-ASSIGNED' : 'badge-LOW'}">
                            ${u.role}
                        </span>
                    </td>
                    <td>${u.createdAtFormatted}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
