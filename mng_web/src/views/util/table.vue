<template>
  <basic-container>
    <h3>表格例子</h3>
    <avue-crud :option="option"
               :page="page"
               :data="data"></avue-crud>
  </basic-container>
</template>

<script>
  export default {
    data() {
      return {
        page: {
          total: 122
        },
        data: [
          {
            username: "smallwei",
            name: "avue",
            password: "123456",
            newpassword: "123456",
            date: "2019-01-01",
            textarea: "这是一条很长很长很长很长很长很长很长很长的个性签名"
          },
          {
            username: "smallwei",
            name: "avue",
            password: "123456",
            newpassword: "123456",
            date: "2019-01-01",
            textarea: "这是一条很长很长很长很长很长很长很长很长的个性签名"
          },

          {
            username: "smallwei",
            name: "avue",
            password: "123456",
            newpassword: "123456",
            date: "2019-01-01",
            textarea: "这是一条很长很长很长很长很长很长很长很长的个性签名"
          },
          {
            username: "smallwei",
            name: "avue",
            password: "123456",
            newpassword: "123456",
            date: "2019-01-01",
            textarea: "这是一条很长很长很长很长很长很长很长很长的个性签名"
          },
          {
            username: "smallwei",
            name: "avue",
            password: "123456",
            newpassword: "123456",
            date: "2019-01-01",
            textarea: "这是一条很长很长很长很长很长很长很长很长的个性签名"
          },
          {
            username: "smallwei",
            name: "avue",
            password: "123456",
            newpassword: "123456",
            date: "2019-01-01",
            textarea: "这是一条很长很长很长很长很长很长很长很长的个性签名"
          },
          {
            username: "smallwei",
            name: "avue",
            password: "123456",
            newpassword: "123456",
            date: "2019-01-01",
            textarea: "这是一条很长很长很长很长很长很长很长很长的个性签名"
          }
        ],
        option: {
          column: [
            {
              label: "用户名",
              prop: "username",
              span: 14,
              row: true
            },
            {
              label: "姓名",
              prop: "name",
              span: 14,
              row: true
            },
            {
              label: "密码",
              prop: "password",
              type: "password",
              span: 14,
              row: true
            },
            {
              label: "确认密码",
              prop: "newpassword",
              type: "password",
              hide: true,
              span: 14,
              row: true
            },
            {
              label: "申请日期",
              prop: "date",
              type: "date",
              span: 14,
              row: true
            },
            {
              label: "个性签名",
              prop: "textarea",
              type: "textarea",
              minRows: 8,
              span: 24,
              overHidden: true,
              row: true
            }
          ]
        }
      };
    }
  };
</script>

<style>
</style>
