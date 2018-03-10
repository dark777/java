create database Agenda;

use Agenda;

create table Contatos

 (
      Codigo int not null auto_increment,
      Nome varchar(50) NOT NULL,
      Endereco varchar(50),
      Estado varchar(2),
      Cidade varchar(50),
      Bairro varchar(50),
      Email varchar(100),
      Observacoes varchar(300),
      constraint pk_Contatos primary key(Codigo));