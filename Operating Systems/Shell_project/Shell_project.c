/**
UNIX Shell Project

Sistemas Operativos
Grados I. Informatica, Computadores & Software
Dept. Arquitectura de Computadores - UMA

Some code adapted from "Fundamentos de Sistemas Operativos", Silberschatz et al.

To compile and run the program:
   $ gcc Shell_project.c job_control.c -o Shell
   $ ./Shell          
	(then type ^D to exit program)
	
Nombre y Apellidos: Emilio Gómez Esteban
Grupo y Titulación: 2ºD, Doble Grado Ing. Informática + Matemáticas
**/

#include "job_control.h"
#include <string.h>   // remember to compile with module job_control.c 

#define MAX_LINE 256 /* 256 chars per line, per command, should be enough. */


job * job_list;
		
void manejador(int senal){
	
	block_SIGCHLD();
	for(int i= list_size(job_list); i>0; i--){
			
		job * j = get_item_bypos(job_list, i);
		
		
		if(j != NULL){
			
			int pid = (j->pgid);
			int status;
			int info;
			enum status status_res;
		
			if(pid == waitpid(pid, &status, WUNTRACED | WNOHANG | WCONTINUED)){
				status_res = analyze_status(status, &info);
				if((status_res == SIGNALED)|| (status_res == EXITED)){
					printf("\nComando %s ejecutado en segundo plano con pid %d ha finalizado su ejecución\n", j->command, j->pgid);
					delete_job(job_list, j);
				} else if (status_res == SUSPENDED){
					printf("\nComando %s ejecutado en segundo plano con pid %d ha suspendido su ejecución\n", j->command, j->pgid);
					(j->state) = STOPPED;
				} else if (status_res == CONTINUED){
					printf("\nComando %s ejecutado en segundo plano con pid %d continuado\n", j->command, j->pgid);
					(j->state) = BACKGROUND;
				}
			}
		}
	}
	unblock_SIGCHLD();
}

// -----------------------------------------------------------------------
//                            MAIN          
// -----------------------------------------------------------------------


int main(void)
{
	char inputBuffer[MAX_LINE]; /* buffer to hold the command entered */
	int background;             /* equals 1 if a command is followed by '&' */
	char *args[MAX_LINE/2];     /* command line (of 256) has max of 128 arguments */
	// probably useful variables:
	int pid_fork, pid_wait; /* pid for created and waited process */
	int status;             /* status returned by wait */
	enum status status_res; /* status processed by analyze_status() */
	int info;	/* info processed by analyze_status() */
	
	job * item;
	
	ignore_terminal_signals();
	signal(SIGCHLD,manejador);
	job_list = new_list("BG/STOPPED");
		
	

	while (1)   /* Program terminates normally inside get_command() after ^D is typed*/
	{   		
		printf("¿Qué vas a ejecutar?->");
		fflush(stdout);
		get_command(inputBuffer, MAX_LINE, args, &background);  /* get next command */
		
			if(args[0]==NULL){ 
				continue;		// if empty command
			}	else if (strcmp(args[0], "cd") == 0){
					
				int n = chdir(args[1]);
				if(n == -1 && args[1] != NULL){
					printf("No se puede cambiar al directorio %s\n", args[1]);
				}
				
			} else if (strcmp(args[0], "jobs") == 0){

				print_job_list(job_list);

					
			} else if (strcmp(args[0], "fg") == 0){

				int n = 1;
				int ind;
				
				if(args[1] != NULL){
					ind = atoi(args[1]);
					
					if (ind>=1 && ind<=list_size(job_list)){
						n=ind;
					}
					else {
						printf("El segundo parámetro %s no ha sido aceptado\n", args[1]);
						continue;
						
					}
				} 
				
					
				job * j = get_item_bypos(job_list, n);
					
				if(j==NULL){
					printf("Error, no such job found: %d \n", n);
				} else {
					int jpid = (j->pgid);
					char * jname = strdup((*j).command);
					set_terminal(jpid);
					delete_job(job_list,j);
					killpg(jpid, SIGCONT);
					waitpid(jpid, &status, WUNTRACED);
					set_terminal(getpid());
					status_res = analyze_status(status, &info);
					
					if(status_res == SUSPENDED){
						item = new_job(jpid, jname, STOPPED);
						add_job(job_list, item);
					} 
					printf("Foreground pid: %d, command: %s, %s, info: %d \n", jpid, jname, status_strings[status_res], info);
					
				}

				
			} else if (strcmp(args[0], "bg") == 0){

				int n = 1;
				int ind;
				
				if(args[1] != NULL){
					ind = atoi(args[1]);
					
					if (ind>=1 && ind<=list_size(job_list)){
						n=ind;
					}
					else {
						printf("El segundo parámetro %s no ha sido aceptado\n", args[1]);
						continue;
						
					}
				} 
				
				job * j = get_item_bypos(job_list, n);
					
				if (j==NULL){
					
					printf("Error, no such job found: %d \n", n);
									
				} else {
					
					int jpid = (j->pgid);
					char * jname = strdup((*j).command);
					(j->state) = BACKGROUND;
					killpg(jpid, SIGCONT);
					printf("Background job running... pid: %d, command: %s\n", jpid, jname);
					
				}
				
			} else {
				
			/* (1) fork a child process using fork()*/
				 
				 pid_fork = fork();
				 
				 
				 if(pid_fork == 0){     //(2) the child process will invoke execvp()
					new_process_group(getpid());
				
					if(background == 0){
						set_terminal(getpid());
					}
						restore_terminal_signals();
						execvp(args[0],args);
						printf("Error command not found: %s\n", args[0]);
						exit(-1);
					
					
				 } else {
					if(background == 0){
						while (waitpid(pid_fork, &status, WUNTRACED) != pid_fork);    // (3) if background == 0, the parent will wait, otherwise continue 
						status_res = analyze_status(status,&info);
						set_terminal(getpid());
						
						if(status_res == SUSPENDED){
							
							block_SIGCHLD();
							item = new_job(pid_fork, args[0], STOPPED);
							add_job(job_list, item);
							printf("Comando %s ejecutado en primer plano con pid %d. Estado: %s. Info: %d\n",
							args[0], pid_fork, status_strings[status_res], info);
							unblock_SIGCHLD();
						}
						
						printf("Foreground pid: %d, command: %s, %s, info: %d\n", pid_fork, args[0], status_strings[status_res], info);   // (4) Shell shows a status message for processed command
					} else {
						block_SIGCHLD();
						item = new_job(pid_fork, args[0], BACKGROUND);
						add_job(job_list, item);
						printf("Background job running... pid: %d, command: %s\n", pid_fork, args[0]);
						unblock_SIGCHLD();
						
					}
					 
				 }
			}// (5) loop returns to get_commnad() function
		

	} // end while
}

