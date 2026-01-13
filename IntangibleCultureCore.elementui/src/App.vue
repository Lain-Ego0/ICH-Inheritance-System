<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>

export default {
  name: 'App',

}
</script>

<style lang="scss">
@import './css/index.css';
@import "./theme/index.css";

/**全局样式 */
:root {
  --primary-color: #870000;
  --content-width: 95%;
  --active-bg-color: rgb(81 85 87);
  --active-color: white;

}

html,
body {
  background-color: rgb(247, 247, 247);
}

.pointer {
  cursor: pointer;
}

.bg-header {
  background-color: var(--primary-color);
  display: flex;
  height: 60px;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  color: white;
}


.header-title {
  line-height: 25px;
  color: var(--primary-color);
  border-left: 5px solid var(--primary-color);
  padding-left: 10px;
  font-weight: 700;
  font-size: 18px;
  margin: 5px 0px;

}

.rich_content {
  width: 100%;
  margin: 0 auto;
  font-family: 微软雅黑;
  font-size: 16px;
  letter-spacing: 0.2em;
  line-height: 1.5em;
  color: #666161;
  box-sizing: content-box;
  text-align: left;

  img {
    width: 100%;
  }
}

#nprogress .bar {
  background: var(--primary-color) !important;
}



.card {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  border-radius: 10px;
  padding: 20px;
  background: white;
}
</style>
