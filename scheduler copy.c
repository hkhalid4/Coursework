/* Fill in your Name and GNumber in the following two comment fields
 * Name: Huda Khalid
 * GNumber: G01148315
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "clock.h"
#include "structs.h"
#include "constants.h"
#include "scheduler.h"

/* Initialize the Schedule Struct
 * Follow the specification for this function.
 * Returns a pointer to the new Schedule or NULL on any error.
 */
Schedule *scheduler_init() {
	//allocating memory for the schedule struct
	Schedule *scheduler;
	if((scheduler = (Schedule*) malloc(sizeof(Schedule))) == NULL){
		return NULL;
	}
	//return null to make sure memory was allocated for ready queue and returns a pointer
	if((scheduler->ready_queue = (Queue*) malloc(sizeof(Queue))) == NULL){
		return NULL;
	}
	else{
		//if memory was allocated then initalize head to null and count to 0 in ready_queue
		scheduler->ready_queue->head = NULL;
		scheduler->ready_queue->count = 0;
	}	

	//return null to make sure memory was allocated for stopped queue and returns a pointer to memory
	if((scheduler->stopped_queue = (Queue*) malloc(sizeof(Queue))) == NULL){
		return NULL;
	}
	else{
		//if memory was allocated then initialize head to null and count to 0 in stopped_queue
		scheduler->stopped_queue->head = NULL;
		scheduler->stopped_queue->count = 0;
	}
	
	//return null if malloc doesnt return a pointer
	if((scheduler->waiting_queue = (Queue*) malloc(sizeof(Queue))) == NULL){
		return NULL;
	}
	else{
		//intialize head to null and count to 0 if malloc was successful
		scheduler->waiting_queue->head = NULL;
		scheduler->waiting_queue->count = 0;
	}
	return scheduler;
}

/*
 * Helper function to add the process in ascending order in the respected queue
 * return 0 on success and -1 on fail
 */
int addAscendingOrder(Queue *queue, Process *process){
	//create a currNode to iterate through the queue
	Process *currNode = NULL;
	//checks if queue is empty
	if((queue->head == NULL) && (queue->count == 0)){
		//set the head to the new process
		queue->head = process;
		//set the next pointer to nul since its the only one
		process->next = NULL;
		//increases the amount of processes in the queue
		queue->count++;
		//return 0 on success
		return 0;
	}
	
	//check if there is only one element in the queue
	else if(queue->head->next == NULL){
		//checks if the process PID is less than the head, then create new head
		if(process->pid < queue->head->pid){
			//set the process next to the current head
			process->next = queue->head;
			//set the second process next to null since its the last in the list
			queue->head->next = NULL;
			//set the new head
			queue->head = process;
			//increment the count
			queue->count++;
			return 0;
		}
		//else; insert the new process to the end of the list since its greater than the head
		else{
			//set head's next to the new process
			queue->head->next = process;
			//set process next to null since its the last in the list
			process->next = NULL;
			//increment count
			queue->count++;
			return 0;
		}
	}
	//add to the queue with multiple elements
	else{
		//change head it process is less than the head
		if(process->pid < queue->head->pid){
			process->next = queue->head;
			queue->head = process;
			queue->count++;
			return 0;
		}
		//if process is greater than head
		else{
			//currNode to iterate through the queue
			currNode = queue->head;
			while(currNode->next != NULL){
				//check to see if currNode's next is greater than the process pid
				if(process->pid < currNode->next->pid){
					//making a connection to currNode's next
					process->next = currNode->next;
					//setting the currNode's next to the new process
					currNode->next = process;
					//increment count
					queue->count++;
					return 0;
				}
				//if the process is greater than currNode->next
				currNode = currNode->next;
			}
			//add process to the end of the queue
			if(currNode->next == NULL){
				//making the connection from the last process in the list to the new one
				currNode->next = process;
				//setting the process next to NULL since its the last in the list
				process->next = NULL;
				//increment count
				queue->count++;
				return 0;
			}

		}
	}

	return -1;

}

/* Add the process into the appropriate linked list.
 * Follow the specification for this function.
 * Returns a 0 on success or a -1 on any error.
 */
int scheduler_add(Schedule *schedule, Process *process) {
	//if process is equal to null or if process is in any other state then return a -1 for error
	if((schedule->ready_queue == NULL) || (process == NULL) || (process->flags & 0x70) || (schedule == NULL)){
		return -1;
	}
	
	//clears the states and preserves the other bits if its a 1 
	process->flags &= ~0xE;	 
	//flags is then changed to the ready state and preserves the other bits if its a 1
	process->flags |= 0x4;
	//check to see if the insertion was successful or not
	if(addAscendingOrder(schedule->ready_queue, process) == 0){
		return 0;
	}
	else{
		return -1;
	}
		
  	return -1;
}

