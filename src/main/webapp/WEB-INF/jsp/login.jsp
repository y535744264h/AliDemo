<html>

<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<title>Welcome to DEMO1</title>
</head>

<body>
<h1>Login to DEMO1</h1>
    <form method="post" action="${base}/login">
    	<table>
    	<tr><td>Login :</td><td><input type="text" name="name"></td></tr>
        <tr><td>Password :</td><td><input type="password" name="password"></td></tr> 
        <tr><td colspan="1"><input type="submit" name="submit" value="Login"></td></tr>
        </table>
    </form>
</body>

</html>
