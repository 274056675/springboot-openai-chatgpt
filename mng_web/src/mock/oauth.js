import Mock from 'mockjs'

export default ({mock}) => {
  if (!mock) return;
  Mock.mock('/api/blade-auth/oauth/token/refresh', 'post', () => {
    return {
      data: {
        "account": "admin",
        "user_name": "admin",
        "nick_name": "管理员",
        "role_name": "administrator",
        "avatar": "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png",
        "access_token": "eyJ0eXAiOiJKc29uV2ViVG9rZW4iLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpc3N1c2VyIiwiYXVkIjoiYXVkaWVuY2UiLCJyb2xlX25hbWUiOiJhZG1pbmlzdHJhdG9yIiwidXNlcl9pZCI6IjExMjM1OTg4MjE3Mzg2NzUyMDEiLCJyb2xlX2lkIjoiMTEyMzU5ODgxNjczODY3NTIwMSIsInVzZXJfbmFtZSI6ImFkbWluIiwibmlja19uYW1lIjoi566h55CG5ZGYIiwiYWNjb3VudCI6ImFkbWluIiwidGVuYW50X2NvZGUiOiIwMDAwMDAiLCJjbGllbnRfaWQiOiJzd29yZCIsImV4cCI6MTU1Nzk0Njc5OSwibmJmIjoxNTU3ODgxODg0fQ.FT8y1v1tg8hOAAoQpKlarKMgmsSXol-561edqtvOwa4",
        "refresh_token": "eyJ0eXAiOiJKc29uV2ViVG9rZW4iLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpc3N1c2VyIiwiYXVkIjoiYXVkaWVuY2UiLCJyb2xlX25hbWUiOiJhZG1pbmlzdHJhdG9yIiwidXNlcl9pZCI6IjExMjM1OTg4MjE3Mzg2NzUyMDEiLCJyb2xlX2lkIjoiMTEyMzU5ODgxNjczODY3NTIwMSIsInVzZXJfbmFtZSI6ImFkbWluIiwibmlja19uYW1lIjoi566h55CG5ZGYIiwiYWNjb3VudCI6ImFkbWluIiwidGVuYW50X2NvZGUiOiIwMDAwMDAiLCJjbGllbnRfaWQiOiJzd29yZCIsImV4cCI6MTU1Nzk0Njc5OSwibmJmIjoxNTU3ODgxODg0fQ.FT8y1v1tg8hOAAoQpKlarKMgmsSXol-561edqtvOwa4",
        "token_type": "bearer",
        "expires_in": 64915,
        "license": "powered by bladex"
      }
    }
  })

}
