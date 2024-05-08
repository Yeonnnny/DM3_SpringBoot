# package import
import pandas as pd
import pickle

from sklearn.datasets import load_iris
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split

# 데이터 불러오기
iris = load_iris()

# DF로 만들기
iris_df = pd.DataFrame(iris['data'], columns=iris['feature_names'])
target = iris['target_names']

# 문제와 답
X = iris_df
y = iris['target']

X_train, X_test, y_train, y_test = train_test_split(X,
                                                    y,
                                                    test_size=0.3,
                                                    random_state=1)
# model
knn_model = KNeighborsClassifier(n_neighbors=3)
knn_model.fit(X_train, y_train)

# 예측하기
knn_model.predict(X_test)

# print(knn_model.score(X_test, y_test))

# 데이터 입력하여 결과 확인 ==> 5.1  3.5 1.4 0.2   ==> 0번(세토사??)
pred = knn_model.predict([[5.1, 3.5, 1.4,0.2]])
# print('결과:', target[pred[0]])

# 피클파일로 저장
with open('data.pickle', 'wb') as f:
    pickle.dump(knn_model, f, pickle.HIGHEST_PROTOCOL)