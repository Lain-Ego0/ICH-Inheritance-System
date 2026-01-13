<template>
    <div class="app-layout">
        <!-- 顶部导航栏 -->
        <el-header class="top-header">
            <div class="header-content">
                <!-- 左侧：品牌和菜单切换 -->
                <div class="header-left">
                    <div class="menu-toggle" @click="toggleSidebar">
                        <i :class="sidebarCollapsed ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
                    </div>
                    <div class="brand">
                        <img class="logo" :src="require('@/assets/logo.png')" alt="非遗文化传承">
                        <span class="brand-text">非遗文化传承平台</span>
                    </div>
                </div>

                <!-- 右侧：用户信息和操作 -->
                <div class="header-right">
                    <div class="user-actions">
                        <!-- 未登录状态 -->
                        <template v-if="!Token">
                            <el-button size="small" type="text" @click="ToLogin()">登录</el-button>
                            <el-button size="small" type="primary" @click="ToRegister()">注册</el-button>
                        </template>
                        <!-- 已登录状态 -->
                        <template v-else>
                            <el-dropdown @command="handleUserCommand" class="user-dropdown">
                                <span class="user-info">
                                    <i class="el-icon-user"></i>
                                    <span>{{ UserInfo.UserName }}</span>
                                    <i class="el-icon-arrow-down"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="profile">个人资料</el-dropdown-item>
                                    <el-dropdown-item command="cart" v-if="RoleType == '用户'">
                                        <i class="el-icon-shopping-cart-2"></i>购物车
                                    </el-dropdown-item>
                                    <el-dropdown-item command="password">修改密码</el-dropdown-item>
                                    <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                            <el-button size="small" type="text" @click="Wechat()" v-if="Token">
                                <i class="el-icon-service"></i>客服
                            </el-button>
                        </template>
                    </div>
                </div>
            </div>
        </el-header>

        <!-- 主内容区域 -->
        <el-container class="main-container">
            <!-- 侧边栏导航 -->
            <el-aside :width="sidebarCollapsed ? '80px' : '240px'" :class="['sidebar', { 'sidebar-collapsed': sidebarCollapsed }]">  
                <div class="sidebar-content">
                    <el-menu
                        :default-active="$route.path"
                        class="sidebar-menu"
                        :collapse="sidebarCollapsed"
                        :unique-opened="true"
                        @select="handleMenuSelect">

                        <!-- 首页 -->
                        <el-menu-item index="/Front/Home">
                            <i class="el-icon-house"></i>
                            <span slot="title">首页</span>
                        </el-menu-item>

                        <!-- 非遗文化 -->
                        <el-menu-item index="/Front/CulturalList">
                            <i class="el-icon-collection"></i>
                            <span slot="title">非遗文化</span>
                        </el-menu-item>

                        <!-- 周边商城 -->
                        <el-menu-item index="/Front/GoodList">
                            <i class="el-icon-shopping-bag-1"></i>
                            <span slot="title">周边商城</span>
                        </el-menu-item>

                        <!-- 传承人 -->
                        <el-menu-item index="/Front/InheritorList">
                            <i class="el-icon-user"></i>
                            <span slot="title">传承人</span>
                        </el-menu-item>

                        <!-- 非遗论坛 -->
                        <el-menu-item index="/Front/TopicList">
                            <i class="el-icon-chat-line-square"></i>
                            <span slot="title">非遗论坛</span>
                        </el-menu-item>

                        <!-- 系统通知 -->
                        <el-menu-item index="/Front/SysNoticeList">
                            <i class="el-icon-bell"></i>
                            <span slot="title">系统通知</span>
                        </el-menu-item>

                        <!-- 个人中心 -->
                        <el-menu-item index="/Front/PersonCenter" v-if="Token && RoleType == '用户'">
                            <i class="el-icon-setting"></i>
                            <span slot="title">个人中心</span>
                        </el-menu-item>
                    </el-menu>
                </div>
            </el-aside>

        

            <!-- 主内容区 -->
            <el-main class="main-content">
                <div class="content-wrapper">
                    <!-- 面包屑导航 -->
                    <div class="breadcrumb-wrapper" v-if="$route.meta.title">
                        <el-breadcrumb separator-class="el-icon-arrow-right">
                            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                            <el-breadcrumb-item v-if="$route.meta.title !== '首页'">{{ $route.meta.title }}</el-breadcrumb-item>
                        </el-breadcrumb>
                    </div>

                    <!-- 页面内容 -->
                    <div class="page-content">
                        <router-view></router-view>
                    </div>
                </div>
            </el-main>
        </el-container>

        <!-- 页脚 -->
        <Footer></Footer>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Footer from "./Footer.vue"

