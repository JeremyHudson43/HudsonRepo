# Some of this code was found from the following link:
# https://www.machinelearningplus.com/nlp/topic-modeling-gensim-python/

import pandas as pd

# import IndividualLDA for redundancies
import IndividualLDA

# import all paths from BuildData
from BuildData import *


all_txt_files = []

# adds each transcript to a list
for file in IndividualLDA.transcriptsLocation.rglob("*.txt"):
    all_txt_files.append(file.name)

all_docs = []

# adds each transcript to a list in string format for processing
for txt_file in all_txt_files:
    with open(transcriptsLocation / txt_file, 'rb') as f:
        txt_file_as_string = f.read()
        # new_words = [word for word in txt_file_as_string.split() if len(word) > 3]

        all_docs.append(txt_file_as_string)

df = pd.DataFrame(all_docs)
data = df.values.tolist()

# tokenizes words using sent_to_words in IndividualLDA
data_words = list(IndividualLDA.sent_to_words(data))

# remove stop words and lemmatize using process_words in IndividualLDA
data_ready = IndividualLDA.process_words(data_words)

# Create Dictionary
id2word = IndividualLDA.corpora.Dictionary(data_ready)

# Term Document Frequency
corpus = [id2word.doc2bow(text) for text in data_ready]

numOfDocs = len(all_docs)

lda_model = IndividualLDA.gensim.models.ldamodel.LdaModel(corpus=corpus,
                                            id2word=id2word,
                                            num_topics=numOfDocs * 4,
                                            passes=100,
                                            alpha='auto',
                                            minimum_probability=0.05,
                                            per_word_topics=True)

doc_lda = lda_model[corpus]


def format_topics_sentences(ldamodel=lda_model, corpus=corpus, texts=data):
    # Init output
    sent_topics_df = pd.DataFrame()

    # Get main topic in each document
    for i, row_list in enumerate(ldamodel[corpus]):
        row = row_list[0] if ldamodel.per_word_topics else row_list
        # print(row)
        row = sorted(row, key=lambda x: (x[1]), reverse=True)
        # Get the Dominant topic, Perc Contribution and Keywords for each document
        for j, (topic_num, prop_topic) in enumerate(row):
            if j == 0:  # => dominant topic
                wp = ldamodel.show_topic(topic_num)
                topic_keywords = ", ".join([word for word, prop in wp])
                sent_topics_df = sent_topics_df.append(
                    pd.Series([int(topic_num), round(prop_topic, 4), topic_keywords]), ignore_index=True)
            else:
                break
    sent_topics_df.columns = ['Dominant_Topic', 'Perc_Contribution', 'Topic_Keywords']

    # Add original text to the end of the output
    contents = pd.Series(all_txt_files)

    sent_topics_df = pd.concat([sent_topics_df, contents], axis=1)
    return (sent_topics_df)


df_topic_sents_keywords = format_topics_sentences(ldamodel=lda_model, corpus=corpus, texts=data_ready)


# Finds the dominant topic in the model for each text file
def dominant_topic():
    # Format
    df_dominant_topic = df_topic_sents_keywords.reset_index()
    df_dominant_topic.columns = ['Document_No', 'Dominant_Topic', 'Topic_Perc_Contrib', 'Keywords', 'Text']

    df_dominant_topic.to_csv(ldaLocation / "Dominant file topic.csv")


# Find the text file that most strongly represents a certain topic
def representative_file():
    # Group top 5 sentences under each topic
    sent_topics_sorteddf_mallet = pd.DataFrame()

    sent_topics_outdf_grpd = df_topic_sents_keywords.groupby('Dominant_Topic')

    for i, grp in sent_topics_outdf_grpd:
        sent_topics_sorteddf_mallet = pd.concat([sent_topics_sorteddf_mallet,
                                                 grp.sort_values(['Perc_Contribution'], ascending=[0]).head(1)],
                                                axis=0)

    # Reset Index
    sent_topics_sorteddf_mallet.reset_index(drop=True, inplace=True)

    # Format
    sent_topics_sorteddf_mallet.columns = ['Topic_Num', "Topic_Perc_Contrib", "Keywords", "Text"]

    # Show
    sent_topics_sorteddf_mallet.head()

    sent_topics_sorteddf_mallet.to_csv(ldaLocation / "Most Representative file per topic.csv")


# Find the percentage of documents a certain topic occurs in
def topic_percentage():
    df_dominant_topics = \
        df_topic_sents_keywords[['Dominant_Topic', 'Topic_Keywords']].groupby(['Dominant_Topic', 'Topic_Keywords'])[
            'Topic_Keywords'].count().reset_index(name='Num_Documents')

    df_dominant_topics['Perc_Documents'] = ((df_dominant_topics['Num_Documents']) / (
        df_dominant_topics['Num_Documents'].sum())) * 100

    df_dominant_topics.to_csv(ldaLocation / "Topic Percentage across files.csv")



