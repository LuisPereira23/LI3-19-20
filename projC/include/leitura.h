#ifndef __LEITURA_H__
#define __LEITURA_H__

#include "utils.h"
#include "catProds.h"
#include "catClientes.h"

int fileToCatalogoProdutos(String path, Cat_Prods c);
int fileToCatalogoClientes(String path, Cat_Clientes c);
int fileToListStringVendas(String path, List_String vendas, Cat_Prods catProds, Cat_Clientes catClientes);

#endif
