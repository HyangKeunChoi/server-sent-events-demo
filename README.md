## 주로 알림과 같은 서비스에서 자주 사용한다.

## SSE를 사용할때의 문제점은 Scale out시에 발생한다.
+ SseEmitter가 서버 메모리에 저장하기 때문에
  - redis pub/sub 이용
