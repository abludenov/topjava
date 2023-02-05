<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<form method="POST" name="formAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}"/>"/><br/>
    Date time : <input name="datetime" required type="datetime-local" value="<c:out value="${meal.dateTime}"/>"/><br/>
    Description : <input name="description" value="<c:out value="${meal.description}"/>"/><br/>
    Calories : <input name="calories" required type="number" value="<c:out value="${meal.calories}"/>"/><br/>
    <input type="submit" value="Save"/>
</form>
</body>
</html>
