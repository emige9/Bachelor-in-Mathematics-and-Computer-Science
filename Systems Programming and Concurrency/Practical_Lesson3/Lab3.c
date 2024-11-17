#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "bst.h"

/**
 * ask SIZE to the user, and create a binary file with random SIZE unsigned values.
 * Use srand(time(NULL)) at the beginning, and use rand()%SIZE to create a random number between 0
 * and SIZE-1
 */
void createFile(char* filename){

	FILE * f = fopen(filename, "wb");

	if(f == NULL){
		perror("I cannot open the file");
	}

	srand(time(NULL));
	printf("How many numbers do you want in the file?\n");
	unsigned int size;
	scanf("%u", &size);

	for(int i=0; i<size; i++){
		int value = rand()%size;
		fwrite(&value, sizeof(int), 1, f);
	}
	fclose(f);

}
/**
 * Show the contents of the file in the screen (the list of unsigned values stored in the file)
 */
void showFile(char* filename){

	FILE * f = fopen(filename, "rb");

	if(f == NULL){
		perror("The file cannot be opened");
	}

	int value;

	while(fread(&value, sizeof(int), 1, f)!=0){
		printf("%i ", value);
	}
	fclose(f);

}

/**
 * Store in the tree the values read from the file)
 */

void loadFile(char* filename, TTree* tree){

	FILE * f = fopen(filename, "rb");

	if(f == NULL){
		perror("Something bad happened");
	}

	unsigned value;

	while(fread(&value, sizeof(int), 1, f)!=0){
		insert(tree, value);
	}

	fclose(f);

}

int main(void) {

	setvbuf(stdout,NULL,_IONBF,0);

	char fname[50];
	printf ("Enter the file name:\n");
	fflush(stdout);
	scanf ("%s",fname);
	fflush(stdin);
	createFile(fname);
	printf("\n Now, we read its contents and show them\n");
	showFile(fname);
	fflush(stdout);

	printf ("\n Now, we load the contents in the tree\n");
	TTree mytree;
	create (&mytree);
	loadFile(fname,&mytree);
	printf ("\n Now we show the ordered values in the tree\n");
	show(mytree);
	fflush(stdout);
	printf("\n Now we write the ordered values\n");
	FILE * fd;
	fd = fopen (fname, "wb");
	save(mytree, fd);
	fclose (fd);
	printf("\n Finally, we show the ordered values in the file\n");
	showFile(fname);
	destroy (&mytree);

	return EXIT_SUCCESS;
}
