#include "navegador.h"
#include "interface.h"
#include "filial.h"
#include "leitura.h"
#include "facturacao.h"
#include "catProds.h"
#include "catClientes.h"
#include "facturacao.h"
#include "filial.h"
#include "queries.h"
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

// Struct SGV
struct sgv {
	Cat_Clientes catC;
	Cat_Prods catP;
	Facturacao faC;
	Filiais fiL;
	struct fileinfo
	{
	String pathProds,pathClientes,pathVendas;
	int totalProds,totalClientes,totalVendas;
	int tamanhoProds,tamanhoClientes,tamanhoVendas;
} info ;
};

// Aloca SGV e inicia navegador
SGV initSGV(){
	SGV sgv = (SGV) malloc(sizeof(struct sgv));
	start(sgv);
}

//destroySGV
void destroySGV(SGV sgv){
	if(sgv !=NULL){
		free_catClis(sgv->catC);
		free_catProds(sgv->catP);
		destroy_filiais(sgv->fiL);
		free_facturacao(sgv->faC);
		free(sgv);
	}
}

// Query 1
static void getCurrentFilesInfo(SGV sgv){
		int total;
		char pathProds[50];
	    char pathClientes[50];
	    char pathVendas[50];

	menu_query1(pathProds, pathClientes, pathVendas);


	sgv->info.pathProds = pathProds;
	sgv->info.pathClientes = pathClientes;
	sgv->info.pathVendas = pathVendas;

	sgv->catP = new_catProds();
	total = fileToCatalogoProdutos(pathProds, sgv->catP);
	sgv->info.totalProds = total;
	sgv->info.tamanhoProds = tamanho_catProds(sgv->catP);


	sgv->catC = new_catClis();
	total = fileToCatalogoClientes(pathClientes, sgv->catC);
	sgv->info.totalClientes = total;

	sgv->info.tamanhoClientes = tamanho_catClis(sgv->catC);

	List_String listaProdutos = lista_completa_catProds(sgv->catP);

	sgv->faC = new_facturacao(listaProdutos);
	destroy_listString(listaProdutos);

	List_String vendas = new_listString();
	total  = fileToListStringVendas(pathVendas, vendas, sgv->catP, sgv->catC);
	sgv->info.totalVendas = total;

	sgv->info.tamanhoVendas = tamanho_listString(vendas);

	sgv->fiL = new_filiais(NBFILIAIS);

	for(int i = 0 ; i < sgv->info.tamanhoVendas ; i++){
		String vendastr = get_listString(vendas, i);
		Venda venda = newVenda(vendastr);
		add_venda_facturacao(sgv->faC, venda);
		int filial = getFilial(venda);
		add_venda_filial(sgv->fiL,venda);
		destroyVenda(venda);
		free(vendastr);
	}

	destroy_listString(vendas); 

}


// Query 2
static void getProductsStartedByLetter(Cat_Prods catProds){
	char c;
	menu_query2(&c);

	List_String lista = query2(catProds, c);
	char titulo[] = "Produtos que comecam por a";
	titulo[25] = c;
	navegar_listString(lista, titulo);
	destroy_listString(lista);
}

// Query 3
static void getProductSalesAndProfit(Facturacao f){
	int filial, mes;
	String codigoProduto = malloc(7*sizeof(char));
	menu_query3(&filial, &mes, codigoProduto);

	int totalVendasN = 0, totalVendasP = 0;
	double totalFacturadoN = 0, totalFacturadoP = 0;

	query3(f, codigoProduto, filial, mes  ,'N', &totalVendasN, &totalFacturadoN);
	query3(f, codigoProduto, filial, mes  ,'P', &totalVendasP, &totalFacturadoP);
	print_query3(codigoProduto, filial, mes ,totalVendasN, totalFacturadoN, totalVendasP, totalFacturadoP);	
	free(codigoProduto);
}

// Query 4
static void getProductsNeverBought(Facturacao f){
	int filial;
    menu_query4(&filial);
	List_String lista = query4(f, filial);
	navegar_listString(lista, "Produtos que ninguem comprou");
	destroy_listString(lista);
}

// Query 5
static void getClientsOfAllBranches(Filiais f){
	List_String lista = query5(f);
	navegar_listString(lista, "Clientes que realisam compras em todas as filiais");
	destroy_listString(lista);
}

// Query 6
static void getClientsAndProcuctsNeverBoughtCount(Filiais f, Facturacao ff, Cat_Clientes allClientes){
	int clientesSemCompras;
	int produtosSemCompras;
	query6(ff, f, &clientesSemCompras, &produtosSemCompras, allClientes);
	print_query6(clientesSemCompras, produtosSemCompras);
}

