<template>
  <el-footer class="footer">
    <div class="footer-content">
      <!-- 页脚主要内容区域 -->
      <div class="footer-main">
        <!-- 左侧 Logo 和简介 -->
        <div class="footer-section">
          <h3>非遗文化管理系统</h3>
          <p class="description">致力于中国非物质文化遗产的保护、传承与发展，让传统文化在新时代绽放光彩。</p>
        </div>

        <!-- 快速导航链接 -->
        <div class="footer-section">
          <h4>快速导航</h4>
          <ul>
            <li><span>首页</span></li>
            <li><span>遗产名录</span></li>
            <li><span>传承人档案</span></li>
            <li><span>保护基地</span></li>
            <li><span>文化展示</span></li>
          </ul>
        </div>

        <!-- 资源中心 -->
        <div class="footer-section">
          <h4>资源中心</h4>
          <ul>
            <li><span>政策法规</span></li>
            <li><span>下载中心</span></li>
            <li><span>研究成果</span></li>
            <li><span>专家智库</span></li>
          </ul>
        </div>

        <!-- 联系方式 -->
        <div class="footer-section">
          <h4>联系我们</h4>
          <ul class="contact-info">
            <li><i class="el-icon-location"></i>地址：北京市东城区东直门内大街1号</li>
            <li><i class="el-icon-phone"></i>电话：010-12345678</li>
            <li><i class="el-icon-message"></i>邮箱：contact@ich.example.com</li>
          </ul>
        </div>
      </div>

      <!-- 社交媒体图标 -->
      <div class="social-media">
        <span class="social-icon"><i class="el-icon-platform-eleme"></i></span>
        <span class="social-icon"><i class="el-icon-chat-dot-round"></i></span>
        <span class="social-icon"><i class="el-icon-connection"></i></span>
      </div>

      <!-- 底部版权信息 -->
      <div class="bottom-bar">
        <div class="copyright">
          <span>© 2025 非物质文化遗产管理系统 版权所有</span>
          <span class="divider">|</span>
          <span>ICP备案号：京ICP备12345678号</span>
        </div>
        <div class="extra-links">
          <span>隐私政策</span>
          <span class="divider">|</span>
          <span>使用条款</span>
          <span class="divider">|</span>
          <span>网站地图</span>
        </div>
      </div>
    </div>
  </el-footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
/* 底部容器基础样式 */
.footer {
  background-color: #222222;
  padding: 40px 0 20px;
  color: #ffffff;
  min-height: 500px;
}

/* 内容容器样式 */
.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;

}

/* 主要内容区域布局 */
.footer-main {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}

/* 各个区块样式 */
.footer-section {
  flex: 1;
  margin-right: 40px;
}

.footer-section:last-child {
  margin-right: 0;
}

/* 标题样式 */
.footer-section h3 {
  font-size: 20px;
  margin-bottom: 20px;
  color: #ffffff;
}

.footer-section h4 {
  font-size: 16px;
  margin-bottom: 15px;
  color: #ffffff;
}

/* 描述文字样式 */
.description {
  color: #999999;
  line-height: 1.6;
  margin-bottom: 20px;
}

/* 列表样式 */
.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section ul li {
  margin-bottom: 10px;
  color: #999999;
}

.footer-section ul li span {
  cursor: pointer;
}

.footer-section ul li span:hover {
  color: #ffffff;
}

/* 联系信息样式 */
.contact-info li {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.contact-info li i {
  margin-right: 10px;
  font-size: 16px;
}

/* 社交媒体图标样式 */
.social-media {
  text-align: center;
  margin-bottom: 30px;
}

.social-icon {
  display: inline-block;
  width: 40px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  background-color: #333333;
  border-radius: 50%;
  margin: 0 10px;
  cursor: pointer;
}

.social-icon:hover {
  background-color: #444444;
}

.social-icon i {
  font-size: 20px;
}

/* 底部版权栏样式 */
.bottom-bar {
  border-top: 1px solid #333333;
  padding-top: 20px;
  text-align: center;
}

.copyright,
.extra-links {
  color: #666666;
  font-size: 12px;
  margin-bottom: 10px;
}

/* 分隔符样式 */
.divider {
  margin: 0 10px;
  color: #444444;
}

/* 链接悬停效果 */
.extra-links span:not(.divider) {
  cursor: pointer;
}

.extra-links span:not(.divider):hover {
  color: #ffffff;
}
</style>
