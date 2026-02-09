<%@ page import="com.example.utils.DataEntity" %>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>emSigner Demo</title>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="./js/index.js"></script>
</head>
<%
	DataEntity dataentity = new DataEntity();
	Gson gson = new Gson();
	String reqJson = gson.toJson(dataentity);
%>
<script>
	let req = <%=reqJson%>
</script>
<body>
	<h1>emSigner Demo</h1>
    <form action="https://demosignergateway.emsigner.com/eMsecure/V3_0/Index" method="POST" accept-charset="UTF-8">
        <input type="text" id="Parameter1" name="Parameter1"/>
        <input type="text" id="Parameter2" name="Parameter2"/>
        <input type="text" id="Parameter3" name="Parameter3"/>
        <input type="submit" id="submit" name="submit"/>
    </form>
</body>
</html>