import http from 'k6/http';

export default function () {
  const res = http.get('http://localhost:3000/report');

  console.log(`status=${res.status}`);
  console.log(`body=${res.body}`);
}
