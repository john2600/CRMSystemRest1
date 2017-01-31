<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>All Customers</title>
	</head>
	<body>
		<h1>All Customers Mostrar todos los usuarios</h1>
		<em>This is a very poor layout - see the SpringMVC course for a much better one!</em>
		<table border=1>
			<tr>
				<th>Id</th>
				<th>Title</th>
				<th>Authors</th>
				
			</tr>
			<c:forEach items="${customers}" var="customer">
				<tr>
					<td>${customer.customerId}</td>
					<td>${customer.companyName}</td>
					<td>${customer.notes}</td>
				</tr>
			</c:forEach>
	 	<table>
	</body>
</html>