from collections import Counter
from nltk.corpus import wordnet
import re
import os
import csv

from BuildData import *

# import all redundancies
import IndividualLDA


def commonWordsDriver():
    for folderName, subfolders, fileName in os.walk(textLocation):

        # try:
        for file in fileName:

            fileFullPath = open(os.path.join(folderName, file), 'rb')

            data = fileFullPath.read().lower().splitlines()

            # filter to words not in stop words and that have a length of greater than two
            texts = [word for word in IndividualLDA.simple_preprocess(str(data)) if word not in IndividualLDA.stop_words and wordnet.synsets(word)]

            # filter out non-letter values
            final_list = [re.sub(r'[^a-zA-Z]', '', i) for i in str(texts).split()]
            final_list = list(filter(None, final_list))

            counter = Counter(final_list)

            most_occur = counter.most_common(1000)

            file = file.replace(".txt", ".csv")

            with open(os.path.join(commonWordsLocation, file), 'w', newline='') as newFile:
                writer = csv.writer(newFile)

                # writes first word to column A and number of occurrences to column B
                for a, b in most_occur:
                    writer.writerow([a, b])

