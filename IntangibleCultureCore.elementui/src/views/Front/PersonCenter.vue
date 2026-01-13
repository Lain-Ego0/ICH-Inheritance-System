<template>
    <div class="page-container">
        <!-- 顶部个人信息卡片 -->
        <div class="profile-header card">
            <div class="user-profile">
                <div class="avatar-wrapper">
                    <img class="avatar" :src="UserInfo.ImageUrls || require('@/assets/emptyheadimg.jpg')">
                </div>
                <div class="user-info">
                    <h2 class="username">{{ UserInfo.Name }}</h2>
                    <p class="account">{{ UserInfo.UserName }}</p>
                    <el-button type="primary" size="small" @click="ToUrl('/Front/UserPerson')">编辑资料</el-button>
                </div>
            </div>
        </div>

        <!-- 功能菜单区域 -->
        <div class="menu-section card margin-top-xs">
            <!-- 订单相关 -->
            <div class="menu-group">
                <div class="group-title">我的订单</div>
                <ul class="menu-grid">
                    <li @click="ToUrl('/Front/MyOrderList')">
                        <img class="menu-icon" :src="require('@/assets/订单合同1.png')">
                        <span>购物订单</span>
                    </li>
                    <li @click="ToUrl('/Front/BuyCard')">
                        <img class="menu-icon" :src="require('@/assets/购物车.png')">
                        <span>购物车</span>
                    </li>
                </ul>
            </div>

            <!-- 收藏与互动 -->
            <div class="menu-group">
                <div class="group-title">我的收藏</div>
                <ul class="menu-grid">
                    <li @click="ToUrl('/Front/GoodCollect')">
                        <img class="menu-icon" :src="require('@/assets/收藏1.png')">
                        <span>文创收藏</span>
                    </li>
                    <li @click="ToUrl('/Front/TopicMy')">
                        <img class="menu-icon" :src="require('@/assets/话题1.png')">
                        <span>我的话题</span>
                    </li>
                    <li @click="ToUrl('/Front/MyTopicCollect')">
                        <img class="menu-icon" :src="require('@/assets/话题收藏.png')">
                        <span>话题收藏</span>
                    </li>
                </ul>
            </div>

            <!-- 设置相关 -->
            <div class="menu-group">
                <div class="group-title">设置</div>
                <ul class="menu-grid">
                    <li @click="ToUrl('/Front/MyUserAddress')">
                        <img class="menu-icon" :src="require('@/assets/收货地址1.png')">
                        <span>收货地址</span>
                    </li>
                    <li @click="ToUrl('/Front/PasswordEdit')">
                        <img class="menu-icon" :src="require('@/assets/修改密码1.png')">
                        <span>修改密码</span>
                    </li>
                    <li @click="LogOut()">
                        <img class="menu-icon" :src="require('@/assets/退出1.png')">
                        <span>退出登录</span>
                    </li>
                </ul>
            </div>
        </div>

        <!-- 推荐区域 -->
        <div class="card margin-top-xs">
            <div class="section-title">猜你喜欢</div>
            <Pagination url="/Good/GetRecommendList" :where="{}">
                <template v-slot:content="{ data }">
                    <GoodListCard :DataList="data"></GoodListCard>
                </template>
            </Pagination>
        </div>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
import GoodListCard from "./GoodListCard.vue";
export default {
    name: "GoodCollectList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {
        Pagination,
        GoodListCard
    },
    data() {

        return {


        };
    },
    created() {

    },
    methods: {

        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },
        ToUrl(url) {
            this.$router.push({
                path: url
            })
        },

        async LogOut() {
            await this.$store.dispatch('Logout')
            this.$router.push(`/`);
        },



    },
};
</script>
<style scoped>
/* 顶部个人信息样式 */
.profile-header {
    padding: 20px;
}

.user-profile {
    display: flex;
    align-items: center;
}

.avatar-wrapper {
    margin-right: 24px;
}

.avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    border: 2px solid var(--primary-color);
}

.user-info {
    flex: 1;
}

.username {
    font-size: 24px;
    font-weight: bold;
    margin: 0 0 8px 0;
}

.account {
    color: #666;
    margin: 0 0 12px 0;
}

/* 菜单区域样式 */
.menu-section {
    padding: 20px;
    display: flex;
    gap: 24px;
    justify-content: space-around;
}

.menu-group {
    flex: 1;
    text-align: center;
    position: relative;
}

.group-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 16px;
    padding: 12px 16px;
    border-left: none;
    border-bottom: 3px solid var(--primary-color);
    padding-bottom: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    border-radius: 8px;
}

.group-title:hover {
    background-color: rgba(64, 158, 255, 0.1);
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.menu-grid {
    display: flex;
    flex-direction: column;
    gap: 8px;
    align-items: stretch;
    opacity: 0;
    visibility: hidden;
    transform: translateY(-10px);
    transition: all 0.3s ease;
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;
    background: white;
    border-radius: 8px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
    padding: 12px;
    z-index: 10;
    border: 1px solid rgba(0, 0, 0, 0.08);
}

.menu-group:hover .menu-grid {
    opacity: 1;
    visibility: visible;
    transform: translateY(0);
}

.menu-grid li {
    display: flex;
    align-items: center;
    padding: 12px 16px;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s;
    width: 100%;
    justify-content: flex-start;
    gap: 12px;
}

.menu-grid li:hover {
    background-color: #f5f7fa;
    transform: translateY(-2px);
}

.menu-icon {
    width: 24px;
    height: 24px;
    margin-bottom: 0;
    flex-shrink: 0;
}

.menu-grid span {
    font-size: 14px;
    color: #333;
    text-align: left;
}

/* 推荐区域样式 */
.section-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 16px;
    padding: 0 20px;
}
</style>