<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register — CMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <div class="auth-card">
        <p class="logo">&#9679; Complaint Management System</p>
        <h2>Create Account</h2>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">${error}</div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="name" required value="${name}" placeholder="Your name">
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required value="${email}" placeholder="you@example.com">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required minlength="6" placeholder="Min 6 characters">
            </div>
            <div class="form-group">
                <label>Role</label>
                <select name="role">
                    <option value="CUSTOMER">Customer</option>
                    <option value="STAFF">Staff</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%; padding:.65rem;">
                Register
            </button>
        </form>

        <p style="text-align:center; margin-top:1.2rem; font-size:.88rem;">
            Already have an account?
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </p>
    </div>
</div>
</body>
</html>
