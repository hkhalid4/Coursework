/* This is the only file you should update and submit. */

/* Fill in your Name and GNumber in the following two comment fields
 * Name:
 * GNumber:
 */

#include "shell.h"
#include "parse.h"

/* Constants */
#define DEBUG 0
static const char *shell_path[] = { "./", "/usr/bin/", NULL };
//static const char *built_ins[] = { "quit", "help", "kill", "fg", "bg", "jobs", NULL};
static int numJobs = 0;
/*Jobs/Process struct definition
 * Create each attribute a part of the struct
 */
typedef struct job_struct{
	pid_t pid;
	int jobID;
	//0 for FG or 1 for BG process
	int fgOrbg;
	//set the state to 1 if it is one of the following 
	int running;
	int stopped;
	int terminated;
	int continued;
	int termBySignal;
	//set to 1 if the process was fg->bg or bg->fg
	int moved;
	//status of the child process for example, WIFEXITED,WIFSIGNALED, ETC. 
	int status;
	char execCmd[100];
	struct job_struct *next;	
}jobProcess;
jobProcess *removeJob(int pid);
jobProcess *head = NULL;
jobProcess *job_generate(char *cmd, int pid);
void appendJob(jobProcess **job);
static jobProcess foregroundProcess;
/*A SIGTSTP(20) should be sent to the foreground job to first pause its execution and 
 * then switch it to be a background job
 */
void ctrlZHandler(int sign){
	log_ctrl_z();
	int status;
	foregroundProcess.moved = 1;
	
		int pid = foregroundProcess.pid;
		//if the foreground process is running then send a signal to stop it
		if(foregroundProcess.running == 1){
			//send signal to stop the process
			if(kill(pid,SIGTSTP) == 0){
				foregroundProcess.stopped = 1;
			}			
		}
		waitpid(pid,&status,WNOHANG | WUNTRACED);
		log_job_state(pid,LOG_FG,foregroundProcess.execCmd,LOG_STOP);
		//create new background job with the foregroundProcess attributes 
		jobProcess *newJob = job_generate(foregroundProcess.execCmd,foregroundProcess.pid);
		//add the new job to the linked list
		appendJob(&newJob);
		
		
}
/*A SIGINT(2) should be sent to the foreground job to terminate its execution
 */
