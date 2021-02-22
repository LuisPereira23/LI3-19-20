#include "interface.h"
#include "navegador.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include "utils.h"

// Auxiliar espacos
static void printEspacos(){
	for (int i = 0 ; i < 4 ; i++)
		printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
}

//Auxiliar fazer print barras
static void printBarra(){
	printf("------------------------------------\n");
}

//Auxiliar print barras laterais
static void printLateral(){
	printf("////////////////////////////////////////////////////////////\n");
}

// Auxiliar print Titulo
static void printVenda(){
	printf("///        ########     #########   #           #        ///\n");
	printf("///        #            #       #    #         #         ///\n");
	printf("///        #            #             #       #          ///\n");
	printf("///        ########     #              #     #           ///\n");
	printf("///               #     #     ###       #   #            ///\n");
	printf("///               #     #       #        # #             ///\n");
	printf("///        ########     #########         #              ///\n");
}


// Menu a ser apresentado
String menu(void){
	printEspacos();
	printEspacos();
	printLateral();
	printVenda();
	printLateral();
	printLateral();
	printf("///                       SGV Menu                       ///\n");
	printf("///  1  : Load ficheiros                                 ///\n");
	printf("///  2  : Produtos que comecam por...                    ///\n");
	printf("///  3  : Numero de vendas/Total facturado.              ///\n");
	printf("///  4  : Produtos que ninguem comprou.                  ///\n");
	printf("///  5  : Clientes que compraram em todas as filiais.    ///\n");
	printf("///  6  : Produtos sem vendas e clientes sem compras.    ///\n");
	printf("///  7  : Tabela produtos comprados.                     ///\n");
	printf("///  8  : Total vendas e facturado entre intervalo.      ///\n");
	printf("///  9  : Clientes que compraram um certo produto.       ///\n");
	printf("///  10 : Produtos mais comprado por um certo cliente.   ///\n");
	printf("///  11 : Produtos mais vendidos.                        ///\n");
	printf("///  12 : Produtos mais caros para um certo cliente.     ///\n");
	printf("///  13 : Resultados de Leitura                          ///\n");
	printf("///  0  : Quit.                                          ///\n");
	printLateral();
	printf("> ");
	return userInput();
}

// Menus apresentados para requisitar input das varias queries
void menu_query1(String pathProds, String pathClientes,	String pathVendas){
	String input;	

	printf("\nName of the Clientes File (Nothing for the default file) : ");
	input = userInput();
	if(strempty(input)){
		strcpy(pathClientes, PATH_CLIENTES);
	} else{
		strcpy(pathClientes, input);
	}

	printf("Name of the Produtos File (Nothing for the default file) : ");
	input = userInput();
	if(strempty(input)){
		strcpy(pathProds, PATH_PRODUTOS);
	} else{
		strcpy(pathProds, input);
	}

	printf("Name of the Vendas File (Nothing for the default file) : ");
	input = userInput();
	if(strempty(input)){
		strcpy(pathVendas, PATH_VENDAS);
	} else{
		strcpy(pathVendas, input);
	}
	printf("\n");
}

void menu_query2(char* letra){	
	printf("Enter a char : ");
	String input = userInput();
	if(strlen(input) > 1)
		*letra = 'A';
	else if (!isalpha(input[0]))
		*letra = 'A';
	else
		*letra = toupper(input[0]);
}


void menu_query3(int* filial, int* mes, String codigoProduto){
	String input;
	
	printf("Codigo Produto : ");
	input = userInput();
	strcpy(codigoProduto, input);

	printf("Mes : ");
	input = userInput();
	if(input[0] =='1' && (input[1] == '0' || input[1] == '1' ||input[1] == '2' )){
		*mes = (input[0] - '0')*10;
		*mes += input[1] - '0';
	} 
	else{
	*mes = input[0] - '0';
	}
	

	printf("Filial (1-2-3, 4 = TODAS) : ");
	input = userInput();
	*filial = input[0] - '0';
}

void menu_query4(int* filial){
	printf("Filial (1-2-3, 4 = TODAS) : ");
	String input = userInput();
	*filial = input[0] - '0';
}

