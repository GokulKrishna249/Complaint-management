<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login — CMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-wrapper">
    <div class="auth-card">
        <p class="logo">&#9679; Complaint Management System</p>
        <h2>Sign In</h2>

        <% if ("registered".equals(request.getParameter("msg"))) { %>
            <div class="alert alert-success">Account created! You can now log in.</div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">${error}</div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required autofocus placeholder="you@example.com">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required placeholder="••••••••">
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%; padding:.65rem;">
                Login
            </button>
        </form>

        <p style="text-align:center; margin-top:1.2rem; font-size:.88rem;">
            No account?
            <a href="${pageContext.request.contextPath}/register">Register here</a>
        </p>

        <hr style="margin:1.5rem 0; border:none; border-top:1px solid #e2e8f0;">
        <p style="font-size:.78rem; color:#718096; text-align:center;">
            <strong>Demo accounts</strong><br>
            admin@cms.com / admin123 &nbsp;|&nbsp;
            alice@cms.com / staff123 &nbsp;|&nbsp;
            gokul@cms.com / customer123
        </p>
    </div>
</div>
</body>
</html>
