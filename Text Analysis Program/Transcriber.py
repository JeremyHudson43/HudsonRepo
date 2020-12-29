from google.cloud import speech_v1
from google.cloud.speech_v1 import enums
import os

os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = r"JSON key would go here"

client = speech_v1.SpeechClient()

podcastName = 'Criminal'

storageUri = 'gs://Google Storage Bucket would go here/Subfolder of bucket/' + podcastName + '.flac'

sampleRateHertz = 44100

languageCode = "en-US"

encoding = enums.RecognitionConfig.AudioEncoding.FLAC
config = {
    "sample_rate_hertz": sampleRateHertz,
    "language_code": languageCode,
    "encoding": encoding,
}
audio = {"uri": storageUri}

operation = client.long_running_recognize(config, audio)

print(u"Waiting for operation to complete...")
response = operation.result()

for result in response.results:

    # finds first most probably secondary result
    alternative = result.alternatives[0]
    print(u"{}".format(alternative.transcript))

    # prints the text output to a text file with whatever podcastName is set to
    f = open(podcastName + ".txt", "a+")
    f.write((u"{}".format(alternative.transcript)))
    f.close()