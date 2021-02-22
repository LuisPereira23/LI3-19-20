#ifndef __FILIAL_H__
#define __FILIAL_H__

#include "venda.h"

#define NBFILIAIS 3

typedef struct filiais *Filiais;

Filiais new_filiais(int nbFiliais); // cria módulo filial vazio
void destroy_filiais(Filiais); // destrói módulo de filial
Boolean add_venda_filial(Filiais, Venda); // insere informações venda no módulo

int compras_cliente(Filiais fs, String codigoCliente, int filial, int mes, char modo); // nr compras do cliente "x" na filial "n"

List_String clientes_en_todas_filiais(Filiais fs); // clientes que compraram em todas as filiais
List_String clientes_sem_compras(Filiais fs, Cat_Clientes allClientes); // clientes que não compraram em nenhuma filial

#endif
