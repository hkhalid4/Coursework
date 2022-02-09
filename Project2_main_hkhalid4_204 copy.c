#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct Bike_struct{
   char maintenanceFlag;
   int bikeId, endStationId;
   int numTrips, totalDuration;
   float totalMinutes;
}Bike;

typedef struct Trip_struct{
   char membershipType[12];
   int startStationId, endStationId, bikeId,
       duration, startHr, startMin;
}Trip;

void printMainMenu(){
        printf("Main menu:\n	Read Trip Data (Press 1)\n	Run Metrics(Press 2)\n	Print Menu (Press 3)\n	Flag Bikes for Maintenance (Press 4)\n	Update Bike Inventory (Press 5)\n	Quit (Press Q)\n");
}

void metricMenu(){
	printf("Average number of trips per hour (Press 1)\n");
	printf("The hour with largest number of trips (Press 2)\n");
	printf("A report of the count of trips in each hour (Press 3)\n");
	printf("Average duration of the trips (in both milliseconds and minutes) (Press 4)\n");
	printf("The trip with the longest duration (in both milliseconds and minutes) (Press 5)\n");
	printf("The 5 busiest starting stations(that had the largest number of trips in the day) (Press 6)\n");
	printf("The 5 busiest ending stations (that had the largest number of trips in the day) (Press 7)\n");
	printf("The percentage of trips in each membership type (Press 8)\n");
	printf("The 10 bikes with the longest duration in minutes (Press 9)\n");
	printf("Where the 10 bikes above are located at the end of the day (Press 10)\n");
	printf("The 10 bikes with the most trips (Press 11)\n");
	printf("How many bikes were only used for 2 or less trips (Press 12)\n");
	printf("For the bikes with 2 or less trips, make a list of the start station IDs and the number of trips for each of these stations (Press 13)\n");
}

void printMenu(){
	printf("Print all the trip data (Press 1)\n");
	printf("Print all the bike inventory (Press 2)\n");
	printf("Print the CEO report (Press 3)\n");
	printf("Print one bike (Press 4)\n");

}
char* menuChoice(char *choice){
        char inBuf[50];
        char charVal = ' ';
        printf("Choose an option from the menu:\n");
        fgets(inBuf, 50, stdin);
        sscanf(inBuf, "%c", &charVal);
        *choice = charVal;
        return choice;
}
float avgNumTrip(int *numLine){
	float avg = 0;
	avg =(float) *numLine/24;
	return avg;
}

int hourLarNumTrips(Trip *tripArray, int *numLine){
	int hours[24];
	int index;
	int i;
	for (i = 0; i < 24; i++)
		hours[i] = 0;
	
	for(i = 0; i < *numLine; i++){
		int x = tripArray[i].startHr;
		hours[x] += 1;	
	}
	int largNum = 0;
	for(i = 0; i < 24; i++){
		if(largNum <=  hours[i]){
			largNum = hours[i];
			index = i; 
		}
	}
	return index;
}
int* countTrips(Trip *tripArray, int *numLine){
	int* hours;
	hours = malloc (sizeof(int) * 24);
   
        int i;
	if(hours == NULL){
		printf("Not enough memory!\n");
		exit(1);
	}
        for (i = 0; i < 24; i++)
                hours[i] = 0;

        for(i = 0; i < *numLine; i++){
                int x = tripArray[i].startHr;
                hours[x] += 1;
        }
	/*for(i = 0; i < 24; i++){
		printf("Hour %d: %d\n",i, hours[i]);
		
	}*/
	return hours;
	
	
}

float avgDurTrip(Trip *tripArray, int *numLine){
	float avg;
	int i;
	long int sum = 0;
	for(i = 0; i < *numLine; i++){
		sum += tripArray[i].duration;
	}
	avg = sum / *numLine;
	printf("%.2f\n",avg);
	return avg;
}
int longDur(Trip *tripArray, int *numLine){
	int duration = 0;
	int index = 0;
	int i;
	for(i = 0; i < *numLine; i++){
                  if(duration <=  tripArray[i].duration){
                          duration  = tripArray[i].duration;
            		  index = i;    
                  }
          }
	//printf("Longest trip is %d\n", tripArray[index].duration);
	return tripArray[index].duration;

}
int comparator(const void* a, const void* b){
	Trip arg1 = *(const Trip*)a;
	Trip arg2 = *(const Trip*)b;
	if(arg1.startStationId > arg2.startStationId) return -1;
	if(arg1.startStationId < arg2.startStationId) return 1;
	return 0;

}
int comp(const void* a, const void* b){
	 Trip arg1 = *(const Trip*)a;
         Trip arg2 = *(const Trip*)b;
         if(arg1.endStationId > arg2.endStationId) return -1;
         if(arg1.endStationId < arg2.endStationId) return 1;
         return 0;
}

