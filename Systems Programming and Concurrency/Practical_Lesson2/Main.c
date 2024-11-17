/*
 * Main.c
 *
 *  Created on: 1 mar 2023
 *      Author: Usuario
 */

#include <stdio.h>
#include <stdlib.h>

const unsigned int delta = 0x9e3779b9;

void decrypt(unsigned int* v, unsigned long* k){
	int sum = 0xC6EF3720;

	for(int i=0; i < 32; i++){

		v[1] -= ((v[0]<<4) + k[2])^(v[0] + sum)^((v[0]>>5) + k[3]);

		/*
		 * Subtract to v[1]: (v[0] left shifted 4 bits + k[2])
					XOR
					(v[0] + sum)
					XOR
					((v[0] right shifted 5 bits) + k[3]) */

		v[0] -= ((v[1]<<4) + k[0])^(v[1] + sum)^((v[1]>>5) + k[1]);
		/* Subtract to v[0]: (v[1] left shifted 4 bits + k[0])
					XOR
					(v[1] + sum)
					XOR
					((v[1] right shifted 5 bits) + k[1])*/

		sum -= delta;
		/* Subtract to sum the value of delta.
		  */

	}
}


int main(){

	FILE *in = fopen("image01.png", "rb");

	if (in==NULL){
		perror("I can not open image01.png");
	}

	unsigned int finalSize; // the size of the final decyphered file
	fread(&finalSize, sizeof(unsigned int), 1, in);
	int numOfBlocks;

	if((finalSize%8) == 0){
		numOfBlocks = finalSize/8;
	} else {
		numOfBlocks = (finalSize/8) + 1;
	}

	unsigned int * buffer = (unsigned int *)malloc(numOfBlocks*8);
	//we read the file
	fread(buffer, 8, numOfBlocks, in);  //buffer contains image01.png - (size of the original file)
	fclose(in);
	unsigned long k[4] = {128, 129, 130, 131};

	for(int i=0; i < numOfBlocks; i++){
		decrypt(buffer + i*2,k);
	}
	// to write the final file
	FILE *out=fopen("decryptedImage.png", "wb");

	if(out==NULL){
		perror("We can not open decryptedImage.png");
	}
	fwrite(buffer, 1, finalSize, out);
	fclose(out);
	free(buffer);

	return 0;
}