/* Receives the process from the CPU and puts it into the Waiting Queue
 * Follow the specification for this function.
 * Returns a 0 on success or -1 on any error.
 */
int scheduler_wait(Schedule *schedule, Process *process, int io_time) {
	//check if processm schedule and io_time is empty or null
	if((schedule->waiting_queue == NULL) || (process == NULL) || (schedule == NULL) || (io_time == 0)){
		return -1;
	} 
	//mask to clear the state bits and preserve exit and sudo bits
	int mask = ~0x7E;
	//clears the state bits and preserves the exit code and sudo bit
	process->flags &= mask;
	process->flags |= WAITING;
	//set the wait_remaining to io_time
	process->wait_remaining = io_time;
	Process *currNode = NULL;
	//checks to see if waiting queue is empty
	if((schedule->waiting_queue->head == NULL) && (schedule->waiting_queue->count == 0)){
		//set the head to the new and first process
		schedule->waiting_queue->head = process;
		//sets process next to null since its the only one in the list
		process->next = NULL;
		//increase the amount of processes in waiting queue
		schedule->waiting_queue->count++;
		return 0;
	}
	//insert to the end if there is one element in the queue
	else if((schedule->waiting_queue->head->next == NULL) && (schedule->waiting_queue->count == 1)){
		schedule->waiting_queue->head->next = process;
		process->next = NULL;
		schedule->waiting_queue->count++;
		return 0;
	}
	//insert the process at the end of the queue if there are many elements
	else{
		//set currNode equal to the head
		currNode = schedule->waiting_queue->head;
		while(currNode->next != NULL){
			//iterate to the end of the list
			currNode = currNode->next;
		}
		//add process to the end of the list
		currNode->next = process;
		process->next = NULL;
		schedule->waiting_queue->count++;
		return 0;

	}
	return -1;
}

/* Receives a process from the CPU and moves it to the stopped queue.
 * Follow the specification for this function.
 * Returns a 0 on success or a -1 on any error.
 */
int scheduler_stop(Schedule *schedule, Process *process) {
	//check to see if schedule, stopped queue, or process are null
	if((schedule == NULL) || (process == NULL) || (schedule->stopped_queue == NULL)){
		return -1;
	}
	
	//clear the states and copy the exit code and sudo bit
	process->flags &= ~0x7E;
	//sets the state bit to stopped
	process->flags |= STOPPED;
	//calling the helper function to add to stop queue in ascending order
	if(addAscendingOrder(schedule->stopped_queue,process) == 0){
		//return 0 on insertion sucess
		return 0;
	}
	//return -1 on failure to add process	
	else{
		return -1;
	}
	return -1;
}

/* Move the process with matching pid from Stopped to Ready.
 * Follow the specification for this function.
 * Returns a 0 on success or a -1 on any error.
 */
int scheduler_continue(Schedule *schedule, int pid) {
	//check to see if schedule is null
	if((schedule == NULL) || (schedule->ready_queue == NULL) || (schedule->stopped_queue == NULL)){
		return -1;
	}

	Process *currNode = NULL;
	Process *removeNode = NULL;
	currNode = schedule->stopped_queue->head;
	//checks to see if head has the same pid
	if(currNode->pid == pid){
		removeNode = currNode;
		schedule->stopped_queue->head = currNode->next;
		removeNode->next = NULL;
		schedule->stopped_queue->count--;
		removeNode->flags &= ~0x7E;
		removeNode->flags |= READY;
	}
	//gets the process from stopped queue which has the same pid passed in
	while((currNode != NULL) && (currNode->next != NULL)){
		//checks to see if next pid is the same
		if(currNode->next->pid == pid){
			//check if the process being removed is the last one
			if(currNode->next->next == NULL){
				removeNode = currNode->next;
				currNode->next = NULL;
				removeNode->next = NULL;
				schedule->stopped_queue->count--;
				removeNode->flags &= ~0x7E;
				removeNode->flags |= READY;	
			}
			//check to see if its being removed from the middle
			else{
				removeNode = currNode->next;
				currNode->next = removeNode->next;
				removeNode->next = NULL;
				schedule->stopped_queue->count--;
				removeNode->flags &= ~0x7E;
				removeNode->flags |= READY;	
			}
			
		}
		currNode = currNode->next;
	}

	//inserting the removedNode from stopped queue to ready queue using the helper function
	if(addAscendingOrder(schedule->ready_queue, removeNode) == 0){
		//return 0 on insertion success
		return 0;
	}
	else{
		//return -1 on failure to insert
		return -1;
		
	}
	return -1;
}

/* Receives the process from the CPU for removal.
 * Follow the specification for this function.
 * Returns its exit code (from flags) on success or a -1 on any error.
 */
