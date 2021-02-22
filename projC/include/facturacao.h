#ifndef __FACTURACAO_H__
#define __FACTURACAO_H__

#include "utils.h"
#include "venda.h"

typedef struct facturacao* Facturacao;

void free_facturacao (Facturacao); // destrói módulo faturação
Boolean add_venda_facturacao (Facturacao f, Venda v); //insere informação da venda no módulo

int total_vendas_full (Facturacao f, Produto p, int mes, int filial, char modo); // nr vendas produto num determinado mês, filial e tipo de venda
double total_facturado_full (Facturacao f, Produto p, int mes, int filial, char modo); // total faturado produto num terminado tempo, filial e tipo de venda
void total_vendas_facturado (Facturacao f, int mes, int* vendas, double* facturado); // nr vendas e total faturado todos os produtos num determinado mês, filial e tipo de venda
Facturacao new_facturacao(List_String listaProdutos); // cria módulo faturação vazio


List_String produtos_nao_comprados(Facturacao f, int filial); // lista produtos não comprados

//getters

Cat_Prods getCatProd(Facturacao f);

#endif
