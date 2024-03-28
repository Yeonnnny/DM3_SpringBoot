#package import 
from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel
from starlette.responses import JSONResponse
import dill
import numpy as np
import pandas as pd

# 데이터 클랜징
from bs4 import BeautifulSoup
import nltk
nltk.download('punkt')
nltk.download('wordnet')
nltk.download('stopwords')
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
import string
import re

# LSTM 모델
import matplotlib.pyplot as plt
import seaborn as sns
import gc
import os
import pickle

from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import StratifiedKFold , train_test_split
from sklearn.feature_extraction.text import CountVectorizer,TfidfVectorizer
from sklearn.decomposition import TruncatedSVD
from sklearn.metrics import log_loss,classification_report,roc_curve,auc,confusion_matrix
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, roc_auc_score

import string
import nltk
nltk.download('punkt')
nltk.download('wordnet')
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from nltk.stem import WordNetLemmatizer
from scipy import sparse
seed = 42

import keras
from keras.preprocessing import sequence
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

from keras.models import Sequential, Model,load_model

from keras.optimizers import Nadam

from keras.layers import Embedding, LSTM, Dense, Bidirectional,Dropout,GlobalMaxPooling1D
from keras.layers import concatenate,Concatenate,Input,BatchNormalization

from keras.regularizers import l2

from keras.callbacks import EarlyStopping,ModelCheckpoint,ReduceLROnPlateau

from tensorflow.keras.models import Sequential, Model,load_model
from tensorflow.keras.layers import Embedding, LSTM, Dropout, Dense, Concatenate
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.preprocessing.text import Tokenizer



# model class 생성
class Product(BaseModel):
    catalogNm : str
    catalogDesc : str


# 데이터 클랜징 함수
def dataCleansing(product:Product):
    with open("preprocessing_function.pkl", 'rb') as f:
        # 전처리 모델 불러오기
        loaded_func = dill.load(f)
        
        dicted = dict(product)
        catalogNm = dicted['catalogNm']
        catalogDesc = dicted['catalogDesc']
        
        # 데이터 프레임으로 변경
        data = {'CATALOG_NM' :[catalogNm], 'CATALOG_DESC':[catalogDesc]}
        test_df = pd.DataFrame(data)

        return loaded_func(test_df)

# 데이터 토큰화 함수
def dataTokenizer(test_df):
    # 토크나이저 모델 불러오기
    with open("tokenizer_6_6.pkl",'rb') as f:
        loaded_tokenizer = dill.load(f)

        # padding 작업
        sequences_new_desc = loaded_tokenizer.texts_to_sequences(test_df['CATALOG_DESC'])
        sequences_new_nm = loaded_tokenizer.texts_to_sequences(test_df['CATALOG_NM'])

        x_new_desc = pad_sequences(sequences_new_desc, maxlen=3727, padding='post')
        x_new_nm = pad_sequences(sequences_new_nm, maxlen=31, padding='post')

        ary = np.array([x_new_desc,x_new_nm])

    return ary


# app 개발
app = FastAPI()

@app.post(path="/weird", status_code=201)
def weirdProduct(product : Product):
    
    # 데이터 준비
    test_df = dataCleansing(product)
    ary = dataTokenizer(test_df)

    
    # LSTM모델 파일 로딩
    loaded_model = load_model('02-0.805.hdf5')
    
    # 새 데이터에 대해 예측
    predict_proba = loaded_model.predict(ary)
    
    # 임계값 = 0.8
    threshold = 0.8
    y_pred = (predict_proba > threshold).astype(int)

    result = {'y_pred':y_pred}

    return JSONResponse(result)

    
if __name__=='__main__':
    uvicorn.run(app, host="127.0.0.1", port=8000)
    # 구동 코드 : uvicorn main:app --reload