<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Adjektiivi/Substantiivi-taivutin</title>
</head>
<body>
	<form action="taivutin" method="POST"> 
		Syötä substantiivi/adjektiivi perusmuodossa:  
		<input type="text" name="word" id="word"/> <br/>
		<p style="color:red">${error}</p>
	    <input type="submit", value="Taivuta"/>    		
	</form>
	
	<p>${partitive}</p>
        
    <a href="index.html">Takaisin</a>
</body>
</html>