// Query 7
static void getProductsBoughtByClient(Filiais f){
	String codigoCliente = malloc(6*sizeof(char));
	menu_query7(codigoCliente);


	int m = 12, n = 3;
	int** tabela = (int**) malloc  (m*sizeof(int*));
	for(int i = 0 ; i < 12 ; i++)
		tabela[i] = (int*) malloc(n*sizeof(int));

	query7(f, codigoCliente, tabela);
	print_query7(codigoCliente, tabela);

	for(int i = 0 ; i < 3 ; i++)
		free(tabela[i]);
	free(codigoCliente);
}

// Query 8
static void getSalesAndProfit(Facturacao f){
	
	int mesMin, mesMax;

	menu_query8(&mesMin, &mesMax);

	if (mesMin==0){
		return;
		menu();
	}

	int totalVendas, totalFacturado;

	query8(f, mesMin, mesMax, &totalVendas, &totalFacturado);

	printf("totalVendas: %d\n",totalVendas);
	printf("totalFacturado: %d\n",totalFacturado);

	print_query8 (mesMin, mesMax, totalVendas, totalFacturado);
	
}

// Query 9
static void getProductBuyers(Filiais f,Facturacao fa){
	int filial;
	String codigoProduto = malloc(7*sizeof(char));
	menu_query9(codigoProduto, &filial);

	List_String clientesN = new_listString();
	List_String clientesP = new_listString();

	int totalVendasN = 0, totalVendasP = 0;

	query9(f,fa,filial, codigoProduto,clientesN, clientesP,&totalVendasN,&totalVendasP);



	int numeroN = tamanho_listString(clientesN);
	int numeroP = tamanho_listString(clientesP);

	print_query9(totalVendasN, totalVendasP);

	navegar_listString(clientesN, "Clientes que mais compraram : N");
	navegar_listString(clientesP, "Clientes que mais compraram : P");
	destroy_listString(clientesN);
	destroy_listString(clientesP);
	free(codigoProduto);
	
}

// Query 10
static void getClientFavoriteProducts(Filiais f){
	int mes;
	String codigoCliente = malloc(6*sizeof(char));
	menu_query10(codigoCliente, &mes);
	
	List_String codigos = query10(f, mes, codigoCliente);

	navegar_listString(codigos, "Produtos mais comprados");
	destroy_listString(codigos);

	free(codigoCliente);
}

// Query 11
static void getTopSelledProducts(Facturacao ff, Filiais f){
	int n;
	menu_query11(&n);
	
	List_String lista = query11(ff, f, n);

	navegar_listString(lista, "Produtos mais vendidos");
	destroy_listString(lista);
}

// Query 12
static void getClientTopProfitProducts(Filiais f){
	String codigoCliente = malloc(6*sizeof(char));
	menu_query12(codigoCliente);

	char codigoProdutoUm[7];
	char codigoProdutoDois[7];
	char codigoProdutoTres[7];

	query12(f, codigoCliente, codigoProdutoUm, codigoProdutoDois, codigoProdutoTres);

	print_query12(codigoCliente, codigoProdutoUm, codigoProdutoDois, codigoProdutoTres);

	free(codigoCliente);
}

// Query 13
void infoFiles(SGV sgv){
	print_query1(sgv->info.pathProds, sgv->info.totalProds, sgv->info.tamanhoProds,sgv->info.pathClientes, sgv->info.totalClientes, sgv->info.tamanhoClientes,sgv->info.pathVendas, sgv->info.totalVendas, sgv->info.tamanhoVendas);
}

// Inicia menu e espera input de utilizador
void start(SGV sgv){

	String buffInput;
	do {	
		buffInput = menu();
		if(strequals(buffInput, "1") ){
			getCurrentFilesInfo(sgv);
		} else if(strequals(buffInput, "2") ){
			getProductsStartedByLetter(sgv->catP);
		} else if(strequals(buffInput, "3") ){
			getProductSalesAndProfit(sgv->faC);
		} else if(strequals(buffInput, "4") ){
			getProductsNeverBought(sgv->faC);
		} else if(strequals(buffInput, "5") ){
			getClientsOfAllBranches(sgv->fiL);
		} else if(strequals(buffInput, "6") ){
			getClientsAndProcuctsNeverBoughtCount(sgv->fiL, sgv->faC, sgv->catC);
		} else if(strequals(buffInput, "7") ){
			getProductsBoughtByClient(sgv->fiL);
		} else if(strequals(buffInput, "8") ){
			getSalesAndProfit(sgv->faC);
		} else if(strequals(buffInput, "9") ){
			getProductBuyers(sgv->fiL,sgv->faC);
		} else if(strequals(buffInput, "10") ){
			getClientFavoriteProducts(sgv->fiL);
		} else if(strequals(buffInput, "11") ){
			getTopSelledProducts(sgv->faC, sgv->fiL);
		} else if(strequals(buffInput, "12") ){
			getClientTopProfitProducts(sgv->fiL);
		}else if(strequals(buffInput, "13") ){
			infoFiles(sgv);
		} else if(strequals(buffInput, "0") ){
			destroySGV(sgv);
		} 
	}while(!strequals(buffInput, "0"));
}

