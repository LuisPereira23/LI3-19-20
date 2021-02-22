#ifndef __QUERIES_H__
#define __QUERIES_H__

#include "utils.h"
#include "catClientes.h"
#include "catProds.h"
#include "filial.h"
#include "facturacao.h"

List_String query2(Cat_Prods catProds, char letra);
void query3(Facturacao f, String codigoProduto, int filial, int mes, char modo, int* tVendas, double* tFacturado);
List_String query4(Facturacao f, int filial);
List_String query5(Filiais f);
void query6(Facturacao ff, Filiais f, int* clientesSemCompras, int* produtosSemCompras,  Cat_Clientes allClientes);
void query7(Filiais f, String codigoCliente, int** tabela);
void query8(Facturacao f, int mesMin, int mesMax, int* totalVendas, int* totalFacturado);
void query9(Filiais f,Facturacao fa, int filial, String codigoProduto, List_String listaN, List_String listaP,int* comprasN,int* comprasP);
List_String query10(Filiais f, int mes, String codigoCliente);
List_String query11(Facturacao ff, Filiais f, int n);
void query12(Filiais f, String codigoCliente, String codigoProdutoUm, String codigoProdutoDois, String codigoProdutoTres);

#endif