int comp2(const void* a, const void* b){
	Trip arg1 = *(const Trip*)a;
        Trip arg2 = *(const Trip*)b;
        if(arg1.bikeId > arg2.bikeId) return -1;
        if(arg1.bikeId < arg2.bikeId) return 1;
        return 0;
}
//create comp in desc order to get the first 10 bikes and store them in an array
int comp3(const void* a, const void* b){
        Bike arg1 = *(const Bike*)a;
        Bike arg2 = *(const Bike*)b;
        if(arg1.totalDuration > arg2.totalDuration) return -1;
        if(arg1.totalDuration < arg2.totalDuration) return 1;
        return 0;
}
void bikeLongDur(Bike *bikeArray){
	qsort(bikeArray, 4000, sizeof(Bike), comp3);
	int i;
	for(i = 0; i < 10; i++){
		printf("The 10 bikes with the longest duration in minutes: \nBike Id: %d, duration %.2lf(minutes)\n",bikeArray[i].bikeId, bikeArray[i].totalMinutes);
		
	}
	
}
void updateMainFlag(Bike *bikeArray){
	qsort(bikeArray, 4000, sizeof(Bike), comp3);
        int i;
        for(i = 0; i < 10; i++){
		bikeArray[i].maintenanceFlag = '1';
		printf("Maintenance flag:%c BikeId:%d End Station Id:%d Number of Trips:%d Total Duration:%d Total Minutes:%.2f\n",bikeArray[i].maintenanceFlag,bikeArray[i].bikeId, bikeArray[i].endStationId, bikeArray[i].numTrips, bikeArray[i].totalDuration, bikeArray[i].totalMinutes);
        	        
        }

}
void locEndOfDay(Bike *bikeArray){
	int i;
        for(i = 0; i < 10; i++){
                printf("Where the 10 bikes above are located at the end of the day: \nBike Id: %d, End StationId:%d\n",bikeArray[i].bikeId, bikeArray[i].endStationId);
        }
}
void printAllTripData(Trip *trip, int *numLine){
	int i;
	for(i = 0; i < *numLine; i++){
		printf("Membership Type:%s Start StationId:%d End StationId:%d BikeId:%d Duration:%d StartHr:%d StartMin%d\n",trip[i].membershipType,trip[i].startStationId,trip[i].endStationId, trip[i].bikeId, trip[i].duration, trip[i].startHr, trip[i].startMin);
	}
	
} 
int comp4(const void* a, const void* b){
        Bike arg1 = *(const Bike*)a;
        Bike arg2 = *(const Bike*)b;
        if(arg1.numTrips > arg2.numTrips) return -1;
        if(arg1.numTrips < arg2.numTrips) return 1;
        return 0;
}
void bikeMostTrip(Bike *bikeArray){
        qsort(bikeArray, 4000, sizeof(Bike), comp4);
        int i;
        for(i = 0; i < 10; i++){
                printf("The 10 bikes with the most trips: \nBike Id: %d, The number of trips:%d\n",bikeArray[i].bikeId, bikeArray[i].numTrips);
        }

}
int comp5(const void* a, const void* b){
        Bike arg1 = *(const Bike*)a;
        Bike arg2 = *(const Bike*)b;
        if(arg1.numTrips < arg2.numTrips) return -1;
        if(arg1.numTrips > arg2.numTrips) return 1;
        return 0;
}
void bikes2orless(Bike *bikeArray){
	qsort(bikeArray, 4000, sizeof(Bike), comp5);
	int i;
	int bikes = 0;
	for(i = 0; i < 4000; i++){
		if((bikeArray[i].numTrips <= 2) && (bikeArray[i].numTrips > 0)){
			bikes++;
			printf("For the bikes with 2 or less trips:\n StationId: %d , The number of trips: %d\n", bikeArray[i].endStationId, bikeArray[i].numTrips);
		}
	}
	printf("How many bikes were only used for 2 or less trips: %d\n",bikes);


}
void print1bike(Bike *bikeArray){
	char inBuf[20];
        int bikeId = 0;
        printf("Enter a Bike ID:\n");
        fgets(inBuf, 20, stdin);
        sscanf(inBuf, "%d", &bikeId);
	int i;
	Bike *oneBike;
	oneBike = malloc(sizeof(int) * 200);
	for(i = 0; i < 4000; i++){
		if(bikeArray[i].bikeId == bikeId){
			*oneBike = bikeArray[i];
		}
	}
	 printf("Maintenance flag:%c BikeId:%d End Station Id:%d Number of Trips:%d Total Duration:%d Total Minutes:%.2f\n",oneBike->maintenanceFlag,oneBike->bikeId, oneBike->endStationId, oneBike->numTrips, oneBike->totalDuration, oneBike->totalMinutes);
	
}
void printAllBikeInv(Bike *bikeArray){
	int i;
	for(i = 0; i < 4000; i++){
		printf("Maintenance flag:%c BikeId:%d End Station Id:%d Number of Trips:%d Total Duration:%d Total Minutes:%.2f\n",bikeArray[i].maintenanceFlag,bikeArray[i].bikeId, bikeArray[i].endStationId, bikeArray[i].numTrips, bikeArray[i].totalDuration, bikeArray[i].totalMinutes);
	}
}
void uniqueBikeId(Trip *tripArray, Bike *bikeArray, int *numLine){
	qsort(tripArray, *numLine, sizeof(Trip), comp2);
	int i;
	int index = 0;
	bikeArray[index].bikeId = tripArray[index].bikeId;
	bikeArray[index].maintenanceFlag = '0';
	bikeArray[index].endStationId = tripArray[index].endStationId;
	bikeArray[index].numTrips = 1;
	bikeArray[index].totalDuration = tripArray[index].duration;
	bikeArray[index].totalMinutes = (float)tripArray[index].duration/600000;
	for(i = 1; i < *numLine; i++){
		if(tripArray[i].bikeId == bikeArray[index].bikeId){
			bikeArray[index].numTrips += 1;
			bikeArray[index].totalDuration += tripArray[i].duration;
			bikeArray[index].totalMinutes +=(float)bikeArray[index].totalDuration/60000;
				
		}else{
			index++;
			bikeArray[index].bikeId = tripArray[i].bikeId;;
			bikeArray[index].maintenanceFlag = '0';
			bikeArray[index].endStationId = tripArray[i].endStationId;
			bikeArray[index].numTrips = 1;
			bikeArray[index].totalDuration = tripArray[i].duration;
			bikeArray[index].totalMinutes = (float)tripArray[i].duration/60000;;
		}
		bikeArray[index].endStationId = tripArray[i].endStationId;
		
	}
	
}


