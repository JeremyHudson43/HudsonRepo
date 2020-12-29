from top2vec import Top2Vec
from BuildData import *
import sys
import os

all_txt_files = []

# adds each transcript to a list
for file in transcriptsLocation.rglob("*.txt"):
    all_txt_files.append(file.name)

all_txt_files.sort()

all_docs = []
# adds each transcript to a list in string format for processing
for txt_file in all_txt_files:
    with open(transcriptsLocation / txt_file, encoding = "utf-8") as f:
        txt_file_as_string = f.read()
    all_docs.append(txt_file_as_string)


model = Top2Vec(all_docs, speed="fast-learn")
model.save("modelTwo")

model = Top2Vec.load("modelTwo")


def printMetaData():
    # get total number of model topics
    print("\nThe total number of model topics are: " + str(model.get_num_topics()))

    # This will return the number of documents most similar to each topic.
    topic_sizes, topic_nums = model.get_topic_sizes()
    print("\nThe number of documents most similar to each topic are: " + str(topic_sizes))
    print("The unique indexes of each topic are: " + str(topic_nums))


def printModelTopics():
    # This will return the topics in decreasing size
    topic_words, word_scores, topic_nums = model.get_topics()
    for word in topic_words:
        print("\nKeywords of total model topic\n\n" + str(word))


# Search for topics most similar to a single keyword
def similarTopics():
    topic_words, word_scores, topic_scores, topic_nums = model.search_topics(keywords=["love"], num_topics=2)
    for word, score in zip(topic_words, topic_scores):
        print("\nKeywords of topic most similar to given keyword\n\n" + str(word))
        print("\nScore of topic: \n" + str(score))


# Search for most similar documents by a single topic number
def docsByTopic():
    print("Documents most similar to given topic number\n")
    documents, document_scores, document_ids = model.search_documents_by_topic(topic_num=1, num_docs=27)
    for doc, score, doc_id in zip(documents, document_scores, document_ids):
        print(f"Document: {doc_id}, Score: {score}")
        print("-----------")
        print(doc[0 : 200])
        print("-----------")
        print()


# Search documents for content semantically similar to given keywords
def semanticSearch():
    print("\nDocuments with highest semantic similarity to given keywords\n")
    documents, document_scores, document_ids = model.search_documents_by_keywords(keywords=["king"], num_docs=27)
    for doc, score, doc_id in zip(documents, document_scores, document_ids):
        print(f"Document: {doc_id}, Score: {score}")
        print("-----------")
        print(doc[0 : 200])
        print("-----------")
        print()


# Search for similar words to given keyword
def similarKeywords():
    print("\nSimilar words to given keyword \n")
    words, word_scores = model.similar_words(keywords=["king"], keywords_neg=[], num_words=20)
    for word, score in zip(words, word_scores):
        print(f"{word} {score}")



printMetaData()
printModelTopics()
similarTopics()
semanticSearch()
docsByTopic()
similarKeywords()

# not ideal but works for now

clear = lambda: os.system('cls')


f = open(os.path.join(top2VecLocation, "metadata.txt"), 'w')
sys.stdout = f
printMetaData()
f.close()
clear()

f = open(os.path.join(top2VecLocation, "Model Topics.txt"), 'w')
sys.stdout = f
printModelTopics()
f.close()
clear()


f = open(os.path.join(top2VecLocation, "Similar Topics.txt"), 'w')
sys.stdout = f
similarTopics()
f.close()
clear()


f = open(os.path.join(top2VecLocation, "Semantic Search.txt"), 'w')
sys.stdout = f
semanticSearch()
f.close()
clear()


f = open(os.path.join(top2VecLocation, "Docs by Topic.txt"), 'w')
sys.stdout = f
docsByTopic()
f.close()
clear()


f = open(os.path.join(top2VecLocation, "Similar Keywords.txt"), 'w')
sys.stdout = f
similarKeywords()
f.close()
clear()

