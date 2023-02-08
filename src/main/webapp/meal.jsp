<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${param.action == 'save' ? 'Save meal' : 'Update meal'}</title>
</head>
<body>
<form method="POST" name="formAddMeal">
    <input type="text" readonly="readonly" hidden name="id" value="${meal.id}"/><br/>
    Date time : <input name="datetime" required type="datetime-local" value="${meal.dateTime}"/><br/>
    Description : <input name="description" value="${meal.description}"/><br/>
    Calories : <input name="calories" required type="number" value="${meal.calories}"/><br/>
    <input type="submit" value="Save"/>
    <input type="button" value="Cancel" onClick='location.href="meals"'/>
</form>
</body>
</html>
