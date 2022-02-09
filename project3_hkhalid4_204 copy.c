#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _songnode
{   
  char title[35];
  char artist[35];
  char album[35];
  double duration;
  struct _songnode *next;
} SongNode;
void printList(SongNode *head);
SongNode *createSongNode(char *title, char *artist, char *album, double duration){
	SongNode* songNode = (SongNode*) malloc(sizeof(SongNode));
	strcpy(songNode->artist, artist);
	strcpy(songNode->title, title);
	strcpy(songNode->album, album);
	songNode->duration = duration;
	songNode->next = NULL;
	return songNode;

}
void appendSong(SongNode **head, SongNode *song){
	SongNode *currNode;
	currNode = *head;
	if((*head) == NULL){
		(*head) = song;
	}
	else{
		if((*head)->next == NULL){
			(*head)->next = song;
		}
		else{
			currNode = (*head)->next;
			while(currNode->next != NULL){
				currNode = currNode->next;
			}
	
			currNode->next = song;
			song->next = NULL;
		}
	}
}
void addSongBeg(SongNode **head, SongNode *song){
	if((*head) == NULL){
		(*head) = song;
		song->next = NULL;
	}	
	else{
		song->next = *head;
		*head = song;	
	}
	
}
SongNode *createEmptyList(){
	SongNode *head = NULL;
	return head;
}
int insertSong(SongNode **head, SongNode *song, int position){
	int pos = 0;
	SongNode *currNode;
	currNode = *head;
	if(*head == NULL){
		*head = song;
	}
	else{
		if(position == 0){
			song->next = *head;
			*head = song;
		}
		
	  	else{
			pos++;
			while((position > pos) && (currNode->next != NULL)){
				if(currNode == NULL){
					//we break because we are doing the shuffle outside the loop
					break;
				}
				else{
					currNode = currNode->next;
					pos++;	
				}
			}
			song->next = currNode->next;
			currNode->next = song;
			currNode = song;
		}
	}
	return pos;

}
void printSongInfo(SongNode *song){
	printf("\nSong Title: %s", song->title);
	printf("Song Artist: %s", song->artist);
	printf("Song Album: %s",song->album);
	printf("Song Duration: %.2lf\n",song->duration);
	
}
SongNode *insertSongSorted(SongNode **head, SongNode *song){
	SongNode *currNode;
	currNode = *head;
	if(*head == NULL){
		*head = song;	
		
	}
	else if((*head)->next == NULL){
		if(strcmp((*head)->title,song->title) < 0){
			(*head)->next = song;
			
		}
		else{
			song->next = *head;
			*head = song;
			
		}
	}
	else{
		if(strcmp(song->title,(*head)->title) < 0){
                        song->next = *head;
                        *head = song;
			
                }
		else{
			while(currNode->next != NULL){
				if((strcmp(currNode->title,song->title) < 0) && (strcmp(song->title,currNode->next->title) < 0)){//if returns less than 0 it is smaller 
					song->next = currNode->next;
					currNode->next = song;
					break;
				}
				currNode = currNode->next;
			}
			if(currNode->next == NULL){
				currNode->next = song;
				
			}
		}	
	}
	return *head;
}

