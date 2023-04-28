<template>
  <div></div>
</template>

<script>
export default {
  name: 'authredirect',
  created () {
    window.close()
    const params = this.$route.query
    const state = params.state
    const code = params.code
    window.opener.location.href = `${window.location.origin}/#/login?state=${state}&code=${code}`
  }
}
</script>


<style>
</style>