export default {
    name: 'FrontLayout',
    components: {
        Footer
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {
        return {
            sidebarCollapsed: false, // 侧边栏是否折叠
        }
    },
    created() {
        // 根据屏幕宽度自动调整侧边栏状态
        this.handleResize()
        window.addEventListener('resize', this.handleResize)
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.handleResize)
    },
    methods: {
        // 切换侧边栏
        toggleSidebar() {
            this.sidebarCollapsed = !this.sidebarCollapsed
        },

        // 处理窗口大小变化
        handleResize() {
            // 在小屏幕上默认折叠侧边栏
            if (window.innerWidth < 768) {
                this.sidebarCollapsed = true
            } else {
                this.sidebarCollapsed = false
            }
        },

        // 处理菜单选择
        handleMenuSelect(index) {
            this.$router.push({ path: index })
        },

        // 处理用户下拉菜单
        handleUserCommand(command) {
            switch (command) {
                case 'profile':
                    this.ToUserInfo()
                    break
                case 'cart':
                    this.$router.push({ path: '/Front/BuyCard' })
                    break
                case 'password':
                    this.ToEditPassword()
                    break
                case 'logout':
                    this.LoginOut()
                    break
            }
        },

        // 前往登录
        ToLogin() {
            this.$router.push({ path: "/Login" })
        },

        // 前往注册
        ToRegister() {
            this.$router.push({ path: "/Register" })
        },

        // 退出登录
        async LoginOut() {
            await this.$store.dispatch('Logout')
            this.$router.push({ path: "/Login" })
        },

        // 跳转到用户信息
        async ToUserInfo() {
            this.$router.push({ path: "/Front/UserPerson" })
        },

        // 跳转到修改密码
        async ToEditPassword() {
            this.$router.push({ path: "/Front/PasswordEdit" })
        },

        // 联系客服
        async Wechat() {
            if (this.Token) {
                await this.$Post("/WechatCollection/InsertWechatCollection", {
                    SelfUserId: this.UserId,
                    OtherUserId: 1
                });
                this.$router.push({
                    path: "/Front/Wechat",
                    query: { OtherUserId: 1 }
                })
            } else {
                this.$message.error("请先登录再操作");
            }
        }
    }
}
</script>

<style scoped lang="scss">
.app-layout {
    min-height: 100vh;
    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

/* 顶部导航栏样式 */
.top-header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 60px;
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(10px);
    border-bottom: 1px solid rgba(0, 0, 0, 0.08);
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
    z-index: 1000;
    transition: all 0.3s ease;
}

.header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 20px;
}

.header-left {
    display: flex;
    align-items: center;
    gap: 4px; /* 减少间距，让logo紧贴按钮 */
}

.menu-toggle {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
    border-radius: 8px;
    background: #f8f9fa;
    cursor: pointer;
    transition: all 0.3s ease;
    color: #666;
    /* 核心新增：固定定位贴左侧 */
    position: fixed; // 固定定位，脱离文档流
    left: 0.8%; // 贴紧浏览器左侧边缘
    top: 50%; // 可选：垂直居中（也可设为 top: 20px 靠顶部）
    transform: translateY(-50%); // 配合 top:50% 实现精准垂直居中
    z-index: 9999; // 确保按钮在最上层，不被其他元素遮挡

    &:hover {
        background: #e9ecef;
        color: #333;
        transform: translateY(-50%) scale(1.05); // 修正hover缩放，保持垂直居中
    }

    i {
        font-size: 18px;
    }
}

.brand {
    display: flex;
    align-items: center;
    gap: 12px;
    transition: all 0.3s ease;
    position: fixed; // 固定定位，脱离文档流
    left: 5%; // 贴紧浏览器左侧边缘
    top: 50%; // 可选：垂直居中（也可设为 top: 20px 靠顶部）
    transform: translateY(-50%); // 配合 top:50% 实现精准垂直居中
    z-index: 9999; // 确保按钮在最上层，不被其他元素遮挡

    .logo {
        height: 40px;
        width: auto;
        object-fit: contain;
    }

    .brand-text {
        font-size: 18px;
        font-weight: 600;
        color: #2c3e50;
        letter-spacing: 0.5px;
        white-space: nowrap;
    }
}

