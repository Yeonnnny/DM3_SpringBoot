1) 분석 모델 작성 ==> 모델완성

2) Python 서버 구축 ==> FastAPI(http로 구동하는 서버 구축해줌)
    - 웹클라이언트 --> Tomcat 서버(Spring) --> Uvicon(유비콘) - 파이썬
    - 레스트 템플릿 
        - json,xml을 쉽게 응답
        - HTTP 서버와의 통신을 단순화

## FastAPI 사용을 위한 설치
1) fastapi
pip install fastapi

2) uvicorn
pip install uvicorn

3) 유비콘 서버 구동하기
uvicorn main:app --reload