void menu_query7(String codigoCliente){
	String input;
	
	printf("Codigo Cliente : ");
	input = userInput();
	strcpy(codigoCliente, input);
}

void menu_query8(int* mesMin, int* mesMax){
	String input;
	
	printf("Mes Inicio : ");
	input = userInput();

	if(input[0] =='-') {
		invalid();
		*mesMin=0;
	    *mesMax=0;}

		else{

			if(input[0] =='1' && (input[1] == '0' || input[1] == '1' ||input[1] == '2' )){
		*mesMin = (input[0] - '0')*10;
		*mesMin += input[1] - '0';
	} 

				else{
		*mesMin = input[0] - '0';
	}

	printf("Mes Final : ");
	input = userInput();

	if(input[0] =='-') {
		invalid();
		*mesMin=0;
	    *mesMax=0;}

	    else{
	if(input[0] =='1' && (input[1] == '0' || input[1] == '1' ||input[1] == '2' )){
		*mesMax = (input[0] - '0')*10;
		*mesMax += input[1] - '0';
	} 
			else{
		*mesMax = input[0] - '0';
	}
}
}
if(*mesMin > *mesMax){
	invalid();
	*mesMin=0;
	*mesMax=0;
}
}


void menu_query9(String codigoProduto, int* filial){
	printf("Codigo Produto : ");
	String input = userInput();
	strcpy(codigoProduto, input);
	
	printf("Filial (1-2-3, 4 = TODAS) : ");
	input = userInput();
	*filial = input[0] - '0';
}

void menu_query10(String codigoCliente, int* mes){
	printf("Codigo Cliente : ");
	String input = userInput();
	strcpy(codigoCliente, input);

	printf("Mes : ");
	input = userInput();
	if(input[0] =='1' && (input[1] == '0' || input[1] == '1' ||input[1] == '2' )){
		*mes = (input[0] - '0')*10;
		*mes += input[1] - '0';
	} 
	else{
		*mes = input[0] - '0';
	}
}

void menu_query11(int* n){
	printf("N : ");
	String input = userInput();
	if(input[2] !='\0'){
		*n = (input[0] - '0')*100;
		*n += (input[1] - '0')*10;
		*n += input[2] - '0';
	} 
	else if(input[1] !='\0'){
		*n = (input[0] - '0')*10;
		*n += input[1] - '0';
	} 
	else{
		*n = input[0] - '0';
	}
}

void menu_query12(String codigoCliente){
	printf("Codigo Cliente : ");
	String input = userInput();
	strcpy(codigoCliente, input);
}

// Apresentacao do resultado das queries

void print_query1(String pathProds,int totalProds,int tamanhoProds,String pathClientes,int totalClientes,int tamanhoClientes,String pathVendas,int totalVendas,int tamanhoVendas){

	printf("Produtos : File \"%s\" read, %d lines analised and %d lines ok.\n", pathProds,totalProds, tamanhoProds);
	printf("Clientes : File \"%s\" read, %d lines analised and %d lines ok.\n", pathClientes, totalClientes, tamanhoClientes);
	printf("Vendas : File \"%s\" read, %d lines analised and %d lines ok.\n", pathVendas, totalVendas, tamanhoVendas);

	printf("\nPress anything to continue...");
	userInput();
}

void print_query3(String produto, int filial, int mes ,int totalVendasN, double totalFacturadoN, int totalVendasP, double totalFacturadoP){
	printEspacos();
	printBarra();
	
	if(filial == 4)
		printf("Produto : %s | Mes : %d | Filial : Todas ", produto, mes);
	else
		printf("Produto : %s | Mes : %d | Filial : %d ", produto, mes, filial);

	printf("\nTotal Vendas N : %d\n", totalVendasN);
	printf("Total Vendas P : %d\n", totalVendasP);
	printf("Total Facturado N : %.2f\n", totalFacturadoN);
	printf("Total Facturado P : %.2f\n", totalFacturadoP);

	printf("\nTotal facturado : %.2f | Total vendas : %d", (totalFacturadoN+totalFacturadoP) , (totalVendasN+ totalVendasP));
	
	printf("\nPress anything to continue...");
	userInput();
}