int scheduler_finish(Process *process) {
	//check to see if process is null
	if(process == NULL){
		return -1;
	}
	//extracts the exit code by shifting it right by 7
	int exit_code = process->flags >> 7;
	//free up the command first and then process
	free(process->command);
	free(process);
	//return the exit_code on sucess
	return exit_code;	
}

/* Create a new Process with the given information.
 * - Malloc and copy the command string, don't just assign it!
 * Follow the specification for this function.
 * Returns the Process on success or a NULL on any error.
 */
Process *scheduler_generate(char *command, int pid, int time_remaining, int is_sudo) {
	Process *process;
	//allocate memory for process and command but return error if unsucessful
	if(((process = (Process*) malloc(sizeof(Process))) == NULL) || ((process->command = (char*)malloc(MAX_COMMAND)) == NULL)){
		return NULL;
	}
	//check to see if the command passed in is null
	if((command == NULL) || (command == '\0')){
		return NULL;
	}
	else{
		//copy the command passed in to the process command
		if((strncpy(process->command, command, MAX_COMMAND)) == NULL){
			return NULL;
		}
	}
	//set the other variables of process
	process->pid = pid;
	process->time_remaining = time_remaining;
	process->time_last_run = clock_get_time();
	process->wait_remaining = 0;
	process->next = NULL;
	//clear all the bits
	process->flags &= 0;
	//if is_sudo passed in is 1 then set sudo bit to a 1 in flags
	if(is_sudo == 1){
		//set the sudo bit to 1 and created bit to 1
		process->flags |= 0x3;			
	}	
	else{
		//set the sudo bit to 0 and created bit to 1
		process->flags |=2;
	}
			
	return process;	
}

/* Select the next process to run from Ready Queue.
 * Follow the specification for this function.
 * Returns the process selected or NULL if none available or on any errors.
 */
Process *scheduler_select(Schedule *schedule) {
	//check to see if schedule is null
	if((schedule == NULL) && (schedule->ready_queue == NULL)){
		return NULL;
	}
	//initialize clock_get_time before looping since clock is always changing
	int clock_time = clock_get_time();
	//create a temp to iterate through the queue
	Process *currNode = NULL;
	currNode = schedule->ready_queue->head;
	//create mask in order to set the state bits to 0, keep the exit code and sudo bit
	int mask = 0xFFFFFF81;
	//check to see if currNode is null
	if((currNode == NULL) || (schedule->ready_queue->count == 0)){
		return NULL;
	}

	//check 1st case to check if removing head
	if((clock_get_time() - currNode->time_last_run) >= TIME_STARVATION){
		if(currNode == schedule->ready_queue->head){
			//set the new head to the next process
			schedule->ready_queue->head = currNode->next;
			//set the next pointer to null to remove it from the list
			currNode->next = NULL;
			//set all the state bits to 0 and copy the sudo_bit
			currNode->flags &= mask;
			//sets the state to running and copies the sudo bit
			currNode->flags |= RUNNING;
			//decrement the count
			schedule->ready_queue->count--;
			//return node to exit the select method
			return currNode;
		}
	}
	//pointer to the removed node
	Process *removeNode = NULL;
	//iterate through the list to find the process which is starving
	while((currNode != NULL) && (currNode->next != NULL)){
		//check to see if the difference btw time and timeLastRun >= 6 (time_starvation constant)
		if((clock_time - currNode->next->time_last_run) >= TIME_STARVATION){
			//2nd case to check if removing from end
			if(currNode->next->next == NULL){
				//set removeNode to the last process in the list
				removeNode = currNode->next;
				//set the currNode nex to NULL since its the last process now
				currNode->next = NULL;
				//set the next pointer to NULL since it is being removed
				removeNode->next = NULL;
				//decrement the count
				schedule->ready_queue->count--;
				//set all state bits to 0, copies sudo bit and exit code
				removeNode->flags &= mask;
				//sets the state to running, copies sudo bit and exit code
				removeNode->flags |= RUNNING;
				return removeNode;
			}

			//3rd case which is else to remove from the middle 
			else{
				//set removeNode to current's next
				removeNode = currNode->next;
				//set the current's next to the next element after the removed node
				currNode->next = removeNode->next;
				//set the next pointer to null since it is being removed
				removeNode->next = NULL;
				schedule->ready_queue->count--;
				//set all state bits to 0, copies sudo bit and exit code
				removeNode->flags &= mask;
				removeNode->flags |= RUNNING;
				return removeNode;	
			}
			return currNode;	
		}
		currNode = currNode->next;
	}
	//reset currNode back to the head
	removeNode = NULL;
	currNode = schedule->ready_queue->head;;	
	//iterate through the list to find the lowest time_remaining after we check for starvation
	while((currNode != NULL) && (currNode->next != NULL)){
		//check to see which process has lowest_time_remaining
		if(removeNode == NULL){
			if(schedule->ready_queue->head->time_remaining > currNode->next->time_remaining){
				removeNode = currNode;
			}
			
		}
		else if(removeNode->next->time_remaining > currNode->next->time_remaining){
			removeNode = currNode;
		}
		currNode = currNode->next;
	}
	//remove the node, set the state to RUNNING while copying exit and sudo bits, and decrement count
	
	//if removeNode is null that means head was the process that had lowest time remaining
	if(removeNode == NULL){
		//set removeNode to head
		removeNode = schedule->ready_queue->head;
		//set the new head to head's next
		schedule->ready_queue->head = removeNode->next;
		//set the removedNode next to NULL
		removeNode->next = NULL;
		//clear the state bits and keep all the exit code and sudo bit
		removeNode->flags &= mask;
		//set the state bit to running
		removeNode->flags |= RUNNING;
		//decrement the count
		schedule->ready_queue->count--;
		//return the removeNode
		return removeNode;	
	}
	//removing the node from the end, removeNode pointing to previous node of which needs to get removed
	else if(removeNode->next->next == NULL){
		//set the last node's next to null since its the last process in the queue
		removeNode->next = NULL;
		//set removeNode to the last element in the list
		removeNode = currNode;
		//set the removeNode next to null
		removeNode->next = NULL;
		//set the state bit to running and copy the exit code and sudo bit
		removeNode->flags &= mask;
		removeNode->flags |= RUNNING;
		schedule->ready_queue->count--;
		return removeNode;
		
	}
	//remove the node from the middle, removeNode pointing to previous node of which needs to get removed
	else{
		//setting currNode to the previous one
		currNode = removeNode;
		//setting removeNode to the process that needs to get removed
		removeNode = removeNode->next;
		//remove the node
		currNode->next = currNode->next->next;
		//set next to null since it is removed
		removeNode->next = NULL;
		removeNode->flags &= mask;
		removeNode->flags |= RUNNING;
		schedule->ready_queue->count--;
		return removeNode;		

	}
	return NULL;
}

