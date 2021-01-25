from nltk.corpus import stopwords

import pyLDAvis
import pyLDAvis.gensim
import gensim
import gensim.corpora as corpora
from gensim.utils import simple_preprocess

import spacy
import warnings
warnings.filterwarnings("ignore", category=DeprecationWarning)

import os

# import all paths from BuildData
from BuildData import *

stop_words = stopwords.words('english')

# extend list of stop words with a text file
with open(str(base_path / 'data/stop words.txt'), 'r') as file:
    stop_words.extend(file.read().split('\n'))
    stop_words = [x.strip(' ') for x in stop_words]


# outputs an HTML file with pyLDAvis result
def printToHTML(ldaModel, fileName, corpus, id2word):
    try:
        lda_display = pyLDAvis.gensim.prepare(ldaModel, corpus, id2word, sort_topics=False)

        htmlFileName = fileName.replace(".txt", ".html")
        pyLDAvis.save_html(lda_display, str(ind_ldaLocation / htmlFileName))
    except:
        print("printToHTMLErorr")


# outputs a text file with LDA model result
def printToText(ldaModel, file):
    topics = ldaModel.print_topics(5, 10)
    print(*topics, sep="\n", file=open(ind_ldaLocation / file, "w+"))


# tokenizes words
def sent_to_words(sentences):
    for sentence in sentences:
        yield (gensim.utils.simple_preprocess(str(sentence), deacc=True))


# Removes stopwords and performs lemmatizations
def process_words(texts, stop_words=stop_words, allowed_postags=['NOUN', 'ADJ', 'VERB', 'ADV']):
    # remove stopwords
    texts = [[word for word in simple_preprocess(str(doc)) if word not in stop_words] for doc in texts]
    texts_out = []
    nlp = spacy.load('en_core_web_sm', disable=['parser', 'ner'])
    # lemmatize
    for sent in texts:
        doc = nlp(" ".join(sent))
        texts_out.append([token.lemma_ for token in doc if token.pos_ in allowed_postags])
    # remove stopwords once more after lemmatization
    texts_out = [[word for word in simple_preprocess(str(doc)) if word not in stop_words] for doc in texts_out]
    return texts_out


# formats the data and produces a model as a result
def formatDataAndModel(dataWords, podcastName):
    data_ready = process_words(dataWords, allowed_postags=['NOUN', 'ADJ', 'VERB', 'ADV'])

    # maps IDs to words
    id2word = corpora.Dictionary(data_ready)

    # maps new lemmitized data to IDs
    corpus = [id2word.doc2bow(text) for text in data_ready]

    lda_model = gensim.models.ldamodel.LdaModel(corpus=corpus,
                                                id2word=id2word,
                                                num_topics=5,
                                                passes=100,
                                                alpha='auto',
                                                minimum_probability=0.05,
                                                per_word_topics=True)

    doc_lda = lda_model[corpus]

    printToHTML(lda_model, podcastName, corpus, id2word)
    printToText(lda_model, podcastName)


results = []
totalList = []


def indLdaDriver():

    # adds each transcript to a list in string format for processing
    for folderName, subfolders, fileName in os.walk(textLocation):

     try:
        for file in fileName:
            f = open(os.path.join(folderName, file), 'rb')

            txt_file_as_string = f.read()

            # split string into list
            data = txt_file_as_string.splitlines()

            # remove stopwords
            data_words_nostops = process_words(data)

            formatDataAndModel(data_words_nostops, str(file))

            f.close()
     except:
         print("error")