int* busyEndStation(Trip *tripArray, int *numLine){
	int* top5NumTrips;
        top5NumTrips = malloc(sizeof(int) * 5);
        qsort(tripArray, *numLine, sizeof(Trip), comp);
        if(top5NumTrips == NULL){
		printf("Not enough memory!\n");
		exit(1);
	} 
	int i;
        int currentNumTrips = 0;
        int curStationId;
        int *top5StationId;
        int j;
        top5StationId = malloc(sizeof(int)* 5);
	if(top5StationId == NULL){
                printf("Not enough memory!\n");
                exit(1);
        } 
     
         for(i = 0; i < 5; i++){
                 top5NumTrips[i] = 0;
                 top5StationId[i] = 0;
         }
         for(i = 0; i < *numLine; i++){
                 if(curStationId != tripArray[i].endStationId){
                         //check all array indices, find if tis bigger than any
                         int smallest = 2000000000;
                         int smallestIndex = 0;
                         int bigger = 0;
                         for(j = 0; j < 5; j++){
                                 if (smallest > top5NumTrips[j]) {
                                         smallest = top5NumTrips[j];
                                         smallestIndex = j;
                                 }
                                 if(currentNumTrips > top5NumTrips[j]){
                                         bigger = 1;
                                 }
                         }
                         if (bigger) {
                                 top5NumTrips[smallestIndex] = currentNumTrips;
                                 top5StationId[smallestIndex] = curStationId;
                         }
                         currentNumTrips = 1;
                         curStationId = tripArray[i].endStationId;
                 } else {
                         currentNumTrips++;
                 }
		
 
         }
       
	return top5StationId;
}

