<template>
    <div>
        <el-container style="height: 100vh">

            <el-header style="padding: 0;">
                <div class="bg-header">
                    <div class="flex align-center">
                        <img :src="require('@/assets/logo.png')" style="width: 50px; height: 50px;">
                        <span style="margin-left:10px">非遗文化管理端</span>
                    </div>
                    <div style="display: flex;align-items: center">
                        <el-avatar :size="60" :src="UserInfo.ImageUrls" size="medium">
                            <img :src="require('@/assets/默认头像.png')" />
                        </el-avatar>
                        <div style="margin-left:10px">
                            <el-dropdown>
                                <span class="el-dropdown-link" style="color:white">
                                    {{ UserInfo.Name }}<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>
                                        <div @click="ToPath('/Admin/UserPerson')">个人信息</div>

                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="ToPath('/Admin/PasswordEdit')">修改密码</div>

                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="LoginOut()">退出</div>
                                    </el-dropdown-item>

                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>

                    </div>
                </div>


            </el-header>


            <el-container>
                <!-- 左侧菜单 -->
                <el-menu
                    :default-active="$route.path"
                    class="el-menu-vertical-demo"
                    theme="dark"
                    background-color="#333"
                    text-color="#ccc"
                    active-text-color="#fff"
                    unique-opened
                    router
                    style="width: 220px; height: calc(100vh - 60px);">
                    <!-- 控制台 -->
                    <!-- <el-menu-item index="/Admin/SynthesizeAnalyse">
                        <i class="el-icon-monitor"></i>
                        <span slot="title">非遗综合分析</span>
                    </el-menu-item> -->

                    <!-- 用户与店铺管理 -->
                    <el-submenu index="user-shop">
                        <template slot="title">
                            <i class="el-icon-user"></i>
                            <span>用户与店铺</span>
                        </template>
                        <el-menu-item index="/Admin/UserList">
                            <i class="el-icon-user-solid"></i>
                            <span>用户信息</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/WeChat">
                            <i class="el-icon-s-shop"></i>
                            <span>用户咨询</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/ShopEdit">
                            <i class="el-icon-s-shop"></i>
                            <span>店铺信息</span>
                        </el-menu-item>
                    </el-submenu>

                    <!-- 文创品管理 -->
                    <el-submenu index="goods">
                        <template slot="title">
                            <i class="el-icon-goods"></i>
                            <span>文创品管理</span>
                        </template>
                        <el-menu-item index="/Admin/GoodTypeList">
                            <i class="el-icon-collection"></i>
                            <span>文创品分类</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/GoodList">
                            <i class="el-icon-shopping-bag-1"></i>
                            <span>文创品维护</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/GoodStockList">
                            <i class="el-icon-box"></i>
                            <span>库存记录</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/GoodReplenishmentList">
                            <i class="el-icon-warning-outline"></i>
                            <span>库存预警</span>
                        </el-menu-item>
                    </el-submenu>

                    <!-- 订单管理 -->
                    <el-submenu index="orders">
                        <template slot="title">
                            <i class="el-icon-document"></i>
                            <span>订单管理</span>
                        </template>
                        <el-menu-item index="/Admin/OrderInfoList">
                            <i class="el-icon-tickets"></i>
                            <span>商家订单</span>
                        </el-menu-item>
                    </el-submenu>

                    <!-- 话题管理 -->
                    <el-submenu index="topics">
                        <template slot="title">
                            <i class="el-icon-chat-dot-square"></i>
                            <span>话题管理</span>
                        </template>
                        <el-menu-item index="/Admin/TopicTypeList">
                            <i class="el-icon-collection-tag"></i>
                            <span>话题类型</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/TopicList">
                            <i class="el-icon-chat-line-square"></i>
                            <span>话题信息</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/TopicAnalyseEchart">
                            <i class="el-icon-data-line"></i>
                            <span>话题统计</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/TopicSynthesizeAnalyse">
                            <i class="el-icon-data-analysis"></i>
                            <span>话题综合分析</span>
                        </el-menu-item>
                    </el-submenu>

                    <!-- 非遗文化管理 -->
                    <el-submenu index="culture">
                        <template slot="title">
                            <i class="el-icon-collection"></i>
                            <span>非遗文化管理</span>
                        </template>
                        <el-menu-item index="/Admin/CulturalTypeList">
                            <i class="el-icon-folder"></i>
                            <span>非遗文化类别</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/CulturalList">
                            <i class="el-icon-reading"></i>
                            <span>非遗文化</span>
                        </el-menu-item>
                        <el-menu-item index="/Admin/InheritorList">
                            <i class="el-icon-user"></i>
                            <span>传人</span>
                        </el-menu-item>
                    </el-submenu>

                    <!-- 系统通知 -->
                    <el-menu-item index="/Admin/SysNoticeList">
                        <i class="el-icon-bell"></i>
                        <span slot="title">系统通知</span>
                    </el-menu-item>
                </el-menu>

                <el-main class="admin-main">
                    <el-breadcrumb separator-class="el-icon-arrow-right" class="margin-bottom-xs">
                        <el-breadcrumb-item v-for="(item, index) in breadList" :key="index" :to="item.path">{{
                            item.meta.title
                        }}</el-breadcrumb-item>
                    </el-breadcrumb>

                    <transition>
                        <router-view></router-view>
                    </transition>

                </el-main>

            </el-container>
        </el-container>
    </div>
</template>

<script>

import { adminRouters } from "@/router/index";
import { mapGetters } from "vuex";
export default {
    name: 'Layout',
    data() {
        return {
            routerMenu: [],
            breadList: [],
        }
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'RoleTypeFormat', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    watch: {
        $route() {
            this.getBreadcrumb();
        },
    },

    created() {
        this.routerMenu = adminRouters;
        this.getBreadcrumb();
    },
    methods: {
        //跳转
        async ToPath(url) {
            this.$router.push({
                path: url
            })
        },
        async LoginOut() {

            await this.$store.dispatch('Logout')
            window.location.reload('/Login')
        },
        isHome(route) {
            return route.path === "/Admin";
        },
        getBreadcrumb() {
            let matched = this.$route.matched;
            if (!this.isHome(matched[0])) {
                matched = [{ path: "/Admin", meta: { title: "控制台" } }].concat(matched);
            }
            this.breadList = matched;

        }
    }
}
</script>

<style scoped>
/* 头部样式 */
.bg-header {
    background: #333;
    color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    height: 60px;
}

.bg-header .el-dropdown-link {
    color: #fff;
}

/* 保持原有样式 */
.admin-main {
    height: calc(100vh - 60px) !important;
    width: calc(100% - 220px) !important;
    background: #e0e0e0;
}
</style>