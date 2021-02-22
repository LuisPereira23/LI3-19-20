#include "filial.h"
#include "venda.h"
#include "produto.h"
#include "catClientes.h"
#include <glib.h>

/// tipos auxiliares

//
typedef struct compra{
	char codigoProduto[7];
	int quantidade;
	double precoUnit;
} *Compra;

//Cria compra
static Compra new_Compra(Venda v){
	Compra c = (Compra) malloc(sizeof(struct compra));
	if(c == NULL)
		return NULL;

	c->quantidade = getQuantidade(v);;
	c->precoUnit = getPrecoUnit(v);

	String codigoProduto = getCodProd(v);
	strcpy(c->codigoProduto, codigoProduto);
	free(codigoProduto);	

	return c;
}

//private compare func
static int compareCompras(Compra c1, Compra c2, Data d){
	int strCompar = strcmp(c1->codigoProduto, c2->codigoProduto);
	if(strCompar!=0)
		return strCompar;

	int quantidadeCompar = c1->quantidade - c2->quantidade;
	if(quantidadeCompar!=0)
		return strCompar;

	return c1->precoUnit - c2->precoUnit;
}

////////////
typedef struct filial{
	Cat_Clientes clientes[12][2];
} *Filial;

// Cria Filial
static Filial new_Filial(){
	Filial f = (Filial) malloc(sizeof(struct filial));
	for(int i = 0 ; i < 12 ; i++ )
		for(int j = 0 ; j < 2 ; j++ )
				f->clientes[i][j] = new_catClis();
		
	return f;
}

void destroy_filial(Filial f){
	for (int i = 0; i < 12; i++)
	{
		for (int j = 0; i < 2; j++)
		{
			free(f->clientes[i][j]);
		}
	}
}
////////////

struct filiais{
	Filial* filiais;
	int nbFiliais;
};

// Cria Filiais
Filiais new_filiais(int nb){
	Filiais fs = (Filiais) malloc(sizeof(struct filiais));
	fs->filiais = (Filial*) malloc(nb*sizeof(struct filial));
	fs->nbFiliais = nb;
	for (int i = 0 ; i < nb ; i++)
		fs->filiais[i] = new_Filial();
	return fs;
}

// Free de filiais
void destroy_filiais(Filiais f){
	int nb = f-> nbFiliais;
	for (int i = 0 ; i < nb ; i++){
		free(f->filiais[i]);
	}
	free(f);
}

// Adiciona venda a uma filial
Boolean add_venda_filial(Filiais fs, Venda v){
	int filial = getFilial(v);

	if(filial > fs->nbFiliais)
		return false;
	Filial f = fs->filiais[filial-1];
	
	String stringCliente = getCodCli(v);
	Cliente cl = newCliente(stringCliente);
	int mes = getMes(v);
	int tipo;
	if(getTipo(v) == 'N')
		tipo = 0;
	else
		tipo = 1;
	Cat_Clientes clientes = f->clientes[mes-1][tipo];
	
	GSequence* compras;
	if(!existe_catClis(clientes, cl)){
		compras = g_sequence_new(free);
		insert_withdata_catClis(clientes, cl, compras);
	} else {
		compras = (GSequence*) get_data_catClis(clientes, cl);
	}

	Compra compra = new_Compra(v);

	g_sequence_insert_sorted(compras, compra,(GCompareDataFunc) compareCompras, NULL);	

	free(cl);
	free(stringCliente);
	
	return true;
}


// aux clientes todas filiais
static List_String cat_clientes_filial(Filial f){
	List_String l = new_listString();	
	for(int i = 0 ; i < 12 ; i++ )
		for(int j = 0 ; j < 2 ; j++ )
			add_to_lista_catClis(f->clientes[i][j], l);
	return l;
}

//Lista de todos os clientes em todas filiais
List_String clientes_en_todas_filiais(Filiais fs){
	List_String todosClientesFilial1str = cat_clientes_filial(fs->filiais[0]);
	List_String todosClientesFilial2str = cat_clientes_filial(fs->filiais[1]);
	List_String todosClientesFilial3str = cat_clientes_filial(fs->filiais[2]);
	Cat_Clientes c1 = new_catClisList(todosClientesFilial2str);
	Cat_Clientes c2 = new_catClisList(todosClientesFilial2str);
	Cat_Clientes c3 = new_catClisList(todosClientesFilial3str);
	
	
	List_String todosClientesFilial1strFiltrados =  lista_completa_catClis(c1);
	List_String toReturn = new_listString();
	for(int i = 0 ; i < tamanho_listString(todosClientesFilial1strFiltrados) ; i++ ){
		String s = get_listString(todosClientesFilial1strFiltrados, i);
		Cliente cl = newCliente(s);
		if(existe_catClis(c2,cl) && existe_catClis(c3,cl))
			insert_listString(toReturn, s);
		free(cl);
	}

	free_catClis(c1);
	free_catClis(c3);
	free_catClis(c2);
	destroy_listString(todosClientesFilial1str);
	destroy_listString(todosClientesFilial2str);
	destroy_listString(todosClientesFilial3str);
	return toReturn;
}

// Lista de clientes sem compras nas filiais
List_String clientes_sem_compras(Filiais fs, Cat_Clientes allClientes){
	List_String todosClientesFilial1str = cat_clientes_filial(fs->filiais[0]);
	List_String todosClientesFilial2str = cat_clientes_filial(fs->filiais[1]);
	List_String todosClientesFilial3str = cat_clientes_filial(fs->filiais[2]);
	Cat_Clientes c1 = new_catClisList(todosClientesFilial1str);
	Cat_Clientes c2 = new_catClisList(todosClientesFilial2str);
	Cat_Clientes c3 = new_catClisList(todosClientesFilial3str);

	List_String todosClientes =  lista_completa_catClis(allClientes);
	List_String toReturn = new_listString();
	for(int i = 0 ; i < tamanho_listString(todosClientes) ; i++ ){
		String s = get_listString(todosClientes, i);
		Cliente cl = newCliente(s);
		if(!existe_catClis(c1,cl)&&!existe_catClis(c2,cl) && !existe_catClis(c3,cl))
			insert_listString(toReturn, s);
		free(cl);
	}


	free_catClis(c1);
	free_catClis(c3);
	free_catClis(c2);
	destroy_listString(todosClientesFilial1str);
	destroy_listString(todosClientesFilial2str);
	destroy_listString(todosClientesFilial3str);

	return toReturn;
}

// Compras feitas por um cliente em certa filial 
int compras_cliente(Filiais fs, String codigoCliente, int filial, int mes, char modo){
	Filial f = fs->filiais[filial];

	Cliente cl = newCliente(codigoCliente);

	GSequence* compras;
	int modoInt = (modo == 'N' ? 0 : 1);
	Cat_Clientes clientes = f->clientes[mes][modoInt];
	compras = (GSequence*) get_data_catClis(clientes, cl);
	free(cl);

	if(compras == NULL)
		return 0;
	return g_sequence_get_length(compras);
}