void ctrlCHandler(int sign){
	log_ctrl_c();
	int pid = foregroundProcess.pid;
	//if the foreground process is running then send a signal to 
	if(foregroundProcess.running == 1){
		if(kill(pid,SIGINT)){
			foregroundProcess.termBySignal = 1;
		}
	}

}
void childSigHandler(int sign){
	int status;
	pid_t pid;
	jobProcess *currNode = head;
	while((pid = waitpid(-1,&status,WNOHANG | WUNTRACED | WCONTINUED))>0){
		//finding the child process within our linked list
		if(pid != foregroundProcess.pid){
			while(currNode != NULL){
				if(currNode->pid == pid){
					break;
				}
				currNode = currNode->next;
			}
			currNode->status = status;
		}
		//if the child process was exited by a signal or terminated normally, then remove the job from the list
		if(WIFEXITED(status) || WIFSIGNALED(status)){
			jobProcess *removeNode = removeJob(pid);
			if(removeNode != NULL){
				//if the removed job was exited normally
				if(WIFEXITED(status)){
					removeNode->terminated = 1;
					log_job_state(pid,LOG_BG,removeNode->execCmd,LOG_TERM);
				}
				//if the removed job was terminated by a signal
				else if(WIFSIGNALED(status)){
					removeNode->termBySignal = 1;
					log_job_state(pid,LOG_BG,removeNode->execCmd, LOG_TERM_SIG);
				}
			}
		}		
	} 
	
}
jobProcess *removeJob(int pid){
	jobProcess *currNode = head;
	jobProcess *removeJob = NULL;
	if(currNode == NULL){
		return NULL;
	}
	//removing the head 
	else if(head->pid == pid){
		numJobs--;
		removeJob = head;
		head = head->next;
		return removeJob;
	}
	//removing in the middle of the list
	else{
		while(currNode->next != NULL){
			//continue tomorrow!
			if(currNode->next->pid == pid){
				removeJob = currNode->next;
				currNode->next = currNode->next->next;
				numJobs--;
				return removeJob;
			}
			currNode = currNode->next;
		}
	}
	return removeJob;

}
void appendJob(jobProcess **job){
	jobProcess *currNode = head;
	if(currNode == NULL){
		(*job)->next = NULL;
		head = *job;
		numJobs = 1;
	}
	else{
		while(currNode->next != NULL){
			currNode = currNode->next;
		}
		currNode->next = *job;
		
	}
}
int childFileRedirect(char *inFile, char *outFile,int isAppend){
	//check to see if we need file redirection
	//-1 for isAppend is if the files are NULL or theres only an inFile 
	//0 for isAppend when theres only an outFile 
	//1 for isAppend is when there is both an inFile and outFile
	if(inFile != NULL || outFile != NULL){
		//checking to see if the user put an inFile and outFile but not appending
		if(inFile != NULL && outFile != NULL && isAppend == 0){
			//check to see if the file exists and if not then create it
			int writeOutFile = open(outFile, O_WRONLY | O_CREAT | O_TRUNC, 00600);
			// check to see if the write to outFile didnt give an error
			if(writeOutFile == -1){
				log_file_open_error(outFile);
				return -1;
			}
			//check to see if the reading file exists
			int readInFile = open(inFile, O_RDONLY, 0600);
			//check to see if the reading inFile exists and doesnt return an error
			if(readInFile == -1){
				log_file_open_error(inFile);
				return -1;
			}
			//change the standard input/output if the call to open succeeded
			dup2(writeOutFile, STDOUT_FILENO);
			dup2(readInFile, STDIN_FILENO);
			return 0;
		}
		//checking if the inFile exists and there is no outFile 
		else if(inFile != NULL && outFile == NULL && isAppend == -1){
			int readInFile = open(inFile, O_RDONLY,0600);
			//check to see if the inFile exists and doesnt return an error
			if(readInFile == -1){
				log_file_open_error(inFile);
				return -1;
			}
			//change the standard input if the call to open succeeded
			dup2(readInFile,STDIN_FILENO);
			return 0;
		}
		//writing to the outFile and no inFile
		else if(inFile == NULL && outFile != NULL && isAppend == 0){
			int writeOutFile = open(outFile, O_WRONLY | O_CREAT | O_TRUNC,0600);
			//if creating or writing out the file gives an error then log the error and exit
			if(writeOutFile == -1){
				log_file_open_error(outFile);
				return -1;
			}
			//change the standard output if the call to open succeeded
			dup2(writeOutFile,STDOUT_FILENO);
			return 0;
		}
		//reading from the inFile and writing it out the outFile 
		
	}
	return -1;
}
/*Generate a new job with the given information.
 * Malloc and copy the command string
 * Return the job on success or NULL on any error
 */
jobProcess *job_generate(char *cmd, int pid){
	jobProcess *newJob;
	//allocate memory for the job, return error if unsuccessful
	newJob = (jobProcess*)malloc(sizeof(jobProcess));
	//check and see if the command passed in is NULL

	//copy the command passed in to the job execCmd
	strcpy(newJob->execCmd,cmd);
	//set the other variables/attributes
	numJobs++;
	newJob->pid = pid;
	newJob->jobID = numJobs;
	newJob->running = 1;
	newJob->stopped = 0;
	newJob->terminated = 0;
	newJob->continued = 0;
	newJob->termBySignal = 0;			
	return newJob;

}
/*When called, the shell would resume the execution of a background job with the specified jobID
 * No change is made with an invalid jobID
 * Return pid on success, and -1 otherwise
 */
