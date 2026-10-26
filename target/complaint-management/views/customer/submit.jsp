<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit Complaint — CMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <span class="brand">&#9679; <span>CMS</span> — Customer Portal</span>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/customer/dashboard">My Complaints</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="page">
    <h2 class="page-title">Submit a New Complaint</h2>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">${error}</div>
    <% } %>

    <div class="card" style="max-width:640px;">
        <form method="post" action="${pageContext.request.contextPath}/customer/submit">

            <div class="form-group">
                <label>Title</label>
                <input type="text" name="title" required maxlength="200"
                       placeholder="Brief summary of your issue">
            </div>

            <div class="form-group">
                <label>Category</label>
                <select name="category" required>
                    <option value="">— Select —</option>
                    <option value="Network">Network</option>
                    <option value="Billing">Billing</option>
                    <option value="Technical">Technical</option>
                    <option value="Service">Service</option>
                    <option value="Other">Other</option>
                </select>
            </div>

            <div class="form-group">
                <label>Priority</label>
                <select name="priority">
                    <option value="LOW">Low</option>
                    <option value="MEDIUM" selected>Medium</option>
                    <option value="HIGH">High</option>
                    <option value="CRITICAL">Critical</option>
                </select>
            </div>

            <div class="form-group">
                <label>Description</label>
                <textarea name="description" rows="5" required
                          placeholder="Describe your issue in detail…"></textarea>
            </div>

            <div style="display:flex; gap:1rem;">
                <button type="submit" class="btn btn-primary">Submit Complaint</button>
                <a href="${pageContext.request.contextPath}/customer/dashboard"
                   class="btn btn-outline">Cancel</a>
            </div>

        </form>
    </div>
</div>
</body>
</html>
