<template>
  <div>cs1</div>
</template>

<script>
// 使用 <remote-js src="https://g.alicdn.com/dingding/dinglogin/0.0.2/ddLogin.js"></remote-js>
export default {
  components: {
    'remote-js': {
      render(createElement) {
        return createElement('script', {
          attrs: { type: 'text/javascript', src: this.src },
        })
      },
      props: {
        src: { type: String, required: true },
      },
    },
  },
}
</script>

<style>
</style>