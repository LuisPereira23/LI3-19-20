#ifndef __CATCLIENTES_H__
#define __CATCLIENTES_H__

#include "cliente.h"
#include "utils.h"

typedef struct catClientes* Cat_Clientes;

Cat_Clientes new_catClis(); // cria catalogo vazio
Cat_Clientes new_catClisList(List_String); // cria catalogo clientes, insere os nrs numa lista
void free_catClis(Cat_Clientes); // elimina catalogo de clientes

void insert_catClis(Cat_Clientes, Cliente); // insere clientes no catalogo
void insert_withdata_catClis(Cat_Clientes, Cliente, Data);
Data get_data_catClis(Cat_Clientes, Cliente);

Boolean existe_catClis(Cat_Clientes, Cliente); // existência dos clientes no catalogo
int tamanho_catClis(Cat_Clientes); // quantos clientes há no catalogo

List_String lista_completa_catClis(Cat_Clientes); // devolve lista de todos os clientes no catalogo
void add_to_lista_catClis(Cat_Clientes c, List_String l); // insere no catalogo os clientes contidos numa lista

#endif