/* Updates the first process in the waiting queue by decrementing waiting_time.
 * If that process is done (waiting_time is 0), move it to the ready queue.
 * Follow the specification for this function.
 * Returns 0 on success or -1 on any errors.
 */
int scheduler_io_run(Schedule *schedule) {
	//check to see if waiting queue is null
	if(schedule->waiting_queue == NULL){
		return -1;
	}
	else if(schedule->waiting_queue->head == NULL){
		//return success because there is nothing in the queue
		return 0;
	}
	Process *removeNode = NULL;
	//decrement the head of waiting queue by 1
	schedule->waiting_queue->head->wait_remaining--;
	//check to see if the wait_remaining is 0
	if(schedule->waiting_queue->head->wait_remaining == 0){
		//save the head before you remove it from the queue
		removeNode = schedule->waiting_queue->head;
		//set the new head since head is removed
		schedule->waiting_queue->head = removeNode->next;
		//set the removed node next to null since its removed
		removeNode->next = NULL;
		//clear the state bits and set it to ready while preserving the exit code and sudo bit
		removeNode->flags &= ~0x7E;
		removeNode->flags |= READY;
		//decrement the count
		schedule->waiting_queue->count--;
		//add the removed node to the ready queue in ascending order by calling the helper function
		if(addAscendingOrder(schedule->ready_queue,removeNode) == 0){
			//inserted into the ready queue is successful
			return 0;
		}
		else{
			//return -1 since it failed to insert
			return -1;
		}
		
	}
  return 0;
}

/* Returns the number of items in a given Linked List (Queue) (Queue)
 * Follow the specification for this function.
 * Returns the count of the Linked List, or -1 on any errors.
 */
int scheduler_count(Queue *ll) {
	//check to see if the queue passed in is null
	if(ll == NULL){
		return -1;
	}	
	return ll->count;
 
}


void freeQueue(Queue *queue){
	if(queue == NULL){
		return;
	}
	//node that we are going to free
	Process *currNode = NULL;
	//pointer to nextNode so we keep track
	Process *nextNode = NULL;;
	currNode = queue->head;
	//iterates through the queue and frees up the memmory
	while(currNode != NULL){
		//sets nextNode to the next node after currNode
		nextNode = currNode->next;
		//free up the command in the currNode
		free(currNode->command);
		//then free up the actual curNode
		free(currNode);
		//currNode is then set to the next node to free
		currNode = nextNode;
	}
	free(queue);

}

/* Completely frees all allocated memory in the scheduler
 * Follow the specification for this function.
 */
void scheduler_free(Schedule *scheduler) {
	//checks to see if schedule is null
	if(scheduler == NULL){
		//return nothing if schedule is null
		return;
	}
	freeQueue(scheduler->ready_queue);
	freeQueue(scheduler->stopped_queue);
	freeQueue(scheduler->waiting_queue);
	free(scheduler);

}