void printList(SongNode *head){
	SongNode *currNode;
	currNode = head;
	if(currNode == NULL){
                printf("Empty!\n");
        }
	while(currNode != NULL){
		printSongInfo(currNode);
		currNode = currNode->next;
	}
	
}
double computeDuration(SongNode *head){
	SongNode *currNode;
	currNode = head;
	double summation = 0;
	while(currNode != NULL){
		summation += currNode->duration;
		currNode = currNode->next;
	
	}
	if(summation == 0){
		printf("The playlist is empty\n");
	}
	printf("\nThe computed duration is %.2lf\n",summation);
	return summation;
}
SongNode* searchByTitle(char searchTitle[50], SongNode *head){
	SongNode *currNode;
	currNode = head;
	while(currNode != NULL){
		if(strcmp(currNode->title,searchTitle) == 0){
			printSongInfo(currNode);
			break;
		}
		else{
			currNode = currNode->next;
		}

	}
	return currNode;
}
SongNode* removeSong(SongNode **head, SongNode *remNode){
	SongNode *currNode;
	SongNode *temp;
	currNode = *head;
	if((*head) == NULL){
		return NULL;
	}
	else if(strcmp(remNode->title, (*head)->title) == 0){
		temp = currNode;
		currNode = currNode->next;
		temp->next = NULL;
		*head = currNode;

	}
	else{
		while(currNode != NULL){
			if(strcmp(currNode->next->title,remNode->title) == 0){
				temp = currNode->next;
				currNode->next = temp->next;
				temp->next = NULL;
				break;
			}
			else{
				currNode = currNode->next;
			}
			
		}
	}

	return temp;
	
}
int getNodePosition(SongNode *head, char *search){
	int position = 0;
	SongNode *currNode;
	currNode = head;
	while(currNode != NULL){
		if(strcmp(currNode->title, search) == 0){
			return position;
			break;
		}
		currNode = currNode->next;
		position++;
	}
	return position;

}
void moveUp(SongNode **head, SongNode *song){
	int position = 0;
	position = getNodePosition(*head, song->title);
	if(position == 0){
		printf("Error: Cannot move up one!\nTry Again\n");
	}
	else{
		song = removeSong(head, song);
		insertSong(head, song, position-1);
	}
}
void moveDown(SongNode **head, SongNode *song){
        int position = 0;
        position = getNodePosition(*head, song->title);
        if(song->next == NULL){
                printf("Error: Cannot move down one!\nTry Again\n");
        }
	else{
		song = removeSong(head, song);
		insertSong(head, song, position+1);
	}
}
void playlistMenu(){
	printf("\nPlaylist Menu\n");
	printf("1. Print Playlist\n");
	printf("2. Show duration\n");
	printf("3. Search By Title\n");
	printf("4. Move a Song Up\n");
	printf("5. Move a Song Down\n");
	printf("6. Remove a Song\n");
	printf("7. Go to Music Library\n");
	printf("8. Quit\n");
}
void libraryMenu(){
	printf("\nLibrary Menu\n");
	printf("1. View All Songs\n");
	printf("2. Search By Title\n");
	printf("3. Add a Song to Playlist\n");
	printf("4. Back to Playlist\n");
}
void addSongMenu(){
	printf("\nAdd Song Menu\n");
	printf("1. Add a Song to the Beggining\n");
	printf("2. Add a Song to the End\n");
	printf("3. Insert Song at a Specific Position\n");
}
char* menuChoice(char *choice){
	char inbuf[10];
	char charVal = ' ';
	printf("\nChoose an Option from the Menu:\n");
	fgets(inbuf, 10, stdin);
	sscanf(inbuf, "%c", &charVal);
	*choice = charVal;
	return choice;
}
SongNode* enterTitlePlay(SongNode *headPlay){
	char inBuf[50];
	SongNode *addSong;
	int opc;
	opc = 0;
	while(opc != 1){
		printf("Enter Title in All CAPS:\n");
        	fgets(inBuf, 50, stdin);
       		addSong = searchByTitle(inBuf, headPlay);
		if(headPlay == NULL){
			printf("The List is Empty!\nTry Again\n");
			break;
		}
		else if(addSong == NULL){
			printf("Song Not Found\nPlease Try Again\n");
		}
		
		else{
			opc = 1;	
			break;
		}

	}
	return addSong;
}
SongNode* enterTitleLib(SongNode *headLib){
        char inBuf[50];
        SongNode *addSong;
        int opc;
	opc = 0;
        while(opc != 1){
                printf("Enter Title in All CAPS:\n");
                fgets(inBuf, 50, stdin);
                addSong = searchByTitle(inBuf, headLib);
		if(headLib == NULL){
			printf("List is empty\nTry Again\n");
			break;
		}
                else if(addSong == NULL){
                        printf("Song Not Found\nPlease Try Again\n");
                }
                else{
                        opc = 1;
                        break;
                }

        }
        return addSong;
}
int deleteSong(SongNode **head, SongNode *delNode)
{
	if(delNode == NULL){
		return -1;
	}
	else{
		free(delNode);
		return 0;
	}
	
}
int main(int argc, char* argv[]){
	char inputTxtName[100];
	SongNode *headLib;
	SongNode *headPlay;
	headLib = createEmptyList();
	headPlay = createEmptyList();
	strcpy(inputTxtName, argv[1]);
	FILE* musicLib = NULL;
	musicLib = fopen(inputTxtName, "r");
	if(musicLib == NULL){
		printf("Could not open file %s \n", inputTxtName);
		exit(1);
	}
	int i;
	char inBuf[50];
	SongNode *songPtr;
	SongNode song;
	while(!feof(musicLib)){
		for(i = 0; i < 4; i++){
			if(fgets(inBuf,50,musicLib) != NULL){
				if(i == 0)
					strcpy(song.title, inBuf);
				if(i == 1)
					strcpy(song.artist, inBuf);
				if(i == 2)
					strcpy(song.album, inBuf);
				if(i == 3)
					sscanf(inBuf, "%lf", &song.duration);
			}
			else if(fgets(inBuf, 50, musicLib) == NULL){
				break;

			}
		}
		
		songPtr = createSongNode(song.title,song.artist,song.album,song.duration);
		insertSongSorted(&headLib, songPtr);	
		
	}
	char choice = ' ';
	double duration;
	duration = 0;
	char inBuf3[10];
	SongNode *addSong;
	SongNode *currNode;
	int position;
	printf("Welcome to Your IPod\nBy:Huda Khalid\n");
	do{
		playlistMenu();
		menuChoice(&choice);
		switch(choice){
			case '1':
				//print playlist
				printList(headPlay);
				break;
			case '2':
				//show duration
				duration = computeDuration(headPlay);
				break;
			case '3':
				//search by title
				addSong = enterTitlePlay(headPlay);	
				break;
			case '4':
				//move song up
				addSong = enterTitlePlay(headPlay);
				moveUp(&headPlay, addSong);
				break;
			case '5':
				//move song down
				addSong = enterTitlePlay(headPlay);
                                moveDown(&headPlay, addSong);
                                break;
			case '6':
				//remove a song from playlist
				addSong = enterTitlePlay(headPlay);
				addSong = removeSong(&headPlay, addSong);
				if(addSong == NULL)
					printf("Song was not removed\n");
				else
					printf("Song removed successfully!\n");
				insertSongSorted(&headLib, addSong);
				break;
			case '7':
				//go to music librar
				do{
					libraryMenu();
					menuChoice(&choice);
					switch(choice){
						
						case '1':
							//print library
							printList(headLib);
							break;
						case '2':
							//search by title
							addSong = enterTitleLib(headLib);
							break;
						case '3':
							//add a song to playlist
							addSong = enterTitleLib(headLib);
							addSongMenu();
							menuChoice(&choice);
							switch(choice){
								case '1':
									//add song to beg
									addSong = removeSong(&headLib, addSong);
									addSongBeg(&headPlay, addSong);
									printList(headPlay);
									break;
								case '2':
									//add song to end
									addSong = removeSong(&headLib, addSong);
									appendSong(&headPlay, addSong);
									printList(headPlay);
									break;
								case '3':
									//insert song at specific position
									printf("Enter Specific Position:\n");
									fgets(inBuf3, 10, stdin);
									sscanf(inBuf3, "%d", &position);
									addSong = removeSong(&headLib, addSong);
									insertSong(&headPlay, addSong, position);
									printList(headPlay);
									break;
								default:
									printf("Invalid Input! Try Again\n");
									break;
							}
				
						case '4':
							//go back to playlist menu
							printf("Going back to Playlist Menu\n");
							break;	
						default:
							printf("Invalid Input! Try Again\n");
							break;
						
					}
				}while(choice != '4');
					break;
				
			case '8':
				//quit and call deleteSong
				addSong = headLib;	
				while(addSong != NULL){
					addSong = headLib;
					currNode = removeSong(&headLib,addSong);
					deleteSong(&headLib,currNode);
					
				}
				
                                addSong = headPlay;
                                while(addSong != NULL){
                                        addSong = headPlay;
                                        currNode = removeSong(&headPlay,addSong);
                                        deleteSong(&headPlay,currNode);

                                }
                                
				break;
			default:
				printf("Invalid Input! Try Again\n");
				break;
		}
		
	}while(choice != '8');
	return 0;

}
