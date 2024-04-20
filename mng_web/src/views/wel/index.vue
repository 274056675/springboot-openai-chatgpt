<template>
  <div>
    <basic-container>{{getTitle}}，欢迎━(*｀∀´*)ノ亻!使用超级AI大脑</basic-container>
  </div>
</template>

<script>
export default {
  name: 'wel',
  data() {
    return {}
  },
  mounted() {},
  computed: {
    getTitle() {
      let time = new Date().getHours()
      let title = ''
      if (time >= 5 && time < 11) {
        title = '上午好'
      } else if (time >= 11 && time < 13) {
        title = '中午好'
      } else if (time >= 13 && time < 18) {
        title = '下午好'
      } else {
        title = '晚上好'
      }
      return title
    },
  },
  methods: {},
}
</script>

<style scoped="scoped" lang="scss">
.wel {
  &__header {
    padding: 10px 0px;
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  &__info {
    display: flex;
    align-items: center;

    &-img {
      border-radius: 72px;
      display: block;
      width: 72px;
      height: 72px;

      img {
        width: 100%;
        height: 100%;
        display: block;
      }
    }

    &-content {
      position: relative;
      margin-left: 24px;
      color: rgba(0, 0, 0, 0.45);
      line-height: 22px;
    }

    &-title {
      font-size: 20px;
      line-height: 28px;
      font-weight: 500;
      color: rgba(20, 20, 20, 0.85);
      margin-bottom: 12px;
    }

    &-subtitle {
      position: relative;
      font-size: 14px;
      color: rgba(0, 0, 0, 0.45);
      line-height: 22px;
    }
  }

  &__extra {
    text-align: center;

    &-item {
      position: relative;
      padding: 0 32px;
      display: inline-block;

      &:last-child {
        &::after {
          display: none;
        }
      }

      &:after {
        background-color: #e8e8e8;
        position: absolute;
        top: 30px;
        right: 0;
        width: 1px;
        height: 40px;
        content: '';
      }
    }

    &-title {
      color: rgba(0, 0, 0, 0.45);
      font-size: 14px;
      line-height: 22px;
      margin-bottom: 4px;
    }

    &-subtitle {
      color: rgba(0, 0, 0, 0.85);
      font-size: 30px;
      line-height: 38px;
      margin: 0;

      span {
        color: rgba(0, 0, 0, 0.45);
        font-size: 20px;
      }
    }
  }
}

/deep/ .data-icons .item-info {
  padding: 0 !important;

  & > span {
    font-size: 16px !important;
  }

  .count {
    min-width: 150px;
    font-size: 16px !important;
    // overflow: hidden;
    // text-overflow: ellipsis;
    // white-space: nowrap;
  }
}

.date {
  flex: 1;
  font-size: 20px;
  color: rgba(0, 0, 0, 0.45);
  margin-left: 80px;
}

.title {
  display: flex;
  justify-content: center;
  width: 100%;
  min-width: 120px;
  font-size: 20px;
  color: rgba(0, 0, 0, 0.45);
  padding: 28px 0 16px 0;
}

/deep/ .data-icons {
  .item-icon {
    padding: 12px 0;
  }

  .item-info {
    padding: 12px 0;
  }

  .count {
    padding: 12px 0;
  }
}
</style>
