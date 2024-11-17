/*
 * bast.c
 *
 *  Created on: 8 mar 2023
 *      Author: Usuario
 */

#include "bst.h"
#include <stdlib.h>
#include <stdio.h>

// Create an empty tree
void create(TTree* tree){
	*tree = NULL;   //point to null
}

// destroy the tree and free all memory
void destroy(TTree * tree){
	if(*tree != NULL){
		destroy(&((*tree)->left));
		destroy(&((*tree)->right));
		free(*tree);
		*tree = NULL;
	}
}

// Insert the value in the tree. If it is already there, do nothing
void insert(TTree* tree,unsigned val){
	if(*tree == NULL){
		*tree = (TTree)malloc(sizeof(struct TNode));
		(*tree)->val = val;
		(*tree)->left = NULL;
		(*tree)->right = NULL;
	} else {
		if(((*tree)->val) > val){
			insert(&((*tree)->left),val);
		} else if (((*tree)->val) < val){
			insert(&((*tree)->right),val);
		}
	}
}

// Show in screen the contents of the tree, ordered
void show(TTree tree){
	if(tree != NULL){
		show(tree->left);
		printf("%u ", tree->val);
		show(tree->right);
	}
}

// Save the tree to a file
void save(TTree tree, FILE* f){
	if(tree != NULL){
		save(tree->left, f);
		fwrite(&(tree->val), sizeof(unsigned), 1, f);
		save(tree->right, f);
	}
}
