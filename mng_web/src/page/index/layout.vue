<template>
  <div>
    <keep-alive>
      <router-view class="avue-view" v-if="$route.meta.keepAlive" />
    </keep-alive>
    <router-view class="avue-view" v-if="!$route.meta.keepAlive" />
  </div>
</template>