int moveToBG(int jobId){
	jobProcess *currNode = head;
	//if the list is empty call the logError
	if(currNode == NULL){
		return -1;
	}
	while(currNode != NULL){
		//make sure the currNode is a stopped background process which hasnt been terminated 
		if((currNode->jobID==jobId) && (WIFSTOPPED(currNode->status))){
			if((WIFSIGNALED(currNode->status)) || (WIFEXITED(currNode->status))){return -1;}
			//log the background process to be switched to running 
			log_job_move(currNode->pid,LOG_BG,currNode->execCmd);
			//send a kill signal to continue the stopped background process
			kill(currNode->pid,SIGCONT);
			//set the attributes to running
			currNode->running = 1;
			currNode->stopped = 0;
			currNode->moved = 1;
			return currNode->pid; 
		}
		//if the background process is already running 
		else if((currNode->jobID==jobId) && (currNode->terminated == 0) && (currNode->termBySignal == 0) && (currNode->stopped == 0)){
			return currNode->pid;
		}
		//if the background process has been terminated then return -1 for logError
		else if(currNode->termBySignal == 1 || currNode->terminated == 1){
			return -1;
		}
		currNode = currNode->next;
	}
	return -1;	

}
/*Switch the background job to be in foreground and then wait until it completes
 * If the job is previously stopped, resume its execution after moving it back to foreground
 * and the status should be "running"
 * return -1 on unsuccess, and pid of the process on success
 */
int moveToFG(int jobId){
	int pid = 0;
	int status = 0;
	jobProcess *currNode = head;
	//list is empty so call the log_error
	if(currNode == NULL){
		return -1;
	}	
	while(currNode != NULL){
		//making sure we find the right background job that has not been terminated
		if((currNode->jobID==jobId) && (WIFSTOPPED(currNode->status))){
			if((WIFSIGNALED(currNode->status)) || (WIFEXITED(currNode->status))){return -1;}
			//log the background process to eb switched to foreground
			log_job_move(currNode->pid,LOG_FG,currNode->execCmd);
			//set the attributes to be of a foregroung process
			foregroundProcess.jobID = currNode->jobID;
			foregroundProcess.pid = currNode->pid;
			foregroundProcess.running = 1;
			foregroundProcess.terminated = 0;
			foregroundProcess.continued = 0;
			foregroundProcess.moved = 1;
			foregroundProcess.termBySignal = 0;
			//if the background process was stopped then move it to FG and resume it
			if(currNode->stopped == 1){
				//send the job a continue signal
				kill(currNode->pid,SIGCONT);
				foregroundProcess.stopped = 0;
				foregroundProcess.continued = 1;
				foregroundProcess.running = 1;
			}
			//wait for the child foreground process to end
			if((pid = waitpid(currNode->pid,&status,0))<0){
				exit(-1);
			}
			//save the status of the child when terminated 
			foregroundProcess.status = status;
			foregroundProcess.terminated = 1;
			foregroundProcess.running = 0;
			foregroundProcess.continued = 0;
			return foregroundProcess.pid;
		}
		currNode = currNode->next;
	}
	return -1;
}

/*Function to kill the pid with the respected signal
 * return 0 on success
 */
int killJob(int signalNum, int pidNum){
	jobProcess *currNode = head;
	//are we supposed to decrement numJobs and/or set jobid to -1?
	//if list is empty then initialize the numJobs = 0 and return -1 on no success
	if(currNode == NULL){
		numJobs = 0;
		return -1;
	} 
	//handle the kill signal of 2(SIGINT) Ctrl+C
	if(signalNum == 2){
		//iterate through the list to find the PID and terminate it
		while(currNode != NULL){
			if(currNode->pid==pidNum){
				//set each attribute to its respected state after kill is called
				kill(currNode->pid,signalNum);
				currNode->running = 0;
				currNode->stopped = 0;
				currNode->terminated = 1;
				currNode->continued = 0;
				currNode->termBySignal = 1;
				return 0;
			}
			currNode = currNode->next;
		}
	}
	//handle the kill signal of 9(SIGKILL) 
	else if(signalNum == 9){
		while(currNode!= NULL){
			if(currNode->pid == pidNum){
				kill(currNode->pid,signalNum);
				currNode->running = 0;
				currNode->stopped = 0;
				currNode->terminated = 1;
				currNode->continued = 0;
				currNode->termBySignal = 1;
				return 0;
			}
			currNode = currNode->next;
		}
	}
	//handle the kill signal of 18(SIGCONT)
	else if(signalNum == 18){
		while(currNode != NULL){
			if(currNode->pid == pidNum){
				kill(currNode->pid,signalNum);
				currNode->running = 1;
				currNode->stopped = 0;
				currNode->continued = 1;
				currNode->terminated = 0;
				return 0;
			}
			currNode = currNode->next;
		}
	}
	//handle the kill signal of 20(SIGTSTP)
	else if(signalNum == 20){
		while(currNode != NULL){
			if(currNode->pid == pidNum){
				kill(currNode->pid,signalNum);
				currNode->running = 0;
				currNode->stopped = 1;
				currNode->continued = 0;
				currNode->terminated = 0;
				return 0;
			}
			currNode = currNode->next;
		}
	}
	return -1;

} 
//logs the number of jobs 
void builtInJobs(){
	jobProcess *currNode = head;
	int termJobs = 0;
	//first checking if the linked list is empty then return 0 for number of BG jobs
	if(currNode == NULL){
		log_job_number(termJobs);
	}
	else if(currNode != NULL){
		//have to log number of BG jobs that are not terminated first
		while(currNode != NULL){
			if(currNode->terminated == 0){
				termJobs++;
			}
			currNode = currNode->next;
		}	
	}
	log_job_number(termJobs);
	currNode = head;
	//for each job then call log_job_details
	while(currNode != NULL){
		if(currNode->terminated == 0){
			//log the job if its in running state
			if(currNode->running == 1){
				log_job_details(currNode->jobID,currNode->pid,"Running",currNode->execCmd);
			}
			//log the job if its in stopped state
			else if(currNode->stopped == 1){
				log_job_details(currNode->jobID,currNode->pid,"Stopped",currNode->execCmd);
			}
		}
		currNode = currNode->next;
	}
}

