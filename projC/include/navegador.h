#ifndef __NAVEGADOR_H__
#define __NAVEGADOR_H__

#include "utils.h"
#include "catProds.h"

#define PATH_CLIENTES "src/txtFiles/Clientes.txt"
#define PATH_PRODUTOS "src/txtFiles/Produtos.txt"
#define PATH_VENDAS "src/txtFiles/Vendas_1M.txt"

#define ELEMENTOS_POR_PAGINA 20

String menu(void);
void menu_query1(String pathProds, String pathClientes,	String pathVendas);
void menu_query2(char* letra);
void menu_query3(int* filial, int* mes, String codigoProduto);
void menu_query4(int* filial);
void menu_query7(String codigoCliente);
void menu_query8(int* mesMin, int* mesMax);
void menu_query9(String codigoProduto, int* filial);
void menu_query10(String codigoCliente, int* mes);
void menu_query11(int* n);
void menu_query12(String codigoCliente);
///////////////////////////////////////////////7

void print_query1 (String pathProds,int totalProds,int tamanhoProds,String pathClientes,int totalClientes,int tamanhoClientes,String pathVendas,int totalVendas,int tamanhoVendas);
void print_query3(String produto, int filial, int mes ,int totalVendasN, double totalFacturadoN, int totalVendasP, double totalFacturadoP);
void print_query6(int clientesSemCompras, int produtosSemCompras);
void print_query7(String codigoCliente, int** tabela);
void print_query8 (int mesMin, int mesMax, int totalVendas, double totalFacturado);
void print_query9(int numeroN, int numeroP);
void print_query12(String codigoCliente, String codigoProdutoUm, String codigoProdutoDois, String codigoProdutoTres);
void invalid();

void navegar_listString(List_String lista, String titlo);

#endif
