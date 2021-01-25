from sklearn.feature_extraction.text import TfidfVectorizer
import os
import pandas as pd

# import IndividualLDA for redundancies
import IndividualLDA

# import all paths from BuildData
from BuildData import *


all_txt_files = []

# adds each transcript to a list
for file in transcriptsLocation.rglob("*.txt"):
    all_txt_files.append(file.name)

all_txt_files.sort()

all_docs = []
# adds each transcript to a list in string format for processing
for txt_file in all_txt_files:
    with open(transcriptsLocation / txt_file) as f:
        txt_file_as_string = f.read()
    all_docs.append(txt_file_as_string)

vectorizer = TfidfVectorizer(max_df=.65, min_df=1, stop_words=IndividualLDA.stop_words, use_idf=True, norm=None)
transformed_documents = vectorizer.fit_transform(all_docs)

transformed_documents_as_array = transformed_documents.toarray()

# construct a list of output file paths using the previous list of text files the relative path for tf_idf_output
output_filenames = [str(txt_file).replace(".txt", ".csv").replace("txt/", "tf_idf_output/") for txt_file in
                    all_txt_files]

def tf_idf_driver():
    # loop each item in transformed_documents_as_array, using enumerate to keep track of the current position
    for counter, doc in enumerate(transformed_documents_as_array):
        # construct a dataframe
        tf_idf_tuples = list(zip(vectorizer.get_feature_names(), doc))

        one_doc_as_df = pd.DataFrame.from_records(tf_idf_tuples, columns=['term', 'score']) \
            .sort_values(by='score', ascending=False).reset_index(drop=True)

        # output to a csv using the enumerated value for the filename
        one_doc_as_df.to_csv(os.path.join(tf_IDFLocation, output_filenames[counter]))
