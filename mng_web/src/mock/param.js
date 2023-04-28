import Mock from 'mockjs'

function getFakeList() {
  const json = {code: 200, success: true, msg: '操作成功'};
  const list = [];
  list.push(
    {
      id: '1',
      paramName: '是否开启注册功能--mock测试',
      paramKey: 'account.registerUser',
      paramValue: 'true',
      remark: '描述',
    },
    {
      id: '2',
      paramName: '账号初始密码--mock测试',
      paramKey: 'account.initPassword',
      paramValue: '123456',
      remark: '描述',
    }
  );
  json.data = {
    total: 10,
    size: 10,
    current: 1,
    searchCount: true,
    pages: 1,
    records: list,
  };
  return json;
}

function getFakeDetail() {
  const json = {code: 200, success: true, msg: '操作成功'};
  json.data = {
    id: '1',
    paramName: '是否开启注册功能',
    paramKey: 'account.registerUser',
    paramValue: 'true',
    remark: '描述',
  };
  return json;
}

function fakeSuccess() {
  return {code: 200, success: true, msg: '操作成功'};
}

export default ({mock}) => {
  if (!mock) return;
  Mock.mock(/\/api\/blade-system\/param\/list/, 'get', getFakeList);

  Mock.mock(/\/api\/blade-system\/param\/detail/, 'get', getFakeDetail);

  Mock.mock(/\/api\/blade-system\/param\/submit/, 'post', fakeSuccess);

  Mock.mock(/\/api\/blade-system\/param\/remove/, 'post', fakeSuccess);

}