float percMemType(Trip *tripArray, int *numLine){
	int regMem = 0;
	int i;
	int casMem = 0;
	char casMemberType[12] = "Casual";
	float percentage = 0;
	char regMemberType[12] = "Registered";
	for(i = 0; i < *numLine; i++){
		if(strcmp(regMemberType,tripArray[i].membershipType) == 0){
			regMem++;
		}
	}
	int j;
	for(j = 0; j < *numLine; j++){
		if(strcmp(casMemberType,tripArray[j].membershipType) == 0){
			casMem++;
		}
	}
	percentage = 1.0 * regMem / *numLine;
	percentage = percentage * 100;
	printf("%.2f \n",percentage);
	return percentage;

}
int* busyStartStation(Trip *tripArray, int *numLine){
	int* top5NumTrips;
	top5NumTrips = malloc(sizeof(int) * 5);
	if(top5NumTrips == NULL){
                printf("Not enough memory!\n");
                exit(1);
        } 
	qsort(tripArray, *numLine, sizeof(Trip), comparator);
	int i;
	int currentNumTrips = 0;
	int curStationId;
	int *top5StationId;
	int j;
	top5StationId = malloc(sizeof(int)* 5);
	if(top5StationId == NULL){
                printf("Not enough memory!\n");
                exit(1);
        } 
   
	for(i = 0; i < 5; i++){
		top5NumTrips[i] = 0;
		top5StationId[i] = 0;
	}
	for(i = 0; i < *numLine; i++){
		if(curStationId != tripArray[i].startStationId){
			//check all array indices, find if tis bigger than any
			int smallest = 2000000000;
			int smallestIndex = 0;
			int bigger = 0;
			for(j = 0; j < 5; j++){
				if (smallest > top5NumTrips[j]) {
					smallest = top5NumTrips[j];
					smallestIndex = j;
				}
				if(currentNumTrips > top5NumTrips[j]){
					bigger = 1;
				}
			}
			if (bigger) {
				top5NumTrips[smallestIndex] = currentNumTrips;
				top5StationId[smallestIndex] = curStationId;
			}
			currentNumTrips = 1;
			curStationId = tripArray[i].startStationId; 
		} else {
			currentNumTrips++;
		}	
	
	}
	return top5StationId;
}
FILE* displayOnFile(){
	char inBuf[20];
	char outputName[50];
	FILE* outFile = NULL;
	printf("Enter name of output file for writing \n");
	fgets(inBuf, 20, stdin);
	sscanf(inBuf, "%s", outputName);
	outFile = fopen(outputName, "w");	
	if(outFile == NULL){
		printf("Could not open file %s \n",outputName);
		exit(1);
	}
	outFile = fopen(outputName, "a");
	return outFile;
}
Trip* readFile(int *numLine){
	Trip *trip;
        trip = malloc(sizeof(Trip) * *numLine);
        if(trip == NULL){
                printf("Not enough memory!\n");
                exit(1);
        }
	char inBuf[50];
	int numOfLine = 0;
	FILE* inFile = NULL;
	char inputName[50];
	printf("Enter name of the input file for reading\n");
	fgets(inBuf, 50, stdin);
	sscanf(inBuf, "%s", inputName);
	inFile = fopen(inputName, "r");
	if(inFile == NULL){
		printf("Could not open file %s \n",inputName);
		exit(1);
	} 
	fgets(inBuf,50, inFile);
	sscanf(inBuf, "%d", &numOfLine);
	trip = (Trip *) malloc (numOfLine * sizeof(Trip));
	int i;
	char inBuf2[numOfLine];
	*numLine = numOfLine; //change whats at 0x457...
	for(i = 0; i < numOfLine; i++){
		fgets(inBuf2, numOfLine, inFile);
		sscanf(inBuf2, "%s %d %d %d %d %d %d\n",trip[i].membershipType,&trip[i].startStationId,&trip[i].endStationId, &trip[i].bikeId, &trip[i].duration, &trip[i].startHr, &trip[i].startMin); 
	}
	fclose(inFile);
	return trip;
}

