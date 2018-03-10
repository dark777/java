<%@ page language="java" import="java.sql.*" %>

<%

// ------  obtem os valores digitados pelo usuário
String Nome  = request.getParameter("txtnome");
String Endereco = request.getParameter("txtendereco");
String Estado  = request.getParameter("estado");
String Cidade  = request.getParameter("cidade");
String Bairro   = request.getParameter("txtbairro");
String Email  = request.getParameter("txtemail");
String Observacoes  = request.getParameter("txtmensagem");

out.print("<table border='0' align='center'>");

out.print("<tr>");
 out.print("<td><h3>Dados Gravados Com Sucesso!!! </td></h3>") ;
out.print("</tr>");

out.print("<br>");

//---------------------------------------------------------------
out.print("<tr>");
out.print("<td>Nome: "+ Nome + "<br></td>");
out.print("</tr>");

out.print("<tr>");
out.print("<td>Endereço: "+ Endereco + "<br></td>");
out.print("</tr>");

out.print("<tr>");
out.print("<td>Estado: "+ Estado + "<br> </td>");
out.print("</tr>");

out.print("<tr>");
out.print("<td>Cidade: "+ Cidade+ "<br> </td>");
out.print("</tr>");

out.print("<tr>");
out.print("<td>Bairro: "+ Bairro + "<br> </td>");
out.print("</tr>");

out.print("<tr>");
out.print("<td>E-Mail: "+ Email + "<br> </td>");
out.print("</tr>");
out.print("</table>");

//--------------------------------------------------------------

// ------ cria as variaveis para acessar o banco de dados
String Driver = "com.mysql.jdbc.Driver";
String url    = "jdbc:mysql://localhost:3306/Agenda" ;
String user   = "root";
String pass   = "root";


// ------ carrega o driver na memória
Class.forName(Driver);

// ------ cria a conexao com o banco de dados
Connection conexao = DriverManager.getConnection(url,user,pass);

// ------ cria o objeto statement para executar o comando do sql
Statement stm = conexao.createStatement();

// ------ prepara o comando sql (insert)
String sql    = "insert into Contatos values ('0','" + Nome + "','" + Endereco + "','" + Estado + "','" + Cidade + "','" + Bairro + "','" + Email + "','" + Observacoes + "')";                                                            


// ------ executa o comando sql usando o objeto statement
int linha     = stm.executeUpdate(sql);



if (linha > 0) {

out.print("<table border='0' align='center'>");
  out.print("<tr>");
out.print("<td><a href='Novo.html' title='Voltar'> <img src='Voltar.jpg'> </td>");
out.print("</tr>");

}else{
   out.print("Erro ao gravar") ;
}

out.print("</table>");

%>