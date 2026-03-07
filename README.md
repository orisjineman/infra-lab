# infra-lab

인프라, 헬스체크, 서버작업시 롤링 등을 로컬 환경에서 연습하기 위해 만들었습니다.

## 실행 방법

root 폴더 infra-labd에서

```bash
docker compose up --build
```

정상 기동 되면 확인

```bash
curl http://localhost:8080/hello
```

몇 번 반복하면 app1/app2 번갈아 나옴

```bash
for i in {1..10}; do curl -s http://localhost:8080/hello; echo; done
```

## 실습 시나리오

작성중...