void print_query6(int clientesSemCompras, int produtosSemCompras){
	printEspacos();
	printBarra();
	printf("\nClientes sem compras : %d | Produtos sem vendas : %d ", clientesSemCompras, produtosSemCompras);
	printf("\nPress anything to continue...");
	userInput();
}

void print_query7(String codigoCliente, int** tabela){
	printEspacos();
	printBarra();
	printf("\n\nCliente : %s\n\n", codigoCliente);

	int totalFilialUm = 0, totalFilialDois = 0, totalFilialTres = 0;
	printf("Filial :    1    2    3    T\n\n");
	for (int i = 0 ; i < 12 ; i++){
		if(i <=8)
			printf("Mes %d ", (i+1));
		else
			printf("Mes %d", (i+1));
		printf(" :    %d    %d    %d    %d\n" , tabela[i][0], tabela[i][1], tabela[i][2], tabela[i][3]);
		totalFilialUm += tabela[i][0];
		totalFilialDois += tabela[i][1];
		totalFilialTres += tabela[i][2];
	}
	printf(" T     :   %d    %d    %d    %d\n", totalFilialUm, totalFilialDois, totalFilialTres, (totalFilialUm+totalFilialDois+totalFilialTres));
	printf("\n\nPress anything to continue...");
	userInput();
}

void print_query8 (int mesMin, int mesMax, int totalVendas, double totalFacturado){
	printEspacos();
	printBarra();
	printf("\n\nMes %d ate o mes %d : Compras %d | Total Facturado : %.2f.\n\n", mesMin, mesMax, totalVendas, totalFacturado);
	printf("\n\nPress anything to continue...");
	userInput();
}

void print_query9(int numeroN, int numeroP){
	printEspacos();
	printBarra();
	printf("\n\nNumero total de compras : %d.\nCompras em modo N : %d.\nCompras em modo P : %d.\n", numeroN+numeroP, numeroN, numeroP);
	printf("\n\nVoce vai ver em primeiro a lista do modo N e depois a lista do modo P.\n");
	printf("\n\nPress anything to continue...");
	userInput();
}

void print_query12(String codigoCliente, String codigoProdutoUm, String codigoProdutoDois, String codigoProdutoTres){
	printEspacos();
	printBarra();
	printf("\n\nArtigos onde Cliente %s gasto mais dinheiro : \n", codigoCliente);
	printf("1 : %s\n", codigoProdutoUm);
	printf("2 : %s\n", codigoProdutoDois);
	printf("3 : %s\n", codigoProdutoTres);
	printf("\n\nPress anything to continue...");
	userInput();
}

void invalid(){
	printf("\n\nInvalid Input");
	printf("\n\nPress anything to continue...");
	userInput();
}

// Navegador das list strings

void navegar_listString(List_String lista, String titulo){
	int pagina = 1;
	int total = tamanho_listString(lista);
	int nbPaginas = (total/ELEMENTOS_POR_PAGINA)+1;

	String buffInput;

	do{
		printEspacos();
		printEspacos();
		printEspacos();
		printEspacos();
		printf("%s\n\n", titulo);
		printBarra();
		for(int i = 1 ; i <= ELEMENTOS_POR_PAGINA ; i++){
			String s = get_listString(lista, i+ELEMENTOS_POR_PAGINA*(pagina-1)-1);
			if(s != NULL)
				printf("%s\n", s);
		}
		printBarra();
		int min = 1 + ELEMENTOS_POR_PAGINA*(pagina-1);
		int max = ( ELEMENTOS_POR_PAGINA*(pagina) > total ? total : ELEMENTOS_POR_PAGINA*(pagina) );
		printf("Pagina : %d/%d | Elementos : %d-%d | Total : %d\n", pagina, (total/ELEMENTOS_POR_PAGINA)+1, min, max, total);
		printf("[n = next page, p = precedent page, q = quit] > ");
		
		buffInput = userInput();
		if(strequals(buffInput, "n") && pagina < nbPaginas)
			pagina++;
		else if(strequals(buffInput, "p") && pagina > 1 )
			pagina--;
		
	}while(!strequals(buffInput, "q"));
}
