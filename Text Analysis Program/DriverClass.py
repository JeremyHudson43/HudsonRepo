import os
# import all paths from BuildData
from BuildData import *


def textAnalysis():

  import Top2Vector

  from IndividualLDA import indLdaDriver
  from BigramFinder import bigramDriver
  from MostCommonWords import commonWordsDriver
  from TfIDFModeling import tf_idf_driver
  from LDAModeling import topic_percentage, representative_file, dominant_topic

  topic_percentage
  representative_file
  dominant_topic

  indLdaDriver()
  tf_idf_driver()
  bigramDriver()
  commonWordsDriver()



if __name__ == '__main__':

  textAnalysis()
