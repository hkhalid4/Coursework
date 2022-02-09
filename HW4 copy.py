import pandas as pd
import numpy as np
import sklearn as sk
import matplotlib
import sklearn as sk
import scipy
import imblearn
from imblearn.over_sampling import RandomOverSampler
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import KFold
from sklearn.metrics import accuracy_score
from sklearn.naive_bayes import GaussianNB
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler
from sklearn.tree import DecisionTreeClassifier
#read in the training file data
trainData = pd.read_csv('/Users/hudakhalid/Documents/CS484/HW4_khalid/additional_files/train.dat',delim_whitespace='true',usecols=["userID","movieID","rating"])
trainData.head()
#print(trainData)
trainData = trainData.astype(int)
trainData = np.array(trainData)
#print(trainData)
#userID and movieID
X = trainData[:,:2]

#ratings
y = trainData[:,-1]
#print(X)
#print(y)

folds = KFold(n_splits=4)
knnArray = []
knnPredict = []
clusterArray = []
predictions = []
dtArray = []
gausNBArray = []
X_train, X_test, y_train, y_test = train_test_split(X, y,test_size=0.3)

for train, test in folds.split(X):
    X_train, X_test, y_train, y_test = X[train], X[test], y[train], y[test]

    scaler = StandardScaler()
    scaler.fit(X_train)
    X_train = scaler.transform(X_train)
    X_test = scaler.transform(X_test)
    pca = PCA()
    pca.fit(X_train)
    pca.fit(X_test)
    X_train = pca.transform(X_train)
    X_test = pca.transform(X_test)

    kmeans1 = KMeans(n_clusters=5)
    kmeans1.fit(X_train, y_train)
    predictions = kmeans1.fit_predict(X_test)
    clusterArray.append(accuracy_score(y_test, predictions))

    knn = KNeighborsClassifier(n_neighbors=5,weights='uniform',p=1)
    knn.fit(X_train, y_train)
    knnPredict = knn.predict(X_test)
    knnArray.append(accuracy_score(y_test,knnPredict))

    DT = DecisionTreeClassifier(class_weight='balanced')
    DT.fit(X_train, y_train)
    DTPredict = DT.predict(X_test)
    dtArray.append(accuracy_score(y_test, DTPredict))

    gausNB = GaussianNB()
    gausNB.fit(X_train, y_train)
    gausNBPredict = gausNB.predict(X_test)
    gausNBArray.append(accuracy_score(y_test, gausNBPredict))

print('\nClassifying with Kmeans Clustering')
print(predictions)
print(np.mean(clusterArray))

print('\nClassifying with knn')
print(knnPredict)
print(np.mean(knnArray))

print('\nClassifying with decision tree')
print(DTPredict)
print(np.mean(dtArray))

print('\nClassifying with Naive Bayes')
print(gausNBPredict)
print(np.mean(gausNBArray))

#test set
testData = pd.read_csv('/Users/hudakhalid/Documents/CS484/HW4_khalid/additional_files/test.dat',delim_whitespace='true',usecols=["userID","movieID"])
testData.head()
#print(testData)
testData = testData.astype(int)
testData = np.array(testData)
#print(testData)
X_train = X
y_train = y
X_test = testData
knnArray = []
knnPredict = []
clusterArray = []
predictions = []
dtArray = []
gausNBArray = []

scaler = StandardScaler()
scaler.fit(X_train)
X_train = scaler.transform(X_train)
X_test = scaler.transform(X_test)
pca = PCA()
pca.fit(X_train)
pca.fit(X_test)
X_train = pca.transform(X_train)
X_test = pca.transform(X_test)

gausNB = GaussianNB()
gausNB.fit(X_train, y_train)
gausNBPredict = gausNB.predict(X_test)

print(gausNBPredict)

formatFile = open('hkhalidHW4.txt','w')
for i in gausNBPredict:
    formatFile.write(str(i) + '\n')

formatFile.close()