/*
 * MemoryManager.c
 *
 *  Created on: 15 feb 2023
 *      Author: Usuario
 */

#include <stdio.h>
#include <stdlib.h>
#include "MemoryManager.h"

const int MAX = 100;

// Creates the required structure to manage the available memory
	void Create(T_handler* handler){
		*handler = (T_handler)malloc(sizeof(struct T_Node));
		(*handler)->start=0;
		(*handler)->end=MAX;
		(*handler)->next=NULL;
	}


// Frees the required structure
	void Destroy(T_handler* handler){
		T_handler ptrAux;
		T_handler ptrFree = (*handler);

		while(ptrFree != NULL){
			ptrAux = ptrFree->next;
			free(ptrFree);
			ptrFree = ptrAux;
		}
		(*handler) = NULL;
	}

/* Returns in "ad" the memory address where the required block of memory with length "size" starts.
 * If this operation finishes successfully then "ok" holds a TRUE value; FALSE otherwise.
 */
	void Allocate(T_handler* handler, unsigned size, unsigned* ad, unsigned* ok){
		T_handler iterator = *handler;
		T_handler prev = NULL;
		int found = 0;

		while(iterator!=NULL && !found){
			if((iterator->end) - (iterator->start) + 1 >= size){
				found=1;
			} else {
				prev = iterator;
				iterator = iterator->next;
			}
		}

		if(!found){
			*ok=0;
			*ad=-1;
		} else {
			*ok = 1;
			*ad = iterator->start;
			//we have to update something pointed by handler
			if (prev==NULL){
				//destruction case
				if((iterator->end) - (iterator->start) + 1 ==size){
					*handler = (*handler)->next;
					free(iterator);
					iterator=NULL;
				//update case
				} else {
					(*handler)->start = (iterator->start) + size;
				}
			} else {
				//destruction case
				if((iterator->end) - (iterator->start) ==size){
					prev->next = iterator->next;
					free(iterator);
				//update case
				} else {
					iterator->start = (iterator->start + size);
				}
			}
		}
	}

/* Frees a block of memory with length "size" which starts at "ad" address.
 * If needed, can be assumed to be a previous allocated block
 */
	void Deallocate(T_handler* handler, unsigned size ,unsigned ad){
		T_handler iterator = *handler;
		T_handler prev = NULL;
		T_handler aux = NULL;

		while ((iterator->next) != NULL && (iterator->end) < ad) {
				prev = iterator;
				iterator = iterator->next;
		}

		if ((iterator->next) == NULL || ad < iterator->start) {
			aux = (T_handler) malloc(sizeof(struct T_Node));
			aux->start = ad;
			aux->end = ad + size - 1;
			aux->next = NULL;
			if (prev == NULL) {
				*handler = aux;
				aux->next = iterator;
			} else if ((iterator->next) == NULL) {
				iterator->next = aux;
			} else {
				prev->next = aux;
				aux->next = iterator;
			}

		} else {

			prev = iterator;
			iterator = iterator->next;

			if (ad + size > MAX) {
				prev->end = MAX;
				prev->next = NULL;
			} else {
				prev->end = ad + size;
				if (ad + size > iterator->start) {
					prev->end = iterator->end;
					prev->next = iterator->next;
				}
			}
		}
	}

/* Shows the current status of the memory */
	void Show (T_handler handler){
		while(handler!=NULL){
			printf("%u-%u\n", handler->start, handler->end);
			handler=handler->next;
		}
	}