/* The entry of your shell program */
int main() {
  	char cmdline[MAXLINE];        /* Command line */
  	char *cmd = NULL;
  	/* Intial Prompt and Welcome */
  	log_prompt();
	log_help();
	//Ctrl+C handler
	struct sigaction act;
	memset(&act,0,sizeof(act));
	struct sigaction old;
	act.sa_handler = ctrlCHandler;
	sigaction(SIGINT,&act,&old);

	//Ctrl+z handler
	struct sigaction act1;
	memset(&act1,0,sizeof(act1));
	struct sigaction old1;
	act1.sa_handler = ctrlZHandler;
	sigaction(SIGTSTP,&act1,&old1);
	
	//child signal handler
	struct sigaction act2;
	memset(&act2,0,sizeof(act2));
	struct sigaction old2;
	act2.sa_handler = childSigHandler;
	sigaction(SIGCHLD,&act2,&old2);
	
	//add the signals to the set in which we want to block
	pid_t pid;
	
  	/* Shell looping here to accept user command and execute */
	while (1) {
    		char *argv[MAXARGS];        /* Argument list */
    		Cmd_aux aux;                /* Auxilliary cmd info: check parse.h */
    		/* Print prompt */
    		log_prompt();

    		/* Read a line */
    		// note: fgets will keep the ending '\n'
		if (fgets(cmdline, MAXLINE, stdin) == NULL) {
      			if (errno == EINTR)
        			continue;
      			exit(-1);
    		}	

    		if (feof(stdin)) {  /* ctrl-d will exit shell */
      			exit(0);
    		}	

    		/* Parse command line */
    		if (strlen(cmdline)==1)   /* empty cmd line will be ignored */
      			continue;     

    		cmdline[strlen(cmdline) - 1] = '\0';        /* remove trailing '\n' */

    		cmd = malloc(strlen(cmdline) + 1);
    		snprintf(cmd, strlen(cmdline) + 1, "%s", cmdline);

    		/* Bail if command is only whitespace */
    		if(!is_whitespace(cmd)) {
      			initialize_argv(argv);    /* initialize arg lists and aux */
      			initialize_aux(&aux);
      			parse(cmd, argv, &aux); /* call provided parse() */

      			if (DEBUG)  /* display parse result, redefine DEBUG to turn it off */
        			debug_print_parse(cmd, argv, &aux, "main (after parse)");

      		/* After parsing: your code to continue from here */
      		/*================================================*/
		}
		//built-in functions: jobs/kill/quit/help/fg/bg/
		//if cmd is quit then exit the program and call log_quit
		if(strcmp(cmd,"quit")==0){
			log_quit();
			exit(0);
		}
		//if the cmd is help then call log_help and return back to the prompt
		if(strcmp(cmd,"help")==0){
			log_help();
		}
		//if statement for when the user inputs the built-in command for jobs
		else if(strcmp(cmd,"jobs")==0){
			builtInJobs();
		}	
		//if statement to execute the built in function for kill
		else if(strcmp(argv[0],"kill")==0){
			//convert and save the pid and signal
			int signal = atoi(argv[1]);
			int pid = atoi(argv[2]);
			int jobKilled = killJob(signal,pid);
			//call the kill function and pass in the parameters
			if(jobKilled==0){
				//if function is successful then log the kill
				log_kill(signal,pid);
			}
			else if(jobKilled == -1){
			}
			
		}
		//if statement for the built in fg function 
		else if(strcmp(argv[0],"fg")==0 && atoi(argv[1])<=numJobs){
			int jobID = atoi(argv[1]);
			int pid;
			//function returns pid on success
			if((pid = moveToFG(jobID) > 0)){
				//call log again to make sure the foreground process terminated properply
				//log_job_move(pid,LOG_FG,"fg");
			}
			else if(pid == -1){
				//log if the specified jobID is invalid and cannot be located in background jobs
				log_jobid_error(jobID);
			}
		}
		//if statement for the built in bg function
		else if(strcmp(argv[0],"bg")==0 && atoi(argv[1])<=numJobs){
			int jobID = atoi(argv[1]);
			int pid;
			
			if((pid = moveToBG(jobID)) > 0){
				//log_job_move(pid,LOG_BG,"bg");
			}
			else if(pid == -1){
				log_jobid_error(jobID);
			}
		}
		//if statement for non-built in functions and check to see if the user does not enter any of the built in functions 
		else if(strcmp(argv[0],"quit")!=0 && strcmp(argv[0],"help")!=0 && strcmp(argv[0],"jobs")!=0 && strcmp(argv[0],"fg")!=0 && strcmp(argv[0],"kill")!=0 && strcmp(argv[0],"bg")!=0){
			jobProcess *newJob;
			//check if it is a background process
			if(aux.is_bg){
				pid = fork();
				newJob = job_generate(cmd,pid);
				appendJob(&newJob);
				//check to see if its the parent process
				if(pid!=0){
					log_start(pid,LOG_BG,cmd);
				}
				else{
					char concatString[100];
                                	strcpy(concatString, shell_path[0]);
                               		strcat(concatString,argv[0]);
					if(execv(concatString,argv)==-1){
						strcpy(concatString,shell_path[1]);
						strcat(concatString,argv[0]);
						if(execv(concatString,argv) == -1){
							log_command_error(argv[0]);
						}
					}
				}
			}
			//else statement for foregroundProcess
			else{
				pid = fork();
				foregroundProcess.pid = pid;
				strcpy(foregroundProcess.execCmd,cmd);
				foregroundProcess.running = 1;
				int status;
				//work with the parent process first
				if(pid!=0){
					log_start(pid,LOG_FG,cmd);
					waitpid(pid,&status,WUNTRACED);
					foregroundProcess.status = status;
					if(WIFEXITED(status)||(WIFSIGNALED(status))){
						if(WIFEXITED(status)){
							log_job_state(foregroundProcess.pid, LOG_FG, foregroundProcess.execCmd,LOG_TERM);
							foregroundProcess.terminated = 1;		
						}
						else if(WIFSIGNALED(status)){
							log_job_state(foregroundProcess.pid,LOG_FG,foregroundProcess.execCmd,LOG_TERM_SIG);
							foregroundProcess.termBySignal = 1;
						}
					}
				}
				else{
					childFileRedirect(aux.in_file,aux.out_file,aux.is_append);
					char concatString[100];
                                        strcpy(concatString, shell_path[0]);
                                        strcat(concatString,argv[0]);
                                        if(execv(concatString,argv)==-1){
                                                strcpy(concatString,shell_path[1]);
                                                strcat(concatString,argv[0]);
                                                if(execv(concatString,argv) == -1){
                                                        log_command_error(argv[0]);
                                                }
                                        }
					
				}
			}
	
		} 	
		
		free_options(&cmd, argv, &aux);
	}
	return 0;
}

