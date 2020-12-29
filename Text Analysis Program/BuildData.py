from pathlib import Path
import requests

base_path = Path(__file__).parent

# Set paths for data folder and all subfolders
dataSaveLocation = (base_path / "data/").resolve()

textLocation = (dataSaveLocation / "text").resolve()
tf_IDFLocation = (dataSaveLocation / "tf_idf_output").resolve()
ldaLocation = (dataSaveLocation / "lda_output").resolve()
ind_ldaLocation = (dataSaveLocation / "individual_lda").resolve()
commonWordsLocation = (dataSaveLocation / "commonWordsLocation").resolve()
bigramLocation = (dataSaveLocation / "bigramLocation").resolve()
top2VecLocation = (dataSaveLocation / "top2VecLocation").resolve()

# Create data folder and all subfolders if they don't exist
dataSaveLocation.mkdir(parents=True, exist_ok=True)

tf_IDFLocation.mkdir(parents=True, exist_ok=True)
ldaLocation.mkdir(parents=True, exist_ok=True)
ind_ldaLocation.mkdir(parents=True, exist_ok=True)
commonWordsLocation.mkdir(parents=True, exist_ok=True)
bigramLocation.mkdir(parents=True, exist_ok=True)
top2VecLocation.mkdir(parents=True, exist_ok=True)

