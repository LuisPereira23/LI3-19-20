#ifndef __CATPRODS_H__
#define __CATPRODS_H__

#include "produto.h"
#include "utils.h"
#include <glib.h>

typedef struct catProds* Cat_Prods;

Cat_Prods new_catProds(); // cria catalogo vazio
void free_catProds(Cat_Prods); // elimina catalogo de produtos

void insert_catProds(Cat_Prods, Produto); // insere produtos no catalogo
void insert_withdata_catProds(Cat_Prods, Produto, Data);
Data get_data_catProds(Cat_Prods, Produto);

Boolean existe_catProds(Cat_Prods, Produto); // existência do produto no catalogo
int tamanho_catProds(Cat_Prods); // quantos produtos há no catalogo

List_String lista_por_letra_catProds(Cat_Prods, char); // devolve lista com produtos que começam por determinada letra
List_String lista_completa_catProds(Cat_Prods); // devolve lista de todos os produtos no catalogo

void foreach_catProds(Cat_Prods, GFunc, Data);// aplica o foreach da GLib

#endif