.header-right {
    .user-actions {
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .user-dropdown {
        .user-info {
            display: flex;
            align-items: center;
            gap: 6px;
            padding: 8px 12px;
            border-radius: 20px;
            background: #f8f9fa;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 14px;
            color: #495057;

            &:hover {
                background: #e9ecef;
            }

            i {
                font-size: 16px;
            }
        }
    }
}

/* 主内容区域样式 */
.main-container {
    margin-top: 60px;
    min-height: calc(100vh - 60px);
}

.sidebar {
    background: linear-gradient(180deg, #f8f9ff 0%, #e8f2ff 100%);
    border-right: 1px solid rgba(0, 0, 0, 0.06);
    box-shadow: 2px 0 8px rgba(0, 0, 0, 0.04);
    transition: all 0.3s ease;
    position: relative;

    .sidebar-logo {
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 16px 0;
        border-bottom: 1px solid rgba(0, 0, 0, 0.06);

        img {
            width: 32px;
            height: 32px;
            object-fit: contain;
        }
    }

    .sidebar-content {
        padding: 20px 0;
    }

    .sidebar-menu {
        border-right: none;
        background: transparent;

        .el-menu-item {
            height: 48px;
            line-height: 48px;
            margin: 4px 12px;
            border-radius: 8px;
            transition: all 0.3s ease;

            &:hover {
                background: rgba(64, 158, 255, 0.1);
                color: #409eff;
            }

            &.is-active {
                background: linear-gradient(135deg, #409eff 0%, #66b1ff 100%);
                color: rgb(255, 255, 255);

                &::before {
                    display: none;
                }
            }

            i {
                color: inherit;
                margin-right: 8px;
                font-size: 18px;
                margin-left:-12px;
            }
        }
    }
}

/* 悬浮展开按钮 */
.floating-expand-btn {
    position: fixed;
    top: 50%;
    left: 80px; /* 与收起侧边栏宽度对齐 */
    transform: translateY(-50%);
    width: 24px;
    height: 48px;
    background: linear-gradient(180deg, #f8f9ff 0%, #e8f2ff 100%);
    border: 1px solid rgba(0, 0, 0, 0.06);
    border-left: none;
    border-radius: 0 4px 4px 0;
    box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
    z-index: 1000;

    &:hover {
        background: #f8f9fa;
        box-shadow: 2px 2px 12px rgba(0, 0, 0, 0.15);
    }

    i {
        color: #666;
        font-size: 16px;
    }
}

.main-content {
    background: transparent;
    padding: 0;

    .content-wrapper {
        max-width: 1400px;
        margin: 0 auto;
        padding: 20px;
    }

    .breadcrumb-wrapper {
        margin-bottom: 20px;
        padding: 12px 20px;
        background: linear-gradient(135deg, #fefefe 0%, #f8f9fa 100%);
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);

        .el-breadcrumb {
            font-size: 14px;
        }
    }

    .page-content {
        background: linear-gradient(135deg, #fefefe 0%, #f8f9fa 100%);
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        min-height: calc(100vh - 180px);
        padding: 24px;
        transition: all 0.3s ease;
    }
}

/* 响应式设计 */
@media (max-width: 768px) {
    .header-content {
        padding: 0 12px;
    }

    .brand {
        /* 移动端保持紧贴布局 */
    }

    .brand-text {
        display: none;
    }

    .sidebar {
        position: fixed;
        top: 60px;
        left: 0;
        height: calc(100vh - 60px);
        z-index: 999;
        box-shadow: 4px 0 20px rgba(0, 0, 0, 0.1);
    }

    .main-content .content-wrapper {
        padding: 12px;
    }

    .floating-expand-btn {
        display: none; /* 移动端隐藏悬浮按钮 */
    }
}

@media (max-width: 480px) {
    .header-left {
        gap: 8px;
    }

    .menu-toggle {
        width: 36px;
        height: 36px;
    }

    .logo {
        height: 32px;
    }

    .user-actions {
        gap: 4px;
    }

    .user-info {
        padding: 6px 10px;
        font-size: 13px;
    }
}

/* 滚动条样式 */
::-webkit-scrollbar {
    width: 6px;
    height: 6px;
}

::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.2);
    border-radius: 3px;

    &:hover {
        background: rgba(0, 0, 0, 0.3);
    }
}

::-webkit-scrollbar-track {
    background: rgba(0, 0, 0, 0.05);
}

/* 动画效果 */
.el-menu-item {
    animation: slideInLeft 0.3s ease-out;
}

@keyframes slideInLeft {
    from {
        opacity: 0;
        transform: translateX(-20px);
    }
    to {
        opacity: 1;
        transform: translateX(0);
    }
}
</style>
