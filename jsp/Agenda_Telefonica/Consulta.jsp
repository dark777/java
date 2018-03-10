<%@ page language="java" import="java.sql.*" %>


<html>
<head>
<title>Pesquisar Contato</title>

<table border="2" align="center" borderColor="gray" cellpadding="0" cellspacing="2" width="100%">

<td bgcolor="gray"><b>Código</td></b>
<td bgcolor="gray"><b>Nome</td></b>
<td bgcolor="gray"><b>Endereço</td></b>
<td bgcolor="gray"><b>Estado</td></b>
<td bgcolor="gray"><b>Cidade</td></b>
<td bgcolor="gray"><b>Email</td></b>

<%

String driver= "com.mysql.jdbc.Driver" ;
String url   = "jdbc:mysql://localhost/Agenda" ;
String user  = "root" ;
String pass  = "root" ;



String vnome = request.getParameter("Pegar") ;


String sql ;

if (vnome == "null") {

sql = "select * from Contatos";

} else {

sql = "select * from Contatos where nome like '" + vnome + "%' ";

}

Class.forName(driver);
Connection conexao = DriverManager.getConnection(url,user,pass);
Statement stm = conexao.createStatement();
ResultSet resultado = stm.executeQuery(sql) ;


out.print("<center>");
out.print("<h1>Resultado da Pesquisa</h1>");
out.print("</center>");


 while ( resultado.next() ) {

 out.print( "<tr>" ) ;
 out.print( "<td>" ) ;
 out.print( resultado.getString("Codigo") ) ;

 out.print( "<td>" ) ;
 out.print( resultado.getString("Nome") ) ;
   
 out.print( "<td>" ) ;
 out.print( resultado.getString("Endereco") ) ;
 out.print( "</td>" ) ;

 out.print( "<td>" ) ;
 out.print( resultado.getString("Estado") ) ;

 out.print( "<td>" ) ;
 out.print( resultado.getString("Cidade") ) ;

 out.print( "<td>" ) ;
 out.print( resultado.getString("Email") ) ;

}

%>

</table>

<center>
<a href="Index.html" title="Menu Principal"><img src="Voltar.jpg">
</center>

</body>
</html>
