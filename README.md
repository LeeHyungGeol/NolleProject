## 프로젝트 소개
|구분|내용|
|------|---|
|한줄 소개|딥러닝 기반 개인화 맛집 추천 서비스입니다.|
|진행 기간|2020.03 ~ 2020.06|
|주요 기술| Android, Django, Yolov3(딥러닝), BiLSTM(딥러닝), MySQL |
|팀원 구성|4명 (Anroid 개발 및 딥러닝 모델 구현 2명, Server 개발 2명)|
|전담 역할|Android 개발 및 딥러닝 모델 구현|
|수상|없음|

## 프로젝트 개요

- NOLLE는 Notifiy only likes, Let’s enjoy로 좋아하는걸 알리고 같이 나누자라는 의미입니다.
- 창의학기제 과목으로 진행한 딥러닝 기반 개인화 맛집 추천 서비스입니다.
- 사용자가 업로드 한 음식점 리뷰에 대한 이미지 및 텍스트, 검색 기록 등을 딥러닝으로 분석하여 성향을 추출 후 나와 유사도가 높은 다른 사용자가 갔던 음식점을 추천해줍니다.
- Yolov3 모델의 경우 [](https://www.notion.so/9ed93986cbf34d53b45ae4d624b36513)총 42가지의 한식, 일식, 중식, 양식 음식에 대한 분류가 가능하며 객체인식 모델 정확성 평가의 경우 TOP-1 정확도 88.61%, TOP-5 정확도 90.13%를 보여주었습니다.
- BiLSTM 모델의 경우 음식에 대한 표현과 장소에 대한 분위기를 범주에 나눠 총 14가지의 텍스트 분류가 가능하며 F1-Score은 90.93%, Accuracy는 90.99%를 보여주었습니다.
- 프로젝트에 사용된 딥러닝 기술에 대한 저널 논문`(개인 성향 추출을 위한 딥러닝 기반 SNS 리뷰 분석 방법에 관한 연구)`을 작성하였습니다.

## 프로젝트 사용 기술 및 라이브러리

### ✔ Languauge

- Java, Python

### ✔ Server

- Django

### ✔ Client

- Android

### ✔ 협업

- GitHub

### ✔ Deep-Learning

- Yolov3 (Tensorflow)
- BiLSTM (Tensorflow)

### ✔ Data Base

- MySQL

### ✔ Open API

- Naver Blog Search API
- Google Maps API


## 주요 기능

- 딥러닝 기반 성향 분석 및 성향 기반 추천
- 텍스트, 음성, 이미지로 다양하고 간편한 검색 및 추천 요청
- 실시간 리뷰, 인기 리뷰 및 태그를 통한 추천
- 사용자가 가고 싶은 장소를 PICK해 저장
- SNS의 순기능인 팔로우, 팔로잉, 좋아요 및 포스팅 기능

## 나의 역할

- Android 구현
- 딥러닝 모델 구현을 위한 데이터 수집 및 정제
- 객체인식 딥러닝(Yolov3) 모델 구현
- 텍스트 딥러닝(BiLSTM) 모델 구현

## 시연 영상

[https://www.youtube.com/watch?v=lJCyzy5Unc0](https://www.youtube.com/watch?v=lJCyzy5Unc0)

## [🛠 실행화면 및 자세한 설명]

[노션 문서](https://www.notion.so/NOLLE-Android-89626697effc4ac8949cc3c8e6f3cc9e)

