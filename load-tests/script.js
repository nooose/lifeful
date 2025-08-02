
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 100 },
    { duration: '30s', target: 100 },
  ],
};

export default function () {
  const url = 'http://localhost:8080/api/v1/members';

  const uniqueEmail = `user_${__VU}_${__ITER}@example.com`;
  const uniqueNickname = `nickname_${__VU}_${__ITER}`;

  const payload = JSON.stringify({
    email: uniqueEmail,
    nickname: uniqueNickname,
    password: 'password123',
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = http.post(url, payload, params);

  // 응답 검증
  check(response, {
    'is status 200': (it) => it.status === 200,
  });

  sleep(1);
}