int main(){
        printf("Welcome to the Bike Share Operation\n");
        char choice = ' ';
	Trip *trip;
	Bike *bike;
	bike = malloc(sizeof(Bike) * 4000);
	if(bike == NULL){
		printf("Not enough memory!\n");
		exit(1);
	}
	int numLine=5; //value=5 location=0x4576543
	int* numlineptr = &numLine; //value=0x4576543 location=dont care
	FILE* outFile; 
        do{
                printMainMenu();
                menuChoice(&choice);
		switch(choice){
			case '1':
				//prompt user for file name containing trip data
				trip = readFile(numlineptr);
				uniqueBikeId(trip,bike,numlineptr);
				break;
						
			case '2':
				//run metrics menu
				metricMenu();
				menuChoice(&choice);
				switch(choice){
					case '1':
						//average number of trips per hour and pass in trip
						//avgNumTrip(numlineptr);
						printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
						menuChoice(&choice);
						switch(choice){
							case '1':
								printf("Average number of trips per hour:%.2f\n",avgNumTrip(numlineptr));
								break;
							case '2':
							
								outFile = displayOnFile();
								fprintf(outFile,"Average number of trips per hour:%.2f\n",avgNumTrip(numlineptr));
								
								break;
							default:
								printf("Invalid input! Try Again\n");
								break;
						}
						break;
					case '2':
						//hourLarNumTrips(trip,numlineptr);
						printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
                                                       case '1':
                                                                printf("The hour with largest number of trips is Hour#%d\n",hourLarNumTrips(trip,numlineptr));
                                                                break;
                                                       case '2':
                                                               
                                                               outFile = displayOnFile();
                                                               fprintf(outFile,"The hour with largest number of trips is Hour#%d\n",hourLarNumTrips(trip,numlineptr));
                                                               break;
                                                      default:
                                                              printf("Invalid input! Try Again\n");
                                                              break;
                                                }

						break;
					case '3':
						//countTrips(trip, numlineptr);
						printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
							int* hours;
							int i;
                                                       case '1':
                                                                hours = countTrips(trip, numlineptr);
								for(i=0; i < 24; i++){
									printf("Hour %d: %d\n",i, hours[i]);
								}	
                                                                break;
                                                       case '2':
                                                               outFile = displayOnFile();
								hours = countTrips(trip, numlineptr);
								for(i = 0; i < 24; i++){
                                                             		fprintf(outFile,"Hour %d: %d\n",i,hours[i]);
								}
                                                               break;
                                                      default:
                                                              printf("Invalid input! Try Again\n");
                                                              break;
                                                }
						break;
					case '4':
						//avgDurTrip(trip, numlineptr);
						   printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
                                                       case '1':
                  						printf("Average duration of the trips: %.2f(milliseconds), %.2f(minutes)\n",avgDurTrip(trip,numlineptr),avgDurTrip(trip,numlineptr)/60000);
                                                                break;
                                                       case '2':
                                                               outFile = displayOnFile();
                                                               fprintf(outFile,"Average duration of the trips: %.2f(milliseconds), %.2f(minutes)\n",avgDurTrip(trip,numlineptr),avgDurTrip(trip,numlineptr)/60000);
                                                               break;
                                                      default:
                                                              printf("Invalid input! Try Again\n");
                                                              break;
                                                }
						break;
					case '5':
						//longDur(trip,numlineptr);
						  printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
                                                       case '1':
                                                                printf("Longest duration in trips:: %d(milliseconds), %.2f(minutes)\n",longDur(trip,numlineptr),(float)longDur(trip,numlineptr)/60000);
                                                                break;
                                                       case '2':
                                                               outFile = displayOnFile();
                                                               fprintf(outFile,"Longest duration in trips:: %d(milliseconds), %.2f(minutes)\n",longDur(trip,numlineptr),(float)longDur(trip,numlineptr)/60000);
                                                               break;
                                                      default:
                                                              printf("Invalid input! Try Again\n");
                                                              break;
                                                }
						break;
					case '6':
						//busyStartStation(trip,numlineptr);
						   printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
                                                        int* busyStartStationId;
                                                        int i;
                                                       case '1':
                                                                busyStartStationId = busyStartStation(trip,numlineptr);
								fprintf(outFile,"The 5 busiest starting stations:\n");
                                                                for(i=0; i < 5; i++){
                                                                        printf("%d\n",busyStartStationId[i]);
                                                                }
                                                                break;
                                                       case '2':
                                                               outFile = displayOnFile();
                                                                busyStartStationId = busyStartStation(trip,numlineptr);
								fprintf(outFile,"The 5 busiest starting stations:\n");
                                                                for(i=0; i < 5; i++){
                                                                       fprintf(outFile,"%d\n",busyStartStationId[i]);
                                                                }
								break;
                                                      default:
                                                              printf("Invalid input! Try Again\n");
                                                              break;
                                                }
						break;
					case '7':
						//busyEndStation(trip, numlineptr);
						    printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
                                                        int* busyEndStationId;
                                                        int i;
                                                       case '1':
                                                                busyEndStationId = busyEndStation(trip,numlineptr);
                                                                fprintf(outFile,"The 5 busiest ending stations:\n");
                                                                for(i=0; i < 5; i++){
                                                                        printf("%d\n",busyEndStationId[i]);
                                                                }
                                                                break;
                                                       case '2':
                                                               outFile = displayOnFile();
                                                                busyEndStationId = busyEndStation(trip,numlineptr);
                                                                fprintf(outFile,"The 5 busiest ending stations:\n");
                                                                for(i=0; i < 5; i++){
                                                                       fprintf(outFile,"%d\n",busyEndStationId[i]);
                                                                }
                                                                break;
                                                      default:
                                                              printf("Invalid input! Try Again\n");
                                                              break;
                                                }
						break;
					case '8':
						//percMemType(trip,numlineptr);
						 printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
                                                        case '1':
                                                                printf("The percentage in each membership type:\nRegistered:%.2f\nCasual:%.2f\n",percMemType(trip,numlineptr),100-percMemType(trip,numlineptr));
                                                                break;
                                                        case '2':

                                                                outFile = displayOnFile();
                                                                fprintf(outFile,"The percentage in each membership type:\nRegistered:%.2f\nCasual:%.2f\n",percMemType(trip,numlineptr),100-percMemType(trip,numlineptr));

                                                                break;
                                                        default:
                                                                printf("Invalid input! Try Again\n");
                                                                break;
                                                }
						break;
					case '9':
						  printf("Choose whether to display data on screen (press 1) or to output file (press 2):\n");
                                                menuChoice(&choice);
                                                switch(choice){
                                                        case '1':
								bikeLongDur(bike);	
                                                                break;
                                                        case '2':
                                                                outFile = displayOnFile();
								Bike *bikeArray;
								bikeArray = bike;
								 qsort(bikeArray, 4000, sizeof(Bike), comp3);
        							int i;
        							for(i = 0; i < 10; i++){
               								 fprintf(outFile,"The 10 bikes with the longest duration in minutes: \nBike Id: %d, duration %.2lf(minutes)\n",bikeArray[i].bikeId, bikeArray[i].totalMinutes);

        							}
                                                                break;
                                                        default:
                                                                printf("Invalid input! Try Again\n");
                                                                break;
                                                }
                                                break;
					default:
                                                printf("Invalid input! Try Again\n");
                                                break;
				}
				break;
			case '3':
				//display printMenu
				printMenu();
				menuChoice(&choice);
				switch(choice){
					case '1':
						printAllTripData(trip,numlineptr);
						break;
					case '2':
						printAllBikeInv(bike);
						break;
					case '3':
						bikeLongDur(bike);
						locEndOfDay(bike);
						bikeMostTrip(bike);
						bikes2orless(bike);
						break;	
					case '4':
						print1bike(bike);
						break;
				}
				break;
			case '4':
				updateMainFlag(bike);
				break;
			case '5':
				//update bike inventory
			case 'Q':
			case 'q':
				break;
			default:
				printf("Invalid input! Try Again\n");
				break;
			
		}
        }while((choice != 'Q')&&(choice != 'q'));
        return 0;

}
